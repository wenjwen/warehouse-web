package com.warehouse.model;

import java.math.BigDecimal;

public class StocktakeItem {
    private Integer id;

    private Integer stocktakeId;

    private Integer materialId;

    private Integer unitId;

    private BigDecimal quantity;
    
    private BigDecimal balance;

    private String result;

    private BigDecimal differQuantity;

    private String remark;

    private Integer updateUser;

    private Integer createUser;

    private String createTime;

    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStocktakeId() {
        return stocktakeId;
    }

    public void setStocktakeId(Integer stocktakeId) {
        this.stocktakeId = stocktakeId;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}

	public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BigDecimal getDifferQuantity()
	{
		return differQuantity;
	}

	public void setDifferQuantity(BigDecimal differQuantity)
	{
		this.differQuantity = differQuantity;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}