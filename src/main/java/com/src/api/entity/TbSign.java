package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_sign")
public class TbSign implements Serializable{
	
	
	
	private Long id; //id
	private Long tseId; //员工id
	private Date amTime; //上午打卡时间
	private int amIsTime;//上午是否打开0没有1无
	private Date pmTime; //下午打卡时间
	private int pmIsTime; //下午是否打卡0没有1有
	private Double dkAmLatitude;//上午打卡维度
	private Double dkAmLongtude;//上午打卡经度
	private Double dkPmLatitude;//下午午打卡维度
	private Double dkPmLongtude;//下午打卡经度
	private String amRemarks;  //上午外勤备注
	private  String pmRemarks;  //下午外勤备注
	
	private Date signDate;  //打卡日期
	private Long pointsId;//站点id
	private  String amAddress;//上午打卡地址
	private String pmAddress;//下午打卡地址
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "am_address")
	public String getAmAddress() {
		return amAddress;
	}
	public void setAmAddress(String amAddress) {
		this.amAddress = amAddress;
	}
	@Column(name = "pm_address")
	public String getPmAddress() {
		return pmAddress;
	}
	public void setPmAddress(String pmAddress) {
		this.pmAddress = pmAddress;
	}
	@Column(name = "points_id")
	public Long getPointsId() {
		return pointsId;
	}
	public void setPointsId(Long pointsId) {
		this.pointsId = pointsId;
	}
	@Column(name = "tse_id")
	public Long getTseId() {
		return tseId;
	}
	public void setTseId(Long tseId) {
		this.tseId = tseId;
	}
	
	@Column(name = "am_time")
	public Date getAmTime() {
		return amTime;
	}
	public void setAmTime(Date amTime) {
		this.amTime = amTime;
	}
	@Column(name = "am_is_sign")
	public int getAmIsTime() {
		return amIsTime;
	}
	public void setAmIsTime(int amIsTime) {
		this.amIsTime = amIsTime;
	}
	@Column(name = "pm_time")
	public Date getPmTime() {
		return pmTime;
	}
	public void setPmTime(Date pmTime) {
		this.pmTime = pmTime;
	}
	@Column(name = "pm_is_sign")
	public int getPmIsTime() {
		return pmIsTime;
	}
	public void setPmIsTime(int pmIsTime) {
		this.pmIsTime = pmIsTime;
	}
	@Column(name = "dk_am_latitude")
	public Double getDkAmLatitude() {
		return dkAmLatitude;
	}

	public void setDkAmLatitude(Double dkAmLatitude) {
		this.dkAmLatitude = dkAmLatitude;
	}
	@Column(name = "dk_am_longtude")
	public Double getDkAmLongtude() {
		return dkAmLongtude;
	}

	public void setDkAmLongtude(Double dkAmLongtude) {
		this.dkAmLongtude = dkAmLongtude;
	}
	@Column(name = "dk_pm_latitude")
	public Double getDkPmLatitude() {
		return dkPmLatitude;
	}
	
	public void setDkPmLatitude(Double dkPmLatitude) {
		this.dkPmLatitude = dkPmLatitude;
	}
	@Column(name = "dk_pm_longtude")
	public Double getDkPmLongtude() {
		return dkPmLongtude;
	}
	public void setDkPmLongtude(Double dkPmLongtude) {
		this.dkPmLongtude = dkPmLongtude;
	}
	@Column(name = "sign_date")
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	@Column(name = "am_remarks")
	public String getAmRemarks() {
		return amRemarks;
	}
	public void setAmRemarks(String amRemarks) {
		this.amRemarks = amRemarks;
	}
	@Column(name = "pm_remarks")
	public String getPmRemarks() {
		return pmRemarks;
	}
	public void setPmRemarks(String pmRemarks) {
		this.pmRemarks = pmRemarks;
	}
	
	
	

}
