package com.hengyu.ticket.api.app;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.API;
import com.hengyu.ticket.entity.Linkman;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.LinkmanService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;

@Controller
@RequestMapping(value = {"/app/api/linkman", "/web/api/linkman"})
public class AppLinkmanControl {

    @Autowired
    private LinkmanService ls;

    //编辑用户
    @RequestMapping("edit")
    public void edit(Linkman link, Writer out) throws Exception {
        if(StringUtils.isNotEmpty(link.getIdcode())){
            Assert.isTrue(link.getIdcode().length()==18,"身份证号码必须是18位！");
        }

        API api = new API();
        if (link != null) {
            if (StringUtils.isNotEmpty(link.getId())) {
                ls.update(link);
            } else {
                Linkman l = ls.findByMobile(link);
                if (l != null) {
                    api.setCode(5010);
                } else {
                    link.setId(DateHanlder.getCurrentDateTimeNumber());
                    link.setMakedate(DateHanlder.getCurrentDate());
                    ls.save(link);
                }
            }
        }
        APIUtil.toJSON(api, out);
    }

    //联系人列表
    @RequestMapping("list")
    public void list(Page page, Writer out, HttpServletRequest req) throws Exception {
        API api = new API();
        page.setParam(req.getAttribute(Const.LOGIN_CUSTOMER));
        List<Linkman> list = ls.findLinks(page);
        api.setDatas(list);
        APIUtil.toJSON(api, out);
    }

    //联系人详情
    @RequestMapping("detail")
    @SuppressWarnings("unchecked")
    public void detail(String id, Writer w) throws Exception {
        if (APIUtil.isNotNull(w, id)) {
            Linkman l = ls.find(id);
            API api = new API();
            api.getDatas().add(l);
            APIUtil.toJSON(api, w);
        }
    }

    //删除联系人
    @SuppressWarnings("unchecked")
    @RequestMapping("delete")
    public void delete(String id, Writer w) throws Exception {
        if (APIUtil.isNotNull(w, id)) {
            API api = new API();
            api.getDatas().add(ls.delete(id));
            APIUtil.toJSON(api, w);
        }
    }

}
