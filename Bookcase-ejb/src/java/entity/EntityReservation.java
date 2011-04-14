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
 * Entita rezervace.
 * @author Adam Činčura
 * @author Tomáš Čerevka
 */
@Entity
@Table(name = "entityReservation", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "EntityReservation.findAll", query = "SELECT r FROM EntityReservation r"),
    @NamedQuery(name = "EntityReservation.findByStatus", query = "SELECT r FROM EntityReservation r WHERE r.status = :status"),
    @NamedQuery(name = "EntityReservation.findByDateTime", query = "SELECT r FROM EntityReservation r WHERE r.dateTime = :dateTime"),
    @NamedQuery(name = "EntityReservation.findById", query = "SELECT r FROM EntityReservation r WHERE r.id = :id")})
public class EntityReservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "status", length = 255)
    private String status;
    @Basic(optional = false)
    @Column(name = "dateTime", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateTime;
    @JoinColumn(name = "copyId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityCopy copyId;
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private EntityUser userId;

    public EntityReservation() {
    }

    public EntityReservation(Integer id) {
        this.id = id;
    }

    public EntityReservation(Integer id, Date dateTime) {
        this.id = id;
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EntityCopy getCopyId() {
        return copyId;
    }

    public void setCopyId(EntityCopy copyId) {
        this.copyId = copyId;
    }

    public EntityUser getUserId() {
        return userId;
    }

    public void setUserId(EntityUser userId) {
        this.userId = userId;
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
        if (!(object instanceof EntityReservation)) {
            return false;
        }
        EntityReservation other = (EntityReservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntityReservation[id=" + id + "]";
    }
}
