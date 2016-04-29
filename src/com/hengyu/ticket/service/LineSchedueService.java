package com.hengyu.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.dao.*;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.Log;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 李冠锋 2015-09-30
 */
@Service
public class LineSchedueService {

    @Autowired
    private LineSchedueDao lineSchedueDao;
    @Autowired
    private TicketLineService tls;
    @Autowired
    LineSchedueRuleDao lsrDao;
    @Autowired
    private LineScheduDetailDao lsdDao;
    @Autowired
    private ShiftStartDao ssdao;
    @Autowired
    private LineManageDao lineManageDao;
    @Autowired
    private LineScheduDetailDao lineScheduDetailDao;

    /**
     * 保存一个对象
     *
     * @param lineSchedue
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int save(LineSchedue lineSchedue) throws Exception {
        if(lineSchedue.getIsdefault()!=null&&lineSchedue.getIsdefault()==1){
            lineSchedueDao.resetDefault(lineSchedue.getLmid());
        }
        Assert.isTrue(lineSchedueDao.save(lineSchedue)==1,"保存排班规则失败！");
        saveOrUpdateScheduleDetail(lineSchedue);
        return 1;
    }

    /**
     * 更新一个对象
     *
     * @param lineSchedue
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int update(LineSchedue lineSchedue) throws Exception {
        if(lineSchedue.getIsdefault()!=null&&lineSchedue.getIsdefault()==1){
            lineSchedueDao.resetDefault(lineSchedue.getLmid());
        }
        Assert.isTrue(lineSchedueDao.update(lineSchedue)==1,"更新排班规则失败！");
        saveOrUpdateScheduleDetail(lineSchedue);
        return 1;
    }

    /**
     * 删除排班
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int delete(Integer id) throws Exception {
        lineScheduDetailDao.deleteByLineSchedule(id);
        Assert.isTrue(lineSchedueDao.delete(id)==1,"删除排班规则失败");
        return 1;
    }

    /**
     * 查询排班列表
     * @return
     * @throws Exception
     */
    public List<LineSchedue> findList(Integer lmid) throws Exception {
        List<LineSchedue> list = lineSchedueDao.findList(lmid);
        return list;
    }

    /**
     * 根据主键查询一个对象
     *
     * @return 返回LineSchedue对象
     * @throws Exception
     */
    public LineSchedue find(Integer id) throws Exception {
        return lineSchedueDao.find(id);
    }

    //保存或者更新排班
    public void saveOrUpdateScheduleDetail(LineSchedue ls) throws Exception {
        List<LineScheduDetail> schedules = ls.getSchedules();
        if(schedules!=null){
            int i = 0;
            for (LineScheduDetail detail : schedules ) {
                if(detail.getId()!=null&&detail.getIsdel()!=null&&detail.getIsdel()==1){
                    Assert.isTrue(lineScheduDetailDao.delete(detail.getId())==1,"删除排班失败！");
                    continue;
                }
                detail.setLsuid(ls.getId());
                if(detail.getId()==null){
                    Assert.isTrue(lineScheduDetailDao.save(detail)==1,"保存排班失败！");
                }else {
                    Assert.isTrue(lineScheduDetailDao.update(detail)==1,"更新排班失败！");
                }
            }
        }
    }


    public void setLineSchedueDao(LineSchedueDao lineSchedueDao) {
        this.lineSchedueDao = lineSchedueDao;
    }

    public Map<String, Object> checkForDistributeDriverAndPlate(Integer lmid, String date){
        Map<String, Object> result = new HashMap<String, Object>();
        List<LineManage> lineManageList = lineManageDao.findLineListBySingleLineID(lmid);
        if(null == lineManageList || lineManageList.size() != 2){
            result.put("distributeFlag", "0");
            return result;
        }
        result.put("line", lineManageList.get(0));
        for (LineManage lineManage : lineManageList) {
            if(ssdao.isShiftStartExist(lineManage.getId(), date) <= 0){
                result.put("distributeFlag", "0");
                return result;
            }
            if(ssdao.isDriverExistForShiftStart(lineManage.getId(), date) != 0){
                result.put("distributeFlag", "2");
                return result;
            }
        }
        result.put("distributeFlag", "1");
        result.put("groupid", lineManageList.get(0).getGroupid());
        result.put("date", date);
        return result;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getDataForDistributeDriverAndPlate(String groupid, Date date) {
        List<LineManage> lineManageList = lineManageDao.findLineListByGroupID(groupid);
        Assert.isTrue(null != lineManageList && lineManageList.size() == 2, "无效线路");
        String dateStr = DateUtil.formatDate(date);
        for (LineManage lineManage : lineManageList) {
            Assert.isTrue(ssdao.isShiftStartExist(lineManage.getId(), dateStr) > 0, "无效排班数据");
            Assert.isTrue(ssdao.isDriverExistForShiftStart(lineManage.getId(), dateStr) == 0, "排班数据已绑定司机");
        }
        List<Map> plateList = ssdao.getPlateListForDistributePlate(groupid, dateStr, null);
        List<Map> driverListWithFixed = ssdao.getDriverListForDistributeDriver(groupid, dateStr, "1", null);
        List<Map> driverListWithoutFixed = ssdao.getDriverListForDistributeDriver(groupid, dateStr, "2", null);
        positionDriverAndPlate(plateList, dateStr, "2");
        positionDriverAndPlate(driverListWithFixed, dateStr, "1");
        positionDriverAndPlate(driverListWithoutFixed, dateStr, "1");

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> dataMapForCityA = new HashMap<String, Object>();
        dataMapForCityA.put("line", lineManageList.get(0));
        dataMapForCityA.put("shiftStartList", ssdao.getShiftStartListBylmidList(new Integer[]{lineManageList.get(0).getId()}, dateStr));
        dataMapForCityA.put("plateListForCity", new ArrayList<Map>());
        dataMapForCityA.put("driverListWithFixedForCity", new ArrayList<Map>());
        dataMapForCityA.put("driverListWithoutFixedForCity", new ArrayList<Map>());

        Map<String, Object> dataMapForCityB = new HashMap<String, Object>();
        dataMapForCityB.put("line", lineManageList.get(1));
        dataMapForCityB.put("shiftStartList", ssdao.getShiftStartListBylmidList(new Integer[]{lineManageList.get(1).getId()}, dateStr));
        dataMapForCityB.put("plateListForCity", new ArrayList<Map>());
        dataMapForCityB.put("driverListWithFixedForCity", new ArrayList<Map>());
        dataMapForCityB.put("driverListWithoutFixedForCity", new ArrayList<Map>());

        String cityAID = lineManageList.get(0).getCitystartid();
        String cityBID = lineManageList.get(1).getCitystartid();
        for (Map plate : plateList) {
            if (cityAID.equals(plate.get("positionid"))) {
                ((List) dataMapForCityA.get("plateListForCity")).add(plate);
            } else if (cityBID.equals(plate.get("positionid"))) {
                ((List) dataMapForCityB.get("plateListForCity")).add(plate);
            } else if(((List) dataMapForCityA.get("plateListForCity")).size() <= ((List) dataMapForCityB.get("plateListForCity")).size()) {
                ((List) dataMapForCityA.get("plateListForCity")).add(plate);
            } else {
                ((List) dataMapForCityB.get("plateListForCity")).add(plate);
            }
        }
        for (Map driver : driverListWithFixed) {
            if (cityAID.equals(driver.get("positionid"))) {
                ((List) dataMapForCityA.get("driverListWithFixedForCity")).add(driver);
            } else if (cityBID.equals(driver.get("positionid"))) {
                ((List) dataMapForCityB.get("driverListWithFixedForCity")).add(driver);
            } else if (((List) dataMapForCityA.get("driverListWithFixedForCity")).size() <= ((List) dataMapForCityB.get("driverListWithFixedForCity")).size()){
                ((List) dataMapForCityA.get("driverListWithFixedForCity")).add(driver);
            } else {
                ((List) dataMapForCityB.get("driverListWithFixedForCity")).add(driver);
            }
        }
        for (Map driver : driverListWithoutFixed) {
            if (cityBID.equals(driver.get("positionid"))) {
                ((List) dataMapForCityB.get("driverListWithoutFixedForCity")).add(driver);
            } else {
                ((List) dataMapForCityA.get("driverListWithoutFixedForCity")).add(driver);
            }
        }
        result.put("dataMapForCityA", dataMapForCityA);
        result.put("dataMapForCityB", dataMapForCityB);
        result.put("groupid", groupid);
        result.put("date", dateStr);
        result.put("driverQuantity", lineManageList.get(0).getDriverquantity());
        if (lineManageList.get(0).getDriverquantity() == 1) {
            sortDriveAndPlate((List<Map>) dataMapForCityA.get("driverListWithFixedForCity"), (List<Map>) dataMapForCityA.get("plateListForCity"));
            sortDriveAndPlate((List<Map>) dataMapForCityB.get("driverListWithFixedForCity"), (List<Map>) dataMapForCityB.get("plateListForCity"));
        }else if(lineManageList.get(0).getDriverquantity() == 2){
            List<Map> driverListWithFixedForCityA = (List<Map>) dataMapForCityA.get("driverListWithFixedForCity");
            if(driverListWithFixedForCityA.size() > 0){
                int index = driverListWithFixedForCityA.size() / 2;
                dataMapForCityA.put("driverListWithFixedForCity", driverListWithFixedForCityA.subList(0, index));
                dataMapForCityA.put("driverList2WithFixedForCity", driverListWithFixedForCityA.subList(index, driverListWithFixedForCityA.size()));
            }
            List<Map> driverListWithFixedForCityB = (List<Map>) dataMapForCityB.get("driverListWithFixedForCity");
            if(driverListWithFixedForCityB.size() > 0){
                int index = driverListWithFixedForCityB.size() / 2;
                dataMapForCityB.put("driverListWithFixedForCity", driverListWithFixedForCityB.subList(0, index));
                dataMapForCityB.put("driverList2WithFixedForCity", driverListWithFixedForCityB.subList(index, driverListWithFixedForCityB.size()));
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private void positionDriverAndPlate(List<Map> list, String date, String type) {
        if (list == null || list.size() == 0) return;
        if ("1".equals(type)) {
            for (Map driver : list) {
                List<Map> positionDateList = ssdao.getDriverPosition((String) driver.get("ID"), date);
                if (positionDateList != null && positionDateList.size() == 1) {
                    driver.put("positionid", positionDateList.get(0).get("ID"));
                    driver.put("positionname", positionDateList.get(0).get("CityName"));
                }
            }
        }
        if ("2".equals(type)) {
            for (Map plate : list) {
                List<Map> positionDateList = ssdao.getPlatePosition((String) plate.get("PlateNum"), date);
                if (positionDateList != null && positionDateList.size() == 1) {
                    plate.put("positionid", positionDateList.get(0).get("ID"));
                    plate.put("positionname", positionDateList.get(0).get("CityName"));
                }
            }
        }
    }

    private void sortDriveAndPlate(List<Map> driverList, List<Map> plateList) {
        if (driverList == null || driverList.size() == 0 || plateList == null || plateList.size() == 0) return;
        LinkedList<Map> sortedDriverList = new LinkedList<Map>();
        LinkedList<Map> sortedPlateList = new LinkedList<Map>();
        for (Map driver : driverList) {
            for (Map plate : plateList) {
                if (driver.get("PlateID") == plate.get("ID")) {
                    sortedPlateList.add(0, plate);
                    sortedDriverList.add(0, driver);
                    break;
                }
            }
        }
        for (Map driver : driverList) {
            if (!sortedDriverList.contains(driver)) sortedDriverList.add(driver);
        }
        for (Map plate : plateList) {
            if (!sortedPlateList.contains(plate)) sortedPlateList.add(plate);
        }
        driverList.clear();
        driverList.addAll(sortedDriverList);
        plateList.clear();
        plateList.addAll(sortedPlateList);
    }

    public Map<String, Object> previewForDistributeDriverAndPlate(Map dataMap) throws Exception {
        checkAndDistributeDriverAndPlate(dataMap);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("shiftStartList", dataMap.get("shiftStartList"));
        result.put("lineForCityA", dataMap.get("lineForCityA"));
        result.put("lineForCityB", dataMap.get("lineForCityB"));
        return result;
    }

    @SuppressWarnings("unchecked")
    public void updateForDistributeDriverAndPlate(Map dataMap) throws Exception {
        checkAndDistributeDriverAndPlate(dataMap);
        for(Map shiftStart : (List<Map>)dataMap.get("shiftStartList")){
            Assert.isTrue(ssdao.bindDriverAndPlateToShiftStart(shiftStart) == 1, "绑定数据失败");
        }
    }

    @SuppressWarnings("unchecked")
    private void checkAndDistributeDriverAndPlate(Map dataMap) throws Exception {
        String groupid = (String) dataMap.get("groupid");
        Assert.hasText(groupid, "GROUPID不能为空");
        Date date = DateUtil.stringToDate((String) dataMap.get("date"));
        Assert.notNull(date, "日期不能为空");
        JSONObject dataMapForCityA = (JSONObject) dataMap.get("dataMapForCityA");
        Assert.notNull(dataMapForCityA, "城市数据不能为空");
        JSONObject dataMapForCityB = (JSONObject) dataMap.get("dataMapForCityB");
        Assert.notNull(dataMapForCityB, "城市数据不能为空");
        String cityIDForCityA = (String) dataMapForCityA.get("cityID");
        String cityIDForCityB = (String) dataMapForCityB.get("cityID");
        Assert.isTrue(StringUtils.isNotBlank(cityIDForCityA) && StringUtils.isNotBlank(cityIDForCityB) && !cityIDForCityA.equals(cityIDForCityB), "无效城市数据");

        List<LineManage> lineManageList = lineManageDao.findLineListByGroupID(groupid);
        Assert.isTrue(null != lineManageList && lineManageList.size() == 2, "无效线路");
        String dateStr = DateUtil.formatDate(date);
        for (LineManage lineManage : lineManageList) {
            Assert.isTrue(ssdao.isShiftStartExist(lineManage.getId(), dateStr) > 0, "无效排班数据");
            Assert.isTrue(ssdao.isDriverExistForShiftStart(lineManage.getId(), dateStr) == 0, "排班数据已绑定司机");
            if(cityIDForCityA.equals(lineManage.getCitystartid())){
                dataMap.put("lineForCityA", lineManage);
                dataMap.put("lineIDForCityA", lineManage.getId());
                Integer minutesForCityA = lineManageDao.getRequireTime(lineManage.getId());
                dataMap.put("minutesForCityA",minutesForCityA);
            }else if(cityIDForCityB.equals(lineManage.getCitystartid())){
                dataMap.put("lineForCityB", lineManage);
                dataMap.put("lineIDForCityB", lineManage.getId());
                Integer minutesForCityB = lineManageDao.getRequireTime(lineManage.getId());
                dataMap.put("minutesForCityB",minutesForCityB);
            }else{
                Assert.isTrue(true, "城市与线路不匹配");
            }
        }
        dataMap.put("shiftStartList", ssdao.getShiftStartListBylmidList(new Integer[]{lineManageList.get(0).getId(),lineManageList.get(1).getId()}, dateStr));
        dataMap.put("driverQuantity", lineManageList.get(0).getDriverquantity());

        List<Object> fullDriveridList = new ArrayList<Object>();
        List<Object> fullPlateidList = new ArrayList<Object>();
        dataMap.put("driverListForCityA", getDriverListByDriveridList(groupid, dateStr, (List) dataMapForCityA.get("driveridList"), fullDriveridList));
        dataMap.put("driverListForCityB", getDriverListByDriveridList(groupid, dateStr, (List) dataMapForCityB.get("driveridList"), fullDriveridList));
        dataMap.put("plateListForCityA", getPlateListByPlateidList(groupid, dateStr, (List) dataMapForCityA.get("plateidList"), fullPlateidList));
        dataMap.put("plateListForCityB", getPlateListByPlateidList(groupid, dateStr, (List) dataMapForCityB.get("plateidList"), fullPlateidList));
        if(lineManageList.get(0).getDriverquantity() == 2){
            dataMap.put("driver2ListForCityA", getDriverListByDriveridList(groupid, dateStr, (List) dataMapForCityA.get("driverid2List"), fullDriveridList));
            dataMap.put("driver2ListForCityB", getDriverListByDriveridList(groupid, dateStr, (List) dataMapForCityB.get("driverid2List"), fullDriveridList));
        }
        distributeDriverAndPlate(dataMap);
    }

    private List<Map> getDriverListByDriveridList(String groupid, String date, List<Object> driveridList, List<Object> fullDriveridList){
        Assert.notEmpty(driveridList, "司机不能为空");
        List<Map> driverList = ssdao.getDriverListForDistributeDriver(groupid, date, null, driveridList);
        Assert.isTrue(driverList != null && driverList.size() == driveridList.size(), "存在无效司机");
        List<Map> sortedDriverList = new ArrayList<Map>();
        for(Object driverid : driveridList){
            Assert.isTrue(!fullDriveridList.contains(driverid), "重复司机");
            fullDriveridList.add(driverid);
            for(Map driver : driverList){
                if(driverid.equals(driver.get("ID"))){
                    sortedDriverList.add(driver);
                    break;
                }
            }
        }
        return sortedDriverList;
    }

    private List<Map> getPlateListByPlateidList(String groupid, String date, List<Object> plateidList, List<Object> fullPlateidList){
        Assert.notEmpty(plateidList, "车牌不能为空");
        List<Map> plateList = ssdao.getPlateListForDistributePlate(groupid, date, plateidList);
        Assert.isTrue(plateList != null && plateList.size() == plateidList.size(), "存在无效车牌");
        List<Map> sortedPlateList = new ArrayList<Map>();
        for(Object plateid : plateidList){
            Assert.isTrue(!fullPlateidList.contains(plateid), "重复车牌");
            fullPlateidList.add(plateid);
            for(Map plate : plateList){
                if(plateid.equals(plate.get("ID").toString())){
                    sortedPlateList.add(plate);
                    break;
                }
            }
        }
        return sortedPlateList;
    }

    @SuppressWarnings("unchecked")
    private void distributeDriverAndPlate(Map dataMap) {
        List<Map> shiftStartList = (List<Map>) dataMap.get("shiftStartList");
        Integer driverQuantity = (Integer) dataMap.get("driverQuantity");

        LinkedList<Map> driverListForCityA = new LinkedList<Map>();
        driverListForCityA.addAll((List<Map>) dataMap.get("driverListForCityA"));
        LinkedList<Map> driver2ListForCityA = new LinkedList<Map>();
        if (driverQuantity == 2) {
            driver2ListForCityA.addAll((List<Map>) dataMap.get("driver2ListForCityA"));
        }
        LinkedList<Map> plateListForCityA = new LinkedList<Map>();
        plateListForCityA.addAll((List<Map>) dataMap.get("plateListForCityA"));
        Integer lineIDForCityA = (Integer) dataMap.get("lineIDForCityA");
        Integer minutesForCityA = (Integer) dataMap.get("minutesForCityA");
        LinkedList<Map> driverListForCityB = new LinkedList<Map>();
        driverListForCityB.addAll((List<Map>) dataMap.get("driverListForCityB"));
        LinkedList<Map> driver2ListForCityB = new LinkedList<Map>();
        if (driverQuantity == 2) {
            driver2ListForCityB.addAll((List<Map>) dataMap.get("driver2ListForCityB"));
        }
        LinkedList<Map> plateListForCityB = new LinkedList<Map>();
        plateListForCityB.addAll((List<Map>) dataMap.get("plateListForCityB"));
        Integer lineIDForCityB = (Integer) dataMap.get("lineIDForCityB");
        Integer minutesForCityB = (Integer) dataMap.get("minutesForCityB");

        Map<Map, Map> driverShiftStartMap = new HashMap<Map, Map>();
        Map<Map, Map> plateShiftStartMap = new HashMap<Map, Map>();
        for (Map shiftStart : shiftStartList) {
            if (lineIDForCityA.equals(shiftStart.get("LMID"))) {
                Map driver = driverListForCityA.pollFirst();
                Assert.isTrue(driver != null && checkTimeForShiftStart(shiftStart, driverShiftStartMap.get(driver), minutesForCityA), "司机数量不够");
                driverListForCityB.add(driver);
                driverShiftStartMap.put(driver, shiftStart);
                shiftStart.put("DriverID", driver.get("ID"));
                shiftStart.put("DriverName", driver.get("DName"));
                if (driverQuantity == 2) {
                    Map driver2 = driver2ListForCityA.pollFirst();
                    Assert.isTrue(driver2 != null && checkTimeForShiftStart(shiftStart, driverShiftStartMap.get(driver2), minutesForCityA), "司机数量不够");
                    driver2ListForCityB.add(driver2);
                    driverShiftStartMap.put(driver2, shiftStart);
                    shiftStart.put("DriverIDII", driver2.get("ID"));
                    shiftStart.put("DriverNameII", driver2.get("DName"));
                }
                Map plate = plateListForCityA.pollFirst();
                Assert.isTrue(plate != null && checkTimeForShiftStart(shiftStart, plateShiftStartMap.get(plate), minutesForCityA), "车牌数量不够");
                plateListForCityB.add(plate);
                plateShiftStartMap.put(plate, shiftStart);
                shiftStart.put("PlateNum", plate.get("PlateNum"));
                shiftStart.put("NuclearLoad", plate.get("NuclearLoad"));
            } else if (lineIDForCityB.equals(shiftStart.get("LMID"))) {
                Map driver = driverListForCityB.pollFirst();
                Assert.isTrue(driver != null && checkTimeForShiftStart(shiftStart, driverShiftStartMap.get(driver), minutesForCityB), "司机数量不够");
                driverListForCityA.add(driver);
                driverShiftStartMap.put(driver, shiftStart);
                shiftStart.put("DriverID", driver.get("ID"));
                shiftStart.put("DriverName", driver.get("DName"));
                if (driverQuantity == 2) {
                    Map driver2 = driver2ListForCityB.pollFirst();
                    Assert.isTrue(driver2 != null && checkTimeForShiftStart(shiftStart, driverShiftStartMap.get(driver2), minutesForCityB), "司机数量不够");
                    driver2ListForCityA.add(driver2);
                    driverShiftStartMap.put(driver2, shiftStart);
                    shiftStart.put("DriverIDII", driver2.get("ID"));
                    shiftStart.put("DriverNameII", driver2.get("DName"));
                }
                Map plate = plateListForCityB.pollFirst();
                Assert.isTrue(plate != null && checkTimeForShiftStart(shiftStart, plateShiftStartMap.get(plate), minutesForCityB), "车牌数量不够");
                plateListForCityA.add(plate);
                plateShiftStartMap.put(plate, shiftStart);
                shiftStart.put("PlateNum", plate.get("PlateNum"));
                shiftStart.put("NuclearLoad", plate.get("NuclearLoad"));
            }
        }
    }

    //检查新旧班次时间差是否大于一个班次所需时间
    private boolean checkTimeForShiftStart(Map newShiftStart, Map lastShiftStart, int minutes) {
        if (newShiftStart == null) return false;
        if (lastShiftStart == null) return true;
        String newOriginStartTime = (String) newShiftStart.get("OriginStartTime");
        String lastOriginStartTime = (String) lastShiftStart.get("OriginStartTime");
        int intervalMinutes = (Integer.parseInt(newOriginStartTime.split(":")[0]) - Integer.parseInt(lastOriginStartTime.split(":")[0])) * 60 +
                Integer.parseInt(newOriginStartTime.split(":")[1]) - Integer.parseInt(lastOriginStartTime.split(":")[1]);
        return intervalMinutes > minutes;
    }
}
