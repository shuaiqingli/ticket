package com.hengyu.ticket.control;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Driver;
import com.hengyu.ticket.entity.DriverHoliday;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.CompanyService;
import com.hengyu.ticket.service.DriverHolidayService;
import com.hengyu.ticket.service.DriverService;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.SecurityHanlder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 司机管理
 *
 * @author LGF
 */
@Controller
@RequestMapping("/user")
public class DriverControl {

    @Autowired
    private DriverService driverService;
    @Autowired
    private DriverHolidayService driverHolidayService;
    @Autowired
    private CompanyService companyService;

    //司机列表
    @RequestMapping(value = "drivermanage.do")
    public String driverPage(Page page, Driver driver, Model m, HttpServletRequest req) throws Exception {
        page.setParam(driver);
        page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
        List<Driver> driverList = driverService.findlist(page);
        page.setData(driverList);
        m.addAttribute(page);
        if(null != driverList && driverList.size() > 0){
            List<String> driveridList = new ArrayList<String>();
            for(Driver d : driverList){
                driveridList.add(d.getId());
            }
            m.addAttribute("lineDataList", driverService.findLineDataForDriverList(driveridList));
        }
        return "user/driver";
    }

    //编辑页面
    @RequestMapping("driveradd.do")
    public String editDriverPage(Driver driver, Model m) throws Exception {
        if (driver != null && StringUtils.isNotEmpty(driver.getId())) {
            Driver d = driverService.find(driver.getId());
            m.addAttribute(d);
        }
        return "user/driveredit";
    }

    //保存或更新
    @RequestMapping("editdriver.do")
    @ResponseBody
    public String editDriver(Driver driver,Integer issave, HttpServletRequest request) throws Exception {
        if (driver == null || !StringUtils.isNotEmpty(driver.getIdcard())) {
            return String.valueOf(Const.ERROR_CODE);
        }
        int result = -1;
        if (driver != null) {
            Admin admin= (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
            Assert.notNull(admin.getTcid(),"当前登录用户没有绑定公司，请绑定公司后操作！");

            driver.setTcid(admin.getTcid());
            driver.setCompanyname(companyService.find(admin.getTcid()).getCompanyname());

            if (StringUtils.isNotEmpty(driver.getDname())) {
                driver.setPyname(PinyinHelper.getShortPinyin(driver.getDname())+ PinyinHelper.convertToPinyinString(driver.getDname(), "", PinyinFormat.WITHOUT_TONE));
            }
            if (StringUtils.isNotEmpty(driver.getId())&&0==issave) {
                result = driverService.update(driver);
            } else {
                driver.setMakedate(DateHanlder.getCurrentDateTime());
                result = driverService.save(driver);
            }
        }
        return String.valueOf(result);
    }

    //删除
    @RequestMapping("deldriver.do")
    @ResponseBody
    public String delete(String id) throws Exception {
        Integer result = -1;
        if (StringUtils.isNotEmpty(id)) {
            result = driverService.delete(id);
        }
        return String.valueOf(result);
    }

    // 修改密码
    @ResponseBody
    @RequestMapping(value = "updateDriverPassword.do", method = RequestMethod.POST)
    public String updateDriverPassword(String driverid, String password) throws Exception {
        Assert.hasText(driverid, "ID不能为空");
        Assert.hasText(password, "密码不能为空");
        driverService.updatePassword(driverid, SecurityHanlder.md5(password));
        return "1";
    }

    //假期管理页
    @RequestMapping(value = "driverHolidayList.do")
    public String driverHolidayList(Page page, String driverid, Model m) {
        Assert.hasText("ID不能为空");
        Driver driver = driverService.find(driverid);
        Assert.notNull(driver, "无效司机");
        page.setParam(driverid);
        page.setData(driverHolidayService.findList(page));
        m.addAttribute(page);
        m.addAttribute(driver);
        return "user/driverHolidayList";
    }

    //新增或编辑假期页
    @RequestMapping("driverHoliday.do")
    public String driverHoliday(Integer id, String driverid, Model m) {
        Assert.hasText(driverid, "司机不能为空");
        Driver driver = driverService.find(driverid);
        Assert.notNull(driver, "无效司机");
        m.addAttribute(driver);
        if (null != id) {
            DriverHoliday driverHoliday = driverHolidayService.find(id);
            Assert.isTrue(driverHoliday != null && driverid.equals(driverHoliday.getDriverid()), "无效假期");
            m.addAttribute(driverHoliday);
        }
        return "user/driverHolidayEdit";
    }

    //新增或编辑假期
    @ResponseBody
    @RequestMapping("driverHolidayAddOrEdit.do")
    public String driverHolidayAddOrEdit(DriverHoliday driverHoliday) {
        Assert.notNull(driverHoliday, "假期信息不能为空");
        Assert.hasText(driverHoliday.getDriverid(), "司机不能为空");
        Assert.isTrue(CollectionUtil.contain(new String[]{"1", "2"}, driverHoliday.getType()), "无效类型");
        if ("1".equals(driverHoliday.getType())) {
            Assert.isTrue(driverHoliday.getWeekday() > 0 && driverHoliday.getWeekday() < 128, "无效可选星期");
            driverHoliday.setStartdate("");
            driverHoliday.setEnddate("");
        }
        if ("2".equals(driverHoliday.getType())) {
            Date startDate = DateUtil.stringToDate(driverHoliday.getStartdate());
            Date endDate = DateUtil.stringToDate(driverHoliday.getEnddate());
            Assert.isTrue(startDate != null, "无效开始时间");
            Assert.isTrue(endDate != null && !endDate.before(startDate), "无效结束时间");
            driverHoliday.setStartdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getFirstDate(startDate)));
            driverHoliday.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLatestDate(endDate)));
            driverHoliday.setWeekday(0);
        }
        driverHoliday.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if (driverHoliday.getId() != null) {
            driverHolidayService.update(driverHoliday);
        } else {
            driverHolidayService.save(driverHoliday);
        }
        return "1";
    }

    //删除假期
    @ResponseBody
    @RequestMapping("driverHolidayDel.do")
    public String driverHolidayDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        driverHolidayService.delete(id);
        return "1";
    }

    //司机选取页
    @RequestMapping(value = "selectDriver.do")
    public String selectDriver(Page page, String groupid, String type, String keyword, Model m) {
        Assert.hasText(groupid, "线路不能为空");
        Assert.isTrue(CollectionUtil.contain(new String[]{"1","2"}, type), "无效类型");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("groupid", groupid);
        params.put("type", type);
        params.put("keyword", keyword);
        page.setParam(params);
        page.setData(driverService.findListForBindLine(page));
        m.addAttribute(page);
        return "user/selectDriver";
    }

}
