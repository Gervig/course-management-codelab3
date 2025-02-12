package app.entities;
import app.enums.PackageDeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate
public class Package
{
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="tracking_number")
    private String trackingNumber;
    @Column(name="sender_name")
    private String senderName;
    @Column(name="receiver_name")
    private String receiverName;
    @Enumerated(EnumType.STRING)
    @Column(name="delivery_status")
    private PackageDeliveryStatus deliveryStatus;
    private LocalDateTime Updated;

    public void setDeliveryStatus(PackageDeliveryStatus deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }

    public void setUpdatedToNow()
    {
        Updated = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
