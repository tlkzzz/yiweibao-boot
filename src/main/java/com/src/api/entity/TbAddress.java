package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tb_address")
public class TbAddress implements Serializable{
	
	private Long tmaId;
	private Integer tmaType;
	private Long tmaMemberId;
	private String tmaName;
	private String tmaPhone;
	private String tmaPostcode;
	private Integer tmaProviceId;
	private Integer tmaCityId;
	private Integer tmaCountyId;
	private String  tmaAddress;
	private Integer tmaDefault;
	private Integer tmaStatus;
	private Timestamp tmaAddtime;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tma_id", unique = true, nullable = false)
	public Long getTmaId() {
		return tmaId;
	}
	public void setTmaId(Long tmaId) {
		this.tmaId = tmaId;
	}
	
	@Column(name = "tma_type")
	public Integer getTmaType() {
		return tmaType;
	}
	public void setTmaType(Integer tmaType) {
		this.tmaType = tmaType;
	}
	
	@Column(name = "tma_member_id")
	public Long getTmaMemberId() {
		return tmaMemberId;
	}
	public void setTmaMemberId(Long tmaMemberId) {
		this.tmaMemberId = tmaMemberId;
	}
	
	@Column(name = "tma_name")
	public String getTmaName() {
		return tmaName;
	}
	public void setTmaName(String tmaName) {
		this.tmaName = tmaName;
	}
	
	@Column(name = "tma_phone")
	public String getTmaPhone() {
		return tmaPhone;
	}
	public void setTmaPhone(String tmaPhone) {
		this.tmaPhone = tmaPhone;
	}
	
	@Column(name = "tma_postcode")
	public String getTmaPostcode() {
		return tmaPostcode;
	}
	public void setTmaPostcode(String tmaPostcode) {
		this.tmaPostcode = tmaPostcode;
	}
	
	@Column(name = "tma_provice_id")
	public Integer getTmaProviceId() {
		return tmaProviceId;
	}
	public void setTmaProviceId(Integer tmaProviceId) {
		this.tmaProviceId = tmaProviceId;
	}
	
	@Column(name = "tma_city_id")
	public Integer getTmaCityId() {
		return tmaCityId;
	}
	public void setTmaCityId(Integer tmaCityId) {
		this.tmaCityId = tmaCityId;
	}
	
	@Column(name = "tma_county_id")
	public Integer getTmaCountyId() {
		return tmaCountyId;
	}
	public void setTmaCountyId(Integer tmaCountyId) {
		this.tmaCountyId = tmaCountyId;
	}
	
	
	@Column(name = "tma_address")
	public String getTmaAddress() {
		return tmaAddress;
	}
	public void setTmaAddress(String tmaAddress) {
		this.tmaAddress = tmaAddress;
	}
	
	@Column(name = "tma_default")
	public Integer getTmaDefault() {
		return tmaDefault;
	}
	public void setTmaDefault(Integer tmaDefault) {
		this.tmaDefault = tmaDefault;
	}
	
	@Column(name = "tma_status")
	public Integer getTmaStatus() {
		return tmaStatus;
	}
	public void setTmaStatus(Integer tmaStatus) {
		this.tmaStatus = tmaStatus;
	}
	
	@Column(name = "tma_addtime")
	public Timestamp getTmaAddtime() {
		return tmaAddtime;
	}
	public void setTmaAddtime(Timestamp tmaAddtime) {
		this.tmaAddtime = tmaAddtime;
	}
	public TbAddress() {
		super();
	}
	
	
	

}
