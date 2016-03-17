package com.hengyu.ticket.control;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.TripPriceList;
import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.TripPriceListService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;

@Controller
@RequestMapping("/user")
public class TripPriceListContron {

	@Autowired
	private LineManageService lms;
	@Autowired
	private TripPriceListService tps;
	
	@ModelAttribute
	public void before(Integer id,Model m) throws Exception{
		if(id!=null){
			TripPriceList tp = tps.find(id);
			if(tp!=null){
				m.addAttribute(tp);
			}
		}
	}
	
	//行程价格规则列表
	@RequestMapping("tripPriceRuleList")
	public String tripPriceRuleList(Page page,TripPriceList tpl,Integer lmid,Model m) throws Exception{
		page.setPageSize(5);
		LineManage lm = lms.find(lmid);
		page.setParam(tpl);
		if(lm!=null){
			m.addAttribute("lm", lm);
		}
		List<TripPriceList> list = tps.findlist(page);
		if(list!=null){
			m.addAttribute("tps",list);
			if(list.size()!=0){
				SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
				TripPriceList tp = list.get(0);
				if(StringUtils.isNotEmpty(tp.getEnddate())&&dateFromat.parse(tp.getBegindate()).getTime()<=dateFromat.parse(DateHanlder.getCurrentDate()).getTime()
					&&dateFromat.parse(tp.getEnddate()).getTime()>=dateFromat.parse(DateHanlder.getCurrentDate()).getTime()){
					tp.setBegindate("今天");
				}
			}
		}
		return "user/trippricerulelist";
	}
	
	//查询TripPriceList
	@RequestMapping("findTripPrice")
	public void findTripPrice(TripPriceList tp,Writer w ) throws IOException{
		APIUtil.toJSONString(tp,w);
	}
	
	//编辑行程价格
	@RequestMapping("editTripPriceRule")
	public void saveTripPrice(@Validated TripPriceList tp,BindingResult br,Writer w) throws Exception{
		if(br.hasErrors()){
			APIUtil.toJSONString(-1,w);
			return;
		}
		if(1 == tp.getIsforever()){
			tp.setEnddate(null);
		}
		SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
		if(StringUtils.isNotEmpty(tp.getEnddate())&&dateFromat.parse(tp.getBegindate()).getTime()>dateFromat.parse(tp.getEnddate()).getTime()){
			APIUtil.toJSONString("结束时间不能小于开始时间!",w);
			return;
		}
		TripPriceList e = tps.findExistsDate(tp);
		if(e!=null){
			if(StringUtils.isEmpty(e.getEnddate())){
				e.setEnddate("无限期");
			}
			APIUtil.toJSONString("行程价格日期与："+e.getBegindate()+"至"+e.getEnddate()+" 冲突，保存失败！", w);
			return;
		}
		int ret = -1;
		if(tp.getId()==null){
			ret = tps.save(tp);
		}else{
			ret = tps.update(tp);
		}
		APIUtil.toJSONString(ret, w);
	}
	
	//获取已经设置的星期
	@RequestMapping("getTripPriceListWeekDay")
	public void getTripPriceListWeekDay(TripPriceRule tpr,Writer w) throws Exception{
		TripPriceList tpl = tps.findWeekdayByTripPrice(tpr);
		APIUtil.toJSONString(tpl, w);
	}
	
}
