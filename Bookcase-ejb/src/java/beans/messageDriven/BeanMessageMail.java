package beans.messageDriven;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Tomáš Čerevka
 */
@MessageDriven(mappedName = "jms/bookcaseMail", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    })
public class BeanMessageMail implements MessageListener {
    
    public BeanMessageMail() {
    }

    @Override
    public void onMessage(Message message) {
    }
    
}
