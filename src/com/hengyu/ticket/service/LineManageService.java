package com.hengyu.ticket.service;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.dao.AdminDao;
import com.hengyu.ticket.dao.LineManageDao;
import com.hengyu.ticket.dao.LineManageStationDao;
import com.hengyu.ticket.dao.LinePriceDao;
import com.hengyu.ticket.dao.RefundRuleLineDao;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.NumberCreate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author 李冠锋 2015-09-26
 */
@Service
public class LineManageService {

    @Autowired
    private LineManageDao lineManageDao;
    @Autowired
    private LineManageStationDao lmsDao;
    @Autowired
    private LinePriceDao linePriceDao;
    @Autowired
    private LineManageStationDao manageStationDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private RefundRuleLineDao rrldao;

    /**
     * 保存一个对象
     *
     * @param lineManage
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int save(LineManage lineManage) throws Exception {
        lineManage.setGroupid(UUID.randomUUID().toString());
        long l = lineManageDao.checkExist(lineManage);
        Assert.isTrue(l==0,"线路编号已经被占用！");

        //检测线路是否号是否被占用
        LineManage end = new LineManage();
        end.setLineid(NumberCreate.getReverseLineId(lineManage.getLineid()));

        Assert.isTrue(lineManageDao.checkExist(end)==0,"线路编号已经被占用！");
        int result = lineManageDao.save(lineManage);
        Assert.isTrue(result==1);

        //克隆对象
        LineManage endline = (LineManage) lineManage.clone();
        endline.setId(null);

        //调换城市
        endline.setCitystartid(lineManage.getCityarriveid());
        endline.setCitystartname(lineManage.getCityarrivename());
        endline.setCityarriveid(lineManage.getCitystartid());
        endline.setCityarrivename(lineManage.getCitystartname());

        //线路名称
        endline.setLinename(new StringBuffer().append(lineManage.getCityarrivename()).append("-").append(lineManage.getCitystartname()).toString());
        endline.setLineid(NumberCreate.getReverseLineId(endline.getLineid()));
        Assert.isTrue(lineManageDao.save(endline)==1);
        return 1;
    }

    /**
     * 更新一个对象
     *
     * @param lineManage
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int update(LineManage lineManage, String ids) throws Exception {
        long l = lineManageDao.checkExist(lineManage);
        if (l >= 1) {
            return Const.EXIST_CODE;
        }
        lineManage.setLinepre(lineManage.getLinepre().toUpperCase());
        saveOrUpdaeLineManage(lineManage, ids);
        int num = lineManageDao.update(lineManage);
        Assert.isTrue(num == 1, "更新失败");
        lineManageDao.delInvalidLineList(lineManage.getId().toString());
        List<String> useridList = lineManageDao.getUseridListForAutoAssociateByLmid(lineManage.getId());
        if(null != useridList && useridList.size() > 0){
            for(String userid : useridList){
                Assert.isTrue(adminDao.bindLineToAdmin(userid, lineManage.getId().toString()) == 1, "同步线路到管理员失败");
            }
        }
        return  num;
    }

    //更新一个对象
    public int update(LineManage lineManage) throws Exception {
        Assert.isTrue(lineManageDao.update(lineManage)==1, "更新失败");

        lineManageDao.delInvalidLineList(lineManage.getId().toString());
        List<String> useridList = lineManageDao.getUseridListForAutoAssociateByLmid(lineManage.getId());
        if(null != useridList && useridList.size() > 0){
            for(String userid : useridList){
                Assert.isTrue(adminDao.bindLineToAdmin(userid, lineManage.getId().toString()) == 1, "同步线路到管理员失败");
            }
        }
        return  1;
    }
    
    
    public int updateNotNull(LineManage lineManage) throws Exception {
    	int num = lineManageDao.updateNotNull(lineManage);
    	return  num;
    }

    /**
     * 更新或保存途经站点
     *
     * @param lm
     * @throws Exception
     */
    public void saveOrUpdaeLineManage(LineManage lm, String ids) throws Exception {
        if (lm.getIsallowupdate() != null && lm.getIsallowupdate() != 0) {
            if (StringUtils.isNotEmpty(ids)) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("ids", ids);
                lmsDao.delete(map);
            }
        }
        List<LineManageStation> lms = lm.getLineManageStataions();
        if (lms != null) {
            for (LineManageStation lineManageStation : lms) {
                if (lineManageStation.getId() != null) {
                    LineManageStation o = lmsDao.find(lineManageStation.getId());
                    if (o == null) {
                        lmsDao.save(lineManageStation);
                    } else {
                        lmsDao.update(lineManageStation);
                    }
                } else {
                    lmsDao.save(lineManageStation);
                }
            }
        }
    }


    /**
     * 根据主键查询一个对象
     *
     * @param id 主键
     * @return 返回LineManage对象
     * @throws Exception
     */
    public LineManage find(Integer id) throws Exception {
        LineManage lm = new LineManage();
        lm.setId(id);
        return lineManageDao.find(lm);
    }

    /**
     * 按 id 查询线路 和 公司id查询
     *
     * @param id
     * @param admin
     * @return
     * @throws Exception
     */
    public LineManage findByIdTcid(Integer id, Object admin) throws Exception {
        LineManage lm = new LineManage();
        lm.setId(id);
        return lineManageDao.findByIdTcid(lm, admin);
    }


    /**
     * 按线路编号查询价格
     *
     * @param lineId
     * @return
     * @throws Exception
     */
    public LineManage findLinePriceByLineId(String lineId) throws Exception {
        return lineManageDao.findLinePriceByLineId(lineId);
    }

    /**
     * 查询线路
     *
     * @param page
     * @return
     * @throws Exception
     */
    public List<LineManage> findList(Page page) throws Exception {
        List<LineManage> list = lineManageDao.findList(page);
        page.setData(list);
        page.setTotalCount(lineManageDao.totalCount(page));
        return list;
    }

    /**
     * 更新价格模板
     *
     * @param lineManage
     * @return
     * @throws Exception
     */
    public int updateLinePrice(LineManage lineManage) throws Exception {
        int count = -1;
        List<LinePrice> linePrices = lineManage.getLinePrices();
        for (LinePrice linePrice : linePrices) {
            count += linePriceDao.updatePrice(linePrice);
        }
        return count;
    }

    public void setLineManageDao(LineManageDao lineManageDao) {
        this.lineManageDao = lineManageDao;
    }

    public List getWayStation(Integer lmid) throws Exception {
        return lineManageDao.getWayStation(lmid);
    }

    // 根据citystartid,cityarriveid,starriveid获取线路ID
    public LineManage getLineidBySCS(Map a) throws Exception {
        return lineManageDao.getLineidBySCS(a);
    }

    // 分页获取所有的线路
    public List getAllLine(Map a) throws Exception {
        return lineManageDao.getAllLine(a);
    }

    public void addDriverList(String groupid, String[] driveridList, String type) {
        for (String driverid : driveridList) {
            Assert.isTrue(lineManageDao.checkForBindDriverToLine(driverid, groupid, type) == 0, "无效司机");
            Assert.isTrue(lineManageDao.bindDriverToLine(driverid, groupid, type) == 1, "操作失败");
        }
    }

    public void delDriver(String groupid, String driverid) {
        Assert.isTrue(lineManageDao.unbindDriverToLine(driverid, groupid) == 1, "操作失败");
    }

    public void addPlateToDriver(String groupid, String driverid, Integer plateid) {
        if(null != plateid){
            lineManageDao.unbindPlateToLineDriver(plateid, groupid);
        }
        Assert.isTrue(lineManageDao.bindPlateToLineDriver(plateid, driverid, groupid) == 1, "操作失败");
    }

    public void addPlateList(String groupid, Integer[] plateidList) {
        for (Integer plateid : plateidList) {
            Assert.isTrue(lineManageDao.bindPlateToLine(plateid, groupid) == 1, "操作失败");
        }
    }

    public void delPlate(String groupid, Integer plateid) {
        lineManageDao.unbindPlateToLineDriver(plateid, groupid);
        Assert.isTrue(lineManageDao.unbindPlateToLine(plateid, groupid) == 1, "操作失败");
    }
    
    //按分组id删除（更新）
    public int delLineManageByGroupId(String groupid) throws Exception{
        List<LineManage> lineManageList = lineManageDao.findLineListByGroupID(groupid);
        Assert.isTrue(lineManageList != null && lineManageList.size() == 2, "无效线路");
    	int r = lineManageDao.delByGroupId(groupid);
    	Assert.isTrue(r==2);

    	Log.info(this.getClass(),"删除途经站点成功！", lineManageDao.delLineManageStationByGroupId(groupid));
    	Log.info(this.getClass(),"删除时间规则成功！",lineManageDao.delLineStationTimeRule(groupid));
    	Log.info(this.getClass(),"删除线路成功！",lineManageDao.delByGroupId(groupid));
    	Log.info(this.getClass(),"删除车牌成功！",lineManageDao.delPlateByGroupId(groupid));
    	Log.info(this.getClass(),"删除司机成功！",lineManageDao.delDriverByGroupId(groupid));

        for(LineManage lm : lineManageList){
            lineManageDao.unbindUserListToLine(lm.getId());
            Log.info(this.getClass(),"删除退票规则关联的线路成功！result ===>", rrldao.deleteByLmid(lm.getId()));
        }

    	return r;
    }

    public List<LineManage> findListForBindAdmin(Page page){
        Long totalCount = lineManageDao.totalCountForBindAdmin(page);
        page.setTotalCount(totalCount);
        return lineManageDao.findListForBindAdmin(page);
    }

    public List<Map> findListForBindProhibit(Page page){
        Long totalCount = lineManageDao.totalCountForBindProhibit(page);
        page.setTotalCount(totalCount);
        return lineManageDao.findListForBindProhibit(page);
    }

    public List<LineManage> findListForBindShowTime(Page page){
        Long totalCount = lineManageDao.totalCountForBindShowTime(page);
        page.setTotalCount(totalCount);
        return lineManageDao.findListForBindShowTime(page);
    }

    public List<Map> findListForBindIntegralRule(Page page){
        page.setTotalCount(lineManageDao.totalCountForBindIntegralRule(page));
        return lineManageDao.findListForBindIntegralRule(page);
    }

    public List<Map> findListByUserid(String userid){
        return lineManageDao.findListByUserid(userid);
    }
    
}
