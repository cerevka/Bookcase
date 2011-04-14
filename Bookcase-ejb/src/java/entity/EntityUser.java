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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entita uzivatele.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityUser", catalog = "bookcase", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@NamedQueries({
    @NamedQuery(name = "EntityUser.findAll", query = "SELECT u FROM EntityUser u"),
    @NamedQuery(name = "EntityUser.findByName", query = "SELECT u FROM EntityUser u WHERE u.name = :name"),
    @NamedQuery(name = "EntityUser.findBySurname", query = "SELECT u FROM EntityUser u WHERE u.surname = :surname"),
    @NamedQuery(name = "EntityUser.findById", query = "SELECT u FROM EntityUser u WHERE u.id = :id"),
    @NamedQuery(name = "EntityUser.findByEmail", query = "SELECT u FROM EntityUser u WHERE u.email = :email"),
    @NamedQuery(name = "EntityUser.findByPassword", query = "SELECT u FROM EntityUser u WHERE u.password = :password")})
public class EntityUser implements Serializable {

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
    @Column(name = "email", length = 255)
    private String email;
    @Column(name = "password", length = 255)
    private String password;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<EntityGroup> groupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<EntityReservation> reservationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId1")
    private Collection<EntityFriendship> friendshipCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId2")
    private Collection<EntityFriendship> friendshipCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<EntityShelf> shelfCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<EntityBorrow> borrowCollection;

    public EntityUser() {
    }

    public EntityUser(Integer id) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<EntityGroup> getGroupCollection() {
        return groupCollection;
    }

    public void setGroupCollection(Collection<EntityGroup> groupCollection) {
        this.groupCollection = groupCollection;
    }

    public Collection<EntityReservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<EntityReservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    public Collection<EntityFriendship> getFriendshipCollection() {
        return friendshipCollection1;
    }

    public void setFriendshipCollection(Collection<EntityFriendship> friendshipCollection) {
        this.friendshipCollection1 = friendshipCollection;
    }

    public Collection<EntityFriendship> getFriendshipCollection1() {
        return friendshipCollection2;
    }

    public void setFriendshipCollection1(Collection<EntityFriendship> friendshipCollection1) {
        this.friendshipCollection2 = friendshipCollection1;
    }

    public Collection<EntityShelf> getShelfCollection() {
        return shelfCollection;
    }

    public void setShelfCollection(Collection<EntityShelf> shelfCollection) {
        this.shelfCollection = shelfCollection;
    }

    public Collection<EntityBorrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<EntityBorrow> borrowCollection) {
        this.borrowCollection = borrowCollection;
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
        if (!(object instanceof EntityUser)) {
            return false;
        }
        EntityUser other = (EntityUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityUser[id=" + id + "]";
    }
}
