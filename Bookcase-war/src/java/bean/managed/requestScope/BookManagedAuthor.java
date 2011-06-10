package bean.managed.requestScope;

import entity.EntityAuthor;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Prace s autory.
 * @author Tomáš Čerevka
 */
@ManagedBean
@RequestScoped
public class BookManagedAuthor {
    
    private EntityAuthor author;

    public EntityAuthor getAuthor() {
        return author;
    }

    public void setAuthor(EntityAuthor author) {
        this.author = author;
    }   
    

    
    public BookManagedAuthor() {
    }
}
