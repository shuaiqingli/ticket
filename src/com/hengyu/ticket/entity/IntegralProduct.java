package com.hengyu.ticket.entity;

import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public class IntegralProduct implements Serializable{

    private Integer id;
    private String name;
    private Integer amount;
    private Integer type;
    private Integer validday;
    private BigDecimal lowlimit;
    private BigDecimal vprice;
    private String iconurl;
    private String mainurl;
    private Integer stockflag;
    private Integer fixedstock;
    private Integer currentstock;
    private String startdate;
    private String enddate;
    private Integer status;
    private String desc;
    private String makedate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getMainurl() {
        return mainurl;
    }

    public void setMainurl(String mainurl) {
        this.mainurl = mainurl;
    }

    public Integer getStockflag() {
        return stockflag;
    }

    public void setStockflag(Integer stockflag) {
        this.stockflag = stockflag;
    }

    public Integer getFixedstock() {
        return fixedstock;
    }

    public void setFixedstock(Integer fixedstock) {
        this.fixedstock = fixedstock;
    }

    public Integer getCurrentstock() {
        return currentstock;
    }

    public void setCurrentstock(Integer currentstock) {
        this.currentstock = currentstock;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEscapeDesc(){
        return HtmlUtils.htmlEscape(desc);
    }

    public BigDecimal getVprice() {
        return vprice;
    }

    public void setVprice(BigDecimal vprice) {
        this.vprice = vprice;
    }

    public BigDecimal getLowlimit() {
        return lowlimit;
    }

    public void setLowlimit(BigDecimal lowlimit) {
        this.lowlimit = lowlimit;
    }

    public Integer getValidday() {
        return validday;
    }

    public void setValidday(Integer validday) {
        this.validday = validday;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
