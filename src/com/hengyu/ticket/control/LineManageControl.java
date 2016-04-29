package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.service.*;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.JSONHanlder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线路管理
 *
 * @author LGF
 */
@Controller
@RequestMapping("/user")
public class LineManageControl {

    //线路管理
    @Autowired
    private LineManageService lms;
    //城市
    @Autowired
    private CityStationService css;
    //途经站点
    @Autowired
    private LineManageStationService lmsService;
    @Autowired
    private BaseResourceService bresService;
    @Autowired
    private TripPriceListService tpls;
    @Autowired
    private ShiftStartService sss;
    @Autowired
    private DriverService driverService;
    @Autowired
    private PlateService plateService;
    @Autowired
    private StationTimeRuleService stationTimeRuleService;
    @Autowired
    private LineSchedueService lineSchedueService;
    @Autowired
    private PromotionService promotionService;


    @ModelAttribute
    public void before(LineManage lm, Model m) throws Exception {
        if (lm != null && lm.getId() != null) {
            LineManage lineManage = lms.find(lm.getId());
          if (lineManage != null) {
//                int isallowupdate = tpls.findisAllowUpdateLineManage(lm.getId());
//                lineManage.setIsallowupdate(isallowupdate);
                m.addAttribute("lm", lineManage);
                m.addAttribute(lineManage);
            }else{
            	throw new BaseException();
            }
        }
    }

    //线路列表
    @RequestMapping(value = {"linemanage.do"})
    public String lineManage(Page page, LineManage lm, Model m, HttpServletRequest req) throws Exception {
        if (lm != null) {
            m.addAttribute(lm);
            page.setParam(lm);
        }
        page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
        m.addAttribute(lms.findList(page));
        return "user/linemanage";
    }

    //编辑页面
    @RequestMapping(value = {"lineadd.do"})
    public String lineManageEditPage(Model m, LineManage lm) throws Exception {
        List<CityStation> citys = css.findAllCity();
        Page page = new Page();
        page.setPageSize(Integer.MAX_VALUE);
        BaseResource bs = new BaseResource();
        page.setParam(bs);
        bs.setTagname(Const.BASE_RESOURCE_CARMODEL);
        List<BaseResource> bslist = bresService.findlist(page);
        if (citys != null) {
            m.addAttribute("citys", citys);
        }
        if (bslist != null) {
            m.addAttribute("cars", bslist);
        }
        LineManage lineManage = (LineManage) m.asMap().get("lineManage");
        if (null != lineManage && StringUtils.isNotBlank(lineManage.getGroupid())) {
            m.addAttribute("driverList", driverService.findListByLineGroupID(lineManage.getGroupid()));
            m.addAttribute("plateList", plateService.findListByLineGroupID(lineManage.getGroupid()));
        }

        List<StationTimeRule> rules = stationTimeRuleService.findByLMID(lm.getId());
        if(rules!=null){
            m.addAttribute("rules",rules);
        }

        List<LineSchedue> scheduleList = lineSchedueService.findList(lm.getId());
        if(scheduleList!=null){
            m.addAttribute("schedules",scheduleList);
        }

        List<TripPriceList> tplRule = tpls.findByLMID(lm.getId());
        if(tplRule!=null){
            m.addAttribute("tplRule",tplRule);
        }

        List<Promotion> promotions = promotionService.findByLMID(lm.getId());
        if(promotions!=null){
            m.addAttribute("promotions",promotions);
        }

        return "user/lineEdit";
    }

    /**
     * 价格模板列表
     *
     * @param m
     * @param lineId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"linepricedetail.do"})
    public String linePriceEditPage(Model m, String lineId) throws Exception {
        LineManage lineManage = lms.findLinePriceByLineId(lineId);
        if (lineManage != null) {
            m.addAttribute(lineManage);
        }
        return "user/lineprice";
    }


    @RequestMapping(value = {"editlineprice.do"})
    @ResponseBody
    public String linePriceEdit(Model m, LineManage lineManage) throws Exception {
        int result = -1;
        if (lineManage.getLinePrices() != null) {
            result = lms.updateLinePrice(lineManage);
        }
        return String.valueOf(result);
    }

    //按线路id查询所有途经站点
    @RequestMapping(value = {"linestations.do"}, produces = Const.MVC_PRODUCES)
    @ResponseBody
    public String lineManageStations(Integer id) throws Exception {
        String resutl = "{}";
        if (id != null) {
            List<LineManageStation> list = lmsService.findBylMID(id);
            resutl = JSONHanlder.getObjectAsString(list);
        }
        return resutl;
    }

    //添加或更新
    @RequestMapping(value = {"lineEdit.do"})
    @ResponseBody
    public String lineManageEdit(LineManage lm, HttpServletRequest req) throws Exception {
        lineManageEditValidate(lm);
        Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
        lm.setLineid(lm.getLineid().toUpperCase());
        //save
        if (lm.getId() == null) {
            lm.setMakedate(DateHanlder.getCurrentDateTime());
            lm.setMakeid(admin.getUserID());
            lm.setMakename(admin.getRealName());
            lms.save(lm);
        }
        //update
        else if (lm.getId() != null) {
            	lms.update(lm);
        }
        return String.valueOf(1);
    }

    //检查线路信息
    private void lineManageEditValidate(LineManage lm) throws Exception {
        Assert.notNull(lm.getCitystartid(),"始发城市编号不能为空！");
        Assert.notNull(lm.getCityarriveid(),"到达城市编号不能为空！");
        Assert.isTrue(lm.getBalanceticketwarn()!=null&&lm.getBalanceticketwarn()>=0,"余票预警不能为空");
        Assert.isTrue(StringUtils.isNotBlank(lm.getLineid())&&lm.getLineid().length()==3,"线路编号填写不正确");
        Assert.isTrue(lm.getTcid()!=null&&StringUtils.isNotBlank(lm.getTranscompany()),"公司不能为空！");
        Assert.notNull(lm.getDriverquantity(),"司机数量不能为空！");
        Assert.notNull(lm.getCarmodelid(),"车型不能为空！");

        lm.setCityarrivename(css.find(lm.getCityarriveid()).getCityname());
        lm.setCitystartname(css.find(lm.getCitystartid()).getCityname());
        lm.setCarmodelname(bresService.find(lm.getCarmodelid()).getTagsubvalue());
        lm.setLinename(lm.getCitystartname()+"-"+lm.getCityarrivename());
    }

    //删除线路
    @RequestMapping("deleteLineManage")
    @ResponseBody
    public  String deleteLineManage(String groupid,HttpServletRequest req) throws Exception {
        Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
        Assert.isTrue(admin.getIsAdmin()!=null&&admin.getIsAdmin()==1,"您没有权限删除线路！");
        Assert.hasText(groupid,"线路分组编号不能为空！");
        int i = lms.delLineManageByGroupId(groupid);
        return String.valueOf(i);
    }

    //更新线路
    @RequestMapping(value = {"updateLineManange"})
    public void updateLineManange(LineManage lm, Writer w) throws Exception {
        int r = -1;
        Assert.notNull(lm,"没有找到该线路！");
        if (lm != null && lm.getId() != null) {
            if (lm.getReleaseday() == null) {
                lm.setReleaseday(0);
            }
            if (lm.getApprovescheduleday() == null) {
            	lm.setApprovescheduleday(0);
            }
            r = lms.updateNotNull(lm);
        }
        APIUtil.toJSONString(r, w);
    }

    //审核列表
    @RequestMapping("approveLineScheduleList")
    public String approveLineScheduleList(Integer id, String date, Model m) throws Exception {
        LineManage lm = lms.find(id);
        m.addAttribute("lm", lm);
        if (date != null && date.isEmpty() == false) {
            m.addAttribute("date", date);
        }
        return "user/approve_line_schedule";
    }

    //绑定司机到线路
    @ResponseBody
    @RequestMapping("bindDriverListToLine.do")
    public String bindDriverListToLine(String groupid, String driveridList, String type) {
        Assert.hasText(groupid, "线路不能为空");
        Assert.hasText(driveridList, "司机不能为空");
        Assert.isTrue(CollectionUtil.contain(new String[]{"1","2"}, type), "无效类型");
        String[] parsedDriveridList = driveridList.split(",");
        lms.addDriverList(groupid, parsedDriveridList, type);
        return "1";
    }

    //解绑司机到线路
    @ResponseBody
    @RequestMapping("unbindDriverToLine.do")
    public String unbindDriverToLine(String groupid, String driverid) {
        Assert.hasText(groupid, "线路不能为空");
        Assert.hasText(driverid, "司机不能为空");
        lms.delDriver(groupid, driverid);
        return "1";
    }

    //线路所含车牌列表
    @RequestMapping("plateListForLine.do")
    public void plateListForLine(String groupid, Writer w) throws IOException {
        Assert.hasText(groupid, "线路不能为空");
        APIUtil.toJSONString(plateService.findListByLineGroupID(groupid), w);
    }

    //绑定车牌到线路司机
    @ResponseBody
    @RequestMapping("bindPlateToLineDriver.do")
    public String bindPlateToLineDriver(String groupid, String driverid, Integer plateid) {
        Assert.hasText(groupid, "线路不能为空");
        Assert.hasText(driverid, "司机不能为空");
        lms.addPlateToDriver(groupid, driverid, plateid);
        return "1";
    }

    //绑定车牌到线路
    @ResponseBody
    @RequestMapping("bindPlateListToLine.do")
    public String bindPlateListToLine(String groupid, String plateidList) {
        Assert.hasText(groupid, "线路不能为空");
        Assert.hasText(plateidList, "车牌不能为空");
        List<Integer> parsedPlateidList = new ArrayList<Integer>();
        for(String plateid : plateidList.split(",")){
            parsedPlateidList.add(Integer.parseInt(plateid));
        }
        Assert.notEmpty(parsedPlateidList, "车牌不能为空");
        lms.addPlateList(groupid, (Integer[]) parsedPlateidList.toArray(new Integer[parsedPlateidList.size()]));
        return "1";
    }

    //解绑车牌到线路
    @ResponseBody
    @RequestMapping("unbindPlateToLine.do")
    public String unbindPlateToLine(String groupid, Integer plateid) {
        Assert.hasText(groupid, "线路不能为空");
        Assert.notNull(plateid, "车牌不能为空");
        lms.delPlate(groupid, plateid);
        return "1";
    }

    //线路选取页
    @RequestMapping(value = "selectLine.do")
    public String selectLine(Page page, String userid, String keyword, Model m) {
        Assert.hasText(userid, "管理员不能为空");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userid", userid);
        params.put("keyword", keyword);
        page.setParam(params);
        page.setData(lms.findListForBindAdmin(page));
        m.addAttribute(page);
        return "user/selectLine";
    }

    //线路选取页
    @RequestMapping(value = "selectLineForProhibit.do")
    public String selectLineForProhibit(Page page, Integer pid, String keyword, Model m) {
        Assert.notNull(pid, "ID不能为空");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", pid);
        params.put("keyword", keyword);
        page.setParam(params);
        page.setData(lms.findListForBindProhibit(page));
        m.addAttribute(page);
        return "user/selectLineForProhibit";
    }

    //线路选取页
    @RequestMapping(value = "selectLineForShowTime.do")
    public String selectLineForShowTime(Page page, Integer stid, String keyword, Model m) {
        Assert.notNull(stid, "ID不能为空");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", stid);
        params.put("keyword", keyword);
        page.setParam(params);
        page.setData(lms.findListForBindShowTime(page));
        m.addAttribute(page);
        return "user/selectLine";
    }

    //线路选取页
    @RequestMapping(value = "selectLineForIntegralRule.do")
    public String selectLineForIntegralRule(Page page, Integer irid, String keyword, Model m) {
        Assert.notNull(irid, "积分规则不能为空");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", irid);
        params.put("keyword", keyword);
        page.setParam(params);
        page.setData(lms.findListForBindIntegralRule(page));
        m.addAttribute(page);
        return "user/selectLineForIntegralRule";
    }

}
