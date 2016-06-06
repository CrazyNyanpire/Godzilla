
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialNameApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MaterialNameApplicationImpl implements MaterialNameApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialNameDTO getMaterialName(Long id) {
		MaterialName materialName = MaterialName.load(MaterialName.class, id);
		MaterialNameDTO materialNameDTO = new MaterialNameDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(materialNameDTO, materialName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialNameDTO.setId((java.lang.Long)materialName.getId());
		return materialNameDTO;
	}
	
	public MaterialNameDTO saveMaterialName(MaterialNameDTO materialNameDTO) {
		MaterialName materialName = new MaterialName();
		try {
        	BeanUtils.copyProperties(materialName, materialNameDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		materialName.save();
		materialNameDTO.setId((java.lang.Long)materialName.getId());
		return materialNameDTO;
	}
	
	public void updateMaterialName(MaterialNameDTO materialNameDTO) {
		MaterialName materialName = MaterialName.get(MaterialName.class, materialNameDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(materialName, materialNameDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMaterialName(Long id) {
		this.removeMaterialNames(new Long[] { id });
	}
	
	public void removeMaterialNames(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MaterialName materialName = MaterialName.load(MaterialName.class, ids[i]);
			materialName.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialNameDTO> findAllMaterialName() {
		List<MaterialNameDTO> list = new ArrayList<MaterialNameDTO>();
		List<MaterialName> all = MaterialName.findAll(MaterialName.class);
		for (MaterialName materialName : all) {
			MaterialNameDTO materialNameDTO = new MaterialNameDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialNameDTO, materialName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialNameDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialNameDTO> pageQueryMaterialName(MaterialNameDTO queryVo, int currentPage, int pageSize) {
		List<MaterialNameDTO> result = new ArrayList<MaterialNameDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _materialName from MaterialName _materialName   where 1=1 ");
	
	
	   	if (queryVo.getMaterialName() != null && !"".equals(queryVo.getMaterialName())) {
	   		jpql.append(" and _materialName.materialName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getMaterialName()));
	   	}		
	
        Page<MaterialName> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (MaterialName materialName : pages.getData()) {
            MaterialNameDTO materialNameDTO = new MaterialNameDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(materialNameDTO, materialName);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                result.add(materialNameDTO);
        }
        return new Page<MaterialNameDTO>(pages.getStart(), pages.getResultCount(), pageSize, result);
	}
	
	
}
