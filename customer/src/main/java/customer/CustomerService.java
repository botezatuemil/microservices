package customer;

import amqp.RabbitMQMessageProducer;
import clients.fraud.FraudCheckResponse;
import clients.fraud.FraudClient;
import clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer producer;

    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName()).email(request.email()).build();
        customerRepository.saveAndFlush(customer);

        // todo: check if email valid
        // todo: check if email not taken
        // todo: check if fraudster

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster caught");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to my microservice project...", customer.getFirstName()),
                        "Emil"
        );

        producer.publish(notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key");

    }
}
