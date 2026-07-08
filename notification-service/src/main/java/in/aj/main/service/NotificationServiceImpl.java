package in.aj.main.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import in.aj.main.dto.EmailRequest;
import in.aj.main.dto.EmailResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {
	
	private final JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;
    @Override
    public EmailResponse sendEmail(
            EmailRequest request) {

    	SimpleMailMessage message =
                new SimpleMailMessage();

        message.setTo(request.getTo());

        message.setSubject(
                "Booking Confirmation - Event Ticket Booking System");

        message.setFrom(fromEmail);
        message.setText(
                buildEmailBody(request));

        try
        {
			mailSender.send(message);
		} catch (Exception e) {
			return EmailResponse.builder()
					.message("Failed to send email: " + e.getMessage())
					.build();
		}
     
        return EmailResponse.builder()
                .message("Email sent successfully.")
                .build();
    }
    
    private String buildEmailBody(EmailRequest request) {

        return """
                Hello %s,

                Your booking has been confirmed successfully!

                ------------------------------
                Booking Details
                ------------------------------

                Event           : %s
                Venue           : %s
                Event Date      : %s
                Seats           : %s
                Amount Paid     : ₹%s
                Transaction Id  : %s

                Thank you for booking with us.

                Enjoy your event!

                Regards,
                Event Ticket Booking Team
                """.formatted(

                request.getCustomerName(),
                request.getEventName(),
                request.getVenue(),
                request.getEventDate(),
                String.join(", ", request.getSeats()),
                request.getAmount(),
                request.getTransactionId()

        );
    }

}
