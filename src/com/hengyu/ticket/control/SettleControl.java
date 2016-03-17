package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.AdminService;
import com.hengyu.ticket.service.SettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/user")
public class SettleControl {

    @Autowired
    private SettleService settleService;
    @Autowired
    private AdminService adminService;

    @RequestMapping("adminSettleList.do")
    public String adminSettleList(String keyword, Page page, Model m) {
        m.addAttribute("dataList", settleService.findAdminSettleList(keyword, page));
        m.addAttribute("page", page);
        return "user/adminSettleList";
    }

    @RequestMapping("adminSettleDetailList.do")
    public String adminSettleDetailList(String userid, Page page, Model m) throws Exception {
        Assert.hasText(userid, "ID不能为空");
        m.addAttribute("admin", adminService.find(userid));
        m.addAttribute("dataList", settleService.findAdminSettleDetailList(userid, page));
        m.addAttribute("page", page);
        return "user/adminSettleDetailList";
    }

    @RequestMapping("adminSettleLogList.do")
    public String adminSettleLogList(String keyword, Page page, Model m) throws Exception {
        m.addAttribute("adminSettleLogList", settleService.findAdminSettleLogList(keyword, page));
        m.addAttribute("page", page);
        return "user/adminSettleLogList";
    }

    @ResponseBody
    @RequestMapping("adminSettle.do")
    public String adminSettle(String userid, BigDecimal amount, HttpServletRequest request) {
        Assert.hasText(userid, "ID不能为空");
        Assert.isTrue(amount.compareTo(new BigDecimal(0)) > 0, "无效金额");
        settleService.updateForAdminSettle(userid, amount, (Admin)request.getSession().getAttribute(Const.LOGIN_USER));
        return "1";
    }
}
