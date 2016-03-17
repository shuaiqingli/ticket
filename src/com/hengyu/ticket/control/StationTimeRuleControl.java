package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineManageStation;
import com.hengyu.ticket.entity.StationTimeRule;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.StationTimeRuleService;
import com.hengyu.ticket.util.DateHanlder;
import com.sun.javafx.binding.StringFormatter;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by LGF on 2016/3/11 0011.
 */
@Controller
@RequestMapping("/user")
public class StationTimeRuleControl {

    @Autowired
    private StationTimeRuleService stationTimeRuleService;
    @Autowired
    private LineManageService lineManageService;
    @Autowired
    private CityStationService cityStationService;

    //线路站点规则编辑页面
    @RequestMapping("stationTimeRule")
    private String stationTimeRule(Integer lmid,Integer id ,Model m) throws Exception {
        Assert.notNull(lmid, "线路编号不能为空！");
        LineManage lm = lineManageService.find(lmid);
        m.addAttribute("lm", lm);

        List<CityStation> begin = cityStationService.findByParent(lm.getCitystartid());
        List<CityStation> end = cityStationService.findByParent(lm.getCityarriveid());
        if (begin != null) {
            m.addAttribute("begincity", begin);
        }
        if (end != null) {
            m.addAttribute("endcity", end);
        }
        if(id!=null){
            StationTimeRule rule = stationTimeRuleService.find(id);
            Assert.isTrue(rule!=null, Const.HTTP_MSG_NOT_FOUND);
            m.addAttribute("rule",rule);
        }
        return "user/lineStationTimeRule";
    }

    //编辑规则
    @RequestMapping("editStationTimeRule")
    @ResponseBody
    public String editStationTimeRule(StationTimeRule rule) throws Exception {
        validateEditStationTimeRule(rule);
        if(rule.getId()==null){
            stationTimeRuleService.save(rule);
        }else{
            stationTimeRuleService.update(rule);
        }
        return "1";
    }


    //复制规则
    @RequestMapping("copyStationTimeRule")
    public String copyStationTimeRule(Integer id) throws Exception {
        StationTimeRule rule = stationTimeRuleService.find(id);
        Assert.notNull(rule,Const.HTTP_MSG_NOT_FOUND);
        validateEditStationTimeRule(rule);

        rule.setId(null);
        rule.setIsdefault(0);
        List<StationTimeRule> rules = rule.getRules();
        for (StationTimeRule r : rules ) {
            r.setId(null);
            r.setGroupid(null);
            List<LineManageStation> stations = r.getStations();
            for (LineManageStation station : stations ) {
                station.setStrid(null);
                station.setId(null);
            }
        }

        if(rule.getId()==null){
            stationTimeRuleService.save(rule);
        }else{
            stationTimeRuleService.update(rule);
        }
        return "redirect:lineadd.do?id="+rule.getLmid()+"#3";
    }

    @RequestMapping("delStationTimeRule")
    public String delStationTimeRule(Integer id,Integer lmid) throws Exception {
        Assert.notNull(id,"编号不能为空！");
        Assert.notNull(lmid,"线路编号不能为空！");
        stationTimeRuleService.delete(id);
        return "redirect:lineadd.do?id="+lmid+"#3";
    }

    //验证非法参数
    public void validateEditStationTimeRule(StationTimeRule rule) throws ParseException {
        Assert.notNull(rule.getLmid(), "线路编号不能为空！");
        Assert.hasText(rule.getRulename(), "规则名称不能为空！");
        if(rule.getId()!=null&&(rule.getRules()==null||rule.getRules().size()==0)){
           return;
        }
        Assert.notEmpty(rule.getRules(), "请添加规则！");
        SimpleDateFormat tf = DateHanlder.getTimeFromat();
        for (StationTimeRule r : rule.getRules()) {
            String begintime = r.getBegintime();
            String endtime = r.getEndtime();

            r.setLmid(rule.getLmid());
            Assert.isTrue(r.getBegintime() != null && r.getBegintime().length() == 5, "开始时间填写不正确！");
            Assert.isTrue(r.getEndtime() != null && r.getEndtime().length() == 5, "结束时间填写不正确！");
            Assert.isTrue(tf.parse(begintime).compareTo(tf.parse(endtime))<=0,"时间填写不正确！");
            Assert.notEmpty(r.getStations(), "请添加途径站点！");
            int j = 0;
            boolean isStartStation = false;
            boolean isEndStation = false;
            for (LineManageStation s : r.getStations()) {
                if (j > 0) {
                    LineManageStation lms = r.getStations().get(j - 1);
                    Assert.isTrue(s.getRequiretime()>lms.getRequiretime(), MessageFormat.format("{0}站点间隔时间填写不正确！",s.getStname()));
                    Assert.isTrue(s.getSubmileage()>lms.getSubmileage(), MessageFormat.format("{0}站点里程填写不正确！",s.getStname()));
                }
                Assert.hasText(s.getStid(), "站点编号不能为空");
                Assert.hasText(s.getStname(), "站点名称不能为空");
                Assert.notNull(s.getRequiretime(), "间隔时间不能为空！");
                Assert.notNull(s.getSubmileage(), "里程不能为空！");
                Assert.notNull(s.getSort());
                Assert.hasText(s.getSortname());
                s.setLmid(rule.getLmid());
                j++;
                if(s.getSort()==0){
                    isStartStation = true;
                }
                if(s.getSort()==1){
                    isEndStation = true;
                }
            }
            Assert.isTrue(isStartStation,"请选择始发城市途径站点！");
            Assert.isTrue(isEndStation,"请选择到达城市途径站点！");
        }
    }
}
