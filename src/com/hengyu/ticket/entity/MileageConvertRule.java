package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2015-11-09
 */
public class MileageConvertRule {

	// ------------------字段-----------------

	private Integer id;
	private Integer mileagecvt;
	private String vprice;
	private Integer isenable;
	private String isenablename;
	private String makedate;

	// ----------------构造方法----------------

	public MileageConvertRule() {

	}

	// -------------- get/set方法 --------------

	/**
	 * 获取id
	 * 
	 * @return Integer
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取mileagecvt
	 * 
	 * @return Integer
	 */
	public Integer getMileagecvt() {
		return mileagecvt;
	}

	/**
	 * 设置mileagecvt
	 * 
	 * @param mileagecvt
	 */
	public void setMileagecvt(Integer mileagecvt) {
		this.mileagecvt = mileagecvt;
	}

	/**
	 * 获取vprice
	 * 
	 * @return String
	 */
	public String getVprice() {
		return vprice;
	}

	/**
	 * 设置vprice
	 * 
	 * @param vprice
	 */
	public void setVprice(String vprice) {
		this.vprice = vprice;
	}

	/**
	 * 获取makedate
	 * 
	 * @return String
	 */
	public String getMakedate() {
		return makedate;
	}

	/**
	 * 设置makedate
	 * 
	 * @param makedate
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Integer getIsenable() {
		return isenable;
	}

	public void setIsenable(Integer isenable) {
		this.isenable = isenable;
	}

	public String getIsenablename() {
		return isenablename;
	}

	public void setIsenablename(String isenablename) {
		this.isenablename = isenablename;
	}

}