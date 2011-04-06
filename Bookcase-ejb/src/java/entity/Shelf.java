package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "shelf", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "Shelf.findAll", query = "SELECT s FROM Shelf s"),
    @NamedQuery(name = "Shelf.findById", query = "SELECT s FROM Shelf s WHERE s.id = :id"),
    @NamedQuery(name = "Shelf.findByName", query = "SELECT s FROM Shelf s WHERE s.name = :name")})
public class Shelf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 255)
    private String name;
    @ManyToMany(mappedBy = "shelfCollection")
    private Collection<Copy> copyCollection;
    @JoinColumn(name = "belongs", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private User belongs;

    public Shelf() {
    }

    public Shelf(Integer id) {
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

    public Collection<Copy> getCopyCollection() {
        return copyCollection;
    }

    public void setCopyCollection(Collection<Copy> copyCollection) {
        this.copyCollection = copyCollection;
    }

    public User getBelongs() {
        return belongs;
    }

    public void setBelongs(User belongs) {
        this.belongs = belongs;
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
        if (!(object instanceof Shelf)) {
            return false;
        }
        Shelf other = (Shelf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Shelf[id=" + id + "]";
    }
}
