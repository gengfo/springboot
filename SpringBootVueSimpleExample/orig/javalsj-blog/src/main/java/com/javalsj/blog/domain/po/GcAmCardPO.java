package com.javalsj.blog.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @description 固定资产台账持久化对象
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:16:07 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Entity
@Table(name="GC_AMCARD")
@Data
public class GcAmCardPO {
	/**
	 * 主键
	 */
	@Id()
	@Column(length=32, name="ID")
	private String id;
	@Column(name="RECVER")
	private long recver;
	@Column(length=32, name="UNITID")
	private String unitid;
	@Column(length=32, name="OPPUNITID")
	private String oppunitid;
	@Column(length=32, name="TYPEID")
	private String typeid;
	@Column(length=30, name="NAME", nullable=false)
	private String name;
	@Column(name="VCHRDATE")
	private long vchrdate;
	@Column(length=30, name="BILLCODE")
	private String billcode;
	@Column(name="ASSETSORTID")
	private String assetsortid;
	@Column(length=32, name="MEASUREID")
	private String measureid;
	@Column(name="ORGNVALUE", precision=12, scale=2)
	private double orgnvalue;
	@Column(name="PURCHASEAMOUNT", precision=12, scale=2)
	private double purchaseamount;
	@Column(name="CHECKAMOUNT", precision=12, scale=2)
	private double checkamount;
	@Column(name="DPRCTTYPEID", length=32)
	private String dprcttypeid;
	@Column(name="DPRCMONTH")
	private int dprcmonth;
	@Column(name="RMRATE", precision=4, scale=2)
	private double rmrate;
	@Column(name="CURRDPRCTVALUE", precision=12, scale=2)
	private double currdprctvalue;
	@Column(name="DPRCTVALUE", precision=12, scale=2)
	private double dprctvalue;
	@Column(name="ASSETSTATE")
	private short assetstate;
	@Column(name="PROFITRATE", precision=12, scale=2)
	private double profitrate;
	@Column(name="BILLSTATE")
	private int billstate;
	@Column(name="YEAR")
	private int year;
	@Column(name="PERIOD")
	private short period;
	@Column(name="CREATOR", length=10)
	private String creator;
	@Column(name="SRCTYPE", length=20)
	private String srctype;
	@Column(name="SRCID", length=32)
	private String srcid;
}
