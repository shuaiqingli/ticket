package com.hengyu.ticket.entity;

import org.springframework.web.util.HtmlUtils;

import java.io.Serializable;

/**
 * Created by Wang Wu on 2016-01-26.
 */
public class ActivityMsg implements Serializable {
    private Integer id;
    private Integer amsort;
    private String amtitle;
    private String amcontent;
    private String ampictureurl;
    private String begindate;
    private String enddate;
    private String makeid;
    private String makename;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmsort() {
        return amsort;
    }

    public void setAmsort(Integer amsort) {
        this.amsort = amsort;
    }

    public String getAmtitle() {
        return amtitle;
    }

    public void setAmtitle(String amtitle) {
        this.amtitle = amtitle;
    }

    public String getAmcontent() {
        return amcontent;
    }

    public void setAmcontent(String amcontent) {
        this.amcontent = amcontent;
    }

    public String getAmpictureurl() {
        return ampictureurl;
    }

    public void setAmpictureurl(String ampictureurl) {
        this.ampictureurl = ampictureurl;
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

    public String getEscapeAmcontent(){
        return HtmlUtils.htmlEscape(amcontent);
    }
}
