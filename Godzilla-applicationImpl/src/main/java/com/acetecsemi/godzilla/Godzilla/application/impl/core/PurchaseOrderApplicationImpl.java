package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.core.PurchaseOrderApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class PurchaseOrderApplicationImpl implements PurchaseOrderApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PurchaseOrderDTO getPurchaseOrder(Long id) {
		PurchaseOrder purchaseOrder = PurchaseOrder.load(PurchaseOrder.class,
				id);
		PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(purchaseOrderDTO, purchaseOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		purchaseOrderDTO.setId((java.lang.Long) purchaseOrder.getId());
		return purchaseOrderDTO;
	}

	public PurchaseOrderDTO savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		try {
			BeanUtils.copyProperties(purchaseOrder, purchaseOrderDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		purchaseOrder.save();
		purchaseOrderDTO.setId((java.lang.Long) purchaseOrder.getId());
		return purchaseOrderDTO;
	}

	public void updatePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
		PurchaseOrder purchaseOrder = PurchaseOrder.get(PurchaseOrder.class,
				purchaseOrderDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(purchaseOrder, purchaseOrderDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removePurchaseOrder(Long id) {
		this.removePurchaseOrders(new Long[] { id });
	}

	public void removePurchaseOrders(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			PurchaseOrder purchaseOrder = PurchaseOrder.load(
					PurchaseOrder.class, ids[i]);
			purchaseOrder.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PurchaseOrderDTO> findAllPurchaseOrder() {
		List<PurchaseOrderDTO> list = new ArrayList<PurchaseOrderDTO>();
		List<PurchaseOrder> all = PurchaseOrder.findAll(PurchaseOrder.class);
		for (PurchaseOrder purchaseOrder : all) {
			PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(purchaseOrderDTO, purchaseOrder);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(purchaseOrderDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PurchaseOrderDTO> pageQueryPurchaseOrder(
			PurchaseOrderDTO queryVo, int currentPage, int pageSize) {
		List<PurchaseOrderDTO> result = new ArrayList<PurchaseOrderDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _purchaseOrder from PurchaseOrder _purchaseOrder   left join _purchaseOrder.createUser  left join _purchaseOrder.lastModifyUser  left join _purchaseOrder.customer  left join _purchaseOrder.product  left join _purchaseOrder.part  where 1=1 ");

		if (queryVo.getPoNumber() != null && !"".equals(queryVo.getPoNumber())) {
			jpql.append(" and _purchaseOrder.poNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPoNumber()));
		}

		if (queryVo.getPoDate() != null) {
			jpql.append(" and _purchaseOrder.poDate between ? and ? ");
			conditionVals.add(queryVo.getPoDate());
			conditionVals.add(queryVo.getPoDateEnd());
		}

		if (queryVo.getCurrency() != null && !"".equals(queryVo.getCurrency())) {
			jpql.append(" and _purchaseOrder.currency like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCurrency()));
		}

		if (queryVo.getPaymentTerm() != null
				&& !"".equals(queryVo.getPaymentTerm())) {
			jpql.append(" and _purchaseOrder.paymentTerm like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPaymentTerm()));
		}

		if (queryVo.getPpoType() != null && !"".equals(queryVo.getPpoType())) {
			jpql.append(" and _purchaseOrder.ppoType like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPpoType()));
		}

		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _purchaseOrder.partNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}

		if (queryVo.getDescription() != null
				&& !"".equals(queryVo.getDescription())) {
			jpql.append(" and _purchaseOrder.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDescription()));
		}

		if (queryVo.getUm() != null && !"".equals(queryVo.getUm())) {
			jpql.append(" and _purchaseOrder.um like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getUm()));
		}

		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _purchaseOrder.deliveryDate between ? and ? ");
			conditionVals.add(queryVo.getDeliveryDate());
			conditionVals.add(queryVo.getDeliveryDateEnd());
		}
		Page<PurchaseOrder> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (PurchaseOrder purchaseOrder : pages.getData()) {
			PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(purchaseOrderDTO, purchaseOrder);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(purchaseOrderDTO);
		}
		return new Page<PurchaseOrderDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByPurchaseOrder(Long id) {
		String jpql = "select e from PurchaseOrder o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByPurchaseOrder(Long id) {
		String jpql = "select e from PurchaseOrder o right join o.lastModifyUser e where o.id=?";
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
	public CustomerDTO findCustomerByPurchaseOrder(Long id) {
		String jpql = "select e from PurchaseOrder o right join o.customer e where o.id=?";
		Customer result = (Customer) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		CustomerDTO dto = new CustomerDTO();
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
	public ProductDTO findProductByPurchaseOrder(Long id) {
		String jpql = "select e from PurchaseOrder o right join o.product e where o.id=?";
		Product result = (Product) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ProductDTO dto = new ProductDTO();
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
	public PartDTO findPartByPurchaseOrder(Long id) {
		String jpql = "select e from PurchaseOrder o right join o.part e where o.id=?";
		Part result = (Part) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		PartDTO dto = new PartDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

}
