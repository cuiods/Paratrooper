package edu.tsinghua.paratrooper.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_msg")
public class TMsgEntity {
    private int id;
    private int receiveId;
    private int sendId;
    private int code;
    private String data;
    private int isRead;

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
    @Column(name = "receive_id")
    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    @Basic
    @Column(name = "send_id")
    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    @Basic
    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "is_read")
    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TMsgEntity that = (TMsgEntity) o;
        return id == that.id &&
                receiveId == that.receiveId &&
                sendId == that.sendId &&
                code == that.code &&
                Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiveId, sendId, code, data);
    }
}
