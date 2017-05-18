package pt.insuranced.sdk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.insuranced.models.Client;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class EmailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);

    /**
     * Utility method to send simple HTML email
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            // set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("noreply@insuranced.com", "InsuranceD"));

            msg.setReplyTo(InternetAddress.parse("noreply@insuranced.com", false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(Date.valueOf(LocalDate.now()));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            LOGGER.info("Message is ready");

            Transport.send(msg);

            LOGGER.info("E-mail sent successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility method to send welcome email to client
     *
     * @param session
     * @param client
     */
    public static void sendWelcomeEmail(Session session, Client client) {
        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress("noreply@insuranced.com", "InsuranceD"));

            msg.setReplyTo(InternetAddress.parse("noreply@insuranced.com", false));

            msg.setSubject("Welcome to InsuranceD", "UTF-8");

            msg.setSentDate(Date.valueOf(LocalDate.now()));

            String emailID = client.getPersonalIdentification().getEmail();
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));

            LOGGER.info("Message is ready");

            MimeMultipart content = new MimeMultipart("related");

            // ContentID is used by both parts
            String cid = "image_id";

            String firstName = client.getPersonalIdentification().getFirstName();
            String lastName = client.getPersonalIdentification().getLastName();
            String username = client.getUsername();
            String password = client.getPassword().getHashedPassword();

            // HTML part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("<html><head><title>Welcome to InsuranceD</title></head>"
                    + "<body><div>Hi " + firstName + " " + lastName + ",</div><br>"
                    + "<div>We would like to welcome you to InsuranceD! Do not hesitate to contact us through our "
                    + "customer center at <i>support@insuranced.com.</i></div><br>"
                    + "<div>Here are the details to access our digital channels portal:<br>"
                    + "Username: <b>" + username + "</b><br>"
                    + "Password: <b>" + password + "</b></div><br>"
                    + "<div>With our Best Regards,<br><img src=\"cid:" + cid + "\" /></div>\n"
                    + "</body></html>", "US-ASCII", "html");
            content.addBodyPart(textPart);

            // Image part
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.attachFile("./src/main/resources/logo.png");
            imagePart.setContentID("<" + cid + ">");
            imagePart.setDisposition(MimeBodyPart.INLINE);
            content.addBodyPart(imagePart);

            // Set the multipart message to the email message
            msg.setContent(content);

            // Send message
            Transport.send(msg);
            LOGGER.info("E-mail with image sent successfully!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
