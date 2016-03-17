package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Driver;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李冠锋 2015-09-26
 */
public interface LineManageDao {

    /**
     * 保存一个对象
     *
     * @param lineManage
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public abstract int save(LineManage lineManage) throws Exception;

    /**
     * 更新一个对象
     *
     * @param lineManage
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public abstract int update(LineManage lineManage) throws Exception;
    
    public abstract int updateNotNull(LineManage lineManage) throws Exception;

    /**
     * 检查该记录是否存在
     *
     * @param lineManage
     * @return
     * @throws Exception
     */
    public abstract long checkExist(LineManage lineManage) throws Exception;

    /**
     * 根据主键查询一个对象
     *
     * @param lineManage
     * @return 返回LineManage对象
     * @throws Exception
     */
    public abstract LineManage find(LineManage lineManage) throws Exception;

    /**
     * 按 id 查询线路 和 公司id查询
     *
     * @param lineManage
     * @param admin
     * @return
     * @throws Exception
     */
    public abstract LineManage findByIdTcid(@Param("lm") LineManage lineManage, @Param("admin") Object admin) throws Exception;

    /**
     * 查询价格模板
     *
     * @param lineId
     * @return
     * @throws Exception
     */
    public abstract LineManage findLinePriceByLineId(String lineId) throws Exception;

    /**
     * 查询线路
     *
     * @param page
     * @return
     * @throws Exception
     */
    public abstract List<LineManage> findList(Page page) throws Exception;

    /**
     * 查询所有审核的线路
     *
     * @return
     * @throws Exception
     */
    public abstract List<LineManage> findApproveLines() throws Exception;

    /**
     * 查询自动发布车票的线路
     *
     * @return
     * @throws Exception
     */
    public abstract List<LineManage> findReleaseLines() throws Exception;

    /**
     * 统计条数
     *
     * @param page
     * @return
     * @throws Exception
     */
    public abstract Long totalCount(Page page) throws Exception;

    // 根据lineid在line_manage,linemanage_station 中找出途经站
    public abstract List getWayStation(Integer lmid) throws Exception;

    //根据ststartid,cityarriveid,starriveid获取线路ID
    public abstract LineManage getLineidBySCS(Map a) throws Exception;

    //分页获取所有的线路
    public abstract List getAllLine(Map a) throws Exception;

    long checkForBindDriverToLine(@Param("driverid") String driverid, @Param("groupid") String groupid, @Param("type") String type);

    int bindDriverToLine(@Param("driverid") String driverid, @Param("groupid") String groupid, @Param("type") String type);

    int unbindDriverToLine(@Param("driverid") String driverid, @Param("groupid") String groupid);

    int bindPlateToLineDriver(@Param("plateid") Integer plateid, @Param("driverid") String driverid, @Param("groupid") String groupid);

    int unbindPlateToLineDriver(@Param("plateid") Integer plateid, @Param("groupid") String groupid);

    int bindPlateToLine(@Param("plateid") Integer plateid, @Param("groupid") String groupid);

    int unbindPlateToLine(@Param("plateid") Integer plateid, @Param("groupid") String groupid);
    
    //按分组id查询线路
    List<LineManage> findByGroupId(String groupid) throws Exception;
    //按分组id删除
    int delByGroupId(@Param("groupid")String groupid) throws Exception;
    //删除途经站点
    int delLineManageStationByGroupId(@Param("groupid") String groupid) throws Exception;
    //删除时间规则
    int delLineStationTimeRule(@Param("groupid") String groupid) throws Exception;

    //按分组id删除车牌
    int delPlateByGroupId(@Param("groupid")String groupid) throws Exception;
    //按分组id删除司机
    int delDriverByGroupId(@Param("groupid")String groupid) throws Exception;

    List<LineManage> findLineListByGroupID(@Param("groupid") String groupid);

    List<LineManage> findLineListBySingleLineID(@Param("id") Integer id);

    List<Map> findListByUserid(String userid);

    Long totalCountForBindAdmin(Page page);

    List<LineManage> findListForBindAdmin(Page page);

    Long totalCountForBindProhibit(Page page);

    List<Map> findListForBindProhibit(Page page);

    List<String> getUseridListForAutoAssociateByLmid(@Param("lmid") Integer lmid);

    int delInvalidLineList(@Param("lmid") String lmid);

    int unbindUserListToLine(@Param("lmid") Integer lmid);

    List<Map> findLineListBySTID(@Param("stid") Integer stid);

    Long totalCountForBindShowTime(Page page);

    List<LineManage> findListForBindShowTime(Page page);

    Long totalCountForBindIntegralRule(Page page);

    List<Map> findListForBindIntegralRule(Page page);

}
