package edu.tsinghua.paratrooper.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_box")
public class TBoxEntity {
    private int id;
    private int locationX;
    private int locationY;
    private int apply;
    private int status;

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
}
