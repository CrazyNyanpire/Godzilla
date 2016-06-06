
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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SubstrateStatusOptLogApplicationImpl implements SubstrateStatusOptLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateStatusOptLogDTO getSubstrateStatusOptLog(Long id) {
		SubstrateStatusOptLog substrateStatusOptLog = SubstrateStatusOptLog.load(SubstrateStatusOptLog.class, id);
		SubstrateStatusOptLogDTO substrateStatusOptLogDTO = new SubstrateStatusOptLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(substrateStatusOptLogDTO, substrateStatusOptLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateStatusOptLogDTO.setId((java.lang.Long)substrateStatusOptLog.getId());
		return substrateStatusOptLogDTO;
	}
	
	public SubstrateStatusOptLogDTO saveSubstrateStatusOptLog(SubstrateStatusOptLogDTO substrateStatusOptLogDTO) {
		SubstrateStatusOptLog substrateStatusOptLog = new SubstrateStatusOptLog();
		try {
        	BeanUtils.copyProperties(substrateStatusOptLog, substrateStatusOptLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		substrateStatusOptLog.save();
		substrateStatusOptLogDTO.setId((java.lang.Long)substrateStatusOptLog.getId());
		return substrateStatusOptLogDTO;
	}
	
	public void updateSubstrateStatusOptLog(SubstrateStatusOptLogDTO substrateStatusOptLogDTO) {
		SubstrateStatusOptLog substrateStatusOptLog = SubstrateStatusOptLog.get(SubstrateStatusOptLog.class, substrateStatusOptLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(substrateStatusOptLog, substrateStatusOptLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeSubstrateStatusOptLog(Long id) {
		this.removeSubstrateStatusOptLogs(new Long[] { id });
	}
	
	public void removeSubstrateStatusOptLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SubstrateStatusOptLog substrateStatusOptLog = SubstrateStatusOptLog.load(SubstrateStatusOptLog.class, ids[i]);
			substrateStatusOptLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstrateStatusOptLogDTO> findAllSubstrateStatusOptLog() {
		List<SubstrateStatusOptLogDTO> list = new ArrayList<SubstrateStatusOptLogDTO>();
		List<SubstrateStatusOptLog> all = SubstrateStatusOptLog.findAll(SubstrateStatusOptLog.class);
		for (SubstrateStatusOptLog substrateStatusOptLog : all) {
			SubstrateStatusOptLogDTO substrateStatusOptLogDTO = new SubstrateStatusOptLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(substrateStatusOptLogDTO, substrateStatusOptLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(substrateStatusOptLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstrateStatusOptLogDTO> pageQuerySubstrateStatusOptLog(SubstrateStatusOptLogDTO queryVo, int currentPage, int pageSize) {
		List<SubstrateStatusOptLogDTO> result = new ArrayList<SubstrateStatusOptLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _substrateStatusOptLog from SubstrateStatusOptLog _substrateStatusOptLog   left join _substrateStatusOptLog.createUser  left join _substrateStatusOptLog.lastModifyUser  left join _substrateStatusOptLog.optLog  left join _substrateStatusOptLog.substrateProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _substrateStatusOptLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _substrateStatusOptLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	
	   	if (queryVo.getHoldCode() != null && !"".equals(queryVo.getHoldCode())) {
	   		jpql.append(" and _substrateStatusOptLog.holdCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getHoldCode()));
	   	}		
	
        Page<SubstrateStatusOptLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (SubstrateStatusOptLog substrateStatusOptLog : pages.getData()) {
            SubstrateStatusOptLogDTO substrateStatusOptLogDTO = new SubstrateStatusOptLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(substrateStatusOptLogDTO, substrateStatusOptLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(substrateStatusOptLogDTO);
        }
        return new Page<SubstrateStatusOptLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserBySubstrateStatusOptLog(Long id) {
		String jpql = "select e from SubstrateStatusOptLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySubstrateStatusOptLog(Long id) {
		String jpql = "select e from SubstrateStatusOptLog o right join o.lastModifyUser e where o.id=?";
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
	public OptLogDTO findOptLogBySubstrateStatusOptLog(Long id) {
		String jpql = "select e from SubstrateStatusOptLog o right join o.optLog e where o.id=?";
		OptLog result = (OptLog) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		OptLogDTO  dto = new OptLogDTO();
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
	public SubstrateProcessDTO findSubstrateStatusOptLogBySubstrateProcess(Long id) {
		String jpql = "select e from SubstrateStatusOptLog o right join o.substrateProcess e where o.id=?";
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

	
}
