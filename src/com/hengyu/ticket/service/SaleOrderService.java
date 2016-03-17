package com.hengyu.ticket.service;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.common.LinkedMap;
import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.dao.*;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
@Service
public class SaleOrderService {

	@Autowired
	private SaleOrderDao saleOrderDao;
	@Autowired
	private TicketLineDao ticketLineDao;
	@Autowired
	private LinkmanDao linkmanDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private TicketStoreDao ticketStoreDao;
	@Autowired
	private TicketConfig tc;
	@Autowired
	private MakeConfService makeConfService;
	@Autowired
	private SaleOrderTicketService sotservice;
	@Autowired
	private CouponsDao couponsDao;
	@Autowired
	private IntegralLineDao integralLineDao;
	@Autowired
	private TicketLineStoreDao tlsdao;
	@Autowired
	private SeatDao seatdao;
	@Autowired
	private SaleOrderTicketDao sotdao;
	@Autowired
	private CityStationDao citystationdao;
	@Autowired
	private RefundRuleDao rrdao;
	@Autowired
	private CouponsDao coupondao;
	@Autowired
	private RefundTicketDao rtdao;
	@Autowired
	private RefundTicketDetailDao rtddao;
	@Autowired
	private LockTicketLogDao lockTicketLogDao;
	@Autowired
	private CouponsRuleDao crdao;
	@Autowired
	private TicketConfig cnf;
	
	//结算用户积分
	public void updateCustomerOrderIntegral() throws Exception{
		List<SaleOrder> list = saleOrderDao.findOrderIntegral();
		if(list!=null){
			for (SaleOrder order : list) {
				try {
					List<SaleOrderTicket> tickets = sotdao.findBySaleOrderId(order.getId());
					//	BigDecimal  coupon = order.getCouponsSum().divide(new BigDecimal(tickets.size()));
					BigDecimal total = new BigDecimal(0);
					int refundcount = 0;
					Integer mileage = 0;
					for (SaleOrderTicket t : tickets) {
						if (t.getStatus() != 0) {
							refundcount++;
							continue;
						}else{
							mileage += order.getMileage();
						}

						if (t.getIsdiscount() != null && t.getIsdiscount() == 1) {
							total = total.add(t.getVprice());
						} else {
							total = total.add(t.getPrice());
						}
					}
					Customer customer = customerDao.find(order.getCID());
					customer.setMileage(customer.getMileage()==null?mileage:customer.getMileage()+mileage);
					if(order.getCouponsSum()!=null&&!order.getCouponsSum().equals(new BigDecimal(0))){
						total = total.subtract(order.getCouponsSum());
					}
					//不算积分
					if (refundcount == tickets.size()||total.compareTo(new BigDecimal(0))<=0) {
						Assert.isTrue(saleOrderDao.updateOrderIntegralStatus(order) == 1, "赠送积分失败！");
						continue;
					}
					
					//赠送积分
					customer.setIntegral(
							(int) (total.floatValue() + (customer.getIntegral() == null ? 0 : customer.getIntegral())));
					if (order.getRank() != null && order.getRank() == 2) {
						Assert.isTrue(saleOrderDao.updateOrderIntegralStatus(order) == 1, "赠送积分失败！");
						Log.info(this.getClass(), "站务购票不送积分！");
						continue;
					}
					//送红包
					giveRedPackage(customer, total,3,true,"满额送","元红包");
					//满额送
					giveRedPackage(customer, total,2,false,"满额送","代金劵");
					
					Assert.isTrue(customerDao.update(customer) == 1, "同步积分失败!" + order.getId());
					IntegralLine integralLine = new IntegralLine();
					integralLine.setOperatorid(order.getId());
					integralLine.setIntegral(order.getActualSum().intValue());
					integralLine.setType(1);
					integralLine.setCustomerid(order.getCID());
					integralLine.setCustomername(order.getCName());
					integralLine.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
					Assert.isTrue(integralLineDao.saveIntegralLine(integralLine) == 1, "记录积分流水失败!" + order.getId());
					Assert.isTrue(saleOrderDao.updateOrderIntegralStatus(order) == 1, "赠送积分失败！");
				} catch (Exception e) {
					Log.exception(e);
				}
				
			}
		}
	}
	
	//送红包,或满额送
	public void giveRedPackage(Customer customer,BigDecimal total,Integer type,boolean isSendMessage,String prefix,String suffix) throws Exception{
		List<CouponsRule> crs = crdao.findCouponRuleByType(type);
		if(crs!=null){
			for (CouponsRule c : crs) {
				if(c.getIslimitgive()!=null&&c.getIslimitgive()==1){
					Integer count = coupondao.checkCountByVsortCID(customer.getCid(), 3);
					if(count!=null&&count>=1){
						break;
					}
					if(total.compareTo(c.getBuysum())>=0){
						Coupons cp = giveCoupon(customer, c, prefix, suffix);
						if(isSendMessage){
							//推送优惠劵消息
							sendCouponMsg(customer, cp);
						}
						break;
					}
				}else{
					if(total.compareTo(c.getBuysum())>=0){
						Coupons cp = giveCoupon(customer, c, prefix, suffix);
						if(isSendMessage){
							//推送优惠劵消息
							sendCouponMsg(customer, cp);
						}
					}
				}
			}
		}
	}
	
	//推送优惠劵消息
	public void sendCouponMsg(Customer customer,Coupons cp){
		if(StringUtils.isNotEmpty(customer.getOpenid())){
			Map<String, String> msg = cnf.getWxmsg();
			Map<String, Object> data = new HashMap<String, Object>();
			String color = msg.get("color");
			DecimalFormat df = new DecimalFormat("0");
			data.put("first", new LinkedMap<String, String>().add("value",msg.get("coupon_first")).add("color", color));
			data.put("coupon", new LinkedMap<String, String>().add("value", String.valueOf(df.format(cp.getVprice()))+"元\n").add("color", color));
			data.put("expDate", new LinkedMap<String, String>().add("value", cp.getEnddate()+"\n").add("color", color));
			data.put("remark", new LinkedMap<String, String>().add("value",msg.get("coupon_remark")+"\n").add("color", color));
			String templateid = msg.get("coupon_template"); 
			String url = msg.get("coupon_url"); 
			WeixinHanlder.Messaeg.sendTemplate(customer.getOpenid(),templateid,url, data );
		}
	}
	
	//赠送优惠劵
	public Coupons giveCoupon(Customer customer,CouponsRule cr,String vnamePrefix,String vname) throws Exception{
		//生成优惠券
		Coupons cp = new Coupons();
		Long tmpvc = Long.valueOf(System.currentTimeMillis()+new Random().nextInt(9));
		String hexvc = Long.toHexString(tmpvc);
		cp.setVouchercode(hexvc.toUpperCase());
		cp.setVouchername(new StringBuilder().append(vnamePrefix).append(cr.getVprice()).append(vname).toString());
		cp.setMemo(cr.getVsortname());
		cp.setCid(customer.getCid());
		cp.setCname(customer.getCname());
		cp.setBegindate(DateUtil.getCurrentDateString());
		cp.setEnddate(DateUtil.formatDate(
				DateUtil.calculatedays(DateUtil.getCurrentDate(), cr.getValidday())));
		cp.setLowlimit(cr.getLowlimit());
		cp.setVprice(cr.getVprice());
		cp.setIsuse(1);
		cp.setIsusename("可用");
		cp.setMakedate(DateUtil.getCurrentDateTime());
		cp.setVsort(cr.getVsort());
		
		int save = coupondao.save(cp);
		Assert.isTrue(save==1,"赠送优惠劵失败！");
		return cp;
	}
	

	/**
	 * 保存一个对象
	 * 
	 * @param saleOrder
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(SaleOrder saleOrder) throws Exception {
		return saleOrderDao.save(saleOrder);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param saleOrder
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(SaleOrder saleOrder) throws Exception {
		return saleOrderDao.update(saleOrder);
	}

	public int updateStatus(SaleOrder saleorder) throws Exception {
		return saleOrderDao.updateStatus(saleorder);
	}

	// 更新微信订单支付状态
	public String updatePayNotify(String orderid, String qrcodepath, String payModel) throws Exception {
		SaleOrder order = saleOrderDao.find(orderid);
		try {
			if (order == null) {
                Log.info(this.getClass(), "订单没有找到！");
                return "error";
            }

			// 已经付款了
			if (order.getPayStatus() == 1) {
                Log.error("============= >> 该订单已经付款：" + order.getId());
                return "success";
            } else {
                order.setPayMode(payModel.toUpperCase());
                order.setTicketCode(NumberCreate.createTicketCode(order.getMobile()));
                order.setOutcode(orderid);
                String url = MatrixToImageWriter.write(qrcodepath, order.getTicketCode(), 360, 360);
                order.setCodeurl(Const.QRCODE_PATH + url);
                //更新付款状态
                updatePay(order,qrcodepath);
                CityStation cs = citystationdao.find(order.getSTStartID());
    //			CityStation ststation = citystationdao.find(order.getSTStartID());
                CityStation endstation = citystationdao.find(order.getStarriveid());
                order.setSTStartName(cs.getCityname()==null?"":cs.getCityname());
                order.setSTArriveName(endstation.getCityname()==null?"":endstation.getCityname());
                VelocityContext ctx = new VelocityContext();
                if(order!=null){
                    ctx.put("order", order);
                }
                if(cs!=null){
                    ctx.put("station", cs);
                }
                String msg = TemplateHanlder.getMsgTemplate(TemplateHanlder.MSG_ORDER_SUCCESS, ctx);
                SmsUtil.sendSms(order.getMobile(), msg);
                Log.info(this.getClass(), "============= >> 发送短信给客户：" + msg);
				Customer c = customerDao.find(order.getCID());
				wxSendOrderSuccessMsg(payModel,order,c.getOpenid());
            }
		} catch (Exception e) {
			Customer customer = customerDao.find(order.getCID());
			if(StringUtils.isNotEmpty(customer.getOpenid())){
				Map<String, String> msg = cnf.getWxmsg();
				Map<String, Object> data = new HashMap<String, Object>();

				StringBuffer sb = new StringBuffer();
				sb.append("订单号：").append(order.getId()).append("\n");
				sb.append("金额：").append(order.getActualSum()).append("\n");
				sb.append("车票购买失败，请联系客服退款！");

				String color = msg.get("color");
				data.put("name", new LinkedMap<String, String>().add("value",order.getLinename()+"车票").add("color", color));
				data.put("remark", new LinkedMap<String, String>().add("value",sb.toString()).add("color", color));
				String templateid = msg.get("buyfailure_template");
				WeixinHanlder.Messaeg.sendTemplate(customer.getOpenid(),templateid,"", data );
				throw e;
			}
		}
		return "success";
	}

	//订单成功推送消息
	public void wxSendOrderSuccessMsg(String payModel,SaleOrder order,String openid){
		try {
			if(openid!=null&&openid.isEmpty()==false){
                Map<String, String> conf = tc.getWxmsg();
                String first = conf.get("buy_success_first");
                String template = conf.get("buy_success_template");
                String remark = conf.get("buy_success_remark");
                String info = new StringBuffer().append(order.getRideDate()).append(" ").append(order.getStartTime())
                        .append(order.getStstartname()).append("-").append(order.getStarrivename())
                        .toString();
                String color = conf.get("color");
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("first",new LinkedMap<String, String>().add("value",first).add("color", color));
                data.put("remark",new LinkedMap<String, String>().add("value",remark).add("color", color));
                data.put("keyword1",new LinkedMap<String, String>().add("value",info).add("color", color));
                data.put("keyword2",new LinkedMap<String, String>().add("value",order.getActualSum().toString()).add("color", color));
                data.put("keyword3",new LinkedMap<String, String>().add("value",order.getQuantity().toString()).add("color", color));
                data.put("keyword4",new LinkedMap<String, String>().add("value",order.getId()).add("color", color));
                WeixinHanlder.Messaeg.sendTemplate(openid,template,"", data);
            }
		} catch (Exception e) {

		}
	}

	/**
	 * 更新付款状态
	 * 
	 * @param saleOrder
	 * @return
	 * @throws Exception
	 */
	public int updatePay(SaleOrder saleOrder,String qrcodepath) throws Exception {
		Assert.isTrue(saleOrder.getStatus()==0,"支付回调订单失败"+saleOrder.getId());
		Integer lmid = saleOrder.getLmid();
		String ridedate = saleOrder.getRideDate();
		Integer quantity = saleOrder.getQuantity();
		String shiftcode = saleOrder.getShiftNum();
		Long l = seatdao.checkExistsByLmidDateShiftCode(lmid, ridedate, shiftcode);
		if(l==null||l==0){
			Log.info(this.getClass(),"不用排座位！");
			int count = 0;
			for (int i = 1; i <= quantity; i++) {
				SaleOrderTicket sot = new SaleOrderTicket();
				//标识是优惠票
				if(i<=saleOrder.getDiscountquantity()){
					sot.setIsdiscount(1);
					sot.setVprice(saleOrder.getVprice());
				}else{
					sot.setIsdiscount(0);
					sot.setVprice(saleOrder.getPrice());
				}
				sot.setLmid(saleOrder.getLmid());
				sot.setSoid(saleOrder.getId());
				sot.setCheckcode(saleOrder.getId() + NumberCreate.createNumber(i, 3));
				sot.setSoid(saleOrder.getId());
				sot.setSeatno(0);
				sot.setTicketcode(saleOrder.getTicketCode());
				sot.setLinename(saleOrder.getLinename());
				sot.setShiftnum(saleOrder.getShiftNum());
				sot.setStarttime(saleOrder.getStartTime());
				sot.setRidedate(saleOrder.getRideDate());
				sot.setPrice(saleOrder.getPrice());
				sot.setStarrivename(saleOrder.getStarrivename());
				sot.setStarriveid(saleOrder.getStarriveid());
				sot.setStstartid(saleOrder.getSTStartID());
				sot.setStstartname(saleOrder.getStstartname());
				if(saleOrder.getRank()!=null&&saleOrder.getRank()==2){
					sot.setPaystatus(2);
				}else{
					sot.setPaystatus(1);
				}
				count += sotservice.save(sot);
				String url = MatrixToImageWriter.write(qrcodepath, sot.getCheckcode(), 360, 360,sot.getCheckcode());
				Log.info(this.getClass(), url);
			}
			Assert.isTrue(count == quantity, "生成订单列表详情错误！" + saleOrder.getId());
		}else{
			synchronized (saleOrder) {
				Log.info(this.getClass(), "排座位!");
				Seat n1 = null;
				Seat n2 = null;
				if(quantity==2){
					Seat tow = seatdao.findTowNotSaleSeat(lmid, ridedate, shiftcode);
					if(tow!=null&&tow.getSeattow()!=null){
						n1 = tow;
						n2 = tow.getSeattow();
						
						n1.setIssale(1);
						Assert.isTrue(seatdao.updateUseSeat(n1)==1,"占用位置1失败！");;
						
						n2.setIssale(1);
						Assert.isTrue(seatdao.updateUseSeat(n2)==1,"占用位置2失败！");;
					}
				}
				int count = 0;
				for (int i = 1; i <= quantity; i++) {
					SaleOrderTicket sot = new SaleOrderTicket();
					//标识是优惠票
					if(i<=saleOrder.getDiscountquantity()){
						sot.setIsdiscount(1);
						sot.setVprice(saleOrder.getVprice());
					}else{
						sot.setIsdiscount(0);
						sot.setVprice(saleOrder.getPrice());
					}
					if(n1!=null&&n2!=null&&quantity==2){
						if(i==1){
							sot.setSeatno(n1.getSeatno());
							sot.setSeatid(n1.getId());
						}else{
							sot.setSeatno(n2.getSeatno());
							sot.setSeatid(n2.getId());
						}
					}else{
						Seat s = seatdao.findUniqueNotSaleSeat(lmid, ridedate, shiftcode);
						sot.setSeatno(s.getSeatno());
						sot.setSeatid(s.getId());
						s.setIssale(1);
						Assert.isTrue(seatdao.updateUseSeat(s)==1,"占用位置失败");
					}
					sot.setCheckcode(saleOrder.getId() + NumberCreate.createNumber(i, 3));
					sot.setSoid(saleOrder.getId());
					sot.setTicketcode(saleOrder.getTicketCode());
					sot.setLinename(saleOrder.getLinename());
					sot.setShiftnum(saleOrder.getShiftNum());
					sot.setStarttime(saleOrder.getStartTime());
					sot.setRidedate(saleOrder.getRideDate());
					sot.setPrice(saleOrder.getPrice());
					sot.setStarrivename(saleOrder.getStarrivename());
					sot.setStarriveid(saleOrder.getStarriveid());
					sot.setStstartid(saleOrder.getSTStartID());
					sot.setStstartname(saleOrder.getStstartname());
					if(saleOrder.getRank()!=null&&saleOrder.getRank()==2){
						sot.setPaystatus(2);
					}else{
						sot.setPaystatus(1);
					}
					count += sotservice.save(sot);
					String url = MatrixToImageWriter.write(qrcodepath, sot.getCheckcode(), 360, 360,sot.getCheckcode());
					Log.info(this.getClass(), url);
				}
				Assert.isTrue(count == quantity, "生成订单列表详情错误！" + saleOrder.getId());
			}
		}
		int r  = -1;
		//站务锁定车票
		//否则更新支付状态
		if(saleOrder.getRank()!=null&&saleOrder.getRank()==2){
			r = saleOrderDao.updateLockPay(saleOrder);
		}else{
			r = saleOrderDao.updatePay(saleOrder);
		}
		
		String couponcode = saleOrder.getCoupons();
		if(StringUtils.isNotEmpty(couponcode)){
			Coupons cp = coupondao.find(couponcode);
			Assert.notNull(cp,"没有找到优惠劵");
			cp.setIsuse(0);
			cp.setIsusename("不可用");
			Assert.isTrue(coupondao.update(cp)==1,"使用优惠劵失败！");
		}
		
		Assert.isTrue(r == 1, "更新支付状态出错了！" + saleOrder.getId());
		
//		Customer customer = customerDao.find(saleOrder.getCID());
//		customer.setIntegral((int) (saleOrder.getActualSum().floatValue()
//				+ (customer.getIntegral() == null ? 0 : customer.getIntegral())));
//		
//		if(customer.getRank()!=null&&customer.getRank()==2){
//			Log.info(this.getClass(),"站务购票不送积分！");
//			return r;
//		}
//		Assert.isTrue(customerDao.update(customer) == 1, "同步积分失败!" + saleOrder.getId());
//		IntegralLine integralLine = new IntegralLine();
//		integralLine.setOperatorid(saleOrder.getId());
//		integralLine.setIntegral(saleOrder.getActualSum().intValue());
//		integralLine.setType(1);
//		integralLine.setCustomerid(saleOrder.getCID());
//		integralLine.setCustomername(saleOrder.getCName());
//		integralLine.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
//		Assert.isTrue(integralLineDao.saveIntegralLine(integralLine) == 1, "记录积分流水失败!" + saleOrder.getId());
		
		return r;
	}

	/**
	 * 更新支付加密信息
	 * 
	 * @param saleOrder
	 * @return
	 * @throws Exception
	 */
	public int updatePayFeedBack(SaleOrder saleOrder) throws Exception {
		return saleOrderDao.updatePayFeedBack(saleOrder);
	}
	
	//退款,退票
	public int updateRefundSaleOrederTicket(String returnedNo,Integer status,String memo,Admin admin,BigDecimal actualprice,Integer rpercent) throws Exception{
		int result = -1;
		RefundTicket rt = rtdao.find(returnedNo);
		Assert.notNull(rt,"没有找到退款订单！");
		Assert.isTrue(rt.getIsaffirm()==1,"站务未审核退款订单！");
		rt.setApprovedate(DateHanlder.getCurrentDateTime());
		if(admin!=null){
			rt.setApproveid(admin.getUserID());
			rt.setApprovename(admin.getRealName());
		}
		rt.setMemo(memo);

		BigDecimal max = new BigDecimal(0).add(rt.getTotalprice());
		if(rt.getRpercent()!=0){
			max = max.add(rt.getOriginalprice().multiply(new BigDecimal(rt.getRpercent())).divide(new BigDecimal(100)));
		}
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		Assert.isTrue(actualprice.doubleValue()<=max.doubleValue(),"退款金额不能比总金额大,最大退款金额："+decimalFormat.format(max));

//		Assert.isTrue(actualprice.doubleValue()<=rt.getTotalprice().doubleValue(),"退款金额不能比总金额大！");
		rt.setActualprice(actualprice);
//		double temp = 100-(actualprice.doubleValue()/rt.getOriginalprice().doubleValue())*100;
//		rt.setRpercent((int)temp);
		rt.setRpercent(rpercent);

		//已退款
		if(rt.getRtstatus()==3){
			return 1;
		}
		List<RefundTicketDetail> details = rtdao.findDetails(returnedNo);
		Assert.isTrue(details!=null&&details.size()!=0,"没有找到退款详情！");
		String[] checkecodes = new String[details.size()];
		int i = 0;
		for (RefundTicketDetail detail : details) {
			checkecodes[i] = detail.getCheckcode();
			i++;

		}
		SaleOrder order = saleOrderDao.find(rt.getSoid());
		Assert.notNull(order,"没有找到订单！");
		Customer c = customerDao.find(order.getCID());
		Assert.notNull(c,"没有找到客户信息！");

		//正常更新退款状态
		if(status==1){
			updateRefundOrder(checkecodes, 3, 1, 3,"已退票",1,"已付款",c,false,1);
		}
		//拒绝退款
		else if(status==2){
			updateRefundOrder(checkecodes, 5, 1, 5,"拒绝退票",1,"已付款", c,false,1);
		}

		Integer rank = 0;
		if(order.getRank()!=null){
			rank = order.getRank();
		}
		//不是站务，退款
		if(rank!=2&&status!=2&&rt.getActualprice().compareTo(new BigDecimal(0))==1){
			if(order.getPayMode().equalsIgnoreCase("WX")){
				Map<String, String> map = wxOrderRefund(order.getId(), returnedNo, order.getActualSum(), rt.getActualprice());
				String refund_id = map.get("refund_id");
				rt.setOutcode(refund_id);
			}
		}
		rt.setRtstatus(status);
		result = rtdao.updateRefund(rt);
		Assert.isTrue(result==1,"更新退款记录失败！");
		return result;
	}

	//微信退款
	public Map<String, String>  wxOrderRefund(String orderid,String returnedNo,BigDecimal totalMoney,BigDecimal returnedMoney) throws Exception{
		Map<String, String> map = WeixinHanlder.returnedorder(orderid, returnedNo, totalMoney, returnedMoney);
		Assert.notNull(map,"微信退款失败！");
		return map;
	}

	/**
	 * 取消订单
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */

	public int updateCancelSaleOrder(SaleOrder order,boolean isadd) throws Exception {
		int result = -1;
		
		try {
			//取消班次，增加余票
			int status = order.getStatus();
			if (isadd) {
				//增加站点票库，和总票库
				returnedTicketStore(order);
			}
			//取消班次释放座位
			if(status==4){
				List<SaleOrderTicket> list = sotdao.findBySaleOrderId(order.getId());
				if(list!=null){
					for (SaleOrderTicket sot : list) {
						Seat seat = new Seat();
						seat.setId(sot.getSeatid());
						seat.setIssale(0);
						Assert.isTrue(seatdao.updateUseSeat(seat)==1,"恢复座位失败！");
					}
				}
				//优惠劵
//				String couponCode = order.getCoupons();
//				if(couponCode!=null&&couponCode.trim().isEmpty()==false){
//					Coupons coupons = coupondao.find(couponCode);
//					if(coupons!=null){
//						coupons.setIsuse(1);
//						coupons.setIsusename("可用");
//						Assert.isTrue(coupondao.update(coupons)==1,"释放优惠劵失败！");
//					}
//
//				}
			}


			//更新订单状态
			result = saleOrderDao.updateOrderReturned(order);
			
			Assert.isTrue(result == 1,"更新订单状态失败：status : " +order.getStatus()+" orderid："+order.getId());
			
		} catch (Exception e) {
			throw e;
		}
		return result;
	}
	
	//取消订单增加余票或一退款订单增加余票
	public int returnedTicketStore(SaleOrder order) throws Exception{
		int result = -1;
		Integer lmid = order.getLmid();
		String shiftcode = order.getShiftNum();
		String ststartid = order.getSTStartID();
		
		Log.info(this.getClass(), "========== 订单 : ", order.getStatusName());
		TicketStore ts = ticketStoreDao.findBySaleOrder(order);
		TicketLineStore tls = tlsdao.findBySaleOrder(order);
		if (ts == null) {
			Log.info(this.getClass(), "========== 票库已被删除，取消无效"," orderid : ",order.getId());
			Assert.isTrue(false, "票库已被删除，取消无效 orderid : "+order.getId());
		}
		if (tls == null) {
			Log.info(this.getClass(), "========== 站点票库已被删除，取消无效"," orderid : ",order.getId());
			Assert.isTrue(false, "站点票库已被删除，取消无效 orderid : "+order.getId()+" lmid:"+lmid+"  shiftcode:"+shiftcode+"  ststartid:"+ststartid);
		}
		
		Log.info(this.getClass(), "========== 增加余票 ： ", order.getQuantity()," orderid : ",order.getId());
		//优惠票不加库存
		//saleOrder.setDiscountquantity(0);

		int addCountResult = ticketStoreDao.addQuantity(ts.getId(), order.getQuantity(),
				order.getDiscountquantity());
		int addQuantityByOrder = tlsdao.addQuantityByOrder(order);
		Assert.isTrue(addCountResult == 1, "系统异常，增加余票失败！");
		Assert.isTrue(addQuantityByOrder == 1, "系统异常，增加站点余票失败！====== >> orderid : "+order.getId());
		
		return result;
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回SaleOrder对象
	 * @throws Exception
	 */
	public SaleOrder find(String id) throws Exception {
		return saleOrderDao.find(id);
	}

	// 按取票码查询，ticketcode
	public SaleOrder getSaleOrderByTicketCode(String ticketcode) throws Exception {
		return saleOrderDao.getSaleOrderByTicketCode(ticketcode);
	}

	// 整单取票按取票码查询，不判断status
	public SaleOrder getAllSaleOrderByTicketCode(String ticketcode) throws Exception {
		return saleOrderDao.getAllSaleOrderByTicketCode(ticketcode);
	}

	public void setSaleOrderDao(SaleOrderDao saleOrderDao) {
		this.saleOrderDao = saleOrderDao;
	}

	/**
	 * 生成车票订单
	 * 
	 * @param tkl
	 *            车票线路查询条件
	 * @param cus
	 *            顾客对象
	 * @param quantity
	 *            车票数量
	 * @param linkId
	 *            联系人id
	 * @param buyWay
	 *            订单途径 wx,android,ios,web
	 * @return
	 * @throws Exception
	 */
	public SaleOrder saveorder(TicketLine tkl, Customer cus, final Integer quantity, final String linkId,
			final String buyWay, final Integer discountCount, final String vouchercode, final String qrcodepath)
					throws Exception {
		// 查询车票线路详情
		final TicketLine tl = ticketLineDao.findUniqueTicketLine(tkl);
		Assert.notNull(tl != null, "没有找到车票线路！");
		final Linkman link = linkmanDao.find(linkId);
		final Customer c = cus;
		final TicketStore ticketStore = tl.getTicketStore();
		Assert.notNull(ticketStore != null, "总票库异常！");
		Assert.isTrue(!(ticketStore.getIsapprove() == 2), "班次已经取消，请选择其他班次！");
		Assert.notNull(link, "没有找到该联系人！");
		link.setSynchrono(System.currentTimeMillis());
		Assert.isTrue(linkmanDao.update(link) == 1, "更新联系人信息失败！");

		SimpleDateFormat dateTimeFromat = DateHanlder.getDateTimeFromat();
		long startdate = dateTimeFromat.parse(tl.getTicketdate() + " " + tl.getStarttime()).getTime();
		if (startdate < System.currentTimeMillis() + 1000 * 60 * (tc.getTicketTime() - 1)) {
			throw new BaseException("订单超时请提前" + tc.getTicketTime() + "分钟下单", 6000);
		}
		// 添加销售记录
		SaleOrder so = new SaleOrder();
		// 生成订单号，保存订单
		return makeConfService.findMakeConf(so, 7, new Execute<SaleOrder, SaleOrder>() {
			@Override
			public SaleOrder execute(MakeConf mc, SaleOrder so, String id) throws Exception {
				// 获取订单前缀
				String prefix = makeConfService.findMakeConf(Const.SALEORDER_PREFIX_KEY, null,
						Const.SALEORDER_PREFIX_KEY_INITVAL, false, new Execute<String, String>() {
					public String execute(MakeConf mc, String o, String id) throws Exception {
						return id;
					}
				});
				
				DecimalFormat dcf = new DecimalFormat("0.0");

				so.setId(prefix + id);
				// so.setTicketCode(NumberCreate.createTicketCode(c.getMobile()));
				so.setCID(c.getCid());
				so.setCName(c.getCname());
				so.setMobile(c.getMobile());
				so.setRideDate(tl.getTicketdate());
				so.setLmid(tl.getLmid());
				so.setLineName(tl.getLinename());
				so.setSTArriveID(tl.getStarriveid());
				so.setSTArriveName(tl.getStarrivename());
				so.setSTStartID(tl.getStstartid());
				so.setSTStartName(tl.getStstartname());
				so.setStartTime(tl.getStarttime());
				so.setArriveTime(tl.getArrivetime());
				so.setShiftNum(tl.getShiftcode());
				so.setPrice(tl.getTicketprice());
				so.setQuantity(quantity);

				so.setLName(link.getLname());
				so.setLMobile(link.getLmobile());
				so.setIDCode(link.getIdcode());
				so.setVprice(tl.getFavprice());
				so.setMileage(tl.getMileage());
				so.setIsintegralsett(0);
				// 优惠价 乘以票数
				so.setRank(c.getRank()==null?0:c.getRank());
				
				BigDecimal total = new BigDecimal(0);
				int dcount = discountCount;
				if(c.getRank()!=null&&c.getRank()==2){
					dcount = 0;
				}
				if(dcount!=0){
					total = total.add(tl.getFavprice().multiply(new BigDecimal(dcount)));
				}
				if((quantity-dcount)!=0){
					total = total.add(tl.getTicketprice().multiply(new BigDecimal(quantity-dcount)));
				}
				so.setTotalSum(total);
				so.setIssett(0);

				// 检查优惠劵是否过期
				Coupons coupons = null;
				if (vouchercode != null && vouchercode.isEmpty() == false) {
					coupons = couponsDao.find(vouchercode);
					if (coupons != null) {
						if (c.getRank()!=null&&c.getRank()==2){
							Assert.isTrue(false,"站务不能使用优惠劵！");
						} 
						SimpleDateFormat df = DateHanlder.getDateFromat();
						Assert.isTrue(!(coupons.getIsuse() != null && coupons.getIsuse() == 0), "优惠劵已经使用过了！");
						// 不可用
						coupons.setIsuse(0);
						coupons.setIsusename("不可用");
						Assert.isTrue(!(df.parse(coupons.getBegindate()).getTime() > System.currentTimeMillis()),
								"优惠时间没到，优惠劵不能使用！");

						Date date = df.parse(df.format(new Date()));
						Assert.isTrue((df.parse(coupons.getEnddate()).getTime() >=  date.getTime()),
								"优惠劵已经过期！");

						Assert.isTrue(coupons.getLowlimit().compareTo(total.divide(new BigDecimal(so.getQuantity()))) <= 0, "均价最低消费不够，不能使用优惠劵！");
						Log.info(this.getClass(), "======》 成功使用优惠劵", "金额：", coupons.getVprice(), " 最低消费：",
								coupons.getLowlimit());
						so.setCoupons(coupons.getVouchercode());
					} else {
						Assert.isTrue(false, "优惠劵无效！");
					}
				}

				so.setActualSum(
						coupons != null && coupons.getVprice() != null ? total.subtract(coupons.getVprice()) : total);
				so.setBalancePay(new BigDecimal(0));

				so.setCouponsSum(
						coupons != null && coupons.getVprice() != null ? coupons.getVprice() : new BigDecimal(0));
				so.setOtherPay(total);
				
//				so.setActualSum(new BigDecimal(dcf.format(so.getActualSum())));

				so.setPayStatus(0);
				so.setStatus(0);
				so.setPayStatusName("未支付");
				so.setStatusName("未取票");
				so.setMakedate(DateHanlder.getCurrentDateTime());
				so.setBuyWay(buyWay);
				// 优惠劵大于等于订单金额
				if (so.getActualSum().floatValue() <= 0) {
					so.setActualSum(new BigDecimal(0));
				}

				// 优惠票
				so.setDiscountquantity(dcount);

				// 修改总票库
				int result = ticketStoreDao.updateQuantity(ticketStore.getId(), quantity, so.getDiscountquantity());

				// 检查修改站点票库是否成功
				if (tlsdao.updateQuantityByOrder(so) != 1) {
					throw new BaseException("站点余票不足，下单失败", 6000);
				}
				// 检查修改总票库是否成功
				if (result != 1) {
					throw new BaseException("总余票不足，下单失败！", 6000);
				}
				so.setIsabnormal(0);
				so.setActualSum(new BigDecimal(dcf.format(so.getActualSum())));

				// 保存订单
				int save = saleOrderDao.save(so);
				Assert.isTrue(save == 1, "服务器忙，下单失败！");

				Log.info(this.getClass(), "===========> 减少余票,下单成功 .....");

				// 实付金额为0更新付款状态
				if (so.getActualSum().compareTo(new BigDecimal(0)) == 0 || (c.getRank()!=null&&c.getRank()==2)) {
					String payModel = buyWay;
					if(c.getRank()!=null&&c.getRank()==2){
						Log.info(this.getClass(), "===========> 站务下单自动付款 .....");
						payModel = "ZW";
					}else{
						Log.info(this.getClass(), "===========> 优惠金额大于等于实付金额，更新付款状态 .....");
					}
					if (!(updatePayNotify(so.getId(), qrcodepath, payModel).equals("success"))) {
						// 更新付款状态失败
						throw new BaseException("下单失败！", 6000);
					}
				}

				return so;
			}

		});
	}

	/**
	 * 取消订单
	 * 
	 * @param minute
	 *            多少分钟没有付款就取消
	 * 
	 */
	public void updatecancelorder(Integer minute) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		long currenttime = System.currentTimeMillis();
		String begintime = DateHanlder.getDateTimeFromat().format(new Date(currenttime - 1000 * 60 * minute));
		// String endtime = DateHanlder.dateTimeFromat.format(new
		// Date(currenttime));
		params.put("begintime", begintime);
		Log.info(this.getClass(), "=============== 取消订单下单时间：", begintime);
		// 查询所有没有付款的订单
		List<SaleOrder> orderlist = saleOrderDao.getNotPayOrder(params);
		Log.info(this.getClass(), "=============== 没有付款的订单记录 ：", orderlist.size());
		for (SaleOrder saleOrder : orderlist) {
			saleOrder.setStatus(4);
			saleOrder.setStatusName("已取消");
			try {
				this.updateCancelSaleOrder(saleOrder, true);
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("订单取消失败：",e.getMessage(),"  =====>> orderid : "+saleOrder.getId());
			}
		}
	}

	// 获取已销售数量
	public Long getSoldQuantity(Map c) throws Exception {
		return saleOrderDao.getSoldQuantity(c);
	}

	// 统计未取票数量,条件：lineid线路id,shiftnum班次，ridedate日期,paysttus=1已支付，status=0未取票
	public Long getNoTakeQuantity(Map q) throws Exception {
		return saleOrderDao.getNoTakeQuantity(q);
	}

	// 已取票人数列表，条件：lineid线路id,shiftnum班次，ridedate日期,paysttus=1已支付，status=1已取票
	public List getAlreadyTake(Map c) throws Exception {
		return saleOrderDao.getAlreadyTake(c);
	}

	// 订单搜索，条件：ststartid,keywords(查订单号id，取票人lname,手机lmobile)
	public List searchSaleOrder(Map a) throws Exception {
		return saleOrderDao.searchSaleOrder(a);
	}

	// 获取有异常的订单记录
	public List getAbnormalSaleOrderList(Map a) throws Exception {
		return saleOrderDao.getAbnormalSaleOrderList(a);
	}

	// 获取有异常的订单记录条数
	public Long totalCountAbnormal(Map a) throws Exception {
		return saleOrderDao.totalCountAbnormal(a);
	}

	/**
	 * APP查询订单列表
	 * 
	 * @param page
	 * @return
	 */
	public List getOrderlist(Page page) {
		page.setTotalCount(saleOrderDao.getOrderlistCount(page));
		return saleOrderDao.getOrderlist(page);
	}

	/**
	 * 后台订单列表
	 * 
	 * @param page
	 * @return
	 */
	public List<SaleOrder> findOrderlist(Page page) {
		page.setTotalCount(saleOrderDao.findOrderListCount(page));
		List<SaleOrder> orderList = saleOrderDao.findOrderList(page);
		return orderList;
	}

	// 订单异常验票
	public int abnormalUpdateSaleOrderShiftNum(Map a) {
		return saleOrderDao.abnormalUpdateSaleOrderShiftNum(a);
	}
	
	//获取退款金额
	public Map<String,Object> getRefundMoney(SaleOrder order,List<SaleOrderTicket> tickets,Customer c,boolean isGetRefundInfo,boolean ischeck,Integer makesort) throws Exception{
		SaleOrder newOrder = saleOrderDao.find(order.getId());
		Assert.isTrue(order.getActualSum().compareTo(new BigDecimal(0))>0,"优惠劵购买全票不能退款！");
		SimpleDateFormat df = DateHanlder.getDateFromat();
		Calendar ticketdate = Calendar.getInstance();
		ticketdate.setTimeInMillis(df.parse(order.getRideDate()).getTime());
		String starttime = order.getStartTime();
		int hour = Integer.parseInt(starttime.split(":")[0]);
		int minute = Integer.parseInt(starttime.split(":")[1]);
		ticketdate.add(Calendar.HOUR_OF_DAY, hour);
		ticketdate.add(Calendar.MINUTE, minute);
		Integer rank = 0;
		if(order.getRank()!=null){
			rank = order.getRank();
		}
		int i = 0;
		int percent = 0;
		double time = 0;
		double matchtime = -1;
		if(rank!=2&&ischeck==true){
			List<RefundRule> rules = rrdao.findRuleByLmidTicketDateRank(order.getLmid(), order.getRideDate(),rank);
			Assert.isTrue(rules!=null&&rules.size()!=0,"该订单不能申请退款，请联系客服！");
			RefundRule rule = rules.get(0);
			List<RefundRuleTime> times = rule.getTimes();
			Assert.isTrue(times!=null&&times.size()!=0,"该订单不能申请退款，请联系客服。");
			long diffTime = ticketdate.getTimeInMillis()-System.currentTimeMillis();
			Assert.isTrue(diffTime>0,"退款已超时，不能退款！");
			time = diffTime/(1000*60*60.00);
			Assert.isTrue(time>0,"退款已超时，不能退款！");
			
			for (RefundRuleTime t : times) {
				if(i==(times.size()-1)){
					Assert.isTrue(time>=t.getAdvancetime(),"发车前"+t.getAdvancetime()+"小时内不能退票！");
				}
				if(time>=t.getAdvancetime()){
					percent = t.getDeduction();
					matchtime = t.getAdvancetime();
					break;
				}
				i++;
			}
		}
		//退款金額
		BigDecimal refundMoney = new BigDecimal(0);
		//退款總金額 100% 不扣除手續費
		BigDecimal originalprice = new BigDecimal(0);
		//总金额，包括优惠劵金额
		BigDecimal total = new BigDecimal(0);
		BigDecimal avgCouponMoney = new BigDecimal(0);
		//订单优惠均价
		if(order.getCouponsSum()!=null&&!order.getCouponsSum().equals(new BigDecimal(0))){
			avgCouponMoney = order.getCouponsSum().divide(new BigDecimal(newOrder.getQuantity()));
		}

		RefundTicket refundticket = null;
		if(isGetRefundInfo){
			refundticket = new RefundTicket();
			refundticket.setId(DateUtil.getBeforeLongDateTimeString());
			refundticket.setMakedate(DateHanlder.getCurrentDateTime());
			refundticket.setCid(c.getCid());
			refundticket.setRidedate(order.getRideDate());
			refundticket.setCname(c.getCname());
			refundticket.setMobile(c.getMobile());
			refundticket.setRtstatus(0);
			refundticket.setSoid(order.getId());
			refundticket.setRank(rank);
			refundticket.setMakesort(makesort);
			refundticket.setQuantity(tickets.size());
			refundticket.setStstartid(order.getSTStartID());
			refundticket.setStstartname(order.getStstartname());
			if(order.getStatus()==1){
				refundticket.setIsaffirm(0);
				refundticket.setIsaffirmname("未确认");
			}else{
				refundticket.setIsaffirm(1);
				refundticket.setIsaffirmname("已确认");
				refundticket.setAffirmmemo("未取票自动确认！");
			}
			refundticket.setStarriveid(order.getStarriveid());
			refundticket.setStarrivename(order.getStarrivename());
			refundticket.setStarttime(order.getStartTime());
			if(rank==2){
				refundticket.setRtstatus(1);
				refundticket.setMemo("站务退票");
			}
		}

		//退票详情
		List<RefundTicketDetail> details = null;
		if(isGetRefundInfo){
			details = new ArrayList<RefundTicketDetail>();
		}

		//计算金额
		RefundTicketDetail tkd = null;
		for (SaleOrderTicket o : tickets) {
			boolean isrefund = (o.getStatus()==0||o.getStatus()==6);
			Assert.isTrue(isrefund,"该订单已经退票");
			Assert.isTrue(o.getIsontrain()==0,"该订单已验票，不能退票！");
			BigDecimal price = new BigDecimal(0);
			//优惠票退款
			if(o.getIsdiscount()!=null&&o.getIsdiscount()==1){
				price = o.getVprice().subtract(avgCouponMoney);
				if(percent!=0){
					price = price.subtract(o.getPrice().multiply(new BigDecimal(percent)).divide(new BigDecimal(100)));
				}
				total.add(o.getVprice());
			}
//			正价票退款
			else{
				price = o.getPrice().subtract(avgCouponMoney);
//				originalprice = originalprice.add(price);
				if(percent!=0){
					price = price.subtract(o.getPrice().multiply(new BigDecimal(percent)).divide(new BigDecimal(100)));
				}
				total.add(o.getPrice());
			}
			if(isGetRefundInfo){
				tkd = new RefundTicketDetail();
				tkd.setCheckcode(o.getCheckcode());
				tkd.setPrice(price);
				tkd.setVprice(avgCouponMoney);
				tkd.setRtid(refundticket.getId());
				details.add(tkd);
			}
			originalprice = originalprice.add(o.getPrice());
			refundMoney = refundMoney.add(price);
		}
		//返回结果
		DecimalFormat decf = new DecimalFormat("0.0");
		DecimalFormat decf2 = new DecimalFormat("0.00");
		
		if(refundMoney.compareTo(new BigDecimal(0))<=0){
			refundMoney = new BigDecimal(0);
		}
		if(isGetRefundInfo){
			refundticket.setOriginalprice(originalprice);
			refundticket.setRpercent(percent);
			refundticket.setActualprice(refundMoney);
			refundticket.setTotalprice(refundMoney);
			refundticket.setDetails(details);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("refundmoney", decf.format(refundMoney));
		map.put("price", decf.format(order.getPrice()));
		map.put("avgcouponmoney", decf.format(avgCouponMoney));
		map.put("total", decf.format(total));
		map.put("percent", String.valueOf(percent));
		map.put("refundcount", String.valueOf(tickets.size()));
		map.put("time", decf2.format(time));
		map.put("rank", rank);
		if(isGetRefundInfo){
			map.put("refundticket", refundticket);
		}
		if(matchtime!=-1){
			map.put("matchtime", decf2.format(matchtime));
		}
		return map;
	}

	/**
	 * 更新退款状态
	 * 退票中的状态，还原票库，释放位置
	 * @param ticketids
	 * 退款的车票id
	 * @param sotNewStatus
	 * 车票的新状态： 1 退票中   3 已退票成功
	 * @param sotOldStatus
	 * 0 正常
	 * @param orderNewStatus
	 * 0未取票 1已取票 2退款中 3已退款 4.已取消 5.拒绝退款
	 * @return
	 * @throws Exception
	 */
	public int updateRefundOrder(String[] ticketids,Integer sotNewStatus,Integer sotOldStatus,
			Integer orderNewStatus,String statusname,Integer paystatus,
			String paystatusname,Customer c,boolean ischeck,Integer makesort) throws Exception{
		SaleOrder order = null;
		int i = 0;
		List<SaleOrderTicket> tickets = new ArrayList<SaleOrderTicket>();
		int refunddiscount = 0;
		int refundcount = 0;
		for (String id : ticketids) {
			SaleOrderTicket sot = sotdao.find(id);
			if(sot.getIsdiscount()!=null&&sot.getIsdiscount()==1){
				refunddiscount++;	
			}
			refundcount++;
			Assert.notNull(sot,"没有找到车票！");
			Assert.isTrue(sot.getPaystatus()==1,"该订单未支付，不能退款！");
			if(i==0){
				order = saleOrderDao.find(sot.getSoid());
			}
			
			if(order.getRank()!=null&&order.getRank()==2){
				Assert.isTrue(sotdao.updateRefundStatus(id, 3, 0,1,"已支付")==1,"退款失败！");
			}else{
				Assert.isTrue(sotdao.updateRefundStatus(id, sotNewStatus, sotOldStatus,paystatus,paystatusname)==1,"退款失败！");
			}
			//申请退款，还原票库
			if(sotNewStatus==1){
				//释放位置失败
				Seat seat = seatdao.find(sot.getSeatid());
				if(seat!=null){
					seat.setIssale(0);
					Assert.isTrue(seatdao.update(seat)==1,"释放位置失败！");
				}
			}
			i++;
			tickets.add(sot);
		}
		Assert.notNull(order,"没有找到订单！");
		//站务退款
		if(order.getRank()!=null&&order.getRank()==2){
			saleOrderDao.updateRefundStatus(order.getId(), 3, 3,"已退票",1,"已支付");
		}else{
			Assert.isTrue(order.getPayStatus()==1,"该订单未付款！");
			//自动改变订单状态，全部退款改变状态
			saleOrderDao.updateRefundStatus(order.getId(), orderNewStatus, sotNewStatus,statusname,paystatus,paystatusname);
		}
		//申请退款，还原票库
		if(sotNewStatus==1){
			//增加余票
			synchronized (order) {
				order.setQuantity(refundcount);
				order.setDiscountquantity(refunddiscount);
				returnedTicketStore(order);
			}
			//获取退款信息
			Map<String, Object> map = getRefundMoney(order, tickets, c, true,ischeck,makesort);
			RefundTicket rt  = (RefundTicket) map.get("refundticket");
			//保存退款订单
			saveRefundTicketOrder(rt);
		}
		return 1;
	}

	//保存退款订单
	public void saveRefundTicketOrder(RefundTicket ticket) throws Exception{
		List<RefundTicketDetail> details = ticket.getDetails();
		for (RefundTicketDetail detail : details) {
			Assert.isTrue(rtddao.save(detail)==1,"保存退款订单详情失败！");
		}
		Assert.isTrue(rtdao.save(ticket)==1,"保存退款订单失败！");
	}

	public SaleOrder saveSaleOrderByAdmin(Integer ticketLineID, final Customer customer, final List<Integer> seatnoList, final Integer quantity, final String ticketUsername, final String ticketMobile, final String qrcodepath) throws Exception {
		// 查询车票线路详情
		TicketLine ticketLineParam = new TicketLine();
		ticketLineParam.setId(ticketLineID);
		final TicketLine ticketLine = ticketLineDao.findUniqueTicketLine(ticketLineParam);
		Assert.notNull(ticketLine, "无效车票线路");
		final TicketStore ticketStore = ticketLine.getTicketStore();
		Assert.notNull(ticketStore, "总票库异常");
		Assert.isTrue(!(ticketStore.getIsapprove() == 2), "班次已经取消，请选择其他班次！");
		SimpleDateFormat dateTimeFromat = DateHanlder.getDateTimeFromat();
		long startdate = dateTimeFromat.parse(ticketLine.getTicketdate() + " " + ticketLine.getStarttime()).getTime();
		if (startdate < System.currentTimeMillis() + 1000 * 60 * (tc.getTicketTime() - 1)) {
			throw new BaseException("订单超时请提前" + tc.getTicketTime() + "分钟下单", 6000);
		}
		final Long seatNum = seatdao.checkExistsByLmidDateShiftCode(ticketLine.getLmid(), ticketLine.getTicketdate(), ticketLine.getShiftcode());
		Assert.isTrue(seatNum <= 0 || (seatnoList != null && seatnoList.size() > 0 && seatnoList.size() == quantity), "无效座位号");
		Assert.isTrue(seatnoList == null || seatnoList.size() == 0 || (seatNum > 0 && seatnoList.size() == quantity), "无效座位号");

		SaleOrder so = new SaleOrder();
		return makeConfService.findMakeConf(so, 7, new Execute<SaleOrder, SaleOrder>() {
			@Override
			public SaleOrder execute(MakeConf mc, SaleOrder so, String id) throws Exception {
				String prefix = makeConfService.findMakeConf(Const.SALEORDER_PREFIX_KEY, null,
						Const.SALEORDER_PREFIX_KEY_INITVAL, false, new Execute<String, String>() {
							public String execute(MakeConf mc, String o, String id) throws Exception {
								return id;
							}
						});
				DecimalFormat dcf = new DecimalFormat("0.0");
				so.setId(prefix + id);
				so.setCID(customer.getCid());
				so.setCName(customer.getCname());
				so.setMobile(customer.getMobile());
				so.setRideDate(ticketLine.getTicketdate());
				so.setLmid(ticketLine.getLmid());
				so.setLineName(ticketLine.getLinename());
				so.setSTArriveID(ticketLine.getStarriveid());
				so.setSTArriveName(ticketLine.getStarrivename());
				so.setSTStartID(ticketLine.getStstartid());
				so.setSTStartName(ticketLine.getStstartname());
				so.setStartTime(ticketLine.getStarttime());
				so.setArriveTime(ticketLine.getArrivetime());
				so.setShiftNum(ticketLine.getShiftcode());
				so.setPrice(ticketLine.getTicketprice());
				so.setQuantity(quantity);

				so.setLName(ticketUsername);
				so.setLMobile(ticketMobile);
				so.setIDCode("");
				so.setVprice(ticketLine.getFavprice());
				so.setMileage(ticketLine.getMileage());
				so.setRank(2);
				so.setTotalSum(ticketLine.getTicketprice().multiply(new BigDecimal(quantity)));
				so.setIssett(0);
				so.setActualSum(so.getTotalSum());
				so.setBalancePay(new BigDecimal(0));
				so.setCouponsSum(new BigDecimal(0));
				so.setOtherPay(so.getTotalSum());
				so.setStatus(0);
				so.setPayStatus(2);
				so.setPayStatusName("已锁定");
				so.setStatusName("未取票");
				so.setMakedate(DateHanlder.getCurrentDateTime());
				so.setBuyWay("web");
				so.setDiscountquantity(0);
				so.setIsabnormal(0);
				so.setActualSum(new BigDecimal(dcf.format(so.getActualSum())));
				so.setPayMode("ZW");
				so.setTicketCode(NumberCreate.createTicketCode(so.getMobile()));
				so.setOutcode(so.getId());
				so.setCodeurl(Const.QRCODE_PATH + MatrixToImageWriter.write(qrcodepath, so.getTicketCode(), 360, 360));
				Assert.isTrue(saleOrderDao.save(so) == 1, "保存订单失败");
				//Assert.isTrue(tlsdao.updateQuantityByOrder(so) == 1, "锁定站点票库失败");
				int seatNumFromFreeze = saveSaleOrderTicketByAdmin(so, seatnoList, quantity);
				if(seatNum > 0){
					Assert.isTrue(ticketStoreDao.updateQuantityByAdminWithSeat(ticketStore.getId(), so.getQuantity()-seatNumFromFreeze, seatNumFromFreeze) == 1, "锁定总票库失败");
					so.setQuantity(so.getQuantity()-seatNumFromFreeze);
					Assert.isTrue(tlsdao.updateQuantityByOrder(so) == 1, "锁定站点票库失败");
				}else{
					TicketStore newTicketStore = ticketStoreDao.findForUpdate(ticketStore.getId());
					Assert.isTrue(ticketStoreDao.updateQuantityByAdminWithoutSeat(ticketStore.getId(), so.getQuantity()) == 1, "锁定总票库失败");
					if(newTicketStore.getBalanceQuantity() > 0){
						so.setQuantity(so.getQuantity() > newTicketStore.getBalanceQuantity() ? newTicketStore.getBalanceQuantity() : so.getQuantity());
						Assert.isTrue(tlsdao.updateQuantityByOrder(so) == 1, "锁定站点票库失败");
					}
				}
				return so;
			}

		});
	}

	public int saveSaleOrderTicketByAdmin(SaleOrder saleOrder, List<Integer> seatnoList, Integer quantity) throws Exception {
		int seatNumFromFreeze = 0;
		for (int i = 1; i <= quantity; i++) {
			SaleOrderTicket saleOrderTicket = new SaleOrderTicket();
			if(seatnoList != null && seatnoList.size() > 0){
				Seat seat = seatdao.findSeat(seatnoList.get(i-1), saleOrder.getLmid().toString(), saleOrder.getShiftNum(), saleOrder.getRideDate());
				if(seat.getIssale() == 0){
					Assert.isTrue(seatdao.lockSeat(seat.getSeatno(), saleOrder.getLmid().toString(), saleOrder.getShiftNum(), saleOrder.getRideDate()) == 1, "锁定座位失败");
				}else{
					Assert.isTrue(seatdao.lockSeatFromFreeze(seat.getSeatno(), saleOrder.getLmid().toString(), saleOrder.getShiftNum(), saleOrder.getRideDate()) == 1, "锁定座位失败");
					seatNumFromFreeze++;
				}
				saleOrderTicket.setSeatno(seat.getSeatno());
				saleOrderTicket.setSeatid(seat.getId());
			}else{
				saleOrderTicket.setSeatno(0);
			}
			if(i <= saleOrder.getDiscountquantity()){
				saleOrderTicket.setIsdiscount(1);
				saleOrderTicket.setVprice(saleOrder.getVprice());
			}else{
				saleOrderTicket.setIsdiscount(0);
				saleOrderTicket.setVprice(saleOrder.getPrice());
			}
			saleOrderTicket.setLmid(saleOrder.getLmid());
			saleOrderTicket.setCheckcode(saleOrder.getId() + NumberCreate.createNumber(i, 3));
			saleOrderTicket.setSoid(saleOrder.getId());
			saleOrderTicket.setTicketcode(saleOrder.getTicketCode());
			saleOrderTicket.setLinename(saleOrder.getLinename());
			saleOrderTicket.setShiftnum(saleOrder.getShiftNum());
			saleOrderTicket.setStarttime(saleOrder.getStartTime());
			saleOrderTicket.setRidedate(saleOrder.getRideDate());
			saleOrderTicket.setPrice(saleOrder.getPrice());
			saleOrderTicket.setStarrivename(saleOrder.getStarrivename());
			saleOrderTicket.setStarriveid(saleOrder.getStarriveid());
			saleOrderTicket.setStstartid(saleOrder.getSTStartID());
			saleOrderTicket.setStstartname(saleOrder.getStstartname());
			saleOrderTicket.setPaystatus(2);
			Assert.isTrue(sotservice.save(saleOrderTicket) == 1, "保存订单详情失败");
		}
		return seatNumFromFreeze;
	}

	public List findSaleOrderListForAdmin(String keyword, Integer type, String rideDate, String makeDate, Page page){
		page.setTotalCount(saleOrderDao.findSaleOrderTotalForAdmin(keyword, type, rideDate, makeDate));
		return  saleOrderDao.findSaleOrderListForAdmin(keyword, type, rideDate, makeDate, page);
	}

	public void updateForPaylockTicket(Admin admin, String id, List<String> checkcodeList, Integer isLock) throws Exception {
		SaleOrder saleOrder = saleOrderDao.find(id);
		Assert.notNull(saleOrder, "无效订单");
		Assert.isTrue(saleOrderDao.payLockSaleOrder(id, admin.getUserID(), admin.getRealName(), DateUtil.formatDateTime(new Date())) == 1, "更新订单状态失败");
		Assert.isTrue(sotdao.payLockSaleOrderTicketByCheckCode(id, checkcodeList) == checkcodeList.size(), "更新订单详情状态失败");
		if(checkcodeList.size() < saleOrder.getQuantity()){
			List<Integer> seatnoList = new ArrayList<Integer>();
			Assert.isTrue(sotdao.cancelLockSaleOrderTicketByCheckCode(id, checkcodeList) == saleOrder.getQuantity() - checkcodeList.size(), "更新订单详情状态失败");
			List<SaleOrderTicket> saleOrderTicketList = sotdao.findSaleOrderTicketListWithoutCheckCode(id, checkcodeList);
			for(SaleOrderTicket saleOrderTicket : saleOrderTicketList){
				if(saleOrderTicket.getSeatid() != null&&saleOrderTicket.getSeatid()!=0){
					seatnoList.add(saleOrderTicket.getSeatno());
					Seat seat = new Seat();
					seat.setId(saleOrderTicket.getSeatid());
					seat.setIssale(isLock == 1 ? 2 : 0);
					Assert.isTrue(seatdao.updateUseSeat(seat)==1,"恢复座位失败");
				}
			}
			TicketStore ticketStore = ticketStoreDao.findBySaleOrder(saleOrder);
			if(isLock == 1){
				Assert.isTrue(ticketStoreDao.addQuantityToLock(ticketStore.getId(), saleOrder.getQuantity() - checkcodeList.size()) == 1, "同步总票库失败");
				LockTicketLog lockTicketLog = new LockTicketLog();
				lockTicketLog.setType(1);
				lockTicketLog.setUserid(admin.getUserID());
				lockTicketLog.setUsername(admin.getRealName());
				lockTicketLog.setLmid(saleOrder.getLmid());
				lockTicketLog.setLinename(saleOrder.getLinename());
				lockTicketLog.setShiftnum(saleOrder.getShiftNum());
				lockTicketLog.setRidedate(saleOrder.getRideDate());
				lockTicketLog.setQuantity(saleOrder.getQuantity() - checkcodeList.size());
				lockTicketLog.setSeats(CollectionUtil.listToString(seatnoList, "|"));
				lockTicketLog.setMakedate(DateUtil.formatDateTime(new Date()));
				Assert.isTrue(lockTicketLogDao.saveLockTicketLog(lockTicketLog) == 1, "记录流水失败");
			}else{
				TicketLineStore ticketLineStore = tlsdao.findBySaleOrder(saleOrder);
				Assert.isTrue(ticketStoreDao.addQuantity(ticketStore.getId(), saleOrder.getQuantity() - checkcodeList.size(), 0) == 1, "同步总票库失败");
				Assert.isTrue(tlsdao.addQuantity(ticketLineStore.getId(), saleOrder.getQuantity() - checkcodeList.size(), 0) == 1, "同步站点票库失败");
			}
		}
	}

	public void updateForCancelLockTicket(Admin admin, String id, Integer isLock) throws Exception {
		SaleOrder saleOrder = saleOrderDao.find(id);
		Assert.notNull(saleOrder, "无效订单");
		Assert.isTrue(saleOrderDao.cancelLockSaleOrder(id, admin.getUserID(), admin.getRealName(), DateUtil.formatDateTime(new Date())) == 1, "更新订单状态失败");
		Assert.isTrue(sotdao.cancelLockSaleOrderTicketByCheckCode(id, null) == saleOrder.getQuantity(), "更新订单详情状态失败");
		List<Integer> seatnoList = new ArrayList<Integer>();
		List<SaleOrderTicket> saleOrderTicketList = sotdao.findSaleOrderTicketListWithoutCheckCode (id, null);
		for(SaleOrderTicket saleOrderTicket : saleOrderTicketList){
			if(saleOrderTicket.getSeatid() != null){
				seatnoList.add(saleOrderTicket.getSeatno());
				Seat seat = new Seat();
				seat.setId(saleOrderTicket.getSeatid());
				seat.setIssale(isLock == 1 ? 2 : 0);
				Assert.isTrue(seatdao.updateUseSeat(seat)==1,"恢复座位失败");
			}
		}
		TicketStore ticketStore = ticketStoreDao.findBySaleOrder(saleOrder);
		if(isLock == 1){
			Assert.isTrue(ticketStoreDao.addQuantityToLock(ticketStore.getId(), saleOrder.getQuantity()) == 1, "同步总票库失败");
			LockTicketLog lockTicketLog = new LockTicketLog();
			lockTicketLog.setType(1);
			lockTicketLog.setUserid(admin.getUserID());
			lockTicketLog.setUsername(admin.getRealName());
			lockTicketLog.setLmid(saleOrder.getLmid());
			lockTicketLog.setLinename(saleOrder.getLinename());
			lockTicketLog.setShiftnum(saleOrder.getShiftNum());
			lockTicketLog.setRidedate(saleOrder.getRideDate());
			lockTicketLog.setQuantity(saleOrder.getQuantity());
			lockTicketLog.setSeats(CollectionUtil.listToString(seatnoList, "|"));
			lockTicketLog.setMakedate(DateUtil.formatDateTime(new Date()));
			Assert.isTrue(lockTicketLogDao.saveLockTicketLog(lockTicketLog) == 1, "记录流水失败");
		}else{
			TicketLineStore ticketLineStore = tlsdao.findBySaleOrder(saleOrder);
			Assert.isTrue(ticketStoreDao.addQuantity(ticketStore.getId(), saleOrder.getQuantity(), 0) == 1, "同步总票库失败");
			Assert.isTrue(tlsdao.addQuantity(ticketLineStore.getId(), saleOrder.getQuantity(), 0) == 1, "同步站点票库失败");
		}
	}

	//站务整单退票
	public int saleOrderRefund(Map a){
		return saleOrderDao.saleOrderRefund(a);
	}
	
	//站务收款
	public int saleOrderReceive(Map a){
		return saleOrderDao.saleOrderReceive(a);
	}

	public void updateForFreezeTicket(Integer ticketLineID, List<Integer> seatnoList, Integer quantity, Admin admin) throws Exception {
		// 查询车票线路详情
		TicketLine ticketLineParam = new TicketLine();
		ticketLineParam.setId(ticketLineID);
		TicketLine ticketLine = ticketLineDao.findUniqueTicketLine(ticketLineParam);
		Assert.notNull(ticketLine, "无效车票线路");
		TicketStore ticketStore = ticketLine.getTicketStore();
		Assert.notNull(ticketStore, "总票库异常");
		Assert.isTrue(!(ticketStore.getIsapprove() == 2), "班次已经取消，请选择其他班次！");
		Long seatNum = seatdao.checkExistsByLmidDateShiftCode(ticketLine.getLmid(), ticketLine.getTicketdate(), ticketLine.getShiftcode());
		Assert.isTrue(seatNum <= 0 || (seatnoList != null && seatnoList.size() > 0 && seatnoList.size() == quantity), "无效座位号");
		Assert.isTrue(seatnoList == null || seatnoList.size() == 0 || (seatNum > 0 && seatnoList.size() == quantity), "无效座位号");

		Assert.isTrue(ticketStoreDao.updateQuantityForFreeze(ticketStore.getId(), quantity) == 1, "锁定总票库失败");
		if(null != seatnoList && seatnoList.size() > 0){
			for (int i = 1; i <= seatnoList.size(); i++) {
				Assert.isTrue(seatdao.freezeSeat(seatnoList.get(i-1), ticketLine.getLmid().toString(), ticketLine.getShiftcode(), ticketLine.getTicketdate()) == 1, "锁定座位失败");
			}
		}
		LockTicketLog lockTicketLog = new LockTicketLog();
		lockTicketLog.setType(1);
		lockTicketLog.setUserid(admin.getUserID());
		lockTicketLog.setUsername(admin.getRealName());
		lockTicketLog.setLmid(ticketLine.getLmid());
		lockTicketLog.setLinename(ticketLine.getLinename());
		lockTicketLog.setShiftnum(ticketLine.getShiftcode());
		lockTicketLog.setRidedate(ticketLine.getTicketdate());
		lockTicketLog.setQuantity(quantity);
		lockTicketLog.setSeats(CollectionUtil.listToString(seatnoList, "|"));
		lockTicketLog.setMakedate(DateUtil.formatDateTime(new Date()));
		Assert.isTrue(lockTicketLogDao.saveLockTicketLog(lockTicketLog) == 1, "记录流水失败");
	}

	public void updateForUnfreezeTicket(Integer ticketLineID, List<Integer> seatnoList, Integer quantity, Admin admin) throws Exception {
		// 查询车票线路详情
		TicketLine ticketLineParam = new TicketLine();
		ticketLineParam.setId(ticketLineID);
		TicketLine ticketLine = ticketLineDao.findUniqueTicketLine(ticketLineParam);
		Assert.notNull(ticketLine, "无效车票线路");
		TicketStore ticketStore = ticketLine.getTicketStore();
		Assert.notNull(ticketStore, "总票库异常");
		Assert.isTrue(!(ticketStore.getIsapprove() == 2), "班次已经取消，请选择其他班次！");
		Long seatNum = seatdao.checkExistsByLmidDateShiftCode(ticketLine.getLmid(), ticketLine.getTicketdate(), ticketLine.getShiftcode());
		Assert.isTrue(seatNum <= 0 || (seatnoList != null && seatnoList.size() > 0 && seatnoList.size() == quantity), "无效座位号");
		Assert.isTrue(seatnoList == null || seatnoList.size() == 0 || (seatNum > 0 && seatnoList.size() == quantity), "无效座位号");

		Assert.isTrue(ticketStoreDao.updateQuantityForUnfreeze(ticketStore.getId(), quantity) == 1, "解锁总票库失败");
		if(null != seatnoList && seatnoList.size() > 0){
			for (int i = 1; i <= seatnoList.size(); i++) {
				Assert.isTrue(seatdao.unfreezeSeat(seatnoList.get(i-1), ticketLine.getLmid().toString(), ticketLine.getShiftcode(), ticketLine.getTicketdate()) == 1, "解锁座位失败");
			}
		}
		LockTicketLog lockTicketLog = new LockTicketLog();
		lockTicketLog.setType(2);
		lockTicketLog.setUserid(admin.getUserID());
		lockTicketLog.setUsername(admin.getRealName());
		lockTicketLog.setLmid(ticketLine.getLmid());
		lockTicketLog.setLinename(ticketLine.getLinename());
		lockTicketLog.setShiftnum(ticketLine.getShiftcode());
		lockTicketLog.setRidedate(ticketLine.getTicketdate());
		lockTicketLog.setQuantity(quantity);
		lockTicketLog.setSeats(CollectionUtil.listToString(seatnoList, "|"));
		lockTicketLog.setMakedate(DateUtil.formatDateTime(new Date()));
		Assert.isTrue(lockTicketLogDao.saveLockTicketLog(lockTicketLog) == 1, "记录流水失败");
	}

	public void updateDateOutOrder() throws Exception {
		saleOrderDao.updateDateOutOrder();
	}

	public List findLockTicketLogList(String keyword, Integer type, String rideDate, String makeDate, Page page){
		page.setTotalCount(lockTicketLogDao.findLockTicketLogTotal(keyword, type, rideDate, makeDate));
		return  lockTicketLogDao.findLockTicketLogList(keyword, type, rideDate, makeDate, page);
	}

	public List<Map> findMiles(String cid){
		return saleOrderDao.findMiles(cid);
	}
}
