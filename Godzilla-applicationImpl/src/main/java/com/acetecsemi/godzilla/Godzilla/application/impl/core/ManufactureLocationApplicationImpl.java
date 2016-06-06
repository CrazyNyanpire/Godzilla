
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
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureLocationApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ManufactureLocationApplicationImpl implements ManufactureLocationApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureLocationDTO getManufactureLocation(Long id) {
		ManufactureLocation manufactureLocation = ManufactureLocation.load(ManufactureLocation.class, id);
		ManufactureLocationDTO manufactureLocationDTO = new ManufactureLocationDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(manufactureLocationDTO, manufactureLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureLocationDTO.setId((java.lang.Long)manufactureLocation.getId());
		return manufactureLocationDTO;
	}
	
	public ManufactureLocationDTO saveManufactureLocation(ManufactureLocationDTO manufactureLocationDTO) {
		ManufactureLocation manufactureLocation = new ManufactureLocation();
		try {
        	BeanUtils.copyProperties(manufactureLocation, manufactureLocationDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		manufactureLocation.save();
		manufactureLocationDTO.setId((java.lang.Long)manufactureLocation.getId());
		return manufactureLocationDTO;
	}
	
	public void updateManufactureLocation(ManufactureLocationDTO manufactureLocationDTO) {
		ManufactureLocation manufactureLocation = ManufactureLocation.get(ManufactureLocation.class, manufactureLocationDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(manufactureLocation, manufactureLocationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeManufactureLocation(Long id) {
		this.removeManufactureLocations(new Long[] { id });
	}
	
	public void removeManufactureLocations(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ManufactureLocation manufactureLocation = ManufactureLocation.load(ManufactureLocation.class, ids[i]);
			manufactureLocation.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureLocationDTO> findAllManufactureLocation() {
		List<ManufactureLocationDTO> list = new ArrayList<ManufactureLocationDTO>();
		List<ManufactureLocation> all = ManufactureLocation.findAll(ManufactureLocation.class);
		for (ManufactureLocation manufactureLocation : all) {
			ManufactureLocationDTO manufactureLocationDTO = new ManufactureLocationDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureLocationDTO, manufactureLocation);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(manufactureLocationDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureLocationDTO> pageQueryManufactureLocation(ManufactureLocationDTO queryVo, int currentPage, int pageSize) {
		List<ManufactureLocationDTO> result = new ArrayList<ManufactureLocationDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _manufactureLocation from ManufactureLocation _manufactureLocation   left join _manufactureLocation.createUser  left join _manufactureLocation.lastModifyUser  left join _manufactureLocation.manufactureLot  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _manufactureLocation.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _manufactureLocation.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getLoctionCode() != null && !"".equals(queryVo.getLoctionCode())) {
	   		jpql.append(" and _manufactureLocation.loctionCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLoctionCode()));
	   	}		
	
        Page<ManufactureLocation> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (ManufactureLocation manufactureLocation : pages.getData()) {
            ManufactureLocationDTO manufactureLocationDTO = new ManufactureLocationDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(manufactureLocationDTO, manufactureLocation);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(manufactureLocationDTO);
        }
        return new Page<ManufactureLocationDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByManufactureLocation(Long id) {
		String jpql = "select e from ManufactureLocation o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByManufactureLocation(Long id) {
		String jpql = "select e from ManufactureLocation o right join o.lastModifyUser e where o.id=?";
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
	public ManufactureLotDTO findManufactureLotByManufactureLocation(Long id) {
		String jpql = "select e from ManufactureLocation o right join o.manufactureLot e where o.id=?";
		ManufactureLot result = (ManufactureLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		ManufactureLotDTO  dto = new ManufactureLotDTO();
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
