package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2016-01-21.
 */
public class ShowTime implements Serializable {
    private Integer id;
    private String begindate;
    private String enddate;
    private String showcontent;
    private String makeid;
    private String makename;
    private String makedate;
    private String lines;
    private List<Map> lineManageList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getShowcontent() {
        return showcontent;
    }

    public void setShowcontent(String showcontent) {
        this.showcontent = showcontent;
    }

    public String getMakeid() {
        return makeid;
    }

    public void setMakeid(String makeid) {
        this.makeid = makeid;
    }

    public String getMakename() {
        return makename;
    }

    public void setMakename(String makename) {
        this.makename = makename;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getLines() {
        return lines;
    }

    public void setLines(String lines) {
        this.lines = lines;
    }

    public List<Map> getLineManageList() {
        return lineManageList;
    }

    public void setLineManageList(List<Map> lineManageList) {
        this.lineManageList = lineManageList;
    }
}
