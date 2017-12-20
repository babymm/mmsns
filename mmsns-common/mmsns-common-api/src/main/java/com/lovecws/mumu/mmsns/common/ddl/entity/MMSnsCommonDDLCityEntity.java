package com.lovecws.mumu.mmsns.common.ddl.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 数据字典 城市
 * @date 2017-11-24 9:27
 * mc_ddl_city
 */
public class MMSnsCommonDDLCityEntity extends PersistentEntity {

	private Integer cId;// 主键id（自增）
    private String cCode;// 市编码
    private String pCode;// 省编码

    private String cName;// 市全写
    private String cSname;// 市的简写
    private String cPinying;// 市的简拼

    private String cPy;// 市的英文缩写

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcSname() {
		return cSname;
	}

	public void setcSname(String cSname) {
		this.cSname = cSname;
	}

	public String getcPinying() {
		return cPinying;
	}

	public void setcPinying(String cPinying) {
		this.cPinying = cPinying;
	}

	public String getcPy() {
		return cPy;
	}

	public void setcPy(String cPy) {
		this.cPy = cPy;
	}
	
}
