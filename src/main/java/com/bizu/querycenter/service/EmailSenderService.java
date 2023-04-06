package com.bizu.querycenter.service;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final EmployeeService employeeService;

    public EmailSenderService(JavaMailSender javaMailSender, EmployeeService employeeService) {
        this.javaMailSender = javaMailSender;
        this.employeeService = employeeService;
    }

    @Scheduled(cron = "#{@report.getSchedules().get(0).getTriggers().get(0).getCronExpression()}")
    public void sendEmail(Report report, String filter, String projection) throws JsonProcessingException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("alimuratkuslu@gmail.com");

        List<Employee> employees = report.getEmployees();

        List<String> employeeEmails = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            employeeEmails.add(employees.get(i).getEmail().trim());
        }

        mailMessage.setTo(employeeEmails.toArray(new String[0]));

        mailMessage.setSubject(report.getSchedules().get(0).getMailSubject());

        mailMessage.setText(employeeService.runQuery(filter, projection).toString());
        javaMailSender.send(mailMessage);
    }
}
