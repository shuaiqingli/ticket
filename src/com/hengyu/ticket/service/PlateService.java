package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.PlateDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Plate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 李冠锋 2015-10-08
 */
@Service
public class PlateService {

    @Autowired
    private PlateDao plateDao;

    /**
     * 保存一个对象
     *
     * @param plate
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int save(Plate plate) throws Exception {
        return plateDao.save(plate);
    }

    /**
     * 更新一个对象
     *
     * @param plate
     * @return 返回受影响行数 (int)
     * @throws Exception
     */
    public int update(Plate plate) throws Exception {
        return plateDao.update(plate);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int delete(Integer id) throws Exception {
        return plateDao.delete(id);
    }

    /**
     * 根据主键查询一个对象
     *
     * @param id 主键
     * @return 返回Plate对象
     * @throws Exception
     */
    public Plate find(Integer id) throws Exception {
        return plateDao.find(id);
    }

    /**
     * 查询车牌列表
     *
     * @param page
     * @return
     * @throws Exception
     */
    public List<Plate> findlist(Page page) throws Exception {
        Long totalCount = plateDao.totalCount(page);
        page.setTotalCount(totalCount);
        return plateDao.findlist(page);
    }

    public void setPlateDao(PlateDao plateDao) {
        this.plateDao = plateDao;
    }

    public List getPlateByKeywords(Map a) throws Exception {
        return plateDao.getPlateByKeywords(a);
    }

    public List<Plate> findListByLineGroupID(String groupID) {
        return plateDao.findListByLineGroupID(groupID);
    }

    public List<Plate> findListForBindLine(Page page) {
        Long totalCount = plateDao.totalCountForBindLine(page);
        page.setTotalCount(totalCount);
        return plateDao.findListForBindLine(page);
    }
}
