package bean.managed.applicationScope;

import bean.stateless.LocalBeanSessionBookcase;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 * Zije tak dlouho jako cela aplikace.
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "application", eager = true)
@ApplicationScoped
public class beanManagedApplication {
    
    @EJB
    private LocalBeanSessionBookcase beanSessionBookcase;
    
    public beanManagedApplication() {
    }

    /**
     * Pri svem vytvoreni zajisti nakonfigurovani databaze.
     * - Vytvoreni view pro prihlasovani.
     * - Vytvoreni uzivatelskych roli.
     * - Vytvoreni testovaciho uzivatele.
     */
    @PostConstruct
    private void init() {
        beanSessionBookcase.initDatabase();
    }
}
