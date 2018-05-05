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
 * TbMachine generated by hbm2java
 */
@Entity
@Table(name = "tb_machine")
public class TbMachine implements java.io.Serializable {

	private Long tmId;
	private String tmCode;
	private String tmName;
	private Long tmBrand;
	private String brandName;
	private Long tmType;
	private String typeName;
	
	private Long tmNeedleType;
	private String needleTypeName;
	private Long tmNeedle;
	private String needleName;
	private Long tmChangeNeedle;
	private String changeNeedleName;
	private Long tmSize;
	private String sizeName;
	private Long tmChangeColor;
	private String changeColorName;
	
	private Long tmGating;
	private String gatingName;
	private Long tmElectric;
	private String electricName;
	private Long tmServo;
	private String servoName;
	private Integer tmWarranty;
	private Integer tmStatus;
	private String tmDesp;
	private Timestamp tmAddtime;
	private Long tmAdduser;
	private Integer tmFrom;
	
	private Long tmdId;
	private Long tmdMachineId;
	private Timestamp tmdDate;
	private Double tmdSellingPrice;
	private Double tmdLeasePrice;
	private Double tmdMonthLeasePrice;
	private Long tmdLeaseTime;
	private String tmdMachineThumbnail;
	private String tmdMachinePhoto;
	private String tmdBrief;

	public TbMachine() {
	}

	public TbMachine(String tmCode, String tmName, Long tmBrand, Long tmType, Long tmGating, Integer tmWarranty,
			Integer tmStatus, Timestamp tmAddtime, Long tmAdduser, Integer tmFrom) {
		this.tmCode = tmCode;
		this.tmName = tmName;
		this.tmBrand = tmBrand;
		this.tmType = tmType;
		this.tmGating = tmGating;
		this.tmWarranty = tmWarranty;
		this.tmStatus = tmStatus;
		this.tmAddtime = tmAddtime;
		this.tmAdduser = tmAdduser;
		this.tmFrom = tmFrom;
	}

	public TbMachine(String tmCode, String tmName, Long tmBrand, Long tmType, Long tmGating, Long tmElectric,
			Long tmServo, Integer tmWarranty, Integer tmStatus, String tmDesp, Timestamp tmAddtime, Long tmAdduser, Integer tmFrom) {
		this.tmCode = tmCode;
		this.tmName = tmName;
		this.tmBrand = tmBrand;
		this.tmType = tmType;
		this.tmGating = tmGating;
		this.tmElectric = tmElectric;
		this.tmServo = tmServo;
		this.tmWarranty = tmWarranty;
		this.tmStatus = tmStatus;
		this.tmDesp = tmDesp;
		this.tmAddtime = tmAddtime;
		this.tmAdduser = tmAdduser;
		this.tmFrom = tmFrom;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "tm_id", unique = true, nullable = false)
	public Long getTmId() {
		return this.tmId;
	}

	public void setTmId(Long tmId) {
		this.tmId = tmId;
	}

	@Column(name = "tm_code", nullable = false, length = 50)
	public String getTmCode() {
		return this.tmCode;
	}

	public void setTmCode(String tmCode) {
		this.tmCode = tmCode;
	}

	@Column(name = "tm_name", nullable = false, length = 100)
	public String getTmName() {
		return this.tmName;
	}

	public void setTmName(String tmName) {
		this.tmName = tmName;
	}

	@Column(name = "tm_brand", nullable = false)
	public Long getTmBrand() {
		return this.tmBrand;
	}

	public void setTmBrand(Long tmBrand) {
		this.tmBrand = tmBrand;
	}

	@Column(name = "tm_type")
	public Long getTmType() {
		return this.tmType;
	}

	public void setTmType(Long tmType) {
		this.tmType = tmType;
	}

	@Column(name = "tm_gating", nullable = false)
	public Long getTmGating() {
		return this.tmGating;
	}

	public void setTmGating(Long tmGating) {
		this.tmGating = tmGating;
	}

	@Column(name = "tm_electric")
	public Long getTmElectric() {
		return this.tmElectric;
	}

	public void setTmElectric(Long tmElectric) {
		this.tmElectric = tmElectric;
	}

	@Column(name = "tm_servo")
	public Long getTmServo() {
		return this.tmServo;
	}

	public void setTmServo(Long tmServo) {
		this.tmServo = tmServo;
	}

	@Column(name = "tm_warranty", nullable = false)
	public Integer getTmWarranty() {
		return this.tmWarranty;
	}

	public void setTmWarranty(Integer tmWarranty) {
		this.tmWarranty = tmWarranty;
	}

	@Column(name = "tm_status", nullable = false)
	public Integer getTmStatus() {
		return this.tmStatus;
	}

	public void setTmStatus(Integer tmStatus) {
		this.tmStatus = tmStatus;
	}

	@Column(name = "tm_desp", length = 225)
	public String getTmDesp() {
		return this.tmDesp;
	}

	public void setTmDesp(String tmDesp) {
		this.tmDesp = tmDesp;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "tm_addtime", nullable = false, length = 19)
	public Timestamp getTmAddtime() {
		return this.tmAddtime;
	}

	public void setTmAddtime(Timestamp tmAddtime) {
		this.tmAddtime = tmAddtime;
	}

	@Column(name = "tm_adduser", nullable = false)
	public Long getTmAdduser() {
		return this.tmAdduser;
	}

	public void setTmAdduser(Long tmAdduser) {
		this.tmAdduser = tmAdduser;
	}

	@Transient
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Transient
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Transient
	public String getGatingName() {
		return gatingName;
	}

	public void setGatingName(String gatingName) {
		this.gatingName = gatingName;
	}

	@Transient
	public String getElectricName() {
		return electricName;
	}

	public void setElectricName(String electricName) {
		this.electricName = electricName;
	}

	@Transient
	public String getServoName() {
		return servoName;
	}

	public void setServoName(String servoName) {
		this.servoName = servoName;
	}

	@Transient
	public Long getTmdId() {
		return tmdId;
	}

	public void setTmdId(Long tmdId) {
		this.tmdId = tmdId;
	}

	@Transient
	public Long getTmdMachineId() {
		return tmdMachineId;
	}

	public void setTmdMachineId(Long tmdMachineId) {
		this.tmdMachineId = tmdMachineId;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Transient
	public Timestamp getTmdDate() {
		return tmdDate;
	}

	public void setTmdDate(Timestamp tmdDate) {
		this.tmdDate = tmdDate;
	}

	@Transient
	public Double getTmdSellingPrice() {
		return tmdSellingPrice;
	}

	public void setTmdSellingPrice(Double tmdSellingPrice) {
		this.tmdSellingPrice = tmdSellingPrice;
	}

	@Transient
	public Double getTmdLeasePrice() {
		return tmdLeasePrice;
	}

	public void setTmdLeasePrice(Double tmdLeasePrice) {
		this.tmdLeasePrice = tmdLeasePrice;
	}

	@Transient
	public Double getTmdMonthLeasePrice() {
		return tmdMonthLeasePrice;
	}

	public void setTmdMonthLeasePrice(Double tmdMonthLeasePrice) {
		this.tmdMonthLeasePrice = tmdMonthLeasePrice;
	}

	@Transient
	public Long getTmdLeaseTime() {
		return tmdLeaseTime;
	}

	public void setTmdLeaseTime(Long tmdLeaseTime) {
		this.tmdLeaseTime = tmdLeaseTime;
	}

	@Transient
	public String getTmdMachineThumbnail() {
		return tmdMachineThumbnail;
	}

	public void setTmdMachineThumbnail(String tmdMachineThumbnail) {
		this.tmdMachineThumbnail = tmdMachineThumbnail;
	}

	@Transient
	public String getTmdMachinePhoto() {
		return tmdMachinePhoto;
	}

	public void setTmdMachinePhoto(String tmdMachinePhoto) {
		this.tmdMachinePhoto = tmdMachinePhoto;
	}

	@Transient
	public String getTmdBrief() {
		return tmdBrief;
	}

	public void setTmdBrief(String tmdBrief) {
		this.tmdBrief = tmdBrief;
	}

	@Column(name = "tm_from", nullable = false)
	public Integer getTmFrom() {
		return tmFrom;
	}

	public void setTmFrom(Integer tmFrom) {
		this.tmFrom = tmFrom;
	}

	@Column(name = "tm_needle_type")
	public Long getTmNeedleType() {
		return tmNeedleType;
	}

	public void setTmNeedleType(Long tmNeedleType) {
		this.tmNeedleType = tmNeedleType;
	}

	@Transient
	public String getNeedleTypeName() {
		return needleTypeName;
	}

	public void setNeedleTypeName(String needleTypeName) {
		this.needleTypeName = needleTypeName;
	}

	@Column(name = "tm_needle")
	public Long getTmNeedle() {
		return tmNeedle;
	}

	public void setTmNeedle(Long tmNeedle) {
		this.tmNeedle = tmNeedle;
	}

	@Transient
	public String getNeedleName() {
		return needleName;
	}

	public void setNeedleName(String needleName) {
		this.needleName = needleName;
	}

	@Column(name = "tm_change_needle")
	public Long getTmChangeNeedle() {
		return tmChangeNeedle;
	}

	public void setTmChangeNeedle(Long tmChangeNeedle) {
		this.tmChangeNeedle = tmChangeNeedle;
	}

	@Transient
	public String getChangeNeedleName() {
		return changeNeedleName;
	}

	public void setChangeNeedleName(String changeNeedleName) {
		this.changeNeedleName = changeNeedleName;
	}

	@Column(name = "tm_size")
	public Long getTmSize() {
		return tmSize;
	}

	public void setTmSize(Long tmSize) {
		this.tmSize = tmSize;
	}

	@Transient
	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	@Column(name = "tm_chage_color")
	public Long getTmChangeColor() {
		return tmChangeColor;
	}

	public void setTmChangeColor(Long tmChangeColor) {
		this.tmChangeColor = tmChangeColor;
	}

	@Transient
	public String getChangeColorName() {
		return changeColorName;
	}

	public void setChangeColorName(String changeColorName) {
		this.changeColorName = changeColorName;
	}
}