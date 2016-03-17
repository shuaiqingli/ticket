package com.hengyu.ticket.control;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.WXSubscribeMsg;
import com.hengyu.ticket.service.WXSubscribeMsgService;
import com.hengyu.ticket.util.DateHanlder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LGF on 2016/3/8 0008.
 */
@RequestMapping("/user")
@Controller
public class WXSubscribeMsgControl {

    @Autowired
    private WXSubscribeMsgService wxSubscribeMsgService;

    @ModelAttribute
    public void before(Model m, Integer id) throws Exception {
        if(id!=null){
            WXSubscribeMsg subscribeMsg = wxSubscribeMsgService.find(id);
            if(subscribeMsg!=null){
                m.addAttribute(subscribeMsg);
                m.addAttribute("msg",subscribeMsg);
            }
        }
    }


    //编辑或更新推送消息
    @RequestMapping("wxsubscribeEdit")
    @ResponseBody
    public String editWXSubscribeMsg(WXSubscribeMsg msg) throws Exception {
        int ret = -1;
        if(msg!=null&&msg.getId()!=null){
            ret = wxSubscribeMsgService.update(msg);
        }else if(msg!=null&&msg.getId()==null){
            msg.setMakedate(DateHanlder.getCurrentDateTime());
            ret = wxSubscribeMsgService.save(msg);
        }
        return String.valueOf(ret);
    }

    //编辑页面跳转
    @RequestMapping("wxsubscribeAdd")
    public String editWXSubscribeMsgPage(){
        return  "user/wxsubscribeEdit";
    }

    //消息列表
    @RequestMapping("wxsubscribeMsgList")
    public  String wxsubscribeMsgList(Model m,Page page,WXSubscribeMsg msg) throws Exception {
        page.setParam(msg);
        List<WXSubscribeMsg> list = wxSubscribeMsgService.findList(page);
        page.setData(list);
        m.addAttribute(page);
        return "user/wxsubscribeList";
    }

}


