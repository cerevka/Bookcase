package rest;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tomáš Čerevka
 */
@XmlRootElement
public class Book {

    public String isbn;
    
    public String title;
    
    @XmlElementWrapper(name="authors")
    @XmlElement(name="author")
    public Collection<Author> authors = new ArrayList<Author>();
}
