package com.bizu.querycenter.service;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public void sendEmail(Report report, String filter, String projection) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress("alimuratkuslu@gmail.com"));

        List<Employee> employees = report.getEmployees();

        for (int i = 0; i < employees.size(); i++) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(employees.get(i).getEmail().trim()));
        }

        message.setSubject(report.getSchedules().get(0).getMailSubject());

        List<String> results = employeeService.runQuery(filter, projection);
        JSONArray jsonArray = new JSONArray(results);

        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
            jsonObjects.add(jsonObject);
        }

        String table = "<table>";
        table += "<tr><th>ID</th><th>Name</th><th>Email</th></tr>";
        for (JSONObject jsonObject : jsonObjects) {
            int id = jsonObject.getInt("_id");
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            table += "<tr><td>" + id + "</td><td>" + name + "</td><td>" + email + "</td></tr>";
        }
        table += "</table>";

        message.setContent(table, "text/html; charset=utf-8");
        javaMailSender.send(message);
    }
}
