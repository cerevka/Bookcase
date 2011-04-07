package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Adam Činčura
 */
@Entity
@Table(name = "borrow", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "Borrow.findAll", query = "SELECT b FROM Borrow b"),
    @NamedQuery(name = "Borrow.findByLimitDate", query = "SELECT b FROM Borrow b WHERE b.limitDate = :limitDate"),
    @NamedQuery(name = "Borrow.findByToDate", query = "SELECT b FROM Borrow b WHERE b.toDate = :toDate"),
    @NamedQuery(name = "Borrow.findByFromDate", query = "SELECT b FROM Borrow b WHERE b.fromDate = :fromDate"),
    @NamedQuery(name = "Borrow.findById", query = "SELECT b FROM Borrow b WHERE b.id = :id")})
public class Borrow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "limitDate")
    @Temporal(TemporalType.DATE)
    private Date limitDate;
    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Basic(optional = false)
    @Column(name = "fromDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @JoinColumn(name = "copyId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Copy copyId;
    @JoinColumn(name = "personId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;

    public Borrow() {
    }

    public Borrow(Integer id) {
        this.id = id;
    }

    public Borrow(Integer id, Date fromDate) {
        this.id = id;
        this.fromDate = fromDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Copy getCopyId() {
        return copyId;
    }

    public void setCopyId(Copy copyId) {
        this.copyId = copyId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
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
        if (!(object instanceof Borrow)) {
            return false;
        }
        Borrow other = (Borrow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Borrow[id=" + id + "]";
    }
}
