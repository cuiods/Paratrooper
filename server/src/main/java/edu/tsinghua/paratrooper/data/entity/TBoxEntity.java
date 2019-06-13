package edu.tsinghua.paratrooper.data.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_box")
@NoArgsConstructor
@AllArgsConstructor
public class TBoxEntity {
    private int id;
    private int locationX;
    private int locationY;
    private int apply;
    private int total;
    private int status;
    private List<TSoldierEntity> soldierEntities;

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
    @Column(name = "apply")
    public int getApply() {
        return apply;
    }

    public void setApply(int apply) {
        this.apply = apply;
    }

    @Basic
    @Column(name = "total")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TBoxEntity that = (TBoxEntity) o;
        return id == that.id &&
                locationX == that.locationX &&
                locationY == that.locationY &&
                Objects.equals(apply, that.apply) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locationX, locationY, apply, status);
    }

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "t_box_apply",
            joinColumns = {@JoinColumn(name = "box_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    public List<TSoldierEntity> getSoldierEntities() {
        return soldierEntities;
    }

    public void setSoldierEntities(List<TSoldierEntity> soldierEntities) {
        this.soldierEntities = soldierEntities;
    }
}
