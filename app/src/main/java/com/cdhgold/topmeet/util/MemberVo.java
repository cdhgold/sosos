package com.cdhgold.topmeet.util;


import java.util.ArrayList;

public class MemberVo {

    private int seq   		    = 0;
    private String gender	    = "";
    private String age          = "";
    private String nickname     = "";
    private String info         = "";
    private String deviceid      = "";
    private String totItem       = ""; // 총합계

    private ArrayList<ProdVo> list = null; // item 정보

    public String getTotItem() {
        return totItem;
    }

    public void setTotItem(String totItem) {
        this.totItem = totItem;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public ArrayList<ProdVo> getList() {
        return list;
    }

    public void setList(ArrayList<ProdVo> list) {
        this.list = list;
    }
}
