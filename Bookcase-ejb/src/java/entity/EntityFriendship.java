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
 * Entita přátelství.
 * @author Adam Činčura
 * @authro Tomáš Čerevka
 */
@Entity
@Table(name = "entityFriendship", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "EntityFriendship.findAll", query = "SELECT f FROM EntityFriendship f"),
    @NamedQuery(name = "EntityFriendship.findByAuthorized", query = "SELECT f FROM EntityFriendship f WHERE f.authorized = :authorized"),
    @NamedQuery(name = "EntityFriendship.findByUserId1", query = "SELECT f FROM EntityFriendship f WHERE f.userId1 = :userId1"),
    @NamedQuery(name = "EntityFriendship.findByUserId2", query = "SELECT f FROM EntityFriendship f WHERE f.userId2 = :userId2")})
public class EntityFriendship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "authorized")
    private Integer authorized;
    @JoinColumn(name = "userId1", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EntityUser userId1;
    @JoinColumn(name = "userId2", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EntityUser userId2;

    public EntityFriendship() {
    }

    public EntityFriendship(int id) {
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

    public EntityUser getUserId1() {
        return userId1;
    }

    public void setUserId1(EntityUser userId) {
        this.userId1 = userId;
    }

    public EntityUser getUserId2() {
        return userId2;
    }

    public void setUserId2(EntityUser userId) {
        this.userId2 = userId;
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
        if (!(object instanceof EntityFriendship)) {
            return false;
        }
        EntityFriendship other = (EntityFriendship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityFriendship[id=" + id + "]";
    }
}
