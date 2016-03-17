package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.StatisticService;
import com.hengyu.ticket.util.ExcelHanlder;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/user")
public class StatisticControl {

    @Autowired
    private StatisticService statisticService;
    @Autowired
    private CityStationService cityStationService;

    @RequestMapping("stationStatistic.do")
    public String stationStatistic(String stationid, String startDate, String endDate, String keyword, Page page, Model m, HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
        List<Map> stationList = cityStationService.findListByUserid(admin.getUserID());
        if (stationList != null && stationList.size() > 0) {
            m.addAttribute("stationList", stationList);
            List<String> stationidList = new ArrayList<String>();
            boolean isStationMatch = false;
            for (Map station : stationList) {
                if (StringUtils.isBlank(stationid)) {
                    stationidList.add((String) station.get("ID"));
                } else {
                    if (stationid.equals(station.get("ID"))) isStationMatch = true;
                }
            }
            if (StringUtils.isNotBlank(stationid)) {
                Assert.isTrue(isStationMatch, "无效站点");
                stationidList.add(stationid);
            }
            m.addAttribute("dataList", statisticService.stationStatistic(stationidList, StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate, keyword, page));
        }
        m.addAttribute("page", page);
        return "user/stationStatistic";
    }

    @RequestMapping("stationStatisticExport.do")
    public void stationStatisticExport(String stationid, String startDate, String endDate, String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Page page = new Page();
        page.setPageSize(1000000);
        Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
        List<Map> stationList = cityStationService.findListByUserid(admin.getUserID());
        List dataList = new ArrayList();
        if (stationList != null && stationList.size() > 0) {
            List<String> stationidList = new ArrayList<String>();
            boolean isStationMatch = false;
            for (Map station : stationList) {
                if (StringUtils.isBlank(stationid)) {
                    stationidList.add((String) station.get("ID"));
                } else {
                    if (stationid.equals(station.get("ID"))) isStationMatch = true;
                }
            }
            if (StringUtils.isNotBlank(stationid)) {
                Assert.isTrue(isStationMatch, "无效站点");
                stationidList.add(stationid);
            }
            dataList = statisticService.stationStatistic(stationidList, StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate, keyword, page);
        }
        String[] titles = new String[]{"班次号", "日期 ", "所属线路", "站点名称", "托运行李件数", "托运行李金额", "乘客超重行李件数", "乘客超重行李金额", "已卖票数"};
        String[] columns = new String[]{"ShiftCode", "RideDate", "LineName", "CurrStationName", "ConsignQuantity", "ConsignSum", "PassengerQuantity", "PassengerSum", "Total"};
        XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "站点统计", dataList, null);
        String name = "站点统计.xlsx";
        byte[] bytes = request.getHeader("User-Agent").contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
        name = new String(bytes, "ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-type", " application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        book.write(response.getOutputStream());
    }

    @RequestMapping("driverStatistic.do")
    public String driverStatistic(String startDate, String endDate, String keyword, Page page, Model m) {
        m.addAttribute("dataList", statisticService.driverStatistic(StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate, keyword, page));
        m.addAttribute("page", page);
        return "user/driverStatistic";
    }

    @RequestMapping("driverStatisticExport.do")
    public void driverStatisticExport(String startDate, String endDate, String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Page page = new Page();
        page.setPageSize(1000000);
        List dataList = statisticService.driverStatistic(StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate, keyword, page);
        String[] titles = new String[]{"司机编号", "司机名称", "班次号", "日期", "线路名称", "人数"};
        String[] columns = new String[]{"CommonDriverID", "CommonDriverName", "ShiftCode", "RideDate", "LineName", "PeopleTotal"};
        XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "司机统计", dataList, null);
        String name = "司机统计.xlsx";
        byte[] bytes = request.getHeader("User-Agent").contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
        name = new String(bytes, "ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-type", " application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        book.write(response.getOutputStream());
    }

    @RequestMapping("billStatistic.do")
    public String billStatistic(String startDate, String endDate, String keyword, Page page, Model m) {
        m.addAttribute("dataList", statisticService.billStatistic(StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate, StringUtils.isBlank(keyword) ? null : Arrays.asList(keyword.split("\\|")), page));
        m.addAttribute("page", page);
        return "user/billStatistic";
    }

    @RequestMapping("billStatisticExport.do")
    public void billStatisticExport(String startDate, String endDate, String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Page page = new Page();
        page.setPageSize(1000000);
        List dataList = statisticService.billStatistic(StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate, StringUtils.isBlank(keyword) ? null : Arrays.asList(keyword.split("\\|")), page);
        String[] titles = new String[]{"出发日期", "线路ID", "线路名称", "线路编码", "站点ID", "站点名称", "原价金额", "原价数量", "优惠价金额", "优惠价数量", "总数量", "代金券总金额", "代金券数量", "实收总金额", "退票数量", "退票总金额"};
        String[] columns = new String[]{"RideDate", "LMID", "LineName", "ShiftNum", "STStartID", "STStartName", "Price", "PriceNum", "VPrice", "VPriceNum", "PriceNumTotal", "CouponSum", "CouponTotal", "ActualSum", "RefundNum", "RefundActualSum"};
        XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "对账统计", dataList, null);
        String name = "对账统计.xlsx";
        byte[] bytes = request.getHeader("User-Agent").contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
        name = new String(bytes, "ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-type", " application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        book.write(response.getOutputStream());
    }

    @RequestMapping("integralProductStatistic.do")
    public String integralProductStatistic(String startDate, String endDate, Model m) {
        m.addAllAttributes(statisticService.integralProductStatistic(StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate));
        return "user/integralProductStatistic";
    }

    @RequestMapping("integralProductStatisticExport.do")
    public void integralProductStatisticExport(String startDate, String endDate, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List dataList = statisticService.integralProductStatisticForExport(StringUtils.isBlank(startDate) ? null : startDate, StringUtils.isBlank(endDate) ? null : endDate);
        String[] titles = new String[]{"客户ID", "客户名称", "类型", "产品名称", "积分值", "数量", "日期"};
        String[] columns = new String[]{"CustomerID", "CustomerName", "Type", "ProductName", "Integral", "Count", "MakeDate"};
        XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "积分流水统计", dataList, null);
        String name = "积分流水统计.xlsx";
        byte[] bytes = request.getHeader("User-Agent").contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
        name = new String(bytes, "ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-type", " application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        book.write(response.getOutputStream());
    }
}
