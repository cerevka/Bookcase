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
    @NamedQuery(name = EntityUser.FIND_ALL, query = "SELECT u FROM EntityUser u"),
    @NamedQuery(name = EntityUser.FIND_BY_ID, query = "SELECT u FROM EntityUser u WHERE u.id = :id"),
    @NamedQuery(name = EntityUser.FIND_BY_NAME, query = "SELECT u FROM EntityUser u WHERE u.name = :name"),
    @NamedQuery(name = EntityUser.FIND_BY_NAME_AND_SURNAME, query = "SELECT u FROM EntityUser u WHERE u.name = :name AND u.surname = :surname"),
    @NamedQuery(name = EntityUser.FIND_BY_SURNAME, query = "SELECT u FROM EntityUser u WHERE u.surname = :surname"),
    @NamedQuery(name = EntityUser.FIND_BY_EMAIL, query = "SELECT u FROM EntityUser u WHERE u.email = :email"),
    @NamedQuery(name = EntityUser.FIND_BY_PASSWORD, query = "SELECT u FROM EntityUser u WHERE u.password = :password")
})
public class EntityUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityUser.findAll";

    public static final String FIND_BY_ID = "EntityUser.findById";

    public static final String FIND_BY_NAME = "EntityUser.findByName";

    public static final String FIND_BY_SURNAME = "EntityUser.findBySurname";

    public static final String FIND_BY_NAME_AND_SURNAME = "EntityUser.findByNameAndSurname";

    public static final String FIND_BY_EMAIL = "EntityUser.findByEmail";

    public static final String FIND_BY_PASSWORD = "EntityUser.findByPassword";

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
    private Collection<EntityGroup> groupCollection = new ArrayList<EntityGroup>();

    @OneToMany(mappedBy = "userId1", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityFriendship> friendshipCollection1 = new ArrayList<EntityFriendship>();

    @OneToMany(mappedBy = "userId2", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityFriendship> friendshipCollection2 = new ArrayList<EntityFriendship>();

    @OneToMany(mappedBy = "userId", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityShelf> shelfCollection = new ArrayList<EntityShelf>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityBorrow> borrowCollection = new ArrayList<EntityBorrow>();

    
    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityPrint> printsCollection = new ArrayList<EntityPrint>();
   
    
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

    public Collection<EntityFriendship> getFriendshipCollection1() {
        return friendshipCollection1;
    }

    public void setFriendshipCollection1(Collection<EntityFriendship> friendshipCollection1) {
        this.friendshipCollection1 = friendshipCollection1;
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

    public Collection<EntityFriendship> getFriendshipCollection2() {
        return friendshipCollection2;
    }

    public void setFriendshipCollection2(Collection<EntityFriendship> friendshipCollection2) {
        this.friendshipCollection2 = friendshipCollection2;
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
