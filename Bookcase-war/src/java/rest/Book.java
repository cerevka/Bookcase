package rest;

import entity.EntityAuthor;
import entity.EntityRelease;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Mapovani knihy na XML reprezentaci.
 * @author Tomáš Čerevka
 */
@XmlRootElement
public class Book {

    public Book() {
    }

    /**
     * Vytahne se vsechna potrebna data z {@link EntityRelease}.
     * @param release Zdroj dat.
     */
    public Book(EntityRelease release) {
        this.isbn = release.getIsbn();
        this.title = release.getBook().getTitle();
        this.description = release.getBook().getDescription();
        for (EntityAuthor entityAuthor : release.getBook().getAuthorCollection()) {
            Author author = new Author(entityAuthor.getName(), entityAuthor.getSurname());
            this.authors.add(author);
        }
        this.publisher = release.getPublisher();
        this.publishDate = DateFormat.getDateInstance(
            DateFormat.MEDIUM).format(release.getPublishDate());
    }

    public String isbn;

    public String title;
    
    public String publisher;
    
    public String description;
    
    public String ean;
    
    public String publishDate;

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    public Collection<Author> authors = new ArrayList<Author>();
}
