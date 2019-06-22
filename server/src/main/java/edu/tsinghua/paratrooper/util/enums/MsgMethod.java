package edu.tsinghua.paratrooper.util.enums;

public enum MsgMethod {

    CONFIRM(4001),
    CAPTAIN(4006),
    VOTE_CAPTAIN(4007),
    BOXOPEN(5001),
    NOTIFY_AUTH(6001),
    NOTIFY_CAPTAIN(6002);

    private int code;

    MsgMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
