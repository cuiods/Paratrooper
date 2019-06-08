package edu.tsinghua.paratrooper.util.enums;

public enum MsgMethod {

    CONFIRM(4001),
    CAPTAIN(4006);

    private int code;

    MsgMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
