package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Adam
 */
@Entity
@Table(name = "friendship", catalog = "bookcase", schema = "")
@NamedQueries({
    @NamedQuery(name = "Friendship.findAll", query = "SELECT f FROM Friendship f"),
    @NamedQuery(name = "Friendship.findByAuthorized", query = "SELECT f FROM Friendship f WHERE f.authorized = :authorized"),
    @NamedQuery(name = "Friendship.findByUser1id", query = "SELECT f FROM Friendship f WHERE f.friendshipPK.user1id = :user1id"),
    @NamedQuery(name = "Friendship.findByUser2id", query = "SELECT f FROM Friendship f WHERE f.friendshipPK.user2id = :user2id")})
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FriendshipPK friendshipPK;
    @Column(name = "authorized")
    private Integer authorized;
    @JoinColumn(name = "User_2_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "User_1_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;

    public Friendship() {
    }

    public Friendship(FriendshipPK friendshipPK) {
        this.friendshipPK = friendshipPK;
    }

    public Friendship(int user1id, int user2id) {
        this.friendshipPK = new FriendshipPK(user1id, user2id);
    }

    public FriendshipPK getFriendshipPK() {
        return friendshipPK;
    }

    public void setFriendshipPK(FriendshipPK friendshipPK) {
        this.friendshipPK = friendshipPK;
    }

    public Integer getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (friendshipPK != null ? friendshipPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friendship)) {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.friendshipPK == null && other.friendshipPK != null) || (this.friendshipPK != null && !this.friendshipPK.equals(other.friendshipPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Friendship[friendshipPK=" + friendshipPK + "]";
    }
}
