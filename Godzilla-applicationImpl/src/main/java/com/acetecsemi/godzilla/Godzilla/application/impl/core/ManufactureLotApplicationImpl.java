package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.SerialNumberUtils;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ManufactureLotApplicationImpl implements ManufactureLotApplication {

	private QueryChannelService queryChannel;

	Logger log = Logger.getLogger(ManufactureLotApplicationImpl.class);

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureLotDTO getManufactureLot(Long id) {
		ManufactureLot manufactureLot = ManufactureLot.load(
				ManufactureLot.class, id);
		ManufactureLotDTO manufactureLotDTO = new ManufactureLotDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(manufactureLotDTO, manufactureLot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		manufactureLotDTO.setId((java.lang.Long) manufactureLot.getId());
		return manufactureLotDTO;
	}

	public ManufactureLotDTO saveManufactureLot(
			ManufactureLotDTO manufactureLotDTO) {
		ManufactureLot manufactureLot = new ManufactureLot();
		try {
			BeanUtils.copyProperties(manufactureLot, manufactureLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		SubstrateProcess substrateProcess = SubstrateProcess.load(
				SubstrateProcess.class,
				manufactureLotDTO.getSubstrateProcessId());
		manufactureLot.setSubstrateProcess(substrateProcess);
		Product product = Product.load(
				Product.class,
				manufactureLotDTO.getProductId());
		Customer customer = Customer.load(
				Customer.class,
				manufactureLotDTO.getCustomerId());
		manufactureLot.setProduct(product);
		manufactureLot.setCustomer(customer);
		//User user;
		//if (manufactureLotDTO.getOptUser() == null) {
			//user = User.findByUserAccount("1");
		//} else
			//user = User.findByUserAccount(manufactureLotDTO.getOptUser()
			//		.getUserAccount());
		//substrateCompanyLot.setLastModifyUser(user);
		manufactureLot.save();
		manufactureLotDTO.setId((java.lang.Long) manufactureLot.getId());
		return manufactureLotDTO;
	}

	public void updateManufactureLot(ManufactureLotDTO manufactureLotDTO) {
		ManufactureLot manufactureLot = ManufactureLot.get(
				ManufactureLot.class, manufactureLotDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(manufactureLot, manufactureLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeManufactureLot(Long id) {
		this.removeManufactureLots(new Long[] { id });
	}

	public void removeManufactureLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			ManufactureLot manufactureLot = ManufactureLot.load(
					ManufactureLot.class, ids[i]);
			manufactureLot.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ManufactureLotDTO> findAllManufactureLot() {
		List<ManufactureLotDTO> list = new ArrayList<ManufactureLotDTO>();
		List<ManufactureLot> all = ManufactureLot.findAll(ManufactureLot.class);
		for (ManufactureLot manufactureLot : all) {
			ManufactureLotDTO manufactureLotDTO = new ManufactureLotDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureLotDTO, manufactureLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(manufactureLotDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureLotDTO> pageQueryManufactureLot(
			ManufactureLotDTO queryVo, int currentPage, int pageSize) {
		List<ManufactureLotDTO> result = new ArrayList<ManufactureLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _manufactureLot from ManufactureLot _manufactureLot   left join _manufactureLot.createUser  left join _manufactureLot.lastModifyUser  left join _manufactureLot.mergeManufactureLot  left join _manufactureLot.parentManufactureLot  left join _manufactureLot.nowManufactureProcess  left join _manufactureLot.manufactureLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _manufactureLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _manufactureLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _manufactureLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		Page<ManufactureLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (ManufactureLot manufactureLot : pages.getData()) {
			ManufactureLotDTO manufactureLotDTO = new ManufactureLotDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(manufactureLotDTO, manufactureLot);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(manufactureLotDTO);
		}
		return new Page<ManufactureLotDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByManufactureLot(Long id) {
		String jpql = "select e from ManufactureLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByManufactureLot(Long id) {
		String jpql = "select e from ManufactureLot o right join o.lastModifyUser e where o.id=?";
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
	public ManufactureLotDTO findMergeManufactureLotByManufactureLot(Long id) {
		String jpql = "select e from ManufactureLot o right join o.mergeManufactureLot e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureLotDTO findParentManufactureLotByManufactureLot(Long id) {
		String jpql = "select e from ManufactureLot o right join o.parentManufactureLot e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ManufactureLotDTO> findManufactureLotsByManufactureLot(Long id,
			int currentPage, int pageSize) {
		List<ManufactureLotDTO> result = new ArrayList<ManufactureLotDTO>();
		String jpql = "select e from ManufactureLot o right join o.manufactureLots e where o.id=?";
		Page<ManufactureLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (ManufactureLot entity : pages.getData()) {
			ManufactureLotDTO dto = new ManufactureLotDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<ManufactureLotDTO>(Page.getStartOfPage(currentPage,
				pageSize), pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureProcessDTO findNowManufactureProcessByManufactureLot(
			Long id) {
		String jpql = "select e from ManufactureLot o right join o.nowManufactureProcess e where o.id=?";
		ManufactureProcess result = (ManufactureProcess) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ManufactureProcessDTO dto = new ManufactureProcessDTO();
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
	public Page<ManufactureProcessDTO> findManufactureProcessesByManufactureLot(
			Long id, int currentPage, int pageSize) {
		List<ManufactureProcessDTO> result = new ArrayList<ManufactureProcessDTO>();
		String jpql = "select e from ManufactureLot o right join o.manufactureProcesses e where o.id=?";
		Page<ManufactureProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (ManufactureProcess entity : pages.getData()) {
			ManufactureProcessDTO dto = new ManufactureProcessDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<ManufactureProcessDTO>(Page.getStartOfPage(currentPage,
				pageSize), pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureLotDTO findManufactureLotByManufactureLot(Long id) {
		String jpql = "select e from ManufactureLot o right join o.manufactureLot e where o.id=?";
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferCompanyLotDTO> findWaferCompanyLotsByManufactureLot(
			Long id, int currentPage, int pageSize) {
		List<WaferCompanyLotDTO> result = new ArrayList<WaferCompanyLotDTO>();
		String jpql = "select e from ManufactureLot o right join o.waferCompanyLots e where o.id=?";
		Page<WaferCompanyLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (WaferCompanyLot entity : pages.getData()) {
			WaferCompanyLotDTO dto = new WaferCompanyLotDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<WaferCompanyLotDTO>(Page.getStartOfPage(currentPage,
				pageSize), pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getNewLotNo(Long customerId) {
		Customer customer = Customer.load(Customer.class, customerId);
		String lotNo = customer.getCustomerType()
				+ customer.getCustomerCode() + "-";
		//String lotNo = "F001-";
		String lastLotNo = this.findMaxLotNo(customer.getId());
		// F001-14380002-01
		lotNo += MyDateUtils.getYYWW();
		if (lastLotNo == null) {
			lotNo += "0001";
		} else {
			String number = lastLotNo.split("-")[1];
			lotNo += SerialNumberUtils.sn(4,
					Integer.parseInt(number.substring(4)), "");
		}
		lotNo += "-01";
		log.info("getNewLotNo : " + lotNo);
		return lotNo;
	}

	private String findMaxLotNo(Long id) {
		StringBuilder jpql = new StringBuilder(
				"select max(substring(_manufactureLot.lotNo,1,LENGTH(_manufactureLot.lotNo)-2)) from ManufactureLot _manufactureLot  where 1=1 ");
		List<Object> conditionVals = new ArrayList<Object>();
		if (id != null) {
			jpql.append(" and _manufactureLot.customer.id = ? ");
			conditionVals.add(id);
		}
		jpql.append("");
		Object lastLotNo = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		if (lastLotNo == null) {
			return null;
		} else {
			return lastLotNo.toString();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureLotDTO findManufactureLotByCompanyId(Long id) {
		String jpql = "select o from ManufactureLot o left join o.substrateCompanyLot e where e.id=?";
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
}
