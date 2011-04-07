package entity;

import java.io.Serializable;
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
 *
 * @author Adam Činčura
 */
@Entity
@Table(name = "copy", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "Copy.findAll", query = "SELECT c FROM Copy c"),
    @NamedQuery(name = "Copy.findById", query = "SELECT c FROM Copy c WHERE c.id = :id"),
    @NamedQuery(name = "Copy.findByNote", query = "SELECT c FROM Copy c WHERE c.note = :note"),
    @NamedQuery(name = "Copy.findByPublished", query = "SELECT c FROM Copy c WHERE c.published = :published")})
public class Copy implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @JoinTable(name = "bookInShelf", joinColumns = {
        @JoinColumn(name = "shelfId", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "copyId", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private Collection<Shelf> shelfCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "copyId")
    private Collection<Reservation> reservationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "copyId")
    private Collection<Borrow> borrowCollection;
    @JoinColumn(name = "bookId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Book bookId;

    public Copy() {
    }

    public Copy(Integer id) {
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

    public Collection<Shelf> getShelfCollection() {
        return shelfCollection;
    }

    public void setShelfCollection(Collection<Shelf> shelfCollection) {
        this.shelfCollection = shelfCollection;
    }

    public Collection<Reservation> getReservationCollection() {
        return reservationCollection;
    }

    public void setReservationCollection(Collection<Reservation> reservationCollection) {
        this.reservationCollection = reservationCollection;
    }

    public Collection<Borrow> getBorrowCollection() {
        return borrowCollection;
    }

    public void setBorrowCollection(Collection<Borrow> borrowCollection) {
        this.borrowCollection = borrowCollection;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
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
        if (!(object instanceof Copy)) {
            return false;
        }
        Copy other = (Copy) object;
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
