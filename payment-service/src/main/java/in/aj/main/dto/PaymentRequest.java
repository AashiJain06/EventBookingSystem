package in.aj.main.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

	@NotNull(message = "Booking ID cannot be null")
    private Long bookingId;
}
