package com.warehouse.model;


public class StockSearch {

    private String stockNo;

    private Integer stockType;

    private String typeName;

    private String stockTime;

    private String driverName;

    private String trunkNo;

    private String target;

    private String source;

    private Integer stockId;

    private Integer materialId;
    
    private String materialName;

    private Integer unitId;

    private Double unitPrice;

    private Double quantity;

    private String remark;

    

    public Integer getStockId()
	{
		return stockId;
	}

	public void setStockId(Integer stockId)
	{
		this.stockId = stockId;
	}

	public Integer getMaterialId()
	{
		return materialId;
	}

	public void setMaterialId(Integer materialId)
	{
		this.materialId = materialId;
	}

	public String getMaterialName()
	{
		return materialName;
	}

	public void setMaterialName(String materialName)
	{
		this.materialName = materialName;
	}

	public Integer getUnitId()
	{
		return unitId;
	}

	public void setUnitId(Integer unitId)
	{
		this.unitId = unitId;
	}

	public Double getUnitPrice()
	{
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	public Double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Double quantity)
	{
		this.quantity = quantity;
	}

	public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStockTime() {
        return stockTime;
    }

    public void setStockTime(String stockTime) {
        this.stockTime = stockTime;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getTrunkNo() {
        return trunkNo;
    }

    public void setTrunkNo(String trunkNo) {
        this.trunkNo = trunkNo;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    
}