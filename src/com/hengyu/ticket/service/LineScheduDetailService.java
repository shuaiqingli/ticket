package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.LineScheduDetailDao;
import com.hengyu.ticket.entity.LineScheduDetail;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
 @Service
public class LineScheduDetailService{
	
	@Autowired
	private LineScheduDetailDao lineScheduDetailDao;
	
	/**
	 * 保存一个对象
	 * @param lineScheduDetail
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(LineScheduDetail lineScheduDetail) throws Exception{
		return lineScheduDetailDao.save(lineScheduDetail);
	}
	
	/**
	 * 更新一个对象
	 * @param lineScheduDetail
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(LineScheduDetail lineScheduDetail) throws Exception{
		return lineScheduDetailDao.update(lineScheduDetail);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LineScheduDetail对象
	 * @throws Exception
	 */
	public LineScheduDetail find(Integer id) throws Exception{
		return lineScheduDetailDao.find(id);
	}
	
	/**
	 * 按排班规则查询，班次详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<LineScheduDetail> findByLineScheduleRule(Integer id) throws Exception{
		return lineScheduDetailDao.findByLineScheduleRule(id);
	}
	
	public void setLineScheduDetailDao(LineScheduDetailDao lineScheduDetailDao){
		this.lineScheduDetailDao = lineScheduDetailDao;
	}
}
