package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.core.domain.ResourceLineAssignment;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;
import com.acetecsemi.godzilla.Godzilla.application.core.LocationApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.SpartPartApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.SpartPartProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SpartPartProcessApplicationImpl implements
		SpartPartProcessApplication {

	private QueryChannelService queryChannel;

	@Inject
	private LocationApplication locationApplication;

	@Inject
	private SpartPartApplication spartPartApplication;
	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SpartPartProcessDTO getSpartPartProcess(Long id) {
		SpartPartProcess spartPartProcess = SpartPartProcess.load(
				SpartPartProcess.class, id);
		SpartPartProcessDTO spartPartProcessDTO = new SpartPartProcessDTO();
		SpartPartDTO spartPartDTO = new SpartPartDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(spartPartProcessDTO, spartPartProcess);
			BeanUtils.copyProperties(spartPartDTO, spartPartProcess.getSpartPart());
		} catch (Exception e) {
			e.printStackTrace();
		}
		spartPartProcessDTO.setPartId(spartPartProcess.getSpartPartCompanyLot().getLotNo());
		spartPartProcessDTO.setPartName(spartPartDTO.getPartName());
		spartPartProcessDTO.setPartNumber(spartPartDTO.getPartNumber());
		spartPartProcessDTO.setDescription(spartPartDTO.getDescription());
		spartPartProcessDTO.setStation(spartPartDTO.getStation());
		spartPartProcessDTO.setEntryDate(spartPartProcess.getCreateDate());
		spartPartProcessDTO.setEntryTime(MyDateUtils.getDayHour(spartPartProcessDTO.getEntryDate(), new Date()));
		spartPartProcessDTO.setQty(spartPartDTO.getQty());
//		spartPartProcessDTO.setLotType(Resource.get(Resource.class, spartPartProcess.getSpartPart().getResLotType().getName()).getName()) ;
		spartPartProcessDTO.setLotType(spartPartProcess.getSpartPart().getResLotType().getIdentifier());
		spartPartProcessDTO.setPon(spartPartDTO.getPon());
		spartPartProcessDTO.setUserName(spartPartProcess.getCreateUser().getUserAccount());
		spartPartProcessDTO.setStatus(spartPartDTO.getCurrStatus());
		for(Location location:spartPartProcess.getLocations())
		{
			spartPartProcessDTO.setStockPos(location.getLoctionCode());
		}
		spartPartProcessDTO.setId((java.lang.Long) spartPartProcess.getId());
		return spartPartProcessDTO;
	}

	public SpartPartProcessDTO saveSpartPartProcess(
			SpartPartProcessDTO spartPartProcessDTO) {
		SpartPartProcess spartPartProcess = new SpartPartProcess();
		try {
			BeanUtils.copyProperties(spartPartProcess, spartPartProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		spartPartProcess.save();
		spartPartProcessDTO.setId((java.lang.Long) spartPartProcess.getId());
		return spartPartProcessDTO;
	}

	public void updateSpartPartProcess(SpartPartProcessDTO spartPartProcessDTO) {
		SpartPartProcess spartPartProcess = SpartPartProcess.get(
				SpartPartProcess.class, spartPartProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(spartPartProcess, spartPartProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeSpartPartProcess(Long id) {
		this.removeSpartPartProcesss(new Long[] { id });
	}

	public void removeSpartPartProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SpartPartProcess spartPartProcess = SpartPartProcess.load(
					SpartPartProcess.class, ids[i]);
			spartPartProcess.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SpartPartProcessDTO> findAllSpartPartProcess() {
		List<SpartPartProcessDTO> list = new ArrayList<SpartPartProcessDTO>();
		List<SpartPartProcess> all = SpartPartProcess
				.findAll(SpartPartProcess.class);
		for (SpartPartProcess spartPartProcess : all) {
			SpartPartProcessDTO spartPartProcessDTO = new SpartPartProcessDTO();
			SpartPartDTO spartPartDTO = new SpartPartDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(spartPartProcessDTO, spartPartProcess);
				BeanUtils.copyProperties(spartPartDTO,
						spartPartProcess.getSpartPart());
				if (spartPartProcess.getSpartPart().getVender() != null)
					spartPartDTO.setVender(spartPartProcess.getSpartPart()
							.getVender().getVenderCode());
				if (spartPartProcess.getSpartPart().getProducts() != null
						&& spartPartProcess.getSpartPart().getProducts().size() > 0) {
					for (Product product : spartPartProcess.getSpartPart()
							.getProducts()) {
						spartPartDTO.setProduct(product.getProductName());
					}
				}
//				if (spartPartProcess.getSpartPart().getStations() != null
//						&& spartPartProcess.getSpartPart().getStations().size() > 0) {
//					for (Station station : spartPartProcess.getSpartPart()
//							.getStations()) {
//						spartPartDTO.setProduct(station.getStationName());
//					}
//				}
				spartPartProcessDTO.setSpartPartDTO(spartPartDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(spartPartProcessDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SpartPartProcessDTO> pageQuerySpartPartProcess(
			SpartPartProcessDTO queryVo, int currentPage, int pageSize) {
		List<SpartPartProcessDTO> result = new ArrayList<SpartPartProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _spartPartProcess from SpartPartProcess _spartPartProcess   left join _spartPartProcess.createUser  left join _spartPartProcess.lastModifyUser  left join _spartPartProcess.spartPart  left join _spartPartProcess.station  left join _spartPartProcess.resLotType  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _spartPartProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _spartPartProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _spartPartProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _spartPartProcess.status like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
		}
		else
		{
			jpql.append(" and _spartPartProcess.status not in (" + StaticValue.NOT_SHOW_STATUS +") ");
		}
		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _spartPartProcess.lotType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}

		if (queryVo.getStationId() != null) {
			jpql.append(" and _spartPartProcess.station.id = ? ");
			conditionVals.add(queryVo.getStationId());
		}

		Page<SpartPartProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (SpartPartProcess spartPartProcess : pages.getData()) {
			SpartPartProcessDTO spartPartProcessDTO = new SpartPartProcessDTO();
			SpartPartDTO spartPartDTO = new SpartPartDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(spartPartProcessDTO, spartPartProcess);
				BeanUtils.copyProperties(spartPartDTO,
						spartPartProcess.getSpartPart());
				if (spartPartProcess.getSpartPart().getVender() != null)
					spartPartProcessDTO.setVender(spartPartProcess.getSpartPart()
							.getVender().getVenderCode());
				if (spartPartProcess.getSpartPart().getProducts() != null
						&& spartPartProcess.getSpartPart().getProducts().size() > 0) {
					for (Product product : spartPartProcess.getSpartPart()
							.getProducts()) {
						spartPartProcessDTO.setProduct(product.getProductName());
					}
				}
//				if (spartPartProcess.getSpartPart().getStations() != null
//						&& spartPartProcess.getSpartPart().getStations().size() > 0) {
//					for (Station station : spartPartProcess.getSpartPart()
//							.getStations()) {
//						spartPartDTO.setProduct(station.getStationName());
//					}
//				}
				spartPartProcessDTO.setPartId(spartPartProcess.getSpartPartCompanyLot().getLotNo());
				spartPartProcessDTO.setPartName(spartPartDTO.getPartName());
				spartPartProcessDTO.setPartNumber(spartPartDTO.getPartNumber());
				spartPartProcessDTO.setDescription(spartPartDTO.getDescription());
				spartPartProcessDTO.setStation(spartPartDTO.getStation());
				spartPartProcessDTO.setEntryDate(spartPartProcess.getCreateDate());
				spartPartProcessDTO.setEntryTime(MyDateUtils.getDayHour(spartPartProcessDTO.getEntryDate(), new Date()));
				spartPartProcessDTO.setQty(spartPartDTO.getQty());
//				spartPartProcessDTO.setLotType(Resource.get(Resource.class, spartPartProcess.getSpartPart().getResLotType().getName()).getName()) ;
				spartPartProcessDTO.setLotType(spartPartProcess.getSpartPart().getResLotType().getIdentifier());
				spartPartProcessDTO.setPon(spartPartDTO.getPon());
				spartPartProcessDTO.setUserName(queryVo.getUserDTO().getUserAccount());
//				spartPartProcessDTO.setStatus(spartPartDTO.getCurrStatus());
//				spartPartProcessDTO.setStatus(spartPartProcess.get);
				for(Location location:spartPartProcess.getLocations())
				{
					spartPartProcessDTO.setStockPos(location.getLoctionCode());
				}
				spartPartProcessDTO.setSpartPartDTO(spartPartDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(spartPartProcessDTO);
		}
		return new Page<SpartPartProcessDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Object> pageQuerySpartPartProcessTotal(SpartPartProcessDTO queryVo) {
		List<SpartPartProcessDTO> result = new ArrayList<SpartPartProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select count(*) from SpartPartProcess _spartPartProcess   left join _spartPartProcess.createUser  left join _spartPartProcess.lastModifyUser  left join _spartPartProcess.spartPart  left join _spartPartProcess.station  left join _spartPartProcess.resLotType  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _spartPartProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _spartPartProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _spartPartProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _spartPartProcess.status like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
		}
		else
		{
			jpql.append(" and _spartPartProcess.status not in (" + StaticValue.NOT_SHOW_STATUS +") ");
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _spartPartProcess.lotType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}

		if (queryVo.getStationId() != null) {
			jpql.append(" and _spartPartProcess.station.id = ? ");
			conditionVals.add(queryVo.getStationId());
		}

		Long object = (Long) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("countLot", String.valueOf(object));
		return resultMap;
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserBySpartPartProcess(Long id) {
		String jpql = "select e from SpartPartProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySpartPartProcess(Long id) {
		String jpql = "select e from SpartPartProcess o right join o.lastModifyUser e where o.id=?";
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
	public SpartPartDTO findSpartPartBySpartPartProcess(Long id) {
		String jpql = "select e from SpartPartProcess o right join o.spartPart e where o.id=?";
		SpartPart result = (SpartPart) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		SpartPartDTO dto = new SpartPartDTO();
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
	public Page<LocationDTO> findLocationsBySpartPartProcess(Long id,
			int currentPage, int pageSize) {
		List<LocationDTO> result = new ArrayList<LocationDTO>();
		String jpql = "select e from SpartPartProcess o right join o.locations e where o.id=?";
		Page<Location> pages = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (Location entity : pages.getData()) {
			LocationDTO dto = new LocationDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<LocationDTO>(
				Page.getStartOfPage(currentPage, pageSize),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public StationDTO findStationBySpartPartProcess(Long id) {
		String jpql = "select e from SpartPartProcess o right join o.station e where o.id=?";
		Station result = (Station) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		StationDTO dto = new StationDTO();
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
	public ResourceDTO findResLotTypeBySpartPartProcess(Long id) {
		String jpql = "select e from SpartPartProcess o right join o.resLotType e where o.id=?";
		Resource result = (Resource) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ResourceDTO dto = new ResourceDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public SpartPartProcessDTO saveSpartPartProcessReceive(
			SpartPartProcessDTO spartPartProcessDTO, Long spartPartId,
			String locationIds) {
		SpartPartProcess spartPartProcess = new SpartPartProcess();
		String jpql = "select o from SpartPart o  where o.id=?";
		SpartPart spartPart = (SpartPart) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { spartPartId }).singleResult();

		Set<Location> spartPartLocations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location spartPartLocation = Location.get(Location.class,
					locationId);
			spartPartLocations.add(spartPartLocation);
		}
		try {
			BeanUtils.copyProperties(spartPartProcess, spartPartProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user;
		if (spartPartProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(spartPartProcessDTO.getUserDTO()
					.getUserAccount());
		spartPartProcess.setCreateUser(user);
		spartPartProcess.setLastModifyUser(user);
		spartPartProcess.setShippingDate(spartPartProcessDTO.getCreateDate());
		spartPartProcess.setSpartPart(spartPart);
		SpartPartCompanyLot spartPartCompanyLot = SpartPartCompanyLot.get(SpartPartCompanyLot.class, spartPartProcessDTO.getSpartPartCompanyLotDTO().getId());
		spartPartProcess.setSpartPartCompanyLot(spartPartCompanyLot);
		spartPartProcess.setLocations(spartPartLocations);
		DefineStationProcess defineStationProcess = spartPart
				.getDefineStationProcess();
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.load(
					DefineStationProcess.class, Long.valueOf(11));
		}
		Set<Station> stations = defineStationProcess.getStations();
		spartPartProcess.setStation(stations.iterator().next());
		spartPartProcess.setStatus("Waiting");
		spartPartProcess.setResLotType(spartPart.getResLotType());
		spartPartProcess.save();
		spartPart.setCurrStatus("Received");
		spartPart.save();
		for (Location loc : spartPartLocations) {
			loc.setSpartPartProcess(spartPartProcess);
			loc.save();
		}
		this.saveOptLog(spartPartProcessDTO, spartPartProcess, user, "Received");
		spartPartProcessDTO.setId((java.lang.Long) spartPartProcess.getId());
		return spartPartProcessDTO;
	}

	public SpartPartProcessDTO saveNextProcess(
			SpartPartProcessDTO spartPartProcessDTO) {
		SpartPartProcess nextSpartPartProcess = new SpartPartProcess();
		SpartPartProcess nowSpartPartProcess = SpartPartProcess.load(
				SpartPartProcess.class, spartPartProcessDTO.getId());
		SpartPart spartPart = nowSpartPartProcess.getSpartPart();
		Set<Location> locations = new HashSet<Location>();
		User user;
		if (spartPartProcessDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(spartPartProcessDTO.getUserDTO()
					.getUserAccount());
		nextSpartPartProcess.setCreateDate(spartPartProcessDTO.getCreateDate());
		nextSpartPartProcess.setLastModifyDate(spartPartProcessDTO
				.getLastModifyDate());
		nextSpartPartProcess.setCreateUser(user);
		nextSpartPartProcess.setLastModifyUser(user);
		nextSpartPartProcess.setShippingDate(spartPartProcessDTO
				.getShippingDate());
//		SpartPartCompanyLot spartPartCompanyLot = SpartPartCompanyLot.get(SpartPartCompanyLot.class, spartPartProcessDTO.getSpartPartCompanyLotDTO().getId());
		
		nextSpartPartProcess.setSpartPart(spartPart);
		SpartPartCompanyLotDTO spartPartCompanyLotDTO = spartPartApplication.getSpartPartCompanyLotBySpartPart(spartPart.getId());
		nextSpartPartProcess.setSpartPartCompanyLot(SpartPartCompanyLot.get(SpartPartCompanyLot.class, spartPartCompanyLotDTO.getId()));
		if (spartPartProcessDTO.getStartFlow() != null) {
			DefineStationProcess defineStationProcess = DefineStationProcess
					.load(DefineStationProcess.class,
							spartPartProcessDTO.getStartFlow());
			nextSpartPartProcess.getSpartPart().setDefineStationProcess(
					defineStationProcess);
		}
		nextSpartPartProcess.setLocations(locations);
		DefineStationProcess defineStationProcess = spartPart
				.getDefineStationProcess();
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.get(
					DefineStationProcess.class, Long.valueOf(11));
		}
		Set<Station> stations = defineStationProcess.getStations();

		Station nextStation = null;
		for (Station station : stations) {
			if (station.getSequence() > nowSpartPartProcess.getStation()
					.getSequence()) {
				nextStation = station;
				break;
			}
		}
		if (spartPartProcessDTO.getNextStationId() != null) {
			nextStation = Station.load(Station.class,
					spartPartProcessDTO.getNextStationId());
		}
		if (nextStation == null) {
			return null;
		}
		nextSpartPartProcess.setStation(nextStation);
		Resource resource;
		if (spartPartProcessDTO.getStatus() != null) {
			resource = Resource.findByProperty(Resource.class, "identifier",
					spartPartProcessDTO.getStatus()).get(0);
		} else {
			resource = Resource.findByProperty(Resource.class, "identifier",
					"Waiting").get(0);
		}
		nextSpartPartProcess.setStatus(resource.getName());
		nextSpartPartProcess.setResLotType(nowSpartPartProcess.getResLotType());
		nextSpartPartProcess.save();
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		nowSpartPartProcess.setStatus(finish.getName());
		nowSpartPartProcess.setLastModifyDate(spartPartProcessDTO
				.getLastModifyDate());
		nowSpartPartProcess.setLastModifyUser(user);
		nowSpartPartProcess.save();
		locationApplication.clearLocationsProcess(this
				.getLocationIdsByLocations(nowSpartPartProcess.getLocations()));
		spartPartProcessDTO
				.setId((java.lang.Long) nextSpartPartProcess.getId());
		return spartPartProcessDTO;
	}
	public void changeLocations(SpartPartProcessDTO spartPartProcessDTO,
			String locationIds, String useraccount, String comments) {
		User user;
		if (spartPartProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(spartPartProcessDTO.getUserDTO()
					.getUserAccount());

		Set<Location> locations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			locations.add(location);
		}
		SpartPartProcess spartPartProcess = SpartPartProcess.get(SpartPartProcess.class,
				spartPartProcessDTO.getId());
		for(Location location : spartPartProcess.getLocations()){
			location.setSpartPartProcess(null);
		}
		spartPartProcess.setLocations(locations);
		for (Location loc : locations) {
			loc.setSpartPartProcess(spartPartProcess);
			loc.save();
		}
		spartPartProcess.setLastModifyDate(spartPartProcessDTO.getCreateDate());
		spartPartProcess.setLastModifyUser(user);
		spartPartProcess.save();
		SpartPartChangeLocationOptLog spartPartChangeLocationOptLog = new SpartPartChangeLocationOptLog();
		OptLog optLog = new OptLog();
		spartPartChangeLocationOptLog
				.setCreateDate(spartPartProcessDTO.getCreateDate());
		spartPartChangeLocationOptLog.setCreateUser(user);
		spartPartChangeLocationOptLog.setLastModifyDate(spartPartProcessDTO
				.getLastModifyDate());
		spartPartChangeLocationOptLog.setLastModifyUser(user);
		spartPartChangeLocationOptLog.setSpartPartProcess(spartPartProcess);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		spartPartChangeLocationOptLog.setOptLog(optLog);
		spartPartChangeLocationOptLog.save();
	}
	
	public void changeStatus(SpartPartProcessDTO spartPartProcessDTO, String holdCode, String holdCodeId, String useraccount,
			String comments)
	{
		changeStatusSpartPartProcess(spartPartProcessDTO,holdCode,holdCodeId,useraccount,comments,null);
	}
	private void changeStatusSpartPartProcess(SpartPartProcessDTO spartPartProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments, String engineerName) {
		User user;
		if (spartPartProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(spartPartProcessDTO.getUserDTO()
					.getUserAccount());
		SpartPartProcess spartPartProcess = WaferProcess.get(SpartPartProcess.class,
				spartPartProcessDTO.getId());
		if(spartPartProcessDTO.getShippingDate() != null){
			spartPartProcess.setShippingDate(spartPartProcessDTO.getShippingDate());
		}
		spartPartProcess.setLastModifyDate(spartPartProcessDTO.getCreateDate());
		spartPartProcess.setLastModifyUser(user); 
//		String nowStatus = spartPartProcess.getStatus();
//		WaferStatusOptLog waferStatusOptLog = new WaferStatusOptLog();
		if (holdCodeId != null && !"".equals(holdCodeId)) {
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment
					.findRelationByResource(Long.valueOf(holdCodeId)).get(0);
//			spartPartProcess.setResHold(rsourceLineAssignment.getChild());
			spartPartProcess.setStatus(rsourceLineAssignment.getParent().getName());
//			waferStatusOptLog.setHoldCode(rsourceLineAssignment.getChild()
//					.getDesc());
		} else {
			spartPartProcess.setStatus(spartPartProcessDTO.getStatus());
//			waferStatusOptLog.setHoldCode(holdCode);
		}
		spartPartProcess.save();
		saveOptLog(spartPartProcessDTO, spartPartProcess, user, spartPartProcess.getStatus());
	}
	private void saveOptLog(SpartPartProcessDTO spartPartProcessDTO,
			SpartPartProcess nowSpartPartProcess, User user, String type) {
		StatusOptLog spartPartOptLog = new StatusOptLog();
		OptLog optLog = new OptLog();

		spartPartOptLog.setCreateDate(spartPartProcessDTO.getCreateDate());
		spartPartOptLog.setCreateUser(user);
		spartPartOptLog.setLastModifyDate(spartPartProcessDTO
				.getLastModifyDate());
		spartPartOptLog.setLastModifyUser(user);
		spartPartOptLog.setSpartPartProcess(nowSpartPartProcess);
		spartPartOptLog.setType(type);
		if (spartPartProcessDTO.getComments() == null)
			optLog.setComments(type);
		else
			optLog.setComments(spartPartProcessDTO.getComments());
		optLog.setRightUserUser(User.findByUserAccount(spartPartProcessDTO
				.getUseraccount()));
		optLog.save();
		spartPartOptLog.setOptLog(optLog);
		spartPartOptLog.save();
	}

	private Set<Location> getLocations(String locationIds) {
		Set<Location> locations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int n = 0; n < locationIdArray.length; n++) {
			Long locationId = Long.parseLong(locationIdArray[n]);
			Location location = Location.get(Location.class, locationId);
			locations.add(location);
		}
		return locations;
	}

	private String getLocationIdsByLocations(Set<Location> locations) {
		// 处理库存位置
		String stockPos = "";
		for (Location location : locations) {
			stockPos += "," + location.getId();
		}
		if (stockPos.length() > 2) {
			stockPos = stockPos.substring(1);
		}
		return stockPos;
	}

}
