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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = EntityAuthor.FIND_ALL, query = "SELECT a FROM EntityAuthor a"),
    @NamedQuery(name = EntityAuthor.FIND_BY_ID, query = "SELECT a FROM EntityAuthor a WHERE a.id = :id"),
    @NamedQuery(name = EntityAuthor.FIND_BY_NAME, query = "SELECT a FROM EntityAuthor a WHERE a.name = :name"),
    @NamedQuery(name = EntityAuthor.FIND_BY_SURNAME, query = "SELECT a FROM EntityAuthor a WHERE a.surname = :surname")
})
public class EntityAuthor implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityAuthor.findAll";

    public static final String FIND_BY_ID = "EntityAuthor.findById";

    public static final String FIND_BY_NAME = "EntityAuthor.findByName";

    public static final String FIND_BY_SURNAME = "EntityAuthor.findBySurname";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "surname", length = 255)
    private String surname;

    /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authorId")
    private Collection<EntityBook> bookCollection;
     */
    @JoinTable(name = "writenBy",
    joinColumns = {
        @JoinColumn(name = "authorId", referencedColumnName = "id", nullable = false)
    },
    inverseJoinColumns = {
        @JoinColumn(name = "bookId", referencedColumnName = "id", nullable = false)
    })
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityBook> bookCollection = new ArrayList<EntityBook>();

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
