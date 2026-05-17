package in.aj.main.dto;




import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "At least one seat must be selected")
    private List<String> selectedSeats;

    @NotNull(message = "Ticket price required")
    private BigDecimal ticketPrice;
}
