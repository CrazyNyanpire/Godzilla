
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
import com.acetecsemi.godzilla.Godzilla.application.exception.DieLessThanZeroException;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitWaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ManufactureDebitWaferProcessApplicationImpl implements ManufactureDebitWaferProcessApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureDebitWaferProcessDTO getManufactureDebitWaferProcess(Long id) {
		ManufactureDebitWaferProcess manufactureDebitWaferProcess = ManufactureDebitWaferProcess.load(ManufactureDebitWaferProcess.class, id);
		ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO = new ManufactureDebitWaferProcessDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(manufactureDebitWaferProcessDTO, manufactureDebitWaferProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureDebitWaferProcessDTO.setId((java.lang.Long)manufactureDebitWaferProcess.getId());
		return manufactureDebitWaferProcessDTO;
	}
	
	public ManufactureDebitWaferProcessDTO saveManufactureDebitWaferProcess(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO){
		ManufactureDebitWaferProcess manufactureDebitWaferProcess = new ManufactureDebitWaferProcess();
		try {
        	BeanUtils.copyProperties(manufactureDebitWaferProcess, manufactureDebitWaferProcessDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		ManufactureProcess manufactureProcess = ManufactureProcess.load(ManufactureProcess.class, manufactureDebitWaferProcessDTO.getManufactureProcessId());
		manufactureDebitWaferProcess.setManufactureProcess(manufactureProcess);
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, manufactureDebitWaferProcessDTO.getWaferProcessId());
		manufactureDebitWaferProcess.setWaferProcess(waferProcess);
		WaferInfo waferInfo = WaferInfo.get(WaferInfo.class, manufactureDebitWaferProcessDTO.getWaferInfoId());
		if(waferInfo.getDieQty() < manufactureDebitWaferProcessDTO.getQty()){
			manufactureDebitWaferProcessDTO.setActionError("manufactureProcess.debit.dieLessThenZero");
			return manufactureDebitWaferProcessDTO;
		}
		waferInfo.setDieQty(waferInfo.getDieQty() - manufactureDebitWaferProcessDTO.getQty());

		waferInfo.setLastModifyDate(manufactureDebitWaferProcessDTO.getLastModifyDate());
		waferProcess.setQtyOut(waferProcess.getQtyOut()-manufactureDebitWaferProcessDTO.getQty());
		waferInfo.save();
		waferProcess.save();
		manufactureDebitWaferProcess.setWaferInfo(waferInfo);
		manufactureDebitWaferProcess.save();
		manufactureDebitWaferProcessDTO.setId((java.lang.Long)manufactureDebitWaferProcess.getId());
		return manufactureDebitWaferProcessDTO;
	}
	
	public void updateManufactureDebitWaferProcess(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO) {
		ManufactureDebitWaferProcess manufactureDebitWaferProcess = ManufactureDebitWaferProcess.get(ManufactureDebitWaferProcess.class, manufactureDebitWaferProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(manufactureDebitWaferProcess, manufactureDebitWaferProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeManufactureDebitWaferProcess(Long id) {
		this.removeManufactureDebitWaferProcesss(new Long[] { id });
	}
	
	public void removeManufactureDebitWaferProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ManufactureDebitWaferProcess manufactureDebitWaferProcess = ManufactureDebitWaferProcess.load(ManufactureDebitWaferProcess.class, ids[i]);
			manufactureDebitWaferProcess.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureDebitWaferProcessDTO> findAllManufactureDebitWaferProcess() {
		List<ManufactureDebitWaferProcessDTO> list = new ArrayList<ManufactureDebitWaferProcessDTO>();
		List<ManufactureDebitWaferProcess> all = ManufactureDebitWaferProcess.findAll(ManufactureDebitWaferProcess.class);
		for (ManufactureDebitWaferProcess manufactureDebitWaferProcess : all) {
			ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO = new ManufactureDebitWaferProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureDebitWaferProcessDTO, manufactureDebitWaferProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(manufactureDebitWaferProcessDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureDebitWaferProcessDTO> pageQueryManufactureDebitWaferProcess(ManufactureDebitWaferProcessDTO queryVo, int currentPage, int pageSize) {
		List<ManufactureDebitWaferProcessDTO> result = new ArrayList<ManufactureDebitWaferProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _manufactureDebitWaferProcess from ManufactureDebitWaferProcess _manufactureDebitWaferProcess   left join _manufactureDebitWaferProcess.createUser  left join _manufactureDebitWaferProcess.lastModifyUser  left join _manufactureDebitWaferProcess.manufactureProcess  left join _manufactureDebitWaferProcess.waferProcess  left join _manufactureDebitWaferProcess.waferInfo  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _manufactureDebitWaferProcess.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _manufactureDebitWaferProcess.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	
	
	
        Page<ManufactureDebitWaferProcess> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (ManufactureDebitWaferProcess manufactureDebitWaferProcess : pages.getData()) {
            ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO = new ManufactureDebitWaferProcessDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(manufactureDebitWaferProcessDTO, manufactureDebitWaferProcess);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(manufactureDebitWaferProcessDTO);
        }
        return new Page<ManufactureDebitWaferProcessDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByManufactureDebitWaferProcess(Long id) {
		String jpql = "select e from ManufactureDebitWaferProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByManufactureDebitWaferProcess(Long id) {
		String jpql = "select e from ManufactureDebitWaferProcess o right join o.lastModifyUser e where o.id=?";
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
	public ManufactureProcessDTO findManufactureProcessByManufactureDebitWaferProcess(Long id) {
		String jpql = "select e from ManufactureDebitWaferProcess o right join o.manufactureProcess e where o.id=?";
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
	public WaferProcessDTO findWaferProcessByManufactureDebitWaferProcess(Long id) {
		String jpql = "select e from ManufactureDebitWaferProcess o right join o.waferProcess e where o.id=?";
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
	public WaferInfoDTO findWaferInfoByManufactureDebitWaferProcess(Long id) {
		String jpql = "select e from ManufactureDebitWaferProcess o right join o.waferInfo e where o.id=?";
		WaferInfo result = (WaferInfo) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		WaferInfoDTO  dto = new WaferInfoDTO();
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
