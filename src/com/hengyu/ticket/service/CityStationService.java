package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.CityStationDao;
import com.hengyu.ticket.dao.MakeConfDao;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.MakeConf;
import com.hengyu.ticket.entity.Page;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
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

	public List getStartStationList(String begincityid,String endcityid, String endstationid) throws Exception {
		return cityStationDao.getStartStationList(begincityid, endcityid, endstationid);
	}

	public List getEndStationList(String endcityid,String begincityid, String beginstationid) throws Exception {
		return cityStationDao.getEndStationList(endcityid, begincityid, beginstationid);
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

	/**
	 * 根据ID查询城市名称和ID(在ParentId为null情况)实则参数的ParentID为查询的主键ID
	 * @param ParentID
	 * @return id和city_name
	 */
	public Map<String, Object> findCityNameAndIdByParentId(String ParentID) {
		return cityStationDao.findCityNameAndIdByParentId(ParentID);
	}

	/**
	 * 根据城市站点管理对象查询城市名称和城市ID
	 * @param cityStation
	 * @return map的key为id和city_name
	 */
	public Map<String, Object> findCityNameAndIdByCityStation(CityStation cityStation) {
		Map<String,Object> map = new HashMap<>();
		if(cityStation!=null){
			//根据现有数据发现,id查询到的数据如果ParentID为空那么就是城市名称和城市ID,如果不为空 那么拿到ParentID再去查这个ID的ParentID为空的那个数据
			String parentid = cityStation.getParentid();
			if(StringUtils.isNotEmpty(parentid)){
				Map<String,Object> resultMap = findCityNameAndIdByParentId(parentid);
				map.put("city_id",resultMap.get("id"));
				map.put("city_name",resultMap.get("city_name"));
			}else{
				map.put("city_id",cityStation.getId());
				map.put("city_name",cityStation.getCityname());
			}
		}
		return map;
	}
}
