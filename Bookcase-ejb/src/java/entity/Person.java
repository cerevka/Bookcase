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
 *
 * @author Adam Činčura
 */
@Entity
@Table(name = "person", catalog = "bookcase", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})})
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT u FROM Person u"),
    @NamedQuery(name = "Person.findByName", query = "SELECT u FROM Person u WHERE u.name = :name"),
    @NamedQuery(name = "Person.findBySurname", query = "SELECT u FROM Person u WHERE u.surname = :surname"),
    @NamedQuery(name = "Person.findById", query = "SELECT u FROM Person u WHERE u.id = :id"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT u FROM Person u WHERE u.email = :email"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT u FROM Person u WHERE u.password = :password")})
public class Person implements Serializable {

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
    @ManyToMany(mappedBy = "personCollection")
    private Collection<Privilege> privilegeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private Collection<Reservation> reservationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId1")
    private Collection<Friendship> friendshipCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId2")
    private Collection<Friendship> friendshipCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private Collection<Shelf> shelfCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private Collection<Borrow> borrowCollection;

    public Person() {
    }

    public Person(Integer id) {
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

    public Collection<Privilege> getPrivilegeCollection() {
        return privilegeCollection;
    }

    public void setPrivilegeCollection(Collection<Privilege> privilegeCollection) {
        this.privilegeCollection = privilegeCollection;
    }

    public Collection<Reservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<Reservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    public Collection<Friendship> getFriendshipCollection() {
        return friendshipCollection1;
    }

    public void setFriendshipCollection(Collection<Friendship> friendshipCollection) {
        this.friendshipCollection1 = friendshipCollection;
    }

    public Collection<Friendship> getFriendshipCollection1() {
        return friendshipCollection2;
    }

    public void setFriendshipCollection1(Collection<Friendship> friendshipCollection1) {
        this.friendshipCollection2 = friendshipCollection1;
    }

    public Collection<Shelf> getShelfCollection() {
        return shelfCollection;
    }

    public void setShelfCollection(Collection<Shelf> shelfCollection) {
        this.shelfCollection = shelfCollection;
    }

    public Collection<Borrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<Borrow> borrowCollection) {
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Person[id=" + id + "]";
    }
}
