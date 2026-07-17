package in.aj.main.service;

import in.aj.main.dto.PaymentRequest;
import in.aj.main.dto.PaymentResponse;


public interface PaymentService {

	PaymentResponse processPayment(PaymentRequest paymentRequest);
}
