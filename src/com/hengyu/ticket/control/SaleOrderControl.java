package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengyu.ticket.util.ExcelHanlder;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.SaleOrderTicket;
import com.hengyu.ticket.service.CustomerService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.service.SaleOrderTicketService;

@Controller
@RequestMapping("/user")
public class SaleOrderControl {
	
	@Autowired
	private SaleOrderService sos;
	@Autowired
	private SaleOrderTicketService sots;
	@Autowired
	private CustomerService cs;
	
	//订单查询
	@RequestMapping("saleorderlist")
	public String saleorderlist(HttpServletRequest req,HttpServletResponse resp,Page page,SaleOrder so,Integer export,Model m) throws Exception {
		if(export!=null&&export==1){
			page.setPageSize(Integer.MAX_VALUE);
		}
		Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
		page.setAdmin(admin);
		page.setParam(so);
		page.setData(sos.findOrderlist(page));
		if(export!=null&&export==1){
			export(page.getData(),resp,req);
			return  "";
		}

		m.addAttribute(page);
		return "user/orders";
	}

	public void export(List<SaleOrder> list, HttpServletResponse resp,HttpServletRequest req) throws Exception {
		String[] titles = new String[]{"订单号","线路","班次号","出发站","到达站","出发日期","发车时间","取票人","手机号码","数量","退票数量",
                "下单总数","实付金额","总价格","下单日期","状态","顾客ID","顾客名称","注册日期","取票日期" };
		String[] columns = new String[]{"id","linename","shiftcode","ststartname","starrivename","ridedate","starttime","lname",
				"lmobile","quantity","refundcount","ordercount","actualsum","totalsum","makedate","statusname","cid","cname","customermakedate","takedate"};
		XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "订单信息", list, new ExcelHanlder.ExcelTypeHanlder<SaleOrder>() {
			@Override
			public void typeHanlder(SaleOrder data, XSSFRow row) {

			}
		});
		ExcelHanlder.writer(book,"订单信息.xlsx",resp,req);
	}
	
	//订单详情
	@RequestMapping("saleorderdetail")
	public String saleorderdetail(String id,Model m) throws Exception{
		SaleOrder order = sos.find(id);
		List<SaleOrderTicket> details = sots.findBySaleOrder(id);
		m.addAttribute(order);
		m.addAttribute("details",details);
		return "user/order_detail";
	}
	
	//客服退款
	@RequestMapping("applyRefundTicket")
	public void applyRefundTicket(String checkcodes,String cid,Writer w) throws Exception{
		Assert.isTrue(checkcodes!=null&&checkcodes.trim().length()!=0,"编号不能为空");
		Assert.isTrue(cid!=null&&cid.trim().length()!=0,"客户编号不能为空");
		Customer c = cs.find(cid);
		Assert.notNull(c,"没有找到客户！");
		String[] ids = checkcodes.split(",");
		int r = sos.updateRefundOrder(ids, 1, 0, 2,"退票中",1,"已付款", c,false,1,false);
		w.write(String.valueOf(r));
		w.close();
	}

}
