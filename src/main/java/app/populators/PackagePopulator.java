package app.populators;

import app.dao.PackageDAO;
import app.entities.Package;
import app.enums.PackageDeliveryStatus;

public class PackagePopulator
{
    public static Package[] populate(PackageDAO packageDAO) {
        Package p1 = Package.builder()
                .trackingNumber("ABC123")
                .senderName("Alice")
                .receiverName("Bob")
                .deliveryStatus(PackageDeliveryStatus.PENDING)
                .build();

        Package p2 = Package.builder()
                .trackingNumber("XYZ789")
                .senderName("Charlie")
                .receiverName("Diana")
                .deliveryStatus(PackageDeliveryStatus.IN_TRANSIT)
                .build();

        return new Package[]{p1, p2};
    }
}
