package in.aj.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.aj.main.dto.EmailRequest;
import in.aj.main.dto.EmailResponse;
import in.aj.main.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/email")
    public ResponseEntity<EmailResponse> sendEmail(
            @Valid @RequestBody EmailRequest request) {

        EmailResponse response =
                notificationService.sendEmail(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
