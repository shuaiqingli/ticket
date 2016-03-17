package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Plate;

import java.util.List;
import java.util.Map;

/**
 * @author 李冠锋 2015-10-08
 */
public interface PlateDao {

    /**
     * 保存一个对象
     *
     * @param plate
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public abstract int save(Plate plate) throws Exception;

    /**
     * 更新一个对象
     *
     * @param plate
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public abstract int update(Plate plate) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    public abstract int delete(Integer id) throws Exception;


    /**
     * 根据主键查询一个对象
     *
     * @param id 主键
     * @return 返回Plate对象
     * @throws Exception
     */
    public abstract Plate find(Integer id) throws Exception;

    /**
     * 车牌列表
     *
     * @param page
     * @return
     * @throws Exception
     */
    public abstract List<Plate> findlist(Page page) throws Exception;

    /**
     * 统计条数
     *
     * @param page
     * @return
     * @throws Exception
     */
    public abstract Long totalCount(Page page) throws Exception;

    //按关键字查询车牌
    public abstract List getPlateByKeywords(Map a) throws Exception;

    List<Plate> findListByLineGroupID(String groupID);

    Long totalCountForBindLine(Page page);

    List<Plate> findListForBindLine(Page page);

}
