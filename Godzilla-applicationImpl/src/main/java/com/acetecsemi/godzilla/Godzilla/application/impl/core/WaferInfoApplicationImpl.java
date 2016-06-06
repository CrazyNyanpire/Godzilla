package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferInfoApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class WaferInfoApplicationImpl implements WaferInfoApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public WaferInfoDTO getWaferInfo(Long id) {
		WaferInfo waferInfo = WaferInfo.load(WaferInfo.class, id);
		WaferInfoDTO waferInfoDTO = new WaferInfoDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(waferInfoDTO, waferInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferInfoDTO.setId((java.lang.Long) waferInfo.getId());
		return waferInfoDTO;
	}

	public WaferInfoDTO saveWaferInfo(WaferInfoDTO waferInfoDTO) {
		WaferInfo waferInfo = new WaferInfo();
		try {
			BeanUtils.copyProperties(waferInfo, waferInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waferInfo.save();
		waferInfoDTO.setId((java.lang.Long) waferInfo.getId());
		return waferInfoDTO;
	}

	public void updateWaferInfo(WaferInfoDTO waferInfoDTO) {
		WaferInfo waferInfo = WaferInfo.get(WaferInfo.class,
				waferInfoDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(waferInfo, waferInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeWaferInfo(Long id) {
		this.removeWaferInfos(new Long[] { id });
	}

	public void removeWaferInfos(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			WaferInfo waferInfo = WaferInfo.load(WaferInfo.class, ids[i]);
			waferInfo.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferInfoDTO> findAllWaferInfo() {
		List<WaferInfoDTO> list = new ArrayList<WaferInfoDTO>();
		List<WaferInfo> all = WaferInfo.findAll(WaferInfo.class);
		for (WaferInfo waferInfo : all) {
			WaferInfoDTO waferInfoDTO = new WaferInfoDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(waferInfoDTO, waferInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(waferInfoDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<WaferInfoDTO> pageQueryWaferInfo(WaferInfoDTO queryVo,
			int currentPage, int pageSize) {
		List<WaferInfoDTO> result = new ArrayList<WaferInfoDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _waferInfo from WaferInfo _waferInfo   left join _waferInfo.createUser  left join _waferInfo.lastModifyUser  left join _waferInfo.waferCompanyLot  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _waferInfo.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _waferInfo.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getWaferId() != null && !"".equals(queryVo.getWaferId())) {
			jpql.append(" and _waferInfo.waferId like ?");
			conditionVals.add(MessageFormat.format("%{0}%",
					queryVo.getWaferId()));
		}

		Page<WaferInfo> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (WaferInfo waferInfo : pages.getData()) {
			WaferInfoDTO waferInfoDTO = new WaferInfoDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(waferInfoDTO, waferInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(waferInfoDTO);
		}
		return new Page<WaferInfoDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByWaferInfo(Long id) {
		String jpql = "select e from WaferInfo o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByWaferInfo(Long id) {
		String jpql = "select e from WaferInfo o right join o.lastModifyUser e where o.id=?";
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
	public WaferCompanyLotDTO findWaferCompanyLotByWaferInfo(Long id) {
		String jpql = "select e from WaferInfo o right join o.waferCompanyLot e where o.id=?";
		WaferCompanyLot result = (WaferCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		WaferCompanyLotDTO dto = new WaferCompanyLotDTO();
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
	public List<WaferInfoDTO> findWaferInfoByWaferCompanyLotId(Long id) {
		List<WaferInfoDTO> result = new ArrayList<WaferInfoDTO>();
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class, id);
		Set<WaferInfo> waferInfos = waferProcess.getWaferCompanyLot()
				.getWaferInfos();
		Iterator<WaferInfo> iWaferInfos = waferInfos.iterator();
		while (iWaferInfos.hasNext()) {
			WaferInfo waferInfo = iWaferInfos.next();
			WaferInfoDTO waferInfoDTO = new WaferInfoDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferInfoDTO, waferInfo);
				waferInfoDTO.setWafer(waferInfo.getWaferId());
				waferInfoDTO.setWaferQty(waferInfo.getDieQty());
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(waferInfoDTO);
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<WaferInfoDTO> findWaferInfoByWaferIds(List<String> ids) {
		List<WaferInfoDTO> result = new ArrayList<WaferInfoDTO>();
		StringBuilder jpql = new StringBuilder(
				"select _waferInfo from WaferInfo _waferInfo where ");
		List<Object> conditionVals = new ArrayList<Object>();
		for(int i = 0 ; i < ids.size() ; i ++ ){
			String id = ids.get(i);
			if ( i == 0 ) {
				jpql.append(" _waferInfo.waferId = ? ");
				conditionVals.add(id);
			}else{
				jpql.append(" or _waferInfo.waferId = ? ");
				conditionVals.add(id);
			}
		}
		List<WaferInfo> list = (List<WaferInfo>)getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.list();
		for (WaferInfo waferInfo : list) {
			WaferInfoDTO waferInfoDTO = new WaferInfoDTO();

			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(waferInfoDTO, waferInfo);
				waferInfoDTO.setWafer(waferInfo.getWaferId());
				waferInfoDTO.setWaferQty(waferInfo.getDieQty());
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(waferInfoDTO);
		}
		return result;
	}

	public void updateWaferInfos(List<WaferInfoDTO> waferInfos) {
		for(WaferInfoDTO waferInfoDTO : waferInfos){
			WaferInfo waferInfo = WaferInfo.get(WaferInfo.class,
					waferInfoDTO.getId());
			// 设置要更新的值
			waferInfo.setWaferId(waferInfoDTO.getWaferId());
			waferInfo.setDieInitQty(waferInfoDTO.getDieQty());
			waferInfo.setDieQty(waferInfoDTO.getDieQty());
		}
	}
}
