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
import com.acetecsemi.godzilla.Godzilla.application.core.DeliveryNoteApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class DeliveryNoteApplicationImpl implements DeliveryNoteApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DeliveryNoteDTO getDeliveryNote(Long id) {
		DeliveryNote deliveryNote = DeliveryNote.load(DeliveryNote.class, id);
		DeliveryNoteDTO deliveryNoteDTO = new DeliveryNoteDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(deliveryNoteDTO, deliveryNote);
		} catch (Exception e) {
			e.printStackTrace();
		}
		deliveryNoteDTO.setId((java.lang.Long) deliveryNote.getId());
		return deliveryNoteDTO;
	}

	public DeliveryNoteDTO saveDeliveryNote(DeliveryNoteDTO deliveryNoteDTO) {
		DeliveryNote deliveryNote = new DeliveryNote();
		try {
			BeanUtils.copyProperties(deliveryNote, deliveryNoteDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		deliveryNote.save();
		deliveryNoteDTO.setId((java.lang.Long) deliveryNote.getId());
		return deliveryNoteDTO;
	}

	public void updateDeliveryNote(DeliveryNoteDTO deliveryNoteDTO) {
		DeliveryNote deliveryNote = DeliveryNote.get(DeliveryNote.class,
				deliveryNoteDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(deliveryNote, deliveryNoteDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeDeliveryNote(Long id) {
		this.removeDeliveryNotes(new Long[] { id });
	}

	public void removeDeliveryNotes(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			DeliveryNote deliveryNote = DeliveryNote.load(DeliveryNote.class,
					ids[i]);
			deliveryNote.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DeliveryNoteDTO> findAllDeliveryNote() {
		List<DeliveryNoteDTO> list = new ArrayList<DeliveryNoteDTO>();
		List<DeliveryNote> all = DeliveryNote.findAll(DeliveryNote.class);
		for (DeliveryNote deliveryNote : all) {
			DeliveryNoteDTO deliveryNoteDTO = new DeliveryNoteDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(deliveryNoteDTO, deliveryNote);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(deliveryNoteDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<DeliveryNoteDTO> pageQueryDeliveryNote(DeliveryNoteDTO queryVo,
			int currentPage, int pageSize) {
		List<DeliveryNoteDTO> result = new ArrayList<DeliveryNoteDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _deliveryNote from DeliveryNote _deliveryNote   left join _deliveryNote.createUser  left join _deliveryNote.lastModifyUser  left join _deliveryNote.customer  left join _deliveryNote.product  left join _deliveryNote.part  where 1=1 ");

		if (queryVo.getDnNo() != null && !"".equals(queryVo.getDnNo())) {
			jpql.append(" and _deliveryNote.dnNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getDnNo()));
		}

		if (queryVo.getSoNo() != null && !"".equals(queryVo.getSoNo())) {
			jpql.append(" and _deliveryNote.soNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getSoNo()));
		}

		if (queryVo.getCustomerPoN() != null
				&& !"".equals(queryVo.getCustomerPoN())) {
			jpql.append(" and _deliveryNote.customerPoN like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getCustomerPoN()));
		}

		if (queryVo.getIvoiceNo() != null && !"".equals(queryVo.getIvoiceNo())) {
			jpql.append(" and _deliveryNote.ivoiceNo like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getIvoiceNo()));
		}

		if (queryVo.getDDate() != null) {
			jpql.append(" and _deliveryNote.dDate between ? and ? ");
			conditionVals.add(queryVo.getDDate());
			conditionVals.add(queryVo.getDDateEnd());
		}

		if (queryVo.getPartNumber() != null
				&& !"".equals(queryVo.getPartNumber())) {
			jpql.append(" and _deliveryNote.partNumber like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getPartNumber()));
		}

		if (queryVo.getDescription() != null
				&& !"".equals(queryVo.getDescription())) {
			jpql.append(" and _deliveryNote.description like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getDescription()));
		}

		if (queryVo.getUm() != null && !"".equals(queryVo.getUm())) {
			jpql.append(" and _deliveryNote.um like ?");
			conditionVals.add(MessageFormat.format("%{0}%", queryVo.getUm()));
		}

		if (queryVo.getDeliveryDate() != null) {
			jpql.append(" and _deliveryNote.deliveryDate between ? and ? ");
			conditionVals.add(queryVo.getDeliveryDate());
			conditionVals.add(queryVo.getDeliveryDateEnd());
		}

		if (queryVo.getStatus() != null && !"".equals(queryVo.getStatus())) {
			jpql.append(" and _deliveryNote.status like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getStatus()));
		}
		Page<DeliveryNote> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (DeliveryNote deliveryNote : pages.getData()) {
			DeliveryNoteDTO deliveryNoteDTO = new DeliveryNoteDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(deliveryNoteDTO, deliveryNote);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(deliveryNoteDTO);
		}
		return new Page<DeliveryNoteDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByDeliveryNote(Long id) {
		String jpql = "select e from DeliveryNote o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByDeliveryNote(Long id) {
		String jpql = "select e from DeliveryNote o right join o.lastModifyUser e where o.id=?";
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
	public CustomerDTO findCustomerByDeliveryNote(Long id) {
		String jpql = "select e from DeliveryNote o right join o.customer e where o.id=?";
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
	public ProductDTO findProductByDeliveryNote(Long id) {
		String jpql = "select e from DeliveryNote o right join o.product e where o.id=?";
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
	public PartDTO findPartByDeliveryNote(Long id) {
		String jpql = "select e from DeliveryNote o right join o.part e where o.id=?";
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
