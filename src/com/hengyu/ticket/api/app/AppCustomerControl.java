package com.hengyu.ticket.api.app;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.*;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.SecurityHanlder;
import com.hengyu.ticket.util.WeixinHanlder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = {"/app/api/customer", "/web/api/customer"})
@SuppressWarnings("unchecked")
public class AppCustomerControl {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CouponsService couponsService;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private SaleOrderService saleOrderService;

    //获取用户最新信息
    @RequestMapping("info")
    public void info(String openid, Writer w, HttpServletRequest req) throws Exception {
        Customer c = customerService.find(req.getAttribute("cid").toString());
        Integer coupons = couponsService.findCountByCustomerId(c.getCid());
        c.setCoupons(coupons);
        //获取微信用户信息

        try {
            boolean isupdate = false;
            if (StringUtils.isEmpty(c.getOpenid())||c.getOpenid().equals("null")||c.getOpenid().trim().length()<10) {
            	if(openid!=null&&openid.trim().length()>10){
            		c.setOpenid(openid);
            		isupdate = true;
            	}
            }
            if (StringUtils.isNotEmpty(openid)) {
                Map<String, String> info = WeixinHanlder.getUserInfoByUnionId(req, openid);
                if (info != null && !StringUtils.isNotEmpty(c.getCname())) {
                	String name = WeixinHanlder.formatNickName(info.get("nickname"), null);
                	c.setCname(name);
                    isupdate = true;
                }
                if (info != null && !StringUtils.isNotEmpty(c.getHeadphoto())) {
                    c.setHeadphoto(info.get("headimgurl"));
                    isupdate = true;
                }
            }
            if (isupdate) {
            	if(c.getOpenid()!=null&&c.getOpenid().length()<10){
            		c.setOpenid(null);
            	}
                customerService.update(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        API api = new API();
        api.getDatas().add(c);
        APIUtil.toJSON(api, w);
    }

    //更新用户信息
    @RequestMapping(value = {"updateInfo", "updatePassword"})
    public void updateCustomer(String cname, String newpassword, String oldpassword, Writer w, HttpServletRequest req) throws Exception {
        Customer customer = customerService.find(req.getAttribute("cid").toString());
        API api = new API();
        int result = -1;
        //修改昵称
        if (StringUtils.isNotEmpty(cname)) {
            customer.setCname(cname);
            result = customerService.update(customer);
        }
        //修改密码
        else if (StringUtils.isNotEmpty(newpassword) && StringUtils.isNotEmpty(oldpassword)) {
            if (customer.getPassword().equals(SecurityHanlder.md5(oldpassword))) {
                customer.setPassword(SecurityHanlder.md5(newpassword));
                customer.setToken(SecurityHanlder.md5(System.currentTimeMillis() / 1000 + customer.getMobile()));
                result = customerService.update(customer);
                if(req.getServletPath().contains("web/api/customer")){
                    req.getSession().invalidate();
                }
            } else {
                api.setCode(5014);
            }
        }
        //error
        else {
            api.setCode(5000);
        }
        api.getDatas().add(result);
        APIUtil.toJSON(api, w);
    }

    //优惠劵列表
    @RequestMapping("coupons")
    public void coupons(Writer w, HttpServletRequest req) throws Exception {
        List<Coupons> cs = couponsService.findByCustomerId(req.getAttribute("cid").toString());
        APIUtil.APIToJSON(cs, w);
    }

    //积分商品列表
    @RequestMapping("integralProducts")
    public void integralProducts(Page page, Writer w, HttpServletRequest request) throws Exception {
        Customer oldCustomer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        request.getSession().getAttribute(Const.LOGIN_CUSTOMER);
        Customer customer = customerService.find(oldCustomer.getCid());
        API api = new API();
        api.getDatas().add(customer.getIntegral() == null ? 0 : customer.getIntegral());
        api.getDatas().add(integralService.findIntegralProductList(null, page, true));
        api.setPage(page);
        APIUtil.toJSON(api, w);
    }

    //兑换积分商品
    @RequestMapping("buyIntegralProduct")
    public void buyIntegralProduct(Integer id, Writer w, HttpServletRequest request) throws Exception {
        Assert.notNull(id, "ID不能为空");
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        integralService.updateForBuyIntegralProduct(customer.getCid(), id);
        APIUtil.APIToJSON(w, "success");
    }

    //留言反馈列表
    @RequestMapping("feedbacks")
    public void feedbacks(Page page, Writer w, HttpServletRequest request) {
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        Feedback feedback = new Feedback();
        feedback.setCid(customer.getCid());
        API api = new API();
        api.setDatas(feedbackService.findFeedbackList(feedback, page));
        api.setPage(page);
        APIUtil.toJSON(api, w);
    }

    //留言反馈详情
    @RequestMapping("feedbackDetail")
    public void feedbackDetail(Integer id, Writer w, HttpServletRequest request) {
        Assert.notNull(id, "ID不能为空");
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        Feedback feedback = feedbackService.findFeedback(id);
        Assert.isTrue(feedback.getCid().equals(customer.getCid()), "无效问题");
        List<FeedbackDetail> feedbackDetailList = feedback.getFeedbackDetailList();
        Integer lastIndex = feedbackDetailList.get(feedbackDetailList.size() - 1).getIndex();
        if (feedback.getLastindexforadmin() < lastIndex) {
            feedback.setLastindexforadmin(lastIndex);
            feedbackService.updateFeedback(feedback);
        }
        APIUtil.APIToJSON(w, feedback);
    }

    //新建留言反馈
    @RequestMapping("feedbackAdd")
    public void feedbackAdd(Feedback feedback, Writer w, HttpServletRequest request) {
        Assert.isTrue(CollectionUtil.contain(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, feedback.getType()), "无效类型");
        Assert.hasText(feedback.getContent(), "内容不能为空");
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        feedback.setCid(customer.getCid());
        feedback.setCname(customer.getMobile());
        feedback.setLastindexforadmin(0);
        feedback.setLastindexforcustomer(0);
        feedback.setMobile(customer.getMobile());
        feedback.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        feedbackService.addFeedback(feedback);
        APIUtil.APIToJSON(w, "success");
    }

    //留言反馈回复
    @RequestMapping("feedbackReply")
    public void feedbackReply(FeedbackDetail feedbackDetail, Writer w, HttpServletRequest request) {
        Assert.notNull(feedbackDetail.getFeedbackid(), "ID不能为空");
        Assert.hasText(feedbackDetail.getContent(), "内容不能为空");
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        feedbackDetail.setType(1);
        feedbackDetail.setReplyid(customer.getCid());
        feedbackDetail.setReplyname(customer.getMobile());
        feedbackDetail.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        feedbackService.addFeedbackDetail(feedbackDetail);
        APIUtil.APIToJSON(w, "success");
    }
    
    //判断登录状态
    @RequestMapping("islogin")
    public void islogin(HttpServletRequest req,Writer w){
    	Object cus = req.getAttribute(Const.LOGIN_CUSTOMER);
    	API api = new API();
    	if(cus==null){
    		api.getDatas().add(0);
    	}else{
    		api.getDatas().add(1);
    	}
    	APIUtil.toJSON(api, w);
    }

    //里程数据
    @RequestMapping("mileData")
    public void mileData(Writer w, HttpServletRequest request) {
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        API api = new API();
        api.getDatas().add(saleOrderService.findMiles(customer.getCid()));
        APIUtil.toJSON(api, w);
    }

    //登出
    @RequestMapping("customerLogout")
    public void customerLogout(Writer w, HttpServletRequest request) {
        Customer customer = (Customer) request.getAttribute(Const.LOGIN_CUSTOMER);
        Assert.notNull(customer, "当前未登录");
        request.getSession().invalidate();
        APIUtil.APIToJSON(w, "success");
    }
}
