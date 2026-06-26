package in.aj.main.dto;

import java.math.BigDecimal;

import in.aj.main.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {

    private Long paymentId;

    private Long bookingId;

    private BigDecimal amount;

    private String transactionId;

    private PaymentStatus  status;
}

