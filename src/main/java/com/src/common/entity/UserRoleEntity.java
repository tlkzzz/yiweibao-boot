package com.src.common.entity;


import javax.persistence.*;

@Entity
@Table(name="t_user_role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;
    @Column(name = "uid", nullable = false, columnDefinition="int(11) COMMENT '用户主键'")
    private Long uid;
    @Column(name = "rid", nullable = false, columnDefinition="int(11) COMMENT '角色主键'")
    private Long rid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}
