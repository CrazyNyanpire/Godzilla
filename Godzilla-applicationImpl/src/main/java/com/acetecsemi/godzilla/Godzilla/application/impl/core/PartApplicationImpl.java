
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
import com.acetecsemi.godzilla.Godzilla.application.core.PartApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class PartApplicationImpl implements PartApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PartDTO getPart(Long id) {
		Part part = Part.load(Part.class, id);
		PartDTO partDTO = new PartDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(partDTO, part);
		} catch (Exception e) {
			e.printStackTrace();
		}
		partDTO.setId((java.lang.Long)part.getId());
		return partDTO;
	}
	
	public PartDTO savePart(PartDTO partDTO) {
		Part part = new Part();
		try {
        	BeanUtils.copyProperties(part, partDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		part.save();
		partDTO.setId((java.lang.Long)part.getId());
		return partDTO;
	}
	
	public void updatePart(PartDTO partDTO) {
		Part part = Part.get(Part.class, partDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(part, partDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removePart(Long id) {
		this.removeParts(new Long[] { id });
	}
	
	public void removeParts(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Part part = Part.load(Part.class, ids[i]);
			part.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PartDTO> findAllPart() {
		List<PartDTO> list = new ArrayList<PartDTO>();
		List<Part> all = Part.findAll(Part.class);
		for (Part part : all) {
			PartDTO partDTO = new PartDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(partDTO, part);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(partDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PartDTO> pageQueryPart(PartDTO queryVo, int currentPage, int pageSize) {
		List<PartDTO> result = new ArrayList<PartDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _part from Part _part   left join _part.createUser  left join _part.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _part.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _part.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getPartNo() != null && !"".equals(queryVo.getPartNo())) {
	   		jpql.append(" and _part.partNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPartNo()));
	   	}		
        Page<Part> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Part part : pages.getData()) {
            PartDTO partDTO = new PartDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(partDTO, part);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(partDTO);
        }
        return new Page<PartDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByPart(Long id) {
		String jpql = "select e from Part o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByPart(Long id) {
		String jpql = "select e from Part o right join o.lastModifyUser e where o.id=?";
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
