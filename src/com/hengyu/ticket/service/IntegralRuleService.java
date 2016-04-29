package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.IntegralRuleDao;
import com.hengyu.ticket.dao.ProhibitDao;
import com.hengyu.ticket.entity.IntegralRule;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Prohibit;
import com.hengyu.ticket.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2016-01-11.
 */
@Service
public class IntegralRuleService {
    private static Logger logger = Logger.getLogger(IntegralRuleService.class);

    @Autowired
    private IntegralRuleDao integralRuleDao;

    public IntegralRule findIntegralRuleForOrder(Map<String,Object> param){
        return integralRuleDao.findIntegralRuleForOrder(param);
    }

    public IntegralRule findIntegralRule(Integer id) {
        IntegralRule integralRule = integralRuleDao.findIntegralRule(id);
        Assert.notNull(integralRule, "无效积分规则");
        integralRule.setLineMapList(integralRuleDao.findLineListForIntegralRule(id));
        return integralRule;
    }

    public List<Map> findIntegralRuleList(String keyword, Page page) {
        page.setTotalCount(integralRuleDao.findIntegralRuleTotal(keyword));
        return integralRuleDao.findIntegralRuleList(keyword, page);
    }

    public void saveIntegralRule(IntegralRule integralRule) {
        Assert.isTrue(integralRuleDao.saveIntegralRule(integralRule) == 1, "操作失败");
    }

    public void updateIntegralRule(IntegralRule integralRule) {
        Assert.isTrue(integralRuleDao.updateIntegralRule(integralRule) == 1, "操作失败");
    }

    public void delIntegralRule(Integer id) {
        Assert.isTrue(integralRuleDao.delIntegralRule(id) == 1, "操作失败");
        integralRuleDao.unbindAllLineToIntegralRule(id);
    }

    public void updateForBindLineListToIntegralRule(List<Integer> lmidList, Integer id) {
        for(Integer lmid : lmidList){
            Assert.isTrue(integralRuleDao.bindLineToIntegralRule(lmid, id) == 1, "操作失败");
        }
    }

    public void updateForUnbindLineToIntegralRule(Integer lmid, Integer id) {
        Assert.isTrue(integralRuleDao.unbindLineToIntegralRule(lmid, id) == 1, "操作失败");
    }
}
