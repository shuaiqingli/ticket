package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Wang Wu on 2016-01-04.
 */
public class Feedback implements Serializable {

    private Integer latestRecordsForCustomer;
    private Integer latestRecordsForAdmin;
    private List<FeedbackDetail> feedbackDetailList;

    private Integer id;
    private Integer type;
    private String cid;
    private String cname;
    private String mobile;
    private String content;
    private Integer lastindexforcustomer;
    private Integer lastindexforadmin;
    private String remark;
    private Integer status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLastindexforcustomer() {
        return lastindexforcustomer;
    }

    public void setLastindexforcustomer(Integer lastindexforcustomer) {
        this.lastindexforcustomer = lastindexforcustomer;
    }

    public Integer getLastindexforadmin() {
        return lastindexforadmin;
    }

    public void setLastindexforadmin(Integer lastindexforadmin) {
        this.lastindexforadmin = lastindexforadmin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public Integer getLatestRecordsForCustomer() {
        return latestRecordsForCustomer;
    }

    public void setLatestRecordsForCustomer(Integer latestRecordsForCustomer) {
        this.latestRecordsForCustomer = latestRecordsForCustomer;
    }

    public Integer getLatestRecordsForAdmin() {
        return latestRecordsForAdmin;
    }

    public void setLatestRecordsForAdmin(Integer latestRecordsForAdmin) {
        this.latestRecordsForAdmin = latestRecordsForAdmin;
    }

    public List<FeedbackDetail> getFeedbackDetailList() {
        return feedbackDetailList;
    }

    public void setFeedbackDetailList(List<FeedbackDetail> feedbackDetailList) {
        this.feedbackDetailList = feedbackDetailList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
