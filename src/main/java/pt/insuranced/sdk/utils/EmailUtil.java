package pt.insuranced.sdk.utils;

import java.sql.Date;
import java.time.LocalDate;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
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
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}
