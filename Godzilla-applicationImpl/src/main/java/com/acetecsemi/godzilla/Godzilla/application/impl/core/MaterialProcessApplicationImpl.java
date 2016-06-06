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
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MaterialProcessApplicationImpl implements
		MaterialProcessApplication {

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
	public MaterialProcessDTO getMaterialProcess(Long id) {
		MaterialProcess materialProcess = MaterialProcess.load(
				MaterialProcess.class, id);
		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(materialProcessDTO, materialProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MaterialLot materialCustomerLot = materialProcess.getMaterialCompanyLot().getMaterialLot();
		materialProcessDTO.setPartId(materialCustomerLot.getMaterial().getPartId());
		materialProcessDTO.setMaterialName(materialCustomerLot.getMaterial().getMaterialName().getMaterialName());
		materialProcessDTO.setPartNameCN(materialCustomerLot.getMaterial().getPartNameCN());			
		materialProcessDTO.setBatchNo(materialCustomerLot.getBatchNo());
		materialProcessDTO.setVenderName(materialCustomerLot.getVender().getVenderName());
		materialProcessDTO.setPon(materialCustomerLot.getPon());
		if("Waiting".equals(materialProcess.getStatus())){
			materialProcessDTO.setEntryDate(materialProcess.getCreateDate());
		}else
			materialProcessDTO.setEntryDate(materialProcess.getShippingDate());
		materialProcessDTO.setEntryTime(MyDateUtils.getDayHour(materialCustomerLot.getShippingDate(), new Date()));
		materialProcessDTO.setUserName(materialCustomerLot.getCreateUser().getName());
		materialProcessDTO.setCurrStatus(materialProcess.getStatus());
		materialProcessDTO.setUnit(materialCustomerLot.getMaterial().getMinUnit());//小单位
		//materialProcessDTO.setBalance(balance);
		Set<Location> locations = materialProcess.getMaterialLocations();
		String stockPos = this.getLocationsByLocations(locations);
		if (stockPos.length() > 5) {
			materialProcessDTO.setStockPos(stockPos);
		}
		materialProcessDTO.setId((java.lang.Long) materialProcess.getId());
		materialProcessDTO.setLotNo(materialProcess.getMaterialCompanyLot().getLotNo());
		materialProcessDTO.setProductionDate(materialProcess.getMaterialCompanyLot().getMaterialLot().getManufactureDate());
		materialProcessDTO.setGuaranteePeriod(materialProcess.getMaterialCompanyLot().getMaterialLot().getExpiryDate());
		materialProcessDTO.setLotType(materialProcess.getResLotType().getIdentifier());
		materialProcessDTO.setInCapacity(materialProcess.getInCapacity());
		materialProcessDTO.setBalance(materialProcess.getBalance());
		return materialProcessDTO;
	}

	public MaterialProcessDTO saveMaterialProcess(
			MaterialProcessDTO materialProcessDTO) {
		MaterialProcess materialProcess = new MaterialProcess();
		try {
			BeanUtils.copyProperties(materialProcess, materialProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialProcess.save();
		materialProcessDTO.setId((java.lang.Long) materialProcess.getId());
		return materialProcessDTO;
	}

	public void updateMaterialProcess(MaterialProcessDTO materialProcessDTO) {
		MaterialProcess materialProcess = MaterialProcess.get(
				MaterialProcess.class, materialProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(materialProcess, materialProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeMaterialProcess(Long id) {
		this.removeMaterialProcesss(new Long[] { id });
	}

	public void removeMaterialProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MaterialProcess materialProcess = MaterialProcess.load(
					MaterialProcess.class, ids[i]);
			materialProcess.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialProcessDTO> findAllMaterialProcess() {
		List<MaterialProcessDTO> list = new ArrayList<MaterialProcessDTO>();
		List<MaterialProcess> all = MaterialProcess
				.findAll(MaterialProcess.class);
		for (MaterialProcess materialProcess : all) {
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialProcessDTO, materialProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialProcessDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialProcessDTO> pageQueryMaterialProcess(
			MaterialProcessDTO queryVo, int currentPage, int pageSize) {
		List<MaterialProcessDTO> result = new ArrayList<MaterialProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _materialProcess from MaterialProcess _materialProcess  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _materialProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _materialProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _materialProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _materialProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _materialProcess.status in (" + queryVo.getStatus() + ")");
		}
		
		if (queryVo.getCurrStatus() != null && !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _materialProcess.status in (" + queryVo.getCurrStatus() + ")");
		}
		
		if (queryVo.getSort() != null) {
			jpql.append(" and _materialProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _materialProcess.resLotType.identifier like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}

		if (queryVo.getMaterialName() != null && !"".equals(queryVo.getMaterialName())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.material.materialName.materialName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getMaterialName()));
		}
		
		if (queryVo.getVenderName() != null && !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}
		
		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		
		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPon()));
		}
		
		if (queryVo.getPartNameCN() != null && !"".equals(queryVo.getPartNameCN())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.material.partNameCN like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNameCN()));
		}
		
		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _materialProcess.materialCompanyLot.lotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotNo()));
		}
		
		if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.material.partId like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartId()));
		}
		
		if (queryVo.getMaterialType() != null) {
			jpql.append(" and _materialProcess.materialType=?");
			conditionVals.add(queryVo.getMaterialType().intValue());
		}
		if (queryVo.getStationId() != null) {
			jpql.append(" and _materialProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and _materialProcess.status not in (" + StaticValue.NOT_SHOW_STATUS +") ");
		jpql.append(" order by _materialProcess.shippingDate DESC");
		Page<MaterialProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (MaterialProcess materialProcess : pages.getData()) {
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialProcessDTO, materialProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			MaterialLot materialCustomerLot = materialProcess.getMaterialCompanyLot().getMaterialLot();
			materialProcessDTO.setPartId(materialCustomerLot.getMaterial().getPartId());
			materialProcessDTO.setMaterialName(materialCustomerLot.getMaterial().getMaterialName().getMaterialName());
			materialProcessDTO.setPartNameCN(materialCustomerLot.getMaterial().getPartNameCN());			
			materialProcessDTO.setBatchNo(materialCustomerLot.getBatchNo());
			materialProcessDTO.setVenderName(materialCustomerLot.getVender().getVenderName());
			materialProcessDTO.setPon(materialCustomerLot.getPon());
			if("Waiting".equals(materialProcess.getStatus())){
				materialProcessDTO.setEntryDate(materialProcess.getCreateDate());
			}else
				materialProcessDTO.setEntryDate(materialProcess.getShippingDate());
			materialProcessDTO.setEntryTime(MyDateUtils.getDayHour(materialProcessDTO.getEntryDate(), new Date()));
			materialProcessDTO.setUserName(materialCustomerLot.getCreateUser().getName());
			materialProcessDTO.setCurrStatus(materialProcess.getStatus());
			materialProcessDTO.setUnit(materialCustomerLot.getMaterial().getMinUnit());
			//materialProcessDTO.setBalance(balance);
			Set<Location> locations = materialProcess.getMaterialLocations();
			String stockPos = this.getLocationsByLocations(locations);
			if (stockPos.length() > 5) {
				materialProcessDTO.setStockPos(stockPos);
			}
			materialProcessDTO.setLotNo(materialProcess.getMaterialCompanyLot().getLotNo());
			materialProcessDTO.setProductionDate(materialProcess.getMaterialCompanyLot().getMaterialLot().getManufactureDate());
			materialProcessDTO.setGuaranteePeriod(materialProcess.getMaterialCompanyLot().getMaterialLot().getExpiryDate());
			materialProcessDTO.setLotType(materialProcess.getResLotType().getIdentifier());
			materialProcessDTO.setBalance(materialProcess.getBalance());
			materialProcessDTO.setInCapacity(materialProcess.getInCapacity());
			MaterialStatusOptLogDTO materialStatusOptLogDTO = this.findMaterialStatusOptLogByProcess(materialProcess.getId());
			if(materialStatusOptLogDTO != null){
				materialProcessDTO.setHoldReason(materialStatusOptLogDTO.getComments());
			}
			result.add(materialProcessDTO);
		}
		return new Page<MaterialProcessDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	public Map<String, Object> pageQueryMaterialProcessTotal(
			MaterialProcessDTO queryVo) {
		List<MaterialProcessDTO> result = new ArrayList<MaterialProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_materialProcess.qtyIn),count(*),sum(_materialProcess.balance),sum(_materialProcess.inCapacity) from MaterialProcess _materialProcess  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _materialProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _materialProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _materialProcess.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _materialProcess.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _materialProcess.status in (" + queryVo.getStatus() + ")");
		}
		
		if (queryVo.getCurrStatus() != null && !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _materialProcess.status in (" + queryVo.getCurrStatus() + ")");
		}
		
		if (queryVo.getSort() != null) {
			jpql.append(" and _materialProcess.sort=?");
			conditionVals.add(queryVo.getSort());
		}

		if (queryVo.getLotType() != null && !"".equals(queryVo.getLotType())) {
			jpql.append(" and _materialProcess.resLotType.identifier like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotType()));
		}

		if (queryVo.getMaterialName() != null && !"".equals(queryVo.getMaterialName())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.material.materialName.materialName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getMaterialName()));
		}
		
		if (queryVo.getVenderName() != null && !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}
		
		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		
		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPon()));
		}
		
		if (queryVo.getPartNameCN() != null && !"".equals(queryVo.getPartNameCN())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.material.partNameCN like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNameCN()));
		}
		
		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _materialProcess.materialCompanyLot.lotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getLotNo()));
		}
		
		if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
			jpql.append(" and _materialProcess.materialCompanyLot.materialLot.material.partId like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartId()));
		}
		
		if (queryVo.getMaterialType() != null) {
			jpql.append(" and _materialProcess.materialType=?");
			conditionVals.add(queryVo.getMaterialType().intValue());
		}
		if (queryVo.getStationId() != null) {
			jpql.append(" and _materialProcess.station.id=?");
			conditionVals.add(queryVo.getStationId());
		}
		jpql.append(" and _materialProcess.status not in (" + StaticValue.NOT_SHOW_STATUS +") ");
		jpql.append(" order by _materialProcess.shippingDate DESC");
		
		Object[] object = ( Object[]) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dieTotal", String.valueOf(object[0]));
		resultMap.put("countLot", String.valueOf(object[1]));
		resultMap.put("balanceTotal", String.valueOf(object[2]));
		resultMap.put("inCapacityTotal", String.valueOf(object[3]));
		return resultMap;
		
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.lastModifyUser e where o.id=?";
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
	public StationDTO findStationByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.station e where o.id=?";
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
	public EquipmentDTO findEquipmentByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.equipment e where o.id=?";
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
	public ResourceDTO findResLotTypeByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.resLotType e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ResourceDTO findResHoldByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.resHold e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialCompanyLotDTO findMaterialCompanyLotByMaterialProcess(Long id) {
		String jpql = "select e from MaterialProcess o right join o.materialCompanyLot e where o.id=?";
		MaterialCompanyLot result = (MaterialCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		MaterialCompanyLotDTO dto = new MaterialCompanyLotDTO();
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
	public Page<MaterialLocationDTO> findMaterialLocationsByMaterialProcess(
			Long id, int currentPage, int pageSize) {
		List<MaterialLocationDTO> result = new ArrayList<MaterialLocationDTO>();
		String jpql = "select e from MaterialProcess o right join o.materialLocations e where o.id=?";
		Page<Location> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (Location entity : pages.getData()) {
			MaterialLocationDTO dto = new MaterialLocationDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<MaterialLocationDTO>(Page.getStartOfPage(currentPage,
				pageSize), pages.getResultCount(), pageSize, result);
	}

	public MaterialProcessDTO saveMaterialProcessReceive(
			MaterialProcessDTO materialProcessDTO, Long materialCustomerLotId,
			String locationIds) {
		MaterialProcess materialProcess = new MaterialProcess();
		String jpql = "select o from MaterialCompanyLot o left join o.materialLot e where e.id=?";
		MaterialCompanyLot materialCompanyLot = (MaterialCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { materialCustomerLotId })
				.singleResult();

		Set<Location> materialLocations = new HashSet<Location>();
		String[] locationIdArray = locationIds.split(",");
		for (int i = 0; i < locationIdArray.length; i++) {
			Long locationId = Long.parseLong(locationIdArray[i]);
			Location materialLocation = Location.get(
					Location.class, locationId);
			materialLocations.add(materialLocation);
		}
		try {
			BeanUtils.copyProperties(materialProcess, materialProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user;
		if (materialProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(materialProcessDTO.getUserDTO()
					.getUserAccount());
		materialProcess.setMaterialType(materialCompanyLot.getMaterialLot().getMaterial().getMaterialName().getMaterialType());
		materialProcess.setCreateUser(user);
		materialProcess.setLastModifyUser(user);
		materialProcess.setShippingDate(materialProcessDTO.getCreateDate());
		materialProcess.setMaterialCompanyLot(materialCompanyLot);
		materialProcess.setMaterialLocations(materialLocations);
		materialProcess.setQtyIn(materialCompanyLot.getMaterialLot().getQty());
		materialProcess.setQtyOut(materialCompanyLot.getMaterialLot().getQty());
		materialProcess.setInCapacity(materialCompanyLot.getMaterialLot().getInCapacity());
		materialProcess.setBalance(materialCompanyLot.getMaterialLot().getInCapacity());
		DefineStationProcess defineStationProcess = materialCompanyLot
				.getMaterialLot().getDefineStationProcess();
		defineStationProcess = this.getDefineStationProcess(defineStationProcess, materialCompanyLot
				.getMaterialLot().getMaterial().getMaterialName().getMaterialType());
		Set<Station> stations = defineStationProcess
				.getStations();
		materialProcess.setStation(stations.iterator().next());
		materialProcess.setStatus("Waiting");
		materialProcess.setResLotType(materialCompanyLot.getMaterialLot().getResLotType());
		materialProcess.save();
		MaterialLot materialCustomerLot = materialCompanyLot.getMaterialLot();
		materialCustomerLot.setCurrStatus("Received");
		materialCustomerLot.save();
		for (Location loc : materialLocations) {
			loc.setMaterialProcess(materialProcess);
			loc.save();
		}
		this.saveOptLog(materialProcessDTO, materialProcess, user, "Received");
		materialProcessDTO.setId((java.lang.Long) materialProcess.getId());
		return materialProcessDTO;
	}

	public MaterialProcessDTO saveNextProcess(
			MaterialProcessDTO materialProcessDTO) {
		MaterialProcess nextMaterialProcess = new MaterialProcess();
		MaterialProcess nowMaterialProcess = MaterialProcess.load(
				MaterialProcess.class, materialProcessDTO.getId());
		MaterialCompanyLot materialCompanyLot = nowMaterialProcess
				.getMaterialCompanyLot();
		Set<Location> locations = new HashSet<Location>();
		User user;
		if (materialProcessDTO.getUserDTO() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(materialProcessDTO.getUserDTO()
					.getUserAccount());
		nextMaterialProcess.setCreateDate(materialProcessDTO.getCreateDate());
		nextMaterialProcess.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		nextMaterialProcess.setCreateUser(user);
		nextMaterialProcess.setLastModifyUser(user);
		nextMaterialProcess.setShippingDate(materialProcessDTO
				.getShippingDate());
		nextMaterialProcess.setMaterialCompanyLot(materialCompanyLot);
		nextMaterialProcess.setMaterialLocations(locations);
		nextMaterialProcess.setQtyIn(nowMaterialProcess.getQtyOut());
		nextMaterialProcess.setQtyOut(nowMaterialProcess.getQtyOut());
		nextMaterialProcess.setBalance(nowMaterialProcess.getBalance());
		nextMaterialProcess.setInCapacity(nowMaterialProcess.getInCapacity());
		DefineStationProcess defineStationProcess = materialCompanyLot
				.getMaterialLot().getDefineStationProcess();
		defineStationProcess = this.getDefineStationProcess(defineStationProcess, materialCompanyLot
				.getMaterialLot().getMaterial().getMaterialName().getMaterialType());
		Set<Station> stations = defineStationProcess
				.getStations();
		Station nextStation = null;
		for (Station station : stations) {
			if (station.getId() > nowMaterialProcess.getStation().getId()) {
				nextStation = station;
				break;
			}
		}
		if(materialProcessDTO.getNextStationId() != null){
			nextStation = Station.load(Station.class, materialProcessDTO.getNextStationId());
		}
		if(nextStation == null){
			return null;
		}
		nextMaterialProcess.setStation(nextStation);
		Resource resource;
		if(materialProcessDTO.getCurrStatus() != null){
			resource = Resource.findByProperty(Resource.class, "identifier",
					materialProcessDTO.getCurrStatus()).get(0);
		}else{
			resource = Resource.findByProperty(Resource.class, "identifier",
					"Waiting").get(0);
		}
		nextMaterialProcess.setStatus(resource.getName());
		nextMaterialProcess.setMaterialType(nowMaterialProcess.getMaterialType());
		nextMaterialProcess.setResLotType(nowMaterialProcess.getResLotType());
		nextMaterialProcess.save();
		Resource finish = Resource.findByProperty(Resource.class, "identifier",
				"Finish").get(0);
		nowMaterialProcess.setStatus(finish.getName());
		nowMaterialProcess.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		nowMaterialProcess.setLastModifyUser(user);
		nowMaterialProcess.save();
		locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(nowMaterialProcess.getMaterialLocations()));
		materialProcessDTO.setId((java.lang.Long) nextMaterialProcess.getId());
		
		MaterialOptLog materialNextOptLog = new MaterialOptLog();
		OptLog optLog = new OptLog();
		materialNextOptLog.setCreateDate(materialProcessDTO.getCreateDate());
		materialNextOptLog.setCreateUser(user);
		materialNextOptLog.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		materialNextOptLog.setLastModifyUser(user);
//		materialNextOptLog.setQty(materialQty);
		materialNextOptLog.setMaterialProcess(nowMaterialProcess);
		materialNextOptLog.setType("next");
		optLog.setComments(materialProcessDTO.getComments());
		optLog.setRightUserUser(User.findByUserAccount(materialProcessDTO.getUseraccount()));
		optLog.save();
		materialNextOptLog.setOptLog(optLog);
		materialNextOptLog.save();
		return materialProcessDTO;
	}

	public String saveSplitMaterialProcess(
			MaterialProcessDTO materialProcessDTO, String useraccount,
			Integer materialQty,String comments) {
		User user;
		if (materialProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(materialProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批
		MaterialProcess nowMaterialProcess = MaterialProcess.get(
				MaterialProcess.class, materialProcessDTO.getId());
		if(nowMaterialProcess.getQtyIn() == 1 || materialQty >= nowMaterialProcess.getQtyIn() || materialProcessDTO.getInCapacity() > nowMaterialProcess.getInCapacity())
		{
			return "material.split.stripQtyEqualZero";
		}
		MaterialCompanyLot materialCompanyLot = nowMaterialProcess
				.getMaterialCompanyLot();

		List<String> lotNoList = this.getSplitLotNo(nowMaterialProcess
				.getMaterialCompanyLot().getLotNo(), 2);
		for (int i = 0; i < lotNoList.size(); i++) {
			MaterialCompanyLot splitMaterialCompanyLot = new MaterialCompanyLot();
			splitMaterialCompanyLot
					.setMaterialLot(materialCompanyLot
							.getMaterialLot());
			splitMaterialCompanyLot.setLotNo(lotNoList.get(i));
			splitMaterialCompanyLot
					.setParentMaterialCompanyLot(materialCompanyLot);
			splitMaterialCompanyLot.setCreateDate(materialProcessDTO
					.getCreateDate());
			splitMaterialCompanyLot.setCreateUser(user);
			splitMaterialCompanyLot.setLastModifyDate(materialProcessDTO
					.getLastModifyDate());
			splitMaterialCompanyLot.setLastModifyUser(user);
			splitMaterialCompanyLot.save();
			MaterialProcess splitMaterialProcess = new MaterialProcess();
			splitMaterialProcess
					.setMaterialCompanyLot(splitMaterialCompanyLot);
			String tempLocationIds;
			if (i == 0) {
				splitMaterialProcess.setQtyIn(nowMaterialProcess.getQtyIn()
						- materialQty);
				splitMaterialProcess.setQtyOut(nowMaterialProcess.getQtyIn()
						- materialQty);
				splitMaterialProcess.setInCapacity(nowMaterialProcess.getInCapacity()-materialProcessDTO.getInCapacity());
				splitMaterialProcess.setBalance(splitMaterialProcess.getInCapacity());
				tempLocationIds = this.getLocationIdsByLocations(nowMaterialProcess.getMaterialLocations());
			} else {
				splitMaterialProcess.setQtyIn((double)materialQty);
				splitMaterialProcess.setQtyOut((double)materialQty);
				splitMaterialProcess.setBalance(materialProcessDTO.getInCapacity());
				splitMaterialProcess.setInCapacity(materialProcessDTO.getInCapacity());
				tempLocationIds = materialProcessDTO.getLocationIds();
			}
			splitMaterialProcess.setShippingDate(nowMaterialProcess
					.getShippingDate());
			splitMaterialProcess.setStatus(nowMaterialProcess.getStatus());
			splitMaterialProcess.setStation(nowMaterialProcess.getStation());
			splitMaterialProcess.setCreateDate(materialProcessDTO
					.getCreateDate());
			splitMaterialProcess.setCreateUser(user);
			splitMaterialProcess.setLastModifyDate(materialProcessDTO
					.getLastModifyDate());
			splitMaterialProcess.setLastModifyUser(user);
			splitMaterialProcess.setResLotType(nowMaterialProcess.getResLotType());
			splitMaterialProcess.setMaterialType(nowMaterialProcess.getMaterialType());
			splitMaterialProcess.save();
			this.locationApplication.updateLocationsMaterialProcess(tempLocationIds, splitMaterialProcess.getId());
		}
		nowMaterialProcess.setStatus("Split");
		nowMaterialProcess.save();
				// 保存操作信息 --start
		MaterialOptLog materialSplitOptLog = new MaterialOptLog();
		OptLog optLog = new OptLog();
		materialSplitOptLog.setCreateDate(materialProcessDTO.getCreateDate());
		materialSplitOptLog.setCreateUser(user);
		materialSplitOptLog.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		materialSplitOptLog.setLastModifyUser(user);
		materialSplitOptLog.setQty(materialQty);
		materialSplitOptLog.setMaterialProcess(nowMaterialProcess);
		materialSplitOptLog.setType("split");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		materialSplitOptLog.setOptLog(optLog);
		materialSplitOptLog.save();
		// 保存操作信息 --end
		return "success";
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialProcessDTO> getSplitMaterialProcess(
			MaterialProcessDTO materialProcessDTO,
			Integer materialQty) {
		// 处理分批
		MaterialProcess nowMaterialProcess = MaterialProcess.get(MaterialProcess.class,
				materialProcessDTO.getId());
		List<String> lotNoList = this.getSplitLotNo(nowMaterialProcess
				.getMaterialCompanyLot().getLotNo(), 2);
		List<MaterialProcessDTO> list = new ArrayList<MaterialProcessDTO>();
		for (int i = 0; i < lotNoList.size(); i++) {
			MaterialProcessDTO sPDTO = new MaterialProcessDTO();
			MaterialCompanyLot nowMaterialCompanyLot = nowMaterialProcess
					.getMaterialCompanyLot();
			MaterialLot materialCustomerLot = nowMaterialCompanyLot.getMaterialLot();
			sPDTO.setLotNo(lotNoList.get(i));
			sPDTO.setCustomerLotNo(nowMaterialCompanyLot.getMaterialLot()
					.getCustomerLotNo());
			sPDTO.setPartId(materialCustomerLot.getMaterial().getPartId());
			if (i == 0) {
				sPDTO.setQtyIn(nowMaterialProcess.getQtyIn().intValue() - materialQty);
				sPDTO.setBalance(nowMaterialProcess.getInCapacity() - materialProcessDTO.getInCapacity());
				sPDTO.setInCapacity(nowMaterialProcess.getInCapacity() - materialProcessDTO.getInCapacity());
				sPDTO.setStockPos(this.getLocationsByLocations(nowMaterialProcess
						.getMaterialLocations()));
			} else {
				sPDTO.setQtyIn(materialQty);
				sPDTO.setBalance(materialProcessDTO.getInCapacity());
				sPDTO.setInCapacity(materialProcessDTO.getInCapacity());
				sPDTO.setStockPos(this.getLocationsById(materialProcessDTO
						.getLocationIds()));
			}
			sPDTO.setPartId(materialCustomerLot.getMaterial().getPartId());
			sPDTO.setMaterialName(materialCustomerLot.getMaterial().getMaterialName().getMaterialName());
			sPDTO.setPartNameCN(materialCustomerLot.getMaterial().getPartNameCN());			
			sPDTO.setBatchNo(materialCustomerLot.getBatchNo());
			sPDTO.setVenderName(materialCustomerLot.getVender().getVenderName());
			sPDTO.setPon(materialCustomerLot.getPon());
			sPDTO.setEntryDate(materialCustomerLot.getShippingDate());
			sPDTO.setEntryTime(MyDateUtils.getDayHour(materialCustomerLot.getShippingDate(), new Date()));
			sPDTO.setUserName(materialCustomerLot.getCreateUser().getName());
			sPDTO.setCurrStatus(nowMaterialProcess.getStatus());
			sPDTO.setStatus(nowMaterialProcess.getStatus());
			sPDTO.setUnit(materialCustomerLot.getMaterial().getUnit());
			//materialProcessDTO.setBalance(balance);
			sPDTO.setProductionDate(nowMaterialProcess.getMaterialCompanyLot().getMaterialLot().getManufactureDate());
			sPDTO.setGuaranteePeriod(nowMaterialProcess.getMaterialCompanyLot().getMaterialLot().getExpiryDate());
			list.add(sPDTO);
		}
		return list;
	}

	public void changeStatusMaterialProcess(
			MaterialProcessDTO materialProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		User user;
		if (materialProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(materialProcessDTO.getUserDTO()
					.getUserAccount());
		MaterialProcess materialProcess = MaterialProcess.load(
				MaterialProcess.class, materialProcessDTO.getId());
		materialProcess.setStatus(materialProcessDTO.getCurrStatus());
		if(materialProcessDTO.getShippingDate() != null){
			materialProcess.setShippingDate(materialProcessDTO.getShippingDate());
		}
		materialProcess.setLastModifyDate(materialProcessDTO.getCreateDate());
		materialProcess.setLastModifyUser(user);
		
		MaterialStatusOptLog materialStatusOptLog = new MaterialStatusOptLog();
		OptLog optLog = new OptLog();
		if(holdCodeId != null && !"".equals(holdCodeId)){
			ResourceLineAssignment rsourceLineAssignment = ResourceLineAssignment.findRelationByResource(Long.valueOf(holdCodeId)).get(0);
			materialProcess.setResHold(rsourceLineAssignment.getChild());
			materialProcess.setStatus(rsourceLineAssignment.getParent().getName());
			materialStatusOptLog.setHoldCode(rsourceLineAssignment.getChild().getDesc());
		}else{
			materialProcess.setStatus(materialProcessDTO.getCurrStatus());
			materialStatusOptLog.setHoldCode(holdCode);
		}
		materialStatusOptLog
				.setCreateDate(materialProcessDTO.getCreateDate());
		materialStatusOptLog.setCreateUser(user);
		materialStatusOptLog.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		materialStatusOptLog.setLastModifyUser(user);
		materialStatusOptLog.setMaterialProcess(materialProcess);
		if (materialProcessDTO.getFutureHoldStationId() != null) {
			Station station = Station.load(Station.class,
					materialProcessDTO.getFutureHoldStationId());
			if (station != null)
				materialStatusOptLog.setFutureStation(station);
		}
		//materialStatusOptLog.setHoldCode(holdCode);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		if(materialProcessDTO.getEngineerName() != null){
			materialStatusOptLog.setEngineerName(materialProcessDTO.getEngineerName());
		}
		materialStatusOptLog.setOptLog(optLog);
		materialStatusOptLog.save();

	}

	public void holdMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			materialProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		} else{
			materialProcessDTO.setCurrStatus("Hold");
		}
		this.changeStatusMaterialProcess(materialProcessDTO, holdCode,holdCodeId,
				useraccount, comments);

	}
	
	public void holdMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String useraccount,
			String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			materialProcessDTO.setCurrStatus("Pass");
			holdCode = "Cus. Pass";
		} else{
			materialProcessDTO.setCurrStatus("Hold");
		}
		this.changeStatusMaterialProcess(materialProcessDTO, holdCode, null,
				useraccount, comments);

	}

	public void engDispMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments) {
		if ("pass".equals(holdCode.toLowerCase())) {
			materialProcessDTO.setCurrStatus("Pass");
			holdCode = "Eng. Pass";
		} else
			materialProcessDTO.setCurrStatus(holdCode);
		this.changeStatusMaterialProcess(materialProcessDTO, holdCode, holdCodeId, useraccount,
				comments);
		
	}

	public String saveMergeMaterialProcess(
			MaterialProcessDTO materialProcessDTO, String mergeIds,
			String useraccount, String comments) {
		User user;
		if (materialProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(materialProcessDTO.getUserDTO()
					.getUserAccount());
		// 处理分批

		MaterialProcess nowMaterialProcess = new MaterialProcess();
		MaterialCompanyLot mergeMaterialCompanyLot = new MaterialCompanyLot();
		String[] idArray = mergeIds.split(",");
		List<MaterialProcess> list = new ArrayList<MaterialProcess>();
		double dieQtyTotal = 0;
		double materialQtyTotal = 0;
		MaterialProcess tempMaterialProcess = null;
		for (String id : idArray) {
			MaterialProcess materialProcess = MaterialProcess.load(
					MaterialProcess.class, Long.parseLong(id));
			if (tempMaterialProcess != null) {
				if(tempMaterialProcess.getMaterialCompanyLot()
							.getMaterialLot().getMaterial().getId() != materialProcess
							.getMaterialCompanyLot().getMaterialLot()
							.getMaterial().getId())
				{
					return "material.mergCompanyLot.product";
				}
				if(!tempMaterialProcess.getMaterialCompanyLot().getMaterialLot().getBatchNo().equals(materialProcess.getMaterialCompanyLot().getMaterialLot().getBatchNo()))
				{
					return "material.mergCompanyLot.batchNo";
				}
				if(!tempMaterialProcess.getMaterialCompanyLot().getMaterialLot().getPon().equals(materialProcess.getMaterialCompanyLot().getMaterialLot().getPon()))
				{
					return "material.mergCompanyLot.pon";
				}
				if(tempMaterialProcess.getMaterialCompanyLot().getMaterialLot().getResLotType().getId() != materialProcess.getMaterialCompanyLot().getMaterialLot().getResLotType().getId())
				{
					return "material.mergCompanyLot.type";
				}
			}
			this.locationApplication.clearLocationsProcess(this.getLocationIdsByLocations(materialProcess.getMaterialLocations()));
			tempMaterialProcess = materialProcess;
			dieQtyTotal += materialProcess.getQtyIn();
			materialQtyTotal += materialProcess.getBalance();
			list.add(materialProcess);
		}
		list = sortMaterialProcess(list);
		nowMaterialProcess = list.get(list.size() - 1);	
		MaterialCompanyLot nowMaterialCompanyLot = nowMaterialProcess
				.getMaterialCompanyLot();
		mergeMaterialCompanyLot.setMaterialLot(nowMaterialCompanyLot
				.getMaterialLot());
		mergeMaterialCompanyLot.setLotNo(nowMaterialCompanyLot.getLotNo());
		mergeMaterialCompanyLot
				.setParentMaterialCompanyLot(nowMaterialCompanyLot);
		mergeMaterialCompanyLot.setCreateDate(materialProcessDTO
				.getCreateDate());
		mergeMaterialCompanyLot.setCreateUser(user);
		mergeMaterialCompanyLot.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		mergeMaterialCompanyLot.setLastModifyUser(user);
		mergeMaterialCompanyLot.save();
		MaterialProcess mergeMaterialProcess = new MaterialProcess();
		mergeMaterialProcess.setMaterialCompanyLot(mergeMaterialCompanyLot);
		mergeMaterialProcess.setShippingDate(nowMaterialProcess
				.getShippingDate());
		mergeMaterialProcess.setStatus(nowMaterialProcess.getStatus());
		mergeMaterialProcess.setStation(nowMaterialProcess.getStation());
		mergeMaterialProcess
				.setCreateDate(materialProcessDTO.getCreateDate());
		mergeMaterialProcess.setCreateUser(user);
		mergeMaterialProcess.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		mergeMaterialProcess.setLastModifyUser(user);
		mergeMaterialProcess.setQtyIn(dieQtyTotal);
		mergeMaterialProcess.setQtyOut(dieQtyTotal);
		mergeMaterialProcess.setBalance(materialQtyTotal);
		mergeMaterialProcess.setInCapacity(materialQtyTotal);
		mergeMaterialProcess.setMaterialType(nowMaterialProcess.getMaterialType());
		mergeMaterialProcess.setMaterialLocations(this.getLocations(materialProcessDTO.getLocationIds()));
		mergeMaterialProcess.setResLotType(nowMaterialProcess.getResLotType());
		mergeMaterialProcess.save();
		this.locationApplication.updateLocationsMaterialProcess(materialProcessDTO.getLocationIds(), mergeMaterialProcess.getId());
		nowMaterialProcess.setStatus("Waiting");
		nowMaterialProcess.save();
		for (MaterialProcess materialProcess : list) {
			MaterialCompanyLot materialCompanyLot = new MaterialCompanyLot();
			materialProcess.setStatus("Merge");
			materialCompanyLot = materialProcess.getMaterialCompanyLot();
			materialCompanyLot
					.setMergeMaterialCompanyLot(mergeMaterialCompanyLot);
			materialCompanyLot.save();
			materialProcess.setMaterialType(nowMaterialProcess.getMaterialType());
			materialProcess.save();
		}

		// 保存操作信息 --start
		MaterialOptLog materialMergeOptLog = new MaterialOptLog();
		OptLog optLog = new OptLog();
		materialMergeOptLog.setCreateDate(materialProcessDTO.getCreateDate());
		materialMergeOptLog.setCreateUser(user);
		materialMergeOptLog.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		materialMergeOptLog.setLastModifyUser(user);
		materialMergeOptLog.setMaterialProcess(nowMaterialProcess);
		materialMergeOptLog.setType("Merge");
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		materialMergeOptLog.setOptLog(optLog);
		materialMergeOptLog.save();
		// 保存操作信息 --end
		return "success";
	}
	
	
	private List<MaterialProcess> sortMaterialProcess(List<MaterialProcess> materialProcesses) {
		Collections.sort(materialProcesses, new Comparator<MaterialProcess>() {
            public int compare(MaterialProcess arg0, MaterialProcess arg1) {
                return arg0.getQtyIn().compareTo(arg1.getQtyIn());
            }
        });
		return materialProcesses;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialProcessDTO getMergeMaterialProcess(
			MaterialProcessDTO materialProcessDTO, String mergeIds) {
		MaterialProcess nowMaterialProcess = new MaterialProcess();
		String[] idArray = mergeIds.split(",");
		List<MaterialProcess> list = new ArrayList<MaterialProcess>();
		int qtyIn = 0;
		int qtyTotal = 0;
		double balanceQtyTotal = 0;
		for (String id : idArray) {
			MaterialProcess materialProcess = MaterialProcess.get(MaterialProcess.class,
					Long.parseLong(id));
			if (qtyIn < materialProcess.getQtyIn()) {
				nowMaterialProcess = materialProcess;
				qtyIn = materialProcess.getQtyIn().intValue();
			}
			if (nowMaterialProcess.getMaterialCompanyLot() == null
					|| nowMaterialProcess.getMaterialCompanyLot()
							.getMaterialLot().getMaterial().getId() != materialProcess
							.getMaterialCompanyLot().getMaterialLot()
							.getMaterial().getId()) {
				return null;
			}
			qtyTotal += materialProcess.getQtyIn();
			balanceQtyTotal += materialProcess.getBalance();
			list.add(materialProcess);
		}

		MaterialCompanyLot nowMaterialCompanyLot = nowMaterialProcess
				.getMaterialCompanyLot();
		MaterialLot materialCustomerLot = nowMaterialCompanyLot.getMaterialLot();
		materialProcessDTO.setVenderName(materialCustomerLot.getVender().getVenderName());
		materialProcessDTO.setLotNo(nowMaterialProcess.getMaterialCompanyLot().getLotNo());
		materialProcessDTO.setCustomerLotNo(materialCustomerLot.getCustomerLotNo());
		materialProcessDTO.setBatchNo(materialCustomerLot.getBatchNo());
		materialProcessDTO.setPon(materialCustomerLot.getPon());
		materialProcessDTO.setEntryDate(new Date());
		materialProcessDTO.setEntryTime(MyDateUtils.getDayHour(materialProcessDTO.getEntryDate(), new Date()));
		materialProcessDTO.setUserName(materialCustomerLot.getCreateUser().getName());
		materialProcessDTO.setStockPos("");
		materialProcessDTO.setCustomerLotNo(materialCustomerLot
				.getCustomerLotNo());
		materialProcessDTO.setQtyIn(qtyTotal);
		materialProcessDTO.setBalance(balanceQtyTotal);
		materialProcessDTO.setGuaranteePeriod(materialCustomerLot.getExpiryDate());
		materialProcessDTO.setProductionDate(materialCustomerLot.getManufactureDate());
		materialProcessDTO.setCurrStatus(nowMaterialProcess.getStatus());
		materialProcessDTO.setStatus(nowMaterialProcess.getStatus());
		materialProcessDTO.setPartId(materialCustomerLot.getMaterial().getPartId());
		materialProcessDTO.setMaterialName(materialCustomerLot.getMaterial().getMaterialName().getMaterialName());
		materialProcessDTO.setPartNameCN(materialCustomerLot.getMaterial().getPartNameCN());			
		materialProcessDTO.setUnit(materialCustomerLot.getMaterial().getUnit());
		materialProcessDTO.setLotType(nowMaterialProcess.getResLotType().getIdentifier());
		//materialProcessDTO.setBalance(balance);
		return materialProcessDTO;
	}


	public void futureHoldMaterialProcess(
			MaterialProcessDTO materialProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments) {
		materialProcessDTO.setCurrStatus("Waiting");
		this.changeStatusMaterialProcess(materialProcessDTO, holdCode, holdCodeId, useraccount, comments);
	}

	public void changeLocations(MaterialProcessDTO materialProcessDTO,
			String locationIds, String useraccount, String comments) {
		User user;
		if (materialProcessDTO.getUserDTO() == null)
			user = User.findByUserAccount("1");
		else
			user = User.findByUserAccount(materialProcessDTO.getUserDTO()
					.getUserAccount());

		Set<Location> locations = this.getLocations(locationIds);
		MaterialProcess materialProcess = MaterialProcess.get(MaterialProcess.class,
				materialProcessDTO.getId());
		MaterialCompanyLot materialCompanyLot = materialProcess.getMaterialCompanyLot();
		materialProcess.setMaterialLocations(locations);
		for (Location loc : locations) {
			loc.setMaterialProcess(materialProcess);
			loc.save();
		}
		materialProcess.setLastModifyDate(materialProcessDTO.getCreateDate());
		materialProcess.setLastModifyUser(user);
		materialProcess.save();
		MaterialChangeLocationOptLog materialChangeLocationOptLog = new MaterialChangeLocationOptLog();
		OptLog optLog = new OptLog();
		materialChangeLocationOptLog
				.setCreateDate(materialProcessDTO.getCreateDate());
		materialChangeLocationOptLog.setCreateUser(user);
		materialChangeLocationOptLog.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		materialChangeLocationOptLog.setLastModifyUser(user);
		materialChangeLocationOptLog.setMaterialProcess(materialProcess);
		optLog.setComments(comments);
		optLog.setRightUserUser(User.findByUserAccount(useraccount));
		optLog.save();
		materialChangeLocationOptLog.setOptLog(optLog);
		materialChangeLocationOptLog.save();

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
	
	private List<String> getLotNoList(String lotNo, int lot) {
		String[] str = lotNo.split("-");// MA00001-01
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
		String jpql = "select max(o.lotNo) from MaterialCompanyLot o where o.lotNo like ?";
		String maxLotNo = (String) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { "%" + lotNo + "%" })
				.singleResult();
		return maxLotNo;
	}

	private DefineStationProcess getDefineStationProcess(DefineStationProcess defineStationProcess, int materialType ){
		if (defineStationProcess == null) {
			Long defineStationProcessId = StaticValue.getMaterialTypeDefineStationProcess().get(materialType);
			defineStationProcess = DefineStationProcess.get(
					DefineStationProcess.class, defineStationProcessId);
		}
		return defineStationProcess;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialStatusOptLogDTO findMaterialStatusOptLogByProcess(Long id) {
		String jpql = "select o from MaterialStatusOptLog o right join o.materialProcess e where e.id=?";
		MaterialStatusOptLog result = (MaterialStatusOptLog) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		MaterialStatusOptLogDTO  dto = new MaterialStatusOptLogDTO();
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
	
	public void abortStep(MaterialProcessDTO materialProcessDTO, String useraccount,
			String comments) {
		materialProcessDTO.setCurrStatus("Waiting");
		materialProcessDTO.setShippingDate(null);
		this.changeStatusMaterialProcess(materialProcessDTO, "Waiting" , null, useraccount,
				comments);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialStatusOptLogDTO findLastMaterialStatusOptLogByProcess(Long id) {
		String jpql = "select o from MaterialStatusOptLog o inner join o.materialProcess e where e.id=? order by o.createDate";
		List<MaterialStatusOptLog> result = (List<MaterialStatusOptLog>) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).list();
		MaterialStatusOptLogDTO  dto = new MaterialStatusOptLogDTO();
		if (result.size() > 1) {
			try {
				BeanUtils.copyProperties(dto, result.get(1));
				dto.setComments(result.get(1).getOptLog().getComments());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
	
	private void saveOptLog(MaterialProcessDTO materialProcessDTO, MaterialProcess nowMaterialProcess ,User user , String type){
		MaterialOptLog materialOptLog = new MaterialOptLog();
		OptLog optLog = new OptLog();
		materialOptLog.setCreateDate(materialProcessDTO.getCreateDate());
		materialOptLog.setCreateUser(user);
		materialOptLog.setLastModifyDate(materialProcessDTO
				.getLastModifyDate());
		materialOptLog.setLastModifyUser(user);
		materialOptLog.setMaterialProcess(nowMaterialProcess);
		materialOptLog.setType(type);
		if(materialProcessDTO.getComments() == null ){
			optLog.setComments(type);
		}else{
			optLog.setComments(materialProcessDTO.getComments());
		}
		optLog.setRightUserUser(User.findByUserAccount(materialProcessDTO.getUseraccount()));
		optLog.save();
		materialOptLog.setOptLog(optLog);
		materialOptLog.save();
	}
}
