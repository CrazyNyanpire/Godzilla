
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MaterialApplicationImpl implements MaterialApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialDTO getMaterial(Long id) {
		Material material = Material.load(Material.class, id);
		MaterialDTO materialDTO = new MaterialDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(materialDTO, material);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialDTO.setMaterialNameId(material.getMaterialName().getId());
		materialDTO.setUnit(material.getMinUnit());
		materialDTO.setId((java.lang.Long)material.getId());
		return materialDTO;
	}
	
	public MaterialDTO saveMaterial(MaterialDTO materialDTO) {
		Material material = new Material();
		try {
        	BeanUtils.copyProperties(material, materialDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		User user;
		if (materialDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(materialDTO.getUserDTO()
					.getUserAccount());
		material.setCreateUser(user);
		MaterialName materialName = MaterialName.load(MaterialName.class, materialDTO.getMaterialNameId());
		material.setMaterialName(materialName);
		material.save();
		materialDTO.setId((java.lang.Long)material.getId());
		return materialDTO;
	}
	
	public void updateMaterial(MaterialDTO materialDTO) {
		Material material = Material.get(Material.class, materialDTO.getId());
		materialDTO.setCreateDate(material.getCreateDate());
		User user;
		if (materialDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(materialDTO.getUserDTO()
					.getUserAccount());
		material.setCreateUser(user);
		MaterialName materialName = MaterialName.load(MaterialName.class, materialDTO.getMaterialNameId());
		material.setMaterialName(materialName);
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(material, materialDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMaterial(Long id) {
		this.removeMaterials(new Long[] { id });
	}
	
	public void removeMaterials(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Material material = Material.load(Material.class, ids[i]);
			material.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialDTO> findAllMaterial() {
		List<MaterialDTO> list = new ArrayList<MaterialDTO>();
		List<Material> all = Material.findAll(Material.class);
		for (Material material : all) {
			MaterialDTO materialDTO = new MaterialDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialDTO, material);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialDTO> pageQueryMaterial(MaterialDTO queryVo, int currentPage, int pageSize) {
		List<MaterialDTO> result = new ArrayList<MaterialDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _material from Material _material   left join _material.createUser  left join _material.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _material.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _material.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getMaterialName() != null && !"".equals(queryVo.getMaterialName())) {
	   		jpql.append(" and _material.materialName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMaterialName()));
	   	}		
	
	   	if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
	   		jpql.append(" and _material.partId like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPartId()));
	   	}		
	
	   	if (queryVo.getPartNameCN() != null && !"".equals(queryVo.getPartNameCN())) {
	   		jpql.append(" and _material.partNameCN like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPartNameCN()));
	   	}		
	
	   	if (queryVo.getUnit() != null && !"".equals(queryVo.getUnit())) {
	   		jpql.append(" and _material.unit like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getUnit()));
	   	}		
	
	   	if (queryVo.getVender() != null && !"".equals(queryVo.getVender())) {
	   		jpql.append(" and _material.vender like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getVender()));
	   	}		
	
	   	if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
	   		jpql.append(" and _material.pon like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
	   	}		
	   	if (queryVo.getMaterialType() != null) {
	   		jpql.append(" and _material.materialName.materialType=?");
	   		conditionVals.add(queryVo.getMaterialType());
	   	}	
	
        Page<Material> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Material material : pages.getData()) {
            MaterialDTO materialDTO = new MaterialDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(materialDTO, material);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
           result.add(materialDTO);
        }
        return new Page<MaterialDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMaterial(Long id) {
		String jpql = "select e from Material o right join o.createUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findLastModifyUserByMaterial(Long id) {
		String jpql = "select e from Material o right join o.lastModifyUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialDTO> findMaterialByType(int type) {
		List<MaterialDTO> list = new ArrayList<MaterialDTO>();
		String jpql = "select o from Material o where o.materialName.materialType=?";
		List<Material> all = (List<Material>) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { type }).list();
		for (Material material : all) {
			MaterialDTO materialDTO = new MaterialDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialDTO, material);
			} catch (Exception e) {
				e.printStackTrace();
			}
			materialDTO.setMaterialName(material.getMaterialName().getMaterialName());
			materialDTO.setMaterialType(material.getMaterialName().getMaterialType());
			list.add(materialDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialDTO> findMaterialNameByType(int type) {
		List<MaterialDTO> list = new ArrayList<MaterialDTO>();
		String jpql = "select o from MaterialName o where o.materialType=?";
		List<MaterialName> all = (List<MaterialName>) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { type }).list();
		for (MaterialName materialName : all) {
			MaterialDTO materialDTO = new MaterialDTO();
			materialDTO.setId(materialName.getId());
			materialDTO.setMaterialName(materialName.getMaterialName());
			materialDTO.setMaterialType(materialName.getMaterialType());
			list.add(materialDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialDTO> getByMaterialNameId(Long type) {
		List<MaterialDTO> list = new ArrayList<MaterialDTO>();
		String jpql = "select o from Material o where o.materialName.id=?";
		List<Material> all = (List<Material>) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { type }).list();
		for (Material material : all) {
			MaterialDTO materialDTO = new MaterialDTO();
			try {
				BeanUtils.copyProperties(materialDTO, material);
			} catch (Exception e) {
				e.printStackTrace();
			}
			materialDTO.setMaterialName(material.getMaterialName().getMaterialName());
			materialDTO.setMaterialType(material.getMaterialName().getMaterialType());
			list.add(materialDTO);
		}
		return list;
	}
	
}
