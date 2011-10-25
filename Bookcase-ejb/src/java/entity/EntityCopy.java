<<<<<<< HEAD
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entita svazku.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityCopy", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityCopy.FIND_ALL, query = "SELECT c FROM EntityCopy c"),
    @NamedQuery(name = EntityCopy.FIND_BY_ID, query = "SELECT c FROM EntityCopy c WHERE c.id = :id"),
    @NamedQuery(name = EntityCopy.FIND_BY_NOTE, query = "SELECT c FROM EntityCopy c WHERE c.note = :note"),
    @NamedQuery(name = EntityCopy.FIND_BY_PUBLISHED, query = "SELECT c FROM EntityCopy c WHERE c.published = :published"),    
    @NamedQuery(name = EntityCopy.FIND_BY_OWNER, query= "SELECT o.copy FROM EntityOwnership o WHERE o.user = :user")
})
public class EntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String FIND_ALL = "EntityCopy.findAll";
    
    public static final String FIND_BY_ID = "EntityCopy.findById";
    
    public static final String FIND_BY_NOTE = "EntityCopy.findByNote";
    
    public static final String FIND_BY_PUBLISHED = "EntityCopy.findByPublished";
    
    public static final String FIND_BY_OWNER = "EntityCopy.findByOwner";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "note", length = 1024)
    private String note;

    @Column(name = "published")
    @Temporal(TemporalType.DATE)
    private Date published;

    @JoinTable(name = "bookInShelf",
    joinColumns = {
        @JoinColumn(name = "copyId", referencedColumnName = "id", nullable = false)
    },
    inverseJoinColumns = {
        @JoinColumn(name = "shelfId", referencedColumnName = "id", nullable = false)
    })
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityShelf> shelfCollection = new ArrayList<EntityShelf>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "copyId")
    private Collection<EntityReservation> reservationCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "copyId")
    private Collection<EntityBorrow> borrowCollection;

    @JoinColumn(name = "bookId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityBook bookId;

    @OneToMany(mappedBy = "copy", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityOwnership> ownershipCollection = new ArrayList<EntityOwnership>();

    public EntityCopy() {
    }
    
    public EntityCopy(EntityBook book) {
        this.bookId = book;
    }

    public EntityCopy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Collection<EntityShelf> getShelfCollection() {
        return shelfCollection;
    }

    public void setShelfCollection(Collection<EntityShelf> shelfCollection) {
        this.shelfCollection = shelfCollection;
    }

    public Collection<EntityReservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<EntityReservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    public Collection<EntityBorrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<EntityBorrow> borrowCollection) {
        this.borrowCollection = borrowCollection;
    }

    public EntityBook getBookId() {
        return bookId;
    }

    public void setBookId(EntityBook bookId) {
        this.bookId = bookId;
    }

    public Collection<EntityOwnership> getOwnershipCollection() {
        return ownershipCollection;
    }

    public void setOwnershipCollection(Collection<EntityOwnership> ownershipCollection) {
        this.ownershipCollection = ownershipCollection;
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
        if (!(object instanceof EntityCopy)) {
            return false;
        }
        EntityCopy other = (EntityCopy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Copy[id=" + id + "]";
    }
}
=======
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entita svazku.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityCopy", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityCopy.FIND_ALL, query = "SELECT c FROM EntityCopy c"),
    @NamedQuery(name = EntityCopy.FIND_BY_ID, query = "SELECT c FROM EntityCopy c WHERE c.id = :id"),
    @NamedQuery(name = EntityCopy.FIND_BY_NOTE, query = "SELECT c FROM EntityCopy c WHERE c.note = :note"),
    @NamedQuery(name = EntityCopy.FIND_BY_PUBLISHED, query = "SELECT c FROM EntityCopy c WHERE c.published = :published"),    
    @NamedQuery(name = EntityCopy.FIND_BY_OWNER, query= "SELECT o.copy FROM EntityOwnership o WHERE o.user = :user")
})
public class EntityCopy implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityCopy.findAll";

    public static final String FIND_BY_ID = "EntityCopy.findById";

    public static final String FIND_BY_NOTE = "EntityCopy.findByNote";

    public static final String FIND_BY_PUBLISHED = "EntityCopy.findByPublished";
    
    public static final String FIND_BY_OWNER = "EntityCopy.findByOwner";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "note", length = 1024)
    private String note;

    @Column(name = "published")
    @Temporal(TemporalType.DATE)
    private Date published;

    @JoinTable(name = "bookInShelf",
    joinColumns = {
        @JoinColumn(name = "copyId", referencedColumnName = "id", nullable = false)
    },
    inverseJoinColumns = {
        @JoinColumn(name = "shelfId", referencedColumnName = "id", nullable = false)
    })
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityShelf> shelfCollection = new ArrayList<EntityShelf>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "copyId")
    private Collection<EntityReservation> reservationCollection = new ArrayList<EntityReservation>();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "copyId")
    private Collection<EntityBorrow> borrowCollection = new ArrayList<EntityBorrow>();

    @JoinColumn(name = "bookId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private EntityBook bookId;

    @OneToMany(mappedBy = "copy", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Collection<EntityOwnership> ownershipCollection = new ArrayList<EntityOwnership>();

    public EntityCopy() {
    }

    public EntityCopy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Collection<EntityShelf> getShelfCollection() {
        return shelfCollection;
    }

    public void setShelfCollection(Collection<EntityShelf> shelfCollection) {
        this.shelfCollection = shelfCollection;
    }

    public Collection<EntityReservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<EntityReservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    public Collection<EntityBorrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<EntityBorrow> borrowCollection) {
        this.borrowCollection = borrowCollection;
    }

    public EntityBook getBookId() {
        return bookId;
    }

    public void setBookId(EntityBook bookId) {
        this.bookId = bookId;
    }

    public Collection<EntityOwnership> getOwnershipCollection() {
        return ownershipCollection;
    }

    public void setOwnershipCollection(Collection<EntityOwnership> ownershipCollection) {
        this.ownershipCollection = ownershipCollection;
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
        if (!(object instanceof EntityCopy)) {
            return false;
        }
        EntityCopy other = (EntityCopy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Copy[id=" + id + "]";
    }
}
>>>>>>> 6bec427... Upraven datovy model, fix #28, pripraveny dekompozicni vztahy pro stav knihy #33.
