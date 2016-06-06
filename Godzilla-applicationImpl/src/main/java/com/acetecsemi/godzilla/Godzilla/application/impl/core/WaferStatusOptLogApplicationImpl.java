
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class WaferStatusOptLogApplicationImpl implements WaferStatusOptLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferStatusOptLogDTO getWaferStatusOptLog(Long id) {
		WaferStatusOptLog waferStatusOptLog = WaferStatusOptLog.load(WaferStatusOptLog.class, id);
		WaferStatusOptLogDTO waferStatusOptLogDTO = new WaferStatusOptLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(waferStatusOptLogDTO, waferStatusOptLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferStatusOptLogDTO.setId((java.lang.Long)waferStatusOptLog.getId());
		return waferStatusOptLogDTO;
	}
	
	public WaferStatusOptLogDTO saveWaferStatusOptLog(WaferStatusOptLogDTO waferStatusOptLogDTO) {
		WaferStatusOptLog waferStatusOptLog = new WaferStatusOptLog();
		try {
        	BeanUtils.copyProperties(waferStatusOptLog, waferStatusOptLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		waferStatusOptLog.save();
		waferStatusOptLogDTO.setId((java.lang.Long)waferStatusOptLog.getId());
		return waferStatusOptLogDTO;
	}
	
	public void updateWaferStatusOptLog(WaferStatusOptLogDTO waferStatusOptLogDTO) {
		WaferStatusOptLog waferStatusOptLog = WaferStatusOptLog.get(WaferStatusOptLog.class, waferStatusOptLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(waferStatusOptLog, waferStatusOptLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeWaferStatusOptLog(Long id) {
		this.removeWaferStatusOptLogs(new Long[] { id });
	}
	
	public void removeWaferStatusOptLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferStatusOptLog waferStatusOptLog = WaferStatusOptLog.load(WaferStatusOptLog.class, ids[i]);
			waferStatusOptLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferStatusOptLogDTO> findAllWaferStatusOptLog() {
		List<WaferStatusOptLogDTO> list = new ArrayList<WaferStatusOptLogDTO>();
		List<WaferStatusOptLog> all = WaferStatusOptLog.findAll(WaferStatusOptLog.class);
		for (WaferStatusOptLog waferStatusOptLog : all) {
			WaferStatusOptLogDTO waferStatusOptLogDTO = new WaferStatusOptLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(waferStatusOptLogDTO, waferStatusOptLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferStatusOptLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferStatusOptLogDTO> pageQueryWaferStatusOptLog(WaferStatusOptLogDTO queryVo, int currentPage, int pageSize) {
		List<WaferStatusOptLogDTO> result = new ArrayList<WaferStatusOptLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _waferStatusOptLog from WaferStatusOptLog _waferStatusOptLog   left join _waferStatusOptLog.createUser  left join _waferStatusOptLog.lastModifyUser  left join _waferStatusOptLog.optLog  left join _waferStatusOptLog.waferProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _waferStatusOptLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _waferStatusOptLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	
	   	if (queryVo.getHoldCode() != null && !"".equals(queryVo.getHoldCode())) {
	   		jpql.append(" and _waferStatusOptLog.holdCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getHoldCode()));
	   	}		
	
        Page<WaferStatusOptLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (WaferStatusOptLog waferStatusOptLog : pages.getData()) {
            WaferStatusOptLogDTO waferStatusOptLogDTO = new WaferStatusOptLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(waferStatusOptLogDTO, waferStatusOptLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(waferStatusOptLogDTO);
        }
        return new Page<WaferStatusOptLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByWaferStatusOptLog(Long id) {
		String jpql = "select e from WaferStatusOptLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByWaferStatusOptLog(Long id) {
		String jpql = "select e from WaferStatusOptLog o right join o.lastModifyUser e where o.id=?";
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
	public OptLogDTO findOptLogByWaferStatusOptLog(Long id) {
		String jpql = "select e from WaferStatusOptLog o right join o.optLog e where o.id=?";
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
	public WaferStatusOptLogDTO findWaferStatusOptLogByWaferProcess(Long id) {
		String jpql = "select e from WaferStatusOptLog o right join o.waferProcess e where o.id=?";
		WaferStatusOptLog result = (WaferStatusOptLog) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferStatusOptLogDTO  dto = new WaferStatusOptLogDTO();
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
