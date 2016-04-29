package com.hengyu.ticket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hengyu.ticket.dao.PromotionDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Promotion;
import com.hengyu.ticket.entity.PromotionTime;
import com.hengyu.ticket.util.RequestTool;

/**
 * @author 李冠锋 2015-11-09
 */
@Service
public class PromotionService {

    @Autowired
    private PromotionDao promotionDao;
    @Autowired
    private PromotionTimeService promotiontimeService;

    /**
     * 保存一个对象
     *
     * @param promotion
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int save(Promotion promotion, String[] begintime, String[] endtime, double[] reducesum, Integer[] couponpercents) throws Exception {
        if (promotion.getIsdefault() != null && promotion.getIsdefault() == 1) {
            promotionDao.resetDefault(promotion.getLmid());
        }
        // 添加时间
        int ret = promotionDao.save(promotion);

        if (begintime != null && begintime.length > 0) {
            for (int i = 0; i < begintime.length; i++) {
                PromotionTime pt = new PromotionTime();
                pt.setPid(promotion.getId());
                pt.setBegintime(begintime[i]);
                pt.setEndtime(endtime[i]);
                pt.setReducesum(reducesum[i]);
                pt.setCouponpercent(couponpercents[i]);

                Assert.notNull(pt.getBegintime(), "时间段，开始时间不能为空！");
                Assert.notNull(pt.getEndtime(), "时间段，结束时间不能为空！");
                Assert.notNull(pt.getReducesum(), "优惠金额不能为空！");
                Assert.notNull(pt.getCouponpercent(), "优惠票百分比不能为空！");
                Assert.isTrue(couponpercents[i] >= 0 && couponpercents[i] <= 100, "优惠票填写范围不正确！");

                promotiontimeService.save(pt);
            }
        }
        return ret;
    }

    /**
     * 更新一个对象
     *
     * @param promotion
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int update(Promotion promotion, String[] begintime, String[] endtime, double[] reducesum, Integer[] couponpercents) throws Exception {
        if (promotion.getIsdefault() != null && promotion.getIsdefault() == 1) {
            promotionDao.resetDefault(promotion.getLmid());
        }
        // 添加时间
        if (begintime != null && begintime.length > 0) {
            //先删除时间
            int delpt = promotiontimeService.delPromotionByPID(promotion.getId());
            for (int i = 0; i < begintime.length; i++) {
                PromotionTime pt = new PromotionTime();
                pt.setPid(promotion.getId());
                pt.setBegintime(begintime[i]);
                pt.setEndtime(endtime[i]);
                pt.setReducesum(reducesum[i]);
                pt.setCouponpercent(couponpercents[i]);
                Assert.isTrue(couponpercents[i] >= 0 && couponpercents[i] <= 100, "优惠票填写范围不正确！");
                Assert.notNull(pt.getBegintime(), "时间段，开始时间不能为空！");
                Assert.notNull(pt.getEndtime(), "时间段，结束时间不能为空！");
                Assert.notNull(pt.getReducesum(), "优惠金额不能为空！");
                Assert.notNull(pt.getCouponpercent(), "优惠票百分比不能为空！");

                Assert.isTrue(promotiontimeService.save(pt) == 1);
            }
        }
        return promotionDao.update(promotion);
    }

    /**
     * 根据主键查询一个对象
     *
     * @param id 主键
     * @return 返回Promotion对象
     * @throws Exception
     */
    public Promotion find(String id) throws Exception {
        return promotionDao.find(id);
    }

    public void setPromotionDao(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    // 获取分页列表
    public List<Promotion> findList(Page page) throws Exception {
        List<Promotion> list = promotionDao.findList(page);
        page.setData(list);
        page.setTotalCount(promotionDao.totalCount(page));
        return list;
    }

    public List<Promotion> findByLMID(Integer lmid) throws Exception {
        List<Promotion> list = promotionDao.findByLMID(lmid);
        return list;
    }

    // 获取记录条数
    public Long totalCount(Page page) throws Exception {
        return promotionDao.totalCount(page);
    }

    //检查优惠规则是否被占用
    public Promotion findChecketPromotionExists(Promotion promotion) throws Exception {
        return promotionDao.checketPromotionExists(promotion);
    }
}
