
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
import com.acetecsemi.godzilla.Godzilla.application.core.OptLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class OptLogApplicationImpl implements OptLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OptLogDTO getOptLog(Long id) {
		OptLog optLog = OptLog.load(OptLog.class, id);
		OptLogDTO optLogDTO = new OptLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(optLogDTO, optLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		optLogDTO.setId((java.lang.Long)optLog.getId());
		return optLogDTO;
	}
	
	public OptLogDTO saveOptLog(OptLogDTO optLogDTO) {
		OptLog optLog = new OptLog();
		try {
        	BeanUtils.copyProperties(optLog, optLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		optLog.save();
		optLogDTO.setId((java.lang.Long)optLog.getId());
		return optLogDTO;
	}
	
	public void updateOptLog(OptLogDTO optLogDTO) {
		OptLog optLog = OptLog.get(OptLog.class, optLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(optLog, optLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeOptLog(Long id) {
		this.removeOptLogs(new Long[] { id });
	}
	
	public void removeOptLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			OptLog optLog = OptLog.load(OptLog.class, ids[i]);
			optLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OptLogDTO> findAllOptLog() {
		List<OptLogDTO> list = new ArrayList<OptLogDTO>();
		List<OptLog> all = OptLog.findAll(OptLog.class);
		for (OptLog optLog : all) {
			OptLogDTO optLogDTO = new OptLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(optLogDTO, optLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(optLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<OptLogDTO> pageQueryOptLog(OptLogDTO queryVo, int currentPage, int pageSize) {
		List<OptLogDTO> result = new ArrayList<OptLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _optLog from OptLog _optLog   left join _optLog.createUser  left join _optLog.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _optLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _optLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getComments() != null && !"".equals(queryVo.getComments())) {
	   		jpql.append(" and _optLog.comments like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getComments()));
	   	}		
	
	   	if (queryVo.getOptName() != null && !"".equals(queryVo.getOptName())) {
	   		jpql.append(" and _optLog.optName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getOptName()));
	   	}		
        Page<OptLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (OptLog optLog : pages.getData()) {
            OptLogDTO optLogDTO = new OptLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(optLogDTO, optLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(optLogDTO);
        }
        return new Page<OptLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByOptLog(Long id) {
		String jpql = "select e from OptLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByOptLog(Long id) {
		String jpql = "select e from OptLog o right join o.lastModifyUser e where o.id=?";
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
