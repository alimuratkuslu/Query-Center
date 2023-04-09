package com.bizu.querycenter.service;

import com.bizu.querycenter.model.Schedule;
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

    @Scheduled(cron = "#{@schedule.getTriggers().get(0).getCronExpression()}")
    public void sendEmail(Schedule schedule, String filter, String databaseName) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress("alimuratkuslu@gmail.com"));

        List<String> employeeMails = schedule.getRecipients();

        for (int i = 0; i < employeeMails.size(); i++) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(employeeMails.get(i).trim()));
        }

        message.setSubject(schedule.getMailSubject());

        List<String> results = employeeService.runQuery(filter, databaseName);
        JSONArray jsonArray = new JSONArray(results);

        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
            jsonObjects.add(jsonObject);
        }

        String table = "<table>";

        if(databaseName.equals("Employees")){
            table += "<tr><th>ID</th><th>Name</th><th>Email</th></tr>";
            for (JSONObject jsonObject : jsonObjects) {
                int id = jsonObject.getInt("_id");
                String name = jsonObject.getString("name");
                String email = jsonObject.getString("email");

                table += "<tr><td>" + id + "</td><td>" + name + "</td><td>" + email + "</td></tr>";
            }
        }
        else if(databaseName.equals("Reports")){
            table += "<tr><th>ID</th><th>Name</th><th>SQL Query</th><th>Active</th></tr>";
            for (JSONObject jsonObject : jsonObjects) {
                int id = jsonObject.getInt("_id");
                String name = jsonObject.getString("name");
                String sqlQuery = jsonObject.getString("sqlQuery");
                Boolean isActive = jsonObject.getBoolean("isActive");

                table += "<tr><td>" + id + "</td><td>" + name + "</td><td>" + sqlQuery + "</td><td>" + isActive + "</td></tr>";
            }
        }
        else if(databaseName.equals("Requests")){
            table += "<tr><th>ID</th><th>Description</th><th>Status</th></tr>";
            for (JSONObject jsonObject : jsonObjects) {
                int id = jsonObject.getInt("_id");
                String description = jsonObject.getString("description");
                String status = jsonObject.getString("status");

                table += "<tr><td>" + id + "</td><td>" + description + "</td><td>" + status + "</td></tr>";
            }
        }
        else if(databaseName.equals("Schedules")){
            table += "<tr><th>ID</th><th>Name</th><th>Mail Subject</th></tr>";
            for (JSONObject jsonObject : jsonObjects) {
                int id = jsonObject.getInt("_id");
                String name = jsonObject.getString("name");
                String mailSubject = jsonObject.getString("mailSubject");

                table += "<tr><td>" + id + "</td><td>" + name + "</td><td>" + mailSubject + "</td></tr>";
            }
        }
        else if(databaseName.equals("Teams")){
            table += "<tr><th>ID</th><th>Name</th><th>Team Email</th></tr>";
            for (JSONObject jsonObject : jsonObjects) {
                int id = jsonObject.getInt("_id");
                String name = jsonObject.getString("name");
                String teamMail = jsonObject.getString("teamMail");

                table += "<tr><td>" + id + "</td><td>" + name + "</td><td>" + teamMail + "</td></tr>";
            }
        }
        else if(databaseName.equals("Triggers")){
            table += "<tr><th>ID</th><th>Name</th><th>Cron Expression</th></tr>";
            for (JSONObject jsonObject : jsonObjects) {
                int id = jsonObject.getInt("_id");
                String name = jsonObject.getString("name");
                String cronExpression = jsonObject.getString("cronExpression");

                table += "<tr><td>" + id + "</td><td>" + name + "</td><td>" + cronExpression + "</td></tr>";
            }
        }

        table += "</table>";

        message.setContent(table, "text/html; charset=utf-8");
        javaMailSender.send(message);
    }
}
