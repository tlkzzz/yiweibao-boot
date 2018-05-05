package com.src.common.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_user")
public class UserEntity {
    //nullable=false 非空
    //varchar(32) varcher长度32  默认255
    //strategy = GenerationType.IDENTITY  自增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;
    @Column(name = "login_user", nullable = false, columnDefinition="varchar(32) COMMENT '登陆用户名'")
    private String loginUser;
    @Column(name = "password", nullable = false , columnDefinition="varchar(255) COMMENT  '密码'")
    private String password;
    @Column(name = "user_name", nullable = false , columnDefinition="varchar(32) COMMENT  '用户名'")
    private String userName;
    @Column(name = "phone", nullable = true, columnDefinition="varchar(11) COMMENT  '手机号'")
    private String phone;
    @Column(name = "email", nullable = true, columnDefinition="varchar(32) COMMENT  '邮箱'")
    private String email;
    @Column(name = "img", nullable = true , columnDefinition="varchar(255) COMMENT  '头像地址'")
    private String img;
    @Column(name = "sex", nullable = true,  columnDefinition="int(1) COMMENT  '性别0女1男'")
    private int sex;
    @Column(name = "state", nullable = false,columnDefinition="int(1) default 0 COMMENT '状态0有效1无效'")
    private int state;
    @Column(name = "login_time", nullable = true, columnDefinition="datetime COMMENT '登陆时间'")
    private Date loginTime;
    @Column(name = "create_time", nullable = true, columnDefinition="datetime COMMENT '创建时间'")
    private Date createTime;
    @Column(name = "create_person", nullable = true, columnDefinition="varchar(32) COMMENT '创建人'")
    private String createPserson;
    @Column(name = "is_admin", nullable = false, columnDefinition="int(1) default 0 COMMENT '0普通用户1最高管理员'")
    private int isAdmin;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatePserson() {
        return createPserson;
    }

    public void setCreatePserson(String createPserson) {
        this.createPserson = createPserson;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
