package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.*;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Service
public class StatisticService {

    @Autowired
    private SaleOrderDao saleOrderDao;
    @Autowired
    private ShiftStartDao shiftStartDao;
    @Autowired
    private IntegralLineDao integralLineDao;

    public List<Map> stationStatistic(List<String> stationidList, String startDate, String endDate, String keyword, Page page){
        page.setTotalCount(saleOrderDao.stationStatisticTotal(stationidList, startDate, endDate, keyword));
        return saleOrderDao.stationStatisticList(stationidList, startDate, endDate, keyword, page);
    }

    public List<Map> driverStatistic(String startDate, String endDate, String keyword, Page page){
        page.setTotalCount(shiftStartDao.driverStatisticTotal(startDate, endDate, keyword));
        return shiftStartDao.driverStatisticList(startDate, endDate, keyword, page);
    }

    public List<Map> billStatistic(String startDate, String endDate, List<String> keywordList, Page page){
        page.setTotalCount(saleOrderDao.billStatisticTotal(startDate, endDate, keywordList));
        return saleOrderDao.billStatisticList(startDate, endDate, keywordList, page);
    }

    public Map<String, Object> integralProductStatistic(String startDate, String endDate){
        Map<String, Object> result = new HashMap<String, Object>();
        List<Map> valueMapList = integralLineDao.integralProductStatisticForValue(startDate, endDate);
        List<Map> customerMapList = integralLineDao.integralProductStatisticForCustomer(startDate, endDate);
        result.put("valueMapList", valueMapList);
        result.put("customerMapList", customerMapList);
        result.put("customerTotal", customerMapList == null ? 0 : customerMapList.size());
        if(valueMapList != null){
            int countTotal = 0;
            int integralTotal = 0;
            for(Map valueMap : valueMapList){
                countTotal += ((BigDecimal) valueMap.get("CountTotal")).intValue();
                integralTotal += ((BigDecimal) valueMap.get("IntegralTotal")).intValue();
            }
            result.put("countTotal", countTotal);
            result.put("integralTotal", integralTotal);
        }
        return  result;
    }

    public List<Map> integralProductStatisticForExport(String startDate, String endDate){
        return integralLineDao.integralProductStatisticForExport(startDate, endDate);
    }
}
