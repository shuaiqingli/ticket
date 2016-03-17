package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.CouponsDao;
import com.hengyu.ticket.dao.CustomerDao;
import com.hengyu.ticket.dao.IntegralLineDao;
import com.hengyu.ticket.dao.IntegralProductDao;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Service
public class IntegralService {

    @Autowired
    private IntegralProductDao integralProductDao;
    @Autowired
    private IntegralLineDao integralLineDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CouponsDao couponsDao;

    public List<IntegralProduct> findIntegralProductList(String keyword, Page page, boolean available) {
        page.setTotalCount(integralProductDao.findIntegralProductTotal(keyword, available));
        return integralProductDao.findIntegralProductList(keyword, page, available);
    }

    public IntegralProduct findIntegralProduct(Integer id, boolean available) {
        return integralProductDao.findIntegralProduct(id, available);
    }

    public void addIntegralProduct(IntegralProduct integralProduct) {
        Assert.isTrue(integralProductDao.saveIntegralProduct(integralProduct) == 1, "操作失败");
    }

    public void updateIntegralProduct(IntegralProduct integralProduct) {
        IntegralProduct savedIntegralProduct = integralProductDao.findIntegralProduct(integralProduct.getId(), false);
        Assert.isTrue(null != savedIntegralProduct && savedIntegralProduct.getStatus() != 2, "无效商品");
        integralProduct.setMakedate(savedIntegralProduct.getMakedate());
        Assert.isTrue(integralProductDao.updateIntegralProduct(integralProduct) == 1, "操作失败");
    }

    public void updateIntegralProductForPuton(Integer id) {
        IntegralProduct savedIntegralProduct = integralProductDao.findIntegralProduct(id, false);
        Assert.isTrue(null != savedIntegralProduct && savedIntegralProduct.getStatus() == 1, "无效商品");
        savedIntegralProduct.setStatus(2);
        Assert.isTrue(integralProductDao.updateIntegralProduct(savedIntegralProduct) == 1, "操作失败");
    }

    public void updateIntegralProductForPutoff(Integer id) {
        IntegralProduct savedIntegralProduct = integralProductDao.findIntegralProduct(id, false);
        Assert.isTrue(null != savedIntegralProduct && savedIntegralProduct.getStatus() == 2, "无效商品");
        savedIntegralProduct.setStatus(1);
        Assert.isTrue(integralProductDao.updateIntegralProduct(savedIntegralProduct) == 1, "操作失败");
    }

    public void delIntegralProduct(Integer id) {
        Assert.isTrue(integralProductDao.delIntegralProduct(id) == 1, "操作失败");
    }

    public void updateForBuyIntegralProduct(String cid, Integer productid) throws Exception {
        Customer customer = customerDao.find(cid);
        Assert.notNull(customer, "无效用户");
        IntegralProduct integralProduct = integralProductDao.findIntegralProduct(productid, true);
        Assert.notNull(integralProduct, "无效产品");
        Assert.isTrue(customer.getIntegral() != null && customer.getIntegral() >= integralProduct.getAmount(), "积分不足");
        if (integralProduct.getStockflag() == 1) {
            Assert.isTrue(integralProduct.getCurrentstock() >= 1, "库存不足");
            Assert.isTrue(integralProductDao.updateIntegralProductStock(1, productid) == 1, "同步库存失败");
        }
        customer.setIntegral(customer.getIntegral() - integralProduct.getAmount());
        Assert.isTrue(customerDao.update(customer) == 1, "同步积分失败");
        IntegralLine integralLine = new IntegralLine();
        integralLine.setOperatorid(integralProduct.getId().toString());
        integralLine.setProductname(integralProduct.getName());
        integralLine.setAmount(integralProduct.getAmount());
        integralLine.setCount(1);
        integralLine.setIntegral(integralProduct.getAmount());
        integralLine.setType(3);
        integralLine.setCustomerid(customer.getCid());
        integralLine.setCustomername(customer.getCname());
        integralLine.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Assert.isTrue(integralLineDao.saveIntegralLine(integralLine) == 1, "记录积分流水失败");
        Coupons coupons = new Coupons();
        coupons.setVouchercode(Long.toHexString(Long.valueOf(DateUtil.getCurrentLongDateTimeString())).toUpperCase());
        coupons.setVouchername(integralProduct.getVprice() + "元优惠券");
        coupons.setMemo("积分兑换");
        coupons.setCid(customer.getCid());
        coupons.setCname(customer.getCname());
        coupons.setBegindate(DateUtil.getCurrentDateString());
        coupons.setEnddate(DateUtil.formatDate(
                DateUtil.calculatedays(DateUtil.getCurrentDate(), integralProduct.getValidday())));
        coupons.setLowlimit(integralProduct.getLowlimit());
        coupons.setVprice(integralProduct.getVprice());
        coupons.setIsuse(1);
        coupons.setIsusename("可用");
        coupons.setMakedate(DateUtil.getCurrentDateTime());
        Assert.isTrue(couponsDao.save(coupons) == 1, "兑换失败");
    }
}
