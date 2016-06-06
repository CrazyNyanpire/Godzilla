package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Iterator;
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
import com.acetecsemi.godzilla.Godzilla.application.core.StationApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class StationApplicationImpl implements StationApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StationDTO getStation(Long id) {
		Station station = Station.load(Station.class, id);
		StationDTO stationDTO = new StationDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(stationDTO, station);
		} catch (Exception e) {
			e.printStackTrace();
		}
		stationDTO.setId((java.lang.Long) station.getId());
		return stationDTO;
	}

	public StationDTO saveStation(StationDTO stationDTO) {
		Station station = new Station();
		try {
			BeanUtils.copyProperties(station, stationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		station.save();
		stationDTO.setId((java.lang.Long) station.getId());
		return stationDTO;
	}

	public void updateStation(StationDTO stationDTO) {
		Station station = Station.get(Station.class, stationDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(station, stationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeStation(Long id) {
		this.removeStations(new Long[] { id });
	}

	public void removeStations(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Station station = Station.load(Station.class, ids[i]);
			station.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StationDTO> findAllStation() {
		List<StationDTO> list = new ArrayList<StationDTO>();
		List<Station> all = Station.findAll(Station.class);
		for (Station station : all) {
			StationDTO stationDTO = new StationDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(stationDTO, station);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(stationDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StationDTO> findConsumeStation() {
		List<StationDTO> list = new ArrayList<StationDTO>();
		List<Station> all = Station.findByProperty(Station.class, "consume", 1);
		for (Station station : all) {
			StationDTO stationDTO = new StationDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(stationDTO, station);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(stationDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<StationDTO> pageQueryStation(StationDTO queryVo,
			int currentPage, int pageSize) {
		List<StationDTO> result = new ArrayList<StationDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _station from Station _station   left join _station.createUser  left join _station.lastModifyUser  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _station.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _station.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getStationName() != null
				&& !"".equals(queryVo.getStationName())) {
			jpql.append(" and _station.stationName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getStationName()));
		}

		if (queryVo.getStationNameEn() != null
				&& !"".equals(queryVo.getStationNameEn())) {
			jpql.append(" and _station.stationNameEn like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getStationNameEn()));
		}

		if (queryVo.getStationType() != null) {
			jpql.append(" and _station.stationType=?");
			conditionVals.add(queryVo.getStationType());
		}

		Page<Station> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (Station station : pages.getData()) {
			StationDTO stationDTO = new StationDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(stationDTO, station);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(stationDTO);
		}
		return new Page<StationDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByStation(Long id) {
		String jpql = "select e from Station o right join o.createUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		UserDTO dto = new UserDTO();
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
	public UserDTO findLastModifyUserByStation(Long id) {
		String jpql = "select e from Station o right join o.lastModifyUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		UserDTO dto = new UserDTO();
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
	public List<EquipmentDTO> findEquipmentsByStation(Long id) {
		Station station = Station.load(Station.class, id);
		Iterator<Equipment> iterator = station.getEquipments().iterator();
		List<EquipmentDTO> equipmentList = new ArrayList<EquipmentDTO>();
		while (iterator.hasNext()) {
			EquipmentDTO equipmentDTO = new EquipmentDTO();
			equipmentDTO.setStationName(station.getStationName());
			Equipment equipment = iterator.next();
			try {
				BeanUtils.copyProperties(equipmentDTO, equipment);
				if (equipment.getWaferProcess() != null && "RUN".equals(equipment.getStatus())) {
					equipmentDTO.setRunlots(equipment.getWaferProcess()
							.getWaferCompanyLot().getLotNo());
				} 
				else if(equipment.getManufactureProcess() != null && "RUN".equals(equipment.getStatus()))
				{
					equipmentDTO.setRunlots(equipment.getManufactureProcess().getManufactureLot().getLotNo());
				}	
				else {
					equipmentDTO.setRunlots("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			equipmentList.add(equipmentDTO);
		}
		return equipmentList;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StationAlertDTO> findAllStationAlert() {
		List<StationAlertDTO> list = new ArrayList<StationAlertDTO>();
		List<StationAlert> all = StationAlert.findAll(StationAlert.class);
		for (StationAlert stationAlert : all) {
			StationAlertDTO stationAlertDTO = new StationAlertDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(stationAlertDTO, stationAlert);
				stationAlertDTO.setStationOutId(stationAlert.getStationOutId().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(stationAlertDTO);
		}
		return list;
	}
}
