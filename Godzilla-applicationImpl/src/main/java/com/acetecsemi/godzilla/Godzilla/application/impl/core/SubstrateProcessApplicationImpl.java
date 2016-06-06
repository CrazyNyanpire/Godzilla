package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SubstrateProcessApplicationImpl implements
		SubstrateProcessApplication {

	private QueryChannelService queryChannel;
	
	@Inject
	private LocationApplication locationApplication;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateProcessDTO getSubstrateProcess(Long id) {
		SubstrateProcess substrateProcess = SubstrateProcess.load(
				SubstrateProcess.class, id);
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(substrateProcessDTO, substrateProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateProcessDTO.setId((java.lang.Long) substrateProcess.getId());
		SubstrateCustomerLot substrateCustomerLot = substrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot();
		substrateProcessDTO.setVenderName(substrateCustomerLot.getVender().getVenderName());
		substrateProcessDTO.setLotNo(substrateProcess.getSubstrateCompanyLot().getLotNo());
		substrateProcessDTO.setCustomerLotNo(substrateCustomerLot.getCustomerLotNo());
		substrateProcessDTO.setPartNumber(substrateCustomerLot.getSubstratePart().getPartNo());
		substrateProcessDTO.setBatchNo(substrateCustomerLot.getBatchNo());
		substrateProcessDTO.setPon(substrateCustomerLot.getPon());
		if("Waiting".equals(substrateProcess.getStatus())){
			substrateProcessDTO.setEntryDate(substrateProcess.getCreateDate());
		}else
			substrateProcessDTO.setEntryDate(substrateProcess.getShippingDate());
		substrateProcessDTO.setEntryTime(MyDateUtils.getDayHour(substrateProcessDTO.getEntryDate(), new Date()));
		substrateProcessDTO.setUserName(substrateCustomerLot.getCreateUser().getName());
		substrateProcessDTO.setCurrStatus(substrateProcess.getStatus());
		substrateProcessDTO.setStep(substrateProcess.getStation().getStationNameEn());
		Set<Location> locations = substrateProcess.getSubstrateLocations();
		String stockPos = "";
		for (Location location : locations) {
			stockPos += ";" + location.getLoctionCode();
		}
		if (stockPos.length() > 5) {
			substrateProcessDTO.setStockPos(stockPos.substring(1));
		}
		substrateProcessDTO.setLotType(substrateProcess.getResLotType().getIdentifier());
		substrateProcessDTO.setGuaranteePeriod(substrateCustomerLot.getExpiryDate());
		substrateProcessDTO.setProductionDate(substrateCustomerLot.getManufactureDate());
		substrateProcessDTO.setSubstrateCompanyId(substrateProcess.getSubstrateCompanyLot().getId());
		return substrateProcessDTO;
	}

	public SubstrateProcessDTO saveSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO) {
		SubstrateProcess substrateProcess = new SubstrateProcess();
		try {
			BeanUtils.copyProperties(substrateProcess, substrateProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateProcess.save();
		substrateProcessDTO.setId((java.lang.Long) substrateProcess.getId());
		return substrateProcessDTO;
	}

	public void updateSubstrateProcess(SubstrateProcessDTO substrateProcessDTO) {
		SubstrateProcess substrateProcess = SubstrateProcess.get(
				SubstrateProcess.class, substrateProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(substrateProcess, substrateProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeSubstrateProcess(Long id) {
		this.removeSubstrateProcesss(new Long[] { id });
	}

	public void removeSubstrateProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SubstrateProcess substrateProcess = SubstrateProcess.load(
					SubstrateProcess.class, ids[i]);
			substrateProcess.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstrateProcessDTO> findAllSubstrateProcess() {
		List<SubstrateProcessDTO> list = new ArrayList<SubstrateProcessDTO>();
		List<SubstrateProcess> all = SubstrateProcess
				.findAll(SubstrateProcess.class);
		for (SubstrateProcess substrateProcess : all) {
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(substrateProcessDTO, substrateProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(substrateProcessDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstrateProcessDTO> pageQuerySubstrateProcess(
			SubstrateProcessDTO queryVo, int currentPage, int pageSize) {
		List<SubstrateProcessDTO> result = new ArrayList<SubstrateProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _substrateProcess from SubstrateProcess _substrateProcess   left join _substrateProcess.createUser  left join _substrateProcess.lastModifyUser  left join _substrateProcess.station  left join _substrateProcess.equipment  left join _substrateProcess.substrateCompanyLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _substrateProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _substrateProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _substrateProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _substrateProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getEndTime() != null) {
			jpql.append(" and _substrateProcess.endTime between ? and ? ");
			conditionVals.add(queryVo.getEndTime());
			conditionVals.add(queryVo.getEndTimeEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _substrateProcess.status in (" + queryVo.getStatus() + ")");
		}
		
		if (queryVo.getCurrStatus() != null && !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _substrateProcess.status in (" + queryVo.getCurrStatus() + ")");
		}

		if (queryVo.getOptDate() != null) {
			jpql.append(" and _substrateProcess.optDate between ? and ? ");
			conditionVals.add(queryVo.getOptDate());
			conditionVals.add(queryVo.getOptDateEnd());
		}
		if (queryVo.getSort() != null) {
			jpql.append(" and _substrateProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _substrateProcess.resLotType.identifier like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}
		
		if (queryVo.getVenderName() != null && !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}
		
		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		
		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPon()));
		}
		
		if (queryVo.getPartNumber() != null && !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.product.partNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}
		
		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.lotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotNo()));
		}
		
		if (queryVo.getCustomerLotNo() != null && !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}
		
		if (queryVo.getStationId() != null) {
			jpql.append(" and _substrateProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and  not(_substrateProcess.status=?)");
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		conditionVals.add(finish.getName());
		jpql.append(" and _substrateProcess.status not in (" + StaticValue.NOT_SHOW_STATUS +") ");
		jpql.append(" order by _substrateProcess.shippingDate DESC");
		Page<SubstrateProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (SubstrateProcess substrateProcess : pages.getData()) {
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(substrateProcessDTO, substrateProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			SubstrateCustomerLot substrateCustomerLot = substrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot();
			substrateProcessDTO.setVenderName(substrateCustomerLot.getVender().getVenderName());
			substrateProcessDTO.setLotNo(substrateProcess.getSubstrateCompanyLot().getLotNo());
			substrateProcessDTO.setCustomerLotNo(substrateCustomerLot.getCustomerLotNo());
			substrateProcessDTO.setPartNumber(substrateCustomerLot.getSubstratePart().getPartNo());
			substrateProcessDTO.setBatchNo(substrateCustomerLot.getBatchNo());
			substrateProcessDTO.setPon(substrateCustomerLot.getPon());
			if("Waiting".equals(substrateProcess.getStatus())){
				substrateProcessDTO.setEntryDate(substrateProcess.getCreateDate());
			}else
				substrateProcessDTO.setEntryDate(substrateProcess.getShippingDate());
			substrateProcessDTO.setEntryTime(MyDateUtils.getDayHour(substrateProcessDTO.getEntryDate(), new Date()));
			substrateProcessDTO.setUserName(substrateCustomerLot.getCreateUser().getName());
			substrateProcessDTO.setCurrStatus(substrateProcess.getStatus());
			substrateProcessDTO.setStep(substrateProcess.getStation().getStationNameEn());
			Set<Location> locations = substrateProcess.getSubstrateLocations();
			String stockPos = "";
			for (Location location : locations) {
				stockPos += ";" + location.getLoctionCode();
			}
			if (stockPos.length() > 5) {
				substrateProcessDTO.setStockPos(stockPos.substring(1));
			}
			substrateProcessDTO.setGuaranteePeriod(substrateCustomerLot.getExpiryDate());
			substrateProcessDTO.setProductionDate(substrateCustomerLot.getManufactureDate());
			substrateProcessDTO.setLotType(substrateProcess.getResLotType().getIdentifier());
			SubstrateStatusOptLogDTO substrateStatusOptLogDTO = this.findSubstrateStatusOptLogByProcess(substrateProcess.getId());
			if(substrateStatusOptLogDTO != null){
				substrateProcessDTO.setHoldReason(substrateStatusOptLogDTO.getComments());
			}
			result.add(substrateProcessDTO);
		}
		return new Page<SubstrateProcessDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}
	
	public Map<String, Object> pageQuerySubstrateProcessTotal(
			SubstrateProcessDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_substrateProcess.qtyIn),sum(_substrateProcess.stripQtyIn),count(*) from SubstrateProcess _substrateProcess   left join _substrateProcess.createUser  left join _substrateProcess.lastModifyUser  left join _substrateProcess.station  left join _substrateProcess.equipment  left join _substrateProcess.substrateCompanyLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _substrateProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _substrateProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _substrateProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _substrateProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getEndTime() != null) {
			jpql.append(" and _substrateProcess.endTime between ? and ? ");
			conditionVals.add(queryVo.getEndTime());
			conditionVals.add(queryVo.getEndTimeEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _substrateProcess.status in (" + queryVo.getStatus() + ")");
		}
		
		if (queryVo.getCurrStatus() != null && !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _substrateProcess.status in (" + queryVo.getCurrStatus() + ")");
		}

		if (queryVo.getOptDate() != null) {
			jpql.append(" and _substrateProcess.optDate between ? and ? ");
			conditionVals.add(queryVo.getOptDate());
			conditionVals.add(queryVo.getOptDateEnd());
		}
		if (queryVo.getSort() != null) {
			jpql.append(" and _substrateProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _substrateProcess.resLotType.identifier like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}
		
		if (queryVo.getVenderName() != null && !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}
		
		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		
		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPon()));
		}
		
		if (queryVo.getPartNumber() != null && !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.product.partNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}
		
		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.lotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotNo()));
		}
		
		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.lotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotNo()));
		}
		
		if (queryVo.getCustomerLotNo() != null && !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _substrateProcess.substrateCompanyLot.substrateCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}
		
		if (queryVo.getStationId() != null) {
			jpql.append(" and _substrateProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and  not(_substrateProcess.status=?)");
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		conditionVals.add(finish.getName());
		jpql.append(" and _substrateProcess.status not in (" + StaticValue.NOT_SHOW_STATUS +") ");
		jpql.append(" order by _substrateProcess.shippingDate DESC");
		Object[] object = ( Object[]) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dieTotal", String.valueOf(object[0]));
		resultMap.put("stripTotal", String.valueOf(object[1]));
		resultMap.put("countLot", String.valueOf(object[2]));
		return resultMap;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserBySubstrateProcess(Long id) {
		String jpql = "select e from SubstrateProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySubstrateProcess(Long id) {
		String jpql = "select e from SubstrateProcess o right join o.lastModifyUser e where o.id=?";
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
	public StationDTO findStationBySubstrateProcess(Long id) {
		String jpql = "select e from SubstrateProcess o right join o.station e where o.id=?";
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
	public Page<LocationDTO> findLocationsBySubstrateProcess(Long id,
			int currentPage, int pageSize) {
		List<LocationDTO> result = new ArrayList<LocationDTO>();
		String jpql = "select e from SubstrateProcess o right join o.locations e where o.id=?";
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
	public EquipmentDTO findEquipmentBySubstrateProcess(Long id) {
		String jpql = "select e from SubstrateProcess o right join o.equipment e where o.id=?";
		Equipment result = (Equipment) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		EquipmentDTO dto = new EquipmentDTO();
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
	public SubstrateCompanyLotDTO findSubstrateCompanyLotBySubstrateProcess(
			Long id) {
		String jpql = "select e from SubstrateProcess o right join o.substrateCompanyLot e where o.id=?";
		SubstrateCompanyLot result = (SubstrateCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		SubstrateCompanyLotDTO dto = new SubstrateCompanyLotDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public SubstrateProcessDTO saveSubstrateProcessReceive(
			SubstrateProcessDTO substrateProcessDTO,
			Long substrateCustomerLotId, String locationIds) {
		SubstrateProcess substrateProcess = new SubstrateProcess();
		String jpql = "select o from SubstrateCompanyLot o left join o.substrateCustomerLot e where e.id=?";
		SubstrateCompanyLot substrateCompanyLot = (SubstrateCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { substrateCustomerLotId })
				.singleResult();

		Set<Location> substrateLocations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location substrateLocation = Location.get(
					Location.class, locationId);
			substrateLocations.add(substrateLocation);
		}
		try {
			BeanUtils.copyProperties(substrateProcess, substrateProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user;
		if (substrateProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		substrateProcess.setCreateUser(user);
		substrateProcess.setLastModifyUser(user);
		substrateProcess.setShippingDate(substrateProcessDTO.getCreateDate());
		substrateProcess.setSubstrateCompanyLot(substrateCompanyLot);
		substrateProcess.setSubstrateLocations(substrateLocations);
		substrateProcess.setQtyIn(substrateCompanyLot.getSubstrateCustomerLot()
				.getQty());
		substrateProcess.setQtyOut(substrateCompanyLot.getSubstrateCustomerLot()
				.getQty());
		substrateProcess.setStripQtyIn(substrateCompanyLot
				.getSubstrateCustomerLot().getStrip());
		substrateProcess.setStripQtyOut(substrateCompanyLot
				.getSubstrateCustomerLot().getStrip());
		DefineStationProcess defineStationProcess = substrateCompanyLot
				.getSubstrateCustomerLot().getDefineStationProcess();
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.load(
					DefineStationProcess.class, Long.valueOf(2));
		}
		Set<Station> stations = defineStationProcess
				.getStations();
		substrateProcess.setStation(stations.iterator().next());
		substrateProcess.setStatus("Waiting");
		substrateProcess.setResLotType(substrateCompanyLot.getSubstrateCustomerLot().getResLotType());
		substrateProcess.save();
		SubstrateCustomerLot substrateCustomerLot = substrateCompanyLot
				.getSubstrateCustomerLot();
		substrateCustomerLot.setCurrStatus("Received");
		substrateCustomerLot.save();
		for (Location loc : substrateLocations) {
			loc.setSubstrateProcess(substrateProcess);
			loc.save();
		}
		this.saveOptLog(substrateProcessDTO, substrateProcess, user, "Received");
		substrateProcessDTO.setId((java.lang.Long) substrateProcess.getId());
		return substrateProcessDTO;
	}

	public SubstrateProcessDTO saveNextProcess(
			SubstrateProcessDTO substrateProcessDTO) {
		SubstrateProcess nextSubstrateProcess = new SubstrateProcess();
		SubstrateProcess nowSubstrateProcess = SubstrateProcess.load(
				SubstrateProcess.class, substrateProcessDTO.getId());
		SubstrateCompanyLot substrateCompanyLot = nowSubstrateProcess
				.getSubstrateCompanyLot();
		Set<Location> locations = new HashSet<Location>();
		User user;
		if (substrateProcessDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		nextSubstrateProcess.setCreateDate(substrateProcessDTO.getCreateDate());
		nextSubstrateProcess.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		nextSubstrateProcess.setCreateUser(user);
		nextSubstrateProcess.setLastModifyUser(user);
		nextSubstrateProcess.setShippingDate(substrateProcessDTO
				.getShippingDate());
		nextSubstrateProcess.setSubstrateCompanyLot(substrateCompanyLot);
		if (substrateProcessDTO.getStartFlow() != null) {
			DefineStationProcess defineStationProcess = DefineStationProcess.load(DefineStationProcess.class, substrateProcessDTO.getStartFlow());
			nextSubstrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().setDefineStationProcess(defineStationProcess);
		}
		nextSubstrateProcess.setSubstrateLocations(locations);
		nowSubstrateProcess.setQtyOut(nowSubstrateProcess.getQtyIn());
		nextSubstrateProcess.setQtyIn(nowSubstrateProcess.getQtyIn());
		nowSubstrateProcess.setStripQtyOut(nowSubstrateProcess.getStripQtyIn());
		nextSubstrateProcess.setStripQtyIn(nowSubstrateProcess.getStripQtyIn());
		DefineStationProcess defineStationProcess = substrateCompanyLot
				.getSubstrateCustomerLot().getDefineStationProcess();
		if(defineStationProcess == null){
			defineStationProcess = DefineStationProcess.get(DefineStationProcess.class,Long.valueOf(2));
		}
		Set<Station> stations = defineStationProcess
				.getStations();

		Station nextStation = null;
		for (Station station : stations) {
			if (station.getSequence() > nowSubstrateProcess.getStation().getSequence()) {
				nextStation = station;
				break;
			}
		}
		if(substrateProcessDTO.getNextStationId() != null){
			nextStation = Station.load(Station.class, substrateProcessDTO.getNextStationId());
		}
		if(nextStation == null){
			return null;
		}
		nextSubstrateProcess.setStation(nextStation);
		Resource resource ;
		if(substrateProcessDTO.getCurrStatus() != null){
			resource = Resource.findByProperty(Resource.class, "identifier",
					substrateProcessDTO.getCurrStatus()).get(0);
		}else{
			resource = Resource.findByProperty(Resource.class, "identifier",
					"Waiting").get(0);
		}
		nextSubstrateProcess.setStatus(resource.getName());
		nextSubstrateProcess.setResLotType(nowSubstrateProcess.getResLotType());
		nextSubstrateProcess.save();
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		nowSubstrateProcess.setStatus(finish.getName());
		nowSubstrateProcess.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		nowSubstrateProcess.setLastModifyUser(user);
		nowSubstrateProcess.save();
		locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(nowSubstrateProcess.getSubstrateLocations()));
		substrateProcessDTO
				.setId((java.lang.Long) nextSubstrateProcess.getId());
		return substrateProcessDTO;
	}

	private Set<Station> sortStation(Set<Station> stations) {
		Set<Station> ps = new TreeSet<Station>(new Comparator<Station>() {
			public int compare(Station o1, Station o2) {
				return o1.getSequence().compareTo(o2.getSequence());
				// 这是正序排列，如果想倒序的话，调换o1、o2的位置，
			}
		});
		ps.addAll(stations);
		return ps;
	}

	public String saveSplitSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String useraccount,
			Integer stripQty, Integer qty, String comments) {
		User user;
		if (substrateProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批
		SubstrateProcess nowSubstrateProcess = SubstrateProcess.get(
				SubstrateProcess.class, substrateProcessDTO.getId());
		if(nowSubstrateProcess.getStripQtyIn() == 1 || stripQty >= nowSubstrateProcess.getStripQtyIn() || qty >= nowSubstrateProcess.getQtyIn())
		{
			return "wafer.split.stripQtyEqualZero";
		}
		SubstrateCompanyLot substrateCompanyLot = nowSubstrateProcess
				.getSubstrateCompanyLot();
		Date date = new Date();
		List<String> lotNoList = this.getSplitLotNo(nowSubstrateProcess
				.getSubstrateCompanyLot().getLotNo(), 2);
		for (int i = 0; i < lotNoList.size(); i++) {
			String tempLocationIds;
			SubstrateCompanyLot splitSubstrateCompanyLot = new SubstrateCompanyLot();
			splitSubstrateCompanyLot
					.setSubstrateCustomerLot(substrateCompanyLot
							.getSubstrateCustomerLot());
			splitSubstrateCompanyLot.setLotNo(lotNoList.get(i));
			splitSubstrateCompanyLot
					.setParentSubstrateCompanyLot(substrateCompanyLot);
			splitSubstrateCompanyLot.setCreateDate(nowSubstrateProcess.getCreateDate());
			splitSubstrateCompanyLot.setCreateUser(user);
			splitSubstrateCompanyLot.setLastModifyDate(date);
			splitSubstrateCompanyLot.setLastModifyUser(user);
			splitSubstrateCompanyLot.save();
			SubstrateProcess splitSubstrateProcess = new SubstrateProcess();
			splitSubstrateProcess
					.setSubstrateCompanyLot(splitSubstrateCompanyLot);
			if (i == 0) {
				splitSubstrateProcess.setQtyIn(nowSubstrateProcess.getQtyIn()
						- qty);
				splitSubstrateProcess.setStripQtyIn(nowSubstrateProcess
						.getStripQtyIn() - stripQty);
				tempLocationIds = this.getLocationIdsByLocations(nowSubstrateProcess.getSubstrateLocations());
			} else {
				splitSubstrateProcess.setQtyIn(qty);
				splitSubstrateProcess.setStripQtyIn(stripQty);
				tempLocationIds = substrateProcessDTO.getLocationIds();
			}
			splitSubstrateProcess.setShippingDate(nowSubstrateProcess
					.getShippingDate());
			splitSubstrateProcess.setStatus(nowSubstrateProcess.getStatus());
			splitSubstrateProcess.setStation(nowSubstrateProcess.getStation());
//			splitSubstrateProcess.setCreateDate(substrateProcessDTO
//					.getCreateDate());
			splitSubstrateProcess.setCreateDate(nowSubstrateProcess.getCreateDate());
			splitSubstrateProcess.setCreateUser(user);
			splitSubstrateProcess.setLastModifyDate(date);
			splitSubstrateProcess.setLastModifyUser(user);
			splitSubstrateProcess.setResLotType(nowSubstrateProcess.getResLotType());
			splitSubstrateProcess.save();
			locationApplication.updateLocationsSubstrateProcess(tempLocationIds, splitSubstrateProcess.getId());
		}
		nowSubstrateProcess.setStatus("Split");
		nowSubstrateProcess.save();
		// 保存操作信息 --start
		SubstrateOptLog substrateSplitOptLog = new SubstrateOptLog();
		OptLog optLog = new OptLog();

		substrateSplitOptLog.setCreateDate(date);
		substrateSplitOptLog.setCreateUser(user);
		substrateSplitOptLog.setLastModifyDate(date);
		substrateSplitOptLog.setLastModifyUser(user);
		substrateSplitOptLog.setStripQty(stripQty);
		substrateSplitOptLog.setQty(qty);
		substrateSplitOptLog.setSubstrateProcess(nowSubstrateProcess);
		substrateSplitOptLog.setType("split");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		substrateSplitOptLog.setOptLog(optLog);
		substrateSplitOptLog.save();
		// 保存操作信息 --end
		return "success";
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstrateProcessDTO> getSplitSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO,
			Integer stripQty, Integer qty) {
		// 处理分批
		SubstrateProcess nowSubstrateProcess = SubstrateProcess.get(SubstrateProcess.class,
				substrateProcessDTO.getId());
		List<String> lotNoList = this.getSplitLotNo(nowSubstrateProcess
				.getSubstrateCompanyLot().getLotNo(), 2);
		List<SubstrateProcessDTO> list = new ArrayList<SubstrateProcessDTO>();
		for (int i = 0; i < lotNoList.size(); i++) {
			SubstrateProcessDTO sPDTO = new SubstrateProcessDTO();
			SubstrateCompanyLot nowSubstrateCompanyLot = nowSubstrateProcess
					.getSubstrateCompanyLot();
			SubstrateCustomerLot substrateCustomerLot = nowSubstrateCompanyLot.getSubstrateCustomerLot();
			sPDTO.setLotNo(lotNoList.get(i));
			sPDTO.setCustomerLotNo(nowSubstrateCompanyLot.getSubstrateCustomerLot()
					.getCustomerLotNo());
			sPDTO.setPartNumber(nowSubstrateCompanyLot.getSubstrateCustomerLot()
					.getSubstratePart().getPartNo());
			if (i == 0) {
				sPDTO.setQtyIn(nowSubstrateProcess.getQtyIn() - qty);
				sPDTO.setStripQtyIn(nowSubstrateProcess.getStripQtyIn() - stripQty);
				sPDTO.setStockPos(this.getLocationsByLocations(nowSubstrateProcess
						.getSubstrateLocations()));
			} else {
				sPDTO.setQtyIn(qty);
				sPDTO.setStripQtyIn(stripQty);
				sPDTO.setStockPos(this.getLocationsById(substrateProcessDTO
						.getLocationIds()));
			}
			sPDTO.setVenderName(substrateCustomerLot.getVender().getVenderName());
			sPDTO.setLotNo(nowSubstrateProcess.getSubstrateCompanyLot().getLotNo());
			sPDTO.setCustomerLotNo(substrateCustomerLot.getCustomerLotNo());
			sPDTO.setPartNumber(substrateCustomerLot.getSubstratePart().getPartNo());
			sPDTO.setBatchNo(substrateCustomerLot.getBatchNo());
			sPDTO.setPon(substrateCustomerLot.getPon());
//			sPDTO.setEntryDate(substrateCustomerLot.getShippingDate());
//			sPDTO.setEntryTime(MyDateUtils.getDayHour(substrateCustomerLot.getShippingDate(), new Date()));
//			sPDTO.setEntryDate(nowSubstrateProcess.getShippingDate());
			sPDTO.setEntryDate(nowSubstrateProcess.getCreateDate());
			sPDTO.setEntryTime(MyDateUtils.getDayHour(sPDTO.getEntryDate(), new Date()));
			sPDTO.setUserName(substrateCustomerLot.getCreateUser().getName());
			sPDTO.setCurrStatus(nowSubstrateProcess.getStatus());
			sPDTO.setGuaranteePeriod(substrateCustomerLot.getExpiryDate());
			sPDTO.setProductionDate(substrateCustomerLot.getManufactureDate());
			sPDTO.setCurrStatus(nowSubstrateProcess.getStatus());
			sPDTO.setStatus(nowSubstrateProcess.getStatus());
			list.add(sPDTO);
		}
		return list;
	}

	private List<String> getLotNoList(String lotNo, int lot) {
		String[] str = lotNo.split("-");// SA00001-01
		List<String> lotList = new ArrayList<String>();
		int n = Integer.parseInt(str[1]);
		for (int i = n; i < n + lot; i++) {
			String num = "00" + String.valueOf(i);
			num = num.substring(num.length() - 2);
			lotList.add(str[0] + "-" + num);
			//lotList.add(str[0] + "-" + String.valueOf(i));
		}
		return lotList;
	}

	private List<String> getSplitLotNo(String lotNo, int lot) {
		String maxLotNo = this.findMaxLotNoByLotNo(lotNo.split("-")[0]);
		List<String> lotNoList = this.getLotNoList(maxLotNo, lot);
		lotNoList.set(0, lotNo);
		return lotNoList;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private String findMaxLotNoByLotNo(String lotNo) {
		String jpql = "select max(o.lotNo) from SubstrateCompanyLot o where o.lotNo like ?";
		String maxLotNo = (String) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { "%" + lotNo + "%" })
				.singleResult();
		return maxLotNo;
	}

	public void changeStatusSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,String holdCodeId,
			String useraccount, String comments) {
		User user;
		if (substrateProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		SubstrateProcess substrateProcess = SubstrateProcess.get(
				SubstrateProcess.class, substrateProcessDTO.getId());
		substrateProcess.setStatus(substrateProcessDTO.getStatus());
		if(substrateProcessDTO.getShippingDate() != null){
			substrateProcess.setShippingDate(substrateProcessDTO.getShippingDate());
		}
		substrateProcess.setLastModifyDate(substrateProcessDTO.getCreateDate());
		substrateProcess.setLastModifyUser(user);
		
		SubstrateStatusOptLog substrateStatusOptLog = new SubstrateStatusOptLog();
		OptLog optLog = new OptLog();
		if(holdCodeId != null && !"".equals(holdCodeId)){
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment.findRelationByResource(Long.valueOf(holdCodeId)).get(0);
			substrateProcess.setResHold(rsourceLineAssignment.getChild());
			substrateProcess.setStatus(rsourceLineAssignment.getParent().getName());
			substrateStatusOptLog.setHoldCode(rsourceLineAssignment.getChild().getDesc());
		}else{
			substrateProcess.setStatus(substrateProcessDTO.getCurrStatus());
			substrateStatusOptLog.setHoldCode(holdCode);
		}
		substrateStatusOptLog
				.setCreateDate(substrateProcessDTO.getCreateDate());
		substrateStatusOptLog.setCreateUser(user);
		substrateStatusOptLog.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		substrateStatusOptLog.setLastModifyUser(user);
		substrateStatusOptLog.setSubstrateProcess(substrateProcess);
		if (substrateProcessDTO.getFutureHoldStationId() != null) {
			Station station = Station.load(Station.class,
					substrateProcessDTO.getFutureHoldStationId());
			if (station != null)
				substrateStatusOptLog.setFutureStation(station);
		}
		//substrateStatusOptLog.setHoldCode(holdCode);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		if(substrateProcessDTO.getEngineerName() != null){
			substrateStatusOptLog.setEngineerName(substrateProcessDTO.getEngineerName());
		}
		substrateStatusOptLog.setOptLog(optLog);
		substrateStatusOptLog.save();
	}

	public void holdSubstrateProcess(SubstrateProcessDTO substrateProcessDTO,
			String holdCode, String useraccount, String comments) {
		//substrateProcessDTO.setStatus("Hold");
		if ("pass".equals(holdCode.toLowerCase())){
			substrateProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		}
		else
			substrateProcessDTO.setCurrStatus(holdCode);
		this.changeStatusSubstrateProcess(substrateProcessDTO, holdCode,null,
				useraccount, comments);

	}

	public void engDispSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,
			String useraccount, String comments) {
		if ("pass".equals(holdCode.toLowerCase())){
			substrateProcessDTO.setStatus("Pass");
			holdCode = "Eng. Pass";
		}
		else
			substrateProcessDTO.setStatus(holdCode);
		this.engDispSubstrateProcess(substrateProcessDTO, holdCode, null, useraccount, comments);
	}

	public String saveMergeSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String mergeIds,
			String useraccount, String comments) {
		User user;
		if (substrateProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批

		SubstrateProcess nowSubstrateProcess = new SubstrateProcess();
		SubstrateCompanyLot mergeSubstrateCompanyLot = new SubstrateCompanyLot();
		String[] idArray = mergeIds.split(",");
		List<SubstrateProcess> list = new ArrayList<SubstrateProcess>();
		int qtyIn = 0;
		int dieQtyTotal = 0;
		int substrateQtyTotal = 0;
		SubstrateProcess tempSubstrateProcess = null;
		for (String id : idArray) {
			SubstrateProcess substrateProcess = SubstrateProcess.load(
					SubstrateProcess.class, Long.parseLong(id));
			if (qtyIn < substrateProcess.getQtyIn()) {
				nowSubstrateProcess = substrateProcess;
				qtyIn = substrateProcess.getQtyIn();

			}
			if (tempSubstrateProcess != null){
			
				if(tempSubstrateProcess.getSubstrateCompanyLot()
				.getSubstrateCustomerLot().getSubstratePart().getId() != substrateProcess
				.getSubstrateCompanyLot().getSubstrateCustomerLot()
				.getSubstratePart().getId()) 
				{
					return "substrate.mergCompanyLot.product";
				}
				if(tempSubstrateProcess.getSubstrateCompanyLot()
						.getSubstrateCustomerLot().getVender().getId() != substrateProcess.getSubstrateCompanyLot()
						.getSubstrateCustomerLot().getVender().getId())
				{
					return "substrate.mergCompanyLot.vender";
				}
//				if(tempSubstrateProcess.getSubstrateCompanyLot()
//						.getSubstrateCustomerLot().getBatchNo()!=null &&
//								substrateProcess.getSubstrateCompanyLot()
//										.getSubstrateCustomerLot().getBatchNo()!= null)
//				{
//					if( !tempSubstrateProcess.getSubstrateCompanyLot()
//						.getSubstrateCustomerLot().getBatchNo().trim().equals(substrateProcess.getSubstrateCompanyLot()
//						.getSubstrateCustomerLot().getBatchNo().trim()))
//					{
//						return "substrate.mergCompanyLot.batchNo";
//					}
//				}
//				else
//				{
//					return "substrate.mergCompanyLot.batchNo";
//				}
				if(!tempSubstrateProcess.getSubstrateCompanyLot()
							.getSubstrateCustomerLot().getPon().equals(substrateProcess
							.getSubstrateCompanyLot().getSubstrateCustomerLot()
							.getPon()))
				{
					return "substrate.mergCompanyLot.pon";
				}
				if(tempSubstrateProcess.getSubstrateCompanyLot()
						.getSubstrateCustomerLot().getResLotType().getId() != substrateProcess.getSubstrateCompanyLot()
						.getSubstrateCustomerLot().getResLotType().getId())
				{
					return "substrate.mergCompanyLot.type";
				}
				if(!tempSubstrateProcess.getStatus().equals(substrateProcess.getStatus()))
				{
					return "substrate.mergCompanyLot.status";
				}
//				if(tempSubstrateProcess.getSubstrateCompanyLot()
//						.getSubstrateCustomerLot().getSubstratePart().getPartNo().equals(substrateProcess.getSubstrateCompanyLot()
//						.getSubstrateCustomerLot().getSubstratePart().getPartNo()))
//				{
//					return "substrate.mergCompanyLot.pon";
//				}
			}
			this.locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(substrateProcess.getSubstrateLocations()));
			tempSubstrateProcess = substrateProcess;
			dieQtyTotal += substrateProcess.getQtyIn();
			substrateQtyTotal += substrateProcess.getStripQtyIn();
			list.add(substrateProcess);
		}
		list = sortSubstrateProcess(list);
		nowSubstrateProcess = list.get(list.size() - 1);
		SubstrateCompanyLot nowSubstrateCompanyLot = nowSubstrateProcess
				.getSubstrateCompanyLot();
		mergeSubstrateCompanyLot.setSubstrateCustomerLot(nowSubstrateCompanyLot
				.getSubstrateCustomerLot());
		mergeSubstrateCompanyLot.setLotNo(nowSubstrateCompanyLot.getLotNo());
		mergeSubstrateCompanyLot
				.setParentSubstrateCompanyLot(nowSubstrateCompanyLot);
		mergeSubstrateCompanyLot.setCreateDate(substrateProcessDTO
				.getCreateDate());
		mergeSubstrateCompanyLot.setCreateUser(user);
		mergeSubstrateCompanyLot.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		mergeSubstrateCompanyLot.setLastModifyUser(user);
		mergeSubstrateCompanyLot.save();
		SubstrateProcess mergeSubstrateProcess = new SubstrateProcess();
		mergeSubstrateProcess.setSubstrateCompanyLot(mergeSubstrateCompanyLot);
		mergeSubstrateProcess.setShippingDate(nowSubstrateProcess
				.getShippingDate());
		mergeSubstrateProcess.setStatus(nowSubstrateProcess.getStatus());
		mergeSubstrateProcess.setStation(nowSubstrateProcess.getStation());
//		mergeSubstrateProcess
//				.setCreateDate(substrateProcessDTO.getCreateDate());
		mergeSubstrateProcess.setCreateDate(nowSubstrateProcess.getCreateDate());
		mergeSubstrateProcess.setCreateUser(user);
		mergeSubstrateProcess.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		mergeSubstrateProcess.setLastModifyUser(user);
		mergeSubstrateProcess.setQtyIn(dieQtyTotal);
		mergeSubstrateProcess.setStripQtyIn(substrateQtyTotal);
		mergeSubstrateProcess.setResLotType(nowSubstrateProcess.getResLotType());
		mergeSubstrateProcess.setSubstrateLocations(this.getLocations(substrateProcessDTO.getLocationIds()));
		mergeSubstrateProcess.save();
		this.locationApplication.updateLocationsSubstrateProcess(substrateProcessDTO.getLocationIds(), mergeSubstrateProcess.getId());
		nowSubstrateProcess.setStatus("Waiting");
		nowSubstrateProcess.save();
		for (SubstrateProcess substrateProcess : list) {
			SubstrateCompanyLot substrateCompanyLot = new SubstrateCompanyLot();
			substrateProcess.setStatus("Merge");
			substrateCompanyLot = substrateProcess.getSubstrateCompanyLot();
			substrateCompanyLot
					.setMergeSubstrateCompanyLot(mergeSubstrateCompanyLot);
			substrateCompanyLot.save();
			substrateProcess.save();
		}

		// 保存操作信息 --start
		SubstrateOptLog substrateMergeOptLog = new SubstrateOptLog();
		OptLog optLog = new OptLog();
		substrateMergeOptLog.setCreateDate(substrateProcessDTO.getCreateDate());
		substrateMergeOptLog.setCreateUser(user);
		substrateMergeOptLog.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		substrateMergeOptLog.setLastModifyUser(user);
		substrateMergeOptLog.setSubstrateProcess(nowSubstrateProcess);
		substrateMergeOptLog.setType("Merge");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		substrateMergeOptLog.setOptLog(optLog);
		substrateMergeOptLog.save();
		// 保存操作信息 --end
		return "success";
	}
	
	private List<SubstrateProcess> sortSubstrateProcess(List<SubstrateProcess> SubstrateProcesses) {
		Collections.sort(SubstrateProcesses, new Comparator<SubstrateProcess>() {
            public int compare(SubstrateProcess arg0, SubstrateProcess arg1) {
                return arg0.getQtyIn().compareTo(arg1.getQtyIn());
            }
        });
		return SubstrateProcesses;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateProcessDTO getMergeSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String mergeIds) {
//		SubstrateProcess nowSubstrateProcess = new SubstrateProcess();
		String[] idArray = mergeIds.split(",");
		List<SubstrateProcess> list = new ArrayList<SubstrateProcess>();
		int qtyIn = 0;
		int qtyTotal = 0;
		int stripQtyTotal = 0;
		for (String id : idArray) {
			SubstrateProcess substrateProcess = SubstrateProcess.get(SubstrateProcess.class,
					Long.parseLong(id));
			list.add(substrateProcess);
		}
			
//			if (qtyIn < substrateProcess.getQtyIn()) {
//				nowSubstrateProcess = substrateProcess;
//				qtyIn = substrateProcess.getQtyIn();
//			}
		SubstrateProcess nowSubstrateProcess = list.get(0);
		SubstrateProcess substrateProcess = list.get(1);
			if (nowSubstrateProcess.getSubstrateCompanyLot() == null
					|| nowSubstrateProcess.getSubstrateCompanyLot()
							.getSubstrateCustomerLot().getSubstratePart().getId() != substrateProcess
							.getSubstrateCompanyLot().getSubstrateCustomerLot()
							.getSubstratePart().getId()) {
				return null;
			}
			if(!(nowSubstrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().getBatchNo()
					.equals(substrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().getBatchNo())))
			{
				return null;
			}
			if(!(nowSubstrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().getCustomerLotNo()
					.equals(substrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().getCustomerLotNo())))
			{
				return null;
			}
			if(nowSubstrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().getResLotType().getId().intValue()
					!= substrateProcess.getSubstrateCompanyLot().getSubstrateCustomerLot().getResLotType().getId().intValue())
			{
				return null;
			}
			if(!(nowSubstrateProcess.getStatus().equals(substrateProcess.getStatus())))
			{
				return null;
			}
			qtyTotal = substrateProcess.getQtyIn() + nowSubstrateProcess.getQtyIn();
			stripQtyTotal = substrateProcess.getStripQtyIn() + nowSubstrateProcess.getStripQtyIn();
//			list.add(substrateProcess);
//		}

		SubstrateCompanyLot nowSubstrateCompanyLot = nowSubstrateProcess
				.getSubstrateCompanyLot();
		SubstrateCustomerLot substrateCustomerLot = nowSubstrateCompanyLot.getSubstrateCustomerLot();
		substrateProcessDTO.setVenderName(substrateCustomerLot.getVender().getVenderName());
		substrateProcessDTO.setLotNo(nowSubstrateProcess.getSubstrateCompanyLot().getLotNo());
		substrateProcessDTO.setCustomerLotNo(substrateCustomerLot.getCustomerLotNo());
		substrateProcessDTO.setPartNumber(substrateCustomerLot.getSubstratePart().getPartNo());
		substrateProcessDTO.setBatchNo(substrateCustomerLot.getBatchNo());
		substrateProcessDTO.setPon(substrateCustomerLot.getPon());
//		substrateProcessDTO.setEntryDate(substrateCustomerLot.getShippingDate());
//		substrateProcessDTO.setEntryTime(MyDateUtils.getDayHour(substrateCustomerLot.getShippingDate(), new Date()));
//		substrateProcessDTO.setEntryDate(nowSubstrateProcess.getShippingDate());
		substrateProcessDTO.setEntryDate(nowSubstrateProcess.getCreateDate());
		substrateProcessDTO.setEntryTime(MyDateUtils.getDayHour(substrateProcessDTO.getEntryDate(), new Date()));
		substrateProcessDTO.setUserName(substrateCustomerLot.getCreateUser().getName());
		substrateProcessDTO.setCurrStatus(nowSubstrateProcess.getStatus());
		substrateProcessDTO.setStockPos("");
		substrateProcessDTO.setCustomerLotNo(nowSubstrateCompanyLot.getSubstrateCustomerLot()
				.getCustomerLotNo());
		substrateProcessDTO.setPartNumber(nowSubstrateCompanyLot.getSubstrateCustomerLot()
				.getSubstratePart().getPartNo());
		substrateProcessDTO.setQtyIn(qtyTotal);
		substrateProcessDTO.setStripQtyIn(stripQtyTotal);
		substrateProcessDTO.setGuaranteePeriod(nowSubstrateCompanyLot.getSubstrateCustomerLot().getExpiryDate());
		substrateProcessDTO.setProductionDate(nowSubstrateCompanyLot.getSubstrateCustomerLot().getManufactureDate());
		substrateProcessDTO.setCurrStatus(nowSubstrateProcess.getStatus());
		substrateProcessDTO.setStatus(nowSubstrateProcess.getStatus());
		substrateProcessDTO.setLotType(nowSubstrateProcess.getResLotType().getIdentifier());
		return substrateProcessDTO;
	}

	public void holdSubstrateProcess(SubstrateProcessDTO substrateProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			substrateProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		} else
			substrateProcessDTO.setCurrStatus(holdCode);
		this.changeStatusSubstrateProcess(substrateProcessDTO, holdCode,holdCodeId,
				useraccount, comments);
	}

	public void engDispSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			substrateProcessDTO.setCurrStatus("Pass");
			holdCode = "Eng. Pass";
		} else
			substrateProcessDTO.setCurrStatus(holdCode);
		this.changeStatusSubstrateProcess(substrateProcessDTO, holdCode,holdCodeId,
				useraccount, comments);
		
	}
	
	public void changeLotType(SubstrateProcessDTO substrateProcessDTO, String lotType,
			String useraccount, String comments) {
		User user;
		if (substrateProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		SubstrateProcess substrateProcess = SubstrateProcess.get(SubstrateProcess.class,
				substrateProcessDTO.getId());
		Resource resource = Resource.load(Resource.class, Long.valueOf(lotType));
		substrateProcess.setResLotType(resource);
		substrateProcess.setLotType(resource.getIdentifier());
		substrateProcess.setLastModifyDate(substrateProcessDTO.getCreateDate());
		substrateProcess.setLastModifyUser(user);

		SubstrateChangeLotTypeOptLog substrateChangeLotTypeOptLog = new SubstrateChangeLotTypeOptLog();
		OptLog optLog = new OptLog();
		substrateChangeLotTypeOptLog.setCreateDate(substrateProcessDTO.getCreateDate());
		substrateChangeLotTypeOptLog.setCreateUser(user);
		substrateChangeLotTypeOptLog.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		substrateChangeLotTypeOptLog.setLastModifyUser(user);
		substrateChangeLotTypeOptLog.setSubstrateProcess(substrateProcess);
		substrateChangeLotTypeOptLog.setLotType(lotType);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		substrateChangeLotTypeOptLog.setOptLog(optLog);
		substrateChangeLotTypeOptLog.save();
	}

	public void changeLocations(SubstrateProcessDTO substrateProcessDTO,
			String locationIds, String useraccount, String comments) {
		User user;
		if (substrateProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(substrateProcessDTO.getUserDTO()
					.getUserAccount());
		Set<Location> locations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			locations.add(location);
		}
		SubstrateProcess substrateProcess = SubstrateProcess.get(SubstrateProcess.class,
				substrateProcessDTO.getId());
		for(Location loction : substrateProcess.getSubstrateLocations()){
			loction.setSubstrateProcess(null);
		}
		substrateProcess.setSubstrateLocations(locations);
		for (Location loc : locations) {
			loc.setSubstrateProcess(substrateProcess);
			loc.save();
		}
		substrateProcess.setLastModifyDate(substrateProcessDTO.getCreateDate());
		substrateProcess.setLastModifyUser(user);
		substrateProcess.save();
		SubstrateChangeLocationOptLog substrateChangeLocationOptLog = new SubstrateChangeLocationOptLog();
		OptLog optLog = new OptLog();
		substrateChangeLocationOptLog
				.setCreateDate(substrateProcessDTO.getCreateDate());
		substrateChangeLocationOptLog.setCreateUser(user);
		substrateChangeLocationOptLog.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		substrateChangeLocationOptLog.setLastModifyUser(user);
		substrateChangeLocationOptLog.setSubstrateProcess(substrateProcess);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		substrateChangeLocationOptLog.setOptLog(optLog);
		substrateChangeLocationOptLog.save();
	}

	public void futureHoldSubstrateProcess(SubstrateProcessDTO substrateProcessDTO,
			String holdCode, String holdCodeId, String useraccount, String comments) {
		substrateProcessDTO.setCurrStatus("Waiting");
		this.changeStatusSubstrateProcess(substrateProcessDTO, holdCode, holdCodeId, useraccount,
				comments);

	}
	
	public void trackIn(SubstrateProcessDTO substrateProcessDTO, String useraccount,
			String comments) {
		substrateProcessDTO.setCurrStatus("Running");
		this.changeStatusSubstrateProcess(substrateProcessDTO, "Running" , null, useraccount,
				comments);

		
	}

	public void abortStep(SubstrateProcessDTO substrateProcessDTO, String useraccount,
			String comments) {
		substrateProcessDTO.setCurrStatus("Waiting");
		substrateProcessDTO.setShippingDate(null);
		this.changeStatusSubstrateProcess(substrateProcessDTO, "Waiting" , null, useraccount,
				comments);
	}

	public SubstrateProcessDTO trackOut(SubstrateProcessDTO substrateProcessDTO) {
		return this.saveNextProcess(substrateProcessDTO);
	}
	
	private String getLocationsById(String locationIds) {
		// 处理库存位置
		Set<Location> locations = new HashSet<Location>();
		locations = this.getLocations(locationIds);
		return this.getLocationsByLocations(locations);
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

	private String getLocationsByLocations(Set<Location> locations) {
		// 处理库存位置
		String stockPos = "";
		for (Location location : locations) {
			stockPos += ";" + location.getLoctionCode();
		}
		if (stockPos.length() > 5) {
			stockPos = stockPos.substring(1);
		}
		return stockPos;
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
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateStatusOptLogDTO findSubstrateStatusOptLogByProcess(Long id) {
		String jpql = "select o from SubstrateStatusOptLog o inner join o.substrateProcess e where e.id=? order by o.createDate desc";
		SubstrateStatusOptLog result = (SubstrateStatusOptLog) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		SubstrateStatusOptLogDTO  dto = new SubstrateStatusOptLogDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setComments(result.getOptLog().getComments());
		}
		return dto;
	}
	
	private void saveOptLog(SubstrateProcessDTO substrateProcessDTO, SubstrateProcess nowSubstrateProcess,User user, String type){
		SubstrateOptLog substrateOptLog = new SubstrateOptLog();
		OptLog optLog = new OptLog();

		substrateOptLog.setCreateDate(substrateProcessDTO.getCreateDate());
		substrateOptLog.setCreateUser(user);
		substrateOptLog.setLastModifyDate(substrateProcessDTO
				.getLastModifyDate());
		substrateOptLog.setLastModifyUser(user);
		substrateOptLog.setSubstrateProcess(nowSubstrateProcess);
		substrateOptLog.setType(type);
		if(substrateProcessDTO.getComments() == null)
			optLog.setComments(type);
		else
			optLog.setComments(substrateProcessDTO.getComments());
		optLog.setRightUserUser(User.findByUserAccount(substrateProcessDTO.getUseraccount()));
		optLog.save();
		substrateOptLog.setOptLog(optLog);
		substrateOptLog.save();
	}
}
