
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
import com.acetecsemi.godzilla.Godzilla.application.core.VenderApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class VenderApplicationImpl implements VenderApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VenderDTO getVender(Long id) {
		Vender vender = Vender.load(Vender.class, id);
		VenderDTO venderDTO = new VenderDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(venderDTO, vender);
		} catch (Exception e) {
			e.printStackTrace();
		}
		venderDTO.setId((java.lang.Long)vender.getId());
		return venderDTO;
	}
	
	public VenderDTO saveVender(VenderDTO venderDTO) {
		Vender vender = new Vender();
		try {
        	BeanUtils.copyProperties(vender, venderDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		vender.save();
		venderDTO.setId((java.lang.Long)vender.getId());
		return venderDTO;
	}
	
	public void updateVender(VenderDTO venderDTO) {
		Vender vender = Vender.get(Vender.class, venderDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(vender, venderDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeVender(Long id) {
		this.removeVenders(new Long[] { id });
	}
	
	public void removeVenders(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Vender vender = Vender.load(Vender.class, ids[i]);
			vender.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VenderDTO> findAllVender() {
		List<VenderDTO> list = new ArrayList<VenderDTO>();
		List<Vender> all = Vender.findAll(Vender.class);
		for (Vender vender : all) {
			VenderDTO venderDTO = new VenderDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(venderDTO, vender);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(venderDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<VenderDTO> pageQueryVender(VenderDTO queryVo, int currentPage, int pageSize) {
		List<VenderDTO> result = new ArrayList<VenderDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _vender from Vender _vender   left join _vender.createUser  left join _vender.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _vender.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _vender.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getVenderCode() != null && !"".equals(queryVo.getVenderCode())) {
	   		jpql.append(" and _vender.venderCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getVenderCode()));
	   	}		
	
	   	if (queryVo.getVenderName() != null && !"".equals(queryVo.getVenderName())) {
	   		jpql.append(" and _vender.venderName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getVenderName()));
	   	}		
        Page<Vender> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Vender vender : pages.getData()) {
            VenderDTO venderDTO = new VenderDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(venderDTO, vender);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(venderDTO);
        }
        return new Page<VenderDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByVender(Long id) {
		String jpql = "select e from Vender o right join o.createUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
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
	public UserDTO findLastModifyUserByVender(Long id) {
		String jpql = "select e from Vender o right join o.lastModifyUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
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
