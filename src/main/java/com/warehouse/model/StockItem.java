package com.warehouse.model;

import java.math.BigDecimal;

public class StockItem {
    private Integer id;

    private Integer stockId;
    
    private Integer materialId;
    
    private BigDecimal balance;

    private Integer unitId;

    private BigDecimal unitPrice;

    // 对应的物料库存数量
    private BigDecimal quantity;
    
    // 对应的物料的总数量
    private BigDecimal totalQuantity;

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

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

	public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}
	
	public BigDecimal getTotalQuantity()
	{
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity)
	{
		this.totalQuantity = totalQuantity;
	}

	public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public BigDecimal getUnitPrice()
	{
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	public BigDecimal getQuantity()
	{
		return quantity;
	}

	public void setQuantity(BigDecimal quantity)
	{
		this.quantity = quantity;
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