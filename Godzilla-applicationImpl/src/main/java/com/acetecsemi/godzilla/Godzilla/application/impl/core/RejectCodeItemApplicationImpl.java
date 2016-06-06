
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
import com.acetecsemi.godzilla.Godzilla.application.core.RejectCodeItemApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class RejectCodeItemApplicationImpl implements RejectCodeItemApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RejectCodeItemDTO getRejectCodeItem(Long id) {
		RejectCodeItem rejectCodeItem = RejectCodeItem.load(RejectCodeItem.class, id);
		RejectCodeItemDTO rejectCodeItemDTO = new RejectCodeItemDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(rejectCodeItemDTO, rejectCodeItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rejectCodeItemDTO.setId((java.lang.Long)rejectCodeItem.getId());
		return rejectCodeItemDTO;
	}
	
	public RejectCodeItemDTO saveRejectCodeItem(RejectCodeItemDTO rejectCodeItemDTO) {
		RejectCodeItem rejectCodeItem = new RejectCodeItem();
		try {
        	BeanUtils.copyProperties(rejectCodeItem, rejectCodeItemDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		rejectCodeItem.save();
		rejectCodeItemDTO.setId((java.lang.Long)rejectCodeItem.getId());
		return rejectCodeItemDTO;
	}
	
	public void updateRejectCodeItem(RejectCodeItemDTO rejectCodeItemDTO) {
		RejectCodeItem rejectCodeItem = RejectCodeItem.get(RejectCodeItem.class, rejectCodeItemDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(rejectCodeItem, rejectCodeItemDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeRejectCodeItem(Long id) {
		this.removeRejectCodeItems(new Long[] { id });
	}
	
	public void removeRejectCodeItems(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			RejectCodeItem rejectCodeItem = RejectCodeItem.load(RejectCodeItem.class, ids[i]);
			rejectCodeItem.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RejectCodeItemDTO> findAllRejectCodeItem() {
		List<RejectCodeItemDTO> list = new ArrayList<RejectCodeItemDTO>();
		List<RejectCodeItem> all = RejectCodeItem.findAll(RejectCodeItem.class);
		for (RejectCodeItem rejectCodeItem : all) {
			RejectCodeItemDTO rejectCodeItemDTO = new RejectCodeItemDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(rejectCodeItemDTO, rejectCodeItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(rejectCodeItemDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RejectCodeItemDTO> pageQueryRejectCodeItem(RejectCodeItemDTO queryVo, int currentPage, int pageSize) {
		List<RejectCodeItemDTO> result = new ArrayList<RejectCodeItemDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _rejectCodeItem from RejectCodeItem _rejectCodeItem   left join _rejectCodeItem.createUser  left join _rejectCodeItem.lastModifyUser  left join _rejectCodeItem.station  left join _rejectCodeItem.manufactureProcess  left join _rejectCodeItem.waferProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _rejectCodeItem.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _rejectCodeItem.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getRejcetCode() != null && !"".equals(queryVo.getRejcetCode())) {
	   		jpql.append(" and _rejectCodeItem.rejcetCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRejcetCode()));
	   	}		
	
	   	if (queryVo.getItemName() != null && !"".equals(queryVo.getItemName())) {
	   		jpql.append(" and _rejectCodeItem.itemName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getItemName()));
	   	}		
	
	   	if (queryVo.getDescription() != null && !"".equals(queryVo.getDescription())) {
	   		jpql.append(" and _rejectCodeItem.description like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDescription()));
	   	}		
	
	   	if (queryVo.getIsUse() != null) {
		   	jpql.append(" and _rejectCodeItem.isUse is ?");
		   	conditionVals.add(queryVo.getIsUse());
	   	}	
	
	
	
	
        Page<RejectCodeItem> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (RejectCodeItem rejectCodeItem : pages.getData()) {
            RejectCodeItemDTO rejectCodeItemDTO = new RejectCodeItemDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(rejectCodeItemDTO, rejectCodeItem);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                                                                           result.add(rejectCodeItemDTO);
        }
        return new Page<RejectCodeItemDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByRejectCodeItem(Long id) {
		String jpql = "select e from RejectCodeItem o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByRejectCodeItem(Long id) {
		String jpql = "select e from RejectCodeItem o right join o.lastModifyUser e where o.id=?";
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
	public StationDTO findStationByRejectCodeItem(Long id) {
		String jpql = "select e from RejectCodeItem o right join o.station e where o.id=?";
		Station result = (Station) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		StationDTO  dto = new StationDTO();
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
	public ManufactureProcessDTO findManufactureProcessByRejectCodeItem(Long id) {
		String jpql = "select e from RejectCodeItem o right join o.manufactureProcess e where o.id=?";
		ManufactureProcess result = (ManufactureProcess) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		ManufactureProcessDTO  dto = new ManufactureProcessDTO();
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
	public WaferProcessDTO findWaferProcessByRejectCodeItem(Long id) {
		String jpql = "select e from RejectCodeItem o right join o.waferProcess e where o.id=?";
		WaferProcess result = (WaferProcess) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferProcessDTO  dto = new WaferProcessDTO();
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
