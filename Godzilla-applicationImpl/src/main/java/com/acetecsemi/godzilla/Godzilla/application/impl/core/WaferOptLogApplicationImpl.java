
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class WaferOptLogApplicationImpl implements WaferOptLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferOptLogDTO getWaferOptLog(Long id) {
		WaferOptLog waferOptLog = WaferOptLog.load(WaferOptLog.class, id);
		WaferOptLogDTO waferOptLogDTO = new WaferOptLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(waferOptLogDTO, waferOptLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferOptLogDTO.setId((java.lang.Long)waferOptLog.getId());
		return waferOptLogDTO;
	}
	
	public WaferOptLogDTO saveWaferOptLog(WaferOptLogDTO waferOptLogDTO) {
		WaferOptLog waferOptLog = new WaferOptLog();
		try {
        	BeanUtils.copyProperties(waferOptLog, waferOptLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		waferOptLog.save();
		waferOptLogDTO.setId((java.lang.Long)waferOptLog.getId());
		return waferOptLogDTO;
	}
	
	public void updateWaferOptLog(WaferOptLogDTO waferOptLogDTO) {
		WaferOptLog waferOptLog = WaferOptLog.get(WaferOptLog.class, waferOptLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(waferOptLog, waferOptLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeWaferOptLog(Long id) {
		this.removeWaferOptLogs(new Long[] { id });
	}
	
	public void removeWaferOptLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferOptLog waferOptLog = WaferOptLog.load(WaferOptLog.class, ids[i]);
			waferOptLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferOptLogDTO> findAllWaferOptLog() {
		List<WaferOptLogDTO> list = new ArrayList<WaferOptLogDTO>();
		List<WaferOptLog> all = WaferOptLog.findAll(WaferOptLog.class);
		for (WaferOptLog waferOptLog : all) {
			WaferOptLogDTO waferOptLogDTO = new WaferOptLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(waferOptLogDTO, waferOptLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferOptLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferOptLogDTO> pageQueryWaferOptLog(WaferOptLogDTO queryVo, int currentPage, int pageSize) {
		List<WaferOptLogDTO> result = new ArrayList<WaferOptLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _waferOptLog from WaferOptLog _waferOptLog   left join _waferOptLog.createUser  left join _waferOptLog.lastModifyUser  left join _waferOptLog.optLog  left join _waferOptLog.waferProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _waferOptLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _waferOptLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	   	if (queryVo.getWaferQty() != null) {
	   		jpql.append(" and _waferOptLog.waferQty=?");
	   		conditionVals.add(queryVo.getWaferQty());
	   	}	
	
	   	if (queryVo.getDieQty() != null) {
	   		jpql.append(" and _waferOptLog.dieQty=?");
	   		conditionVals.add(queryVo.getDieQty());
	   	}	
	
	
	   	if (queryVo.getType() != null && !"".equals(queryVo.getType())) {
	   		jpql.append(" and _waferOptLog.type like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getType()));
	   	}		
	
	
        Page<WaferOptLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (WaferOptLog waferOptLog : pages.getData()) {
            WaferOptLogDTO waferOptLogDTO = new WaferOptLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(waferOptLogDTO, waferOptLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                                             result.add(waferOptLogDTO);
        }
        return new Page<WaferOptLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByWaferOptLog(Long id) {
		String jpql = "select e from WaferOptLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByWaferOptLog(Long id) {
		String jpql = "select e from WaferOptLog o right join o.lastModifyUser e where o.id=?";
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
	public OptLogDTO findOptLogByWaferOptLog(Long id) {
		String jpql = "select e from WaferOptLog o right join o.optLog e where o.id=?";
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
	public WaferProcessDTO findWaferProcessByWaferOptLog(Long id) {
		String jpql = "select e from WaferOptLog o right join o.waferProcess e where o.id=?";
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
