package com.warehouse.model;

public class TakeStock {
    private Integer id;

    private Double quantity;

    private Double balance;

    private String stockTakeDate;

    private Integer submitted;

    private String submitDate;

    private String stockTakePerson;

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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getStockTakeDate() {
        return stockTakeDate;
    }

    public void setStockTakeDate(String stockTakeDate) {
        this.stockTakeDate = stockTakeDate;
    }

    public Integer getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Integer submitted) {
        this.submitted = submitted;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getStockTakePerson() {
        return stockTakePerson;
    }

    public void setStockTakePerson(String stockTakePerson) {
        this.stockTakePerson = stockTakePerson;
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