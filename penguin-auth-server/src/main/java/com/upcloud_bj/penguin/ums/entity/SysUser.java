package com.upcloud_bj.penguin.ums.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser {
    /**
     * UserID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    private String password;

    private String name;

    @Column(name = "header_img")
    private String headerImg;

    /**
     * 备注
     */
    private String comment;

    /**
     * 00：正常/01：禁用/-1：删除
     */
    private String status;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private Integer createUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_user_id")
    private Integer updateUserId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取UserID
     *
     * @return id - UserID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置UserID
     *
     * @param id UserID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return user_name - 账号
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置账号
     *
     * @param userName 账号
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return header_img
     */
    public String getHeaderImg() {
        return headerImg;
    }

    /**
     * @param headerImg
     */
    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    /**
     * 获取备注
     *
     * @return comment - 备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置备注
     *
     * @param comment 备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取00：正常/01：禁用/-1：删除
     *
     * @return status - 00：正常/01：禁用/-1：删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置00：正常/01：禁用/-1：删除
     *
     * @param status 00：正常/01：禁用/-1：删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取创建人
     *
     * @return create_user_id - 创建人
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * 设置创建人
     *
     * @param createUserId 创建人
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return update_user_id - 更新人
     */
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人
     *
     * @param updateUserId 更新人
     */
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}