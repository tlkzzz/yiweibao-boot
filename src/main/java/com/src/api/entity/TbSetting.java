package com.src.api.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "tb_setting")
public class TbSetting implements Serializable{

//	ts_collect_time
//	ts_collect_frequency
//ts_tips_no_deal
//ts_tips_no_deal_open
//ts_fitting_settle_day

	private String collectTime;
	private Integer collectFrequency;
	private Integer noDeal;
	private Integer noDealOpen;
	private String settleDay;

	private Long tsId;//id
	private Double tsPayment;//维修单价
	private Integer tsMinute;//维修单价基数，以分钟为单位
	private Double tsServiceDistance;//开始维修允许距离内
	private Integer tsInsuranceDay;//保修剩余天数
	private Integer tsIfSendSms;//是否发送短信0:否 1:是
	private Integer tsIfDispatch;//是否自动派工 0:否 1:是 只有一个员工时默认自动派工
	private Integer tsTimeInDaysAutoEvaluation;//工单（tb_service_order）多少天内自动评价
	private Integer tsEmployeeIfSendPosition;//维修工是否实时上传位置 1:是 0:否
	private Integer tsPositionTime;//可获取的维修工上传的位置时间内 单位（秒）
	private Integer tsIfHandPrice; //是否手动输入修改价格
	private Long tsCompanyId; //公司id
	private Long tsPoints; //站点
	private  Double tsLatitude;//维度
	private  Double tsLongtude;//经度
	private  Integer tsSignsize;//考勤范围

	@Column(name = "ts_collect_time")
	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	@Column(name = "ts_collect_frequency")
	public Integer getCollectFrequency() {
		return collectFrequency;
	}

	public void setCollectFrequency(Integer collectFrequency) {
		this.collectFrequency = collectFrequency;
	}
	@Column(name = "ts_tips_no_deal")
	public Integer getNoDeal() {
		return noDeal;
	}

	public void setNoDeal(Integer noDeal) {
		this.noDeal = noDeal;
	}
	@Column(name = "ts_tips_no_deal_open")
	public Integer getNoDealOpen() {
		return noDealOpen;
	}

	public void setNoDealOpen(Integer noDealOpen) {
		this.noDealOpen = noDealOpen;
	}
	@Column(name = "ts_fitting_settle_day")
	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	@Column(name = "ts_signsize")
	public Integer getTsSignsize() {
		return tsSignsize;
	}
	public void setTsSignsize(Integer tsSignsize) {
		this.tsSignsize = tsSignsize;
	}
	@Column(name = "ts_points")
	public Long getTsPoints() {
		return tsPoints;
	}
	public void setTsPoints(Long tsPoints) {
		this.tsPoints = tsPoints;
	}
	@Column(name = "ts_latitude")
	public Double getTsLatitude() {
		return tsLatitude;
	}
	public void setTsLatitude(Double tsLatitude) {
		this.tsLatitude = tsLatitude;
	}
	@Column(name = "ts_longtude")
	public Double getTsLongtude() {
		return tsLongtude;
	}
	public void setTsLongtude(Double tsLongtude) {
		this.tsLongtude = tsLongtude;
	}
	@Column(name = "ts_company_id")
	public Long getTsCompanyId() {
		return tsCompanyId;
	}
	public void setTsCompanyId(Long tsCompanyId) {
		this.tsCompanyId = tsCompanyId;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ts_id", unique = true, nullable = false)
	public Long getTsId() {
		return tsId;
	}
	public void setTsId(Long tsId) {
		this.tsId = tsId;
	}



	@Column(name = "ts_if_hand_price")
	public Integer getTsIfHandPrice() {
		return tsIfHandPrice;
	}
	public void setTsIfHandPrice(Integer tsIfHandPrice) {
		this.tsIfHandPrice = tsIfHandPrice;
	}
	@Column(name = "ts_payment")
	public Double getTsPayment() {
		return tsPayment;
	}
	public void setTsPayment(Double tsPayment) {
		this.tsPayment = tsPayment;
	}
	@Column(name = "ts_minute")
	public Integer getTsMinute() {
		return tsMinute;
	}
	public void setTsMinute(Integer tsMinute) {
		this.tsMinute = tsMinute;
	}
	@Column(name = "ts_service_distance")
	public Double getTsServiceDistance() {
		return tsServiceDistance;
	}
	public void setTsServiceDistance(Double tsServiceDistance) {
		this.tsServiceDistance = tsServiceDistance;
	}
	@Column(name = "ts_insurance_day")
	public Integer getTsInsuranceDay() {
		return tsInsuranceDay;
	}
	public void setTsInsuranceDay(Integer tsInsuranceDay) {
		this.tsInsuranceDay = tsInsuranceDay;
	}
	@Column(name = "ts_if_send_sms")
	public Integer getTsIfSendSms() {
		return tsIfSendSms;
	}
	public void setTsIfSendSms(Integer tsIfSendSms) {
		this.tsIfSendSms = tsIfSendSms;
	}
	@Column(name = "ts_if_dispatch")
	public Integer getTsIfDispatch() {
		return tsIfDispatch;
	}
	public void setTsIfDispatch(Integer tsIfDispatch) {
		this.tsIfDispatch = tsIfDispatch;
	}
	@Column(name = "ts_time_in_days_auto_evaluation")
	public Integer getTsTimeInDaysAutoEvaluation() {
		return tsTimeInDaysAutoEvaluation;
	}
	public void setTsTimeInDaysAutoEvaluation(Integer tsTimeInDaysAutoEvaluation) {
		this.tsTimeInDaysAutoEvaluation = tsTimeInDaysAutoEvaluation;
	}
	@Column(name = "ts_employee_if_send_position")
	public Integer getTsEmployeeIfSendPosition() {
		return tsEmployeeIfSendPosition;
	}
	public void setTsEmployeeIfSendPosition(Integer tsEmployeeIfSendPosition) {
		this.tsEmployeeIfSendPosition = tsEmployeeIfSendPosition;
	}
	@Column(name = "ts_position_time")
	public Integer getTsPositionTime() {
		return tsPositionTime;
	}
	public void setTsPositionTime(Integer tsPositionTime) {
		this.tsPositionTime = tsPositionTime;
	}
}
