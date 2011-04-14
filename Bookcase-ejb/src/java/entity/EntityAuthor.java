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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Autor knihy.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityAuthor", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "EntityAuthor.findAll", query = "SELECT a FROM EntityAuthor a"),
    @NamedQuery(name = "EntityAuthor.findById", query = "SELECT a FROM EntityAuthor a WHERE a.id = :id"),
    @NamedQuery(name = "EntityAuthor.findByName", query = "SELECT a FROM EntityAuthor a WHERE a.name = :name"),
    @NamedQuery(name = "EntityAuthor.findBySurname", query = "SELECT a FROM EntityAuthor a WHERE a.surname = :surname")})
public class EntityAuthor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "surname", length = 255)
    private String surname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorId")
    private Collection<EntityBook> bookCollection;

    public EntityAuthor() {
    }

    public EntityAuthor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Collection<EntityBook> getBookCollection() {
        return bookCollection;
    }

    public void setBookCollection(Collection<EntityBook> bookCollection) {
        this.bookCollection = bookCollection;
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
        if (!(object instanceof EntityAuthor)) {
            return false;
        }
        EntityAuthor other = (EntityAuthor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ENtityAuthor[id=" + id + "]";
    }
}
