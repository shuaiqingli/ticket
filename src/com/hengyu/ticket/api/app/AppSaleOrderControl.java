package com.hengyu.ticket.api.app;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.dao.CityStationDao;
import com.hengyu.ticket.entity.API;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.SaleOrderTicket;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.service.SaleOrderTicketService;
import com.hengyu.ticket.service.SeatService;
import com.hengyu.ticket.service.ShowTimeService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;

@Controller
@RequestMapping(value={"/app/api/saleorder","/web/api/saleorder"})
@SuppressWarnings("unchecked")
public class AppSaleOrderControl {
	
	Logger log = Logger.getLogger(AppSaleOrderControl.class);
	
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private SaleOrderTicketService sotService;
	@Autowired
	private CityStationService css;
	@Autowired
	private SaleOrderTicketService sots;
	@Autowired
	private ShowTimeService sts;

	//生成订单
	@RequestMapping("order")
	public void order(TicketLine tl,Integer quantity,String linkid,String buyway,Integer discountnum,Integer isdiscount,String vouchercode,Writer w,HttpServletRequest req) throws Exception{
		Customer c = (Customer) req.getAttribute(Const.LOGIN_CUSTOMER);
		if(discountnum==null){
			discountnum = 0;
		}
		if(APIUtil.isNotNull(w,quantity,linkid,buyway)){
			Assert.isTrue(quantity>=1,"填写票数不正确！");
			Assert.isTrue(quantity<=5,"购买票不能超过5张！");
			Assert.isTrue(discountnum<=5,"购买票不能超过5张！");
			API api = new API();
			Integer discount = 0;
			if(isdiscount!=null&&isdiscount==1){
				discount = 1;
			}
			if(discountnum!=null){
				discount = discountnum;
			}
			SaleOrder so = saleOrderService.saveorder(tl, c, quantity, linkid, buyway,discount,vouchercode,req.getSession().getServletContext().getRealPath(Const.QRCODE_PATH));
			if(so==null){
				api.setCode(5011);
			}else{
				Map<String,String> result = new HashMap<String,String>(1);
				result.put("id", so.getId());
				if((c.getRank()!=null&&c.getRank()==2)||so.getActualSum().compareTo(new BigDecimal(0))==0){
					result.put("autopay","1");
				}
				api.getDatas().add(result);
			}
			APIUtil.toJSON(api, w);
		}
	}
	
	//详情
	@RequestMapping("detail")
	public void detail(String id,Writer w) throws Exception{
		if(APIUtil.isNotNull(w,id)){
			API api = new API();
			SaleOrder so = saleOrderService.find(id);
			Assert.notNull(so,"没有找到该订单！");
			CityStation cs = css.find(so.getSTStartID());
			so.setTicketaddr(cs.getTicketaddr());
			so.setStationaddr(cs.getTicketaddr());
			List<SaleOrderTicket> sots = sotService.findBySaleOrder(so.getId());
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (SaleOrderTicket s : sots) {
				if(s.getSeatno()==null||s.getSeatno()==0){
					continue;
				}
				sb.append(s.getSeatno());
				if(i!=sots.size()-1){
					sb.append("、");
				}
				i++;
			}
			so.setSeatnos(sb.toString());
			so.setSots(sots);
			
			String content = sts.findShowContent(so.getLmid(),so.getRideDate());
			if(content==null){
				content = "";
			}
			so.setTipcontent(content);
			api.getDatas().add(so);
			APIUtil.toJSON(api, w,true);
		}
	}
	
	
	//详情
	@RequestMapping("getLineTipContent")
	public void getTipContent(Integer lmid,String date,Writer w) throws Exception{
		String content = sts.findShowContent(lmid,date);
		if(content==null){
			content = "";
		}
		API api = new API();
		api.getDatas().add(content);
		APIUtil.toJSON(api, w,true);
	}
	
	/**
	 * 
	 * @param id
	 * @param status
	 * 支持状态：2退款中 4.已取消
	 * @param w
	 * @throws IOException
	 */
	@RequestMapping("cancelorder")
	public void cancelorder(String id,Integer status,Writer w) throws Exception{
		if(APIUtil.isNotNull(w, id,status)){
			API api = new API();
			SaleOrder so = saleOrderService.find(id);
			//退款，必须是 已经付款状态、未取票
			if(status==2&&so.getPayStatus()==1&&so.getStatus()==0){
//				so.setStatus(2);
//				so.setStatusName("退款中");
//				api.getDatas().add(saleOrderService.updateReturned(so,false));
			}
			//取消，必须是 未付款状态
			else if(status==4&&so.getPayStatus()==0){
				so.setStatus(4);
				so.setStatusName("已取消");
				api.getDatas().add(saleOrderService.updateCancelSaleOrder(so,true));
			}else{
				api.setCode(5012);
			}
			APIUtil.toJSON(api, w);
		}
	}
	
	//订单列表
	@SuppressWarnings("rawtypes")
	@RequestMapping("orderlist")
	public void orderlist(Page page,Writer w,HttpServletRequest req){
		Customer c = (Customer) req.getAttribute(Const.LOGIN_CUSTOMER);
		API api = new API();
		page.setParam(c);
		List orderlist = saleOrderService.getOrderlist(page);
		api.setPage(page);
		api.setDatas(orderlist);
		APIUtil.toJSON(api, w);
	}
	
	//按订单号查询车票子列表
	@RequestMapping("ticketDetail")
	public void orderTicketDetail(String orderid,Writer w) throws Exception{
		List<SaleOrderTicket> orders = sots.findBySaleOrder(orderid);
		SimpleDateFormat df = DateHanlder.getDateFromat();
		for (SaleOrderTicket s : orders) {
			String date = s.getRidedate();
			Date d = df.parse(date);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			String time = s.getStarttime();
			c.add(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
			c.add(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
			if(c.getTime().getTime()<=System.currentTimeMillis()){
				s.setIsontrain(1);
			}
		}
		API api = new API();
		api.setDatas(orders);
		APIUtil.toJSON(w, api, new Class[]{SaleOrderTicket.class}, new String[]{"seatno,checkcode,status,isontrain"}, true, false);
	}
	
	//获取退款信息
	@RequestMapping("getRefundInfo")
	public void getRefundInfo(String checkcodes, Writer w, HttpServletRequest req, HttpServletResponse response) throws Exception{
		Assert.isTrue(checkcodes!=null&&checkcodes.trim().length()!=0,"编号不能为空");
		Customer c = (Customer) req.getAttribute(Const.LOGIN_CUSTOMER);
		String[] ids = checkcodes.split(",");
		List<SaleOrderTicket> tickets = new ArrayList<SaleOrderTicket>();
		for (String id : ids) {
			SaleOrderTicket o = sots.find(id);
			Assert.notNull(o,"没有找到车票记录！");
			tickets.add(o);
		}
		SaleOrder so = saleOrderService.find(tickets.get(0).getSoid());

		Cookie cookie = new Cookie("orderstatus",so.getStatus()+"");
		cookie.setPath("/");
		response.addCookie(cookie);

		Assert.notNull(so,"没有找到订单！");
		Map<String, Object> refundMoney = saleOrderService.getRefundMoney(so, tickets,c,false,true,0);
		refundMoney.put("status",so.getStatus());
		API api = new API();
		api.getDatas().add(refundMoney);
		APIUtil.toJSON(api, w);
	}
	
	//订单申请退款，添加退款记录
	@RequestMapping("refundTicket")
	public void refundTicket(String checkcodes,Writer w,HttpServletRequest req) throws Exception{
		Assert.isTrue(checkcodes!=null&&checkcodes.trim().length()!=0,"编号不能为空");
		Customer c = (Customer) req.getAttribute(Const.LOGIN_CUSTOMER);
		String[] ids = checkcodes.split(",");
		
		int r = saleOrderService.updateRefundOrder(ids, 1, 0, 2,"退票中",1,"已付款", c,true,0);
		API api = new API();
		api.getDatas().add(r);
		APIUtil.toJSON(api, w);
	}
	
	
}
