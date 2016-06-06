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
import com.acetecsemi.godzilla.Godzilla.application.core.BomListItemApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class BomListItemApplicationImpl implements BomListItemApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BomListItemDTO getBomListItem(Long id) {
		BomListItem bomListItem = BomListItem.load(BomListItem.class, id);
		BomListItemDTO bomListItemDTO = new BomListItemDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(bomListItemDTO, bomListItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bomListItemDTO.setId((java.lang.Long) bomListItem.getId());
		return bomListItemDTO;
	}

	public BomListItemDTO saveBomListItem(BomListItemDTO bomListItemDTO) {
		BomListItem bomListItem = new BomListItem();
		try {
			BeanUtils.copyProperties(bomListItem, bomListItemDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bomListItem.save();
		bomListItemDTO.setId((java.lang.Long) bomListItem.getId());
		return bomListItemDTO;
	}

	public void updateBomListItem(BomListItemDTO bomListItemDTO) {
		BomListItem bomListItem = BomListItem.get(BomListItem.class,
				bomListItemDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(bomListItem, bomListItemDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeBomListItem(Long id) {
		this.removeBomListItems(new Long[] { id });
	}

	public void removeBomListItems(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			BomListItem bomListItem = BomListItem.load(BomListItem.class,
					ids[i]);
			bomListItem.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BomListItemDTO> findAllBomListItem() {
		List<BomListItemDTO> list = new ArrayList<BomListItemDTO>();
		List<BomListItem> all = BomListItem.findAll(BomListItem.class);
		for (BomListItem bomListItem : all) {
			BomListItemDTO bomListItemDTO = new BomListItemDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(bomListItemDTO, bomListItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(bomListItemDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BomListItemDTO> pageQueryBomListItem(BomListItemDTO queryVo,
			int currentPage, int pageSize) {
		List<BomListItemDTO> result = new ArrayList<BomListItemDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _bomListItem from BomListItem _bomListItem   left join _bomListItem.createUser  left join _bomListItem.lastModifyUser  left join _bomListItem.material  left join _bomListItem.bomList  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _bomListItem.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _bomListItem.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getSort() != null && !"".equals(queryVo.getSort())) {
			jpql.append(" and _bomListItem.sort like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSort()));
		}

		if (queryVo.getStage() != null && !"".equals(queryVo.getStage())) {
			jpql.append(" and _bomListItem.stage like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getStage()));
		}
		if (queryVo.getSn() != null) {
			jpql.append(" and _bomListItem.sn=?");
			conditionVals.add(queryVo.getSn());
		}

		if (queryVo.getQty() != null) {
			jpql.append(" and _bomListItem.qty=?");
			conditionVals.add(queryVo.getQty());
		}

		Page<BomListItem> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (BomListItem bomListItem : pages.getData()) {
			BomListItemDTO bomListItemDTO = new BomListItemDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(bomListItemDTO, bomListItem);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(bomListItemDTO);
		}
		return new Page<BomListItemDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByBomListItem(Long id) {
		String jpql = "select e from BomListItem o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByBomListItem(Long id) {
		String jpql = "select e from BomListItem o right join o.lastModifyUser e where o.id=?";
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
	public MaterialDTO findMaterialByBomListItem(Long id) {
		String jpql = "select e from BomListItem o right join o.material e where o.id=?";
		Material result = (Material) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		MaterialDTO dto = new MaterialDTO();
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
	public BomListDTO findBomListByBomListItem(Long id) {
		String jpql = "select e from BomListItem o right join o.bomList e where o.id=?";
		BomList result = (BomList) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		BomListDTO dto = new BomListDTO();
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
	public BomListItemDTO findBomListItemByProductAndMaterial(Long productId,
			Long materialId) {
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _bomListItem from BomListItem _bomListItem where 1=1 ");

		if (productId != null) {
			jpql.append(" and _bomListItem.bomList.product.id=?");
			conditionVals.add(productId);
		}

		if (materialId != null) {
			jpql.append(" and _bomListItem.material.id=?");
			conditionVals.add(materialId);
		}
		BomListItem bomListItem = (BomListItem) getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		BomListItemDTO bomListItemDTO = new BomListItemDTO();

		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(bomListItemDTO, bomListItem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bomListItemDTO;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BomListItemDTO> findBomListItemByProduct(Long productId,String bomListType,String materialName) {
		Map<String,Object> conditionVals = new HashMap<String,Object>();
		List<BomListItemDTO> result = new ArrayList<BomListItemDTO>();
//		StringBuilder jpql = new StringBuilder(
//				"select _bomListItem from BomListItem _bomListItem where 1=1 ");

		if (productId != null) {
//			jpql.append(" and _bomListItem.bomList.product.id=? and _bomListItem.bomList.bomListType=?");
			conditionVals.put("bomList.product.id",productId);
			conditionVals.put("bomList.bomListType",bomListType);
		}
		if (materialName != null) {
//			jpql.append(" and _bomListItem.material.materialName.materialName=?");
//			conditionVals.add(materialName);
			conditionVals.put("material.materialName.materialName", materialName);
		}
		
//		List<BomListItem> bomListItems = (List<BomListItem>) getQueryChannelService()
//				.createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(0, 50).pagedList().getData();
		List<BomListItem> bomListItems = BomListItem.findByProperties(BomListItem.class, conditionVals);
		for(BomListItem bomListItem:bomListItems)
		{
			// 将domain转成VO
			BomListItemDTO bomListItemDTO = new BomListItemDTO();
			try {
				BeanUtilsExtends.copyProperties(bomListItemDTO, bomListItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(bomListItemDTO);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BomListItemDTO findBomListItemByWaferProcessAndMaterial(Long waferProcessId,
			Long materialId) {
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, waferProcessId);
		if(waferProcess != null)
			return this.findBomListItemByProductAndMaterial(waferProcess.getWaferCompanyLot().getWaferCustomerLot().getProduct().getId(), materialId);
		else
			return new BomListItemDTO();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BomListItemDTO findBomListItemByManufactureProcessAndMaterial(Long manufactureProcessId,
			Long materialProcessId) {
		ManufactureProcess manufactureProcess = ManufactureProcess.load(ManufactureProcess.class, manufactureProcessId);
		MaterialProcess materialProcess =MaterialProcess.load(MaterialProcess.class, materialProcessId);
		if(manufactureProcess != null && materialProcess != null)
			return this.findBomListItemByProductAndMaterial(manufactureProcess.getManufactureLot().getProduct().getId(), materialProcess.getMaterialCompanyLot().getMaterialLot().getMaterial().getId());
		else
			return new BomListItemDTO();
	}

}
