package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.ActivityMsg;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.ActivityMsgService;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/user")
public class ActivityMsgControl {

    @Autowired
    private ActivityMsgService activityMsgService;

    @RequestMapping("activityMsgList.do")
    public String activityMsgList(Page page, String keyword, Integer amsort, Model m) {
        m.addAttribute("activityMsgList", activityMsgService.findActivityMsgList(keyword, amsort, page));
        m.addAttribute("page", page);
        return "user/activityMsgList";
    }

    @RequestMapping("activityMsgEditPage.do")
    public String activityMsgEditPage(Integer id, Model m) {
        if (null != id) {
            m.addAttribute("activityMsg", activityMsgService.findActivityMsg(id));
        }
        return "user/activityMsgEdit";
    }

    @ResponseBody
    @RequestMapping("activityMsgEdit.do")
    public String activityMsgEdit(ActivityMsg activityMsg, HttpServletRequest request) {
        Assert.isTrue(CollectionUtil.contain(new Integer[]{0, 1, 2}, activityMsg.getAmsort()), "无效类型");
        Assert.hasText(activityMsg.getAmtitle(), "标题不能为空");
        Assert.isTrue(activityMsg.getAmsort() == 1 || activityMsg.getAmsort() == 2 || StringUtils.isNotBlank(activityMsg.getAmpictureurl()), "图片不能为空");
        Assert.isTrue(activityMsg.getAmsort() == 1 || activityMsg.getAmsort() == 2 || StringUtils.isNotBlank(activityMsg.getAmcontent()), "内容不能为空");
        Date beginDate = DateUtil.stringToDate(activityMsg.getBegindate());
        Date endDate = DateUtil.stringToDate(activityMsg.getEnddate());
        Assert.isTrue(beginDate != null, "无效生效时间");
        Assert.isTrue(endDate != null && !endDate.before(beginDate), "无效失效时间");
        activityMsg.setBegindate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getFirstDate(beginDate)));
        activityMsg.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLatestDate(endDate)));
        if (null != activityMsg.getId()) {
            activityMsgService.updateActivityMsg(activityMsg);
        } else {
            Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
            activityMsg.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            activityMsg.setMakeid(admin.getUserID());
            activityMsg.setMakename(admin.getRealName());
            activityMsgService.addActivityMsg(activityMsg);
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping("activityMsgDel.do")
    public String activityMsgDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        activityMsgService.delActivityMsg(id);
        return "1";
    }
}
