
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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SubstrateCompanyLotApplicationImpl implements SubstrateCompanyLotApplication {
	
	Logger log = Logger.getLogger(SubstrateCompanyLotApplicationImpl.class);

	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateCompanyLotDTO getSubstrateCompanyLot(Long id) {
		SubstrateCompanyLot substrateCompanyLot = SubstrateCompanyLot.load(SubstrateCompanyLot.class, id);
		SubstrateCompanyLotDTO substrateCompanyLotDTO = new SubstrateCompanyLotDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(substrateCompanyLotDTO, substrateCompanyLot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateCompanyLotDTO.setId((java.lang.Long)substrateCompanyLot.getId());
		return substrateCompanyLotDTO;
	}
	
	public SubstrateCompanyLotDTO saveSubstrateCompanyLot(SubstrateCompanyLotDTO substrateCompanyLotDTO) {
		SubstrateCompanyLot substrateCompanyLot = new SubstrateCompanyLot();
		try {
        	BeanUtils.copyProperties(substrateCompanyLot, substrateCompanyLotDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		substrateCompanyLot.save();
		substrateCompanyLotDTO.setId((java.lang.Long)substrateCompanyLot.getId());
		
		SubstrateCustomerLot substrateCustomerLot = SubstrateCustomerLot.load(SubstrateCustomerLot.class, substrateCompanyLotDTO.getSubstrateCustomerLotId());
		substrateCompanyLot.setSubstrateCustomerLot(substrateCustomerLot);
		User user ;
		if(substrateCompanyLotDTO.getOptUser() == null ){
			user = User.findByUserAccount("1");
		}else
			user = User.findByUserAccount(substrateCompanyLotDTO.getOptUser().getUserAccount());
		substrateCustomerLot.setLastModifyUser(user);
		
		substrateCompanyLot.save();
		substrateCompanyLotDTO.setId((java.lang.Long)substrateCompanyLot.getId());
		return substrateCompanyLotDTO;
	}
	
	public void updateSubstrateCompanyLot(SubstrateCompanyLotDTO substrateCompanyLotDTO) {
		SubstrateCompanyLot substrateCompanyLot = SubstrateCompanyLot.get(SubstrateCompanyLot.class, substrateCompanyLotDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(substrateCompanyLot, substrateCompanyLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeSubstrateCompanyLot(Long id) {
		this.removeSubstrateCompanyLots(new Long[] { id });
	}
	
	public void removeSubstrateCompanyLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SubstrateCompanyLot substrateCompanyLot = SubstrateCompanyLot.load(SubstrateCompanyLot.class, ids[i]);
			substrateCompanyLot.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstrateCompanyLotDTO> findAllSubstrateCompanyLot() {
		List<SubstrateCompanyLotDTO> list = new ArrayList<SubstrateCompanyLotDTO>();
		List<SubstrateCompanyLot> all = SubstrateCompanyLot.findAll(SubstrateCompanyLot.class);
		for (SubstrateCompanyLot substrateCompanyLot : all) {
			SubstrateCompanyLotDTO substrateCompanyLotDTO = new SubstrateCompanyLotDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(substrateCompanyLotDTO, substrateCompanyLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(substrateCompanyLotDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstrateCompanyLotDTO> pageQuerySubstrateCompanyLot(SubstrateCompanyLotDTO queryVo, int currentPage, int pageSize) {
		List<SubstrateCompanyLotDTO> result = new ArrayList<SubstrateCompanyLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _substrateCompanyLot from SubstrateCompanyLot _substrateCompanyLot   left join _substrateCompanyLot.createUser  left join _substrateCompanyLot.lastModifyUser  left join _substrateCompanyLot.substrateCustomerLot  left join _substrateCompanyLot.mergeSubstrateCompanyLot  left join _substrateCompanyLot.parentSubstrateCompanyLot  left join _substrateCompanyLot.nowSubstrateProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _substrateCompanyLot.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _substrateCompanyLot.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	   	if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
	   		jpql.append(" and _substrateCompanyLot.lotNo like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
	   	}		
	
	
	
	
	
        Page<SubstrateCompanyLot> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (SubstrateCompanyLot substrateCompanyLot : pages.getData()) {
            SubstrateCompanyLotDTO substrateCompanyLotDTO = new SubstrateCompanyLotDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(substrateCompanyLotDTO, substrateCompanyLot);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(substrateCompanyLotDTO);
        }
        return new Page<SubstrateCompanyLotDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserBySubstrateCompanyLot(Long id) {
		String jpql = "select e from SubstrateCompanyLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySubstrateCompanyLot(Long id) {
		String jpql = "select e from SubstrateCompanyLot o right join o.lastModifyUser e where o.id=?";
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
	public SubstrateCustomerLotDTO findSubstrateCustomerLotBySubstrateCompanyLot(Long id) {
		String jpql = "select e from SubstrateCompanyLot o right join o.substrateCustomerLot e where o.id=?";
		SubstrateCustomerLot result = (SubstrateCustomerLot) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		SubstrateCustomerLotDTO  dto = new SubstrateCustomerLotDTO();
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
	public SubstrateCompanyLotDTO findMargeSubstrateCompanyLotBySubstrateCompanyLot(Long id) {
		String jpql = "select e from SubstrateCompanyLot o right join o.mergeSubstrateCompanyLot e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateCompanyLotDTO findParentSubstrateCompanyLotBySubstrateCompanyLot(Long id) {
		String jpql = "select e from SubstrateCompanyLot o right join o.parentSubstrateCompanyLot e where o.id=?";
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


	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstrateCompanyLotDTO> findSubstrateCompanyLotsBySubstrateCompanyLot(Long id, int currentPage, int pageSize) {
		List<SubstrateCompanyLotDTO> result = new ArrayList<SubstrateCompanyLotDTO>();
		String jpql = "select e from SubstrateCompanyLot o right join o.substrateCompanyLots e where o.id=?";
		Page<SubstrateCompanyLot> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).setPage(currentPage, pageSize).pagedList();
        for (SubstrateCompanyLot entity : pages.getData()) {
            SubstrateCompanyLotDTO dto = new SubstrateCompanyLotDTO();
            try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
            result.add(dto);
        }
        return new Page<SubstrateCompanyLotDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
	}		
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateProcessDTO findNowSubstrateProcessBySubstrateCompanyLot(Long id) {
		String jpql = "select e from SubstrateCompanyLot o right join o.nowSubstrateProcess e where o.id=?";
		SubstrateProcess result = (SubstrateProcess) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		SubstrateProcessDTO  dto = new SubstrateProcessDTO();
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
	public Page<SubstrateProcessDTO> findSubstrateProcessesBySubstrateCompanyLot(Long id, int currentPage, int pageSize) {
		List<SubstrateProcessDTO> result = new ArrayList<SubstrateProcessDTO>();
		String jpql = "select e from SubstrateCompanyLot o right join o.substrateProcesses e where o.id=?";
		Page<SubstrateProcess> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).setPage(currentPage, pageSize).pagedList();
        for (SubstrateProcess entity : pages.getData()) {
            SubstrateProcessDTO dto = new SubstrateProcessDTO();
            try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
            result.add(dto);
        }
        return new Page<SubstrateProcessDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
	}		
	
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getNewLotNo() {
		String lotNo = "S";
		String lastLotNo = this.findMaxLotNo(null);
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
	
	private String findMaxLotNo(Long id){
		StringBuilder jpql = new StringBuilder("select max(substring(_substrateCompanyLot.lotNo,1,LENGTH(_substrateCompanyLot.lotNo)-3)) from SubstrateCompanyLot _substrateCompanyLot   left join _substrateCompanyLot.createUser  left join _substrateCompanyLot.lastModifyUser  left join _substrateCompanyLot.substrateCustomerLot  left join _substrateCompanyLot.mergeSubstrateCompanyLot  left join _substrateCompanyLot.parentSubstrateCompanyLot  left join _substrateCompanyLot.nowSubstrateProcess  where 1=1 ");List<Object> conditionVals = new ArrayList<Object>();
		if (id != null) {
	   		jpql.append(" and _substrateCompanyLot.substrateCustomerLot.customer.id = ? ");
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
	public SubstrateCompanyLotDTO findSubstrateCompanyLotByCustomerId(Long id) {
		String jpql = "select o from SubstrateCompanyLot o left join o.substrateCustomerLot e where e.id=?";
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
