package com.javalsj.blog.domain.dto;

import lombok.Data;

/**
 * @description 固定资产台账传输对象
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:12:11 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Data
public class GcAmCardDTO {
	private String id;
	private Long recver;
	private String unitid;
	private String oppunitid;
	private String typeid;
	private String name;
	private Long vchrdate;
	private String billcode;
	private String assetsortid;
	private String measureid;
	private Double orgnvalue;
	private Double purchaseamount;
	private Double checkamount;
	private String dprcttypeid;
	private Integer dprcmonth;
	private Double rmrate;
	private Double currdprctvalue;
	private Double dprctvalue;
	private Short assetstate;
	private Double profitrate;
	private Integer billstate;
	private Integer year;
	private Short period;
	private String creator;
	private String srctype;
	private String srcid;
}
