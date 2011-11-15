package rest;

import entity.EntityAuthor;
import entity.EntityBook;
import entity.EntityRelease;
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

    public Book() {
    }

    public Book(EntityRelease release) {
        this.isbn = release.getIsbn();
        this.title = release.getBook().getTitle();
        this.description = release.getBook().getDescription();
        for (EntityAuthor entityAuthor : release.getBook().getAuthorCollection()) {
            Author author = new Author(entityAuthor.getName(), entityAuthor.getSurname());
            this.authors.add(author);
        }
    }

    public String isbn;

    public String title;

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    public Collection<Author> authors = new ArrayList<Author>();

    public String description;
}
