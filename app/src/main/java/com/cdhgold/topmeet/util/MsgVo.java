package com.cdhgold.topmeet.util;


/*
message
 */
public class MsgVo {

    private String eml	        = "";
    private String message      = "";
    private String fromEml      = "";
    private String regdt        = "";
    private String nicknm        = "";

    public String getNicknm() {
        return nicknm;
    }

    public void setNicknm(String nicknm) {
        this.nicknm = nicknm;
    }

    public String getEml() {
        return eml;
    }

    public void setEml(String eml) {
        this.eml = eml;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromEml() {
        return fromEml;
    }

    public void setFromEml(String fromEml) {
        this.fromEml = fromEml;
    }

    public String getRegdt() {
        return regdt;
    }

    public void setRegdt(String regdt) {
        this.regdt = regdt;
    }
}
