
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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstratePartApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SubstratePartApplicationImpl implements SubstratePartApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstratePartDTO getSubstratePart(Long id) {
		SubstratePart substratePart = SubstratePart.load(SubstratePart.class, id);
		SubstratePartDTO substratePartDTO = new SubstratePartDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(substratePartDTO, substratePart);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substratePartDTO.setId((java.lang.Long)substratePart.getId());
		return substratePartDTO;
	}
	
	public SubstratePartDTO saveSubstratePart(SubstratePartDTO substratePartDTO) {
		SubstratePart substratePart = new SubstratePart();
		try {
        	BeanUtils.copyProperties(substratePart, substratePartDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		substratePart.save();
		substratePartDTO.setId((java.lang.Long)substratePart.getId());
		return substratePartDTO;
	}
	
	public void updateSubstratePart(SubstratePartDTO substratePartDTO) {
		SubstratePart substratePart = SubstratePart.get(SubstratePart.class, substratePartDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(substratePart, substratePartDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeSubstratePart(Long id) {
		this.removeSubstrateParts(new Long[] { id });
	}
	
	public void removeSubstrateParts(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SubstratePart substratePart = SubstratePart.load(SubstratePart.class, ids[i]);
			substratePart.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstratePartDTO> findAllSubstratePart() {
		List<SubstratePartDTO> list = new ArrayList<SubstratePartDTO>();
		List<SubstratePart> all = SubstratePart.findAll(SubstratePart.class);
		for (SubstratePart substratePart : all) {
			SubstratePartDTO substratePartDTO = new SubstratePartDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(substratePartDTO, substratePart);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(substratePartDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstratePartDTO> pageQuerySubstratePart(SubstratePartDTO queryVo, int currentPage, int pageSize) {
		List<SubstratePartDTO> result = new ArrayList<SubstratePartDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _substratePart from SubstratePart _substratePart   left join _substratePart.createUser  left join _substratePart.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _substratePart.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _substratePart.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getPartNo() != null && !"".equals(queryVo.getPartNo())) {
	   		jpql.append(" and _substratePart.partNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPartNo()));
	   	}		
        Page<SubstratePart> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (SubstratePart substratePart : pages.getData()) {
            SubstratePartDTO substratePartDTO = new SubstratePartDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(substratePartDTO, substratePart);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(substratePartDTO);
        }
        return new Page<SubstratePartDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserBySubstratePart(Long id) {
		String jpql = "select e from SubstratePart o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySubstratePart(Long id) {
		String jpql = "select e from SubstratePart o right join o.lastModifyUser e where o.id=?";
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

	
}
