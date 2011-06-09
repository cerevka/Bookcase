package bean.messageDriven;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Umistuje e-maily k odeslani do fronty a zpracovava ji.
 * @author Tomáš Čerevka
 */
@MessageDriven(mappedName = "jms/bookcaseMail", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class BeanMessageDrivenMail implements MessageListener {

    public static final Logger logger = Logger.getLogger(BeanMessageDrivenMail.class.getName());

    @Resource(name = "mail/bookcase")
    private javax.mail.Session mailSession;

    public BeanMessageDrivenMail() {
    }

    /**
     * Zpracovani zpravy.
     * @param message Zprava s parametry "recipient", "subject", "body".
     */
    @Override
    public void onMessage(Message message) {
        try {
            // Pripravi se data.
            String recipient = message.getStringProperty("recipient");
            String subject = message.getStringProperty("subject");
            String body = message.getStringProperty("body");

            // Vytvori se e-mail.
            javax.mail.Message email = new MimeMessage(mailSession);
            email.setRecipients(RecipientType.TO, InternetAddress.parse(recipient, false));
            email.setSubject(subject); 
            email.setContent("<html>" + body + "</html>", "text/html");
            email.setSentDate(new Date());

            // E-mail se odesle.
            Transport.send(email);
        } catch (AddressException exception) {
            logger.log(Level.SEVERE, "Error when processing a message: address exception.", exception);
        } catch (MessagingException exception) {
            logger.log(Level.SEVERE, "Error when processing a message: messaging exception", exception);
        } catch (JMSException exception) {
            logger.log(Level.SEVERE, "Error when processing a message: JMS exception", exception);
        }
    }
}
