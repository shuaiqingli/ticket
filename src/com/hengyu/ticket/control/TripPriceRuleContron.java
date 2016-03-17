package com.hengyu.ticket.control;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.LineStation;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineManageStation;
import com.hengyu.ticket.entity.StationLine;
import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.entity.TripPriceSub;
import com.hengyu.ticket.service.ApproveTicketService;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.LineManageStationService;
import com.hengyu.ticket.service.StationLineService;
import com.hengyu.ticket.service.TripPriceRuleService;
import com.hengyu.ticket.service.TripPriceSubService;
import com.hengyu.ticket.util.APIUtil;


@Controller
@RequestMapping("/user")
public class TripPriceRuleContron {
	
	@Autowired
	private LineManageService lms;
	@Autowired
	private ApproveTicketService ats;
	@Autowired
	private TripPriceRuleService tprs;
	@Autowired
	private TripPriceSubService tpss;
	@Autowired
	private StationLineService sls;
	@Autowired
	private LineManageStationService lmss;
	
	@ModelAttribute
	public void before(Integer id,Model m) throws Exception{
		TripPriceRule tpr = tprs.find(id);
		if(tpr==null){return;}
		m.addAttribute(tpr);
	}
	
	//生成行程价格站点，或者查询
	@RequestMapping("getTripPriceTicketLineData")
	public void getTripPriceTicketLineData(Integer lmid,TripPriceRule tpr,Writer w) throws Exception{
		if(tpr==null||tpr.getId()==null){
			if(tpr==null){
				tpr = new  TripPriceRule();
			}
			tpr.setLmid(lmid);
			//查询模板
			List<TripPriceSub> list = tpss.findByTripPriceRule(tpr);
			LineManage lm = lms.find(lmid);
			List<LineStation> stations = ats.getTickLineStations(lm);
			List<TripPriceSub> sublist = new ArrayList<TripPriceSub>();
			TripPriceSub sub = null;
			int i = 0;
			for (LineStation ls : stations) {
				sub = new TripPriceSub();
				sub.setLinename(lm.getLinename());
				sub.setLmid(lm.getId());
				sub.setStstartname(ls.getStartStation().getStname());
				sub.setStarrivename(ls.getEndStation().getStname());
				Integer m = (ls.getEndStation().getSubmileage()==null?0:ls.getEndStation().getSubmileage()) - (ls.getStartStation().getSubmileage()==null?0:ls.getStartStation().getSubmileage());
				sub.setMileage(m);
				sub.setPricesort(i);
				
				if(list!=null&&list.size()!=0){
					//匹配模板
					for (TripPriceSub tpsub : list) {
						if(tpsub.getStarriveid().equals(sub.getStarriveid())&&tpsub.getStstartid().equals(sub.getStstartid())){
							sub.setPrice(tpsub.getPrice());
							break;
						}
					}
				}
				
				
				sublist.add(sub);
				i++;
			}
			tpr.setId(null);
			tpr.setTps(sublist);
			
			//始发城市站点
			tpr.setStationlines(sls.findByLMID(lmid));
		}else{
			List<TripPriceSub> list = tpss.findByTripPriceRule(tpr);
			tpr.setTps(list);
			List<StationLine> stationlist = sls.findByTripPriceRule(tpr);
			if(stationlist==null||stationlist.size()==0){
				tpr.setStationlines(sls.findByLMID(lmid));
			}else{
				tpr.setStationlines(stationlist);
			}
		}
		APIUtil.toJSONString(tpr, w);
	}
	
	//编辑行程价格
	@RequestMapping("saveOrUpdateTripPriceRule")
	public void saveOrUpdateTripPriceRule(TripPriceRule tpr,BindingResult br,Model m,Writer w) throws Exception{
		int r = -1;
		Assert.notNull(tpr);
		Assert.notNull(tpr.getTicketquantity(),"总余票不能为空");
		Assert.notNull(tpr.getLockquantity(),"锁票不能为空");
		Assert.notNull(tpr.getStartseat(),"卖票开始位置不能为空");
		Assert.isTrue(tpr.getTicketquantity()>=tpr.getLockquantity(),"锁票不能大于总票！");
		Assert.isTrue(tpr.getStartseat()>=1,"卖票位置不能小于1！");
		Assert.notNull(tpr.getTplid(),"行程价格日期参数丢失！");
		Assert.notNull(tpr.getWeekday(),"星期不能为空！");
		Assert.notEmpty(tpr.getTps(),"行程价格设置无效！");
		List<StationLine> stationlines = tpr.getStationlines();
		Assert.notEmpty(stationlines,"限制站点卖票规则无效！");
		if(tpr.getIsstart()!=0){
			Assert.isTrue(tpr.getIsstart()<=tpr.getStartseat(),"卖票开始位置不能小于，开始位置！");
			int b = tpr.getTicketquantity() - tpr.getLockquantity();
			Assert.isTrue((tpr.getStartseat()+b)<=(tpr.getIsstart()+tpr.getTicketquantity()),"卖票开始位置超出最大位置！");
		}
		
		//行程价格
		List<TripPriceSub> tps = tpr.getTps();
		for (TripPriceSub tp : tps) {
			Assert.notNull(tp.getPrice(),"价格不能为空！");
			Assert.isTrue(tp.getPrice().compareTo(new BigDecimal(0))==1,"价格不能为0或负数！");
		}
		
		//限制站点卖票规则
		Integer maxcouponticket = 0;
		for (StationLine sl : stationlines) {
			System.out.println(JSON.toJSON(sl));
			Assert.notNull(sl.getStationid(),"站点ID参数丢失!");
			Assert.isTrue(sl.getCouponticketpercent()>=0&&sl.getCouponticketpercent()<=100,sl.getStationname()+"填写百分比不正确");
			Assert.isTrue(sl.getTicketpercent()>=0&&sl.getTicketpercent()<=100,sl.getStationname()+"填写百分比不正确");
			//计算票数
			sl.setCouponsalequantity(sl.getCouponticketpercent()*tpr.getTicketquantity()/100);
			sl.setSalequantity(sl.getTicketpercent()*tpr.getTicketquantity()/100);
			if(maxcouponticket<sl.getCouponsalequantity()){
				maxcouponticket = sl.getCouponsalequantity();
			}
			
			Assert.isTrue(sl.getCouponsalequantity() <= sl.getSalequantity(),sl.getStationname()+"优惠票不能大于站点总票！");
			
		}
		//总优惠票
		tpr.setCouponticketquantity(maxcouponticket);
		
		if(tpr.getId()==null){
			r = tprs.save(tpr);
		}else{
			r = tprs.update(tpr);
		}
		APIUtil.toJSONString(r, w);
	}
	
	//按规则获取详情
	@RequestMapping("findTripPriceSubByTripPriceRule")
	public void findTripPriceSubByTripPriceRule(TripPriceRule tpr,Writer w) throws Exception{
		List<TripPriceSub> list = tpss.findByTripPriceRule(tpr);
		APIUtil.toJSONString(list, w);
	}
	

	
}
