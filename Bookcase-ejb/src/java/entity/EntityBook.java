package entity;

import java.io.Serializable;
import java.util.Collection;
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

/**
 * Entita knihy.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityBook", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "EntityBook.findAll", query = "SELECT b FROM EntityBook b"),
    @NamedQuery(name = "EntityBook.findById", query = "SELECT b FROM EntityBook b WHERE b.id = :id"),
    @NamedQuery(name = "EntityBook.findByTitle", query = "SELECT b FROM EntityBook b WHERE b.title = :title"),
    @NamedQuery(name = "EntityBook.findByDescription", query = "SELECT b FROM EntityBook b WHERE b.description = :description")})
public class EntityBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "title", length = 255)
    private String title;
    @Column(name = "description", length = 1024)
    private String description;
    @JoinColumn(name = "authorId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityAuthor authorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    private Collection<EntityCopy> copyCollection;

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

    public EntityAuthor getAuthorId() {
        return authorId;
    }

    public void setAuthorId(EntityAuthor authorId) {
        this.authorId = authorId;
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
