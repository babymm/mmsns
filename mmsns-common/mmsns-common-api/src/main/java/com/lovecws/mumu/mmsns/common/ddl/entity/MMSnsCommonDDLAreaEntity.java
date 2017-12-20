package com.lovecws.mumu.mmsns.common.ddl.entity;

import com.lovecws.mumu.core.entity.PersistentEntity;

/**
 * @author babymm
 * @version 1.0-SNAPSHOT
 * @Description: 数据字典 地区
 * @date 2017-11-24 9:27
 * mc_ddl_area
 */
public class MMSnsCommonDDLAreaEntity extends PersistentEntity {

	private Integer aId;// 主键id（自增）
	private String aCode;// 区编码
	private String cCode;// 市编码

	private String aName;// 区全写
	private String aSname;// 区的简写
	private String aPinying;// 区的简拼

	private String aPy;// 区的英文缩写

	public Integer getaId() {
		return aId;
	}

	public void setaId(final Integer aId) {
		this.aId = aId;
	}

	public String getaCode() {
		return aCode;
	}

	public void setaCode(final String aCode) {
		this.aCode = aCode;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(final String cCode) {
		this.cCode = cCode;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(final String aName) {
		this.aName = aName;
	}

	public String getaSname() {
		return aSname;
	}

	public void setaSname(final String aSname) {
		this.aSname = aSname;
	}

	public String getaPinying() {
		return aPinying;
	}

	public void setaPinying(final String aPinying) {
		this.aPinying = aPinying;
	}

	public String getaPy() {
		return aPy;
	}

	public void setaPy(final String aPy) {
		this.aPy = aPy;
	}
}
