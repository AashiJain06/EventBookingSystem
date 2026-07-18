package in.aj.main.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import in.aj.main.dto.BookingNotificationDetails;
import in.aj.main.dto.BookingResponse;
import in.aj.main.dto.EmailRequest;
import in.aj.main.dto.PaymentRequest;
import in.aj.main.dto.PaymentResponse;
import in.aj.main.entity.Payment;
import in.aj.main.entity.PaymentStatus;
import in.aj.main.feign.cleint.BookingCleint;
import in.aj.main.feign.cleint.NotificationClient;
import in.aj.main.repository.PaymentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;


@Service
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;
	private final BookingCleint bookingClient;
	private final NotificationClient notificationClient;
	public PaymentServiceImpl(PaymentRepository paymentRepository, BookingCleint bookingClient , NotificationClient notificationClient) {
		this.paymentRepository = paymentRepository;
		this.bookingClient = bookingClient;
		this.notificationClient = notificationClient;
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
			BookingNotificationDetails bookingDetails =  bookingClient.confirmBooking(paymentRequest.getBookingId());
			EmailRequest emailRequest =
			        EmailRequest.builder()

			        .to(
			            bookingDetails.getUserEmail()
			        )

			        .customerName(
			            bookingDetails.getUserName()
			        )

			        .eventName(
			            bookingDetails.getEventName()
			        )

			        .venue(
			            bookingDetails.getVenue()
			        )

			        .eventDate(
			            bookingDetails.getEventDate()
			        )

			        .seats(
			            bookingDetails.getSeats()
			        )

			        .amount(
			            payment.getAmount()
			        )

			        .transactionId(
			            payment.getTransactionId()
			        )

			        .build();


			sendNotification(emailRequest);
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
		
		@Retry(name = "notificationRetry")
		@CircuitBreaker(
		        name = "notificationService",
		        fallbackMethod = "notificationFallback"
		)
		public void sendNotification(
		        EmailRequest emailRequest) {

			System.out.println("Trying to send email");
		    notificationClient.sendEmail(emailRequest);

		}
		
		public void notificationFallback(
		        EmailRequest emailRequest,
		        Throwable ex) {

		    System.out.println("=================================");
		    System.out.println("Notification Service is DOWN");
		    System.out.println("Email will be sent later.");
		    System.out.println("Reason : " + ex.getMessage());
		    System.out.println("=================================");
		}
	}


