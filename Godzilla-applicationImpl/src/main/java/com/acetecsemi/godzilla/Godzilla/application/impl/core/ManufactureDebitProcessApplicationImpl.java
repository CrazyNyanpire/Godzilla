
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
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ManufactureDebitProcessApplicationImpl implements ManufactureDebitProcessApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureDebitProcessDTO getManufactureDebitProcess(Long id) {
		ManufactureDebitProcess manufactureDebitProcess = ManufactureDebitProcess.load(ManufactureDebitProcess.class, id);
		ManufactureDebitProcessDTO manufactureDebitProcessDTO = new ManufactureDebitProcessDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(manufactureDebitProcessDTO, manufactureDebitProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureDebitProcessDTO.setId((java.lang.Long)manufactureDebitProcess.getId());
		return manufactureDebitProcessDTO;
	}
	
	public ManufactureDebitProcessDTO saveManufactureDebitProcess(ManufactureDebitProcessDTO manufactureDebitProcessDTO) {
		ManufactureDebitProcess manufactureDebitProcess = new ManufactureDebitProcess();
		try {
        	BeanUtilsExtends.copyProperties(manufactureDebitProcess, manufactureDebitProcessDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		ManufactureProcess manufactureProcess = ManufactureProcess.load(ManufactureProcess.class, manufactureDebitProcessDTO.getManufactureProcessId());
		manufactureDebitProcess.setManufactureProcess(manufactureProcess);
		MaterialProcess materialProcess =  MaterialProcess.get(MaterialProcess.class, manufactureDebitProcessDTO.getMaterialProcessId());
		if(materialProcess.getBalance() - manufactureDebitProcessDTO.getBalance() > 0 ){
			materialProcess.setBalance(manufactureDebitProcessDTO.getBalance());
		}else if(materialProcess.getBalance() - manufactureDebitProcessDTO.getBalance() == 0 ){
			materialProcess.setBalance(manufactureDebitProcessDTO.getBalance());
			Resource finish = Resource.findByProperty(Resource.class, "identifier",
					"Finish").get(0);
			materialProcess.setStatus(finish.getName());
		}else
			return null;
		manufactureDebitProcess.setMaterialProcess(materialProcess);
		manufactureDebitProcess.save();
		materialProcess.setBalance(manufactureDebitProcessDTO.getBalance());
		materialProcess.save();
		manufactureDebitProcessDTO.setId((java.lang.Long)manufactureDebitProcess.getId());
		return manufactureDebitProcessDTO;
	}
	
	public void updateManufactureDebitProcess(ManufactureDebitProcessDTO manufactureDebitProcessDTO) {
		ManufactureDebitProcess manufactureDebitProcess = ManufactureDebitProcess.get(ManufactureDebitProcess.class, manufactureDebitProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(manufactureDebitProcess, manufactureDebitProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeManufactureDebitProcess(Long id) {
		this.removeManufactureDebitProcesss(new Long[] { id });
	}
	
	public void removeManufactureDebitProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ManufactureDebitProcess manufactureDebitProcess = ManufactureDebitProcess.load(ManufactureDebitProcess.class, ids[i]);
			manufactureDebitProcess.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureDebitProcessDTO> findAllManufactureDebitProcess() {
		List<ManufactureDebitProcessDTO> list = new ArrayList<ManufactureDebitProcessDTO>();
		List<ManufactureDebitProcess> all = ManufactureDebitProcess.findAll(ManufactureDebitProcess.class);
		for (ManufactureDebitProcess manufactureDebitProcess : all) {
			ManufactureDebitProcessDTO manufactureDebitProcessDTO = new ManufactureDebitProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureDebitProcessDTO, manufactureDebitProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(manufactureDebitProcessDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureDebitProcessDTO> pageQueryManufactureDebitProcess(ManufactureDebitProcessDTO queryVo, int currentPage, int pageSize) {
		List<ManufactureDebitProcessDTO> result = new ArrayList<ManufactureDebitProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _manufactureDebitProcess from ManufactureDebitProcess _manufactureDebitProcess   left join _manufactureDebitProcess.createUser  left join _manufactureDebitProcess.lastModifyUser  left join _manufactureDebitProcess.manufactureProcess  left join _manufactureDebitProcess.materialProcess  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _manufactureDebitProcess.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _manufactureDebitProcess.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	
        Page<ManufactureDebitProcess> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (ManufactureDebitProcess manufactureDebitProcess : pages.getData()) {
            ManufactureDebitProcessDTO manufactureDebitProcessDTO = new ManufactureDebitProcessDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(manufactureDebitProcessDTO, manufactureDebitProcess);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(manufactureDebitProcessDTO);
        }
        return new Page<ManufactureDebitProcessDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByManufactureDebitProcess(Long id) {
		String jpql = "select e from ManufactureDebitProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByManufactureDebitProcess(Long id) {
		String jpql = "select e from ManufactureDebitProcess o right join o.lastModifyUser e where o.id=?";
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
	public ManufactureProcessDTO findManufactureProcessByManufactureDebitProcess(Long id) {
		String jpql = "select e from ManufactureDebitProcess o right join o.manufactureProcess e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialProcessDTO findMaterialProcessByManufactureDebitProcess(Long id) {
		String jpql = "select e from ManufactureDebitProcess o right join o.materialProcess e where o.id=?";
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
