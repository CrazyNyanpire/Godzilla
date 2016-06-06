package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.core.domain.ResourceLineAssignment;
import org.openkoala.koala.auth.core.domain.ResourceType;
import org.openkoala.koala.auth.core.domain.ResourceTypeAssignment;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;
import org.dayatang.utils.DateUtils;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;
import com.acetecsemi.godzilla.Godzilla.application.core.EquipmentApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.LocationApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class WaferProcessApplicationImpl implements WaferProcessApplication {

	private QueryChannelService queryChannel;

	@Inject
	private LocationApplication locationApplication;
	
	@Inject
	private EquipmentApplication equipmentApplication;

	@Inject
	private JpaTransactionManager transactionManager;
	Logger log = Logger.getLogger(WaferProcessApplicationImpl.class);

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferProcessDTO getWaferProcess(Long id) {
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, id);
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(waferProcessDTO, waferProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferProcessDTO.setCustomerCode(waferProcess.getWaferCompanyLot()
				.getWaferCustomerLot().getCustomer().getCustomerCode());
		waferProcessDTO.setLotNo(waferProcess.getWaferCompanyLot().getLotNo());
		waferProcessDTO.setCustomerLotNo(waferProcess.getWaferCompanyLot()
				.getWaferCustomerLot().getCustomerLotNo());
		waferProcessDTO.setPartNumber(waferProcess.getWaferCompanyLot()
				.getWaferCustomerLot().getPart().getPartNo());
		waferProcessDTO.setProductName(waferProcess.getWaferCompanyLot()
				.getWaferCustomerLot().getProduct().getProductName());
		waferProcessDTO.setCustomerOrder(waferProcess.getWaferCompanyLot()
				.getWaferCustomerLot().getCustomerOrder());
		waferProcessDTO.setOptUser(waferProcess.getLastModifyUser()
				.getUserAccount());
		waferProcessDTO.setCurrStatus(waferProcess.getStatus());
		if("Waiting".equals(waferProcess.getStatus())){
			waferProcessDTO.setEntryDate(waferProcess.getCreateDate());
		}else
			waferProcessDTO.setEntryDate(waferProcess.getShippingDate());
		waferProcessDTO.setEntryTime(MyDateUtils.getDayHour(
				waferProcessDTO.getEntryDate(), new Date()));
		waferProcessDTO.setStockPos(this.getLocationsByLocations(waferProcess
				.getLocations()));
		waferProcessDTO
				.setLotType(waferProcess.getResLotType().getIdentifier());
		waferProcessDTO.setStep(waferProcess.getStation().getStationNameEn());
		waferProcessDTO.setId((java.lang.Long) waferProcess.getId());
		if (waferProcess.getEquipment() != null) {
			waferProcessDTO.setEquipment(waferProcess.getEquipment()
					.getEquipment());
		}
		if(waferProcess.getRejectStation() != null){
			waferProcessDTO.setStep(waferProcess.getRejectStation().getStationName());
		}
		return waferProcessDTO;
	}

	public WaferProcessDTO saveWaferProcess(WaferProcessDTO waferProcessDTO) {
		WaferProcess waferProcess = new WaferProcess();
		try {
			BeanUtilsExtends.copyProperties(waferProcess, waferProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferProcess.save();
		waferProcessDTO.setId((java.lang.Long) waferProcess.getId());
		return waferProcessDTO;
	}

	public void updateWaferProcess(WaferProcessDTO waferProcessDTO) {
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtilsExtends.copyProperties(waferProcess, waferProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeWaferProcess(Long id) {
		this.removeWaferProcesss(new Long[] { id });
	}

	public void removeWaferProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferProcess waferProcess = WaferProcess.load(WaferProcess.class,
					ids[i]);
			waferProcess.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferProcessDTO> findAllWaferProcess() {
		List<WaferProcessDTO> list = new ArrayList<WaferProcessDTO>();
		List<WaferProcess> all = WaferProcess.findAll(WaferProcess.class);
		for (WaferProcess waferProcess : all) {
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferProcessDTO, waferProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferProcessDTO);
		}
		return list;
	}

	public void updateWaferProcessDTODelayTime(List<WaferProcessDTO> list,int batchSize) 
	{
		EntityManager em = transactionManager.getEntityManagerFactory().createEntityManager();
		Date date = new Date();
		int i = 0;
		em.getTransaction().begin();
//		Query q = null;
		for(WaferProcessDTO waferProcessDTO:list){
			waferProcessDTO.setDelayTime(DateUtils.getDayDiff(waferProcessDTO.getShippingDate(), date));
			Query q = em.createQuery("update WaferProcess m set m.delayTime=:delayTime where m.id=:id");
			q.setParameter("delayTime", waferProcessDTO.getDelayTime());
			q.setParameter("id", waferProcessDTO.getId());
			q.executeUpdate();
			i++;
			if(i % batchSize == 0)
			{
				em.flush();
				em.clear();
				em.getTransaction().commit();
			}
			
		}
		em.close();
		em = null;
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferProcessDTO> pageQueryWaferProcess(WaferProcessDTO queryVo,
			int currentPage, int pageSize) {
		List<WaferProcessDTO> result = new ArrayList<WaferProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _waferProcess from WaferProcess _waferProcess   left join _waferProcess.createUser  left join _waferProcess.lastModifyUser  left join _waferProcess.station  left join _waferProcess.equipment  left join _waferProcess.waferCompanyLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _waferProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _waferProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _waferProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _waferProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}
		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _waferProcess.status in (" + queryVo.getStatus()
					+ ")");
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _waferProcess.status in ("
					+ queryVo.getCurrStatus() + ")");
		}
		if (queryVo.getSort() != null) {
			jpql.append(" and _waferProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _waferProcess.resLotType.id in ("
					+ queryVo.getLotType() + ")");
		}

		if (queryVo.getCustomerCode() != null
				&& !"".equals(queryVo.getCustomerCode())) {
			jpql.append(" and _waferProcess.waferCompanyLot.waferCustomerLot.customer.customerCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerCode()));
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _waferProcess.waferCompanyLot.waferCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _waferProcess.waferCompanyLot.waferCustomerLot.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _waferProcess.waferCompanyLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		if (queryVo.getDelayTime() != null) {
			jpql.append(" and _waferProcess.delayTime=?");
			conditionVals.add(queryVo.getDelayTime());
		}

		if (queryVo.getStationId() != null) {
			jpql.append(" and _waferProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and  not(_waferProcess.status=?)");
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		conditionVals.add(finish.getName());
		jpql.append(" and _waferProcess.status not in ("
				+ StaticValue.NOT_SHOW_STATUS + ") ");
		jpql.append(" order by _waferProcess.shippingDate DESC");
		Page<WaferProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		int waferStatusId = 0;
		for (WaferProcess waferProcess : pages.getData()) {
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();

			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferProcessDTO, waferProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}

			waferProcessDTO.setCustomerCode(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getCustomer().getCustomerCode());
			waferProcessDTO.setLotNo(waferProcess.getWaferCompanyLot()
					.getLotNo());
			waferProcessDTO.setCustomerLotNo(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getCustomerLotNo());
			waferProcessDTO.setPartNumber(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getPart().getPartNo());
			waferProcessDTO.setProductName(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getProduct().getProductName());
			waferProcessDTO.setCustomerOrder(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getCustomerOrder());
			waferProcessDTO.setOptUser(waferProcess.getLastModifyUser()
					.getUserAccount());
			waferProcessDTO.setCurrStatus(waferProcess.getStatus());
			if("Waiting".equals(waferProcess.getStatus())){
				waferProcessDTO.setEntryDate(waferProcess.getCreateDate());
			}else
				waferProcessDTO.setEntryDate(waferProcess.getShippingDate());
			waferProcessDTO.setEntryTime(MyDateUtils.getDayHour(
					waferProcessDTO.getEntryDate(), new Date()));
			waferProcessDTO.setLotType(waferProcess.getResLotType()
					.getIdentifier());
			waferProcessDTO.setStep(waferProcess.getStation()
					.getStationNameEn());
//			waferProcessDTO.setStep(waferProcess.getStation()
//					.getStationNameEn());
			waferProcessDTO.setStockPos(this
					.getLocationsByLocations(waferProcess.getLocations()));
			waferProcessDTO.setProductionDate(waferProcess.getWaferCompanyLot().getWaferCustomerLot().getManufactureDate());
			waferProcessDTO.setGuaranteePeriod(waferProcess.getWaferCompanyLot().getWaferCustomerLot().getExpiryDate());
			waferStatusId = waferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId() == null? 0:1;
			waferProcessDTO.setWaferStatusId(waferStatusId);
			if(waferStatusId == 1)
			{
				waferProcessDTO.setWaferStatusName("CP PASS");
			}
			else
			{
				waferProcessDTO.setWaferStatusName("Pending CP");
			}
			if (waferProcess.getEquipment() != null) {
				waferProcessDTO.setEquipment(waferProcess.getEquipment()
						.getEquipment());
			}
			if(waferProcess.getRejectStation() != null){
				waferProcessDTO.setRejectStation(waferProcess.getRejectStation().getStationNameEn());
			}
			List<RejectCodeItemDTO> rejectCodeItemDTOs = new ArrayList<RejectCodeItemDTO>();
			for(RejectCodeItem rejectCodeitem :	waferProcess.getRejectCodeItems()){
				if(rejectCodeitem.getQty() > 0){
					RejectCodeItemDTO rejectCodeItemDTO = new RejectCodeItemDTO();
					try {
						BeanUtilsExtends.copyProperties(rejectCodeItemDTO, rejectCodeitem);
					} catch (Exception e) {
						e.printStackTrace();
					}
					rejectCodeItemDTOs.add(rejectCodeItemDTO);
				}
			}
			waferProcessDTO.setRejectCodeItemDTOs(rejectCodeItemDTOs);
			WaferStatusOptLogDTO waferStatusOptLogDTO = this.findWaferStatusOptLogByProcess(waferProcess.getId(), false);
			if(waferStatusOptLogDTO != null){
				waferProcessDTO.setHoldReason(waferStatusOptLogDTO.getComments());
			}
			result.add(waferProcessDTO);
		}
		return new Page<WaferProcessDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Object> pageQueryWaferProcessTotal(
			WaferProcessDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_waferProcess.qtyIn),sum(_waferProcess.waferQtyIn),sum(_waferProcess.stripQtyIn),count(*) from WaferProcess _waferProcess   left join _waferProcess.createUser  left join _waferProcess.lastModifyUser  left join _waferProcess.station  left join _waferProcess.equipment  left join _waferProcess.waferCompanyLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _waferProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _waferProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _waferProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _waferProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}
		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _waferProcess.status in (" + queryVo.getStatus()
					+ ")");
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _waferProcess.status in ("
					+ queryVo.getCurrStatus() + ")");
		}
		if (queryVo.getSort() != null) {
			jpql.append(" and _waferProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _waferProcess.resLotType.id in ("
					+ queryVo.getLotType() + ")");
		}

		if (queryVo.getCustomerCode() != null
				&& !"".equals(queryVo.getCustomerCode())) {
			jpql.append(" and _waferProcess.waferCompanyLot.waferCustomerLot.customer.customerCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerCode()));
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _waferProcess.waferCompanyLot.waferCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _waferProcess.waferCompanyLot.waferCustomerLot.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _waferProcess.waferCompanyLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		if (queryVo.getDelayTime() != null) {
			jpql.append(" and _waferProcess.delayTime=?");
			conditionVals.add(queryVo.getDelayTime());
		}

		if (queryVo.getStationId() != null) {
			jpql.append(" and _waferProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and  not(_waferProcess.status=?)");
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		conditionVals.add(finish.getName());
		jpql.append(" and _waferProcess.status not in ("
				+ StaticValue.NOT_SHOW_STATUS + ") ");
		jpql.append(" order by _waferProcess.shippingDate DESC");
		Object[] object = (Object[]) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dieTotal", String.valueOf(object[0]));
		resultMap.put("waferTotal", String.valueOf(object[1]));
		resultMap.put("stripTotal", String.valueOf(object[2]));
		resultMap.put("countLot", String.valueOf(object[3]));
		return resultMap;

	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByWaferProcess(Long id) {
		String jpql = "select e from WaferProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByWaferProcess(Long id) {
		String jpql = "select e from WaferProcess o right join o.lastModifyUser e where o.id=?";
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
	public StationDTO findStationByWaferProcess(Long id) {
		String jpql = "select e from WaferProcess o right join o.station e where o.id=?";
		Station result = (Station) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		StationDTO dto = new StationDTO();
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
	public UserDTO findOptUserByWaferProcess(Long id) {
		String jpql = "select e from WaferProcess o right join o.optUser e where o.id=?";
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
	public Page<LocationDTO> findLocationsByWaferProcess(Long id,
			int currentPage, int pageSize) {
		List<LocationDTO> result = new ArrayList<LocationDTO>();
		String jpql = "select e from WaferProcess o right join o.locations e where o.id=?";
		Page<Location> pages = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (Location entity : pages.getData()) {
			LocationDTO dto = new LocationDTO();
			try {
				BeanUtilsExtends.copyProperties(dto, entity);
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
	public EquipmentDTO findEquipmentByWaferProcess(Long id) {
		String jpql = "select e from WaferProcess o right join o.equipment e where o.id=?";
		Equipment result = (Equipment) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		EquipmentDTO dto = new EquipmentDTO();
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
	public WaferCompanyLotDTO findWaferCompanyLotByWaferProcess(Long id) {
		String jpql = "select e from WaferProcess o right join o.waferCompanyLot e where o.id=?";
		WaferCompanyLot result = (WaferCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		WaferCompanyLotDTO dto = new WaferCompanyLotDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public WaferProcessDTO saveWaferProcessReceive(
			WaferProcessDTO waferProcessDTO, Long waferCustomerLotId,
			String locationIds, List<WaferInfoDTO> waferInfoList) {
		WaferProcess waferProcess = new WaferProcess();
		String jpql = "select o from WaferCompanyLot o left join o.waferCustomerLot e where e.id=?";
		WaferCompanyLot waferCompanyLot = (WaferCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { waferCustomerLotId })
				.singleResult();
		Set<Location> locations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			locations.add(location);
		}
		try {
			BeanUtilsExtends.copyProperties(waferProcess, waferProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		waferProcess.setCreateUser(user);
		waferProcess.setLastModifyUser(user);
		waferProcess.setWaferCompanyLot(waferCompanyLot);
		waferProcess.setLocations(locations);
		waferProcess.setQtyIn(waferCompanyLot.getWaferCustomerLot().getQty());
		waferProcess.setWaferQtyIn(waferCompanyLot.getWaferCustomerLot()
				.getWafer());
		waferProcess.setQtyOut(waferCompanyLot.getWaferCustomerLot().getQty());
		waferProcess.setWaferQtyOut(waferCompanyLot.getWaferCustomerLot()
				.getWafer());
		DefineStationProcess defineStationProcess = waferCompanyLot
				.getWaferCustomerLot().getDefineStationProcess();
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.load(
					DefineStationProcess.class, Long.valueOf(1));
		}
		Set<Station> stations = defineStationProcess.getStations();
		waferProcess.setStation(stations.iterator().next());
		waferProcess.setStatus("Waiting");
		waferProcess.setResLotType(waferCompanyLot.getWaferCustomerLot()
				.getResLotType());
		waferProcess.save();
		WaferCustomerLot waferCustomerLot = waferCompanyLot
				.getWaferCustomerLot();
		waferCustomerLot.setCurrStatus("Received");
		waferCustomerLot.save();
		for (Location loc : locations) {
			loc.setWaferProcess(waferProcess);
			loc.save();
		}
		Set<WaferInfo> waferInfos = new HashSet<WaferInfo>();
		for (WaferInfoDTO waferInfoDTO : waferInfoList) {
			WaferInfo waferInfo = new WaferInfo();
			try {
				BeanUtils.copyProperties(waferInfo, waferInfoDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
			waferInfo.setDieInitQty(waferInfoDTO.getDieQty());
			waferInfo.setWaferCustomerLot(waferCustomerLot);
			waferInfo.save();
			waferInfos.add(waferInfo);
		}
		waferCompanyLot.setWaferInfos(waferInfos);
		waferCompanyLot.save();
		waferProcessDTO.setId((java.lang.Long) waferProcess.getId());
		this.saveOptLog(waferProcessDTO, waferProcess, user, "Received");
		return waferProcessDTO;
	}

	public WaferProcessDTO saveNextProcess(WaferProcessDTO waferProcessDTO) {
		WaferProcess nextWaferProcess = new WaferProcess();
		WaferProcess nowWaferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		if (nowWaferProcess.getEquipment() != null) {
			Equipment equipment = nowWaferProcess.getEquipment();
			equipment.setStatus("IDLE");
			equipment.save();
		}
		User user;
		if (waferProcessDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		Set<WaferInfo> waferInfos = new HashSet<WaferInfo>();
		if(waferProcessDTO.getWaferInfoDTOs() != null){
			for(WaferInfoDTO waferInfoDTO : waferProcessDTO.getWaferInfoDTOs()){
				WaferInfo waferInfo = WaferInfo.load(WaferInfo.class, waferInfoDTO.getId());
				waferInfo.setDieQty(waferInfoDTO.getDieQty());
				waferInfo.setLastModifyDate(waferProcessDTO.getLastModifyDate());
				waferInfo.save();
				//waferInfos.add(waferInfo);
				WaferInfo waferInfoScraps = new WaferInfo();
				waferInfoScraps.setWaferNumber(waferInfo.getWaferNumber());
				waferInfoScraps.setWaferId(waferInfo.getWaferId());
				waferInfoScraps.setDieQty(waferInfoDTO.getScrapsQty());
				waferInfoScraps.setDieInitQty(waferInfo.getDieInitQty());
				waferInfoScraps.setWaferCustomerLot(waferInfo.getWaferCustomerLot());
				waferInfoScraps.setCreateDate(new Date());
				waferInfoScraps.setCreateUser(user);
				waferInfoScraps.setLastModifyDate(waferProcessDTO.getLastModifyDate());
				waferInfoScraps.save();
				waferInfos.add(waferInfoScraps);
			}
		}
		WaferCompanyLot waferCompanyLot = nowWaferProcess.getWaferCompanyLot();
		Set<Location> locations = new HashSet<Location>();

		nextWaferProcess.setCreateDate(waferProcessDTO.getCreateDate());
		nextWaferProcess.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		nextWaferProcess.setCreateUser(user);
		nextWaferProcess.setLastModifyUser(user);
		nextWaferProcess.setShippingDate(waferProcessDTO.getShippingDate());
		nextWaferProcess.setWaferCompanyLot(waferCompanyLot);
		if (waferProcessDTO.getStartFlow() != null) {
			DefineStationProcess defineStationProcess = DefineStationProcess
					.load(DefineStationProcess.class,
							waferProcessDTO.getStartFlow());
			nextWaferProcess.getWaferCompanyLot().getWaferCustomerLot()
					.setDefineStationProcess(defineStationProcess);
		}
		nextWaferProcess.setLocations(locations);
		if(nowWaferProcess.getResHold() != null){
			nextWaferProcess.setResHold(nowWaferProcess.getResHold());
		}
		if (waferProcessDTO.getScrapsQtyOut() != null) {
			nowWaferProcess.setScrapsQtyOut(waferProcessDTO.getScrapsQtyOut());
		} else {
			nowWaferProcess.setScrapsQtyOut(0);
		}
		if (waferProcessDTO.getGoodQtyOut() != null) {
			nowWaferProcess.setQtyOut(waferProcessDTO.getGoodQtyOut());
		} else {
			nowWaferProcess.setQtyOut(nowWaferProcess.getQtyIn());
		}
		if (waferProcessDTO.getStripQtyOut() != null
				&& waferProcessDTO.getStripQtyOut() > 0) {
			nowWaferProcess.setStripQtyOut(waferProcessDTO.getStripQtyOut());
		} else if (nowWaferProcess.getStripQtyIn() != null) {
			nowWaferProcess.setStripQtyOut(nowWaferProcess.getStripQtyIn());
		}
		if (waferProcessDTO.getWaferQtyOut() != null
				&& waferProcessDTO.getWaferQtyOut() > 0) {
			nowWaferProcess.setWaferQtyOut(waferProcessDTO.getWaferQtyOut());
		} else {
			nowWaferProcess.setWaferQtyOut(nowWaferProcess.getWaferQtyIn());
		}
		Set<RejectCodeItem> rejectCodeItems = this
				.saveRejectCodeItems(waferProcessDTO.getRejectCodeItemDTOs());
		nowWaferProcess.setRejectCodeItems(rejectCodeItems);
		nextWaferProcess.setQtyIn(nowWaferProcess.getQtyOut());
		nextWaferProcess.setWaferQtyIn(nowWaferProcess.getWaferQtyOut());
		nextWaferProcess.setQtyOut(nowWaferProcess.getQtyOut());
		nextWaferProcess.setWaferQtyOut(nowWaferProcess.getWaferQtyOut());
		DefineStationProcess defineStationProcess = waferCompanyLot
				.getWaferCustomerLot().getDefineStationProcess();

		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.load(
					DefineStationProcess.class, Long.valueOf(1));
		}
		Station nextStation = this.getNextStation(defineStationProcess,
				nowWaferProcess);
		if(waferProcessDTO.getNextStationId() != null){
			nextStation = Station.load(Station.class, waferProcessDTO.getNextStationId());
		}
		if (nextStation == null) {
			return null;
		}
		nextWaferProcess.setStation(nextStation);
		Resource resource;
		if(nowWaferProcess.getFutureStation() != null){
			nextWaferProcess.setFutureStation(nowWaferProcess.getFutureStation());
		}
		if (nowWaferProcess.getFutureStation() != null
				&& nextStation.getId().equals(
						nowWaferProcess.getWaferCompanyLot()
								.getFutureHoldStation().getId())) {
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment
					.findRelationByResource(Long.valueOf(nowWaferProcess.getResHold().getId())).get(0);
			resource = rsourceLineAssignment.getParent();
			nextWaferProcess.setFutureStation(null);
		} else {
			if(waferProcessDTO.getCurrStatus() != null){
				resource = Resource.findByProperty(Resource.class, "identifier",
						waferProcessDTO.getCurrStatus()).get(0);
			}else{
				resource = Resource.findByProperty(Resource.class, "identifier",
						"Waiting").get(0);
			}
		}


		nextWaferProcess.setStatus(resource.getName());
		nextWaferProcess.setResLotType(nowWaferProcess.getResLotType());
		nextWaferProcess.save();
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		nowWaferProcess.setStatus(finish.getName());
		nowWaferProcess.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		nowWaferProcess.setLastModifyUser(user);
		nowWaferProcess.save();
		locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(nowWaferProcess.getLocations()));
		if (nowWaferProcess.getScrapsQtyOut() > 0) {
			this.splitScrapsLot(rejectCodeItems, nowWaferProcess,
					waferProcessDTO, user,waferInfos);
		}
		if(nextWaferProcess.getFutureStation() != null){
			this.saveFutureOpt(nextWaferProcess, nowWaferProcess.getId());
		}
		this.saveOptLog(waferProcessDTO, nowWaferProcess, user, "saveNextProcess");
		waferProcessDTO.setId((java.lang.Long) nextWaferProcess.getId());
		return waferProcessDTO;
	}

	private void saveFutureOpt(WaferProcess nextWaferProcess, Long nowManufactureProcessId){
		WaferStatusOptLogDTO waferStatusOptLogDTO = this.findWaferStatusOptLogByProcess(nowManufactureProcessId, true);
		WaferStatusOptLog waferStatusOptLog = WaferStatusOptLog.get(WaferStatusOptLog.class, waferStatusOptLogDTO.getId());
		WaferStatusOptLog nextWaferStatusOptLog = new WaferStatusOptLog();
		nextWaferStatusOptLog.setCreateDate(waferStatusOptLog.getCreateDate());
		nextWaferStatusOptLog.setLastModifyDate(waferStatusOptLog.getLastModifyDate());
		nextWaferStatusOptLog.setCreateUser(waferStatusOptLog.getCreateUser());
		nextWaferStatusOptLog.setLastModifyUser(waferStatusOptLog.getLastModifyUser());
		nextWaferStatusOptLog.setFutureStation(waferStatusOptLog.getFutureStation());
		nextWaferStatusOptLog.setOptLog(waferStatusOptLog.getOptLog());
		nextWaferStatusOptLog.setHoldCode(waferStatusOptLog.getHoldCode());
		nextWaferStatusOptLog.setWaferProcess(nextWaferProcess);
		nextWaferStatusOptLog.setEngineerName(waferStatusOptLog.getEngineerName());
		nextWaferStatusOptLog.save();
	}
	
	private Station getNextStation(DefineStationProcess defineStationProcess,
			WaferProcess nowWaferProcess) {
		Set<Station> stations = defineStationProcess.getStations();
		Station nextStation = null;
		for (Station station : stations) {
			if (station.getSequence() > nowWaferProcess.getStation()
					.getSequence()) {
				nextStation = station;
				break;
			}
		}
		return nextStation;
	}

	private void splitScrapsLot(Set<RejectCodeItem> rejectCodeItems,
			WaferProcess nowWaferProcess, WaferProcessDTO waferProcessDTO,
			User user,Set<WaferInfo> waferInfos) {
		WaferCompanyLot waferCompanyLot = nowWaferProcess
				.getWaferCompanyLot();
		WaferCompanyLot splitWaferCompanyLot = new WaferCompanyLot();
		splitWaferCompanyLot.setWaferCustomerLot(waferCompanyLot
				.getWaferCustomerLot());
		List<String> lotNoList = this.getSplitLotNo(nowWaferProcess
				.getWaferCompanyLot().getLotNo(), 2);
		splitWaferCompanyLot.setLotNo(lotNoList.get(1));
		splitWaferCompanyLot.setParentWaferCompanyLot(waferCompanyLot);
		splitWaferCompanyLot.setCreateDate(waferProcessDTO.getCreateDate());
		splitWaferCompanyLot.setCreateUser(user);
		splitWaferCompanyLot.setLastModifyDate(waferProcessDTO
				.getLastModifyDate());
		splitWaferCompanyLot.setLastModifyUser(user);
		splitWaferCompanyLot.setWaferInfos(waferInfos);
		splitWaferCompanyLot.setFromWaferProcess(nowWaferProcess);
		splitWaferCompanyLot.save();
		WaferProcess splitWaferProcess = new WaferProcess();
		splitWaferProcess.setRejectStation(nowWaferProcess.getStation());
		splitWaferProcess.setWaferCompanyLot(splitWaferCompanyLot);
		splitWaferProcess.setRejectCodeItems(rejectCodeItems);
		splitWaferProcess.setQtyIn(nowWaferProcess.getScrapsQtyOut());
		splitWaferProcess.setQtyOut(nowWaferProcess.getScrapsQtyOut());
		splitWaferProcess.setWaferQtyIn(nowWaferProcess.getWaferQtyOut());
		splitWaferProcess.setWaferQtyOut(nowWaferProcess.getWaferQtyOut());
		splitWaferProcess.setShippingDate(nowWaferProcess.getShippingDate());
		Resource resource = Resource.findByProperty(Resource.class,
				"identifier", "Waiting").get(0);
		splitWaferProcess.setStatus(resource.getName());
		splitWaferProcess.setStation(nowWaferProcess.getStation());
		splitWaferProcess.setCreateDate(waferProcessDTO.getCreateDate());
		splitWaferProcess.setCreateUser(user);
		splitWaferProcess
				.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		splitWaferProcess.setResLotType(nowWaferProcess.getResLotType());
		splitWaferProcess.setLastModifyUser(user);
		Station station = Station.load(Station.class, (long) 1003);
		splitWaferProcess.setStation(station);
		splitWaferProcess.save();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Set<RejectCodeItem> saveRejectCodeItems(
			List<RejectCodeItemDTO> rejectCodeItemDTOs) {
		Set<RejectCodeItem> rejectCodeItems = new HashSet<RejectCodeItem>();
		if (rejectCodeItemDTOs != null && rejectCodeItemDTOs.size() > 0) {
			for (RejectCodeItemDTO rejectCodeItemDTO : rejectCodeItemDTOs) {
				RejectCodeItem rejectCodeItem = new RejectCodeItem();
				try {
					BeanUtils.copyProperties(rejectCodeItem, rejectCodeItemDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// rejectCodeItem.setWaferProcess(nowWaferProcess);
				rejectCodeItem.save();
				rejectCodeItems.add(rejectCodeItem);
			}
			// nowWaferProcess.setRejectCodeItems(rejectCodeItems);
		}
		return rejectCodeItems;
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
	
	private List<WaferProcess> sortWaferProcess(List<WaferProcess> waferProcesss) {
		Collections.sort(waferProcesss, new Comparator<WaferProcess>() {
            public int compare(WaferProcess arg0, WaferProcess arg1) {
                return arg0.getQtyIn().compareTo(arg1.getQtyIn());
            }
        });
		return waferProcesss;
	}

	public String saveSplitWaferProcess(WaferProcessDTO waferProcessDTO,
			String useraccount, Integer waferQty, Integer dieQty,
			String comments, String locationIds, List<String> waferIds) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批
		WaferProcess nowWaferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		if(nowWaferProcess.getWaferQtyIn() == 1)
		{
			return "wafer.trackout.waferQtyEqualZero";
		}
		WaferCompanyLot waferCompanyLot = nowWaferProcess.getWaferCompanyLot();
		List<String> lotNoList = this.getSplitLotNo(nowWaferProcess
				.getWaferCompanyLot().getLotNo(), 2);
		List<WaferInfo> waferInfos = new ArrayList<WaferInfo>();
		boolean waferInfoSplitSign = false;
		if (waferIds.size() > 0) {
			waferInfoSplitSign = true;
			waferInfos = this.findWaferInfoByWaferIds(waferIds);
		}
		Set<WaferInfo> nowWaferInfosSet = new HashSet<WaferInfo>();
		Set<WaferInfo> nowWaferInfos = waferCompanyLot.getWaferInfos();
		for (int i = 0; i < lotNoList.size(); i++) {
			WaferCompanyLot splitWaferCompanyLot = new WaferCompanyLot();
			splitWaferCompanyLot.setWaferCustomerLot(waferCompanyLot
					.getWaferCustomerLot());
			splitWaferCompanyLot.setLotNo(lotNoList.get(i));
			splitWaferCompanyLot.setParentWaferCompanyLot(waferCompanyLot);
//			splitWaferCompanyLot.setCreateDate(waferProcessDTO.getCreateDate());
			splitWaferCompanyLot.setCreateDate(nowWaferProcess.getCreateDate());
			splitWaferCompanyLot.setCreateUser(user);
			splitWaferCompanyLot.setLastModifyDate(waferProcessDTO
					.getLastModifyDate());
			splitWaferCompanyLot.setLastModifyUser(user);
			WaferProcess splitWaferProcess = new WaferProcess();
			splitWaferProcess.setWaferCompanyLot(splitWaferCompanyLot);
			String tempLocationIds;
			if (i == 0) {
				splitWaferProcess.setQtyIn(nowWaferProcess.getQtyIn() - dieQty);
				splitWaferProcess.setQtyOut(splitWaferProcess.getQtyIn());
				splitWaferProcess.setWaferQtyIn(nowWaferProcess.getWaferQtyIn()
						- waferQty);
				splitWaferProcess.setWaferQtyOut(splitWaferProcess.getQtyOut());
				tempLocationIds = this.getLocationIdsByLocations(nowWaferProcess.getLocations());
				// 晶圆片分批
				if (waferInfoSplitSign) {
					nowWaferInfos.removeAll(waferInfos);
					Set<WaferInfo> splitWaferInfos = new HashSet<WaferInfo>();
					for (WaferInfo waferInfo : nowWaferInfos) {
						WaferInfo wi = new WaferInfo();
						wi = WaferInfo.load(WaferInfo.class, waferInfo.getId());
						splitWaferInfos.add(wi);
					}
					splitWaferCompanyLot.setWaferInfos(splitWaferInfos);
				}

			} else {
				splitWaferProcess.setQtyIn(dieQty);
				splitWaferProcess.setQtyOut(splitWaferProcess.getQtyIn());
				splitWaferProcess.setWaferQtyIn(waferQty);
				splitWaferProcess.setWaferQtyOut(splitWaferProcess.getQtyOut());
				//splitWaferProcess.setLocations(this.getLocations(locationIds));
				// 晶圆片分批
				if (waferInfoSplitSign) {
					nowWaferInfosSet.addAll(waferInfos);
					splitWaferCompanyLot.setWaferInfos(nowWaferInfosSet);
				}
				tempLocationIds = locationIds;
			}
			splitWaferCompanyLot.save();
			splitWaferProcess
					.setShippingDate(nowWaferProcess.getShippingDate());
			splitWaferProcess.setStatus(nowWaferProcess.getStatus());
			splitWaferProcess.setStation(nowWaferProcess.getStation());
			splitWaferProcess.setCreateDate(nowWaferProcess.getCreateDate());
			splitWaferProcess.setCreateUser(user);
			splitWaferProcess.setLastModifyDate(waferProcessDTO
					.getLastModifyDate());
			splitWaferProcess.setShippingDate(nowWaferProcess.getShippingDate());
			splitWaferProcess.setResLotType(nowWaferProcess.getResLotType());
			splitWaferProcess.setLastModifyUser(user);
			splitWaferProcess.save();
			this.locationApplication.updateLocationsWaferProcess(tempLocationIds, splitWaferProcess.getId());
		}
		nowWaferProcess.setStatus("Split");
		nowWaferProcess.save();
		// 保存操作信息 --start
		WaferOptLog waferSplitOptLog = new WaferOptLog();
		OptLog optLog = new OptLog();
		waferSplitOptLog.setCreateDate(waferProcessDTO.getCreateDate());
		waferSplitOptLog.setCreateUser(user);
		waferSplitOptLog.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		waferSplitOptLog.setLastModifyUser(user);
		waferSplitOptLog.setWaferQty(waferQty);
		waferSplitOptLog.setDieQty(dieQty);
		waferSplitOptLog.setWaferProcess(nowWaferProcess);
		waferSplitOptLog.setType("Split");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		waferSplitOptLog.setOptLog(optLog);
		waferSplitOptLog.save();
		// 保存操作信息 --end
		return "success";
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferProcessDTO> getSplitWaferProcess(
			WaferProcessDTO waferProcessDTO, Integer waferQty, Integer dieQty) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批
		WaferProcess nowWaferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		List<String> lotNoList = this.getSplitLotNo(nowWaferProcess
				.getWaferCompanyLot().getLotNo(), 2);
		List<WaferProcessDTO> list = new ArrayList<WaferProcessDTO>();
		for (int i = 0; i < lotNoList.size(); i++) {
			WaferProcessDTO wpDTO = new WaferProcessDTO();
			WaferCompanyLot nowWaferCompanyLot = nowWaferProcess
					.getWaferCompanyLot();
			wpDTO.setCustomerCode(nowWaferCompanyLot.getWaferCustomerLot()
					.getCustomer().getCustomerCode());
			wpDTO.setLotNo(lotNoList.get(i));
			wpDTO.setCustomerLotNo(nowWaferCompanyLot.getWaferCustomerLot()
					.getCustomerLotNo());
			wpDTO.setPartNumber(nowWaferCompanyLot.getWaferCustomerLot()
					.getPart().getPartNo());
			if (i == 0) {
				wpDTO.setQtyIn(nowWaferProcess.getQtyIn() - dieQty);
				wpDTO.setWaferQtyIn(nowWaferProcess.getWaferQtyIn() - waferQty);
				wpDTO.setStockPos(this.getLocationsByLocations(nowWaferProcess
						.getLocations()));
			} else {
				wpDTO.setQtyIn(dieQty);
				wpDTO.setWaferQtyIn(waferQty);
				wpDTO.setStockPos(this.getLocationsById(waferProcessDTO
						.getLocationIds()));
			}
			wpDTO.setCustomerOrder(nowWaferCompanyLot.getWaferCustomerLot()
					.getCustomerOrder());
			wpDTO.setOptUser(user.getUserAccount());
			wpDTO.setCurrStatus(nowWaferProcess.getStatus());
			wpDTO.setStatus(nowWaferProcess.getStatus());
			//wpDTO.setEntryDate(new Date());
			wpDTO.setEntryDate(nowWaferProcess.getCreateDate());
			wpDTO.setEntryTime(MyDateUtils.getDayHour(
					wpDTO.getEntryDate(), new Date()));
			wpDTO.setLotType(nowWaferProcess.getResLotType().getIdentifier());
			list.add(wpDTO);
		}

		return list;
	}

	private List<String> getLotNoList(String lotNo, int lot) {
		String[] str = lotNo.split("-");// WA00001-01
		List<String> lotList = new ArrayList<String>();
		int n = Integer.parseInt(str[1]);
		for (int i = n; i < n + lot; i++) {
			String num = "00" + String.valueOf(i);
			num = num.substring(num.length() - 2);
			lotList.add(str[0] + "-" + num);
			// lotList.add(str[0] + "-" + String.valueOf(i));
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
		String jpql = "select max(o.lotNo) from WaferCompanyLot o where o.lotNo like ?";
		String maxLotNo = (String) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { "%" + lotNo + "%" })
				.singleResult();
		return maxLotNo;
	}

	public void changeStatusWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments) {
		this.changeStatusWaferProcess(waferProcessDTO, holdCode, holdCodeId,
				useraccount, comments, "");
	}

	public void changeStatusWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments, String engineerName) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		if(waferProcessDTO.getShippingDate() != null){
			waferProcess.setShippingDate(waferProcessDTO.getShippingDate());
		}
		waferProcess.setLastModifyDate(waferProcessDTO.getCreateDate());
		waferProcess.setLastModifyUser(user);
		String nowStatus = waferProcess.getStatus();
		WaferStatusOptLog waferStatusOptLog = new WaferStatusOptLog();
		if (holdCodeId != null && !"".equals(holdCodeId)) {
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment
					.findRelationByResource(Long.valueOf(holdCodeId)).get(0);
			waferProcess.setResHold(rsourceLineAssignment.getChild());
			waferProcess.setStatus(rsourceLineAssignment.getParent().getName());
			waferStatusOptLog.setHoldCode(rsourceLineAssignment.getChild()
					.getDesc());
		} else {
			waferProcess.setStatus(waferProcessDTO.getCurrStatus());
			waferStatusOptLog.setHoldCode(holdCode);
		}
		if (waferProcessDTO.getEquipmentId() != null) {
			Equipment equipment = Equipment.load(Equipment.class,
					waferProcessDTO.getEquipmentId());
			equipment.setWaferProcess(waferProcess);
			equipment.setStatus(waferProcessDTO.getEquipmentStatus());
			waferProcess.setEquipment(equipment);
			equipment.save();
		}
		if("IDLE".equals(waferProcessDTO.getEquipmentStatus())){
			if(waferProcess.getEquipment() != null){
				Equipment equipment = Equipment.load(Equipment.class,
						waferProcess.getEquipment().getId());
				equipment.setWaferProcess(null);
				equipment.setStatus(waferProcessDTO.getEquipmentStatus());
				waferProcess.setEquipment(null);
				equipment.save();
			}
		}
		OptLog optLog = new OptLog();
		waferStatusOptLog.setCreateDate(waferProcessDTO.getCreateDate());
		waferStatusOptLog.setEngineerName(engineerName);
		waferStatusOptLog.setCreateUser(user);
		waferStatusOptLog
				.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		waferStatusOptLog.setLastModifyUser(user);
		waferStatusOptLog.setWaferProcess(waferProcess);

		if (waferProcessDTO.getFutureHoldStationId() != null) {
			Station station = Station.get(Station.class,
					waferProcessDTO.getFutureHoldStationId());
			if (station.getId() != null) {
				waferProcess.setStatus(nowStatus);
				waferProcess.setFutureStation(station);
				waferStatusOptLog.setFutureStation(station);
				WaferCompanyLot waferCompanyLot = waferProcess
						.getWaferCompanyLot();
				waferCompanyLot.setFutureHoldStation(station);
			}
		}
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		waferStatusOptLog.setOptLog(optLog);
		waferStatusOptLog.save();
	}

	public void holdWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			waferProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		} else
		{
			waferProcessDTO.setEquipmentStatus("IDLE");
			waferProcessDTO.setEquipmentId(null);
			waferProcessDTO.setCurrStatus(holdCode);
		}
		this.changeStatusWaferProcess(waferProcessDTO, holdCode, holdCodeId,
				useraccount, comments);

	}

	public void engDispWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments, String engineerName) {
		if ("pass".equals(holdCode.toLowerCase())) {
			waferProcessDTO.setCurrStatus("Pass");
			holdCode = "Eng. Pass";
		} else
			waferProcessDTO.setCurrStatus(holdCode);
		this.changeStatusWaferProcess(waferProcessDTO, holdCode, holdCodeId,
				useraccount, comments);
	}
	
	public void outSpecWaferProcess(WaferProcessDTO waferProcessDTO,
			String useraccount,String comments) {
		String holdCode = "Out-Spec";
		Resource resource = (Resource) Resource.findByProperty(Resource.class, "identifier", "Out Spectinon").get(0);
		String holdCodeId = String.valueOf(resource.getId());
		comments = "Out Spectinon";
		this.changeStatusWaferProcess(waferProcessDTO, holdCode, holdCodeId,
				useraccount, comments);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int checkOutSpecWaferProcessTime(Long waferProcessId) {
		String jpql = "select o from WaferStatusOptLog o inner join o.waferProcess e where e.id=? ";
		jpql += "and o.holdCode=?" ;
		jpql += "order by o.createDate desc";
		List<WaferStatusOptLog> result = (List<WaferStatusOptLog>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { waferProcessId, "Out Spectinon"})
				.list();
		WaferStatusOptLogDTO dto = new WaferStatusOptLogDTO();
		if (result != null) {
			return result.size();
		}
		return 0;
	}
	
	public String saveMergeWaferProcess(WaferProcessDTO waferProcessDTO,
			String mergeIds, String useraccount, String comments,
			String locationIds) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理合批

		WaferProcess nowWaferProcess = new WaferProcess();
		WaferCompanyLot mergeWaferCompanyLot = new WaferCompanyLot();
		String[] idArray = mergeIds.split(",");
		List<WaferProcess> list = new ArrayList<WaferProcess>();
		int dieQtyTotal = 0;
		int waferQtyTotal = 0;
		Set<WaferInfo> waferInfos = new HashSet<WaferInfo>();
		WaferProcess tempWaferProcess = null;
		for (String id : idArray) {
			WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
					Long.parseLong(id));
			this.locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(waferProcess.getLocations()));
			if (tempWaferProcess != null) {
				if (tempWaferProcess.getWaferCompanyLot().getWaferCustomerLot()
						.getProduct().getId() != waferProcess.getWaferCompanyLot()
						.getWaferCustomerLot().getProduct().getId())
				{
					return "wafer.mergeCompanyLot.productName";
				}
				if (!tempWaferProcess
						.getWaferCompanyLot()
						.getWaferCustomerLot()
						.getCustomer()
						.getCustomerCode()
						.equals(waferProcess.getWaferCompanyLot()
								.getWaferCustomerLot().getCustomer()
								.getCustomerCode()))
				{
					return "wafer.mergeCompanyLot.cusCode";
				}
				if (!tempWaferProcess
						.getWaferCompanyLot()
						.getWaferCustomerLot()
						.getCustomerLotNo()
						.equals(waferProcess.getWaferCompanyLot()
								.getWaferCustomerLot().getCustomerLotNo()))
				{
					return "wafer.mergeCompanyLot.cusLotNo";
				}
				if (!tempWaferProcess
						.getWaferCompanyLot()
						.getWaferCustomerLot()
						.getPart()
						.getPartNo()
						.equals(waferProcess.getWaferCompanyLot()
								.getWaferCustomerLot().getPart().getPartNo()))
				{
					return "wafer.mergeCompanyLot.partNo";
				}
				if (tempWaferProcess.getWaferCompanyLot().getWaferCustomerLot()
						.getResLotType().getId() != waferProcess
						.getWaferCompanyLot().getWaferCustomerLot().getResLotType()
						.getId())
				{
					return "wafer.mergeCompanyLot.type";
				}
				if (!tempWaferProcess.getStatus().equals(waferProcess.getStatus()))
				{
					return "wafer.mergeCompanyLot.status";
				}
				if(!(tempWaferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId() == waferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId()))
				{
					return "wafer.mergeCompanyLot.waferstatus";
				}
//				return "Please select same product!";
			}
			tempWaferProcess = waferProcess;
			dieQtyTotal += waferProcess.getQtyIn();
			waferQtyTotal += waferProcess.getWaferQtyIn();
			waferInfos.addAll(waferProcess.getWaferCompanyLot().getWaferInfos());
			list.add(waferProcess);
		}
		list = sortWaferProcess(list);
		nowWaferProcess = list.get(list.size() - 1);		
		WaferCompanyLot nowWaferCompanyLot = nowWaferProcess
				.getWaferCompanyLot();
		mergeWaferCompanyLot.setWaferCustomerLot(nowWaferCompanyLot
				.getWaferCustomerLot());
		mergeWaferCompanyLot.setLotNo(nowWaferCompanyLot.getLotNo());
		//mergeWaferCompanyLot.setParentWaferCompanyLot(nowWaferCompanyLot);
		mergeWaferCompanyLot.setCreateDate(waferProcessDTO.getLastModifyDate());
		mergeWaferCompanyLot.setCreateUser(user);
		mergeWaferCompanyLot.setLastModifyDate(waferProcessDTO
				.getLastModifyDate());
		mergeWaferCompanyLot.setLastModifyUser(user);
		mergeWaferCompanyLot.setWaferInfos(waferInfos);
		mergeWaferCompanyLot.save();
		WaferProcess mergeWaferProcess = new WaferProcess();
		mergeWaferProcess.setWaferCompanyLot(mergeWaferCompanyLot);
		mergeWaferProcess.setShippingDate(nowWaferProcess.getShippingDate());
		mergeWaferProcess.setStatus(nowWaferProcess.getStatus());
		mergeWaferProcess.setStation(nowWaferProcess.getStation());
		mergeWaferProcess.setCreateDate(nowWaferProcess.getCreateDate());
		mergeWaferProcess.setCreateUser(user);
		mergeWaferProcess
				.setLastModifyDate(nowWaferProcess.getLastModifyDate());
		mergeWaferProcess.setLastModifyUser(user);
		mergeWaferProcess.setQtyIn(dieQtyTotal);
		mergeWaferProcess.setQtyOut(mergeWaferProcess.getQtyIn());
		mergeWaferProcess.setWaferQtyIn(waferQtyTotal);
		mergeWaferProcess.setWaferQtyOut(mergeWaferProcess.getWaferQtyIn());
		mergeWaferProcess.setResLotType(nowWaferProcess.getResLotType());
		// 处理库存位置
		mergeWaferProcess.save();
		this.locationApplication.updateLocationsWaferProcess(locationIds, mergeWaferProcess.getId());
		for (WaferProcess waferProcess : list) {
			WaferCompanyLot waferCompanyLot = new WaferCompanyLot();
			waferProcess.setStatus("Merge");
			waferCompanyLot = waferProcess.getWaferCompanyLot();
			waferCompanyLot.setMergeWaferCompanyLot(mergeWaferCompanyLot);
			waferCompanyLot.save();
			waferProcess.save();
		}

		// 保存操作信息 --start
		WaferOptLog waferMergeOptLog = new WaferOptLog();
		OptLog optLog = new OptLog();
		waferMergeOptLog.setCreateDate(waferProcessDTO.getCreateDate());
		waferMergeOptLog.setCreateUser(user);
		waferMergeOptLog.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		waferMergeOptLog.setLastModifyUser(user);
		waferMergeOptLog.setWaferProcess(nowWaferProcess);
		waferMergeOptLog.setType("Merge");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		waferMergeOptLog.setOptLog(optLog);
		waferMergeOptLog.save();
		// 保存操作信息 --end
		return "success";
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferProcessDTO getMergeWaferProcess(
			WaferProcessDTO waferProcessDTO, String mergeIds) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		
		String[] idArray = mergeIds.split(",");
		List<WaferProcess> list = new ArrayList<WaferProcess>();
		int qtyIn = 0;
		int dieQtyTotal = 0;
		int waferQtyTotal = 0;
		for (String id : idArray) {
			WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
					Long.parseLong(id));
			list.add(waferProcess);
		}
//		sortWaferProcess(list);
		
//		for (String id : idArray) {
//		for (WaferProcess listtemp:list){
			WaferProcess waferProcess = list.get(0);
			WaferProcess nowWaferProcess = list.get(1);
//			if (qtyIn < waferProcess.getQtyIn()) {
//				nowWaferProcess = waferProcess;
//				qtyIn = waferProcess.getQtyIn();
//			}
			if (nowWaferProcess.getWaferCompanyLot() == null
					|| nowWaferProcess.getWaferCompanyLot()
							.getWaferCustomerLot().getProduct().getId() != waferProcess
							.getWaferCompanyLot().getWaferCustomerLot()
							.getProduct().getId()) {
				return null;
			}
		
			if (!nowWaferProcess
					.getWaferCompanyLot()
					.getWaferCustomerLot()
					.getCustomer()
					.getCustomerCode()
					.equals(waferProcess.getWaferCompanyLot()
							.getWaferCustomerLot().getCustomer()
							.getCustomerCode()))
			{
				return null;
			}
			if (!nowWaferProcess
					.getWaferCompanyLot()
					.getWaferCustomerLot()
					.getCustomerLotNo()
					.equals(waferProcess.getWaferCompanyLot()
							.getWaferCustomerLot().getCustomerLotNo()))
			{
				return null;
			}
			if (!nowWaferProcess
					.getWaferCompanyLot()
					.getWaferCustomerLot()
					.getPart()
					.getPartNo()
					.equals(waferProcess.getWaferCompanyLot()
							.getWaferCustomerLot().getPart().getPartNo()))
			{
				return null;
			}
			if (nowWaferProcess.getWaferCompanyLot().getWaferCustomerLot()
					.getResLotType().getId() != waferProcess
					.getWaferCompanyLot().getWaferCustomerLot().getResLotType()
					.getId())
			{
				return null;
			}
			if (!nowWaferProcess.getStatus().equals(waferProcess.getStatus()))
			{
				return null;
			}
			if(!(nowWaferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId() == waferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId()))
			{
				return null;
			}
			if(nowWaferProcess.getStation() == null || waferProcess.getStation() == null || waferProcess.getStation().getId() != nowWaferProcess.getStation().getId())
			{
				return null;
			}
			if(nowWaferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId() != waferProcess.getWaferCompanyLot().getWaferCustomerLot().getWaferStatusId())
			{
				return null;
			}
			dieQtyTotal = waferProcess.getQtyIn() + nowWaferProcess.getQtyIn();
			waferQtyTotal = waferProcess.getWaferQtyIn() + nowWaferProcess.getWaferQtyIn();
//			list.add(waferProcess);
//		}
		WaferCompanyLot nowWaferCompanyLot = nowWaferProcess
				.getWaferCompanyLot();
		waferProcessDTO.setCustomerCode(nowWaferCompanyLot
				.getWaferCustomerLot().getCustomer().getCustomerCode());
		waferProcessDTO.setLotNo(nowWaferCompanyLot.getLotNo());
		waferProcessDTO.setCustomerLotNo(nowWaferCompanyLot
				.getWaferCustomerLot().getCustomerLotNo());
		waferProcessDTO.setPartNumber(nowWaferCompanyLot.getWaferCustomerLot()
				.getPart().getPartNo());
		waferProcessDTO.setQtyIn(dieQtyTotal);
		waferProcessDTO.setWaferQtyIn(waferQtyTotal);
		waferProcessDTO.setCustomerOrder(nowWaferCompanyLot
				.getWaferCustomerLot().getCustomerOrder());
		waferProcessDTO.setOptUser(user.getUserAccount());
		waferProcessDTO.setCurrStatus(nowWaferProcess.getStatus());
		waferProcessDTO.setStatus(nowWaferProcess.getStatus());
		waferProcessDTO.setEntryDate(nowWaferProcess.getCreateDate());
		waferProcessDTO.setEntryTime(MyDateUtils.getDayHour(
				nowWaferProcess.getCreateDate(), new Date()));
		waferProcessDTO.setLotType(nowWaferProcess.getResLotType().getIdentifier());
		waferProcessDTO.setStockPos("");
		waferProcessDTO.setProductName(nowWaferProcess.getWaferCompanyLot().getWaferCustomerLot().getProduct().getProductName());
		return waferProcessDTO;
	}

	public void changeLotType(WaferProcessDTO waferProcessDTO, String lotType,
			String useraccount, String comments) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		Resource resource = Resource
				.load(Resource.class, Long.valueOf(lotType));
		waferProcess.setResLotType(resource);
		waferProcess.setLotType(resource.getIdentifier());
		waferProcess.setLastModifyDate(waferProcessDTO.getCreateDate());
		waferProcess.setLastModifyUser(user);

		WaferChangeLotTypeOptLog waferChangeLotTypeOptLog = new WaferChangeLotTypeOptLog();
		OptLog optLog = new OptLog();
		waferChangeLotTypeOptLog.setCreateDate(waferProcessDTO.getCreateDate());
		waferChangeLotTypeOptLog.setCreateUser(user);
		waferChangeLotTypeOptLog.setLastModifyDate(waferProcessDTO
				.getLastModifyDate());
		waferChangeLotTypeOptLog.setLastModifyUser(user);
		waferChangeLotTypeOptLog.setWaferProcess(waferProcess);
		waferChangeLotTypeOptLog.setLotType(lotType);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		waferChangeLotTypeOptLog.setOptLog(optLog);
		waferChangeLotTypeOptLog.save();
	}

	public void changeLocations(WaferProcessDTO waferProcessDTO,
			String locationIds, String useraccount, String comments) {
		User user;
		if (waferProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(waferProcessDTO.getUserDTO()
					.getUserAccount());

		Set<Location> locations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(Location.class, locationId);
			locations.add(location);
		}
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
				waferProcessDTO.getId());
		for(Location location : waferProcess.getLocations()){
			location.setWaferProcess(null);
		}
		waferProcess.setLocations(locations);
		for (Location loc : locations) {
			loc.setWaferProcess(waferProcess);
			loc.save();
		}
		waferProcess.setLastModifyDate(waferProcessDTO.getCreateDate());
		waferProcess.setLastModifyUser(user);
		waferProcess.save();
		WaferChangeLocationOptLog waferChangeLocationOptLog = new WaferChangeLocationOptLog();
		OptLog optLog = new OptLog();
		waferChangeLocationOptLog
				.setCreateDate(waferProcessDTO.getCreateDate());
		waferChangeLocationOptLog.setCreateUser(user);
		waferChangeLocationOptLog.setLastModifyDate(waferProcessDTO
				.getLastModifyDate());
		waferChangeLocationOptLog.setLastModifyUser(user);
		waferChangeLocationOptLog.setWaferProcess(waferProcess);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		waferChangeLocationOptLog.setOptLog(optLog);
		waferChangeLocationOptLog.save();
	}

	public void futureHoldWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments) {
		waferProcessDTO.setCurrStatus("Waiting");
		this.changeStatusWaferProcess(waferProcessDTO, holdCode, holdCodeId,
				useraccount, comments);

	}

	public List<Map<String, String>> getLotTypes() {
		ResourceType resourceType = ResourceType.findByProperty(
				ResourceType.class, "name", "lotType").get(0);
		List<ResourceTypeAssignment> list = ResourceTypeAssignment
				.findResourceByType(resourceType.getId());
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		for (ResourceTypeAssignment resourceTypeAssignment : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(resourceTypeAssignment.getResource()
					.getId()));
			map.put("name", resourceTypeAssignment.getResource().getName());
			map.put("value", resourceTypeAssignment.getResource()
					.getIdentifier());
			res.add(map);
		}
		return res;
	}

	public List<Map<String, String>> getResourceByParentIdentifier(
			String identifier) {
		List<Map<String, String>> res = new ArrayList<Map<String, String>>();
		Resource parent = Resource.findByProperty(Resource.class, "identifier",
				identifier).get(0);
		List<Resource> list = Resource.findChildByParent(parent.getId());
		for (Resource resource : list) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(resource.getId()));
			map.put("name", resource.getName());
			map.put("value", resource.getIdentifier());
			res.add(map);
		}
		return res;
	}

	public void trackIn(WaferProcessDTO waferProcessDTO, String useraccount,
			String comments) {
		waferProcessDTO.setCurrStatus("Running");
		waferProcessDTO.setEquipmentStatus("RUN");
		this.changeStatusWaferProcess(waferProcessDTO, "Running", null,
				useraccount, comments);

	}

	public void abortStep(WaferProcessDTO waferProcessDTO, String useraccount,
			String comments) {
		waferProcessDTO.setCurrStatus("Waiting");
		waferProcessDTO.setEquipmentStatus("IDLE");
//		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
//				waferProcessDTO.getId());
//		if (waferProcess.getEquipment() != null) {
//			waferProcessDTO.setEquipmentId(waferProcess.getEquipment().getId());
//		}
		waferProcessDTO.setEquipmentId(null);
		this.changeStatusWaferProcess(waferProcessDTO, waferProcessDTO.getCurrStatus(), null,
				useraccount, comments);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferStatusOptLogDTO findLastWaferStatusOptLogByProcess(Long id) {
		String jpql = "select o from WaferStatusOptLog o inner join o.waferProcess e where e.id=? ";
		jpql += "order by o.createDate desc";
		List<WaferStatusOptLog> result = (List<WaferStatusOptLog>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.list();
		WaferStatusOptLogDTO dto = new WaferStatusOptLogDTO();
		if (result.size() > 1) {
			try {
				BeanUtilsExtends.copyProperties(dto, result.get(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setComments(result.get(1).getOptLog().getComments());
		}
		return dto;
	}
	
	public void release(WaferProcessDTO waferProcessDTO, String useraccount,
			String comments) {
		WaferStatusOptLogDTO waferStatusOptLogDTO = this.findLastWaferStatusOptLogByProcess(waferProcessDTO.getId());
		if(waferStatusOptLogDTO.getHoldCode() != null && "Running".equals(waferStatusOptLogDTO.getHoldCode())){
			waferProcessDTO.setCurrStatus(waferStatusOptLogDTO.getHoldCode());
			waferProcessDTO.setEquipmentStatus("RUN");
		}else{
			waferProcessDTO.setCurrStatus("Waiting");
			waferProcessDTO.setEquipmentStatus("IDLE");
		}
//		waferProcessDTO.setShippingDate(null);
//		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class,
//				waferProcessDTO.getId());
//		if(waferProcess.getFutureStation() != null && waferProcess.getFutureStation().getId() == waferProcess.getStation().getId()){
//			waferProcess.setFutureStation(null);
//		}
//		if (waferProcess.getEquipment() != null) {
//			waferProcessDTO.setEquipmentId(waferProcess.getEquipment().getId());
//		}
		this.changeStatusWaferProcess(waferProcessDTO, waferProcessDTO.getCurrStatus(), null,
				useraccount, comments);
	}

	public WaferProcessDTO trackOut(WaferProcessDTO waferProcessDTO) {

		return this.saveNextProcess(waferProcessDTO);
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
			stockPos += "," + location.getLoctionCode();
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
	public WaferStatusOptLogDTO findWaferStatusOptLogByProcess(Long id,
			boolean isFuture) {
		String jpql = "select o from WaferStatusOptLog o inner join o.waferProcess e where e.id=? ";
		if (isFuture) {
			jpql += "and o.futureStation is not null and e.futureStation.id is not null ";
		}
		jpql += "order by o.createDate desc";
		WaferStatusOptLog result = (WaferStatusOptLog) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		WaferStatusOptLogDTO dto = new WaferStatusOptLogDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setOptName(result.getOptLog().getRightUserUser()
					.getUserAccount());
			if(result.getFutureStation() != null){
				dto.setFutureStationName(result.getFutureStation().getStationName());
				dto.setFutureStationNameEn(result.getFutureStation()
						.getStationNameEn());
			}
			dto.setComments(result.getOptLog().getComments());
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StationDTO> findAfterStationsByProcessId(Long id) {
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, id);
		Station station = waferProcess.getStation();
		DefineStationProcess defineStationProcess = waferProcess
				.getWaferCompanyLot().getWaferCustomerLot()
				.getDefineStationProcess();
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.load(
					DefineStationProcess.class, Long.valueOf(1));
		}
		List<StationDTO> stationList = new ArrayList<StationDTO>();
		Iterator<Station> stations = defineStationProcess.getStations()
				.iterator();
		while (stations.hasNext()) {
			StationDTO stationDTO = new StationDTO();
			Station defStation = stations.next();
			if (defStation.getSequence() > station.getSequence()) {
				try {
					BeanUtils.copyProperties(stationDTO, defStation);
				} catch (Exception e) {
					e.printStackTrace();
				}
				stationList.add(stationDTO);
			}
		}
		Collections.sort(stationList, new Comparator<StationDTO>() {
			public int compare(StationDTO arg0, StationDTO arg1) {
				return arg0.getSequence().compareTo(arg1.getSequence());
			}
		});
		return stationList;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private List<WaferInfo> findWaferInfoByWaferIds(List<String> ids) {
		StringBuilder jpql = new StringBuilder(
				"select _waferInfo from WaferInfo _waferInfo where ");
		List<Object> conditionVals = new ArrayList<Object>();
		for (int i = 0; i < ids.size(); i++) {
			String id = ids.get(i);
			if (i == 0) {
				jpql.append(" _waferInfo.waferId = ? ");
				conditionVals.add(id);
			} else {
				jpql.append(" or _waferInfo.waferId = ? ");
				conditionVals.add(id);
			}
		}
		List<WaferInfo> list = (List<WaferInfo>) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();
		return list;
	}

	public List<WaferProcessDTO> findWaferLotBeforeDA(Long id) {
		List<WaferProcessDTO> result = new ArrayList<WaferProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _waferProcess from WaferProcess _waferProcess   left join _waferProcess.createUser  left join _waferProcess.lastModifyUser  left join _waferProcess.station  left join _waferProcess.equipment  left join _waferProcess.waferCompanyLot  where 1=1 ");

		jpql.append(" and _waferProcess.status in ('Waiting')");
		if (id != null) {
			jpql.append(" and _waferProcess.station.id=?");
			conditionVals.add(id);
		}
		jpql.append(" and  not(_waferProcess.status=?)");
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		conditionVals.add(finish.getName());
		jpql.append(" and _waferProcess.status not in ("
				+ StaticValue.NOT_SHOW_STATUS + ") ");
		jpql.append(" order by _waferProcess.shippingDate DESC");
		List<WaferProcess> list = (List<WaferProcess>) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();
		for (WaferProcess waferProcess : list) {
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferProcessDTO, waferProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			waferProcessDTO.setCustomerCode(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getCustomer().getCustomerCode());
			waferProcessDTO.setLotNo(waferProcess.getWaferCompanyLot()
					.getLotNo());
			waferProcessDTO.setCustomerLotNo(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getCustomerLotNo());
			waferProcessDTO.setPartNumber(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getPart().getPartNo());
			waferProcessDTO.setProductName(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getProduct().getProductName());
			waferProcessDTO.setCustomerOrder(waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getCustomerOrder());
			waferProcessDTO.setOptUser(waferProcess.getLastModifyUser()
					.getUserAccount());
			waferProcessDTO.setCurrStatus(waferProcess.getStatus());
			waferProcessDTO.setEntryDate(waferProcess.getShippingDate());
			waferProcessDTO.setEntryTime(MyDateUtils.getDayHour(
					waferProcess.getShippingDate(), new Date()));
			waferProcessDTO.setLotType(waferProcess.getResLotType()
					.getIdentifier());
			waferProcessDTO.setStockPos(this
					.getLocationsByLocations(waferProcess.getLocations()));
			if (waferProcess.getEquipment() != null) {
				waferProcessDTO.setEquipment(waferProcess.getEquipment()
						.getEquipment());
			}
			result.add(waferProcessDTO);
		}
		return result;
	}

	public String getProductNameByWaferProcessId(Long id) {
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, id);
		return waferProcess.getWaferCompanyLot().getWaferCustomerLot()
				.getProduct().getProductName();
	}

	public String checkProcess(Long waferCustomerLotId,
			List<Map<String, Object>> waferInfo)
	{
		String jpql = "select e from WaferCustomerLot e where e.id=?";
		WaferCustomerLot waferCustomerLot = (WaferCustomerLot) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { waferCustomerLotId })
				.singleResult();
		String resultCode = checkQty(waferCustomerLot,waferInfo);
		if(!"success".equals(resultCode))
		{
			return resultCode;
		}	
		resultCode = checkStatus(waferCustomerLot);
		if(!"success".equals(resultCode))
		{
			return resultCode;
		}
		resultCode = checkWaferInfoId(waferCustomerLot,waferInfo);
		return resultCode;
	}
	private String checkStatus(WaferCustomerLot waferCustomerLot)
	{
		if(waferCustomerLot == null)
		{
			return "waferIn.0002";
		}
		if(waferCustomerLot.getCurrStatus().equalsIgnoreCase("hold"))
		{
			return "waferIn.0005";
		}
		return "success";
	}
	private String checkWaferInfoId(WaferCustomerLot waferCustomerLot,List<Map<String, Object>> waferInfo)
	{
		if(waferCustomerLot == null)
		{
			return "waferIn.0002";
		}
		Map<String,Object> map = new HashMap<String,Object>();
		String waferId = "";
		for (Map<String, Object> wafer : waferInfo) {
		
			waferId =  wafer.get("wafer").toString();
			map.put(waferId,waferId);
			if(waferId.contains("Dummy"))
			{
				continue;
			}
			List<WaferInfo> waferInfos = WaferInfo.findByProperty(WaferInfo.class, "waferId",waferId);
			if(waferInfos.size() > 0)
			{
				return "waferIn.0006";
			}
		}
		
		if(waferCustomerLot.getWafer() > map.size())
		{
			return "waferIn.0006";
		}
		return "success";
	}
	private String checkQty(WaferCustomerLot waferCustomerLot,
			List<Map<String, Object>> waferInfo) {
		String resultCode = "success";
		
		if (waferCustomerLot != null) {
			int sumQty = 0;
			// String customerLotId = waferCustomerLot.getId().toString();
			for (Map<String, Object> wafer : waferInfo) {
				try {
					sumQty += Integer
							.parseInt(wafer.get("waferQty").toString());
				} catch (NumberFormatException ne) {
					log.error("收料时数字转换异常", ne);
					resultCode = "waferIn.0003";
					return resultCode;
				}

			}
			if (waferCustomerLot.getQty() != sumQty) {
				resultCode = "waferIn.0001";
			}
		} else {
			resultCode = "waferIn.0002";
		}
		return resultCode;
	}
	

	public String nextProcessVerify(Map<String, Object> info,Long waferProcessId) {
		
//		Map<String, Object> info = (Map<String, Object>) maps.get("data");
		if (info == null)
		{
			return "success";
		}
		
		if(info.get("WaferQtyOut") == null)
		{
			return "wafer.trackout.WaferQtyOutNull";
		}
		if(info.get("GoodQtyOut") == null)
		{
			return "wafer.trackout.GoodQtyOutNull";
		}
		if(info.get("ScrapsQtyOut") == null)
		{
			return "wafer.trackout.ScrapsQtyOutNull";
		}
		int waferQtyOut = Integer.parseInt(info.get("WaferQtyOut").toString());
		int goodQtyOut = Integer.parseInt(info.get("GoodQtyOut").toString());
		int scrapsQtyout = Integer.parseInt(info.get("ScrapsQtyOut").toString());
		WaferProcess nowWaferProcess = WaferProcess.get(WaferProcess.class,waferProcessId);
		if(waferQtyOut != nowWaferProcess.getWaferQtyIn())
		{
			return "wafer.trackout.waferQtyNotEqual";
		}
		if(goodQtyOut > nowWaferProcess.getQtyIn() || scrapsQtyout > nowWaferProcess.getQtyIn() || (goodQtyOut+scrapsQtyout) != nowWaferProcess.getQtyIn())
		{
			return "wafer.trackout.qtyDieNotEqual";
		}
//		验证rejectCode的数量与不良品数量之和是否相等
//		List<Map<String, String>> rejectCodeItemMaps = (List<Map<String, String>>) info
//				.get("rejectCode");
//		int sumScrapsQtyOut = 0;
//		int tempScrapsQtyOut = 0;
//		for (Map<String, String> rejectCodeItemMap : rejectCodeItemMaps)
//		{
//			if ("".equals(rejectCodeItemMap.get("value").toString()))
//			{
//				tempScrapsQtyOut = 0;
//			}
//			else
//			{
//				tempScrapsQtyOut = Integer.parseInt(rejectCodeItemMap.get("value").toString());
//			}
//			sumScrapsQtyOut += tempScrapsQtyOut;
//			if(sumScrapsQtyOut != scrapsQtyout)
//			{
//				return "wafer.trackout.qtyDieScrapsSumNotEqual";
//			}
//		}
		return "success";
	}
	public String verifyEquipmentRunningStatusById(Long id) {
		EquipmentDTO equipmentDTO = equipmentApplication.getEquipment(id);
		WaferProcessDTO waferProcessDTO = equipmentApplication.findWaferProcessByEquipment(id);
		if(equipmentDTO == null)
		{
			return "wafer.trackin.equimentStatusDown";
		}
		if(equipmentDTO.getEquipment().equalsIgnoreCase("dummy")  || equipmentDTO.getStatus().equalsIgnoreCase("IDLE"))
		{
			return "success";
		}
		if(equipmentDTO.getStatus().equalsIgnoreCase("DOWN"))
		{
			return "wafer.trackin.equimentStatusDown";
		}
		if (equipmentDTO != null && waferProcessDTO != null
				&& "RUN".equals(equipmentDTO.getStatus())) {
			return "wafer.trackin.equimentStatusUsed";
		}
		
		return "success";
	}

	
	private void saveOptLog(WaferProcessDTO waferProcessDTO,WaferProcess nowWaferProcess,User user,String type){
		WaferOptLog waferOptLog = new WaferOptLog();
		OptLog optLog = new OptLog();
		waferOptLog.setCreateDate(waferProcessDTO.getCreateDate());
		waferOptLog.setCreateUser(user);
		waferOptLog.setLastModifyDate(waferProcessDTO.getLastModifyDate());
		waferOptLog.setLastModifyUser(user);
		waferOptLog.setWaferProcess(nowWaferProcess);
		waferOptLog.setType(type);
		optLog.setComments(waferProcessDTO.getComments());
		optLog.setRightUserUser(User.findByUserAccount(waferProcessDTO.getUseraccount()));
		optLog.save();
		waferOptLog.setOptLog(optLog);
		waferOptLog.save();
	}
}
