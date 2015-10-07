package com.warehouse.model;

import java.math.BigDecimal;

public class Material {
    private Integer id;

    private Integer updateUser;

    private Integer createUser;

    private String createTime;

    private String updateTime;

    private Integer unitId;
    
    private String unitName;

    private BigDecimal totalQuantity;

    private BigDecimal balance;

    private String size;

    private String lastStockTake;

    private BigDecimal avgUnitPrice;

    private BigDecimal unitPrice;

    private String name;

    private String code;

    private String shelf;

    private Integer categoryId;

    private Integer disabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName()
	{
		return unitName;
	}

	public void setUnitName(String unitName)
	{
		this.unitName = unitName;
	}

	public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLastStockTake() {
        return lastStockTake;
    }

    public void setLastStockTake(String lastStockTake) {
        this.lastStockTake = lastStockTake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

	public BigDecimal getTotalQuantity()
	{
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity)
	{
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}

	public BigDecimal getAvgUnitPrice()
	{
		return avgUnitPrice;
	}

	public void setAvgUnitPrice(BigDecimal avgUnitPrice)
	{
		this.avgUnitPrice = avgUnitPrice;
	}

	public BigDecimal getUnitPrice()
	{
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice)
	{
		this.unitPrice = unitPrice;
	}
    
    
}