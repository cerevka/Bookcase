package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entita knihy.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityBook", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityBook.FIND_ALL, query = "SELECT b FROM EntityBook b"),
    @NamedQuery(name = EntityBook.FIND_BY_ID, query = "SELECT b FROM EntityBook b WHERE b.id = :id"),
    @NamedQuery(name = EntityBook.FIND_BY_TITLE, query = "SELECT b FROM EntityBook b WHERE b.title = :title"),
    @NamedQuery(name = EntityBook.FIND_BY_DESCRIPTION, query = "SELECT b FROM EntityBook b WHERE b.description = :description")
})
public class EntityBook implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "EntityBook.findAll";
    
    public static final String FIND_BY_ID = "EntityBook.findById";
    
    public static final String FIND_BY_TITLE = "EntityBook.findByTitle";
    
    public static final String FIND_BY_DESCRIPTION = "EntityBook.findByDescription";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "description", length = 1024)
    private String description;

    /*
    @JoinColumn(name = "authorId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityAuthor authorId;
    */
    
    @ManyToMany(mappedBy = "bookCollection", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityAuthor> authorCollection = new ArrayList<EntityAuthor>();
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "bookId")
    private Collection<EntityCopy> copyCollection = new ArrayList<EntityCopy>();

    public EntityBook() {
    }

    public EntityBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<EntityAuthor> getAuthorCollection() {
        return authorCollection;
    }

    public void setAuthorCollection(Collection<EntityAuthor> authorCollection) {
        this.authorCollection = authorCollection;
    }

    public Collection<EntityCopy> getCopyCollection() {
        return copyCollection;
    }

    public void setCopyCollection(Collection<EntityCopy> copyCollection) {
        this.copyCollection = copyCollection;
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
        if (!(object instanceof EntityBook)) {
            return false;
        }
        EntityBook other = (EntityBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Book[id=" + id + "]";
    }
}