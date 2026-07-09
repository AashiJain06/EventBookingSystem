package in.aj.main.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingNotificationDetails {

    private Long bookingId;

    private String userName;

    private String userEmail;

    private String eventName;

    private String venue;

    private LocalDateTime eventDate;

    private List<String> seats;

    private BigDecimal amount;
}