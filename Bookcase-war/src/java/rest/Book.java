package rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
    
    public String publisher;
    
    public String description;
    
    public String ean;
    
    public Date publishDate;
    
    @XmlElementWrapper(name="authors")
    @XmlElement(name="author")
    public Collection<Author> authors = new ArrayList<Author>();
}
