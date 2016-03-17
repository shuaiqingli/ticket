package com.hengyu.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.PromotionLineDao;
import com.hengyu.ticket.entity.PromotionLine;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
@Service
public class PromotionLineService {

	@Autowired
	private PromotionLineDao promotionLineDao;

	/**
	 * 保存一个对象
	 * 
	 * @param promotionLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(PromotionLine promotionLine) throws Exception {
		return promotionLineDao.save(promotionLine);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param promotionLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(PromotionLine promotionLine) throws Exception {
		return promotionLineDao.update(promotionLine);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回PromotionLine对象
	 * @throws Exception
	 */
	public PromotionLine find(Integer id) throws Exception {
		return promotionLineDao.find(id);
	}

	public void setPromotionLineDao(PromotionLineDao promotionLineDao) {
		this.promotionLineDao = promotionLineDao;
	}

	// 按促销ID查线路
	public List getPromotionLineByPID(String pid) throws Exception {
		return promotionLineDao.getPromotionLineByPID(pid);
	}
}
