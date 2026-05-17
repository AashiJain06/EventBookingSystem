package in.aj.main.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import in.aj.main.entity.BookingStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private Long userId;

    private Long eventId;

    private Integer numberOfTickets;
    
    private String selectedSeats;

    private BigDecimal ticketPrice;

    private BigDecimal totalAmount;

    private BookingStatus bookingStatus;

    private LocalDateTime bookingDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}