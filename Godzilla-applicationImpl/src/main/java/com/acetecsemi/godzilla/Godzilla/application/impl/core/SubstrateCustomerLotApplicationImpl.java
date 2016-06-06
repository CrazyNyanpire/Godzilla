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
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateCustomerLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SubstrateCustomerLotApplicationImpl implements
		SubstrateCustomerLotApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SubstrateCustomerLotDTO getSubstrateCustomerLot(Long id) {
		SubstrateCustomerLot substrateCustomerLot = SubstrateCustomerLot.load(
				SubstrateCustomerLot.class, id);
		SubstrateCustomerLotDTO substrateCustomerLotDTO = new SubstrateCustomerLotDTO();
		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(substrateCustomerLotDTO,
					substrateCustomerLot);
			substrateCustomerLotDTO.setPartNumber(substrateCustomerLot
					.getSubstratePart().getPartNo());
			substrateCustomerLotDTO.setProductId(substrateCustomerLot
					.getSubstratePart().getId());
			substrateCustomerLotDTO.setVenderId(substrateCustomerLot
					.getVender().getId());
			substrateCustomerLotDTO.setVenderName(substrateCustomerLot
					.getVender().getVenderName());
			substrateCustomerLotDTO.setProductionDate(substrateCustomerLot
					.getManufactureDate());
			substrateCustomerLotDTO.setGuaranteePeriod(substrateCustomerLot
					.getExpiryDate());
			substrateCustomerLotDTO.setLotType(substrateCustomerLot
					.getResLotType().getIdentifier());
			substrateCustomerLotDTO.setShippingTime(MyDateUtils.getDayHour(
					substrateCustomerLot.getShippingDate(), new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateCustomerLotDTO.setId((java.lang.Long) substrateCustomerLot
				.getId());
		return substrateCustomerLotDTO;
	}

	public SubstrateCustomerLotDTO saveSubstrateCustomerLot(
			SubstrateCustomerLotDTO substrateCustomerLotDTO) {
		SubstrateCustomerLot substrateCustomerLot = new SubstrateCustomerLot();
		try {
			BeanUtilsExtends.copyProperties(substrateCustomerLot,
					substrateCustomerLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		substrateCustomerLot.setVender(Vender.load(Vender.class,
				substrateCustomerLotDTO.getVenderId()));
		substrateCustomerLot.setSubstratePart(SubstratePart.load(SubstratePart.class,
				substrateCustomerLotDTO.getProductId()));
		User user;
		if (substrateCustomerLotDTO.getOptUser() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(substrateCustomerLotDTO.getOptUser()
					.getUserAccount());
		substrateCustomerLot.setCreateUser(user);
		substrateCustomerLot.setLastModifyUser(user);
		Resource resource = Resource.load(Resource.class,
				substrateCustomerLotDTO.getLotTypeId());
		substrateCustomerLot.setResLotType(resource);
		substrateCustomerLot.save();
		substrateCustomerLotDTO.setId((java.lang.Long) substrateCustomerLot
				.getId());
		return substrateCustomerLotDTO;
	}

	public void updateSubstrateCustomerLot(
			SubstrateCustomerLotDTO substrateCustomerLotDTO) {
		SubstrateCustomerLot substrateCustomerLot = SubstrateCustomerLot.get(
				SubstrateCustomerLot.class, substrateCustomerLotDTO.getId());
		// 设置要更新的值
		try {
			substrateCustomerLotDTO.setCreateDate(substrateCustomerLot
					.getCreateDate());
			BeanUtilsExtends.copyProperties(substrateCustomerLot,
					substrateCustomerLotDTO);
			substrateCustomerLot.setVender(Vender.load(Vender.class,
					substrateCustomerLotDTO.getVenderId()));
			substrateCustomerLot.setSubstratePart(SubstratePart.load(SubstratePart.class,
					substrateCustomerLotDTO.getProductId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeSubstrateCustomerLot(Long id) {
		this.removeSubstrateCustomerLots(new Long[] { id });
	}

	public void removeSubstrateCustomerLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SubstrateCustomerLot substrateCustomerLot = SubstrateCustomerLot
					.load(SubstrateCustomerLot.class, ids[i]);
			substrateCustomerLot.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SubstrateCustomerLotDTO> findAllSubstrateCustomerLot() {
		List<SubstrateCustomerLotDTO> list = new ArrayList<SubstrateCustomerLotDTO>();
		List<SubstrateCustomerLot> all = SubstrateCustomerLot
				.findAll(SubstrateCustomerLot.class);
		for (SubstrateCustomerLot substrateCustomerLot : all) {
			SubstrateCustomerLotDTO substrateCustomerLotDTO = new SubstrateCustomerLotDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(substrateCustomerLotDTO,
						substrateCustomerLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(substrateCustomerLotDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SubstrateCustomerLotDTO> pageQuerySubstrateCustomerLot(
			SubstrateCustomerLotDTO queryVo, int currentPage, int pageSize) {
		List<SubstrateCustomerLotDTO> result = new ArrayList<SubstrateCustomerLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _substrateCustomerLot from SubstrateCustomerLot _substrateCustomerLot   left join _substrateCustomerLot.createUser  left join _substrateCustomerLot.lastModifyUser  left join _substrateCustomerLot.vender  left join _substrateCustomerLot.runCardTemplate  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _substrateCustomerLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _substrateCustomerLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _substrateCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _substrateCustomerLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		if (queryVo.getQty() != null) {
			jpql.append(" and _substrateCustomerLot.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		if (queryVo.getStrip() != null) {
			jpql.append(" and _substrateCustomerLot.strip=?");
			conditionVals.add(queryVo.getStrip());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _substrateCustomerLot.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		
		
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _substrateCustomerLot.deliveryDate=?");
			conditionVals.add(queryVo.getDeliveryDate());
		}
		
		
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _substrateCustomerLot.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getDelayTime() != null) {
			jpql.append(" and _substrateCustomerLot.delayTime=?");
			conditionVals.add(queryVo.getDelayTime());
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _substrateCustomerLot.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getUserName() != null && !"".equals(queryVo.getUserName())) {
			jpql.append(" and _substrateCustomerLot.userName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getUserName()));
		}

		if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
			jpql.append(" and _substrateCustomerLot.remark like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
		}

		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _substrateCustomerLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}

		if (queryVo.getProductionDate() != null) {
			jpql.append(" and _substrateCustomerLot.productionDate between ? and ? ");
			conditionVals.add(queryVo.getProductionDate());
			conditionVals.add(queryVo.getProductionDateEnd());
		}

		if (queryVo.getGuaranteePeriod() != null
				&& !"".equals(queryVo.getGuaranteePeriod())) {
			jpql.append(" and _substrateCustomerLot.guaranteePeriod like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getGuaranteePeriod()));
		}

		if (queryVo.getVenderName() != null
				&& !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _substrateCustomerLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _substrateCustomerLot.currStatus in (")
					.append(queryVo.getCurrStatus()).append(")");
			// conditionVals.add(MessageFormat.format("%{0}%",
			// queryVo.getCurrStatus()));
		}else
			jpql.append(" and _substrateCustomerLot.currStatus not in (").append(StaticValue.NOT_SHOW_STATUS_CUSTOMER).append(")");
		jpql.append(" order by _substrateCustomerLot.shippingDate DESC");
		Page<SubstrateCustomerLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (SubstrateCustomerLot substrateCustomerLot : pages.getData()) {
			SubstrateCustomerLotDTO substrateCustomerLotDTO = new SubstrateCustomerLotDTO();

			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(substrateCustomerLotDTO,
						substrateCustomerLot);
				substrateCustomerLotDTO.setVenderName(substrateCustomerLot
						.getVender().getVenderName());
				substrateCustomerLotDTO.setPartNumber(substrateCustomerLot
						.getSubstratePart().getPartNo());
				substrateCustomerLotDTO.setProductionDate(substrateCustomerLot
						.getManufactureDate());
				substrateCustomerLotDTO.setGuaranteePeriod(substrateCustomerLot
						.getExpiryDate());
				substrateCustomerLotDTO.setVenderId(substrateCustomerLot.getVender().getId());
				substrateCustomerLotDTO.setProductId(substrateCustomerLot.getSubstratePart().getId());
				substrateCustomerLotDTO.setLotType(substrateCustomerLot
						.getResLotType().getIdentifier());
				substrateCustomerLotDTO.setShippingTime(MyDateUtils.getDayHour(
						substrateCustomerLot.getShippingDate(), new Date()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(substrateCustomerLotDTO);
		}
		return new Page<SubstrateCustomerLotDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	public Map<String, Object> pageQuerySubstrateCustomerLotTotal(
			SubstrateCustomerLotDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_substrateCustomerLot.qty),sum(_substrateCustomerLot.strip),count(*) from SubstrateCustomerLot _substrateCustomerLot   left join _substrateCustomerLot.createUser  left join _substrateCustomerLot.lastModifyUser  left join _substrateCustomerLot.vender  left join _substrateCustomerLot.runCardTemplate  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _substrateCustomerLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _substrateCustomerLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _substrateCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getBatchNo() != null && !"".equals(queryVo.getBatchNo())) {
			jpql.append(" and _substrateCustomerLot.batchNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getBatchNo()));
		}
		if (queryVo.getQty() != null) {
			jpql.append(" and _substrateCustomerLot.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		if (queryVo.getStrip() != null) {
			jpql.append(" and _substrateCustomerLot.strip=?");
			conditionVals.add(queryVo.getStrip());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _substrateCustomerLot.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _substrateCustomerLot.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getDelayTime() != null) {
			jpql.append(" and _substrateCustomerLot.delayTime=?");
			conditionVals.add(queryVo.getDelayTime());
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _substrateCustomerLot.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getUserName() != null && !"".equals(queryVo.getUserName())) {
			jpql.append(" and _substrateCustomerLot.userName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getUserName()));
		}

		if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
			jpql.append(" and _substrateCustomerLot.remark like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
		}

		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _substrateCustomerLot.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}

		if (queryVo.getProductionDate() != null) {
			jpql.append(" and _substrateCustomerLot.productionDate between ? and ? ");
			conditionVals.add(queryVo.getProductionDate());
			conditionVals.add(queryVo.getProductionDateEnd());
		}

		if (queryVo.getGuaranteePeriod() != null
				&& !"".equals(queryVo.getGuaranteePeriod())) {
			jpql.append(" and _substrateCustomerLot.guaranteePeriod like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getGuaranteePeriod()));
		}

		if (queryVo.getVenderName() != null
				&& !"".equals(queryVo.getVenderName())) {
			jpql.append(" and _substrateCustomerLot.vender.venderName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getVenderName()));
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _substrateCustomerLot.currStatus in (")
					.append(queryVo.getCurrStatus()).append(")");
			// conditionVals.add(MessageFormat.format("%{0}%",
			// queryVo.getCurrStatus()));
		}else
			jpql.append(" and _substrateCustomerLot.currStatus not in (").append(StaticValue.NOT_SHOW_STATUS_CUSTOMER).append(")");
		jpql.append(" order by _substrateCustomerLot.shippingDate DESC");
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
	public UserDTO findCreateUserBySubstrateCustomerLot(Long id) {
		String jpql = "select e from SubstrateCustomerLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySubstrateCustomerLot(Long id) {
		String jpql = "select e from SubstrateCustomerLot o right join o.lastModifyUser e where o.id=?";
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
	public VenderDTO findVenderBySubstrateCustomerLot(Long id) {
		String jpql = "select e from SubstrateCustomerLot o right join o.vender e where o.id=?";
		Vender result = (Vender) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		VenderDTO dto = new VenderDTO();
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
	public ProductDTO findProductBySubstrateCustomerLot(Long id) {
		String jpql = "select e from SubstrateCustomerLot o right join o.product e where o.id=?";
		Product result = (Product) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ProductDTO dto = new ProductDTO();
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
	public Page<DefineStationProcessDTO> findDefineStationProcessesBySubstrateCustomerLot(
			Long id, int currentPage, int pageSize) {
		List<DefineStationProcessDTO> result = new ArrayList<DefineStationProcessDTO>();
		String jpql = "select e from SubstrateCustomerLot o right join o.defineStationProcesses e where o.id=?";
		Page<DefineStationProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (DefineStationProcess entity : pages.getData()) {
			DefineStationProcessDTO dto = new DefineStationProcessDTO();
			try {
				BeanUtilsExtends.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<DefineStationProcessDTO>(Page.getStartOfPage(
				currentPage, pageSize), pages.getResultCount(), pageSize,
				result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RunCardTemplateDTO findRunCardTemplateBySubstrateCustomerLot(Long id) {
		String jpql = "select e from SubstrateCustomerLot o right join o.runCardTemplate e where o.id=?";
		RunCardTemplate result = (RunCardTemplate) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		RunCardTemplateDTO dto = new RunCardTemplateDTO();
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
