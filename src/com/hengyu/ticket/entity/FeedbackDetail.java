package com.hengyu.ticket.entity;

import java.io.Serializable;

/**
 * Created by Wang Wu on 2016-01-04.
 */
public class FeedbackDetail implements Serializable {
    private Integer id;
    private Integer feedbackid;
    private Integer type;
    private String content;
    private String replyid;
    private String replyname;
    private Integer index;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeedbackid() {
        return feedbackid;
    }

    public void setFeedbackid(Integer feedbackid) {
        this.feedbackid = feedbackid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyid() {
        return replyid;
    }

    public void setReplyid(String replyid) {
        this.replyid = replyid;
    }

    public String getReplyname() {
        return replyname;
    }

    public void setReplyname(String replyname) {
        this.replyname = replyname;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }
}
