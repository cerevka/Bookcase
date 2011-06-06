package validator;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Tomáš Čerevka
 */
@FacesValidator(value = "emailValidator")
public class emailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String enteredEmail = value.toString();
        Pattern patter = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = patter.matcher(enteredEmail);
        
        if (!matcher.matches()) {
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "bundle");
            FacesMessage message = new FacesMessage(bundle.getString("message.error.email"));
            throw new ValidatorException(message);
        }        
    }
}
