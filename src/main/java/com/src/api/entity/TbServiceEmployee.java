package com.src.api.entity;
// Generated 2015-8-31 15:24:09 by Hibernate Tools 4.3.1

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TbServiceEmployee generated by hbm2java
 */
@Entity
@Table(name = "tb_service_employee")
public class TbServiceEmployee implements java.io.Serializable {

	private Long tseId;
	private Long tseUnitid;
	private String tspName;
	private String tseName;
	private String tseNumber;
	private Integer tseSex;
	private String tseMobile;
	private String tseLoginUser;
	private String tseLoginPass;
	private Timestamp tseAddDate;
	private Long tseAddPerson;
	private Integer tseStatus;
	private String tsePhoto;
	private Long tseDepartment;
	private Long tsePosition;
	private String twName;
	private Long tseProvinceId;
	private Long tseCityId;
	private Long tseCountyId;
	private String tseAddress;
	
	

	private String dept;
	private Integer tseWorkType;
	
	
	@Column(name = "tse_work_type", nullable = false)
	public Integer getTseWorkType() {
		return tseWorkType;
	}

	public void setTseWorkType(Integer tseWorkType) {
		this.tseWorkType = tseWorkType;
	}
	
	
	
	@Transient
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Transient
	public String getTwName() {
		return twName;
	}

	public void setTwName(String twName) {
		this.twName = twName;
	}

	public TbServiceEmployee() {
	}

	public TbServiceEmployee(Long tseUnitid, String tseName, Timestamp tseAddDate, Long tseAddPerson, Long tseDepartment,
			Long tsePosition) {
		this.tseUnitid = tseUnitid;
		this.tseName = tseName;
		this.tseAddDate = tseAddDate;
		this.tseAddPerson = tseAddPerson;
		this.tseDepartment = tseDepartment;
		this.tsePosition = tsePosition;
	}

	public TbServiceEmployee(Long tseUnitid, String tseName, String tseNumber, Integer tseSex, String tseMobile,
			String tseLoginUser, String tseLoginPass, Timestamp tseAddDate, Long tseAddPerson, Integer tseStatus,
			String tsePhoto, Long tseDepartment, Long tsePosition) {
		this.tseUnitid = tseUnitid;
		this.tseName = tseName;
		this.tseNumber = tseNumber;
		this.tseSex = tseSex;
		this.tseMobile = tseMobile;
		this.tseLoginUser = tseLoginUser;
		this.tseLoginPass = tseLoginPass;
		this.tseAddDate = tseAddDate;
		this.tseAddPerson = tseAddPerson;
		this.tseStatus = tseStatus;
		this.tsePhoto = tsePhoto;
		this.tseDepartment = tseDepartment;
		this.tsePosition = tsePosition;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "tse_id", unique = true, nullable = false)
	public Long getTseId() {
		return this.tseId;
	}

	public void setTseId(Long tseId) {
		this.tseId = tseId;
	}

	@Column(name = "tse_unitid", nullable = false)
	public Long getTseUnitid() {
		return this.tseUnitid;
	}

	public void setTseUnitid(Long tseUnitid) {
		this.tseUnitid = tseUnitid;
	}

	@Column(name = "tse_name", nullable = false, length = 50)
	public String getTseName() {
		return this.tseName;
	}

	public void setTseName(String tseName) {
		this.tseName = tseName;
	}

	@Column(name = "tse_number", length = 20)
	public String getTseNumber() {
		return this.tseNumber;
	}

	public void setTseNumber(String tseNumber) {
		this.tseNumber = tseNumber;
	}

	@Column(name = "tse_sex")
	public Integer getTseSex() {
		return this.tseSex;
	}

	public void setTseSex(Integer tseSex) {
		this.tseSex = tseSex;
	}

	@Column(name = "tse_mobile", length = 21)
	public String getTseMobile() {
		return this.tseMobile;
	}

	public void setTseMobile(String tseMobile) {
		this.tseMobile = tseMobile;
	}

	@Column(name = "tse_login_user", length = 21)
	public String getTseLoginUser() {
		return this.tseLoginUser;
	}

	public void setTseLoginUser(String tseLoginUser) {
		this.tseLoginUser = tseLoginUser;
	}

	@Column(name = "tse_login_pass", length = 50)
	public String getTseLoginPass() {
		return this.tseLoginPass;
	}

	public void setTseLoginPass(String tseLoginPass) {
		this.tseLoginPass = tseLoginPass;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "tse_add_date", nullable = false, length = 19)
	public Timestamp getTseAddDate() {
		return this.tseAddDate;
	}

	public void setTseAddDate(Timestamp tseAddDate) {
		this.tseAddDate = tseAddDate;
	}

	@Column(name = "tse_add_person", nullable = false)
	public Long getTseAddPerson() {
		return this.tseAddPerson;
	}

	public void setTseAddPerson(Long tseAddPerson) {
		this.tseAddPerson = tseAddPerson;
	}

	@Column(name = "tse_status")
	public Integer getTseStatus() {
		return this.tseStatus;
	}

	public void setTseStatus(Integer tseStatus) {
		this.tseStatus = tseStatus;
	}

	@Column(name = "tse_photo", length = 225)
	public String getTsePhoto() {
		return this.tsePhoto;
	}

	public void setTsePhoto(String tsePhoto) {
		this.tsePhoto = tsePhoto;
	}

	@Column(name = "tse_department", nullable = false)
	public Long getTseDepartment() {
		return this.tseDepartment;
	}

	public void setTseDepartment(Long tseDepartment) {
		this.tseDepartment = tseDepartment;
	}

	@Column(name = "tse_position", nullable = false)
	public Long getTsePosition() {
		return this.tsePosition;
	}

	public void setTsePosition(Long tsePosition) {
		this.tsePosition = tsePosition;
	}
	
	@Column(name = "tse_province_id")
	public Long getTseProvinceId() {
		return tseProvinceId;
	}

	public void setTseProvinceId(Long tseProvinceId) {
		this.tseProvinceId = tseProvinceId;
	}

	@Column(name = "tse_city_id")
	public Long getTseCityId() {
		return tseCityId;
	}

	public void setTseCityId(Long tseCityId) {
		this.tseCityId = tseCityId;
	}

	@Column(name = "tse_county_id")
	public Long getTseCountyId() {
		return tseCountyId;
	}

	public void setTseCountyId(Long tseCountyId) {
		this.tseCountyId = tseCountyId;
	}

	@Column(name = "tse_address")
	public String getTseAddress() {
		return tseAddress;
	}

	public void setTseAddress(String tseAddress) {
		this.tseAddress = tseAddress;
	}

	@Transient
	public String getTspName() {
		return tspName;
	}

	public void setTspName(String tspName) {
		this.tspName = tspName;
	}

}