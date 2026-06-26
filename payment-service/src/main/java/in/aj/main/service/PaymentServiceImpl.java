package in.aj.main.service;

import java.util.UUID;

import in.aj.main.dto.BookingResponse;
import in.aj.main.dto.PaymentRequest;
import in.aj.main.dto.PaymentResponse;
import in.aj.main.entity.Payment;
import in.aj.main.entity.PaymentStatus;
import in.aj.main.feign.cleint.BookingCleint;
import in.aj.main.repository.PaymentRepository;

public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final BookingCleint bookingClient;
	public PaymentServiceImpl(PaymentRepository paymentRepository, BookingCleint bookingClient) {
		this.paymentRepository = paymentRepository;
		this.bookingClient = bookingClient;
	}
	@Override
	public PaymentResponse processPayment(PaymentRequest paymentRequest) {
		// Check if the booking exists
		BookingResponse booking =  bookingClient.getBooking(paymentRequest.getBookingId());
		if (booking == null) {
			throw new IllegalArgumentException("Booking not found for ID: " + paymentRequest.getBookingId());
		}
		if (booking.getBookingStatus().equals("CONFIRMED")) {
			// Handle the case when the booking is already confirmed
			throw new IllegalStateException("Booking is already confirmed for ID: " + paymentRequest.getBookingId());
		}
		if (booking.getBookingStatus().equals("CANCELLED")) {
			// Handle the case when the booking is already cancelled
			throw new IllegalStateException("Booking is already cancelled for ID: " + paymentRequest.getBookingId());
			
		}
		Payment payment = Payment.builder()
				.bookingId(paymentRequest.getBookingId())
				.amount(booking.getTotalAmount())
				.status(PaymentStatus.SUCCESS)
				.transactionId(generateTransactionId())
				.build();
		paymentRepository.save(payment);
		boolean paymentSucess = true; // Simulate payment success
		if(paymentSucess)
			{
			payment.setStatus(PaymentStatus.SUCCESS);
			paymentRepository.save(payment);
			bookingClient.confirmBooking(paymentRequest.getBookingId());
			return PaymentResponse.builder()
					.paymentId(payment.getId())
					.bookingId(payment.getBookingId())
					.amount(payment.getAmount())
					.transactionId(payment.getTransactionId())
					.status(payment.getStatus())
					.build();
		}
		else
		{
			payment.setStatus(PaymentStatus.FAILED);
			paymentRepository.save(payment);
			bookingClient.cancelBooking(paymentRequest.getBookingId());
			return PaymentResponse.builder()
					.paymentId(payment.getId())
					.bookingId(payment.getBookingId())
					.amount(payment.getAmount())
					.transactionId(payment.getTransactionId())
					.status(payment.getStatus())
					.build();
		}
	}
		private String generateTransactionId() {

		    return "TXN-" +
		            UUID.randomUUID()
		                    .toString()
		                    .replace("-","")
		                    .substring(0,12)
		                    .toUpperCase();

		}
	}


