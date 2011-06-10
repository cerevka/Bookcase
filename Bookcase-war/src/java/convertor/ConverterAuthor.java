package convertor;

import entity.EntityAuthor;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Konvertor pro entitu autora.
 * @author Tomáš Čerevka
 */
@FacesConverter(forClass = EntityAuthor.class)
public class ConverterAuthor implements Converter {
    
    private static final Logger logger = Logger.getLogger(ConverterAuthor.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "bundle");
        EntityAuthor author = null;
        try {
            author = new EntityAuthor();
            author.setId(new Integer(value));            
        } catch (NumberFormatException exception) {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("message.error.unknownAuthor"), "");
            logger.log(Level.SEVERE, "Invalid id for author.", exception);
            throw new ConverterException(facesMessage);
        }
        return author;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        EntityAuthor author = (EntityAuthor) value;
        return author.getId().toString();
    }
}
