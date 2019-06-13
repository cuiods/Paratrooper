package edu.tsinghua.paratrooper.data.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_box_apply")
@NoArgsConstructor
@AllArgsConstructor
public class TBoxApplyEntity {
    private int id;
    private int boxId;
    private int userId;
    private String key;

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
    @Column(name = "box_id")
    public int getBoxId() {
        return boxId;
    }

    public void setBoxId(int boxId) {
        this.boxId = boxId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "box_key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TBoxApplyEntity that = (TBoxApplyEntity) o;
        return id == that.id &&
                boxId == that.boxId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, boxId, userId);
    }
}
