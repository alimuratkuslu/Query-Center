package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.SendEmailRequest;
import com.bizu.querycenter.service.EmailSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/email")
public class EmailSenderController {

    private final EmailSenderService emailSenderService;

    public EmailSenderController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody SendEmailRequest request){
        emailSenderService.sendEmail(request.getReport(), request.getFilter(), request.getProjection());

        return "Message Sent!";
    }
}
