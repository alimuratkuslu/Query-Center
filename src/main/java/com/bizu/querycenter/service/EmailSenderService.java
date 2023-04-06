package com.bizu.querycenter.service;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    public void sendEmail(Report report, String filter, String projection){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("alimuratkuslu@gmail.com");

        List<Employee> employees = report.getEmployees();

        List<String> employeeEmails = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            employeeEmails.add(employees.get(i).getEmail());
        }

        mailMessage.setTo("e210503065@stud.tau.edu.tr");

        mailMessage.setSubject(report.getSchedules().get(0).getMailSubject());
        mailMessage.setText(employeeService.runQuery(filter, projection).toString());

        javaMailSender.send(mailMessage);
    }
}
