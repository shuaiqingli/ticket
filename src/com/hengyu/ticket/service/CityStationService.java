package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.CityStationDao;
import com.hengyu.ticket.dao.MakeConfDao;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.MakeConf;
import com.hengyu.ticket.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 城市站点管理
 * 
 * @author 李冠锋 2015-09-26
 *
 */
@Service
public class CityStationService {

	final static int NUM = 6;

	@Autowired
	private CityStationDao cityStationDao;
	@Autowired
	private MakeConfDao makeConfDao;
	@Autowired
	private MakeConfService confService;

	/**
	 * 保存一个对象
	 * 
	 * @param cityStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(CityStation cityStation) throws Exception {
		Long count = cityStationDao.findCityByNameCheck(cityStation.getCityname());
		if (count == null || count <= 0) {
			return confService.findMakeConf(cityStation, 6, new Execute<Integer,CityStation>() {
				@Override
				public Integer execute(MakeConf mc, CityStation o, String id) throws Exception {
					o.setId(id);
					int result = cityStationDao.save(o);
					return result;
				}
			});
		}
		return -1;
	}

	/**
	 * 更新一个对象
	 * 
	 * @param cityStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(CityStation cityStation) throws Exception {
		return cityStationDao.update(cityStation);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param iD
	 *            主键
	 * @return 返回CityStation对象
	 * @throws Exception
	 */
	public CityStation find(String iD) throws Exception {
		return cityStationDao.find(iD);
	}

	/**
	 * 查询城市站点列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<CityStation> findList(Page page) throws Exception {
		List<CityStation> list = cityStationDao.findList(page);
		Long totalCount = cityStationDao.totalCount(page);
		page.setData(list);
		page.setTotalCount(totalCount);
		return list;
	}

	/**
	 * 获取所有城市
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CityStation> findAllCity() throws Exception {
		return cityStationDao.findAllCity();
	}

	/**
	 * 获取所有城市
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getAllCity(CityStation c) throws Exception {
		return cityStationDao.getAllCity(c);
	}
	
	//获取站点地址
	public List<Map<String, String>> getAddressByIds(String... ids) throws Exception {
		return cityStationDao.getAddressByIds(Arrays.asList(ids));
	}
	
	public List getStartStationByCityid(String begincityid,String endcityid,Integer sort) throws Exception {
		return cityStationDao.getStartStationByCityid(begincityid,endcityid,sort);
	}

	public void setCityStationDao(CityStationDao cityStationDao) {
		this.cityStationDao = cityStationDao;
	}

	// 查找城市站点
	public List getCityStationByKeywords(Map a) throws Exception {
		return cityStationDao.getCityStationByKeywords(a);
	}

	public List<CityStation> findListForBindAdmin(Page page){
		Long totalCount = cityStationDao.totalCountForBindAdmin(page);
		page.setTotalCount(totalCount);
		return cityStationDao.findListForBindAdmin(page);
	}

	public List<Map> findListByUserid(String userid){
		return cityStationDao.findListByUserid(userid);
	}

	public List<CityStation> findByParent(String pid) throws Exception{
		return cityStationDao.findByParent(pid);
	}
}
