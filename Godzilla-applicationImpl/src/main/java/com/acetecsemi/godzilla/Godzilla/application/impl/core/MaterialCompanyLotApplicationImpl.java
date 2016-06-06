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
import com.acetecsemi.godzilla.Godzilla.application.utils.SerialNumberUtils;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;
import com.acetecsemi.godzilla.Godzilla.core.Process;

@Named
@Transactional
public class MaterialCompanyLotApplicationImpl implements
		MaterialCompanyLotApplication {

	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialCompanyLotDTO getMaterialCompanyLot(Long id) {
		MaterialCompanyLot materialCompanyLot = MaterialCompanyLot.load(
				MaterialCompanyLot.class, id);
		MaterialCompanyLotDTO materialCompanyLotDTO = new MaterialCompanyLotDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(materialCompanyLotDTO, materialCompanyLot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialCompanyLotDTO
				.setId((java.lang.Long) materialCompanyLot.getId());
		return materialCompanyLotDTO;
	}

	public MaterialCompanyLotDTO saveMaterialCompanyLot(
			MaterialCompanyLotDTO materialCompanyLotDTO) {
		MaterialCompanyLot materialCompanyLot = new MaterialCompanyLot();
		try {
			BeanUtils.copyProperties(materialCompanyLot, materialCompanyLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		materialCompanyLot.setMaterialLot(MaterialLot.load(MaterialLot.class, materialCompanyLotDTO.getMaterialLotId()));
		materialCompanyLot.save();
		materialCompanyLotDTO
				.setId((java.lang.Long) materialCompanyLot.getId());
		return materialCompanyLotDTO;
	}

	public void updateMaterialCompanyLot(
			MaterialCompanyLotDTO materialCompanyLotDTO) {
		MaterialCompanyLot materialCompanyLot = MaterialCompanyLot.get(
				MaterialCompanyLot.class, materialCompanyLotDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(materialCompanyLot, materialCompanyLotDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeMaterialCompanyLot(Long id) {
		this.removeMaterialCompanyLots(new Long[] { id });
	}

	public void removeMaterialCompanyLots(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MaterialCompanyLot materialCompanyLot = MaterialCompanyLot.load(
					MaterialCompanyLot.class, ids[i]);
			materialCompanyLot.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MaterialCompanyLotDTO> findAllMaterialCompanyLot() {
		List<MaterialCompanyLotDTO> list = new ArrayList<MaterialCompanyLotDTO>();
		List<MaterialCompanyLot> all = MaterialCompanyLot
				.findAll(MaterialCompanyLot.class);
		for (MaterialCompanyLot materialCompanyLot : all) {
			MaterialCompanyLotDTO materialCompanyLotDTO = new MaterialCompanyLotDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialCompanyLotDTO,
						materialCompanyLot);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(materialCompanyLotDTO);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MaterialCompanyLotDTO> pageQueryMaterialCompanyLot(
			MaterialCompanyLotDTO queryVo, int currentPage, int pageSize) {
		List<MaterialCompanyLotDTO> result = new ArrayList<MaterialCompanyLotDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
		StringBuilder jpql = new StringBuilder(
				"select _materialCompanyLot from MaterialCompanyLot _materialCompanyLot   left join _materialCompanyLot.createUser  left join _materialCompanyLot.lastModifyUser  left join _materialCompanyLot.margeMaterialCompanyLot  left join _materialCompanyLot.parentMaterialCompanyLot  left join _materialCompanyLot.nowProcess  where 1=1 ");

		if (queryVo.getCreateDate() != null) {
			jpql.append(" and _materialCompanyLot.createDate between ? and ? ");
			conditionVals.add(queryVo.getCreateDate());
			conditionVals.add(queryVo.getCreateDateEnd());
		}

		if (queryVo.getLastModifyDate() != null) {
			jpql.append(" and _materialCompanyLot.lastModifyDate between ? and ? ");
			conditionVals.add(queryVo.getLastModifyDate());
			conditionVals.add(queryVo.getLastModifyDateEnd());
		}

		if (queryVo.getLotNo() != null && !"".equals(queryVo.getLotNo())) {
			jpql.append(" and _materialCompanyLot.lotNo like ?");
			conditionVals
					.add(MessageFormat.format("%{0}%", queryVo.getLotNo()));
		}

		Page<MaterialCompanyLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.setPage(currentPage, pageSize).pagedList();
		for (MaterialCompanyLot materialCompanyLot : pages.getData()) {
			MaterialCompanyLotDTO materialCompanyLotDTO = new MaterialCompanyLotDTO();

			// 将domain转成VO
			try {
				BeanUtils.copyProperties(materialCompanyLotDTO,
						materialCompanyLot);
			} catch (Exception e) {
				e.printStackTrace();
			}

			result.add(materialCompanyLotDTO);
		}
		return new Page<MaterialCompanyLotDTO>(pages.getPageIndex(),
				pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMaterialCompanyLot(Long id) {
		String jpql = "select e from MaterialCompanyLot o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByMaterialCompanyLot(Long id) {
		String jpql = "select e from MaterialCompanyLot o right join o.lastModifyUser e where o.id=?";
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
	public MaterialCompanyLotDTO findMargeMaterialCompanyLotByMaterialCompanyLot(
			Long id) {
		String jpql = "select e from MaterialCompanyLot o right join o.margeMaterialCompanyLot e where o.id=?";
		MaterialCompanyLot result = (MaterialCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		MaterialCompanyLotDTO dto = new MaterialCompanyLotDTO();
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
	public MaterialCompanyLotDTO findParentMaterialCompanyLotByMaterialCompanyLot(
			Long id) {
		String jpql = "select e from MaterialCompanyLot o right join o.parentMaterialCompanyLot e where o.id=?";
		MaterialCompanyLot result = (MaterialCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		MaterialCompanyLotDTO dto = new MaterialCompanyLotDTO();
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
	public Page<MaterialCompanyLotDTO> findMaterialCompanyLotsByMaterialCompanyLot(
			Long id, int currentPage, int pageSize) {
		List<MaterialCompanyLotDTO> result = new ArrayList<MaterialCompanyLotDTO>();
		String jpql = "select e from MaterialCompanyLot o right join o.materialCompanyLots e where o.id=?";
		Page<MaterialCompanyLot> pages = getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.setPage(currentPage, pageSize).pagedList();
		for (MaterialCompanyLot entity : pages.getData()) {
			MaterialCompanyLotDTO dto = new MaterialCompanyLotDTO();
			try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result.add(dto);
		}
		return new Page<MaterialCompanyLotDTO>(Page.getStartOfPage(currentPage,
				pageSize), pages.getResultCount(), pageSize, result);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MaterialCompanyLotDTO findMaterialCompanyLotByCustomerId(Long id) {
		String jpql = "select o from MaterialCompanyLot o left join o.materialLot e where e.id=?";
		MaterialCompanyLot result = (MaterialCompanyLot) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		MaterialCompanyLotDTO dto = new MaterialCompanyLotDTO();
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
	public String getNewLotNo() {
		String lotNo = "M";
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
		StringBuilder jpql = new StringBuilder("select max(substring(_materialCompanyLot.lotNo,1,LENGTH(_materialCompanyLot.lotNo)-3)) from MaterialCompanyLot _materialCompanyLot  where 1=1 ");List<Object> conditionVals = new ArrayList<Object>();
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
