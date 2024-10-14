package net.engineeringdigest.journalApp.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

//    @Autowired
//    private JavaMailSender javaMailSender;

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

//    public void sendEmail(String to, String subject, String body) {
//        try {
//            SimpleMailMessage mail = new SimpleMailMessage();
//            mail.setTo(to);
//            mail.setSubject(subject);
//            mail.setText(body);
//            javaMailSender.send(mail);
//        } catch (Exception e) {
//            log.error("Exception while sendEmail ", e);
//        }
//    }
    public String sendEmailUsingSendGRid(String to, String subject, String body) {
        Email from = new Email("pawankumar1466269393@example.com"); // Your verified email
        Email toEmail = new Email(to);

        Content emailContent = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            return "Status Code: " + response.getStatusCode() +
                    "\nBody: " + response.getBody() +
                    "\nHeaders: " + response.getHeaders();
        } catch (Exception ex) {
            return "Error occurred: " + ex.getMessage();
        }
    }

}
