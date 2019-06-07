package edu.tsinghua.paratrooper.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_soldier")
@PrimaryKeyJoinColumn(name = "id")
public class TSoldierEntity extends TUserEntity {
    private int id;
    private int locationX;
    private int locationY;
    private String publicKey;
    private int captain;
    private int groupNum;
    private int updateStatus;
    private int alive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "location_x")
    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    @Basic
    @Column(name = "location_y")
    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    @Basic
    @Column(name = "public_key")
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Basic
    @Column(name = "captain")
    public int getCaptain() {
        return captain;
    }

    public void setCaptain(int captain) {
        this.captain = captain;
    }

    @Basic
    @Column(name = "group_num")
    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    @Basic
    @Column(name = "update_status")
    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int update) {
        this.updateStatus = update;
    }

    @Basic
    @Column(name = "alive")
    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSoldierEntity that = (TSoldierEntity) o;
        return id == that.id &&
                locationX == that.locationX &&
                locationY == that.locationY &&
                Objects.equals(publicKey, that.publicKey) &&
                Objects.equals(captain, that.captain) &&
                Objects.equals(groupNum, that.groupNum) &&
                Objects.equals(updateStatus, that.updateStatus) &&
                Objects.equals(alive, that.alive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationX, locationY, publicKey, captain, groupNum, updateStatus, alive);
    }
}
