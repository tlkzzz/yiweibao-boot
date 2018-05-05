package com.src.common.entity;

import javax.persistence.*;


@Entity
@Table(name="t_role_menu")
public class RoleMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;
    @Column(name = "rid", nullable = false, columnDefinition="int(11) COMMENT '角色主键'")
    private Long rid;
    @Column(name = "mid", nullable = false, columnDefinition="int(11) COMMENT '菜单主键'")
    private Long mid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }
}
