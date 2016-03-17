package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.IntegralRule;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.IntegralRuleService;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/user")
public class IntegralRuleControl {

    @Autowired
    private IntegralRuleService integralRuleService;

    @ResponseBody
    @RequestMapping("integralRuleDel.do")
    public String integralRuleDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        integralRuleService.delIntegralRule(id);
        return "1";
    }

    @RequestMapping("integralRuleList.do")
    public String integralRuleList(String keyword, Page page, Model m) throws Exception {
        m.addAttribute("integralRuleList", integralRuleService.findIntegralRuleList(keyword, page));
        m.addAttribute("page", page);
        return "user/integralRuleList";
    }

    @RequestMapping("integralRuleEditPage.do")
    public String integralRuleEditPage(Integer id, Model m) throws Exception {
        if (null != id) {
            m.addAttribute("integralRule", integralRuleService.findIntegralRule(id));
        }
        return "user/integralRuleEdit";
    }

    @ResponseBody
    @RequestMapping("integralRuleEdit.do")
    public String integralRuleEdit(IntegralRule integralRule, HttpServletRequest request) {
        Date beginDate = DateUtil.stringToDate(integralRule.getBegindate());
        Date endDate = DateUtil.stringToDate(integralRule.getEnddate());
        Assert.isTrue(beginDate != null, "无效开始时间");
        Assert.isTrue(endDate != null && !endDate.before(beginDate), "无效结束时间");
        integralRule.setBegindate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getFirstDate(beginDate)));
        integralRule.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLatestDate(endDate)));
        Assert.isTrue(integralRule.getWeekdays() > 0 && integralRule.getWeekdays() < 128, "无效可选星期");
        Assert.isTrue(integralRule.getMultiple() > 0, "无效积分倍数");
        if (null == integralRule.getId()) {
            Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
            integralRule.setMakeid(admin.getUserID());
            integralRule.setMakename(admin.getRealName());
            integralRule.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            integralRuleService.saveIntegralRule(integralRule);
        } else {
            integralRuleService.updateIntegralRule(integralRule);
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping("bindLineListToIntegralRule.do")
    public String bindLineListToIntegralRule(Integer id, String lmidList) {
        Assert.notNull(id, "积分规则不能为空");
        Assert.hasText(lmidList, "线路不能为空");
        List<Integer> parsedLmidList = new ArrayList<Integer>();
        for (String lmid : lmidList.split(",")) {
            parsedLmidList.add(Integer.parseInt(lmid));
        }
        integralRuleService.updateForBindLineListToIntegralRule(parsedLmidList, id);
        return "1";
    }

    @ResponseBody
    @RequestMapping("unbindLineToIntegralRule.do")
    public String unbindLineToIntegralRule(Integer lmid, Integer id) {
        Assert.notNull(id, "积分规则不能为空");
        Assert.notNull(lmid, "线路不能为空");
        integralRuleService.updateForUnbindLineToIntegralRule(lmid, id);
        return "1";
    }
}
