package com.hengyu.ticket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.CompanyDao;
import com.hengyu.ticket.entity.Company;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-12-02
 *
 */
 @Service
public class CompanyService{
	
	@Autowired
	private CompanyDao companyDao;
	
	/**
	 * 保存一个对象
	 * @param company
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Company company) throws Exception{
		return companyDao.save(company);
	}
	
	/**
	 * 更新一个对象
	 * @param company
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Company company) throws Exception{
		return companyDao.update(company);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回Company对象
	 * @throws Exception
	 */
	public Company find(Integer id) throws Exception{
		return companyDao.find(id);
	}
	
	//公司列表
	public List<Company> findList(Page page) throws Exception{
		List<Company> list = companyDao.findlist(page);
		Long count = companyDao.count(page);
		page.setTotalCount(count);
		page.setData(list);
		return list;
	}
	
	public void setCompanyDao(CompanyDao companyDao){
		this.companyDao = companyDao;
	}
}
