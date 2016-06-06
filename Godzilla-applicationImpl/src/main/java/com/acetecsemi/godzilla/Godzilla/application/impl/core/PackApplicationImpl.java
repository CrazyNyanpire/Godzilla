
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
import com.acetecsemi.godzilla.Godzilla.application.core.PackApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class PackApplicationImpl implements PackApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PackDTO getPack(Long id) {
		Pack pack = Pack.load(Pack.class, id);
		PackDTO packDTO = new PackDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(packDTO, pack);
		} catch (Exception e) {
			e.printStackTrace();
		}
		packDTO.setProductId(pack.getProduct().getId());
		packDTO.setProductName(pack.getProduct().getProductName());
		packDTO.setId((java.lang.Long)pack.getId());
		return packDTO;
	}
	
	public PackDTO savePack(PackDTO packDTO) {
		Pack pack = new Pack();
		pack.setProduct(Product.load(Product.class, packDTO.getProductId()));
		User user;
		if (packDTO.getUser() == null) {
			user = User.findByUserAccount("1");
		} else
			user = User.findByUserAccount(packDTO.getUser()
					.getUserAccount());
		pack.setCreateUser(user);
		try {
        	BeanUtils.copyProperties(pack, packDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		pack.save();
		packDTO.setId((java.lang.Long)pack.getId());
		return packDTO;
	}
	
	public void updatePack(PackDTO packDTO) {
		Pack pack = Pack.get(Pack.class, packDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(pack, packDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removePack(Long id) {
		this.removePacks(new Long[] { id });
	}
	
	public void removePacks(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Pack pack = Pack.load(Pack.class, ids[i]);
			pack.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PackDTO> findAllPack() {
		List<PackDTO> list = new ArrayList<PackDTO>();
		List<Pack> all = Pack.findAll(Pack.class);
		for (Pack pack : all) {
			PackDTO packDTO = new PackDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(packDTO, pack);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(packDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PackDTO> findPackByProduct(Long productId) {
		List<PackDTO> list = new ArrayList<PackDTO>();
		List<Pack> all = Pack.findByProperty(Pack.class, "product.id", productId);
		for (Pack pack : all) {
			PackDTO packDTO = new PackDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(packDTO, pack);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(packDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<PackDTO> pageQueryPack(PackDTO queryVo, int currentPage, int pageSize) {
		List<PackDTO> result = new ArrayList<PackDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _pack from Pack _pack   left join _pack.createUser  left join _pack.lastModifyUser  left join _pack.product  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _pack.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _pack.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	
	   	if (queryVo.getPackSize() != null && !"".equals(queryVo.getPackSize())) {
	   		jpql.append(" and _pack.packSize like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getPackSize()));
	   	}		
	   	if(queryVo.getProductName() != null && !"".equals(queryVo.getProductName()))
	   	{
	   		jpql.append(" and _pack.product.productName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProductName()));
	   	}
        Page<Pack> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Pack pack : pages.getData()) {
            PackDTO packDTO = new PackDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(packDTO, pack);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
           result.add(packDTO);
        }
        return new Page<PackDTO>(pages.getStart(), pages.getResultCount(), pageSize, result);
	}
	
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public UserDTO findCreateUserByPack(Long id) {
//		String jpql = "select e from Pack o right join o.createUser e where o.id=?";
//		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
//		UserDTO  dto = new UserDTO();
//		if (result != null) {
//			try {
//				BeanUtils.copyProperties(dto, result);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return dto;
//	}
//
//	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
//	public UserDTO findLastModifyUserByPack(Long id) {
//		String jpql = "select e from Pack o right join o.lastModifyUser e where o.id=?";
//		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
//		UserDTO  dto = new UserDTO();
//		if (result != null) {
//			try {
//				BeanUtils.copyProperties(dto, result);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return dto;
//	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductDTO findProductByPack(Long id) {
		String jpql = "select e from Pack o right join o.product e where o.id=?";
		Product result = (Product) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		ProductDTO  dto = new ProductDTO();
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
