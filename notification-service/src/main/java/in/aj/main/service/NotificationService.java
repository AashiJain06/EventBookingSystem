package in.aj.main.service;

import in.aj.main.dto.EmailRequest;
import in.aj.main.dto.EmailResponse;

public interface NotificationService {

    EmailResponse sendEmail(EmailRequest request);

}
