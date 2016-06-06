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
import com.acetecsemi.godzilla.Godzilla.application.core.DefineStationProcessApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class DefineStationProcessApplicationImpl implements
		DefineStationProcessApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DefineStationProcessDTO getDefineStationProcess(Long id) {
		DefineStationProcess defineStationProcess = DefineStationProcess.load(
				DefineStationProcess.class, id);
		DefineStationProcessDTO defineStationProcessDTO = new DefineStationProcessDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(defineStationProcessDTO,
					defineStationProcess);
		} catch (Exception e) {
			e.printStackTrace();
		}
		defineStationProcessDTO.setId((java.lang.Long) defineStationProcess
				.getId());
		return defineStationProcessDTO;
	}

	public DefineStationProcessDTO saveDefineStationProcess(
			DefineStationProcessDTO defineStationProcessDTO) {
		DefineStationProcess defineStationProcess = new DefineStationProcess();
		try {
			BeanUtils.copyProperties(defineStationProcess,
					defineStationProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		defineStationProcess.save();
		defineStationProcessDTO.setId((java.lang.Long) defineStationProcess
				.getId());
		return defineStationProcessDTO;
	}

	public void updateDefineStationProcess(
			DefineStationProcessDTO defineStationProcessDTO) {
		DefineStationProcess defineStationProcess = DefineStationProcess.get(
				DefineStationProcess.class, defineStationProcessDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(defineStationProcess,
					defineStationProcessDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeDefineStationProcess(Long id) {
		this.removeDefineStationProcesss(new Long[] { id });
	}

	public void removeDefineStationProcesss(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			DefineStationProcess defineStationProcess = DefineStationProcess
					.load(DefineStationProcess.class, ids[i]);
			defineStationProcess.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<DefineStationProcessDTO> findAllDefineStationProcess() {
		List<DefineStationProcessDTO> list = new ArrayList<DefineStationProcessDTO>();
		List<DefineStationProcess> all = DefineStationProcess
				.findAll(DefineStationProcess.class);
		for (DefineStationProcess defineStationProcess : all) {
			DefineStationProcessDTO defineStationProcessDTO = new DefineStationProcessDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(defineStationProcessDTO,
						defineStationProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(defineStationProcessDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<DefineStationProcessDTO> pageQueryDefineStationProcess(
			DefineStationProcessDTO queryVo, int currentPage, int pageSize) {
		List<DefineStationProcessDTO> result = new ArrayList<DefineStationProcessDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _defineStationProcess from DefineStationProcess _defineStationProcess   left join _defineStationProcess.createUser  left join _defineStationProcess.lastModifyUser  left join _defineStationProcess.waferCustomerLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _defineStationProcess.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _defineStationProcess.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		Page<DefineStationProcess> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (DefineStationProcess defineStationProcess : pages.getData()) {
			DefineStationProcessDTO defineStationProcessDTO = new DefineStationProcessDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(defineStationProcessDTO,
						defineStationProcess);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(defineStationProcessDTO);
		}
		return new Page<DefineStationProcessDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByDefineStationProcess(Long id) {
		String jpql = "select e from DefineStationProcess o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByDefineStationProcess(Long id) {
		String jpql = "select e from DefineStationProcess o right join o.lastModifyUser e where o.id=?";
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
	public WaferCustomerLotDTO findWaferCustomerLotByDefineStationProcess(
			Long id) {
		String jpql = "select e from DefineStationProcess o right join o.waferCustomerLot e where o.id=?";
		WaferCustomerLot result = (WaferCustomerLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		WaferCustomerLotDTO dto = new WaferCustomerLotDTO();
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
	public Page<StationDTO> findStationsByDefineStationProcess(Long id,
			int currentPage, int pageSize) {
		List<StationDTO> result = new ArrayList<StationDTO>();
		String jpql = "select e from DefineStationProcess o right join o.stations e where o.id=?";
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
	public List<DefineStationProcessDTO> findDefineStationProcessByType(
			Integer id) {
		String jpql = "select o from DefineStationProcess o where o.type=?";
		@SuppressWarnings("unchecked")
		List<DefineStationProcess> list = (List<DefineStationProcess>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.list();
		List<DefineStationProcessDTO> result = new ArrayList<DefineStationProcessDTO>();
		for (DefineStationProcess entity : list) {
			DefineStationProcessDTO dto = new DefineStationProcessDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return result;
	}
}
