package clients.notification;

public record NotificationRequest(Integer customerId, String email, String message, String firstName) {
}
