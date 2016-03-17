package com.hengyu.ticket.entity;

import java.io.Serializable;

/**
 * Created by Wang Wu on 2016-01-19.
 */
public class LockTicketLog implements Serializable {
    private Integer id;
    private Integer type;
    private String userid;
    private String username;
    private Integer lmid;
    private String linename;
    private String shiftnum;
    private String ridedate;
    private Integer quantity;
    private String seats;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLmid() {
        return lmid;
    }

    public void setLmid(Integer lmid) {
        this.lmid = lmid;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getShiftnum() {
        return shiftnum;
    }

    public void setShiftnum(String shiftnum) {
        this.shiftnum = shiftnum;
    }

    public String getRidedate() {
        return ridedate;
    }

    public void setRidedate(String ridedate) {
        this.ridedate = ridedate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }
}
