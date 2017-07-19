package edu.tsinghua.dmcs;

/**
 * Created by chenning on 17/7/19.
 */
public class DmcsException extends Exception {



    public int code;

    public String msg;

    public DmcsException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
