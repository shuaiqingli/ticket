package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public class CouponLine implements Serializable {

    private Integer id;
    private String vouchercode;
    private String vouchername;
    private String cid;
    private String cname;
    private String begindate;
    private String enddate;
    private BigDecimal lowlimit;
    private BigDecimal vprice;
    private String userid;
    private String username;
    private String makedate;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVouchercode() {
        return vouchercode;
    }

    public void setVouchercode(String vouchercode) {
        this.vouchercode = vouchercode;
    }

    public String getVouchername() {
        return vouchername;
    }

    public void setVouchername(String vouchername) {
        this.vouchername = vouchername;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public BigDecimal getLowlimit() {
        return lowlimit;
    }

    public void setLowlimit(BigDecimal lowlimit) {
        this.lowlimit = lowlimit;
    }

    public BigDecimal getVprice() {
        return vprice;
    }

    public void setVprice(BigDecimal vprice) {
        this.vprice = vprice;
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

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
