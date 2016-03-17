package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Wang Wu on 2016-01-20.
 */
public class AdminSettleLog implements Serializable {
    private Integer id;
    private String userid;
    private String username;
    private String saleorders;
    private Integer quantitytotal;
    private BigDecimal pricetotal;
    private String operatorid;
    private String operatorname;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSaleorders() {
        return saleorders;
    }

    public void setSaleorders(String saleorders) {
        this.saleorders = saleorders;
    }

    public Integer getQuantitytotal() {
        return quantitytotal;
    }

    public void setQuantitytotal(Integer quantitytotal) {
        this.quantitytotal = quantitytotal;
    }

    public BigDecimal getPricetotal() {
        return pricetotal;
    }

    public void setPricetotal(BigDecimal pricetotal) {
        this.pricetotal = pricetotal;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }
}
