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

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.core.domain.ResourceLineAssignment;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
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
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ManufactureProcessApplicationImpl implements
		ManufactureProcessApplication {

	private QueryChannelService queryChannel;
	@Inject
	private EquipmentApplication equipmentApplication;
	
	@Inject
	private LocationApplication locationApplication;
	
	@Inject
	private JpaTransactionManager transactionManager;
	
	
	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureProcessDTO getManufactureProcess(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.load(
				ManufactureProcess.class, id);
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(manufactureProcessDTO, manufactureProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureProcessDTO
				.setId((java.lang.Long) manufactureProcess.getId());
		SubstrateCompanyLot substrateCompanyLot = manufactureProcess
				.getManufactureLot().getSubstrateProcess()
				.getSubstrateCompanyLot();
		SubstrateCustomerLot substrateCustomerLot = substrateCompanyLot
				.getSubstrateCustomerLot();
		manufactureProcessDTO.setVenderName(substrateCustomerLot.getVender()
				.getVenderName());
		manufactureProcessDTO.setLotNo(manufactureProcess.getManufactureLot()
				.getLotNo());
		manufactureProcessDTO.setCustomerLotNo(substrateCustomerLot
				.getCustomerLotNo());
		manufactureProcessDTO.setPartNumber(substrateCustomerLot.getSubstratePart()
				.getPartNo());
		manufactureProcessDTO.setBatchNo(substrateCustomerLot.getBatchNo());
		manufactureProcessDTO.setPon(substrateCustomerLot.getPon());
		if(manufactureProcess.getStatus().indexOf("Waiting") > -1 ){
			manufactureProcessDTO.setEntryDate(manufactureProcess.getCreateDate());
		}else
			manufactureProcessDTO.setEntryDate(manufactureProcess.getShippingDate());
		manufactureProcessDTO.setEntryTime(MyDateUtils.getDayHour(
				manufactureProcessDTO.getEntryDate(), new Date()));
		manufactureProcessDTO.setUserName(substrateCustomerLot.getCreateUser()
				.getName());
		manufactureProcessDTO.setCurrStatus(manufactureProcess.getStatus());
		manufactureProcessDTO.setProductName(manufactureProcess
				.getManufactureLot().getProduct().getProductName());
		manufactureProcessDTO.setSubBatch(substrateCompanyLot.getLotNo());
		manufactureProcessDTO.setStep(manufactureProcess.getStation().getStationNameEn());
		Set<Location> locations = manufactureProcess
				.getManufactureLocations();
		String stockPos = "";
		for (Location location : locations) {
			stockPos += ";" + location.getLoctionCode();
		}
		if (stockPos.length() > 5) {
			manufactureProcessDTO.setStockPos(stockPos.substring(1));
		}
		manufactureProcessDTO.setLotType(manufactureProcess.getResLotType()
				.getIdentifier());
		manufactureProcessDTO.setGuaranteePeriod(substrateCustomerLot
				.getExpiryDate());
		manufactureProcessDTO.setProductionDate(substrateCustomerLot
				.getManufactureDate());
		if (manufactureProcess.getEquipment() != null) {
			manufactureProcessDTO.setEquipmentName(manufactureProcess
					.getEquipment().getEquipment());
		}
		if(manufactureProcess.getRejectStation() != null){
			manufactureProcessDTO.setStep(manufactureProcess.getRejectStation().getStationName());
		}
		return manufactureProcessDTO;
	}

	public ManufactureProcessDTO saveManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO) {
		ManufactureProcess manufactureProcess = new ManufactureProcess();
		try {
			BeanUtils.copyProperties(manufactureProcess, manufactureProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureProcess.save();
		manufactureProcessDTO
				.setId((java.lang.Long) manufactureProcess.getId());
		return manufactureProcessDTO;
	}

	public void updateManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO) {
		ManufactureProcess manufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(manufactureProcess, manufactureProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateManufactureProcessDelayTime(List<ManufactureProcessDTO> list,int batchSize) {
		
		EntityManager em = transactionManager.getEntityManagerFactory().createEntityManager();
		Date date = new Date();
		int i = 0;
		em.getTransaction().begin();
		Query q = null;
		for(ManufactureProcessDTO manufactureProcessDTO:list){
			manufactureProcessDTO.setDelayTime(DateUtils.getDayDiff(manufactureProcessDTO.getEntryDate(), date));
			q = em.createQuery("update ManufactureProcess m set m.delayTime=:delayTime where m.id=:id");
			q.setParameter("delayTime", manufactureProcessDTO.getDelayTime());
			q.setParameter("id", manufactureProcessDTO.getId());
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

	public void removeManufactureProcess(Long id) {
		this.removeManufactureProcesss(new Long[] { id });
	}

	public void removeManufactureProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ManufactureProcess manufactureProcess = ManufactureProcess.load(
					ManufactureProcess.class, ids[i]);
			manufactureProcess.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureProcessDTO> findAllManufactureProcess() {
		List<ManufactureProcessDTO> list = new ArrayList<ManufactureProcessDTO>();
		List<ManufactureProcess> all = ManufactureProcess
				.findAll(ManufactureProcess.class);
		for (ManufactureProcess manufactureProcess : all) {
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureProcessDTO,
						manufactureProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(manufactureProcessDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureProcessDTO> pageQueryManufactureProcess(
			ManufactureProcessDTO queryVo, int currentPage, int pageSize) {
		List<ManufactureProcessDTO> result = new ArrayList<ManufactureProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _manufactureProcess from ManufactureProcess _manufactureProcess   left join _manufactureProcess.createUser  left join _manufactureProcess.lastModifyUser  left join _manufactureProcess.station  left join _manufactureProcess.equipment  left join _manufactureProcess.manufactureLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _manufactureProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _manufactureProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _manufactureProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _manufactureProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getEndTime() != null) {
			jpql.append(" and _manufactureProcess.endTime between ? and ? ");
			conditionVals.add(queryVo.getEndTime());
			conditionVals.add(queryVo.getEndTimeEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _manufactureProcess.status in ("
					+ queryVo.getStatus() + ")");
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _manufactureProcess.status in ("
					+ queryVo.getCurrStatus() + ")");
		}
		else
		{
			jpql.append(" and  not(_manufactureProcess.status=?)");
			Resource finish = Resource.findByProperty(Resource.class, "identifier",
					"Finish").get(0);
			conditionVals.add(finish.getName());
			jpql.append(" and _manufactureProcess.status not in ("
					+ StaticValue.NOT_SHOW_STATUS + ") ");
		}

		if (queryVo.getOptDate() != null) {
			jpql.append(" and _manufactureProcess.optDate between ? and ? ");
			conditionVals.add(queryVo.getOptDate());
			conditionVals.add(queryVo.getOptDateEnd());
		}
		if (queryVo.getSort() != null) {
			jpql.append(" and _manufactureProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _manufactureProcess.resLotType.identifier like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}

		if (queryVo.getVenderName() != null
				&& !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}

		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}

		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}

		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.product.partNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getStationId() != null) {
			jpql.append(" and _manufactureProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		
		jpql.append(" order by _manufactureProcess.shippingDate DESC");
		Page<ManufactureProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (ManufactureProcess manufactureProcess : pages.getData()) {
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureProcessDTO,
						manufactureProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			SubstrateCompanyLot substrateCompanyLot = manufactureProcess
					.getManufactureLot().getSubstrateProcess()
					.getSubstrateCompanyLot();
			SubstrateCustomerLot substrateCustomerLot = substrateCompanyLot
					.getSubstrateCustomerLot();
			manufactureProcessDTO.setVenderName(substrateCustomerLot
					.getVender().getVenderName());
			manufactureProcessDTO.setLotNo(manufactureProcess
					.getManufactureLot().getLotNo());
			manufactureProcessDTO.setCustomerLotNo(substrateCustomerLot
					.getCustomerLotNo());
			manufactureProcessDTO.setPartNumber(substrateCustomerLot
					.getSubstratePart().getPartNo());
			manufactureProcessDTO.setBatchNo(substrateCustomerLot.getBatchNo());
			manufactureProcessDTO.setPon(substrateCustomerLot.getPon());
			if(manufactureProcess.getStatus().indexOf("Waiting") > -1 ){
				manufactureProcessDTO.setEntryDate(manufactureProcess.getCreateDate());
			}else
				manufactureProcessDTO.setEntryDate(manufactureProcess.getShippingDate());
			manufactureProcessDTO.setEntryTime(MyDateUtils.getDayHour(
					manufactureProcessDTO.getEntryDate(), new Date()));
			manufactureProcessDTO.setUserName(substrateCustomerLot
					.getCreateUser().getName());
			manufactureProcessDTO.setCurrStatus(manufactureProcess.getStatus());
			manufactureProcessDTO.setProductName(manufactureProcess
					.getManufactureLot().getProduct().getProductName());
			manufactureProcessDTO.setPackSize(manufactureProcess.getManufactureLot().getPackSize());
			manufactureProcessDTO.setSubBatch(substrateCompanyLot.getLotNo());
			Set<Location> locations = manufactureProcess
					.getManufactureLocations();
			String stockPos = "";
			for (Location location : locations) {
				stockPos += ";" + location.getLoctionCode();
			}
			if (stockPos.length() > 5) {
				manufactureProcessDTO.setStockPos(stockPos.substring(1));
			}
			manufactureProcessDTO.setGuaranteePeriod(substrateCustomerLot
					.getExpiryDate());
			manufactureProcessDTO.setProductionDate(substrateCustomerLot
					.getManufactureDate());
			manufactureProcessDTO.setLotType(manufactureProcess.getResLotType()
					.getIdentifier());
			
			manufactureProcessDTO.setStep(manufactureProcess.getStation().getStationNameEn());
			if (manufactureProcess.getRejectStation() != null) {
				manufactureProcessDTO.setRejectStation(manufactureProcess.getRejectStation().getStationNameEn());
			}
			if (manufactureProcess.getEquipment() != null) {
				manufactureProcessDTO.setEquipmentName(manufactureProcess
						.getEquipment().getEquipment());
			}
			List<RejectCodeItemDTO> rejectCodeItemDTOs = new ArrayList<RejectCodeItemDTO>();
			for(RejectCodeItem rejectCodeitem :	manufactureProcess.getRejectCodeItems()){
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
			ManufactureStatusOptLogDTO manufactureStatusOptLogDTO = this.findManufactureStatusOptLogByProcess(manufactureProcess.getId(),false);
			if(manufactureStatusOptLogDTO != null){
				manufactureProcessDTO.setHoldReason(manufactureStatusOptLogDTO.getComments());
			}
			result.add(manufactureProcessDTO);
		}
		return new Page<ManufactureProcessDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	public Map<String, Object> pageQueryManufactureProcessTotal(
			ManufactureProcessDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_manufactureProcess.qtyIn),sum(_manufactureProcess.stripQtyIn),count(*) from ManufactureProcess _manufactureProcess   left join _manufactureProcess.createUser  left join _manufactureProcess.lastModifyUser  left join _manufactureProcess.station  left join _manufactureProcess.equipment  left join _manufactureProcess.manufactureLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _manufactureProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _manufactureProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _manufactureProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _manufactureProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getEndTime() != null) {
			jpql.append(" and _manufactureProcess.endTime between ? and ? ");
			conditionVals.add(queryVo.getEndTime());
			conditionVals.add(queryVo.getEndTimeEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _manufactureProcess.status in ("
					+ queryVo.getStatus() + ")");
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _manufactureProcess.status in ("
					+ queryVo.getCurrStatus() + ")");
		}

		if (queryVo.getOptDate() != null) {
			jpql.append(" and _manufactureProcess.optDate between ? and ? ");
			conditionVals.add(queryVo.getOptDate());
			conditionVals.add(queryVo.getOptDateEnd());
		}
		if (queryVo.getSort() != null) {
			jpql.append(" and _manufactureProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _manufactureProcess.resLotType.identifier like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}

		if (queryVo.getVenderName() != null
				&& !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}

		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}

		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}

		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.product.partNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _manufactureProcess.manufactureLot.manufactureCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getStationId() != null) {
			jpql.append(" and _manufactureProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and  not(_manufactureProcess.status=?)");
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		conditionVals.add(finish.getName());
		jpql.append(" and _manufactureProcess.status not in ("
				+ StaticValue.NOT_SHOW_STATUS + ") ");
		jpql.append(" order by _manufactureProcess.shippingDate DESC");
		Object[] object = (Object[]) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dieTotal", String.valueOf(object[0]));
		resultMap.put("stripTotal", String.valueOf(object[1]));
		resultMap.put("countLot", String.valueOf(object[2]));
		return resultMap;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByManufactureProcess(Long id) {
		String jpql = "select e from ManufactureProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByManufactureProcess(Long id) {
		String jpql = "select e from ManufactureProcess o right join o.lastModifyUser e where o.id=?";
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
	public StationDTO findStationByManufactureProcess(Long id) {
		String jpql = "select e from ManufactureProcess o right join o.station e where o.id=?";
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
	public Page<LocationDTO> findLocationsByManufactureProcess(Long id,
			int currentPage, int pageSize) {
		List<LocationDTO> result = new ArrayList<LocationDTO>();
		String jpql = "select e from ManufactureProcess o right join o.locations e where o.id=?";
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
	public EquipmentDTO findEquipmentByManufactureProcess(Long id) {
		String jpql = "select e from ManufactureProcess o right join o.equipment e where o.id=?";
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
	public ManufactureLotDTO findManufactureLotByManufactureProcess(Long id) {
		String jpql = "select e from ManufactureProcess o right join o.manufactureLot e where o.id=?";
		ManufactureLot result = (ManufactureLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ManufactureLotDTO dto = new ManufactureLotDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	public ManufactureProcessDTO saveManufactureProcessReceive(
			ManufactureProcessDTO manufactureProcessDTO,
			Long manufactureCustomerLotId, String locationIds) {
		return null;
	}

	public ManufactureProcessDTO saveNextProcess(
			ManufactureProcessDTO manufactureProcessDTO) {
		ManufactureProcess nextManufactureProcess = new ManufactureProcess();
		ManufactureProcess nowManufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		ManufactureLot manufactureLot = nowManufactureProcess
				.getManufactureLot();
		Set<Location> locations = new HashSet<Location>();
		User user;
		if (manufactureProcessDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		nextManufactureProcess.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		nextManufactureProcess.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		nextManufactureProcess.setCreateUser(user);
		nextManufactureProcess.setLastModifyUser(user);
		nextManufactureProcess.setShippingDate(manufactureProcessDTO
				.getShippingDate());
		nextManufactureProcess.setManufactureLot(manufactureLot);
		if(nowManufactureProcess.getResHold() != null){
			nextManufactureProcess.setResHold(nowManufactureProcess.getResHold());
		}

		if (manufactureProcessDTO.getStartFlow() != null) {
			DefineStationProcess defineStationProcess = DefineStationProcess
					.load(DefineStationProcess.class,
							manufactureProcessDTO.getStartFlow());
			nextManufactureProcess.getManufactureLot().setDefineStationProcess(defineStationProcess);
		}
		nextManufactureProcess.setManufactureLocations(locations);
		nextManufactureProcess.setQtyIn(nowManufactureProcess.getQtyOut());
		nextManufactureProcess.setStripQtyIn(nowManufactureProcess
				.getStripQtyOut());
		
		if(manufactureProcessDTO.getScrapsQtyOut()!=null){
			nowManufactureProcess.setScrapsQtyOut(manufactureProcessDTO.getScrapsQtyOut());
		}else{
			nowManufactureProcess.setScrapsQtyOut(0);
		}
		if(manufactureProcessDTO.getQtyOut() != null){
			nowManufactureProcess.setQtyOut(manufactureProcessDTO.getQtyOut());
		}else{
			nowManufactureProcess.setQtyOut(nowManufactureProcess.getQtyIn());
		}
		if(manufactureProcessDTO.getStripQtyOut() != null && manufactureProcessDTO.getStripQtyOut() > 0){
			nowManufactureProcess.setStripQtyOut(manufactureProcessDTO.getStripQtyOut());
		}else{
			nowManufactureProcess.setStripQtyOut(nowManufactureProcess.getStripQtyIn());
		}
		if(manufactureProcessDTO.getSampleQtyOut() != null && manufactureProcessDTO.getSampleQtyOut() > 0){
			nowManufactureProcess.setSampleQtyOut(manufactureProcessDTO.getSampleQtyOut());
		}else{
			nowManufactureProcess.setSampleQtyOut(0);
		}
		nowManufactureProcess.setQtyOut(nowManufactureProcess.getQtyOut() - nowManufactureProcess.getScrapsQtyOut());
		nowManufactureProcess.setStripQtyOut(nowManufactureProcess.getStripQtyOut());
		Set<RejectCodeItem> rejectCodeItems = this.saveRejectCodeItems(manufactureProcessDTO.getRejectCodeItemDTOs());
		nowManufactureProcess.setRejectCodeItems(rejectCodeItems);
		nextManufactureProcess.setQtyIn(nowManufactureProcess.getQtyOut());
		nextManufactureProcess.setStripQtyIn(nowManufactureProcess
				.getStripQtyOut());
		//nextManufactureProcess.setQtyOut(nowManufactureProcess.getQtyOut());
		nextManufactureProcess.setStripQtyOut(nowManufactureProcess
				.getStripQtyOut());
		
		
		DefineStationProcess defineStationProcess = manufactureLot.getDefineStationProcess();
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.get(
					DefineStationProcess.class, Long.valueOf(6));
		}
		Set<Station> stations = defineStationProcess.getStations();

		Station nextStation = null;
		for (Station station : stations) {
			if (station.getSequence() > nowManufactureProcess.getStation()
					.getSequence()) {
				nextStation = station;
				break;
			}
		}
		if(nextStation == null){
			return null;
		}
		if(manufactureProcessDTO.getNextStationId() != null){
			nextStation = Station.load(Station.class, manufactureProcessDTO.getNextStationId());
		}
		nextManufactureProcess.setStation(nextStation);
		if(nowManufactureProcess.getFutureStation() != null){
			nextManufactureProcess.setFutureStation(nowManufactureProcess.getFutureStation());
		}
		Resource resource;
		if (nowManufactureProcess.getFutureStation() != null
				&& nextStation.getId().equals(
						nowManufactureProcess.getFutureStation().getId())) {
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment
					.findRelationByResource(Long.valueOf(nowManufactureProcess.getResHold().getId())).get(0);
			resource = rsourceLineAssignment.getParent();
			//nowManufactureProcess.setFutureStation(null);
			nextManufactureProcess.setFutureStation(null);
		} else {
			resource = Resource.findByProperty(Resource.class, "identifier",
					"Waiting").get(0);
		}

		nextManufactureProcess.setStatus(resource.getName());
		nextManufactureProcess.setResLotType(nowManufactureProcess
				.getResLotType());
		nextManufactureProcess.save();
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		nowManufactureProcess.setStatus(finish.getName());
		nowManufactureProcess.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		nowManufactureProcess.setLastModifyUser(user);
		if (nowManufactureProcess.getEquipment() != null) {
			Equipment equipment = nowManufactureProcess.getEquipment();
			equipment.setStatus("IDLE");
			equipment.save();
		}
		nowManufactureProcess.save();
		locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(nowManufactureProcess.getManufactureLocations()));
		if(nowManufactureProcess.getScrapsQtyOut() > 0){
			this.splitScrapsLot(rejectCodeItems,nowManufactureProcess, manufactureProcessDTO, user);
		}
		if(nextManufactureProcess.getFutureStation() != null){
			this.saveFutureOpt(nextManufactureProcess, nowManufactureProcess.getId());
		}
		this.saveOptLog(manufactureProcessDTO, nowManufactureProcess, user, "saveNextProcess");
		manufactureProcessDTO.setId((java.lang.Long) nextManufactureProcess
				.getId());
		return manufactureProcessDTO;
	}

	private void saveFutureOpt(ManufactureProcess nextManufactureProcess, Long nowManufactureProcessId){
		ManufactureStatusOptLogDTO manufactureStatusOptLogDTO = this.findManufactureStatusOptLogByProcess(nowManufactureProcessId, true);
		ManufactureStatusOptLog manufactureStatusOptLog = ManufactureStatusOptLog.get(ManufactureStatusOptLog.class, manufactureStatusOptLogDTO.getId());
		ManufactureStatusOptLog nextManufactureStatusOptLog = new ManufactureStatusOptLog();
		nextManufactureStatusOptLog.setCreateDate(manufactureStatusOptLog.getCreateDate());
		nextManufactureStatusOptLog.setLastModifyDate(manufactureStatusOptLog.getLastModifyDate());
		nextManufactureStatusOptLog.setCreateUser(manufactureStatusOptLog.getCreateUser());
		nextManufactureStatusOptLog.setLastModifyUser(manufactureStatusOptLog.getLastModifyUser());
		nextManufactureStatusOptLog.setFutureStation(manufactureStatusOptLog.getFutureStation());
		nextManufactureStatusOptLog.setOptLog(manufactureStatusOptLog.getOptLog());
		nextManufactureStatusOptLog.setHoldCode(manufactureStatusOptLog.getHoldCode());
		nextManufactureStatusOptLog.setResHold(manufactureStatusOptLog.getResHold());
		nextManufactureStatusOptLog.setManufactureProcess(nextManufactureProcess);
		nextManufactureStatusOptLog.setEngineerName(manufactureStatusOptLog.getEngineerName());
		nextManufactureStatusOptLog.save();
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private Set<RejectCodeItem> saveRejectCodeItems( List<RejectCodeItemDTO> rejectCodeItemDTOs){
		Set<RejectCodeItem> rejectCodeItems = new HashSet<RejectCodeItem>();
		if(rejectCodeItemDTOs != null && rejectCodeItemDTOs.size() > 0){
			for(RejectCodeItemDTO rejectCodeItemDTO : rejectCodeItemDTOs){
				RejectCodeItem rejectCodeItem = new RejectCodeItem();
				try {
					BeanUtils.copyProperties(rejectCodeItem, rejectCodeItemDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//rejectCodeItem.setWaferProcess(nowWaferProcess);
				rejectCodeItem.save();
				rejectCodeItems.add(rejectCodeItem);
			}
			//nowWaferProcess.setRejectCodeItems(rejectCodeItems);
		}
		return rejectCodeItems;
	}
	
	private Set<Station> sortStations(Set<Station> stations) {
		Set<Station> ps = new TreeSet<Station>(new Comparator<Station>() {
			public int compare(Station o1, Station o2) {
				return o1.getSequence().compareTo(o2.getSequence());
				// 这是正序排列，如果想倒序的话，调换o1、o2的位置，
			}
		});
		ps.addAll(stations);
		return ps;
	}

	public String saveSplitManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String useraccount,
			Integer stripQty, Integer qty, String comments) {
		User user;
		if (manufactureProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批
		ManufactureProcess nowManufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		ManufactureLot manufactureLot = nowManufactureProcess
				.getManufactureLot();
		if(stripQty >= nowManufactureProcess.getStripQtyIn() || qty >= nowManufactureProcess.getQtyIn())
		{
			return "wafer.split.stripQtyEqualZero";
		}
		List<String> lotNoList = this.getSplitLotNo(nowManufactureProcess
				.getManufactureLot().getLotNo(), 2);
		String tempLocationIds;
		for (int i = 0; i < lotNoList.size(); i++) {

			ManufactureLot splitManufactureLot = new ManufactureLot();
			splitManufactureLot.setSubstrateProcess(manufactureLot
					.getSubstrateProcess());
			splitManufactureLot.setLotNo(lotNoList.get(i));
			splitManufactureLot.setParentManufactureLot(manufactureLot);
			splitManufactureLot.setCreateDate(manufactureProcessDTO
					.getCreateDate());
			splitManufactureLot.setCreateUser(user);
//			splitManufactureLot.setLastModifyDate(manufactureProcessDTO
//					.getLastModifyDate());
			splitManufactureLot.setLastModifyDate(new Date());
			splitManufactureLot.setLastModifyUser(user);
			splitManufactureLot.setProduct(manufactureLot.getProduct());
			splitManufactureLot.setDefineStationProcess(manufactureLot.getDefineStationProcess());
			splitManufactureLot.save();

			ManufactureProcess splitManufactureProcess = new ManufactureProcess();
			splitManufactureProcess.setManufactureLot(splitManufactureLot);
			if (i == 0) {
				splitManufactureProcess.setQtyIn(nowManufactureProcess
						.getQtyIn() - qty);
				splitManufactureProcess.setStripQtyIn(nowManufactureProcess
						.getStripQtyIn() - stripQty);
				tempLocationIds=this.getLocationIdsByLocations(nowManufactureProcess
						.getManufactureLocations());
			} else {
				splitManufactureProcess.setQtyIn(qty);
				splitManufactureProcess.setStripQtyIn(stripQty);
				tempLocationIds=manufactureProcessDTO.getLocationIds();
			}
			splitManufactureProcess.setShippingDate(nowManufactureProcess
					.getShippingDate());
			splitManufactureProcess
					.setStatus(nowManufactureProcess.getStatus());
			splitManufactureProcess.setStation(nowManufactureProcess
					.getStation());
//			splitManufactureProcess.setCreateDate(manufactureProcessDTO
//					.getCreateDate());
			splitManufactureProcess.setCreateDate(nowManufactureProcess.getCreateDate());
			splitManufactureProcess.setCreateUser(user);
			splitManufactureProcess.setLastModifyDate(manufactureProcessDTO
					.getLastModifyDate());
			splitManufactureProcess.setLastModifyUser(user);
			splitManufactureProcess.setResLotType(nowManufactureProcess
					.getResLotType());
			if(splitManufactureProcess.getResHold() != null){
				splitManufactureProcess.setResHold(nowManufactureProcess.getResHold());
			}
			if(splitManufactureProcess.getFutureStation() != null){
				splitManufactureProcess.setFutureStation(nowManufactureProcess.getFutureStation());
			}
			// if (nowManufactureProcess.getEquipment() != null) {
			// splitManufactureProcess.setEquipment(nowManufactureProcess.getEquipment());
			// }
			splitManufactureProcess.save();
			this.locationApplication.updateLocationsManufactureProcess(tempLocationIds, splitManufactureProcess.getId());
		}
		nowManufactureProcess.setStatus("Split");
		nowManufactureProcess.save();
		// 保存操作信息 --start
		ManufactureOptLog manufactureSplitOptLog = new ManufactureOptLog();
		OptLog optLog = new OptLog();

		manufactureSplitOptLog.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		manufactureSplitOptLog.setCreateUser(user);
		manufactureSplitOptLog.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		manufactureSplitOptLog.setLastModifyUser(user);
		manufactureSplitOptLog.setStripQty(stripQty);
		manufactureSplitOptLog.setQty(qty);
		manufactureSplitOptLog.setManufactureProcess(nowManufactureProcess);
		manufactureSplitOptLog.setType("split");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		manufactureSplitOptLog.setOptLog(optLog);
		manufactureSplitOptLog.save();
		// 保存操作信息 --end
		return "success";
	}
	
	private void splitScrapsLot(Set<RejectCodeItem> rejectCodeItems,ManufactureProcess nowManufactureProcess,ManufactureProcessDTO manufactureProcessDTO,User user){
		ManufactureLot splitManufactureLot = new ManufactureLot();
		splitManufactureLot.setSubstrateProcess(nowManufactureProcess.getManufactureLot()
				.getSubstrateProcess());
		List<String> lotNoList = this.getSplitLotNo(nowManufactureProcess
				.getManufactureLot().getLotNo(), 2);
		splitManufactureLot.setLotNo(lotNoList.get(1));
		//splitManufactureLot.setParentManufactureLot(nowManufactureProcess.getManufactureLot());
		splitManufactureLot.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		splitManufactureLot.setCreateUser(user);
		splitManufactureLot.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		splitManufactureLot.setLastModifyUser(user);
		splitManufactureLot.setFromManufactureProcess(nowManufactureProcess);
		splitManufactureLot.setProduct(nowManufactureProcess.getManufactureLot().getProduct());
		splitManufactureLot.save();

		ManufactureProcess splitManufactureProcess = new ManufactureProcess();
		splitManufactureProcess.setRejectStation(nowManufactureProcess.getStation());
		splitManufactureProcess.setRejectCodeItems(rejectCodeItems);
		splitManufactureProcess.setManufactureLot(splitManufactureLot);
		splitManufactureProcess.setQtyIn(manufactureProcessDTO.getScrapsQtyOut());
		splitManufactureProcess.setStripQtyIn(manufactureProcessDTO.getStripQtyOut());
		splitManufactureProcess.setQtyOut(manufactureProcessDTO.getScrapsQtyOut());
		splitManufactureProcess.setStripQtyOut(manufactureProcessDTO.getStripQtyOut());	
		splitManufactureProcess.setShippingDate(nowManufactureProcess
				.getShippingDate());
		Resource resource = Resource.findByProperty(Resource.class, "identifier",
				"Waiting").get(0);
		splitManufactureProcess
				.setStatus(resource.getName());
		Station station = Station.load(Station.class, (long)1002);
		splitManufactureProcess.setStation(station);
		splitManufactureProcess.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		splitManufactureProcess.setCreateUser(user);
		splitManufactureProcess.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		splitManufactureProcess.setLastModifyUser(user);
		splitManufactureProcess.setResLotType(nowManufactureProcess
				.getResLotType());
		if(splitManufactureProcess.getResHold() != null){
			splitManufactureProcess.setResHold(nowManufactureProcess.getResHold());
		}
		if(splitManufactureProcess.getFutureStation() != null){
			splitManufactureProcess.setFutureStation(nowManufactureProcess.getFutureStation());
		}
		splitManufactureProcess.save();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureProcessDTO> getSplitManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, Integer stripQty,
			Integer qty) {
		// 处理分批
		ManufactureProcess nowManufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		List<String> lotNoList = this.getSplitLotNo(nowManufactureProcess
				.getManufactureLot().getLotNo(), 2);
		List<ManufactureProcessDTO> list = new ArrayList<ManufactureProcessDTO>();
		for (int i = 0; i < lotNoList.size(); i++) {
			ManufactureProcessDTO sPDTO = new ManufactureProcessDTO();
			ManufactureLot nowManufactureLot = nowManufactureProcess
					.getManufactureLot();
			SubstrateCompanyLot substrateCompanyLot = nowManufactureLot
					.getSubstrateProcess().getSubstrateCompanyLot();
			SubstrateCustomerLot substrateCustomerLot = substrateCompanyLot
					.getSubstrateCustomerLot();

			sPDTO.setLotNo(lotNoList.get(i));
			sPDTO.setCustomerLotNo(nowManufactureLot.getSubstrateProcess()
					.getSubstrateCompanyLot().getSubstrateCustomerLot()
					.getCustomerLotNo());
			sPDTO.setPartNumber(nowManufactureLot.getSubstrateProcess()
					.getSubstrateCompanyLot().getSubstrateCustomerLot()
					.getSubstratePart().getPartNo());
			if (i == 0) {
				sPDTO.setQtyIn(nowManufactureProcess.getQtyIn() - qty);
				sPDTO.setStripQtyIn(nowManufactureProcess.getStripQtyIn()
						- stripQty);
				sPDTO.setStockPos(this
						.getLocationsByLocations(nowManufactureProcess
								.getManufactureLocations()));
			} else {
				sPDTO.setQtyIn(qty);
				sPDTO.setStripQtyIn(stripQty);
				sPDTO.setStockPos(this.getLocationsById(manufactureProcessDTO
						.getLocationIds()));
			}
			sPDTO.setVenderName(substrateCustomerLot.getVender()
					.getVenderName());
			// sPDTO.setLotNo(nowManufactureProcess.getManufactureLot().getLotNo());
			sPDTO.setCustomerLotNo(substrateCustomerLot.getCustomerLotNo());
			sPDTO.setPartNumber(substrateCustomerLot.getSubstratePart().getPartNo());
			sPDTO.setBatchNo(substrateCustomerLot.getBatchNo());
			sPDTO.setPon(substrateCustomerLot.getPon());
//			sPDTO.setEntryDate(new Date());
			sPDTO.setEntryDate(nowManufactureProcess.getCreateDate());
			sPDTO.setEntryTime(MyDateUtils.getDayHour(
					sPDTO.getEntryDate(), new Date()));
			sPDTO.setUserName(substrateCustomerLot.getCreateUser().getName());
			sPDTO.setCurrStatus(nowManufactureProcess.getStatus());
			sPDTO.setStockPos("");
			sPDTO.setGuaranteePeriod(substrateCustomerLot.getExpiryDate());
			sPDTO.setProductionDate(substrateCustomerLot.getManufactureDate());
			sPDTO.setCurrStatus(nowManufactureProcess.getStatus());
			sPDTO.setStatus(nowManufactureProcess.getStatus());
			sPDTO.setProductName(nowManufactureProcess.getManufactureLot()
					.getProduct().getProductName());
			sPDTO.setSubBatch(substrateCompanyLot.getLotNo());
			sPDTO.setLotType(nowManufactureProcess.getResLotType()
					.getIdentifier());
			if (nowManufactureProcess.getEquipment() != null) {
				sPDTO.setEquipmentName(nowManufactureProcess.getEquipment()
						.getEquipment());
			}
			list.add(sPDTO);
		}
		return list;
	}

	private List<String> getLotNoList(String lotNo, int lot) {
		String[] str = lotNo.split("-");// F001-14380002-01
		List<String> lotList = new ArrayList<String>();
		int n = Integer.parseInt(str[2]);
		for (int i = n; i < n + lot; i++) {
			String num = "00" + String.valueOf(i);
			num = num.substring(num.length() - 2);
			lotList.add(str[0] + "-" + str[1] + "-" + num);
		}
		return lotList;
	}

	private List<String> getSplitLotNo(String lotNo, int lot) {
		String[] arr = lotNo.split("-");
		String maxLotNo = this.findMaxLotNoByLotNo(arr[0] + "-" + arr[1]);
		List<String> lotNoList = this.getLotNoList(maxLotNo, lot);
		lotNoList.set(0, lotNo);
		return lotNoList;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private String findMaxLotNoByLotNo(String lotNo) {
		String jpql = "select max(o.lotNo) from ManufactureLot o where o.lotNo like ?";
		String maxLotNo = (String) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { "%" + lotNo + "%" })
				.singleResult();
		return maxLotNo;
	}

	public void changeStatusManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		User user;
		if (manufactureProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		ManufactureProcess manufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		manufactureProcess.setStatus(manufactureProcessDTO.getStatus());
		if(manufactureProcessDTO.getShippingDate() != null){
			manufactureProcess.setShippingDate(manufactureProcessDTO.getShippingDate());
		}
		manufactureProcess.setLastModifyDate(manufactureProcessDTO
				.getCreateDate());
		manufactureProcess.setLastModifyUser(user);

		ManufactureStatusOptLog manufactureStatusOptLog = new ManufactureStatusOptLog();
		OptLog optLog = new OptLog();
		if (holdCodeId != null && !"".equals(holdCodeId)) {
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment
					.findRelationByResource(Long.valueOf(holdCodeId)).get(0);
			manufactureProcess.setResHold(rsourceLineAssignment.getChild());
			manufactureProcess.setStatus(rsourceLineAssignment.getParent()
					.getName());
			manufactureStatusOptLog.setHoldCode(rsourceLineAssignment.getChild()
					.getDesc());
			manufactureStatusOptLog.setResHold(rsourceLineAssignment.getParent());
		} else {
			manufactureProcess.setStatus(manufactureProcessDTO.getCurrStatus());
			manufactureStatusOptLog.setHoldCode(holdCode);
		}
		if (manufactureProcessDTO.getEquipmentId() != null) {
			Equipment equipment = Equipment.load(Equipment.class,
					manufactureProcessDTO.getEquipmentId());
			equipment.setManufactureProcess(manufactureProcess);
			equipment.setStatus(manufactureProcessDTO.getEquipmentStatus());
			manufactureProcess.setEquipment(equipment);
			equipment.save();
		}
		else
		{
			if(manufactureProcess.getEquipment() != null && "IDLE".equals(manufactureProcessDTO.getEquipmentStatus())){
				Equipment equipment = Equipment.load(Equipment.class,
						manufactureProcess.getEquipment().getId());
				equipment.setManufactureProcess(null);
				equipment.setStatus(manufactureProcessDTO.getEquipmentStatus());
				manufactureProcess.setEquipment(null);
				equipment.save();
			}
		}
		manufactureStatusOptLog.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		manufactureStatusOptLog.setCreateUser(user);
		manufactureStatusOptLog.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		manufactureStatusOptLog.setLastModifyUser(user);
		manufactureStatusOptLog.setManufactureProcess(manufactureProcess);
		if (manufactureProcessDTO.getFutureHoldStationId() != null) {
			Station station = Station.get(Station.class,
					manufactureProcessDTO.getFutureHoldStationId());
			if (station != null){
				manufactureProcess.setFutureStation(station);
				manufactureStatusOptLog.setFutureStation(station);
				manufactureProcess.setStatus(holdCode);
				manufactureStatusOptLog.setResHold(manufactureProcess.getResHold());
			}
		}
		if(manufactureProcessDTO.getCurrStatus() != null && !"".equals(manufactureProcessDTO.getCurrStatus()))
			manufactureProcess.setStatus(manufactureProcessDTO.getCurrStatus());
		manufactureProcess.save();
		//manufactureStatusOptLog.setHoldCode(holdCode);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		if(manufactureProcessDTO.getEngineerName() != null){
			manufactureStatusOptLog.setEngineerName(manufactureProcessDTO.getEngineerName());
		}
		manufactureStatusOptLog.setOptLog(optLog);
		manufactureStatusOptLog.save();
	}

	public void holdManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String useraccount, String comments) {
		// manufactureProcessDTO.setStatus("Hold");
		if ("pass".equals(holdCode.toLowerCase())) {
			manufactureProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		} else
			manufactureProcessDTO.setCurrStatus(holdCode);
		this.changeStatusManufactureProcess(manufactureProcessDTO, holdCode,
				null, useraccount, comments);

	}

	public void engDispManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String useraccount, String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			manufactureProcessDTO.setStatus("Pass");
			holdCode = "Eng. Pass";
		} else
			manufactureProcessDTO.setStatus(holdCode);
		this.engDispManufactureProcess(manufactureProcessDTO, holdCode, null,
				useraccount, comments);
	}

	public String saveMergeManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String mergeIds,
			String useraccount, String comments) {
		User user;
		if (manufactureProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批

		ManufactureProcess nowManufactureProcess = new ManufactureProcess();
		ManufactureLot mergeManufactureLot = new ManufactureLot();
		String[] idArray = mergeIds.split(",");
		List<ManufactureProcess> list = new ArrayList<ManufactureProcess>();
		int dieQtyTotal = 0;
		int manufactureQtyTotal = 0;
		ManufactureProcess tempManufactureProcess = null;
		for (String id : idArray) {
			ManufactureProcess manufactureProcess = ManufactureProcess.load(
					ManufactureProcess.class, Long.parseLong(id));
			if (tempManufactureProcess != null
					&& tempManufactureProcess.getManufactureLot()
							.getSubstrateProcess().getSubstrateCompanyLot()
							.getSubstrateCustomerLot().getSubstratePart().getId() != manufactureProcess
							.getManufactureLot().getSubstrateProcess()
							.getSubstrateCompanyLot().getSubstrateCustomerLot()
							.getSubstratePart().getId()) {
				return "Please select same product!";
			}
			this.locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(manufactureProcess.getManufactureLocations()));
			dieQtyTotal += manufactureProcess.getQtyIn();
			manufactureQtyTotal += manufactureProcess.getStripQtyIn();
			list.add(manufactureProcess);
		}
		list = sortManufactureProcess(list);
		nowManufactureProcess = list.get(list.size() - 1);
		
		ManufactureLot nowManufactureLot = nowManufactureProcess
				.getManufactureLot();
		mergeManufactureLot.setSubstrateProcess(nowManufactureLot
				.getSubstrateProcess());
		mergeManufactureLot.setProduct(nowManufactureLot.getProduct());
		mergeManufactureLot.setLotNo(nowManufactureLot.getLotNo());
		mergeManufactureLot.setParentManufactureLot(nowManufactureLot);
		mergeManufactureLot
				.setCreateDate(manufactureProcessDTO.getCreateDate());
		mergeManufactureLot.setCreateUser(user);
		mergeManufactureLot.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());

		mergeManufactureLot.setLastModifyUser(user);
		mergeManufactureLot.setDefineStationProcess(nowManufactureLot.getDefineStationProcess());
		mergeManufactureLot.save();
		ManufactureProcess mergeManufactureProcess = new ManufactureProcess();
		mergeManufactureProcess.setManufactureLot(mergeManufactureLot);
		mergeManufactureProcess.setShippingDate(nowManufactureProcess
				.getShippingDate());
		mergeManufactureProcess.setStatus(nowManufactureProcess.getStatus());
		mergeManufactureProcess.setStation(nowManufactureProcess.getStation());
//		mergeManufactureProcess.setCreateDate(manufactureProcessDTO
//				.getCreateDate());
		mergeManufactureProcess.setCreateDate(nowManufactureProcess.getCreateDate());
		mergeManufactureProcess.setCreateUser(user);
//		mergeManufactureProcess.setLastModifyDate(manufactureProcessDTO
//				.getLastModifyDate());
		mergeManufactureProcess.setLastModifyDate(new Date());
		mergeManufactureProcess.setLastModifyUser(user);
		mergeManufactureProcess.setQtyIn(dieQtyTotal);
		mergeManufactureProcess.setStripQtyIn(manufactureQtyTotal);
		mergeManufactureProcess.setResLotType(nowManufactureProcess
				.getResLotType());
		mergeManufactureProcess.setManufactureLocations(this
				.getLocations(manufactureProcessDTO.getLocationIds()));
		if(mergeManufactureProcess.getResHold() != null){
			mergeManufactureProcess.setResHold(nowManufactureProcess.getResHold());
		}
		if(mergeManufactureProcess.getFutureStation() != null){
			mergeManufactureProcess.setFutureStation(nowManufactureProcess.getFutureStation());
		}
		mergeManufactureProcess.save();
		this.locationApplication.updateLocationsManufactureProcess(manufactureProcessDTO.getLocationIds(), mergeManufactureProcess.getId());
		nowManufactureProcess.setStatus("Waiting");
		nowManufactureProcess.save();
		for (ManufactureProcess manufactureProcess : list) {
			ManufactureLot manufactureLot = new ManufactureLot();
			manufactureProcess.setStatus("Merge");
			manufactureLot = manufactureProcess.getManufactureLot();
			manufactureLot.setMergeManufactureLot(mergeManufactureLot);
			manufactureLot.save();
			manufactureProcess.save();
		}

		// 保存操作信息 --start
		manufactureProcessDTO.setComments(comments);
		this.saveOptLog(manufactureProcessDTO, nowManufactureProcess, user, "Merge");
		// 保存操作信息 --end
		return "success";
	}
	
	private List<ManufactureProcess> sortManufactureProcess(List<ManufactureProcess> manufactureProcesses) {
		Collections.sort(manufactureProcesses, new Comparator<ManufactureProcess>() {
            public int compare(ManufactureProcess arg0, ManufactureProcess arg1) {
                return arg0.getQtyIn().compareTo(arg1.getQtyIn());
            }
        });
		return manufactureProcesses;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureProcessDTO getMergeManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String mergeIds) {
//		ManufactureProcess nowManufactureProcess = new ManufactureProcess();
		String[] idArray = mergeIds.split(",");
		List<ManufactureProcess> list = new ArrayList<ManufactureProcess>();
		int qtyIn = 0;
		int qtyTotal = 0;
		int stripQtyTotal = 0;
		for (String id : idArray) {
			ManufactureProcess manufactureProcess = ManufactureProcess.get(
					ManufactureProcess.class, Long.parseLong(id));
			list.add(manufactureProcess);
		}
//			if (qtyIn < manufactureProcess.getQtyIn()) {
//				nowManufactureProcess = manufactureProcess;
//				qtyIn = manufactureProcess.getQtyIn();
//			}
		ManufactureProcess nowManufactureProcess = list.get(0);
		ManufactureProcess manufactureProcess = list.get(1);
			if (nowManufactureProcess.getManufactureLot() == null
					|| nowManufactureProcess.getManufactureLot()
							.getSubstrateProcess().getSubstrateCompanyLot()
							.getSubstrateCustomerLot().getSubstratePart().getId() != manufactureProcess
							.getManufactureLot().getSubstrateProcess()
							.getSubstrateCompanyLot().getSubstrateCustomerLot()
							.getSubstratePart().getId()) {
				return null;
			}
//			if(!(nowManufactureProcess.getManufactureLot().getCustomer().getCustomerCode().equals(manufactureProcess.getManufactureLot().getCustomer().getCustomerCode())))
//			{
//				return null;
//			}
			if(!(nowManufactureProcess.getManufactureLot().getLotNo().substring(0, nowManufactureProcess.getManufactureLot().getLotNo().length() - 3).equals(manufactureProcess.getManufactureLot().getLotNo().substring(0, manufactureProcess.getManufactureLot().getLotNo().length() - 3))))
			{
				return null;
			}
			if(!(nowManufactureProcess.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getSubstrateCustomerLot().getBatchNo().equals(manufactureProcess.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getSubstrateCustomerLot().getBatchNo())))
			{
				return null;
			}
			if(!(nowManufactureProcess.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getLotNo().equals(manufactureProcess.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getLotNo())))
			{
				return null;
			}
			if(nowManufactureProcess.getResLotType().getId().intValue() != manufactureProcess.getResLotType().getId().intValue())
			{
				return null;
			}
			if(!(nowManufactureProcess.getStatus().equals(manufactureProcess.getStatus())))
			{
				return null;
			}
			qtyTotal = manufactureProcess.getQtyIn() + nowManufactureProcess.getQtyIn();
			stripQtyTotal = manufactureProcess.getStripQtyIn() + nowManufactureProcess.getStripQtyIn();
			

		ManufactureLot nowManufactureLot = nowManufactureProcess
				.getManufactureLot();
		SubstrateCompanyLot substrateCompanyLot = nowManufactureLot
				.getSubstrateProcess().getSubstrateCompanyLot();
		SubstrateCustomerLot substrateCustomerLot = substrateCompanyLot
				.getSubstrateCustomerLot();

		manufactureProcessDTO.setVenderName(substrateCustomerLot.getVender()
				.getVenderName());
		manufactureProcessDTO.setLotNo(nowManufactureProcess
				.getManufactureLot().getLotNo());
		manufactureProcessDTO.setCustomerLotNo(substrateCustomerLot
				.getCustomerLotNo());
		manufactureProcessDTO.setPartNumber(substrateCustomerLot.getSubstratePart()
				.getPartNo());
		manufactureProcessDTO.setBatchNo(substrateCustomerLot.getBatchNo());
		manufactureProcessDTO.setPon(substrateCustomerLot.getPon());
//		manufactureProcessDTO.setEntryDate(substrateCustomerLot
//				.getShippingDate());
		manufactureProcessDTO.setEntryDate(nowManufactureProcess.getCreateDate());
//		manufactureProcessDTO.setEntryTime(MyDateUtils.getDayHour(
//				substrateCustomerLot.getShippingDate(), new Date()));
		manufactureProcessDTO.setEntryTime(MyDateUtils.getDayHour(
				manufactureProcessDTO.getShippingDate(), new Date()));
		manufactureProcessDTO.setUserName(substrateCustomerLot.getCreateUser()
				.getName());
		manufactureProcessDTO.setCurrStatus(nowManufactureProcess.getStatus());
		Set<Location> locations = nowManufactureProcess
				.getManufactureLocations();
		manufactureProcessDTO.setStockPos(this
				.getLocationsByLocations(locations));
		manufactureProcessDTO.setCustomerLotNo(substrateCustomerLot
				.getCustomerLotNo());
		manufactureProcessDTO.setPartNumber(substrateCustomerLot.getSubstratePart()
				.getPartNo());
		manufactureProcessDTO.setQtyIn(qtyTotal);
		manufactureProcessDTO.setStripQtyIn(stripQtyTotal);
		manufactureProcessDTO.setGuaranteePeriod(substrateCustomerLot
				.getExpiryDate());
		manufactureProcessDTO.setProductionDate(substrateCustomerLot
				.getManufactureDate());
		manufactureProcessDTO.setCurrStatus(nowManufactureProcess.getStatus());
		manufactureProcessDTO.setStatus(nowManufactureProcess.getStatus());
		manufactureProcessDTO.setProductName(nowManufactureProcess
				.getManufactureLot().getProduct().getProductName());
		manufactureProcessDTO.setSubBatch(substrateCompanyLot.getLotNo());
		manufactureProcessDTO.setLotType(nowManufactureProcess.getResLotType()
				.getIdentifier());
		manufactureProcessDTO.setUserName(substrateCustomerLot.getCreateUser()
				.getName());
		if (nowManufactureProcess.getEquipment() != null) {
			manufactureProcessDTO.setEquipmentName(nowManufactureProcess
					.getEquipment().getEquipment());
		}
		return manufactureProcessDTO;
	}
	
	private boolean checkMerge(ManufactureProcess nowManufactureProcess, ManufactureProcess manufactureProcess){
		if(nowManufactureProcess.getManufactureLot() == null){
			return true;
		}else if(nowManufactureProcess.getManufactureLot()
						.getSubstrateProcess().getSubstrateCompanyLot()
						.getSubstrateCustomerLot().getSubstratePart().getId() != manufactureProcess
						.getManufactureLot().getSubstrateProcess()
						.getSubstrateCompanyLot().getSubstrateCustomerLot()
						.getSubstratePart().getId()){
			return true;
		}
		return false;
	}

	public void holdManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			manufactureProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		} else
			manufactureProcessDTO.setCurrStatus(holdCode);
		this.changeStatusManufactureProcess(manufactureProcessDTO, holdCode,
				holdCodeId, useraccount, comments);
	}

	public void engDispManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			manufactureProcessDTO.setCurrStatus("Pass");
			holdCode = "Eng. Pass";
		} else
			manufactureProcessDTO.setCurrStatus(holdCode);
		this.changeStatusManufactureProcess(manufactureProcessDTO, holdCode,
				holdCodeId, useraccount, comments);

	}

	public void changeLotType(ManufactureProcessDTO manufactureProcessDTO,
			String lotType, String useraccount, String comments) {
		User user;
		if (manufactureProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		ManufactureProcess manufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		Resource resource = Resource
				.load(Resource.class, Long.valueOf(lotType));
		manufactureProcess.setResLotType(resource);
		manufactureProcess.setLotType(resource.getIdentifier());
		manufactureProcess.setLastModifyDate(manufactureProcessDTO
				.getCreateDate());
		manufactureProcess.setLastModifyUser(user);

		ManufactureChangeLotTypeOptLog manufactureChangeLotTypeOptLog = new ManufactureChangeLotTypeOptLog();
		OptLog optLog = new OptLog();
		manufactureChangeLotTypeOptLog.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		manufactureChangeLotTypeOptLog.setCreateUser(user);
		manufactureChangeLotTypeOptLog.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		manufactureChangeLotTypeOptLog.setLastModifyUser(user);
		manufactureChangeLotTypeOptLog
				.setManufactureProcess(manufactureProcess);
		manufactureChangeLotTypeOptLog.setLotType(lotType);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		manufactureChangeLotTypeOptLog.setOptLog(optLog);
		manufactureChangeLotTypeOptLog.save();
	}

	public void changeLocations(ManufactureProcessDTO manufactureProcessDTO,
			String locationIds, String useraccount, String comments) {
		User user;
		if (manufactureProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		Set<Location> locations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location location = Location.get(
					Location.class, locationId);
			locations.add(location);
		}
		ManufactureProcess manufactureProcess = ManufactureProcess.get(
				ManufactureProcess.class, manufactureProcessDTO.getId());
		for(Location loction : manufactureProcess.getManufactureLocations()){
			loction.setManufactureProcess(null);
		}
		manufactureProcess.setManufactureLocations(locations);
		for (Location loc : locations) {
			loc.setManufactureProcess(manufactureProcess);
			loc.save();
		}
		manufactureProcess.setLastModifyDate(manufactureProcessDTO
				.getCreateDate());
		manufactureProcess.setLastModifyUser(user);
		manufactureProcess.save();
		ManufactureChangeLocationOptLog manufactureChangeLocationOptLog = new ManufactureChangeLocationOptLog();
		OptLog optLog = new OptLog();
		manufactureChangeLocationOptLog.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		manufactureChangeLocationOptLog.setCreateUser(user);
		manufactureChangeLocationOptLog.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		manufactureChangeLocationOptLog.setLastModifyUser(user);
		manufactureChangeLocationOptLog
				.setManufactureProcess(manufactureProcess);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		manufactureChangeLocationOptLog.setOptLog(optLog);
		manufactureChangeLocationOptLog.save();
	}

	public void futureHoldManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		manufactureProcessDTO.setCurrStatus("Waiting");
		this.changeStatusManufactureProcess(manufactureProcessDTO, holdCode,
				holdCodeId, useraccount, comments);

	}

	public void trackIn(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount, String comments) {
		manufactureProcessDTO.setCurrStatus("Running");
		manufactureProcessDTO.setEquipmentStatus("RUN");
		this.changeStatusManufactureProcess(manufactureProcessDTO, "Running",
				null, useraccount, comments);
	}

	public void abortStep(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount, String comments) {
		manufactureProcessDTO.setCurrStatus("Waiting");
		manufactureProcessDTO.setEquipmentStatus("IDLE");
//		ManufactureProcess manufactureProcess = ManufactureProcess.get(ManufactureProcess.class,
//				manufactureProcessDTO.getId());
//		if (manufactureProcess.getEquipment() != null) {
//			manufactureProcessDTO.setEquipmentId(manufactureProcess.getEquipment().getId());
//		}
		this.changeStatusManufactureProcess(manufactureProcessDTO, "Waiting",
				null, useraccount, comments);
	}
	
	public void release(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount, String comments) {
		ManufactureStatusOptLogDTO manufactureStatusOptLogDTO = this.findLastManufactureStatusOptLogByProcess(manufactureProcessDTO.getId());
		if(manufactureStatusOptLogDTO.getHoldCode() != null){
			manufactureProcessDTO.setCurrStatus(manufactureStatusOptLogDTO.getHoldCode());
		}else{
			manufactureProcessDTO.setCurrStatus("Waiting");
			manufactureProcessDTO.setEquipmentStatus("IDLE");
		}
		ManufactureProcess manufactureProcess = ManufactureProcess.get(ManufactureProcess.class,
				manufactureProcessDTO.getId());
		manufactureProcess.setFutureStation(null);
		if (manufactureProcess.getEquipment() != null) {
			manufactureProcessDTO.setEquipmentId(manufactureProcess.getEquipment().getId());
		}
		manufactureProcessDTO.setShippingDate(null);
		this.changeStatusManufactureProcess(manufactureProcessDTO, manufactureProcessDTO.getCurrStatus(),
				null, useraccount, comments);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureStatusOptLogDTO findLastManufactureStatusOptLogByProcess(
			Long id) {
		String jpql = "select o from ManufactureStatusOptLog o inner join o.manufactureProcess e where e.id=? ";
		jpql+="order by o.createDate desc";
		List<ManufactureStatusOptLog> result = (List<ManufactureStatusOptLog>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id }).
				list();
		ManufactureStatusOptLogDTO dto = new ManufactureStatusOptLogDTO();
		if (result != null && result.size() > 1) {
			try {
				BeanUtils.copyProperties(dto, result.get(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setComments(result.get(1).getOptLog().getComments());
		}
		return dto;
	}

	public ManufactureProcessDTO trackOut(
			ManufactureProcessDTO manufactureProcessDTO) {
		
		return this.saveNextProcess(manufactureProcessDTO);
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
			Location location = Location.get(
					Location.class, locationId);
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
	public ManufactureStatusOptLogDTO findManufactureStatusOptLogByProcess(
			Long id,boolean isFuture) {
		String jpql = "select o from ManufactureStatusOptLog o inner join o.manufactureProcess e where e.id=? ";
		if(isFuture){
			jpql+="and o.futureStation is not null and e.futureStation.id is not null ";
		}
		jpql+="order by o.createDate desc";
		ManufactureStatusOptLog result = (ManufactureStatusOptLog) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ManufactureStatusOptLogDTO dto = new ManufactureStatusOptLogDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setOptName(result.getOptLog().getRightUserUser().getUserAccount());
			if(result.getFutureStation() != null){
				dto.setFutureStationName(result.getFutureStation().getStationName());
				dto.setFutureStationNameEn(result.getFutureStation().getStationNameEn());
			}
			dto.setComments(result.getOptLog().getComments());
		}
		return dto;
	}

	public ManufactureProcessDTO saveManufactureProcess(Long manufactureLotId,
			Long substrateProcessId, ManufactureProcessDTO manufactureProcessDTO) {
		ManufactureProcess manufactureProcess = new ManufactureProcess();
		ManufactureLot manufactureLot = ManufactureLot.get(
				ManufactureLot.class, manufactureLotId);
		SubstrateProcess substrateProcess = SubstrateProcess.load(
				SubstrateProcess.class, substrateProcessId);
		try {
			BeanUtilsExtends.copyProperties(manufactureProcess,
					manufactureProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user;
		if (manufactureProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(manufactureProcessDTO.getUserDTO()
					.getUserAccount());
		manufactureProcess.setCreateUser(user);
		manufactureProcess.setLastModifyUser(user);
		manufactureProcess.setShippingDate(manufactureProcessDTO
				.getCreateDate());
		manufactureProcess.setManufactureLot(manufactureLot);
		manufactureProcess.setQtyIn(substrateProcess.getQtyIn());
		manufactureProcess.setStripQtyIn(substrateProcess.getStripQtyIn());
		DefineStationProcess defineStationProcess = null;
		if (manufactureProcessDTO.getStartFlow() != null) {
			defineStationProcess = DefineStationProcess
					.load(DefineStationProcess.class,
							manufactureProcessDTO.getStartFlow());
		}
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.load(
					DefineStationProcess.class, Long.valueOf(6));
		}
		Set<Station> stations = defineStationProcess.getStations();
		manufactureLot.setDefineStationProcess(defineStationProcess);
		manufactureProcess.setStation(stations.iterator().next());
		manufactureProcess.setStatus("Waiting");
		manufactureProcess.setResLotType(manufactureLot.getSubstrateProcess()
				.getSubstrateCompanyLot().getSubstrateCustomerLot()
				.getResLotType());
		manufactureLot.setPackSize((Pack.load(Pack.class, manufactureProcessDTO.getPackSizeId())).getPackSize());
		manufactureLot.setPackSizeId(manufactureProcessDTO.getPackSizeId());
		manufactureLot.save();
		manufactureProcess.save();
		substrateProcess.setStatus("Finish");
		substrateProcess.save();
		manufactureProcessDTO.setId(manufactureProcess.getId());
//		manufactureProcessDTO.setPackSize(manufactureLot.getPackSize());
//		manufactureProcessDTO.setPackSizeId(manufactureLot.getPackSizeId());
		return manufactureProcessDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<StationDTO> findAfterStationsByProcessId(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.load(
				ManufactureProcess.class, id);
		Station station = manufactureProcess.getStation();
		DefineStationProcess defineStationProcess = null;
		if (defineStationProcess == null) {
			defineStationProcess = DefineStationProcess.get(
					DefineStationProcess.class, Long.valueOf(6));
		}
		List<StationDTO> stationList = new ArrayList<StationDTO>();
		Iterator<Station> stations =  this.sortStations(defineStationProcess
				.getStations()).iterator();
		while (stations.hasNext()) {
			StationDTO stationDTO = new StationDTO();
			Station defStation = stations.next();
			if (defStation.getSequence() > station.getSequence()) {
				try {
					BeanUtilsExtends.copyProperties(stationDTO, defStation);
				} catch (Exception e) {
					e.printStackTrace();
				}
				stationList.add(stationDTO);
			}
		}
		return stationList;
	}
	
public String nextProcessVerify(Map<String, Object> info,Long waferProcessId) {
		
//		Map<String, Object> info = (Map<String, Object>) maps.get("data");
		if (info == null)
		{
			return "success";
		}
		
		if(info.get("StripQtyOut") == null)
		{
			return "product.trackout.substrateNull";
		}
		if(info.get("GoodQtyOut") == null)
		{
			return "wafer.trackout.GoodQtyOutNull";
		}
		if(info.get("ScrapsQtyOut") == null)
		{
			return "wafer.trackout.ScrapsQtyOutNull";
		}
		int waferQtyOut = Integer.parseInt(info.get("StripQtyOut").toString());
		int goodQtyOut = Integer.parseInt(info.get("GoodQtyOut").toString());
		int scrapsQtyout = Integer.parseInt(info.get("ScrapsQtyOut").toString());
		ManufactureProcess manufactureProcess = ManufactureProcess.get(ManufactureProcess.class,waferProcessId);
		if(waferQtyOut != manufactureProcess.getStripQtyIn())
		{
			return "product.trackout.substrateNotEqual";
		}
		if(goodQtyOut > manufactureProcess.getQtyIn() || scrapsQtyout > manufactureProcess.getQtyIn() || (goodQtyOut+scrapsQtyout) != manufactureProcess.getQtyIn())
		{
			return "product.trackout.substrateUnitNotEqual";
		}

		return "success";
	}
	public String verifyEquipmentRunningStatusById(Long id) {
		EquipmentDTO equipmentDTO = equipmentApplication.getEquipment(id);
		ManufactureProcessDTO manufactureProcessDTO = equipmentApplication.findManufactureProcessByEquipment(id);
		if(equipmentDTO == null)
		{
			return "wafer.trackin.equimentStatusDown";
		}
		if(equipmentDTO.getEquipment().equalsIgnoreCase("dummy")  ||  equipmentDTO.getStatus() == null ||equipmentDTO.getStatus().equalsIgnoreCase("IDLE"))
		{
			return "success";
		}
		if(equipmentDTO.getStatus().equalsIgnoreCase("DOWN"))
		{
			return "wafer.trackin.equimentStatusDown";
		}
		if (equipmentDTO != null && manufactureProcessDTO != null
				&& "RUN".equals(equipmentDTO.getStatus())) {
			return "wafer.trackin.equimentStatusUsed";
		}
		
		return "success";
	}	
	
	public void outSpecManufactureProcess(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount,String comments) {
		String holdCode = "Out-Spec";
		Resource resource = (Resource) Resource.findByProperty(Resource.class, "identifier", "Out Spectinon").get(0);
		String holdCodeId = String.valueOf(resource.getId());
		comments = "Out Spectinon";
		this.changeStatusManufactureProcess(manufactureProcessDTO, holdCode, holdCodeId,
				useraccount, comments);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int checkOutSpecManufactureProcessTime(Long manufactureProcessId) {
		String jpql = "select o from ManufactureStatusOptLog o inner join o.manufactureProcess e where e.id=? ";
		jpql += "and o.holdCode=?" ;
		jpql += "order by o.createDate desc";
		List<WaferStatusOptLog> result = (List<WaferStatusOptLog>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { manufactureProcessId, "Out Spectinon"})
				.list();
		if (result != null) {
			return result.size();
		}
		return 0;
	}
	
	private void saveOptLog(ManufactureProcessDTO manufactureProcessDTO,ManufactureProcess nowManufactureProcess,User user,String type){
		ManufactureOptLog manufactureOptLog = new ManufactureOptLog();
		OptLog optLog = new OptLog();

		manufactureOptLog.setCreateDate(manufactureProcessDTO
				.getCreateDate());
		manufactureOptLog.setCreateUser(user);
		manufactureOptLog.setLastModifyDate(manufactureProcessDTO
				.getLastModifyDate());
		manufactureOptLog.setLastModifyUser(user);
		manufactureOptLog.setManufactureProcess(nowManufactureProcess);
		manufactureOptLog.setType(type);
		if(manufactureProcessDTO.getComments() == null)
			optLog.setComments(type);
		else
			optLog.setComments(manufactureProcessDTO.getComments());
		optLog.setRightUserUser(User.findByUserAccount(manufactureProcessDTO.getUseraccount()));
		optLog.save();
		manufactureOptLog.setOptLog(optLog);
		manufactureOptLog.save();
	}
}
