package com.hengyu.ticket.entity;

import com.hengyu.ticket.util.APIStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口工具类
 *
 * @param <T>
 * @author LGF
 *         2015-10-17
 */
@SuppressWarnings("all")
public class API {

    //分页工具
    private Page page;
    //消息
    private Object msg;
    //用户标记
    private String token;
    //状态码
    private Integer code = 200;
    //数据集合
    private List datas = new ArrayList(1);


    public Page getPage() {
        if (page != null) {
            page.setAdmin(null);
            page.setData(null);
            page.setParam(null);
        }
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Object getMsg() {
        if (code != null && msg == null) {
            msg = APIStatus.codes.get(code);
        }
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }
}
