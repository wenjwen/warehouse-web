package com.warehouse.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.warehouse.common.BaseMapper;
import com.warehouse.common.BaseService;
import com.warehouse.mapper.MaterialMapper;
import com.warehouse.model.Material;
import com.warehouse.util.Entry;

@Service
public class MaterialService extends BaseService<Material>
{
	@Resource
	private MaterialMapper materialMapper;

	/**
	 *  找查所有id,name
	 * @return list
	 */
	public List<Entry> findAllEntry()
	{
		return materialMapper.findAllEntry();
	}

	public List<Material> findAll()
	{
		return materialMapper.findAll();
	}
	
	public List<Material> findFroImport()
	{
		return materialMapper.findFroImport();
	}

	public List<Material> findSelective(Material m)
	{
		return materialMapper.findSelective(m);
	}

	public void updateByIdSelective(Material material)
	{
		materialMapper.updateByPrimaryKeySelective(material);
	}

	public void save(Material material)
	{
		// updata orderNo
		if (material.getOrderNo() != null && material.getOrderNo() > 0){
			// 从这个orderNo开始，全往后退1，如比2变3，4变5
			materialMapper.updateOrderNo(material.getOrderNo());
		}
		
		materialMapper.insert(material);
	}
	
	public int update(Material material){
		if (material.getOrderNo() != null && material.getOrderNo() > 0){
			Material old = materialMapper.selectByPrimaryKey(material.getId());
			// 如果跟数据库中的不一样，则是修改过
			if (old.getOrderNo() != null && old.getOrderNo() > 0 && !material.getOrderNo().equals(old.getOrderNo())){
				// 用户把物料的排序向后调整了
				if(material.getOrderNo() > old.getOrderNo()){ 
					// 把两个序号之间的序号往前推
					materialMapper.updateOrderNoUp(old.getOrderNo()+1, material.getOrderNo());
				}
				// 用户把物料的排序往前调整了
				else if (material.getOrderNo() < old.getOrderNo()){
					// 把两个序号之间的序号往后推
					materialMapper.updateOrderNoDown(material.getOrderNo(), old.getOrderNo()-1);
				}
			} 
		}
		return materialMapper.updateByPrimaryKey(material);
	}

	@Override
	public BaseMapper<Material> getMapper()
	{
		return materialMapper;
	}

}
