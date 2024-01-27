package notifications;

import clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder().toCustomerId(notificationRequest.customerId()).toCustomerEmail(notificationRequest.email()).message(notificationRequest.message()).sender("Emil").sentAt(LocalDateTime.now()).build();

        notificationRepository.save(notification);
    }
}
