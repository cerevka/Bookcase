package bean.managed.sessionScoped;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "language")
@SessionScoped
public class BeanManagedLanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    private static final Logger logger = Logger.getLogger(BeanManagedLanguage.class.getName());

    /**
     * Vytvori translator.
     */
    public BeanManagedLanguage() {
    }

    /**
     * Vrati aktualni lokale.
     * @return Aktualni lokale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Vrati aktualni jazyk.
     * @return Aktualni jazyk.
     */
    public String getLanguage() {
        return locale.getLanguage();
    }

    /**
     * Zmeni jazyk
     * @param language Novy jazyk.
     */
    public void doChange(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
