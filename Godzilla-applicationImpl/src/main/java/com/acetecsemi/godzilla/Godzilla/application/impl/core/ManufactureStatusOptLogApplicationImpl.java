
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
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ManufactureStatusOptLogApplicationImpl implements ManufactureStatusOptLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureStatusOptLogDTO getManufactureStatusOptLog(Long id) {
		ManufactureStatusOptLog manufactureStatusOptLog = ManufactureStatusOptLog.load(ManufactureStatusOptLog.class, id);
		ManufactureStatusOptLogDTO manufactureStatusOptLogDTO = new ManufactureStatusOptLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(manufactureStatusOptLogDTO, manufactureStatusOptLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureStatusOptLogDTO.setId((java.lang.Long)manufactureStatusOptLog.getId());
		return manufactureStatusOptLogDTO;
	}
	
	public ManufactureStatusOptLogDTO saveManufactureStatusOptLog(ManufactureStatusOptLogDTO manufactureStatusOptLogDTO) {
		ManufactureStatusOptLog manufactureStatusOptLog = new ManufactureStatusOptLog();
		try {
        	BeanUtils.copyProperties(manufactureStatusOptLog, manufactureStatusOptLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		manufactureStatusOptLog.save();
		manufactureStatusOptLogDTO.setId((java.lang.Long)manufactureStatusOptLog.getId());
		return manufactureStatusOptLogDTO;
	}
	
	public void updateManufactureStatusOptLog(ManufactureStatusOptLogDTO manufactureStatusOptLogDTO) {
		ManufactureStatusOptLog manufactureStatusOptLog = ManufactureStatusOptLog.get(ManufactureStatusOptLog.class, manufactureStatusOptLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(manufactureStatusOptLog, manufactureStatusOptLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeManufactureStatusOptLog(Long id) {
		this.removeManufactureStatusOptLogs(new Long[] { id });
	}
	
	public void removeManufactureStatusOptLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ManufactureStatusOptLog manufactureStatusOptLog = ManufactureStatusOptLog.load(ManufactureStatusOptLog.class, ids[i]);
			manufactureStatusOptLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureStatusOptLogDTO> findAllManufactureStatusOptLog() {
		List<ManufactureStatusOptLogDTO> list = new ArrayList<ManufactureStatusOptLogDTO>();
		List<ManufactureStatusOptLog> all = ManufactureStatusOptLog.findAll(ManufactureStatusOptLog.class);
		for (ManufactureStatusOptLog manufactureStatusOptLog : all) {
			ManufactureStatusOptLogDTO manufactureStatusOptLogDTO = new ManufactureStatusOptLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureStatusOptLogDTO, manufactureStatusOptLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(manufactureStatusOptLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureStatusOptLogDTO> pageQueryManufactureStatusOptLog(ManufactureStatusOptLogDTO queryVo, int currentPage, int pageSize) {
		List<ManufactureStatusOptLogDTO> result = new ArrayList<ManufactureStatusOptLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _manufactureStatusOptLog from ManufactureStatusOptLog _manufactureStatusOptLog   left join _manufactureStatusOptLog.createUser  left join _manufactureStatusOptLog.lastModifyUser  left join _manufactureStatusOptLog.optLog  left join _manufactureStatusOptLog.manufactureProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _manufactureStatusOptLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _manufactureStatusOptLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	
	   	if (queryVo.getHoldCode() != null && !"".equals(queryVo.getHoldCode())) {
	   		jpql.append(" and _manufactureStatusOptLog.holdCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getHoldCode()));
	   	}		
	
        Page<ManufactureStatusOptLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (ManufactureStatusOptLog manufactureStatusOptLog : pages.getData()) {
            ManufactureStatusOptLogDTO manufactureStatusOptLogDTO = new ManufactureStatusOptLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(manufactureStatusOptLogDTO, manufactureStatusOptLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(manufactureStatusOptLogDTO);
        }
        return new Page<ManufactureStatusOptLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByManufactureStatusOptLog(Long id) {
		String jpql = "select e from ManufactureStatusOptLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByManufactureStatusOptLog(Long id) {
		String jpql = "select e from ManufactureStatusOptLog o right join o.lastModifyUser e where o.id=?";
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
	public OptLogDTO findOptLogByManufactureStatusOptLog(Long id) {
		String jpql = "select e from ManufactureStatusOptLog o right join o.optLog e where o.id=?";
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
	public ManufactureProcessDTO findManufactureProcessByManufactureStatusOptLog(Long id) {
		String jpql = "select e from ManufactureStatusOptLog o right join o.manufactureProcess e where o.id=?";
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

	
}
