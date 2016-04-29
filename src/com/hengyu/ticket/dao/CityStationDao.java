package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李冠锋 2015-09-26
 */
public interface CityStationDao {

    /**
     * 保存一个对象
     *
     * @param cityStation
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public abstract int save(CityStation cityStation) throws Exception;

    /**
     * 更新一个对象
     *
     * @param cityStation
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public abstract int update(CityStation cityStation) throws Exception;

    /**
     * 根据主键查询一个对象
     *
     * @param iD 主键
     * @return 返回CityStation对象
     * @throws Exception
     */
    public abstract CityStation find(String iD) throws Exception;

    /**
     * 查询城市站点列表
     *
     * @param page
     * @return
     * @throws Exception
     */
    public abstract List<CityStation> findList(Page page) throws Exception;

    /**
     * 统计城市条数
     *
     * @param page
     * @return
     * @throws Exception
     */
    public abstract Long totalCount(Page page) throws Exception;

    /**
     * 获取所有城市
     *
     * @return
     * @throws Exception
     */
    public abstract List<CityStation> findAllCity() throws Exception;

    /**
     * 获取所有城市
     *
     * @return
     * @throws Exception
     */
    public abstract List<Map> getAllCity(CityStation c) throws Exception;
    
    /**
     * 获取始发城市的站点
     * @return
     * @throws Exception
     */
    public abstract List<Map> getStartStationByCityid(@Param("begincityid")String begincityid,@Param("endcityid")String endcityid,@Param("sort")Integer sort) throws Exception;
    
    //获取站点地址
    public abstract List<Map<String,String>> getAddressByIds(List<String> ids) throws Exception;

    /**
     * 按城市名称，验证是否已存在
     *
     * @param name
     * @return
     * @throws Exception
     */
    public abstract Long findCityByNameCheck(String name) throws Exception;

    // 查找城市站点
    public abstract List getCityStationByKeywords(Map a) throws Exception;

    Long totalCountForBindAdmin(Page page);

    List<CityStation> findListForBindAdmin(Page page);

    List<Map> findListByUserid(@Param("userid") String userid);

    List<CityStation> findByParent(@Param("pid") String pid) throws Exception;

    List<Map> getStartStationList(@Param("begincityid")String begincityid,@Param("endcityid")String endcityid,@Param("endstationid")String endstationid);

    List<Map> getEndStationList(@Param("endcityid")String endcityid,@Param("begincityid")String begincityid,@Param("beginstationid")String beginstationid);

	public abstract Map<String, Object> findCityNameAndIdByParentId(String ParentID);

}
