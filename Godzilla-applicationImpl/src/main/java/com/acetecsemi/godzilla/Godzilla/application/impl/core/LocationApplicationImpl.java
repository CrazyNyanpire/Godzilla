package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.core.LocationApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;
import com.acetecsemi.godzilla.Godzilla.core.Process;

@Named
@Transactional
public class LocationApplicationImpl implements LocationApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LocationDTO getLocation(Long id) {
		Location location = Location.load(Location.class, id);
		LocationDTO locationDTO = new LocationDTO();
		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(locationDTO, location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		locationDTO.setId((java.lang.Long) location.getId());
		return locationDTO;
	}

	public LocationDTO saveLocation(LocationDTO locationDTO) {
		Location location = new Location();
		locationDTO.setCreateDate(new Date());
		locationDTO.setLastModifyDate(new Date());
		try {
			BeanUtilsExtends.copyProperties(location, locationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		location.save();
		locationDTO.setId((java.lang.Long) location.getId());
		return locationDTO;
	}

	public void updateLocation(LocationDTO locationDTO) {
		Location location = Location.get(Location.class, locationDTO.getId());
		locationDTO.setLastModifyDate(new Date());
		// 设置要更新的值
		try {
			BeanUtilsExtends.copyProperties(location, locationDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLocationsWaferProcess(String locationIds, Long processId) {
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			if(locationIdArray[i].length() < 1)
				break;
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			WaferProcess waferProcess = WaferProcess.load(WaferProcess.class,
					processId);
			location.setWaferProcess(waferProcess);
			location.save();
		}
	}

	public void updateLocationsSubstrateProcess(String locationIds,
			Long processId) {
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			if(locationIdArray[i].length() < 1)
				break;
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			SubstrateProcess substrateProcess = SubstrateProcess.load(
					SubstrateProcess.class, processId);
			location.setSubstrateProcess(substrateProcess);
			location.save();
		}
	}

	public void updateLocationsMaterialProcess(String locationIds,
			Long processId) {
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			if(locationIdArray[i].length() < 1)
				break;
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			MaterialProcess materialProcess = MaterialProcess.load(
					MaterialProcess.class, processId);
			location.setMaterialProcess(materialProcess);
			location.save();
		}
	}

	public void updateLocationsManufactureProcess(String locationIds,
			Long processId) {
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			if(locationIdArray[i].length() < 1)
				break;
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			ManufactureProcess manufactureProcess = ManufactureProcess.load(
					ManufactureProcess.class, processId);
			location.setManufactureProcess(manufactureProcess);
			location.save();
		}
	}

	public void removeLocation(Long id) {
		this.removeLocations(new Long[] { id });
	}

	public void removeLocations(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Location location = Location.load(Location.class, ids[i]);
			location.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LocationDTO> findAllLocation() {
		List<LocationDTO> list = new ArrayList<LocationDTO>();
		List<Location> all = Location.findAll(Location.class);
		for (Location location : all) {
			LocationDTO locationDTO = new LocationDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(locationDTO, location);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (location.getWaferProcess() == null
					&& location.getManufactureProcess() == null
					&& location.getSubstrateProcess() == null
					&& location.getMaterialProcess() == null
					&& location.getSpartPartProcess() == null)
				list.add(locationDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LocationDTO> pageQueryLocation(LocationDTO queryVo,
			int currentPage, int pageSize) {
		List<LocationDTO> result = new ArrayList<LocationDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _location from Location _location  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _location.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _location.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getLoctionCode() != null
				&& !"".equals(queryVo.getLoctionCode())) {
			jpql.append(" and _location.loctionCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLoctionCode()));
		}
		
		if (queryVo.getDescription() != null
				&& !"".equals(queryVo.getDescription())) {
			jpql.append(" and _location.loctionCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDescription()));
		}

		Page<Location> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (Location location : pages.getData()) {
			LocationDTO locationDTO = new LocationDTO();

			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(locationDTO, location);
				if(location.getWaferProcess() != null){
					locationDTO.setLotNo(location.getWaferProcess().getWaferCompanyLot().getLotNo());
				}
				if(location.getSubstrateProcess() != null){
					locationDTO.setLotNo(location.getSubstrateProcess().getSubstrateCompanyLot().getLotNo());
				}
				if(location.getMaterialProcess() != null){
					locationDTO.setLotNo(location.getMaterialProcess().getMaterialCompanyLot().getLotNo());
				}
				if(location.getManufactureProcess() != null){
					locationDTO.setLotNo(location.getManufactureProcess().getManufactureLot().getLotNo());
				}
				if(location.getSpartPartProcess() != null){
					locationDTO.setLotNo(location.getSpartPartProcess().getSpartPart().getPartId());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(locationDTO);
		}
		return new Page<LocationDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByLocation(Long id) {
		String jpql = "select e from Location o right join o.createUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		UserDTO dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findLastModifyUserByLocation(Long id) {
		String jpql = "select e from Location o right join o.lastModifyUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		UserDTO dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferProcessDTO findNowProcessByLocation(Long id) {
		String jpql = "select e from Location o  e where o.id=?";
		Process result = (Process) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		WaferProcessDTO dto = new WaferProcessDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	public void clearLocationsProcess(String locationIds) {
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			if(locationIdArray[i].length() < 1)
				break;
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			location.setMaterialProcess(null);
			location.setWaferProcess(null);
			location.setSubstrateProcess(null);
			location.setMaterialProcess(null);
			location.setSpartPartProcess(null);
			location.save();
		}
	}

}
