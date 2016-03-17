package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2015-10-08
 */
public class Driver {

	// ------------------字段-----------------
	// 编号
	private String id;
	// 姓名
	private String dname;
	// 手机号码
	private String dmobile;
	//密码
	private String password;
	//token
	private String token;
	// 身份证
	private String idcard;
	//是否停用
	private Integer isstop;
	//停用名称
	private String isstopname;
	//创建日期
	private String makedate;
	//拼音
	private String pyname;

	private Integer tcid;
	private String companyname;



	// ----------------构造方法----------------

	public Driver() {

	}

	// -------------- get/set方法 --------------


	public Integer getTcid() {
		return tcid;
	}

	public void setTcid(Integer tcid) {
		this.tcid = tcid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	/**
	 * 获取id
	 * 
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取dname
	 * 
	 * @return String
	 */
	public String getDname() {
		return dname;
	}

	/**
	 * 设置dname
	 * 
	 * @param dname
	 */
	public void setDname(String dname) {
		this.dname = dname;
	}

	/**
	 * 获取dmobile
	 * 
	 * @return String
	 */
	public String getDmobile() {
		return dmobile;
	}

	/**
	 * 设置dmobile
	 * 
	 * @param dmobile
	 */
	public void setDmobile(String dmobile) {
		this.dmobile = dmobile;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Integer getIsstop() {
		return isstop;
	}

	public void setIsstop(Integer isstop) {
		this.isstop = isstop;
	}

	public String getIsstopname() {
		return isstopname;
	}

	public void setIsstopname(String isstopname) {
		this.isstopname = isstopname;
	}

	public String getMakedate() {
		return makedate;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public String getPyname() {
		return pyname;
	}

	public void setPyname(String pyname) {
		this.pyname = pyname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}