package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Promotion;
import com.hengyu.ticket.entity.TicketStore;

/**
 * @author 李冠锋 2015-11-09
 */
public interface PromotionDao {

    /**
     * 保存一个对象
     *
     * @param promotion
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    int save(Promotion promotion) throws Exception;

    /**
     * 更新一个对象
     *
     * @param promotion
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    int update(Promotion promotion) throws Exception;

    //重置默认规则
    int resetDefault(Integer lmid) throws Exception;

    /**
     * 根据主键查询一个对象
     *
     * @param id 主键
     * @return 返回Promotion对象
     * @throws Exception
     */
    Promotion find(String id) throws Exception;

    // 获取分页列表
    List<Promotion> findList(Page page) throws Exception;

    List<Promotion> findByLMID(Integer lmid) throws Exception;

    // 获取记录条数
    Long totalCount(Page page) throws Exception;

    //检查优惠规则是否被占用
    Promotion checketPromotionExists(Promotion promotion) throws Exception;

    //按日期查询优惠规则
    List<Promotion> findByTicketStore(TicketStore ts) throws Exception;

}
