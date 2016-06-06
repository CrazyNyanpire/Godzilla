
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.SerialNumberUtils;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;


@Named
@Transactional
public class WaferCompanyLotApplicationImpl implements WaferCompanyLotApplication {

	Logger log = Logger.getLogger(WaferCompanyLotApplicationImpl.class);
			
	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferCompanyLotDTO getWaferCompanyLot(Long id) {
		WaferCompanyLot waferCompanyLot = WaferCompanyLot.load(WaferCompanyLot.class, id);
		WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(waferCompanyLotDTO, waferCompanyLot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferCompanyLotDTO.setId((java.lang.Long)waferCompanyLot.getId());
		return waferCompanyLotDTO;
	}
	
	public WaferCompanyLotDTO saveWaferCompanyLot(WaferCompanyLotDTO waferCompanyLotDTO) {
		WaferCompanyLot waferCompanyLot = new WaferCompanyLot();
		try {
        	BeanUtils.copyProperties(waferCompanyLot, waferCompanyLotDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		WaferCustomerLot waferCustomerLot = WaferCustomerLot.load(WaferCustomerLot.class, waferCompanyLotDTO.getWaferCustomerLotId());
		waferCompanyLot.setWaferCustomerLot(waferCustomerLot);
		User user ;
		if(waferCompanyLotDTO.getOptUser() == null ){
			user = User.findByUserAccount("1");
		}else
			user = User.findByUserAccount(waferCompanyLotDTO.getOptUser().getUserAccount());
		waferCustomerLot.setCreateUser(user);
		waferCustomerLot.setLastModifyUser(user);
		waferCompanyLot.save();
		waferCompanyLotDTO.setId((java.lang.Long)waferCompanyLot.getId());
		return waferCompanyLotDTO;
	}
	
	public void updateWaferCompanyLot(WaferCompanyLotDTO waferCompanyLotDTO) {
		WaferCompanyLot waferCompanyLot = WaferCompanyLot.get(WaferCompanyLot.class, waferCompanyLotDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(waferCompanyLot, waferCompanyLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeWaferCompanyLot(Long id) {
		this.removeWaferCompanyLots(new Long[] { id });
	}
	
	public void removeWaferCompanyLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferCompanyLot waferCompanyLot = WaferCompanyLot.load(WaferCompanyLot.class, ids[i]);
			waferCompanyLot.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferCompanyLotDTO> findAllWaferCompanyLot() {
		List<WaferCompanyLotDTO> list = new ArrayList<WaferCompanyLotDTO>();
		List<WaferCompanyLot> all = WaferCompanyLot.findAll(WaferCompanyLot.class);
		for (WaferCompanyLot waferCompanyLot : all) {
			WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(waferCompanyLotDTO, waferCompanyLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferCompanyLotDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferCompanyLotDTO> pageQueryWaferCompanyLot(WaferCompanyLotDTO queryVo, int currentPage, int pageSize) {
		List<WaferCompanyLotDTO> result = new ArrayList<WaferCompanyLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _waferCompanyLot from WaferCompanyLot _waferCompanyLot   left join _waferCompanyLot.createUser  left join _waferCompanyLot.lastModifyUser  left join _waferCompanyLot.waferCustomerLot  left join _waferCompanyLot.margeWaferCompanyLot  left join _waferCompanyLot.parentWaferCompanyLot  left join _waferCompanyLot.nowWaferProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _waferCompanyLot.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _waferCompanyLot.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	   	if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
	   		jpql.append(" and _waferCompanyLot.lotNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
	   	}		
	
	
	
	
	
        Page<WaferCompanyLot> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (WaferCompanyLot waferCompanyLot : pages.getData()) {
            WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(waferCompanyLotDTO, waferCompanyLot);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(waferCompanyLotDTO);
        }
        return new Page<WaferCompanyLotDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByWaferCompanyLot(Long id) {
		String jpql = "select e from WaferCompanyLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByWaferCompanyLot(Long id) {
		String jpql = "select e from WaferCompanyLot o right join o.lastModifyUser e where o.id=?";
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
	public WaferCustomerLotDTO findWaferCustomerLotByWaferCompanyLot(Long id) {
		String jpql = "select e from WaferCompanyLot o right join o.waferCustomerLot e where o.id=?";
		WaferCustomerLot result = (WaferCustomerLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferCustomerLotDTO  dto = new WaferCustomerLotDTO();
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
	public WaferCompanyLotDTO findMargeWaferCompanyLotByWaferCompanyLot(Long id) {
		String jpql = "select e from WaferCompanyLot o right join o.margeWaferCompanyLot e where o.id=?";
		WaferCompanyLot result = (WaferCompanyLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferCompanyLotDTO  dto = new WaferCompanyLotDTO();
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
	public WaferCompanyLotDTO findParentWaferCompanyLotByWaferCompanyLot(Long id) {
		String jpql = "select e from WaferCompanyLot o right join o.parentWaferCompanyLot e where o.id=?";
		WaferCompanyLot result = (WaferCompanyLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferCompanyLotDTO  dto = new WaferCompanyLotDTO();
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
	public Page<WaferCompanyLotDTO> findWaferCompanyLotsByWaferCompanyLot(Long id, int currentPage, int pageSize) {
		List<WaferCompanyLotDTO> result = new ArrayList<WaferCompanyLotDTO>();
		String jpql = "select e from WaferCompanyLot o right join o.waferCompanyLots e where o.id=?";
		Page<WaferCompanyLot> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).setPage(currentPage, pageSize).pagedList();
        for (WaferCompanyLot entity : pages.getData()) {
            WaferCompanyLotDTO dto = new WaferCompanyLotDTO();
            try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
            result.add(dto);
        }
        return new Page<WaferCompanyLotDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
	}		
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferProcessDTO findNowProcessByWaferCompanyLot(Long id) {
		String jpql = "select e from WaferCompanyLot o right join o.nowProcess e where o.id=?";
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
	public Page<WaferProcessDTO> findProcessesByWaferCompanyLot(Long id, int currentPage, int pageSize) {
		List<WaferProcessDTO> result = new ArrayList<WaferProcessDTO>();
		String jpql = "select e from WaferCompanyLot o right join o.processes e where o.id=?";
		Page<WaferProcess> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).setPage(currentPage, pageSize).pagedList();
        for (WaferProcess entity : pages.getData()) {
        	WaferProcessDTO dto = new WaferProcessDTO();
            try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
            result.add(dto);
        }
        return new Page<WaferProcessDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getNewLotNo(Long id) {
		String lotNo = "W";
		String lastLotNo = this.findMaxLotNoByCustomerLot(null);
		if(lastLotNo == null){
			lotNo += "A00001";
		}else{
			String number = lastLotNo.split("-")[0].substring(1);
			lotNo += SerialNumberUtils.sn(6, Integer.parseInt(number.substring(1)), number.substring(0,1));
		}
		lotNo += "-01";
		log.info("getNewLotNo : " + lotNo);
		return lotNo;
	}		
	
	private String findMaxLotNoByCustomerLot(Long id){
		StringBuilder jpql = new StringBuilder("select max(substring(_waferCompanyLot.lotNo,1,LENGTH(_waferCompanyLot.lotNo)-3)) from WaferCompanyLot _waferCompanyLot   left join _waferCompanyLot.createUser  left join _waferCompanyLot.lastModifyUser  left join _waferCompanyLot.waferCustomerLot  where 1=1 ");
		List<Object> conditionVals = new ArrayList<Object>();
		if (id != null) {
	   		jpql.append(" and _waferCompanyLot.waferCustomerLot.customer.id = ? ");
	   		conditionVals.add(id);
	   	}
		
		jpql.append("");
		Object lastLotNo = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		if(lastLotNo == null){
			return null;
		}else{
			return lastLotNo.toString();
		}
		
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferCompanyLotDTO findWaferCompanyLotByCustomerId(Long id) {
		String jpql = "select o from WaferCompanyLot o left join o.waferCustomerLot e where e.id=?";
		WaferCompanyLot result = (WaferCompanyLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferCompanyLotDTO  dto = new WaferCompanyLotDTO();
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
