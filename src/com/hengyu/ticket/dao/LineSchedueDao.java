package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.LineSchedue;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-09-30
 *
 */
public interface LineSchedueDao {
	
	/**
	 * 保存一个对象
	 * @param lineSchedue
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(LineSchedue lineSchedue) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param lineSchedue
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(LineSchedue lineSchedue) throws Exception;
	
	/**
	 * 删除排班
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(Integer id) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @return 返回LineSchedue对象
	 * @throws Exception
	 */
	LineSchedue find(Integer id) throws Exception;
	
	/**
	 * 按日期匹配排班
	 * @return
	 * @throws Exception
	 */
	List<LineSchedue> findRuleByLineSchedue(LineSchedue ls) throws Exception;
	
	/**
	 * 查询最后一个排班
	 * @param lineSchedue
	 * @return
	 * @throws Exception
	 */
	LineSchedue findExistsSchedue(LineSchedue lineSchedue) throws Exception;
	
	//按结束日期查询下一个排班
	LineSchedue findByEndDate(LineSchedue lineSchedue) throws Exception;
	
	//按开始日期查询上一个排班
	LineSchedue findByBeginDate(LineSchedue lineSchedue) throws Exception;
	
	// 查询最后的排班
	LineSchedue findLastDate(LineSchedue lineSchedue) throws Exception;
	
	/**
	 * 查询排班列表
	 * @return
	 * @throws Exception
	 */
	List<LineSchedue> findList(@Param("lmid") Integer lmid) throws Exception;
	
	/**
	 * 统计条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Long totalCount(Page page) throws Exception;
}
