package com.hengyu.ticket.control;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.*;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/user")
public class LockTicketControl {

    @Autowired
    private CityStationService cityStationService;
    @Autowired
    private TicketLineService ticketLineService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private SaleOrderService saleOrderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private SaleOrderTicketService saleOrderTicketService;
    @Autowired
    private ShiftService shiftService;

    @RequestMapping("lockTicketManage.do")
    public String lockTicketManage(Model m) throws Exception {
        m.addAttribute("cityList", cityStationService.findAllCity());
        return "user/lockTicketManage";
    }

    @ResponseBody
    @RequestMapping("getStationByCityId.do")
    public String getStationByCityId(String startCityID, String endCityID) throws Exception {
        Assert.hasText(startCityID, "出发城市不能为空");
        Assert.hasText(endCityID, "到达城市不能为空");
        return JSON.toJSONString(new Object[]{
                cityStationService.getStartStationByCityid(startCityID, endCityID, 0),
                cityStationService.getStartStationByCityid(startCityID, endCityID, 1)
        });
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("getTicketLineList.do")
    public String getTicketLineList(TicketLine ticketLine) throws Exception {
        Assert.hasText(ticketLine.getCitystartid(), "出发城市不能为空");
        Assert.hasText(ticketLine.getCityarriveid(), "到达城市不能为空");
        Assert.hasText(ticketLine.getTicketdate(), "出发日期不能为空");
        List<Map> shiftList = shiftService.findShiftListForLock(CollectionUtil.objectToMap(ticketLine));
        if(null != shiftList && shiftList.size() > 0){
            Map<String, String> showcontentMap = new HashMap<String, String>();
            for(Object shift : shiftList){
                Map parsedShift = (Map) shift;
                String lmid = parsedShift.get("lmid").toString();
                String ticketdate = (String) parsedShift.get("ticketdate");
                if(null == showcontentMap.get(lmid)){
                    String showcontent = showTimeService.findShowContent(Integer.parseInt(lmid), ticketdate);
                    showcontentMap.put(lmid, showcontent==null?"":showcontent);
                }
                parsedShift.put("showcontent", showcontentMap.get(lmid));
            }
        }
        return JSON.toJSONString(shiftList);
    }

    @RequestMapping("lockTicketDetail.do")
    public String lockTicketDetail(TicketLine ticketLine, Page page, Model m) throws Exception {
        Assert.notNull(ticketLine.getId(), "ID不能为空");
        page.setParam(ticketLine);
        List ticketLineList = ticketLineService.findTicketList(page, false);
        Assert.isTrue(ticketLineList != null && ticketLineList.size() == 1, "无效班次");
        Map parsedTicketLine = (Map) ticketLineList.get(0);
        parsedTicketLine.put("showcontent", showTimeService.findShowContent(Integer.parseInt((String) parsedTicketLine.get("lmid")), (String) parsedTicketLine.get("ticketdate")));
        parsedTicketLine.put("lockedseatnolist", saleOrderTicketService.findSeatNOListForLock(parsedTicketLine.get("shiftid").toString()));
        m.addAttribute("ticketLine", parsedTicketLine);
        m.addAttribute("ticketDate", DateUtil.stringToDate((String) ((Map) ticketLineList.get(0)).get("ticketdate")));
        m.addAttribute("seatList", seatService.findSeatList(parsedTicketLine.get("shiftid").toString()));
        return "user/lockTicketDetail";
    }

    @ResponseBody
    @RequestMapping("lockTicket.do")
    public String lockTicket(Integer id, String name, String mobile, String seatnoList, Integer quantity, HttpServletRequest request) throws Exception {
        Assert.notNull(id, "ID不能为空");
        Assert.hasText(name, "姓名不能为空");
        Assert.hasText(mobile, "电话不能为空");
        List<Integer> parsedSeatnoList = new ArrayList<Integer>();
        if(StringUtils.isNotBlank(seatnoList)){
            Set<String> seatnoSet = new HashSet<String>();
            for(String seatno : seatnoList.split(",")){
                Assert.isTrue(!seatnoSet.contains(seatno), "不能包含重复座位号");
                seatnoSet.add(seatno);
                parsedSeatnoList.add(Integer.parseInt(seatno));
            }
            quantity = parsedSeatnoList.size();
        }
        Assert.isTrue(quantity > 0 && quantity <= 5, "一次购买票数为1到5张");
        Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
        Customer customer = customerService.findCustomerByAdmin(admin.getUserID());
        Assert.notNull(customer, "请先绑定站务级别顾客账号");
        saleOrderService.saveSaleOrderByAdmin(id, customer, parsedSeatnoList, quantity, name, mobile, request.getSession().getServletContext().getRealPath(Const.QRCODE_PATH));
        return "1";
    }

    @RequestMapping("lockTicketList.do")
    public String lockTicketList(Integer type, String keyword, String rideDate, String makeDate, Page page, Model m) {
        List dataList = saleOrderService.findSaleOrderListForAdmin(keyword, type, StringUtils.isBlank(rideDate)?null:rideDate, StringUtils.isBlank(makeDate)?null:makeDate, page);
        if(dataList != null && dataList.size() > 0){
            for(Object data : dataList){
                Map parsedData = (Map)data;
                parsedData.put("showcontent", showTimeService.findShowContent((Integer) parsedData.get("LMID"), (String) parsedData.get("RideDate")));
            }
        }
        m.addAttribute("dataList", dataList);
        m.addAttribute("page", page);
        return "user/lockTicketList";
    }

    @ResponseBody
    @RequestMapping("payLockTicket.do")
    public String payLockTicket(String id, String checkcodeList, Integer isLock, HttpServletRequest request) throws Exception {
        Assert.hasText(id, "ID不能为空");
        Assert.isTrue(CollectionUtil.contain(new Integer[]{0, 1}, isLock), "无效锁定选项");
        List<String> parsedCheckcodeList = new ArrayList<String>();
        if(StringUtils.isNotBlank(checkcodeList)){
            Set<String> checkcodeSet = new HashSet<String>();
            for(String checkcode : checkcodeList.split(",")){
                Assert.isTrue(!checkcodeSet.contains(checkcode), "不能包含重复验票码");
                checkcodeSet.add(checkcode);
                parsedCheckcodeList.add(checkcode);
            }
        }
        Assert.isTrue(parsedCheckcodeList.size() > 0, "验票码不能为空");
        saleOrderService.updateForPaylockTicket((Admin) request.getSession().getAttribute(Const.LOGIN_USER), id, parsedCheckcodeList, isLock);
        return "1";
    }

    @ResponseBody
    @RequestMapping("cancelLockTicket.do")
    public String cancelLockTicket(String id, Integer isLock, HttpServletRequest request) throws Exception {
        Assert.hasText(id, "ID不能为空");
        Assert.isTrue(CollectionUtil.contain(new Integer[]{0, 1}, isLock), "无效锁定选项");
        saleOrderService.updateForCancelLockTicket((Admin) request.getSession().getAttribute(Const.LOGIN_USER), id, isLock);
        return "1";
    }

    @ResponseBody
    @RequestMapping("shiftDetailForFreezeOrUnfreeze.do")
    public String shiftDetailForFreezeOrUnfreeze(Long id) throws Exception {
        Assert.notNull(id, "无效ID");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id.toString());
        List<Map> shiftList = shiftService.findShiftListForLock(param);
        Assert.isTrue(null != shiftList && shiftList.size() == 1, "无效班次");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("shift", shiftList.get(0));
        result.put("seatList", seatService.findSeatList(id.toString()));
        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("freezeTicket.do")
    public String freezeTicket(Integer id, String seatnoList, Integer quantity, HttpServletRequest request) throws Exception {
        Assert.notNull(id, "ID不能为空");
        List<Integer> parsedSeatnoList = new ArrayList<Integer>();
        if(StringUtils.isNotBlank(seatnoList)){
            Set<String> seatnoSet = new HashSet<String>();
            for(String seatno : seatnoList.split(",")){
                Assert.isTrue(!seatnoSet.contains(seatno), "不能包含重复座位号");
                seatnoSet.add(seatno);
                parsedSeatnoList.add(Integer.parseInt(seatno));
            }
            quantity = parsedSeatnoList.size();
        }
        Assert.isTrue(quantity > 0, "一次锁定票数必须大于0");
        saleOrderService.updateForFreezeTicket(id, parsedSeatnoList, quantity, (Admin) request.getSession().getAttribute(Const.LOGIN_USER));
        return "1";
    }

    @ResponseBody
    @RequestMapping("unfreezeTicket.do")
    public String unfreezeTicket(Integer id, String seatnoList, Integer quantity, HttpServletRequest request) throws Exception {
        Assert.notNull(id, "ID不能为空");
        List<Integer> parsedSeatnoList = new ArrayList<Integer>();
        if(StringUtils.isNotBlank(seatnoList)){
            Set<String> seatnoSet = new HashSet<String>();
            for(String seatno : seatnoList.split(",")){
                Assert.isTrue(!seatnoSet.contains(seatno), "不能包含重复座位号");
                seatnoSet.add(seatno);
                parsedSeatnoList.add(Integer.parseInt(seatno));
            }
            quantity = parsedSeatnoList.size();
        }
        Assert.isTrue(quantity > 0, "一次解锁票数必须大于0");
        saleOrderService.updateForUnfreezeTicket(id, parsedSeatnoList, quantity, (Admin) request.getSession().getAttribute(Const.LOGIN_USER));
        return "1";
    }

    @RequestMapping("freezeTicketList.do")
    public String freezeTicketList(Integer type, String keyword, String rideDate, String makeDate, Page page, Model m) {
        m.addAttribute("dataList", saleOrderService.findLockTicketLogList(keyword, type, StringUtils.isBlank(rideDate)?null:rideDate, StringUtils.isBlank(makeDate)?null:makeDate, page));
        m.addAttribute("page", page);
        return "user/freezeTicketList";
    }
}
