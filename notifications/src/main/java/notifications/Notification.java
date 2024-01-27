package notifications;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class Notification {
    @Id
    @SequenceGenerator(
            name = "notification_id_sequence",
            sequenceName = "notification_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_id_sequence"
    )
    private Integer notificationId;
    private Integer toCustomerId;

    private String message;
    private String sender;
    private LocalDateTime sentAt;
    private String toCustomerEmail;


}
