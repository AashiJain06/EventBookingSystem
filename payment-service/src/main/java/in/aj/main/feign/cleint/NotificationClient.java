package in.aj.main.feign.cleint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import in.aj.main.dto.EmailRequest;
import in.aj.main.dto.EmailResponse;

@FeignClient(name = "notification-service")
public interface NotificationClient {
	
	@PostMapping("/api/notifications/email")
	EmailResponse sendEmail(EmailRequest request);

}
