package com.src.common.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false )
    private Long id;
    @Column(name = "pid", nullable = false , columnDefinition="int(11) COMMENT  '菜单名称'")
    private int pid;
    @Column(name = "name", nullable = false , columnDefinition="varchar(32) COMMENT  '菜单名称'")
    private String name;
    @Column(name = "icon", nullable = false , columnDefinition="varchar(255) COMMENT  'icon图标'")
    private String icon;
    @Column(name = "url", nullable = false , columnDefinition="varchar(255) COMMENT  'url地址'")
    private String url;
    @Column(name = "sort", nullable = false,columnDefinition="int(11)  COMMENT '排序'")
    private int sort;
    @Column(name = "state", nullable = false,columnDefinition="int(1) default 0 COMMENT '状态0有效1无效'")
    private int state;
    @Column(name = "create_pserson", nullable = false, columnDefinition="varchar(32) COMMENT '创建人'")
    private int createPsersonId;
    @Column(name = "create_time", nullable = true, columnDefinition="datetime COMMENT '创建时间'")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCreatePsersonId() {
        return createPsersonId;
    }

    public void setCreatePsersonId(int createPsersonId) {
        this.createPsersonId = createPsersonId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
