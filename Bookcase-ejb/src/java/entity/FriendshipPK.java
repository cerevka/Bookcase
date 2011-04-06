package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Adam
 */
@Embeddable
public class FriendshipPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "User_1_id", nullable = false)
    private int user1id;
    @Basic(optional = false)
    @Column(name = "User_2_id", nullable = false)
    private int user2id;

    public FriendshipPK() {
    }

    public FriendshipPK(int user1id, int user2id) {
        this.user1id = user1id;
        this.user2id = user2id;
    }

    public int getUser1id() {
        return user1id;
    }

    public void setUser1id(int user1id) {
        this.user1id = user1id;
    }

    public int getUser2id() {
        return user2id;
    }

    public void setUser2id(int user2id) {
        this.user2id = user2id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) user1id;
        hash += (int) user2id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendshipPK)) {
            return false;
        }
        FriendshipPK other = (FriendshipPK) object;
        if (this.user1id != other.user1id) {
            return false;
        }
        if (this.user2id != other.user2id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.FriendshipPK[user1id=" + user1id + ", user2id=" + user2id + "]";
    }
}
