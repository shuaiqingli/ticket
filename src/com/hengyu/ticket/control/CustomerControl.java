package com.hengyu.ticket.control;

import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.CustomerService;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.ExcelHanlder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 顾客管理
 */
@Controller
@RequestMapping("/admin")
public class CustomerControl {

    @Autowired
    private CustomerService customerService;

    //顾客列表
    @RequestMapping("customerList.do")
    public String customerList(Page page, Customer customer, Model m) {
        m.addAttribute("customerList", customerService.findCustomerList(customer, page));
        m.addAttribute(page);
        return "admin/customerList";
    }

    @RequestMapping("customerListExport.do")
    public void customerListExport(Customer customer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Page page = new Page();
        page.setPageSize(1000000);
        List dataList = customerService.findCustomerList(customer, page);
        String[] titles = new String[]{"客户ID", "客户名称", "手机", "余额", "里程", "积分", "级别", "有效订单数", "注册日期"};
        String[] columns = new String[]{"CID", "CName", "Mobile", "Money", "Mileage", "Integral", "Rank", "OrderTotal", "MakeDate"};
        XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "客户统计", dataList, null);
        String name = "客户统计.xlsx";
        byte[] bytes = request.getHeader("User-Agent").contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
        name = new String(bytes, "ISO-8859-1");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-type", " application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        book.write(response.getOutputStream());
    }

    //顾客级别更新
    @ResponseBody
    @RequestMapping("customerRankEdit.do")
    public String customerRankEdit(Customer customer) {
        Assert.hasText(customer.getCid(), "ID不能为空");
        Assert.isTrue(CollectionUtil.contain(new Integer[]{0, 1, 2}, customer.getRank()), "无效级别");
        customerService.updateCustomerRank(customer);
        return "1";
    }

    //绑定管理员
    @ResponseBody
    @RequestMapping("bindAdminToCustomer.do")
    public String bindAdminToCustomer(String userid, String cid) throws Exception {
        Assert.hasText(userid, "管理员不能为空");
        Assert.hasText(cid, "顾客不能为空");
        customerService.updateCustomerForBindAdmin(userid, cid);
        return "1";
    }

    @RequestMapping("selectCustomer.do")
    public String selectCustomer(Page page, String keyword, Model m) {
        m.addAttribute("customerList", customerService.findCustomerListForSelect(keyword, page));
        m.addAttribute(page);
        return "admin/selectCustomer";
    }

    //顾客详情
    @RequestMapping("customerDetail.do")
    public String customerDetail(String cid, Model m) throws Exception {
        Assert.hasText(cid, "ID不能为空");
        m.addAllAttributes(customerService.findCustomerDetail(cid));
        return "admin/customerDetail";
    }

}
