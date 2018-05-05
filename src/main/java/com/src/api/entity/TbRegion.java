package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 区域运费
 * @author Administrator
 *
 */


@Entity
@Table(name="tb_region")
public class TbRegion implements Serializable{

	private Long trId;
	private String trName;
	private Double trFreight;
	private Integer trStatus;
	private Timestamp trAddtime;
	private Long trAdduser;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tr_id", unique = true, nullable = false)
	public Long getTrId() {
		return trId;
	}
	public void setTrId(Long trId) {
		this.trId = trId;
	}
	
	@Column(name = "tr_name")
	public String getTrName() {
		return trName;
	}
	public void setTrName(String trName) {
		this.trName = trName;
	}
	
	@Column(name = "tr_freight")
	public Double getTrFreight() {
		return trFreight;
	}
	public void setTrFreight(Double trFreight) {
		this.trFreight = trFreight;
	}
	
	@Column(name = "tr_status")
	public Integer getTrStatus() {
		return trStatus;
	}
	public void setTrStatus(Integer trStatus) {
		this.trStatus = trStatus;
	}
	
	@Column(name = "tr_addtime")
	public Timestamp getTrAddtime() {
		return trAddtime;
	}
	public void setTrAddtime(Timestamp trAddtime) {
		this.trAddtime = trAddtime;
	}
	
	@Column(name = "tr_adduser")
	public Long getTrAdduser() {
		return trAdduser;
	}
	public void setTrAdduser(Long trAdduser) {
		this.trAdduser = trAdduser;
	}
	public TbRegion() {
		super();
	}
	
	
	
	
}
