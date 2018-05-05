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
@Table(name="tb_message")
public class TbMessage implements Serializable{
	private Long tmId;
	private Long tmReceiveId;
	private Integer tmType;
	private String tmTitle;
	private String tmContent;
	private Integer tmStatus;
	private Timestamp tmAddDate;
	private String tmSign;
	private Long tmAddUser;
	
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "tm_id", unique = true, nullable = false)
	public Long getTmId() {
		return tmId;
	}
	public void setTmId(Long tmId) {
		this.tmId = tmId;
	}
	
	@Column(name = "tm_receive_id")
	public Long getTmReceiveId() {
		return tmReceiveId;
	}
	public void setTmReceiveId(Long tmReceiveId) {
		this.tmReceiveId = tmReceiveId;
	}
	
	@Column(name = "tm_type")
	public Integer getTmType() {
		return tmType;
	}
	public void setTmType(Integer tmType) {
		this.tmType = tmType;
	}
	
	@Column(name = "tm_title")
	public String getTmTitle() {
		return tmTitle;
	}
	public void setTmTitle(String tmTitle) {
		this.tmTitle = tmTitle;
	}
	
	@Column(name = "tm_content")
	public String getTmContent() {
		return tmContent;
	}
	public void setTmContent(String tmContent) {
		this.tmContent = tmContent;
	}
	
	@Column(name = "tm_status")
	public Integer getTmStatus() {
		return tmStatus;
	}
	public void setTmStatus(Integer tmStatus) {
		this.tmStatus = tmStatus;
	}
	
	@Column(name = "tm_add_date")
	public Timestamp getTmAddDate() {
		return tmAddDate;
	}
	public void setTmAddDate(Timestamp tmAddDate) {
		this.tmAddDate = tmAddDate;
	}
	
	@Column(name = "tm_sign")
	public String getTmSign() {
		return tmSign;
	}
	public void setTmSign(String tmSign) {
		this.tmSign = tmSign;
	}
	
	@Column(name = "tm_add_user")
	public Long getTmAddUser() {
		return tmAddUser;
	}
	public void setTmAddUser(Long tmAddUser) {
		this.tmAddUser = tmAddUser;
	}
	public TbMessage() {
		super();
	}
	
	
	

}
