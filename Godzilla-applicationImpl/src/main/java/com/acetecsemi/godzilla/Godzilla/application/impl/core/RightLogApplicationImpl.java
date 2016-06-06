
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.core.RightLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class RightLogApplicationImpl implements RightLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RightLogDTO getRightLog(Long id) {
		RightLog rightLog = RightLog.load(RightLog.class, id);
		RightLogDTO rightLogDTO = new RightLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(rightLogDTO, rightLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		rightLogDTO.setId((java.lang.Long)rightLog.getId());
		return rightLogDTO;
	}
	
	public RightLogDTO saveRightLog(RightLogDTO rightLogDTO) {
		RightLog rightLog = new RightLog();
		try {
        	BeanUtils.copyProperties(rightLog, rightLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		rightLog.save();
		rightLogDTO.setId((java.lang.Long)rightLog.getId());
		return rightLogDTO;
	}
	
	public void updateRightLog(RightLogDTO rightLogDTO) {
		RightLog rightLog = RightLog.get(RightLog.class, rightLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(rightLog, rightLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeRightLog(Long id) {
		this.removeRightLogs(new Long[] { id });
	}
	
	public void removeRightLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			RightLog rightLog = RightLog.load(RightLog.class, ids[i]);
			rightLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RightLogDTO> findAllRightLog() {
		List<RightLogDTO> list = new ArrayList<RightLogDTO>();
		List<RightLog> all = RightLog.findAll(RightLog.class);
		for (RightLog rightLog : all) {
			RightLogDTO rightLogDTO = new RightLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(rightLogDTO, rightLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(rightLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RightLogDTO> pageQueryRightLog(RightLogDTO queryVo, int currentPage, int pageSize) {
		List<RightLogDTO> result = new ArrayList<RightLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _rightLog from RightLog _rightLog   left join _rightLog.createUser  left join _rightLog.lastModifyUser  left join _rightLog.resource  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _rightLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _rightLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
        Page<RightLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (RightLog rightLog : pages.getData()) {
            RightLogDTO rightLogDTO = new RightLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(rightLogDTO, rightLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                result.add(rightLogDTO);
        }
        return new Page<RightLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByRightLog(Long id) {
		String jpql = "select e from RightLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByRightLog(Long id) {
		String jpql = "select e from RightLog o right join o.lastModifyUser e where o.id=?";
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
	public ResourceDTO findResourceByRightLog(Long id) {
		String jpql = "select e from RightLog o right join o.resource e where o.id=?";
		Resource result = (Resource) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		ResourceDTO  dto = new ResourceDTO();
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
