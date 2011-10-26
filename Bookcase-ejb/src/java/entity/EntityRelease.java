package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entita představující vydání knihy (šarže).
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityRelease", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityRelease.FIND_ALL, query = "SELECT r FROM EntityRelease r"),
    @NamedQuery(name = EntityRelease.FIND_BY_ID, query = "SELECT r FROM EntityRelease r WHERE r.id = :id"),
    @NamedQuery(name = EntityRelease.FIND_BY_ISBN, query = "SELECT r FROM EntityRelease r WHERE r.isbn = :isbn"),
    @NamedQuery(name = EntityRelease.FIND_BY_EAN, query = "SELECT r FROM EntityRelease r WHERE r.ean = :ean")
})
public class EntityRelease implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityRelease.findAll";

    public static final String FIND_BY_ID = "EntityRelease.findById";

    public static final String FIND_BY_ISBN = "EntityRelease.findByISBN";

    public static final String FIND_BY_EAN = "EntityRelease.findByEAN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "isbn", length = 13)
    private String isbn;

    @Column(name = "ean", length = 20)
    private String ean;

    @Column(name = "publisher", length = 200)
    private String publisher;

    @Column(name = "publishDate")
    @Temporal(TemporalType.DATE)
    private Date publishDate;

    @JoinColumn(name = "bookId", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private EntityBook book;
        
    @OneToMany(mappedBy="release", cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityPrint> printsCollection = new ArrayList<EntityPrint>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public EntityBook getBook() {
        return book;
    }

    public void setBook(EntityBook book) {
        this.book = book;
    }

    public Collection<EntityPrint> getPrintsCollection() {
        return printsCollection;
    }

    public void setPrintsCollection(Collection<EntityPrint> printsCollection) {
        this.printsCollection = printsCollection;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityRelease)) {
            return false;
        }
        EntityRelease other = (EntityRelease) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityRelease[ id=" + id + " ]";
    }
}
