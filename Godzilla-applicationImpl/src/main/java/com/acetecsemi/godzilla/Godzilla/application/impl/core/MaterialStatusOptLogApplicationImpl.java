
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
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialStatusOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MaterialStatusOptLogApplicationImpl implements MaterialStatusOptLogApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialStatusOptLogDTO getMaterialStatusOptLog(Long id) {
		MaterialStatusOptLog materialStatusOptLog = MaterialStatusOptLog.load(MaterialStatusOptLog.class, id);
		MaterialStatusOptLogDTO materialStatusOptLogDTO = new MaterialStatusOptLogDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(materialStatusOptLogDTO, materialStatusOptLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialStatusOptLogDTO.setId((java.lang.Long)materialStatusOptLog.getId());
		return materialStatusOptLogDTO;
	}
	
	public MaterialStatusOptLogDTO saveMaterialStatusOptLog(MaterialStatusOptLogDTO materialStatusOptLogDTO) {
		MaterialStatusOptLog materialStatusOptLog = new MaterialStatusOptLog();
		try {
        	BeanUtils.copyProperties(materialStatusOptLog, materialStatusOptLogDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		materialStatusOptLog.save();
		materialStatusOptLogDTO.setId((java.lang.Long)materialStatusOptLog.getId());
		return materialStatusOptLogDTO;
	}
	
	public void updateMaterialStatusOptLog(MaterialStatusOptLogDTO materialStatusOptLogDTO) {
		MaterialStatusOptLog materialStatusOptLog = MaterialStatusOptLog.get(MaterialStatusOptLog.class, materialStatusOptLogDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(materialStatusOptLog, materialStatusOptLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMaterialStatusOptLog(Long id) {
		this.removeMaterialStatusOptLogs(new Long[] { id });
	}
	
	public void removeMaterialStatusOptLogs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MaterialStatusOptLog materialStatusOptLog = MaterialStatusOptLog.load(MaterialStatusOptLog.class, ids[i]);
			materialStatusOptLog.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialStatusOptLogDTO> findAllMaterialStatusOptLog() {
		List<MaterialStatusOptLogDTO> list = new ArrayList<MaterialStatusOptLogDTO>();
		List<MaterialStatusOptLog> all = MaterialStatusOptLog.findAll(MaterialStatusOptLog.class);
		for (MaterialStatusOptLog materialStatusOptLog : all) {
			MaterialStatusOptLogDTO materialStatusOptLogDTO = new MaterialStatusOptLogDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialStatusOptLogDTO, materialStatusOptLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialStatusOptLogDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialStatusOptLogDTO> pageQueryMaterialStatusOptLog(MaterialStatusOptLogDTO queryVo, int currentPage, int pageSize) {
		List<MaterialStatusOptLogDTO> result = new ArrayList<MaterialStatusOptLogDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _materialStatusOptLog from MaterialStatusOptLog _materialStatusOptLog   left join _materialStatusOptLog.createUser  left join _materialStatusOptLog.lastModifyUser  left join _materialStatusOptLog.optLog  left join _materialStatusOptLog.materialProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _materialStatusOptLog.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _materialStatusOptLog.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	
	   	if (queryVo.getHoldCode() != null && !"".equals(queryVo.getHoldCode())) {
	   		jpql.append(" and _materialStatusOptLog.holdCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getHoldCode()));
	   	}		
	
        Page<MaterialStatusOptLog> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (MaterialStatusOptLog materialStatusOptLog : pages.getData()) {
            MaterialStatusOptLogDTO materialStatusOptLogDTO = new MaterialStatusOptLogDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(materialStatusOptLogDTO, materialStatusOptLog);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(materialStatusOptLogDTO);
        }
        return new Page<MaterialStatusOptLogDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMaterialStatusOptLog(Long id) {
		String jpql = "select e from MaterialStatusOptLog o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByMaterialStatusOptLog(Long id) {
		String jpql = "select e from MaterialStatusOptLog o right join o.lastModifyUser e where o.id=?";
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
	public OptLogDTO findOptLogByMaterialStatusOptLog(Long id) {
		String jpql = "select e from MaterialStatusOptLog o right join o.optLog e where o.id=?";
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
	public MaterialProcessDTO findMaterialProcessByMaterialStatusOptLog(Long id) {
		String jpql = "select e from MaterialStatusOptLog o right join o.materialProcess e where o.id=?";
		MaterialProcess result = (MaterialProcess) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		MaterialProcessDTO  dto = new MaterialProcessDTO();
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
