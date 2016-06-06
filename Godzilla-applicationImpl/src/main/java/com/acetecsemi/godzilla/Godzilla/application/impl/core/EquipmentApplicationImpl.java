
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.core.EquipmentApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class EquipmentApplicationImpl implements EquipmentApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public EquipmentDTO getEquipment(Long id) {
		Equipment equipment = Equipment.load(Equipment.class, id);
		EquipmentDTO equipmentDTO = new EquipmentDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(equipmentDTO, equipment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		equipmentDTO.setId((java.lang.Long)equipment.getId());
		return equipmentDTO;
	}
	
	public EquipmentDTO saveEquipment(EquipmentDTO equipmentDTO) {
		Equipment equipment = new Equipment();
		try {
        	BeanUtils.copyProperties(equipment, equipmentDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		equipment.save();
		equipmentDTO.setId((java.lang.Long)equipment.getId());
		return equipmentDTO;
	}
	
	public void updateEquipment(EquipmentDTO equipmentDTO) {
		Equipment equipment = Equipment.get(Equipment.class, equipmentDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(equipment, equipmentDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeEquipment(Long id) {
		this.removeEquipments(new Long[] { id });
	}
	
	public void removeEquipments(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Equipment equipment = Equipment.load(Equipment.class, ids[i]);
			equipment.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<EquipmentDTO> findAllEquipment() {
		List<EquipmentDTO> list = new ArrayList<EquipmentDTO>();
		List<Equipment> all = Equipment.findAll(Equipment.class);
		for (Equipment equipment : all) {
			EquipmentDTO equipmentDTO = new EquipmentDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(equipmentDTO, equipment);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(equipmentDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<EquipmentDTO> pageQueryEquipment(EquipmentDTO queryVo, int currentPage, int pageSize) {
		List<EquipmentDTO> result = new ArrayList<EquipmentDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _equipment from Equipment _equipment  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _equipment.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _equipment.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getEquipment() != null && !"".equals(queryVo.getEquipment())) {
	   		jpql.append(" and _equipment.equipment like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getEquipment()));
	   	}		
	
	   	if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
	   		jpql.append(" and _equipment.status like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
	   	}		
	
	   	if (queryVo.getDescription() != null && !"".equals(queryVo.getDescription())) {
	   		jpql.append(" and _equipment.discription like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDescription()));
	   	}		
	
	   	if (queryVo.getArea() != null && !"".equals(queryVo.getArea())) {
	   		jpql.append(" and _equipment.area like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getArea()));
	   	}		
	
	
        Page<Equipment> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Equipment equipment : pages.getData()) {
            EquipmentDTO equipmentDTO = new EquipmentDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(equipmentDTO, equipment);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                                                            result.add(equipmentDTO);
        }
        return new Page<EquipmentDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByEquipment(Long id) {
		String jpql = "select e from Equipment o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByEquipment(Long id) {
		String jpql = "select e from Equipment o right join o.lastModifyUser e where o.id=?";
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
	public WaferProcessDTO findWaferProcessByEquipment(Long id) {
		String jpql = "select e from Equipment o right join o.waferProcess e where o.id=?";
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
	public ManufactureProcessDTO findManufactureProcessByEquipment(Long id) {
		String jpql = "select e from Equipment o right join o.manufactureProcess e where o.id=?";
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
	public StationDTO findStationByEquipment(Long id) {
		String jpql = "select e from Equipment o right join o.station e where o.id=?";
		Station result = (Station) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		StationDTO  dto = new StationDTO();
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
