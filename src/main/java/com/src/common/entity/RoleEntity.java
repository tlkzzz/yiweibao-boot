package com.src.common.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 角色实体类
 */

@Entity
@Table(name="t_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;
    @Column(name = "role_name", nullable = false, columnDefinition="varchar(32) COMMENT '角色名称'")
    private String roleName;
    @Column(name = "remark", nullable = true, columnDefinition="varchar(32) COMMENT '角色备注'")
    private String remark;
    @Column(name = "state", nullable = false, columnDefinition="int(1)  default 0 COMMENT '状态0有效1无效'")
    private int state;
    @Column(name = "create_time", nullable = true, columnDefinition="datetime COMMENT '创建时间'")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
