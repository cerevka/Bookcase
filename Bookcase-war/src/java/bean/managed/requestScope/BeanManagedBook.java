package bean.managed.requestScope;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityCopy;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Prace s knihami, svazky a autory.
 * @author Tomáš Čerevka
 */
@ManagedBean(name = "book")
@RequestScoped
public class BeanManagedBook {
    
    private static final Logger logger = Logger.getLogger(BeanManagedBook.class.getName());
    
    private EntityBook book;
    
    private EntityCopy copy;
    
    private EntityAuthor author;

    public EntityAuthor getAuthor() {
        return author;
    }

    public void setAuthor(EntityAuthor author) {
        this.author = author;
    }

    public EntityBook getBook() {
        return book;
    }

    public void setBook(EntityBook book) {
        this.book = book;
    }

    public EntityCopy getCopy() {
        return copy;
    }

    public void setCopy(EntityCopy copy) {
        this.copy = copy;
    }
    
    

    
    public BeanManagedBook() {
    }
}
