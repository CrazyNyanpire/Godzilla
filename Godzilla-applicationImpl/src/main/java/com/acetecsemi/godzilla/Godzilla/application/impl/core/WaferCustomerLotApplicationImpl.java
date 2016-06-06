package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.text.MessageFormat;

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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferCustomerLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class WaferCustomerLotApplicationImpl implements
		WaferCustomerLotApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferCustomerLotDTO getWaferCustomerLot(Long id) {
		WaferCustomerLot waferCustomerLot = WaferCustomerLot.load(
				WaferCustomerLot.class, id);
		WaferCustomerLotDTO waferCustomerLotDTO = new WaferCustomerLotDTO();
		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(waferCustomerLotDTO, waferCustomerLot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferCustomerLotDTO.setId((java.lang.Long) waferCustomerLot.getId());
		waferCustomerLotDTO.setCustomerId(waferCustomerLot.getCustomer().getId());
		waferCustomerLotDTO.setCustomerCode(waferCustomerLot.getCustomer().getCustomerCode());
		waferCustomerLotDTO.setProductId(waferCustomerLot.getProduct().getId());
		waferCustomerLotDTO.setPartId(waferCustomerLot.getPart().getId());
		waferCustomerLotDTO.setProductName(waferCustomerLot.getProduct().getProductName());
		waferCustomerLotDTO.setPartNumber(waferCustomerLot.getProduct().getPartNo());
		waferCustomerLotDTO.setProductionDate(waferCustomerLot.getManufactureDate());
		waferCustomerLotDTO.setGuaranteePeriod(waferCustomerLot.getExpiryDate());
		waferCustomerLotDTO.setLotType(waferCustomerLot.getResLotType().getIdentifier());
		waferCustomerLotDTO.setDeliveryDate(waferCustomerLot.getDeliveryDate());
		return waferCustomerLotDTO;
	}

	public WaferCustomerLotDTO saveWaferCustomerLot(
			WaferCustomerLotDTO waferCustomerLotDTO) {
		WaferCustomerLot waferCustomerLot = new WaferCustomerLot();
		try {
			BeanUtilsExtends.copyProperties(waferCustomerLot, waferCustomerLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferCustomerLot.setCustomer(Customer.load(Customer.class, waferCustomerLotDTO.getCustomerId()));
		waferCustomerLot.setProduct(Product.load(Product.class, waferCustomerLotDTO.getProductId())); 
		waferCustomerLot.setPart(Part.load(Part.class, waferCustomerLotDTO.getPartId()));//
		User user ;
		if(waferCustomerLotDTO.getOptUser() == null ){
			user = User.findByUserAccount("1");
		}else
			user = User.findByUserAccount(waferCustomerLotDTO.getOptUser().getUserAccount());
		waferCustomerLot.setCreateUser(user);
		waferCustomerLot.setLastModifyUser(user);
		Resource resource = Resource
				.load(Resource.class, waferCustomerLotDTO.getLotTypeId());
		waferCustomerLot.setResLotType(resource);
		waferCustomerLot.save();
		waferCustomerLotDTO.setId((java.lang.Long) waferCustomerLot.getId());
		
		return waferCustomerLotDTO;
	}

	public void updateWaferCustomerLot(WaferCustomerLotDTO waferCustomerLotDTO) {
		WaferCustomerLot waferCustomerLot = WaferCustomerLot.get(
				WaferCustomerLot.class, waferCustomerLotDTO.getId());
		// 设置要更新的值
		try {
			waferCustomerLotDTO.setCreateDate(waferCustomerLot.getCreateDate());
			BeanUtilsExtends.copyProperties(waferCustomerLot, waferCustomerLotDTO);
			waferCustomerLot.setCustomer(Customer.load(Customer.class, waferCustomerLotDTO.getCustomerId()));
			waferCustomerLot.setProduct(Product.load(Product.class, waferCustomerLotDTO.getProductId())); 
			waferCustomerLot.setPart(Part.load(Part.class, waferCustomerLotDTO.getPartId()));
			waferCustomerLot.setManufactureDate(waferCustomerLotDTO.getProductionDate());
			waferCustomerLot.setExpiryDate(waferCustomerLotDTO.getGuaranteePeriod());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeWaferCustomerLot(Long id) {
		this.removeWaferCustomerLots(new Long[] { id });
	}

	public void removeWaferCustomerLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferCustomerLot waferCustomerLot = WaferCustomerLot.load(
					WaferCustomerLot.class, ids[i]);
			waferCustomerLot.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferCustomerLotDTO> findAllWaferCustomerLot() {
		List<WaferCustomerLotDTO> list = new ArrayList<WaferCustomerLotDTO>();
		List<WaferCustomerLot> all = WaferCustomerLot
				.findAll(WaferCustomerLot.class);
		for (WaferCustomerLot waferCustomerLot : all) {
			WaferCustomerLotDTO waferCustomerLotDTO = new WaferCustomerLotDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferCustomerLotDTO, waferCustomerLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferCustomerLotDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferCustomerLotDTO> pageQueryWaferCustomerLot(
			WaferCustomerLotDTO queryVo, int currentPage, int pageSize) {
		List<WaferCustomerLotDTO> result = new ArrayList<WaferCustomerLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _waferCustomerLot from WaferCustomerLot _waferCustomerLot   left join _waferCustomerLot.createUser  left join _waferCustomerLot.lastModifyUser  left join _waferCustomerLot.customer  left join _waferCustomerLot.product  left join _waferCustomerLot.runCardTemplate  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _waferCustomerLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _waferCustomerLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _waferCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getQty() != null) {
			jpql.append(" and _waferCustomerLot.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		if (queryVo.getFirstPQty() != null) {
			jpql.append(" and _waferCustomerLot.firstPQty=?");
			conditionVals.add(queryVo.getFirstPQty());
		}

		if (queryVo.getSecondPQty() != null) {
			jpql.append(" and _waferCustomerLot.secondPQty=?");
			conditionVals.add(queryVo.getSecondPQty());
		}

		if (queryVo.getThirdPQty() != null) {
			jpql.append(" and _waferCustomerLot.thirdPQty=?");
			conditionVals.add(queryVo.getThirdPQty());
		}

		if (queryVo.getWafer() != null) {
			jpql.append(" and _waferCustomerLot.wafer=?");
			conditionVals.add(queryVo.getWafer());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _waferCustomerLot.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _waferCustomerLot.deliveryDate=?");
			conditionVals.add(queryVo.getDeliveryDate());
		}

		if (queryVo.getDelayTime() != null) {
			jpql.append(" and _waferCustomerLot.delayTime=?");
			conditionVals.add(queryVo.getDelayTime());
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _waferCustomerLot.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getUserName() != null && !"".equals(queryVo.getUserName())) {
			jpql.append(" and _waferCustomerLot.userName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getUserName()));
		}
		
		if (queryVo.getCustomerCode() != null && !"".equals(queryVo.getCustomerCode())) {
			jpql.append(" and _waferCustomerLot.customer.customerCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerCode()));
		}

		if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
			jpql.append(" and _waferCustomerLot.remark like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
		}
		
		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _waferCustomerLot.currStatus in (").append(queryVo.getCurrStatus()).append(")");
			//conditionVals.add(MessageFormat.format("%{0}%",
			//		queryVo.getCurrStatus()));
		}
		else
		{
			jpql.append(" and _waferCustomerLot.currStatus not in (").append(StaticValue.NOT_SHOW_STATUS_CUSTOMER).append(")");
		}
		if(queryVo.getSortname() != null && !"".equals(queryVo.getSortname()))
		{
			
			if(queryVo.getSortname().equals("productName"))
			{
				jpql.append(" order by _waferCustomerLot.product.productName ").append(queryVo.getSortorder());
			}
			else if(queryVo.getSortname().equals("customerCode"))
			{
				jpql.append(" order by _waferCustomerLot.customer.customerCode ").append(queryVo.getSortorder());
			}
			else if(queryVo.getSortname().equals("partNumber"))
			{
				jpql.append(" order by _waferCustomerLot.part.partNo ").append(queryVo.getSortorder());
			}
			else
			{
				jpql.append(" order by _waferCustomerLot.").append(queryVo.getSortname()).append(" ").append(queryVo.getSortorder());
			}
		}
		else
		{
			jpql.append(" order by _waferCustomerLot.currStatus DESC,_waferCustomerLot.shippingDate DESC");
		}
		Page<WaferCustomerLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		int waferStatusId = 1;
		for (WaferCustomerLot waferCustomerLot : pages.getData()) {
			WaferCustomerLotDTO waferCustomerLotDTO = new WaferCustomerLotDTO();

			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferCustomerLotDTO, waferCustomerLot);
				if(waferCustomerLot.getCustomer() != null){
					waferCustomerLotDTO.setCustomerId(waferCustomerLot.getCustomer().getId());
					waferCustomerLotDTO.setCustomerCode(waferCustomerLot.getCustomer().getCustomerCode());
				}
				if(waferCustomerLot.getProduct() != null){
					waferCustomerLotDTO.setProductId(waferCustomerLot.getProduct().getId());
					waferCustomerLotDTO.setProductName(waferCustomerLot.getProduct().getProductName());
				}
				if(waferCustomerLot.getPart() != null){
					waferCustomerLotDTO.setPartId(waferCustomerLot.getPart().getId());
					waferCustomerLotDTO.setPartNumber(waferCustomerLot.getPart().getPartNo());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			waferStatusId = waferCustomerLot.getWaferStatusId().intValue();
			waferCustomerLotDTO.setWaferStatusId(waferStatusId);
			if(waferStatusId == 1)
			{
				waferCustomerLotDTO.setWaferStatusName("CP PASS");
			}
			else
			{
				waferCustomerLotDTO.setWaferStatusName("Pending CP");
			}
			waferCustomerLotDTO.setLotType(waferCustomerLot.getResLotType().getIdentifier());
			waferCustomerLotDTO.setProductionDate(waferCustomerLot.getManufactureDate());
			waferCustomerLotDTO.setGuaranteePeriod(waferCustomerLot.getExpiryDate());
			waferCustomerLotDTO.setDeliveryDate(waferCustomerLot.getDeliveryDate());
			result.add(waferCustomerLotDTO);
		}
		return new Page<WaferCustomerLotDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}
	
	
	public int waferCustomerLotTotal(WaferCustomerLotDTO queryVo) {
		List<WaferCustomerLotDTO> result = new ArrayList<WaferCustomerLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select count(_waferCustomerLot.id) as totalRec from WaferCustomerLot _waferCustomerLot   left join _waferCustomerLot.createUser  left join _waferCustomerLot.lastModifyUser  left join _waferCustomerLot.customer  left join _waferCustomerLot.product  left join _waferCustomerLot.runCardTemplate  where 1=1");

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _waferCustomerLot.currStatus in(").append(queryVo.getCurrStatus()).append(")");
		}
		jpql.append(" order by _waferCustomerLot.currStatus DESC, _waferCustomerLot.shippingDate DESC");
		Long object = (Long) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		return object.intValue();
	}
	public Map<String, Object> pageQueryWaferCustomerLotTotal(
			WaferCustomerLotDTO queryVo) {
		List<WaferCustomerLotDTO> result = new ArrayList<WaferCustomerLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select sum(_waferCustomerLot.qty),sum(_waferCustomerLot.firstPQty),sum(_waferCustomerLot.secondPQty),sum(_waferCustomerLot.thirdPQty),sum(_waferCustomerLot.wafer),count(*) from WaferCustomerLot _waferCustomerLot   left join _waferCustomerLot.createUser  left join _waferCustomerLot.lastModifyUser  left join _waferCustomerLot.customer  left join _waferCustomerLot.product  left join _waferCustomerLot.runCardTemplate  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _waferCustomerLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _waferCustomerLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _waferCustomerLot.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getQty() != null) {
			jpql.append(" and _waferCustomerLot.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		if (queryVo.getFirstPQty() != null) {
			jpql.append(" and _waferCustomerLot.firstPQty=?");
			conditionVals.add(queryVo.getFirstPQty());
		}

		if (queryVo.getSecondPQty() != null) {
			jpql.append(" and _waferCustomerLot.secondPQty=?");
			conditionVals.add(queryVo.getSecondPQty());
		}

		if (queryVo.getThirdPQty() != null) {
			jpql.append(" and _waferCustomerLot.thirdPQty=?");
			conditionVals.add(queryVo.getThirdPQty());
		}

		if (queryVo.getWafer() != null) {
			jpql.append(" and _waferCustomerLot.wafer=?");
			conditionVals.add(queryVo.getWafer());
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _waferCustomerLot.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _waferCustomerLot.deliveryDate=?");
			conditionVals.add(queryVo.getDeliveryDate());
		}

		if (queryVo.getDelayTime() != null) {
			jpql.append(" and _waferCustomerLot.delayTime=?");
			conditionVals.add(queryVo.getDelayTime());
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _waferCustomerLot.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getUserName() != null && !"".equals(queryVo.getUserName())) {
			jpql.append(" and _waferCustomerLot.userName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getUserName()));
		}
		
		if (queryVo.getCustomerCode() != null && !"".equals(queryVo.getCustomerCode())) {
			jpql.append(" and _waferCustomerLot.customer.customerCode like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerCode()));
		}

		if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
			jpql.append(" and _waferCustomerLot.remark like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
		}
		
		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _waferCustomerLot.currStatus in (").append(queryVo.getCurrStatus()).append(")");
			//conditionVals.add(MessageFormat.format("%{0}%",
			//		queryVo.getCurrStatus()));
		}else
			jpql.append(" and _waferCustomerLot.currStatus not in (").append(StaticValue.NOT_SHOW_STATUS_CUSTOMER).append(")");
		jpql.append(" order by _waferCustomerLot.currStatus DESC,_waferCustomerLot.shippingDate DESC");
		Object[] object = ( Object[]) getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dieTotal", String.valueOf(object[0]));
		resultMap.put("firstPQty", String.valueOf(object[1]));
		resultMap.put("secondPQty", String.valueOf(object[2]));
		resultMap.put("thirdPQty", String.valueOf(object[3]));
		resultMap.put("waferTotal", String.valueOf(object[4]));
		resultMap.put("countLot", String.valueOf(object[5]));
		return resultMap;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByWaferCustomerLot(Long id) {
		String jpql = "select e from WaferCustomerLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByWaferCustomerLot(Long id) {
		String jpql = "select e from WaferCustomerLot o right join o.lastModifyUser e where o.id=?";
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
	public CustomerDTO findCustomerByWaferCustomerLot(Long id) {
		String jpql = "select e from WaferCustomerLot o right join o.customer e where o.id=?";
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
	public ProductDTO findProductByWaferCustomerLot(Long id) {
		String jpql = "select e from WaferCustomerLot o right join o.product e where o.id=?";
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
	public Page<DefineStationProcessDTO> findDefineStationProcessesByWaferCustomerLot(
			Long id, int currentPage, int pageSize) {
		List<DefineStationProcessDTO> result = new ArrayList<DefineStationProcessDTO>();
		String jpql = "select e from WaferCustomerLot o right join o.defineStationProcesses e where o.id=?";
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
	public RunCardTemplateDTO findRunCardTemplateByWaferCustomerLot(Long id) {
		String jpql = "select e from WaferCustomerLot o right join o.runCardTemplate e where o.id=?";
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
