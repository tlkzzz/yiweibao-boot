package com.src.api.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
*    
* 项目名称：MingDeApi   
* 类名称：TbServiceOrder   
* 类描述：   客户报修记录表
* 创建人：lm 
* 创建时间：2015年7月17日 下午2:00:45   
* @version    
*
 */
@Entity
@Table(name = "tb_servcie_order")
public class TbServiceOrder implements java.io.Serializable {

	// Fields
	private Long tsoId;
	private String tsoNumber;//工单号码,Mmddhhssmm+rand(3)
	private Long tsoCustomerId;//客户id,外联: tb_customers
	private Long tsoOrderId;//订单id
	private String tsoOrderDetailId;//订单详情id
	private Long tsoGoodId;//产品id,外联： tb_goods
	private String tsoGoodName;//报修产品名称,手动输入的
	private Long tsoItemId;//报修类别,外联： tb_item
	private String tsoAudioFile;//语音文件
	private String tsoQuestion;//问题描述,在线编辑器编辑
	private Timestamp tsoAddDate;//
	private Timestamp tsoServiceTime;//维修时间
	private String tsoMemo;//备注
	private Integer tsoStatus;//工单状态,1：客户报修 2：已派工 3：已确认 4：待维修 5：维修中 6: 已维修 7：已验收 8：已评价 0：删除
	private Integer tsoEvaluateManner;//服务态度
	private Integer tsoEvaluateResult;//维修结果
	private Long tsoCompanyId;//
	private Integer tsoType;//1:普通报修 2:售后报修
	private String tsoPicsFile;//图片文件
	private Double tsoPrice;//价格
	private Integer tsoPayType;//1.支付宝2.微信3.银联4.线下

	private String tcName;//客户名称
	private String tcProv;//客户 省
	private String tcCity;//客户 市
	private String tcRegion;//客户 区
	private String tcAddress;//客户地址
	
	private String tgName;//产品名称
	private String tgSimpleName;//产品简称
	private String tgModel;//产品型号
	private String tgBrand;//产品品牌
	private String tgcName;//产品分类
	
	private String tiName;//保修类别
	private String tiParentName;//保修类别上级分类
	
	// Constructors

	/** default constructor */
	public TbServiceOrder() {
	}

	/** full constructor */
	

	public TbServiceOrder(Long tsoId, String tsoNumber, Long tsoCustomerId,
			Long tsoOrderId, Long tsoGoodId, String tsoGoodName,
			Long tsoItemId, String tsoAudioFile, String tsoQuestion,
			Timestamp tsoAddDate, Timestamp tsoServiceTime, String tsoMemo,
			Integer tsoStatus, Integer tsoEvaluateManner,
			Integer tsoEvaluateResult, Long tsoCompanyId, Integer tsoType,
			String tsoPicsFile, Double tsoPrice, String tcName, String tcProv,
			String tcCity, String tcRegion, String tcAddress, String tgName,
			String tgSimpleName, String tgModel, String tgBrand,
			String tgcName, String tiName, String tiParentName) {
		super();
		this.tsoId = tsoId;
		this.tsoNumber = tsoNumber;
		this.tsoCustomerId = tsoCustomerId;
		this.tsoOrderId = tsoOrderId;
		this.tsoGoodId = tsoGoodId;
		this.tsoGoodName = tsoGoodName;
		this.tsoItemId = tsoItemId;
		this.tsoAudioFile = tsoAudioFile;
		this.tsoQuestion = tsoQuestion;
		this.tsoAddDate = tsoAddDate;
		this.tsoServiceTime = tsoServiceTime;
		this.tsoMemo = tsoMemo;
		this.tsoStatus = tsoStatus;
		this.tsoEvaluateManner = tsoEvaluateManner;
		this.tsoEvaluateResult = tsoEvaluateResult;
		this.tsoCompanyId = tsoCompanyId;
		this.tsoType = tsoType;
		this.tsoPicsFile = tsoPicsFile;
		this.tsoPrice = tsoPrice;
		this.tcName = tcName;
		this.tcProv = tcProv;
		this.tcCity = tcCity;
		this.tcRegion = tcRegion;
		this.tcAddress = tcAddress;
		this.tgName = tgName;
		this.tgSimpleName = tgSimpleName;
		this.tgModel = tgModel;
		this.tgBrand = tgBrand;
		this.tgcName = tgcName;
		this.tiName = tiName;
		this.tiParentName = tiParentName;
	}
	public TbServiceOrder(Long tsoId, String tsoNumber, Long tsoCustomerId,
			Long tsoOrderId, String tsoOrderDetailId, Long tsoGoodId,
			String tsoGoodName, Long tsoItemId, String tsoAudioFile,
			String tsoQuestion, Timestamp tsoAddDate, Timestamp tsoServiceTime,
			String tsoMemo, Integer tsoStatus, Integer tsoEvaluateManner,
			Integer tsoEvaluateResult, Long tsoCompanyId, Integer tsoType,
			String tsoPicsFile, Double tsoPrice, Integer tsoPayType) {
		super();
		this.tsoId = tsoId;
		this.tsoNumber = tsoNumber;
		this.tsoCustomerId = tsoCustomerId;
		this.tsoOrderId = tsoOrderId;
		this.tsoOrderDetailId = tsoOrderDetailId;
		this.tsoGoodId = tsoGoodId;
		this.tsoGoodName = tsoGoodName;
		this.tsoItemId = tsoItemId;
		this.tsoAudioFile = tsoAudioFile;
		this.tsoQuestion = tsoQuestion;
		this.tsoAddDate = tsoAddDate;
		this.tsoServiceTime = tsoServiceTime;
		this.tsoMemo = tsoMemo;
		this.tsoStatus = tsoStatus;
		this.tsoEvaluateManner = tsoEvaluateManner;
		this.tsoEvaluateResult = tsoEvaluateResult;
		this.tsoCompanyId = tsoCompanyId;
		this.tsoType = tsoType;
		this.tsoPicsFile = tsoPicsFile;
		this.tsoPrice = tsoPrice;
		this.tsoPayType = tsoPayType;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "tso_id", unique = true, nullable = false)
	public Long getTsoId() {
		return tsoId;
	}

	
	public void setTsoId(Long tsoId) {
		this.tsoId = tsoId;
	}

	@Column(name = "tso_number")
	public String getTsoNumber() {
		return tsoNumber;
	}

	public void setTsoNumber(String tsoNumber) {
		this.tsoNumber = tsoNumber;
	}

	@Column(name = "tso_customer_id")
	public Long getTsoCustomerId() {
		return tsoCustomerId;
	}

	public void setTsoCustomerId(Long tsoCustomerId) {
		this.tsoCustomerId = tsoCustomerId;
	}

	@Column(name = "tso_good_id")
	public Long getTsoGoodId() {
		return tsoGoodId;
	}

	public void setTsoGoodId(Long tsoGoodId) {
		this.tsoGoodId = tsoGoodId;
	}

	@Column(name = "tso_good_name")
	public String getTsoGoodName() {
		return tsoGoodName;
	}

	public void setTsoGoodName(String tsoGoodName) {
		this.tsoGoodName = tsoGoodName;
	}

	@Column(name = "tso_item_id")
	public Long getTsoItemId() {
		return tsoItemId;
	}

	public void setTsoItemId(Long tsoItemId) {
		this.tsoItemId = tsoItemId;
	}

	@Column(name = "tso_audio_file")
	public String getTsoAudioFile() {
		return tsoAudioFile;
	}

	public void setTsoAudioFile(String tsoAudioFile) {
		this.tsoAudioFile = tsoAudioFile;
	}

	@Column(name = "tso_question")
	public String getTsoQuestion() {
		return tsoQuestion;
	}

	public void setTsoQuestion(String tsoQuestion) {
		this.tsoQuestion = tsoQuestion;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
	@Column(name = "tso_add_date", nullable = false)
	public Timestamp getTsoAddDate() {
		return tsoAddDate;
	}

	public void setTsoAddDate(Timestamp tsoAddDate) {
		this.tsoAddDate = tsoAddDate;
	}
	@Column(name = "tso_service_time", nullable = false)
	public Timestamp getTsoServiceTime() {
		return tsoServiceTime;
	}

	public void setTsoServiceTime(Timestamp tsoServiceTime) {
		this.tsoServiceTime = tsoServiceTime;
	}

	@Column(name = "tso_memo")
	public String getTsoMemo() {
		return tsoMemo;
	}

	public void setTsoMemo(String tsoMemo) {
		this.tsoMemo = tsoMemo;
	}

	@Column(name = "tso_status")
	public Integer getTsoStatus() {
		return tsoStatus;
	}

	public void setTsoStatus(Integer tsoStatus) {
		this.tsoStatus = tsoStatus;
	}
	
	@Column(name = "tso_evaluate_manner")
	public Integer getTsoEvaluateManner() {
		return tsoEvaluateManner;
	}

	public void setTsoEvaluateManner(Integer tsoEvaluateManner) {
		this.tsoEvaluateManner = tsoEvaluateManner;
	}

	@Column(name = "tso_evaluate_result")
	public Integer getTsoEvaluateResult() {
		return tsoEvaluateResult;
	}

	public void setTsoEvaluateResult(Integer tsoEvaluateResult) {
		this.tsoEvaluateResult = tsoEvaluateResult;
	}
	@Column(name = "tso_company_id")
	public Long getTsoCompanyId() {
		return tsoCompanyId;
	}

	public void setTsoCompanyId(Long tsoCompanyId) {
		this.tsoCompanyId = tsoCompanyId;
	}
	@Column(name = "tso_type")
	public Integer getTsoType() {
		return tsoType;
	}

	public void setTsoType(Integer tsoType) {
		this.tsoType = tsoType;
	}
	
	@Column(name = "tso_pics_file")
	public String getTsoPicsFile() {
		return tsoPicsFile;
	}

	public void setTsoPicsFile(String tsoPicsFile) {
		this.tsoPicsFile = tsoPicsFile;
	}
	@Column(name = "tso_order_detail_id")
	public String getTsoOrderDetailId() {
		return tsoOrderDetailId;
	}

	public void setTsoOrderDetailId(String tsoOrderDetailId) {
		this.tsoOrderDetailId = tsoOrderDetailId;
	}

	@Transient
	public String getTcName() {
		return tcName;
	}

	public void setTcName(String tcName) {
		this.tcName = tcName;
	}

	@Transient
	public String getTcProv() {
		return tcProv;
	}

	public void setTcProv(String tcProv) {
		this.tcProv = tcProv;
	}

	@Transient
	public String getTcCity() {
		return tcCity;
	}

	public void setTcCity(String tcCity) {
		this.tcCity = tcCity;
	}

	@Transient
	public String getTcRegion() {
		return tcRegion;
	}

	public void setTcRegion(String tcRegion) {
		this.tcRegion = tcRegion;
	}

	@Transient
	public String getTcAddress() {
		return tcAddress;
	}

	public void setTcAddress(String tcAddress) {
		this.tcAddress = tcAddress;
	}

	@Transient
	public String getTgName() {
		return tgName;
	}

	public void setTgName(String tgName) {
		this.tgName = tgName;
	}

	@Transient
	public String getTgSimpleName() {
		return tgSimpleName;
	}

	public void setTgSimpleName(String tgSimpleName) {
		this.tgSimpleName = tgSimpleName;
	}

	@Transient
	public String getTgModel() {
		return tgModel;
	}

	public void setTgModel(String tgModel) {
		this.tgModel = tgModel;
	}

	@Transient
	public String getTgBrand() {
		return tgBrand;
	}

	public void setTgBrand(String tgBrand) {
		this.tgBrand = tgBrand;
	}

	@Transient
	public String getTgcName() {
		return tgcName;
	}

	public void setTgcName(String tgcName) {
		this.tgcName = tgcName;
	}

	@Transient
	public String getTiName() {
		return tiName;
	}

	public void setTiName(String tiName) {
		this.tiName = tiName;
	}

	@Transient
	public String getTiParentName() {
		return tiParentName;
	}
	
	public void setTiParentName(String tiParentName) {
		this.tiParentName = tiParentName;
	}
	@Column(name = "tso_order_id")
	public Long getTsoOrderId() {
		return tsoOrderId;
	}
	
	public void setTsoOrderId(Long tsoOrderId) {
		this.tsoOrderId = tsoOrderId;
	}
	@Column(name = "tso_price")
	public Double getTsoPrice() {
		return tsoPrice;
	}
	
	public void setTsoPrice(Double tsoPrice) {
		this.tsoPrice = tsoPrice;
	}
	@Column(name = "tso_pay_type")
	public Integer getTsoPayType() {
		return tsoPayType;
	}

	public void setTsoPayType(Integer tsoPayType) {
		this.tsoPayType = tsoPayType;
	}

	
}