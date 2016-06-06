
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialLocationApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MaterialLocationApplicationImpl implements MaterialLocationApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialLocationDTO getMaterialLocation(Long id) {
		MaterialLocation materialLocation = MaterialLocation.load(MaterialLocation.class, id);
		MaterialLocationDTO materialLocationDTO = new MaterialLocationDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(materialLocationDTO, materialLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialLocationDTO.setId((java.lang.Long)materialLocation.getId());
		return materialLocationDTO;
	}
	
	public MaterialLocationDTO saveMaterialLocation(MaterialLocationDTO materialLocationDTO) {
		MaterialLocation materialLocation = new MaterialLocation();
		try {
        	BeanUtils.copyProperties(materialLocation, materialLocationDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		materialLocation.save();
		materialLocationDTO.setId((java.lang.Long)materialLocation.getId());
		return materialLocationDTO;
	}
	
	public void updateMaterialLocation(MaterialLocationDTO materialLocationDTO) {
		MaterialLocation materialLocation = MaterialLocation.get(MaterialLocation.class, materialLocationDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(materialLocation, materialLocationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMaterialLocation(Long id) {
		this.removeMaterialLocations(new Long[] { id });
	}
	
	public void removeMaterialLocations(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MaterialLocation materialLocation = MaterialLocation.load(MaterialLocation.class, ids[i]);
			materialLocation.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialLocationDTO> findAllMaterialLocation() {
		List<MaterialLocationDTO> list = new ArrayList<MaterialLocationDTO>();
		List<MaterialLocation> all = MaterialLocation.findAll(MaterialLocation.class);
		for (MaterialLocation materialLocation : all) {
			MaterialLocationDTO materialLocationDTO = new MaterialLocationDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialLocationDTO, materialLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialLocationDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialLocationDTO> pageQueryMaterialLocation(MaterialLocationDTO queryVo, int currentPage, int pageSize) {
		List<MaterialLocationDTO> result = new ArrayList<MaterialLocationDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _materialLocation from MaterialLocation _materialLocation   left join _materialLocation.createUser  left join _materialLocation.lastModifyUser  left join _materialLocation.materialCompanyLot  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _materialLocation.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _materialLocation.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getLoctionCode() != null && !"".equals(queryVo.getLoctionCode())) {
	   		jpql.append(" and _materialLocation.loctionCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLoctionCode()));
	   	}		
	
        Page<MaterialLocation> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (MaterialLocation materialLocation : pages.getData()) {
            MaterialLocationDTO materialLocationDTO = new MaterialLocationDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(materialLocationDTO, materialLocation);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(materialLocationDTO);
        }
        return new Page<MaterialLocationDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMaterialLocation(Long id) {
		String jpql = "select e from MaterialLocation o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByMaterialLocation(Long id) {
		String jpql = "select e from MaterialLocation o right join o.lastModifyUser e where o.id=?";
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
	public MaterialCompanyLotDTO findMaterialCompanyLotByMaterialLocation(Long id) {
		String jpql = "select e from MaterialLocation o right join o.materialCompanyLot e where o.id=?";
		MaterialCompanyLot result = (MaterialCompanyLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		MaterialCompanyLotDTO  dto = new MaterialCompanyLotDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	
}
