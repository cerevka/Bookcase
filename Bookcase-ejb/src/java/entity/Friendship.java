package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Adam Činčura
 */
@Entity
@Table(name = "friendship", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "Friendship.findAll", query = "SELECT f FROM Friendship f"),
    @NamedQuery(name = "Friendship.findByAuthorized", query = "SELECT f FROM Friendship f WHERE f.authorized = :authorized"),
    @NamedQuery(name = "Friendship.findByPersonId1", query = "SELECT f FROM Friendship f WHERE f.personId1 = :personId1"),
    @NamedQuery(name = "Friendship.findByPersonId2", query = "SELECT f FROM Friendship f WHERE f.personId2 = :personId2")})
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "authorized")
    private Integer authorized;
    @JoinColumn(name = "personId1", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person personId1;
    @JoinColumn(name = "personId2", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Person personId2;

    public Friendship() {
    }

    public Friendship(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

    public Person getPersonId1() {
        return personId1;
    }

    public void setPersonId1(Person personId) {
        this.personId1 = personId;
    }

    public Person getPersonId2() {
        return personId2;
    }

    public void setPersonId2(Person personId) {
        this.personId2 = personId;
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
        if (!(object instanceof Friendship)) {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Friendship[id=" + id + "]";
    }
}
