package com.hengyu.ticket.entity;

import java.io.Serializable;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public class IntegralLine implements Serializable {

    private Integer id;
    private String operatorid;
    private String productname;
    private Integer amount;
    private Integer count;
    private Integer integral;
    private Integer type;
    private String customerid;
    private String customername;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperatorid() {
        return operatorid;
    }

    public void setOperatorid(String operatorid) {
        this.operatorid = operatorid;
    }
}
