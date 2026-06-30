package in.aj.main.service.impl;

import org.springframework.stereotype.Service;

import in.aj.main.dto.EmailRequest;
import in.aj.main.dto.EmailResponse;
import in.aj.main.service.NotificationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    @Override
    public EmailResponse sendEmail(
            EmailRequest request) {

        return null;
    }

}
