package in.aj.main.dto;




import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Event id is required")
    private Long eventId;

    @NotNull(message = "Number of tickets required")
    @Min(value = 1, message = "At least 1 ticket required")
    private Integer numberOfTickets;

    @NotNull(message = "Ticket price required")
    private BigDecimal ticketPrice;
}
