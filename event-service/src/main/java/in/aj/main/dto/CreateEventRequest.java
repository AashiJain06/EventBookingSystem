package in.aj.main.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Venue is required")
    private String venue;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Event date is required")
    private LocalDateTime eventDate;

    @NotNull(message = "Total seats required")
    @Min(value = 1, message = "Seats must be greater than 0")
    private Integer totalSeats;

    @NotNull(message = "Ticket price required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal ticketPrice;
}
