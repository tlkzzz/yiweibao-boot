package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_company_set")
public class TbCompanySet implements Serializable{

	
	private Long tcsId;
	private Long tcsCompanyId;
	private Double tcsPayment; 
	private Integer tcsMinute;
	public TbCompanySet(Long tcsId, Long tcsCompanyId, Double tcsPayment, Integer tcsMinute) {
		super();
		this.tcsId = tcsId;
		this.tcsCompanyId = tcsCompanyId;
		this.tcsPayment = tcsPayment;
		this.tcsMinute = tcsMinute;
	}
	
	public TbCompanySet(Long tcsId,Double tcsPayment, Integer tcsMinute) {
		super();
		this.tcsId = tcsId;
		this.tcsPayment = tcsPayment;
		this.tcsMinute = tcsMinute;
	}
	public TbCompanySet() {
		super();
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tcs_id", unique = true, nullable = false)
	public Long getTcsId() {
		return tcsId;
	}
	public void setTcsId(Long tcsId) {
		this.tcsId = tcsId;
	}
	
	@Column(name = "tcs_company_id")
	public Long getTcsCompanyId() {
		return tcsCompanyId;
	}
	public void setTcsCompanyId(Long tcsCompanyId) {
		this.tcsCompanyId = tcsCompanyId;
	}
	
	@Column(name = "tcs_payment")
	public Double getTcsPayment() {
		return tcsPayment;
	}
	public void setTcsPayment(Double tcsPayment) {
		this.tcsPayment = tcsPayment;
	}
	
	@Column(name = "tcs_minute")
	public Integer getTcsMinute() {
		return tcsMinute;
	}
	public void setTcsMinute(Integer tcsMinute) {
		this.tcsMinute = tcsMinute;
	}

	@Override
	public String toString() {
		return "TbCompanySet [tcsId=" + tcsId + ", tcsCompanyId=" + tcsCompanyId + ", tcsPayment=" + tcsPayment
				+ ", tcsMinute=" + tcsMinute + "]";
	}
	
	
	
	
	
}
