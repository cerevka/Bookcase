package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Entita výpůjčky.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityBorrow", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = EntityBorrow.FIND_ALL, query = "SELECT b FROM EntityBorrow b"),
    @NamedQuery(name = EntityBorrow.FIND_BY_ID, query = "SELECT b FROM EntityBorrow b WHERE b.id = :id")   
})
public class EntityBorrow implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "EntityBorrow.findAll";

    public static final String FIND_BY_ID = "EntityBorrow.findById";
    

    public static enum EnumBorrowStatus {

        RESERVED, REJECTED, BORROWED, RETURNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "reservationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date reservationDate;

    @Column(name = "rejectionDate")
    @Temporal(TemporalType.DATE)
    private Date rejectionDate;

    @Column(name = "limitDate")
    @Temporal(TemporalType.DATE)
    private Date limitDate;

    @Column(name = "returnDate")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Basic(optional = false)
    @Column(name = "borrowDate")
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityUser user;

    @JoinColumn(name = "printId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityPrint print;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumBorrowStatus status;

    public EntityBorrow() {
    }

    public EntityBorrow(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public EnumBorrowStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBorrowStatus status) {
        this.status = status;
    }

    public EntityPrint getPrint() {
        return print;
    }

    public void setPrint(EntityPrint print) {
        this.print = print;
    }

    public EntityUser getUser() {
        return user;
    }

    public void setUser(EntityUser user) {
        this.user = user;
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
        if (!(object instanceof EntityBorrow)) {
            return false;
        }
        EntityBorrow other = (EntityBorrow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityBorrow[id=" + id + "]";
    }
}
