package com.warehouse.model;

public class Stocktake {
    private Integer id;

    private String name;

    private Integer type;

    private String stocktakePerson;

    private String stocktakeTime;

    private Integer submitted;

    private String submitTime;

    private String auditor;

    private String remark;

    private String createTime;

    private Integer createUser;

    private Integer updateUser;

    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStocktakePerson() {
        return stocktakePerson;
    }

    public void setStocktakePerson(String stocktakePerson) {
        this.stocktakePerson = stocktakePerson;
    }

    public String getStocktakeTime() {
        return stocktakeTime;
    }

    public void setStocktakeTime(String stocktakeTime) {
        this.stocktakeTime = stocktakeTime;
    }

    public Integer getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Integer submitted) {
        this.submitted = submitted;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}