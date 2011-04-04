/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

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
 * @author Adam
 */
@Entity
@Table(name = "borrow", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "Borrow.findAll", query = "SELECT b FROM Borrow b"),
    @NamedQuery(name = "Borrow.findByLimitDate", query = "SELECT b FROM Borrow b WHERE b.limitDate = :limitDate"),
    @NamedQuery(name = "Borrow.findByDo1", query = "SELECT b FROM Borrow b WHERE b.do1 = :do1"),
    @NamedQuery(name = "Borrow.findByOd", query = "SELECT b FROM Borrow b WHERE b.od = :od"),
    @NamedQuery(name = "Borrow.findById", query = "SELECT b FROM Borrow b WHERE b.id = :id")})
public class Borrow implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "limit_date")
    @Temporal(TemporalType.DATE)
    private Date limitDate;
    @Column(name = "do")
    @Temporal(TemporalType.DATE)
    private Date do1;
    @Basic(optional = false)
    @Column(name = "od", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date od;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JoinColumn(name = "Copy_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Copy copyid;
    @JoinColumn(name = "User_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private User userid;

    public Borrow() {
    }

    public Borrow(Integer id) {
        this.id = id;
    }

    public Borrow(Integer id, Date od) {
        this.id = id;
        this.od = od;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public Date getDo1() {
        return do1;
    }

    public void setDo1(Date do1) {
        this.do1 = do1;
    }

    public Date getOd() {
        return od;
    }

    public void setOd(Date od) {
        this.od = od;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Copy getCopyid() {
        return copyid;
    }

    public void setCopyid(Copy copyid) {
        this.copyid = copyid;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
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
        return "model.Borrow[id=" + id + "]";
    }

}
