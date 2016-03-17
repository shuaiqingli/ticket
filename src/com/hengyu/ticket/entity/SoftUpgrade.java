package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2015-12-02
 */
public class SoftUpgrade {

    //------------------字段-----------------

    private Integer id;
    private String appname;
    private Integer versioncode;
    private String fileurl;
    private String softdesc;
    private String makedate;

    //0 站务app 1 android客户端APP
    private Integer sort;

    //----------------构造方法----------------

    public SoftUpgrade() {

    }

    //-------------- get/set方法 --------------

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Integer getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(Integer versioncode) {
        this.versioncode = versioncode;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getSoftdesc() {
        return softdesc;
    }

    public void setSoftdesc(String softdesc) {
        this.softdesc = softdesc;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }
}