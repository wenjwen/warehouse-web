package com.warehouse.util;

public class Entry
{
	private Integer id;
	
	private String name;
	
	private Object extraValue1;
	
	private Object extraValue2;

	

	public Object getExtraValue1()
	{
		return extraValue1;
	}

	public void setExtraValue1(Object extraValue1)
	{
		this.extraValue1 = extraValue1;
	}

	public Object getExtraValue2()
	{
		return extraValue2;
	}

	public void setExtraValue2(Object extraValue2)
	{
		this.extraValue2 = extraValue2;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
}
