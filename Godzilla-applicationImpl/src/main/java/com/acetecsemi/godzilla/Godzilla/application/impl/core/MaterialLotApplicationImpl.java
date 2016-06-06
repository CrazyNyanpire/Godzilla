package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MaterialLotApplicationImpl implements MaterialLotApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialLotDTO getMaterialLot(Long id) {
		MaterialLot materialLot = MaterialLot.load(MaterialLot.class, id);

		MaterialLotDTO materialLotDTO = new MaterialLotDTO();
		MaterialDTO materialDTO = new MaterialDTO();
		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(materialLotDTO, materialLot);
			BeanUtilsExtends.copyProperties(materialDTO,
					materialLot.getMaterial());
			materialLotDTO.setMaterialId(materialDTO.getId());
			materialLotDTO.setMaterialDTO(materialDTO);
			materialLotDTO.setMaterialName(materialLot.getMaterial().getMaterialName().getMaterialName());
			materialLotDTO.setMaterialNameId(materialLot.getMaterial().getMaterialName().getId());
			materialLotDTO.setPartId(materialDTO.getPartId());
			materialLotDTO.setPartNameCN(materialDTO.getPartNameCN());
			materialLotDTO.setMaterialType(materialDTO.getMaterialType());
			materialLotDTO.setUnit(materialLot.getMaterial().getMinUnit());//显示小单位
			materialLotDTO.setVenderName(materialLot.getVender()
					.getVenderName());
			materialLotDTO.setVenderId(materialLot.getVender().getId());
			materialLotDTO.setProductionDate(materialLot.getManufactureDate());
			materialLotDTO.setGuaranteePeriod(materialLot.getExpiryDate());
			materialLotDTO.setLotType(materialLot.getResLotType()
					.getIdentifier());
			materialLotDTO.setShippingTime(MyDateUtils.getDayHour(
					materialLot.getShippingDate(), new Date()));
			materialLotDTO.setInCapacity(materialLot.getInCapacity());
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialLotDTO.setId((java.lang.Long) materialLot.getId());
		return materialLotDTO;
	}

	public MaterialLotDTO saveMaterialLot(MaterialLotDTO materialLotDTO) {
		MaterialLot materialLot = new MaterialLot();
		Material material = Material.load(Material.class,
				Long.parseLong(materialLotDTO.getPartId()));//前台回传的materialId为partId
		try {
			materialLot.setBatchNo(materialLotDTO.getBatchNo());
			materialLot.setQty(materialLotDTO.getQty());
			materialLot.setPon(materialLotDTO.getPon());
			materialLot.setShippingDate(materialLotDTO.getShippingDate());
			materialLot.setDeliveryDate(materialLotDTO.getDeliveryDate());
			if (materialLotDTO.getManufactureDate() != null) {
				materialLot.setManufactureDate(materialLotDTO
						.getManufactureDate());
			}
			if (materialLotDTO.getExpiryDate() != null) {
				materialLot.setExpiryDate(materialLotDTO.getExpiryDate());
			}
			materialLot.setRemark(materialLotDTO.getRemark());
			materialLot.setIsEngineering(materialLotDTO.getIsEngineering());
			materialLot.setCurrStatus(materialLotDTO.getCurrStatus());
			materialLot.setUserName(materialLotDTO.getUserName());
			materialLot.setInCapacity(materialLotDTO.getInCapacity());
			Resource resource = Resource.load(Resource.class,
					materialLotDTO.getLotTypeId());
			materialLot.setResLotType(resource);
			// BeanUtilsExtends.copyProperties(material,
			// materialLotDTO.getMaterialDTO());
		} catch (Exception e) {
			e.printStackTrace();
		}

		materialLot.setVender(Vender.load(Vender.class,
				materialLotDTO.getVenderId()));

		User user;
		if (materialLotDTO.getOptUser() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(materialLotDTO.getOptUser()
					.getUserAccount());
		materialLot.setCreateUser(user);
		materialLot.setLastModifyUser(user);
		materialLot.setCreateDate(materialLotDTO.getCreateDate());
		materialLot.setLastModifyDate(materialLotDTO.getLastModifyDate());
		materialLot.setMaterial(material);
		materialLot.save();
		materialLotDTO.setId((java.lang.Long) materialLot.getId());
		return materialLotDTO;
	}

	public void updateMaterialLot(MaterialLotDTO materialLotDTO) {
		MaterialLot materialLot = MaterialLot.get(MaterialLot.class,
				materialLotDTO.getId());
//		Material material = Material.get(Material.class, Long.parseLong(materialLotDTO.getPartId()));
//		try
//		{
//			material = Material.get(Material.class, Long.parseLong(materialLotDTO.getPartId()));
//		}
//		catch(NumberFormatException ne)
//		{
//			material = Material.get(Material.class, materialLotDTO.getMaterialId());
//		}
		// 设置要更新的值
		User user;
		if (materialLotDTO.getOptUser() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(materialLotDTO.getOptUser()
					.getUserAccount());
		try {
			BeanUtilsExtends.copyProperties(materialLot, materialLotDTO);
//			material.setMaterialName(materialLotDTO.getMaterialName());
//			material.setPartId(materialLotDTO.getPartId());
//			material.setPartNameCN(materialLotDTO.getPartNameCN());
//			material.setUnit(materialLotDTO.getUnit());
			materialLot.setCreateUser(user);
			materialLot.setLastModifyUser(user);
			// BeanUtilsExtends.copyProperties(material,
			// materialLotDTO.getMaterialDTO());
			materialLot.setVender(Vender.get(Vender.class,
					materialLotDTO.getVenderId()));
//			materialLot.setMaterial(material);
			materialLot.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeMaterialLot(Long id) {
		this.removeMaterialLots(new Long[] { id });
	}

	public void removeMaterialLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MaterialLot materialLot = MaterialLot.load(MaterialLot.class,
					ids[i]);
			materialLot.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialLotDTO> findAllMaterialLot() {
		List<MaterialLotDTO> list = new ArrayList<MaterialLotDTO>();
		List<MaterialLot> all = MaterialLot.findAll(MaterialLot.class);
		for (MaterialLot materialLot : all) {
			MaterialLotDTO materialLotDTO = new MaterialLotDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(materialLotDTO, materialLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialLotDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialLotDTO> pageQueryMaterialLot(MaterialLotDTO queryVo,
			int currentPage, int pageSize) {
		List<MaterialLotDTO> result = new ArrayList<MaterialLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _materialLot from MaterialLot _materialLot   left join _materialLot.createUser  left join _materialLot.lastModifyUser  left join _materialLot.customer  left join _materialLot.material  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _materialLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _materialLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _materialLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _materialLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		if (queryVo.getQty() != null) {
			jpql.append(" and _materialLot.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		if (queryVo.getStrip() != null) {
			jpql.append(" and _materialLot.strip=?");
			conditionVals.add(queryVo.getStrip());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _materialLot.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _materialLot.deliveryDate between ? and ? ");
			conditionVals.add(queryVo.getDeliveryDate());
			conditionVals.add(queryVo.getDeliveryDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _materialLot.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}
		if (queryVo.getPartNameCN() != null
				&& !"".equals(queryVo.getPartNameCN())) {
			jpql.append(" and _materialLot.material.partNameCN like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNameCN()));
		}
		if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
			jpql.append(" and _materialLot.material.partId like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getPartId()));
		}
		if (queryVo.getMaterialName() != null
				&& !"".equals(queryVo.getMaterialName())) {
			jpql.append(" and _materialLot.material.materialName.materialName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getMaterialName()));
		}
		if (queryVo.getVenderName() != null
				&& !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _materialLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}
		if (queryVo.getMaterialType() != null) {
			jpql.append(" and _materialLot.material.materialName.materialType=?");
			conditionVals.add(queryVo.getMaterialType());
		}
		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _materialLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}
		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _materialLot.currStatus in (")
					.append(queryVo.getCurrStatus()).append(")");
			// conditionVals.add(MessageFormat.format("%{0}%",
			// queryVo.getCurrStatus()));
		}else
			jpql.append(" and _materialLot.currStatus not in (").append(StaticValue.NOT_SHOW_STATUS_CUSTOMER).append(")");
		jpql.append(" order by _materialLot.shippingDate DESC");
		Page<MaterialLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (MaterialLot materialLot : pages.getData()) {
			MaterialLotDTO materialLotDTO = new MaterialLotDTO();
			MaterialDTO materialDTO = new MaterialDTO();
			// 将domain转成VO
			try {
				materialLotDTO.setId(materialLot.getId());
				materialLotDTO.setBatchNo(materialLot.getBatchNo());
				materialLotDTO.setQty(materialLot.getQty());
				materialLotDTO.setPon(materialLot.getPon());
				materialLotDTO.setShippingDate(materialLot.getShippingDate());
				materialLotDTO.setDeliveryDate(materialLot.getDeliveryDate());
				if (materialLot.getManufactureDate() != null) {
					materialLotDTO.setManufactureDate(materialLot
							.getManufactureDate());
				}
				if (materialLot.getExpiryDate() != null) {
					materialLotDTO.setExpiryDate(materialLot.getExpiryDate());
				}
				materialLotDTO.setRemark(materialLot.getRemark());
				materialLotDTO.setIsEngineering(materialLot.getIsEngineering());
				materialLotDTO.setCurrStatus(materialLot.getCurrStatus());
				materialLotDTO.setUserName(materialLot.getUserName());
				BeanUtilsExtends.copyProperties(materialDTO,
						materialLot.getMaterial());
				materialLotDTO.setMaterialId(materialDTO.getId());
				materialLotDTO.setMaterialDTO(materialDTO);
				materialLotDTO.setMaterialName(materialLot.getMaterial().getMaterialName().getMaterialName());
				materialLotDTO.setPartId(materialDTO.getPartId());
				materialLotDTO.setPartNameCN(materialDTO.getPartNameCN());
				materialLotDTO.setMaterialType(materialDTO.getMaterialType());
				materialLotDTO.setUnit(materialLot.getMaterial().getMinUnit());//显示小单位
				materialLotDTO.setVenderName(materialLot.getVender()
						.getVenderName());
				materialLotDTO.setVenderId(materialLot.getVender().getId());
				materialLotDTO.setProductionDate(materialLot
						.getManufactureDate());
				materialLotDTO.setGuaranteePeriod(materialLot.getExpiryDate());
				materialLotDTO.setLotType(materialLot.getResLotType()
						.getIdentifier());
				materialLotDTO.setInCapacity(materialLot.getInCapacity());
				materialLotDTO.setShippingTime(MyDateUtils.getDayHour(
						materialLot.getShippingDate(), new Date()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(materialLotDTO);
		}
		return new Page<MaterialLotDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	public Map<String, Object> pageQueryMaterialLotTotal(MaterialLotDTO queryVo) {
		List<MaterialLotDTO> result = new ArrayList<MaterialLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_materialLot.qty),count(*),sum(_materialLot.inCapacity) from MaterialLot _materialLot   left join _materialLot.createUser  left join _materialLot.lastModifyUser  left join _materialLot.customer  left join _materialLot.material  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _materialLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _materialLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _materialLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _materialLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		if (queryVo.getQty() != null) {
			jpql.append(" and _materialLot.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		if (queryVo.getStrip() != null) {
			jpql.append(" and _materialLot.strip=?");
			conditionVals.add(queryVo.getStrip());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _materialLot.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _materialLot.deliveryDate between ? and ? ");
			conditionVals.add(queryVo.getDeliveryDate());
			conditionVals.add(queryVo.getDeliveryDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _materialLot.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}
		if (queryVo.getPartNameCN() != null
				&& !"".equals(queryVo.getPartNameCN())) {
			jpql.append(" and _materialLot.material.partNameCN like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNameCN()));
		}
		if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
			jpql.append(" and _materialLot.material.partId like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getPartId()));
		}
		if (queryVo.getMaterialName() != null
				&& !"".equals(queryVo.getMaterialName())) {
			jpql.append(" and _materialLot.material.materialName.materialName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getMaterialName()));
		}
		if (queryVo.getVenderName() != null
				&& !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _materialLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}
		if (queryVo.getMaterialType() != null) {
			jpql.append(" and _materialLot.material.materialName.materialType=?");
			conditionVals.add(queryVo.getMaterialType());
		}
		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _materialLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}
		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _materialLot.currStatus in (")
					.append(queryVo.getCurrStatus()).append(")");
			// conditionVals.add(MessageFormat.format("%{0}%",
			// queryVo.getCurrStatus()));
		}else
			jpql.append(" and _materialLot.currStatus not in (").append(StaticValue.NOT_SHOW_STATUS_CUSTOMER).append(")");
		jpql.append(" order by _materialLot.shippingDate DESC");
		Object[] object = (Object[]) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dieTotal", String.valueOf(object[0]));
		resultMap.put("countLot", String.valueOf(object[1]));
		resultMap.put("inCapacityTotal", String.valueOf(object[2]));
		return resultMap;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMaterialLot(Long id) {
		String jpql = "select e from MaterialLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByMaterialLot(Long id) {
		String jpql = "select e from MaterialLot o right join o.lastModifyUser e where o.id=?";
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
	public CustomerDTO findCustomerByMaterialLot(Long id) {
		String jpql = "select e from MaterialLot o right join o.customer e where o.id=?";
		Customer result = (Customer) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		CustomerDTO dto = new CustomerDTO();
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
	public MaterialDTO findMaterialByMaterialLot(Long id) {
		String jpql = "select e from MaterialLot o right join o.material e where o.id=?";
		Material result = (Material) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		MaterialDTO dto = new MaterialDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}
}
