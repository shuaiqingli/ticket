package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.*;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Service
public class SettleService {

    @Autowired
    private SaleOrderDao saleOrderDao;
    @Autowired
    private AdminSettleLogDao adminSettleLogDao;

    @SuppressWarnings("unchecked")
    public List<Map> findAdminSettleList(String keyword, Page page){
        page.setTotalCount(saleOrderDao.findAdminSettleTotal(keyword));
        return saleOrderDao.findAdminSettleList(keyword, page);
    }

    @SuppressWarnings("unchecked")
    public List<Map> findAdminSettleDetailList(String userid, Page page){
        page.setTotalCount(saleOrderDao.findAdminSettleDetailTotal(userid));
        return saleOrderDao.findAdminSettleDetailList(userid, page);
    }

    public List<AdminSettleLog> findAdminSettleLogList(String keyword, Page page){
        page.setTotalCount(adminSettleLogDao.findAdminSettleLogTotal(keyword));
        return adminSettleLogDao.findAdminSettleLogList(keyword, page);
    }

    @SuppressWarnings("unchecked")
    public void updateForAdminSettle(String userid, BigDecimal amount, Admin admin){
        Page page = new Page();
        page.setPageSize(1000000);
        List<Map> dataList = saleOrderDao.findAdminSettleDetailList(userid, page);
        Assert.notEmpty(dataList, "没有可结算订单");
        List<String> idList = new ArrayList<String>();
        BigDecimal pricetotal = new BigDecimal(0);
        Integer quantitytotal = 0;
        for(Map data : dataList){
            idList.add((String) data.get("ID"));
            pricetotal = pricetotal.add((BigDecimal) data.get("PriceTotal"));
            quantitytotal += ((Long) data.get("QuantityTotal")).intValue();
        }
        Assert.isTrue(amount.compareTo(pricetotal) == 0, "结算金额不匹配");
        Assert.isTrue(saleOrderDao.updateForSettle(userid, idList) == idList.size(), "订单数量不匹配");
        AdminSettleLog adminSettleLog = new AdminSettleLog();
        adminSettleLog.setUserid(userid);
        adminSettleLog.setUsername((String) dataList.get(0).get("ReceiveName"));
        adminSettleLog.setSaleorders(CollectionUtil.listToString(idList, "|"));
        adminSettleLog.setQuantitytotal(quantitytotal);
        adminSettleLog.setPricetotal(pricetotal);
        adminSettleLog.setOperatorid(admin.getUserID());
        adminSettleLog.setOperatorname(admin.getRealName());
        adminSettleLog.setMakedate(DateUtil.formatDateTime(new Date()));
        Assert.isTrue(adminSettleLogDao.saveAdminSettleLog(adminSettleLog) == 1, "记录结算日志失败");
    }
}
