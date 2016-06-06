
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferInfoRejectDescriptionApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class WaferInfoRejectDescriptionApplicationImpl implements WaferInfoRejectDescriptionApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferInfoRejectDescriptionDTO getWaferInfoRejectDescription(Long id) {
		WaferInfoRejectDescription waferInfoRejectDescription = WaferInfoRejectDescription.load(WaferInfoRejectDescription.class, id);
		WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO = new WaferInfoRejectDescriptionDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(waferInfoRejectDescriptionDTO, waferInfoRejectDescription);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferInfoRejectDescriptionDTO.setId((java.lang.Long)waferInfoRejectDescription.getId());
		return waferInfoRejectDescriptionDTO;
	}
	
	public WaferInfoRejectDescriptionDTO saveWaferInfoRejectDescription(WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO) {
		WaferInfoRejectDescription waferInfoRejectDescription = new WaferInfoRejectDescription();
		try {
        	BeanUtils.copyProperties(waferInfoRejectDescription, waferInfoRejectDescriptionDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		waferInfoRejectDescription.save();
		waferInfoRejectDescriptionDTO.setId((java.lang.Long)waferInfoRejectDescription.getId());
		return waferInfoRejectDescriptionDTO;
	}
	
	public void updateWaferInfoRejectDescription(WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO) {
		WaferInfoRejectDescription waferInfoRejectDescription = WaferInfoRejectDescription.get(WaferInfoRejectDescription.class, waferInfoRejectDescriptionDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(waferInfoRejectDescription, waferInfoRejectDescriptionDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeWaferInfoRejectDescription(Long id) {
		this.removeWaferInfoRejectDescriptions(new Long[] { id });
	}
	
	public void removeWaferInfoRejectDescriptions(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferInfoRejectDescription waferInfoRejectDescription = WaferInfoRejectDescription.load(WaferInfoRejectDescription.class, ids[i]);
			waferInfoRejectDescription.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferInfoRejectDescriptionDTO> findAllWaferInfoRejectDescription() {
		List<WaferInfoRejectDescriptionDTO> list = new ArrayList<WaferInfoRejectDescriptionDTO>();
		List<WaferInfoRejectDescription> all = WaferInfoRejectDescription.findAll(WaferInfoRejectDescription.class);
		for (WaferInfoRejectDescription waferInfoRejectDescription : all) {
			WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO = new WaferInfoRejectDescriptionDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(waferInfoRejectDescriptionDTO, waferInfoRejectDescription);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferInfoRejectDescriptionDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferInfoRejectDescriptionDTO> pageQueryWaferInfoRejectDescription(WaferInfoRejectDescriptionDTO queryVo, int currentPage, int pageSize) {
		List<WaferInfoRejectDescriptionDTO> result = new ArrayList<WaferInfoRejectDescriptionDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _waferInfoRejectDescription from WaferInfoRejectDescription _waferInfoRejectDescription   left join _waferInfoRejectDescription.waferProcess  left join _waferInfoRejectDescription.waferInfo  where 1=1 ");
	
	
	
	
	   	if (queryVo.getRejcetDescription() != null && !"".equals(queryVo.getRejcetDescription())) {
	   		jpql.append(" and _waferInfoRejectDescription.rejcetDescription like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRejcetDescription()));
	   	}		
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _waferInfoRejectDescription.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
        Page<WaferInfoRejectDescription> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (WaferInfoRejectDescription waferInfoRejectDescription : pages.getData()) {
            WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO = new WaferInfoRejectDescriptionDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(waferInfoRejectDescriptionDTO, waferInfoRejectDescription);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                result.add(waferInfoRejectDescriptionDTO);
        }
        return new Page<WaferInfoRejectDescriptionDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferProcessDTO findWaferProcessByWaferInfoRejectDescription(Long id) {
		String jpql = "select e from WaferInfoRejectDescription o right join o.waferProcess e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferInfoDTO findWaferInfoByWaferInfoRejectDescription(Long id) {
		String jpql = "select e from WaferInfoRejectDescription o right join o.waferInfo e where o.id=?";
		WaferInfo result = (WaferInfo) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferInfoDTO  dto = new WaferInfoDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public void saveWaferInfoRejectDescriptions(List<WaferInfoRejectDescriptionDTO> waferInfoRejectDescriptionDTOs) {
		for(WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO : waferInfoRejectDescriptionDTOs){
			WaferInfoRejectDescription waferInfoRejectDescription = new WaferInfoRejectDescription();
			WaferInfo waferInfo = WaferInfo.load(WaferInfo.class, waferInfoRejectDescriptionDTO.getWaferInfoId());
			waferInfoRejectDescription.setWaferInfo(waferInfo);
			WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, waferInfoRejectDescriptionDTO.getWaferProcessId());
			waferInfoRejectDescription.setWaferProcess(waferProcess);
			waferInfoRejectDescription.setRejcetDescription(waferInfoRejectDescriptionDTO.getRejcetDescription());
			waferInfoRejectDescription.setCreateDate(new Date());
			waferInfoRejectDescription.save();
		}
	}
	
}
