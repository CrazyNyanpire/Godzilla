package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.SerialNumberUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;
import com.acetecsemi.godzilla.Godzilla.application.core.SpartPartApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class SpartPartApplicationImpl implements SpartPartApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SpartPartDTO getSpartPart(Long id) {
		SpartPart spartPart = SpartPart.load(SpartPart.class, id);
		SpartPartDTO spartPartDTO = new SpartPartDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(spartPartDTO, spartPart);
			if (spartPart.getVender() != null)
				spartPartDTO.setVender(spartPart.getVender().getVenderCode());
			if (spartPart.getProducts() != null
					&& spartPart.getProducts().size() > 0) {
				for (Product product : spartPart.getProducts()) {
					spartPartDTO.setProduct(product.getProductName());
				}
			}
//			if (spartPart.getStations() != null
//					&& spartPart.getStations().size() > 0) {
//				for (Station station : spartPart.getStations()) {
//					spartPartDTO.setProduct(station.getStationName());
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		spartPartDTO.setLotType(spartPart.getResLotType().getName());
		spartPartDTO.setLotTypeId(spartPart.getResLotType().getId());
		spartPartDTO.setId((java.lang.Long) spartPart.getId());
		return spartPartDTO;
	}

	public SpartPartDTO saveSpartPart(SpartPartDTO spartPartDTO) {
		SpartPart spartPart = new SpartPart();
		try {
			BeanUtils.copyProperties(spartPart, spartPartDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		spartPart.setVender(Vender.load(Vender.class, spartPartDTO.getVenderId()));
		spartPart.setResLotType(Resource.load(Resource.class, spartPartDTO.getLotTypeId()));
		Set<Product> products = new HashSet<Product>();
		products.add(Product.load(Product.class, spartPartDTO.getProductId()));
		spartPart.setProducts(products);
		Date date = new Date();
		spartPart.setCreateDate(date);
		spartPart.setLastModifyDate(date);
		spartPart.setCurrStatus("Waiting");
		spartPart.save();
		spartPartDTO.setId((java.lang.Long) spartPart.getId());
		return spartPartDTO;
	}

	public void updateSpartPart(SpartPartDTO spartPartDTO) {
		SpartPart spartPart = SpartPart.get(SpartPart.class,
				spartPartDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(spartPart, spartPartDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		spartPart.setVender(Vender.load(Vender.class, spartPartDTO.getVenderId()));
		spartPart.setResLotType(Resource.load(Resource.class, spartPartDTO.getLotTypeId()));
		Set<Product> products = new HashSet<Product>();
		products.add(Product.load(Product.class, spartPartDTO.getProductId()));
		spartPart.setProducts(products);
		Date date = new Date();
		spartPart.setLastModifyDate(date);
		spartPart.save();
	}

	public void removeSpartPart(Long id) {
		this.removeSpartParts(new Long[] { id });
	}

	public void removeSpartParts(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			SpartPart spartPart = SpartPart.load(SpartPart.class, ids[i]);
			spartPart.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SpartPartDTO> findAllSpartPart() {
		List<SpartPartDTO> list = new ArrayList<SpartPartDTO>();
		List<SpartPart> all = SpartPart.findAll(SpartPart.class);
		for (SpartPart spartPart : all) {
			SpartPartDTO spartPartDTO = new SpartPartDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(spartPartDTO, spartPart);
				if (spartPart.getVender() != null)
					spartPartDTO.setVender(spartPart.getVender()
							.getVenderCode());
				if (spartPart.getProducts() != null
						&& spartPart.getProducts().size() > 0) {
					for (Product product : spartPart.getProducts()) {
						spartPartDTO.setProduct(product.getProductName());
					}
				}
//				if (spartPart.getStations() != null
//						&& spartPart.getStations().size() > 0) {
//					for (Station station : spartPart.getStations()) {
//						spartPartDTO.setProduct(station.getStationName());
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(spartPartDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<SpartPartDTO> pageQuerySpartPart(SpartPartDTO queryVo,
			int currentPage, int pageSize) {
		List<SpartPartDTO> result = new ArrayList<SpartPartDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _spartPart from SpartPart _spartPart   left join _spartPart.createUser  left join _spartPart.lastModifyUser  left join _spartPart.resLotType  left join _spartPart.vender  left join _spartPart.defineStationProcess  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _spartPart.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _spartPart.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getPartName() != null && !"".equals(queryVo.getPartName())) {
			jpql.append(" and _spartPart.partName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartName()));
		}

		if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
			jpql.append(" and _spartPart.partId like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getPartId()));
		}

		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _spartPart.partNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}

		if (queryVo.getDescription() != null
				&& !"".equals(queryVo.getDescription())) {
			jpql.append(" and _spartPart.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDescription()));
		}

		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _spartPart.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _spartPart.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _spartPart.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _spartPart.deliveryDate between ? and ? ");
			conditionVals.add(queryVo.getDeliveryDate());
			conditionVals.add(queryVo.getDeliveryDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _spartPart.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _spartPart.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getUserName() != null && !"".equals(queryVo.getUserName())) {
			jpql.append(" and _spartPart.userName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getUserName()));
		}

		if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
			jpql.append(" and _spartPart.remark like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _spartPart.currStatus like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCurrStatus()));
		}
		else
		{
			jpql.append(" and _spartPart.currStatus not in (" + StaticValue.NOT_SHOW_STATUS +",'Received') ");
		}
		if (queryVo.getManufactureDate() != null) {
			jpql.append(" and _spartPart.manufactureDate between ? and ? ");
			conditionVals.add(queryVo.getManufactureDate());
			conditionVals.add(queryVo.getManufactureDateEnd());
		}

		if (queryVo.getExpiryDate() != null) {
			jpql.append(" and _spartPart.expiryDate between ? and ? ");
			conditionVals.add(queryVo.getExpiryDate());
			conditionVals.add(queryVo.getExpiryDateEnd());
		}
		if (queryVo.getIsEngineering() != null) {
			jpql.append(" and _spartPart.isEngineering=?");
			conditionVals.add(queryVo.getIsEngineering());
		}

		Page<SpartPart> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (SpartPart spartPart : pages.getData()) {
			SpartPartDTO spartPartDTO = new SpartPartDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(spartPartDTO, spartPart);
				if (spartPart.getVender() != null)
					spartPartDTO.setVender(spartPart.getVender()
							.getVenderCode());
				if (spartPart.getProducts() != null
						&& spartPart.getProducts().size() > 0) {
					for (Product product : spartPart.getProducts()) {
						spartPartDTO.setProduct(product.getProductName());
					}
				}
//				if (spartPart.getStations() != null
//						&& spartPart.getStations().size() > 0) {
//					for (Station station : spartPart.getStations()) {
//						spartPartDTO.setProduct(station.getStationName());
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			spartPartDTO.setShippingTime((MyDateUtils.getDayHour(
					spartPart.getShippingDate(), new Date())));
			spartPartDTO.setDeliveryDate(spartPartDTO.getDeliveryDate());
			result.add(spartPartDTO);
		}
		return new Page<SpartPartDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String, Object> pageQuerySpartPartTotal(SpartPartDTO queryVo) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select select sum(_spartPart.qty),sum(_spartPart.firstPQty),sum(_spartPart.secondPQty),sum(_spartPart.thirdPQty),sum(_spartPart.wafer),count(*) from SpartPart _spartPart   left join _spartPart.createUser  left join _spartPart.lastModifyUser  left join _spartPart.resLotType  left join _spartPart.vender  left join _spartPart.defineStationProcess  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _spartPart.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _spartPart.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getPartName() != null && !"".equals(queryVo.getPartName())) {
			jpql.append(" and _spartPart.partName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartName()));
		}

		if (queryVo.getPartId() != null && !"".equals(queryVo.getPartId())) {
			jpql.append(" and _spartPart.partId like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getPartId()));
		}

		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _spartPart.partNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}

		if (queryVo.getDescription() != null
				&& !"".equals(queryVo.getDescription())) {
			jpql.append(" and _spartPart.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDescription()));
		}

		if (queryVo.getPon() != null && !"".equals(queryVo.getPon())) {
			jpql.append(" and _spartPart.pon like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPon()));
		}

		if (queryVo.getCustomerLotNo() != null
				&& !"".equals(queryVo.getCustomerLotNo())) {
			jpql.append(" and _spartPart.customerLotNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerLotNo()));
		}

		if (queryVo.getShippingDate() != null) {
			jpql.append(" and _spartPart.shippingDate between ? and ? ");
			conditionVals.add(queryVo.getShippingDate());
			conditionVals.add(queryVo.getShippingDateEnd());
		}
		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _spartPart.deliveryDate between ? and ? ");
			conditionVals.add(queryVo.getDeliveryDate());
			conditionVals.add(queryVo.getDeliveryDateEnd());
		}
		if (queryVo.getShippingTime() != null) {
			jpql.append(" and _spartPart.shippingTime=?");
			conditionVals.add(queryVo.getShippingTime());
		}

		if (queryVo.getCustomerOrder() != null
				&& !"".equals(queryVo.getCustomerOrder())) {
			jpql.append(" and _spartPart.customerOrder like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerOrder()));
		}

		if (queryVo.getUserName() != null && !"".equals(queryVo.getUserName())) {
			jpql.append(" and _spartPart.userName like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getUserName()));
		}

		if (queryVo.getRemark() != null && !"".equals(queryVo.getRemark())) {
			jpql.append(" and _spartPart.remark like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getRemark()));
		}

		if (queryVo.getCurrStatus() != null
				&& !"".equals(queryVo.getCurrStatus())) {
			jpql.append(" and _spartPart.currStatus like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCurrStatus()));
		}

		if (queryVo.getManufactureDate() != null) {
			jpql.append(" and _spartPart.manufactureDate between ? and ? ");
			conditionVals.add(queryVo.getManufactureDate());
			conditionVals.add(queryVo.getManufactureDateEnd());
		}

		if (queryVo.getExpiryDate() != null) {
			jpql.append(" and _spartPart.expiryDate between ? and ? ");
			conditionVals.add(queryVo.getExpiryDate());
			conditionVals.add(queryVo.getExpiryDateEnd());
		}
		if (queryVo.getIsEngineering() != null) {
			jpql.append(" and _spartPart.isEngineering=?");
			conditionVals.add(queryVo.getIsEngineering());
		}

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
	public UserDTO findCreateUserBySpartPart(Long id) {
		String jpql = "select e from SpartPart o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserBySpartPart(Long id) {
		String jpql = "select e from SpartPart o right join o.lastModifyUser e where o.id=?";
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
	public Page<ProductDTO> findProductsBySpartPart(Long id, int currentPage,
			int pageSize) {
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		String jpql = "select e from SpartPart o right join o.products e where o.id=?";
		Page<Product> pages = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (Product entity : pages.getData()) {
			ProductDTO dto = new ProductDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<ProductDTO>(Page.getStartOfPage(currentPage, pageSize),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<StationDTO> findStationsBySpartPart(Long id, int currentPage,
			int pageSize) {
		List<StationDTO> result = new ArrayList<StationDTO>();
		String jpql = "select e from SpartPart o right join o.stations e where o.id=?";
		Page<Station> pages = getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (Station entity : pages.getData()) {
			StationDTO dto = new StationDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<StationDTO>(Page.getStartOfPage(currentPage, pageSize),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ResourceDTO findResLotTypeBySpartPart(Long id) {
		String jpql = "select e from SpartPart o right join o.resLotType e where o.id=?";
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
	public VenderDTO findVenderBySpartPart(Long id) {
		String jpql = "select e from SpartPart o right join o.vender e where o.id=?";
		Vender result = (Vender) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		VenderDTO dto = new VenderDTO();
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
	public DefineStationProcessDTO findDefineStationProcessBySpartPart(Long id) {
		String jpql = "select e from SpartPart o right join o.defineStationProcess e where o.id=?";
		DefineStationProcess result = (DefineStationProcess) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		DefineStationProcessDTO dto = new DefineStationProcessDTO();
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
	public SpartPartCompanyLotDTO getSpartPartCompanyLot(Long id) {
		SpartPartCompanyLot spartPartCompanyLot = SpartPartCompanyLot.load(SpartPartCompanyLot.class, id);
		SpartPartCompanyLotDTO spartPartCompanyLotDTO = new SpartPartCompanyLotDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(spartPartCompanyLotDTO, spartPartCompanyLot);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spartPartCompanyLotDTO;
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SpartPartCompanyLotDTO getSpartPartCompanyLotBySpartPart(Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("spartPart.id", id);
		List<SpartPartCompanyLot> spartPartCompanyLots = SpartPartCompanyLot.findByProperties(SpartPartCompanyLot.class, map);
		SpartPartCompanyLot spartPartCompanyLot = null;
		if(spartPartCompanyLots != null && spartPartCompanyLots.size()>0)
		{
			spartPartCompanyLot = spartPartCompanyLots.get(0);
		}
		else
		{
			return null;
		}
		SpartPartCompanyLotDTO spartPartCompanyLotDTO = new SpartPartCompanyLotDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(spartPartCompanyLotDTO, spartPartCompanyLot);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spartPartCompanyLotDTO;
	}
	public SpartPartCompanyLotDTO saveSpartPartCompanyLot(
			SpartPartCompanyLotDTO spartPartCompanyLotDTO) {
		SpartPartCompanyLot spartPartCompanyLot = new SpartPartCompanyLot();
		try {
			BeanUtils.copyProperties(spartPartCompanyLot, spartPartCompanyLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpartPart spartPart = SpartPart.get(SpartPart.class, spartPartCompanyLotDTO.getSpartPartId());
		spartPartCompanyLot.setSpartPart(spartPart);
//		materialCompanyLot.setMaterialLot(MaterialLot.load(MaterialLot.class, materialCompanyLotDTO.getMaterialLotId()));
		spartPartCompanyLot.save();
		spartPartCompanyLotDTO
				.setId((java.lang.Long) spartPartCompanyLot.getId());
		return spartPartCompanyLotDTO;
	}
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getNewLotNo() {
		String lotNo = "P";
		String lastLotNo = this.findMaxLotNo(null);
		if(lastLotNo == null){
			lotNo += "A00001";
		}else{
			String number = lastLotNo.split("-")[0].substring(1);
			lotNo += SerialNumberUtils.sn(6, Integer.parseInt(number.substring(1)), number.substring(0,1));
		}
		lotNo += "-01";
		return lotNo;
	}		
	
	private String findMaxLotNo(Long id){
		StringBuilder jpql = new StringBuilder("select max(substring(_materialCompanyLot.lotNo,1,LENGTH(_materialCompanyLot.lotNo)-3)) from SpartPartCompanyLot _materialCompanyLot  where 1=1 ");
		List<Object> conditionVals = new ArrayList<Object>();
		if (id != null) {
	   		jpql.append(" and _materialCompanyLot.materialLot.vender.id = ? ");
	   		conditionVals.add(id);
	   	}		
		jpql.append("");
		Object lastLotNo = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).singleResult();
		if(lastLotNo == null){
			return null;
		}else{
			return lastLotNo.toString();
		}
	}
}
