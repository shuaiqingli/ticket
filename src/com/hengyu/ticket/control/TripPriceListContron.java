package com.hengyu.ticket.control;

import java.util.List;

import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class TripPriceListContron {

    @Autowired
    private LineManageService lms;
    @Autowired
    private TripPriceListService tps;
    @Autowired
    private StationTimeRuleService stationTimeRuleService;
    @Autowired
    private CityStationService cityStationService;

    //获取售票规则
    @RequestMapping("lineSaleRule")
    public String getSaleRule(Integer lmid, Integer lrid, Integer id, Model model) throws Exception {
        Assert.notNull(lmid, "线路编号不能为空");
        List<StationTimeRule> timeRules = stationTimeRuleService.findByLMID(lmid);
        Assert.notEmpty(timeRules, "没有找到线路规则！");
        if (lrid == null) {
            for (StationTimeRule tr : timeRules) {
                lrid = tr.getIsdefault() != null && tr.getIsdefault() == 1 ? tr.getId() : timeRules.get(0).getId();
            }
        }
        TripPriceList saleRule = tps.getSaleRule(id, lrid);

        model.addAttribute("rule", saleRule);
        model.addAttribute("timeRules", timeRules);
        return "user/lineSaleRule";
    }


    @RequestMapping("copyLineSaleRule")
    public String copyLineSaleRule(Integer id, Model model) throws Exception {
        TripPriceList tpl = tps.find(id);
        tpl.setIsdefault(0);
        Assert.notNull(tpl,"没有找到售票规则！");
        tpl.setId(null);
        editTripPriceRule(tpl);
        return "redirect:lineadd.do?id="+tpl.getLmid()+"#5";
    }

    @RequestMapping("delSaleRule")
    public String delSaleRule(Integer id,Integer lmid, Model model) throws Exception {
        tps.delete(id,null,true);
        return "redirect:lineadd.do?id="+lmid+"#5";
    }

    //编辑行程价格
    @RequestMapping("editTripPriceRule")
    @ResponseBody
    public String editTripPriceRule(TripPriceList tp) throws Exception {
        validateEditTripPriceRule(tp);
        if (tp.getId() != null) {
            tps.update(tp);
        } else {
            tps.save(tp);
        }
        return "1";
    }

    //验证
    public void validateEditTripPriceRule(TripPriceList tp) throws Exception {
        Assert.notNull(tp.getLmid(), "线路编号不能为空");
        Assert.notNull(tp.getStrid(), "线路规则编号不能为空");
        Assert.hasText(tp.getTplname(), "规则名称不能为空！");
        Assert.isTrue(tp.getTicketquantity() != null && tp.getTicketquantity() >= 0, "总票数填写不正确！");
        Assert.isTrue(tp.getLockquantity() != null && tp.getLockquantity() >= 0, "锁票数填写不正确！");
        Assert.isTrue(tp.getIsstart() != null && tp.getIsstart() >= 0, "位置填写不正确！");
        Assert.isTrue(tp.getStartseat() != null && tp.getStartseat() >= 0, "卖票位置填写不正确！");
        Assert.isTrue(tp.getTicketquantity() >= tp.getLockquantity(), "锁票数量不能大于总票数！");
        Assert.notEmpty(tp.getTps(), "行程不能为空！");
        Assert.isTrue(tp.getStartseat() >= tp.getIsstart(), "开始卖票位置必须大于等于开始位置！");
        if (tp.getIsstart() != 0) {
            Assert.isTrue((tp.getTicketquantity() - tp.getLockquantity() + tp.getStartseat()) <= (tp.getIsstart() + tp.getTicketquantity()), "卖票位置不能超过" + (tp.getIsstart() + tp.getTicketquantity()));
        }
        LineManage lm = lms.find(tp.getLmid());
        int i = 1;
        int couponquantity = 0;
        for (TripPriceSub sub : tp.getTps()) {
            Assert.hasText(sub.getStstartid(), "城市不能为空！");
            Assert.hasText(sub.getStarriveid(), "城市不能为空！");
            Assert.isTrue(sub.getLimitsale() != null && sub.getLimitsale() >= 0, "正价票填写不正确！");
            Assert.isTrue(sub.getLimitcoupons() != null && sub.getLimitcoupons() >= 0, "优惠票填写不正确！");
            Assert.isTrue(sub.getPrice() != null && sub.getPrice().doubleValue() > 0, "价格填写不正确！");
            sub.setLmid(lm.getId());
            sub.setLinename(lm.getLinename());
            sub.setStstartname(cityStationService.find(sub.getStstartid()).getCityname());
            sub.setStarrivename(cityStationService.find(sub.getStarriveid()).getCityname());
            Assert.isTrue(tp.getTicketquantity() >= sub.getLimitsale(), "行程销售票数不能大于总票数量！");
            Assert.isTrue(sub.getLimitsale() >= sub.getLimitcoupons(), "优惠票数量不能大于正价票！");
            sub.setPricesort(i);
            i++;
            couponquantity = couponquantity < sub.getLimitcoupons() ? sub.getLimitcoupons() : couponquantity;
        }
        Assert.isTrue((couponquantity+tp.getLockquantity())<=tp.getTicketquantity(),"锁票数与调价票数之和不能超过总票数!");
        tp.setCouponticketquantity(couponquantity);
    }


}
