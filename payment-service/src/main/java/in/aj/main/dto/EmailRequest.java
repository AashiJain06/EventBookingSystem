package in.aj.main.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Recipient email is required")
    private String to;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Event name is required")
    private String eventName;

    @NotBlank(message = "Venue is required")
    private String venue;

    @NotNull(message = "Event date is required")
    private LocalDateTime eventDate;

    @NotEmpty(message = "Seats cannot be empty")
    private List<String> seats;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotBlank(message = "Transaction Id is required")
    private String transactionId;
}