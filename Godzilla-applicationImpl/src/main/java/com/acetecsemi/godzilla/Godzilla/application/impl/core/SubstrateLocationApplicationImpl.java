
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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateLocationApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SubstrateLocationApplicationImpl implements SubstrateLocationApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateLocationDTO getSubstrateLocation(Long id) {
		SubstrateLocation substrateLocation = SubstrateLocation.load(SubstrateLocation.class, id);
		SubstrateLocationDTO substrateLocationDTO = new SubstrateLocationDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(substrateLocationDTO, substrateLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateLocationDTO.setId((java.lang.Long)substrateLocation.getId());
		return substrateLocationDTO;
	}
	
	public SubstrateLocationDTO saveSubstrateLocation(SubstrateLocationDTO substrateLocationDTO) {
		SubstrateLocation substrateLocation = new SubstrateLocation();
		try {
        	BeanUtils.copyProperties(substrateLocation, substrateLocationDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		substrateLocation.save();
		substrateLocationDTO.setId((java.lang.Long)substrateLocation.getId());
		return substrateLocationDTO;
	}
	
	public void updateSubstrateLocation(SubstrateLocationDTO substrateLocationDTO) {
		SubstrateLocation substrateLocation = SubstrateLocation.get(SubstrateLocation.class, substrateLocationDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(substrateLocation, substrateLocationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeSubstrateLocation(Long id) {
		this.removeSubstrateLocations(new Long[] { id });
	}
	
	public void removeSubstrateLocations(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SubstrateLocation substrateLocation = SubstrateLocation.load(SubstrateLocation.class, ids[i]);
			substrateLocation.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstrateLocationDTO> findAllSubstrateLocation() {
		List<SubstrateLocationDTO> list = new ArrayList<SubstrateLocationDTO>();
		List<SubstrateLocation> all = SubstrateLocation.findAll(SubstrateLocation.class);
		for (SubstrateLocation substrateLocation : all) {
			SubstrateLocationDTO substrateLocationDTO = new SubstrateLocationDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(substrateLocationDTO, substrateLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(substrateLocationDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstrateLocationDTO> pageQuerySubstrateLocation(SubstrateLocationDTO queryVo, int currentPage, int pageSize) {
		List<SubstrateLocationDTO> result = new ArrayList<SubstrateLocationDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _substrateLocation from SubstrateLocation _substrateLocation   left join _substrateLocation.createUser  left join _substrateLocation.lastModifyUser  left join _substrateLocation.substrateCompanyLot  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _substrateLocation.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _substrateLocation.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getLoctionCode() != null && !"".equals(queryVo.getLoctionCode())) {
	   		jpql.append(" and _substrateLocation.loctionCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLoctionCode()));
	   	}		
	
        Page<SubstrateLocation> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (SubstrateLocation substrateLocation : pages.getData()) {
            SubstrateLocationDTO substrateLocationDTO = new SubstrateLocationDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(substrateLocationDTO, substrateLocation);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(substrateLocationDTO);
        }
        return new Page<SubstrateLocationDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserBySubstrateLocation(Long id) {
		String jpql = "select e from SubstrateLocation o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySubstrateLocation(Long id) {
		String jpql = "select e from SubstrateLocation o right join o.lastModifyUser e where o.id=?";
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
	public SubstrateCompanyLotDTO findSubstrateCompanyLotBySubstrateLocation(Long id) {
		String jpql = "select e from SubstrateLocation o right join o.substrateCompanyLot e where o.id=?";
		SubstrateCompanyLot result = (SubstrateCompanyLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		SubstrateCompanyLotDTO  dto = new SubstrateCompanyLotDTO();
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
