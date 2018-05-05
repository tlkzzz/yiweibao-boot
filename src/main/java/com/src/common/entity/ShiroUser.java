package com.src.common.entity;

import com.src.api.entity.TbCompany;

import java.io.Serializable;


public class ShiroUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String workno;
	private String account;
	private String email;
	private String sex;

	public ShiroUser() {}

//	public ShiroUser(Userinfo user) {
//		this.id = user.getId();
//		this.account = user.getAccount();
//		this.name = user.getName();
//		this.email = user.getEmail();
//	}
	public ShiroUser(TbCompany user) {
		this.id = user.getTcId();
		this.account = user.getTcLoginUser();
		this.name = user.getTcName();
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.account;
	}

	/**
	 * 重载equals,只计算account;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}