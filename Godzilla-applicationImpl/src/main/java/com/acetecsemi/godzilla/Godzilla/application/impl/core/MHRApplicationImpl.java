
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
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.utils.SerialNumberUtils;
import com.acetecsemi.godzilla.Godzilla.application.core.MHRApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class MHRApplicationImpl implements MHRApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MHRDTO getMHR(Long id) {
		MHR mHR = MHR.load(MHR.class, id);
		MHRDTO mHRDTO = new MHRDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(mHRDTO, mHR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mHRDTO.setId((java.lang.Long)mHR.getId());
		return mHRDTO;
	}
	
	public MHRDTO saveMHR(MHRDTO mHRDTO) {
		MHR mHR = new MHR();
		try {
        	BeanUtils.copyProperties(mHR, mHRDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		mHR.save();
		mHRDTO.setId((java.lang.Long)mHR.getId());
		return mHRDTO;
	}
	
	public void updateMHR(MHRDTO mHRDTO) {
		MHR mHR = MHR.get(MHR.class, mHRDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(mHR, mHRDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeMHR(Long id) {
		this.removeMHRs(new Long[] { id });
	}
	
	public void removeMHRs(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			MHR mHR = MHR.load(MHR.class, ids[i]);
			mHR.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MHRDTO> findAllMHR() {
		List<MHRDTO> list = new ArrayList<MHRDTO>();
		List<MHR> all = MHR.findAll(MHR.class);
		for (MHR mHR : all) {
			MHRDTO mHRDTO = new MHRDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(mHRDTO, mHR);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(mHRDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MHRDTO> pageQueryMHR(MHRDTO queryVo, int currentPage, int pageSize) {
		List<MHRDTO> result = new ArrayList<MHRDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _mHR from MHR _mHR   left join _mHR.createUser  left join _mHR.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _mHR.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _mHR.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getNo() != null && !"".equals(queryVo.getNo())) {
	   		jpql.append(" and _mHR.no like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getNo()));
	   	}		
        Page<MHR> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (MHR mHR : pages.getData()) {
            MHRDTO mHRDTO = new MHRDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(mHRDTO, mHR);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(mHRDTO);
        }
        return new Page<MHRDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByMHR(Long id) {
		String jpql = "select e from MHR o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByMHR(Long id) {
		String jpql = "select e from MHR o right join o.lastModifyUser e where o.id=?";
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
	
	
	public String getNewMHRNo() {
		String MHRNo = "MHR-";
		String lastNHRNo = this.findMaxMHRNo();
		// MHR: 编码为MHR-YYMMEE
		MHRNo += MyDateUtils.getYYMM();
		if (lastNHRNo == null) {
			MHRNo += "01";
		} else {
			String number = lastNHRNo.split("-")[1];
			MHRNo += SerialNumberUtils.sn(2,
					Integer.parseInt(number.substring(4)), "");
		}
		MHR mhr = new MHR();
		mhr.setNo(MHRNo);
		mhr.save();
		return MHRNo;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private String findMaxMHRNo() {
		StringBuilder jpql = new StringBuilder(
				"select max(_mhr.no) from MHR _mhr  where 1=1 ");
		List<Object> conditionVals = new ArrayList<Object>();
		Object lastMHRNo = getQueryChannelService()
				.createJpqlQuery(jpql.toString()).setParameters(conditionVals)
				.singleResult();
		if (lastMHRNo == null) {
			return null;
		} else {
			return lastMHRNo.toString();
		}
	}
}
