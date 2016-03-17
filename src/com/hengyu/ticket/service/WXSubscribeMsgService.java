package com.hengyu.ticket.service;
import com.hengyu.ticket.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.WXSubscribeMsgDao;
import com.hengyu.ticket.entity.WXSubscribeMsg;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李冠锋 2016-02-24
 *
 */
 @Service
public class WXSubscribeMsgService{
	
	@Autowired
	private WXSubscribeMsgDao wxSubscribeMsgDao;
	
	/**
	 * 保存一个对象
	 * @param wxSubscribeMsg
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(WXSubscribeMsg wxSubscribeMsg) throws Exception{
		return wxSubscribeMsgDao.save(wxSubscribeMsg);
	}
	
	/**
	 * 更新一个对象
	 * @param wxSubscribeMsg
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(WXSubscribeMsg wxSubscribeMsg) throws Exception{
		return wxSubscribeMsgDao.update(wxSubscribeMsg);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回WxSubscribeMsg对象
	 * @throws Exception
	 */
	public WXSubscribeMsg find(Integer id) throws Exception{
		return wxSubscribeMsgDao.find(id);
	}

	public List<WXSubscribeMsg> findList(Page page) throws Exception {
		page.setTotalCount(wxSubscribeMsgDao.findListCount(page));
		return  wxSubscribeMsgDao.findList(page);
	}

	public List<Map<String,String>> findSubscribeMsg() throws Exception {
		return wxSubscribeMsgDao.findSubscribeMsg();
	}

	
	public void setWxSubscribeMsgDao(WXSubscribeMsgDao wxSubscribeMsgDao){
		this.wxSubscribeMsgDao = wxSubscribeMsgDao;
	}
}
