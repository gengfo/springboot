package com.javalsj.blog.domain.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @description 固定资产台账视图显示层对象
 * @author WANGJIHONG
 * @date 2018年5月25日 下午5:16:44 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Data
public class GcAmCardVo {
	
	private String id;
	private long recver;
	private String unitid;
	private String oppunitid;
	private String typeid;
	@NotNull(message = "资产名称不允许为空")
	@Size(min = 6, max = 24, message = "资产名称长度应该在6和24位之间")
	private String name;
	private long vchrdate;
	private String billcode;
	private String assetsortid;
	private String measureid;
	private double orgnvalue;
	private double purchaseamount;
	private double checkamount;
	private String dprcttypeid;
	private int dprcmonth;
	private double rmrate;
	private double currdprctvalue;
	private double dprctvalue;
	private short assetstate;
	private double profitrate;
	private int billstate;
	private int year;
	private short period;
	private String creator;
	private String srctype;
	private String srcid;
}
