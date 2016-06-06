package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;
import com.acetecsemi.godzilla.Godzilla.application.core.BomListApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class BomListApplicationImpl implements BomListApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BomListDTO getBomList(Long id) {
		BomList bomList = BomList.load(BomList.class, id);
		BomListDTO bomListDTO = new BomListDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(bomListDTO, bomList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bomListDTO.setId((java.lang.Long) bomList.getId());
		return bomListDTO;
	}

	public BomListDTO saveBomList(BomListDTO bomListDTO) {
		BomList bomList = new BomList();
		try {
			BeanUtils.copyProperties(bomList, bomListDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bomList.save();
		bomListDTO.setId((java.lang.Long) bomList.getId());
		return bomListDTO;
	}

	public void updateBomList(BomListDTO bomListDTO) {
		BomList bomList = BomList.get(BomList.class, bomListDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(bomList, bomListDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBomList(Long id) {
		this.removeBomLists(new Long[] { id });
	}

	public void removeBomLists(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			BomList bomList = BomList.load(BomList.class, ids[i]);
			bomList.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BomListDTO> findAllBomList() {
		List<BomListDTO> list = new ArrayList<BomListDTO>();
		List<BomList> all = BomList.findAll(BomList.class);
		for (BomList bomList : all) {
			BomListDTO bomListDTO = new BomListDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(bomListDTO, bomList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(bomListDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BomListDTO> pageQueryBomList(BomListDTO queryVo,
			int currentPage, int pageSize) {
		List<BomListDTO> result = new ArrayList<BomListDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _bomList from BomList _bomList   left join _bomList.createUser  left join _bomList.lastModifyUser  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _bomList.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _bomList.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getBomListType() != null
				&& !"".equals(queryVo.getBomListType())) {
			jpql.append(" and _bomList.bomListType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBomListType()));
		}

		Page<BomList> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (BomList bomList : pages.getData()) {
			BomListDTO bomListDTO = new BomListDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(bomListDTO, bomList);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(bomListDTO);
		}
		return new Page<BomListDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByBomList(Long id) {
		String jpql = "select e from BomList o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByBomList(Long id) {
		String jpql = "select e from BomList o right join o.lastModifyUser e where o.id=?";
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
	public Page<BomListItemDTO> findBomListItemsByBomList(Long id,
			int currentPage, int pageSize) {
		List<BomListItemDTO> result = new ArrayList<BomListItemDTO>();
		String jpql = "select e from BomList o right join o.bomListItems e where o.id=?";
		Page<BomListItem> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (BomListItem entity : pages.getData()) {
			BomListItemDTO dto = new BomListItemDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<BomListItemDTO>(Page.getStartOfPage(currentPage,
				pageSize), pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BomListDTO findBomListByProduct(Long productId, String bomListType) {
		List<BomListDTO> list = new ArrayList<BomListDTO>();
		String jpql = "select o from BomList o where o.product.id=? and bomListType = ?";
		BomList bomList = (BomList) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { productId, bomListType })
				.singleResult();
		BomListDTO bomListDTO = new BomListDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(bomListDTO, bomList);
			bomListDTO.setProductName(bomList.getProduct().getProductName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.add(bomListDTO);
		return bomListDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BomListDTO findBomListByProcess(Long processId,String type) {
		if(type.equals(StaticValue.BOMLIST_TYPE_ASSEMBLY)){
			ManufactureProcess manufactureProcess = ManufactureProcess.load(
					ManufactureProcess.class, processId);
			Long productId = manufactureProcess.getManufactureLot().getProduct()
					.getId();
			return this.findBomListByProduct(productId, type);
		}else{
			WaferProcess waferProcess = WaferProcess.load(WaferProcess.class,
					processId);
			Long productId = waferProcess.getWaferCompanyLot()
					.getWaferCustomerLot().getProduct().getId();
			return this.findBomListByProduct(productId, type);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, List<BomListItemDTO>> findBomListItemsByBomList(Long id,
			boolean isMonitor) {
		String jpql = "select e from BomList o right join o.bomListItems e where o.id=?";
		if (isMonitor) {
			jpql += " and o.monitor = 1 ";
		}
		List<BomListItem> list = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).list();
		Map<String, List<BomListItemDTO>> map = new HashMap<String, List<BomListItemDTO>>();
		for (BomListItem entity : list) {
			List<BomListItemDTO> itemList = map.get(entity.getStation().getParentStation()
					.getStationName());
			if (itemList == null)
				itemList = new ArrayList<BomListItemDTO>();
			BomListItemDTO dto = new BomListItemDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setMaterialName(entity.getMaterial().getMaterialName().getMaterialName());
			dto.setPartId(entity.getMaterial().getPartId());
			dto.setUnit(entity.getMaterial().getUnit());
			dto.setPartNameCN(entity.getMaterial().getPartNameCN());
			itemList.add(dto);
			map.put(entity.getStation().getParentStation().getStationName(), itemList);
		}
		return map;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, List<SelectItemDTO>> findMaterialByManuProcess(Long manuProcessid,Long stationId,
			boolean isMonitor) {
		String jpql = "select distinct o.material from BomListItem o,ManufactureProcess m where m.station.id=o.station.id and m.id=?";
		if (isMonitor) {
			jpql += " and o.monitor = 1 ";
		}
		List<Material> list = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { manuProcessid }).list();
		Map<String, List<SelectItemDTO>> map = new HashMap<String, List<SelectItemDTO>>();
		List<SelectItemDTO> directList = new ArrayList<SelectItemDTO>();
		List<SelectItemDTO> inDirectList = new ArrayList<SelectItemDTO>();
		List<SelectItemDTO> waferList = new ArrayList<SelectItemDTO>();
		for (Material entity : list) {
			List<MaterialLotDTO> dto = new ArrayList<MaterialLotDTO>();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (entity.getMaterialName().getMaterialType() == 1) {
				directList.addAll(this.findMaterialLotSelByMaterial(
						entity.getId(), (long) 1502));
			} else if (entity.getMaterialName().getMaterialType() == 2) {
				inDirectList.addAll(this.findMaterialLotSelByMaterial(
						entity.getId(), (long) 1503));
			}
		}
		if (directList.size() > 0)
			map.put("direct", directList);
		if (inDirectList.size() > 0)
			map.put("indirect", inDirectList);
		if (StaticValue.getWaferDebitStation().get(stationId) != null)
			waferList = this.findWaferLotSelect(manuProcessid,stationId);
		if (waferList.size() > 0)
			map.put("wafer", waferList);
		return map;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private List<MaterialLotDTO> findMaterialLotByMaterial(Long id) {
		String jpql = "select o from MaterialLot o right join o.material e where e.id=?";
		List<MaterialLot> result = (List<MaterialLot>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.list();
		List<MaterialLotDTO> materialLotList = new ArrayList<MaterialLotDTO>();
		for (MaterialLot entity : result) {
			MaterialLotDTO dto = new MaterialLotDTO();
			try {
				BeanUtilsExtends.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			materialLotList.add(dto);
		}
		return materialLotList;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private List<SelectItemDTO> findMaterialLotSelByMaterial(Long id,
			Long stationId) {
		String jpql = "select o from MaterialProcess o inner join o.materialCompanyLot.materialLot.material e where e.id=? and o.station.id=? and o.status='Waiting'";
		List<MaterialProcess> result = (List<MaterialProcess>) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(new Object[] { id, stationId }).list();
		List<SelectItemDTO> materialProcessList = new ArrayList<SelectItemDTO>();
		for (MaterialProcess entity : result) {
			SelectItemDTO dto = new SelectItemDTO();
			dto.setId(entity.getId());
			dto.setValue(entity.getMaterialCompanyLot().getLotNo());
			dto.setQty(entity.getQtyOut());
			materialProcessList.add(dto);
		}
		return materialProcessList;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private List<SelectItemDTO> findWaferLotSelect(Long manuProcessId, Long stationId) {
		String partNo = "";
		switch(stationId.intValue())
		{
		case 503:  //上片1
			partNo = "U1";
			break;
		case 506: //上片2
			partNo = "U2";
			break;
		case 508:  //上片3
			partNo = "U3";
			break;
		}
		List<Object> conditionVals = new ArrayList<Object>();
		conditionVals.add(manuProcessId);
		String jpql = "select o from WaferProcess o,ManufactureProcess m where m.manufactureLot.product.id=o.waferCompanyLot.waferCustomerLot.product.id and m.id=? and o.station.id = 203 and o.status not in ("
				+ StaticValue.NOT_SHOW_STATUS + ")";
		if(!partNo.equals(""))
		{
			jpql += " and o.waferCompanyLot.waferCustomerLot.part.partNo=?";
			conditionVals.add(partNo);
		}
		List<WaferProcess> result = (List<WaferProcess>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(conditionVals).list();
		List<SelectItemDTO> waferProcessList = new ArrayList<SelectItemDTO>();
		for (WaferProcess entity : result) {
			SelectItemDTO dto = new SelectItemDTO();
			dto.setId(entity.getId());
			dto.setValue(entity.getWaferCompanyLot().getLotNo());
			//dto.setQty(entity.getQtyIn());
			waferProcessList.add(dto);
		}
		return waferProcessList;
	}
}
