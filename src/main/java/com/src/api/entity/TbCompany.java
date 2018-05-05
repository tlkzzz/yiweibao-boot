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
@Table(name="tb_company")
public class TbCompany implements Serializable{
	private Long tcId;
	private String tcName;
	private String tcLogo;
	private Long tcProvId;
	private Long tcCityId;
	private Long tcRegionId;
	private String tcAddress;
	private Timestamp tcAddDate;
	private Integer tcStatus;
	private Integer tcTyep;
	private String tcLoginUser;
	private String tcLoginPass;
	private String tcCode;
	private String tcContactName;
	private String tcContactPhone;
	
	private Integer tcSmsCount; //短信余量
	private Double  tcAmount;  //账户余额
	
	
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tc_id", unique = true, nullable = false)
	public Long getTcId() {
		return tcId;
	}
	public void setTcId(Long tcId) {
		this.tcId = tcId;
	}
	
	@Column(name = "tc_name")
	public String getTcName() {
		return tcName;
	}
	public void setTcName(String tcName) {
		this.tcName = tcName;
	}
	
	@Column(name = "tc_logo")
	public String getTcLogo() {
		return tcLogo;
	}
	public void setTcLogo(String tcLogo) {
		this.tcLogo = tcLogo;
	}
	
	@Column(name = "tc_prov_id")
	public Long getTcProvId() {
		return tcProvId;
	}
	public void setTcProvId(Long tcProvId) {
		this.tcProvId = tcProvId;
	}
	
	@Column(name = "tc_city_id")
	public Long getTcCityId() {
		return tcCityId;
	}
	public void setTcCityId(Long tcCityId) {
		this.tcCityId = tcCityId;
	}
	@Column(name = "tc_region_id")
	public Long getTcRegionId() {
		return tcRegionId;
	}
	public void setTcRegionId(Long tcRegionId) {
		this.tcRegionId = tcRegionId;
	}
	@Column(name = "tc_address")
	public String getTcAddress() {
		return tcAddress;
	}
	public void setTcAddress(String tcAddress) {
		this.tcAddress = tcAddress;
	}
	@Column(name = "tc_add_date")
	public Timestamp getTcAddDate() {
		return tcAddDate;
	}
	public void setTcAddDate(Timestamp tcAddDate) {
		this.tcAddDate = tcAddDate;
	}
	
	@Column(name = "tc_status")
	public Integer getTcStatus() {
		return tcStatus;
	}
	public void setTcStatus(Integer tcStatus) {
		this.tcStatus = tcStatus;
	}
	
	@Column(name = "tc_type")
	public Integer getTcTyep() {
		return tcTyep;
	}
	public void setTcTyep(Integer tcTyep) {
		this.tcTyep = tcTyep;
	}
	
	@Column(name = "tc_login_user")
	public String getTcLoginUser() {
		return tcLoginUser;
	}
	public void setTcLoginUser(String tcLoginUser) {
		this.tcLoginUser = tcLoginUser;
	}
	
	@Column(name = "tc_login_pass")
	public String getTcLoginPass() {
		return tcLoginPass;
	}
	public void setTcLoginPass(String tcLoginPass) {
		this.tcLoginPass = tcLoginPass;
	}
	
	@Column(name = "tc_code")
	public String getTcCode() {
		return tcCode;
	}
	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}
	
	@Column(name = "tc_contact_name")
	public String getTcContactName() {
		return tcContactName;
	}
	public void setTcContactName(String tcContactName) {
		this.tcContactName = tcContactName;
	}
	@Column(name = "tc_contact_phone")
	public String getTcContactPhone() {
		return tcContactPhone;
	}
	public void setTcContactPhone(String tcContactPhone) {
		this.tcContactPhone = tcContactPhone;
	}
	
	

	@Column(name = "tc_sms_count")
	public Integer getTcSmsCount() {
		return tcSmsCount;
	}
	public void setTcSmsCount(Integer tcSmsCount) {
		this.tcSmsCount = tcSmsCount;
	}
	
	@Column(name = "tc_amount", precision=8, scale=2)
	public Double getTcAmount() {
		return tcAmount;
	}
	public void setTcAmount(Double tcAmount) {
		this.tcAmount = tcAmount;
	}
	
	
	public TbCompany() {
		super();
	}
	public TbCompany(Long tcId, String tcName, String tcLogo, Long tcProvId,
			Long tcCityId, Long tcRegionId, String tcAddress,
			Timestamp tcAddDate, Integer tcStatus, Integer tcTyep,
			String tcLoginUser, String tcLoginPass, String tcCode,
			String tcContactName, String tcContactPhone,Integer tcSmsCount,Double tcAmount) {
		super();
		this.tcId = tcId;
		this.tcName = tcName;
		this.tcLogo = tcLogo;
		this.tcProvId = tcProvId;
		this.tcCityId = tcCityId;
		this.tcRegionId = tcRegionId;
		this.tcAddress = tcAddress;
		this.tcAddDate = tcAddDate;
		this.tcStatus = tcStatus;
		this.tcTyep = tcTyep;
		this.tcLoginUser = tcLoginUser;
		this.tcLoginPass = tcLoginPass;
		this.tcCode = tcCode;
		this.tcContactName = tcContactName;
		this.tcContactPhone = tcContactPhone;
		this.tcSmsCount = tcSmsCount;
		this.tcAmount = tcAmount;
	}

}
