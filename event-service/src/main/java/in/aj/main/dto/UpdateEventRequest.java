package in.aj.main.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventRequest {

    private String title;

    private String description;

    private String venue;

    private String city;

    private String category;

    private LocalDateTime eventDate;

    private Integer totalSeats;

    private Integer availableSeats;

    private BigDecimal ticketPrice;
}
