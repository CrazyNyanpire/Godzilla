
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.core.ProductApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class ProductApplicationImpl implements ProductApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductDTO getProduct(Long id) {
		Product product = Product.load(Product.class, id);
		ProductDTO productDTO = new ProductDTO();
		// 将domain转成VO
		try {
			BeanUtilsExtends.copyProperties(productDTO, product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		productDTO.setId((java.lang.Long)product.getId());
		return productDTO;
	}
	
	public ProductDTO saveProduct(ProductDTO productDTO) {
		Product product = new Product();
		try {
        	BeanUtilsExtends.copyProperties(product, productDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		product.setCreateDate(new Date());
		product.setLastModifyDate(new Date());
		product.save();
		productDTO.setId((java.lang.Long)product.getId());
		return productDTO;
	}
	
	public void updateProduct(ProductDTO productDTO) {
		Product product = Product.get(Product.class, productDTO.getId());
		// 设置要更新的值
		try {
			product.setProductName(productDTO.getProductName());
			product.setLastModifyDate(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeProduct(Long id) {
		this.removeProducts(new Long[] { id });
	}
	
	public void removeProducts(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Product product = Product.load(Product.class, ids[i]);
			product.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ProductDTO> findAllProduct() {
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		List<Product> all = Product.findAll(Product.class);
		for (Product product : all) {
			ProductDTO productDTO = new ProductDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(productDTO, product);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(productDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<ProductDTO> pageQueryProduct(ProductDTO queryVo, int currentPage, int pageSize) {
		List<ProductDTO> result = new ArrayList<ProductDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _product from Product _product   left join _product.createUser  left join _product.lastModifyUser  left join _product.customer  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _product.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _product.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getProductName() != null && !"".equals(queryVo.getProductName())) {
	   		jpql.append(" and _product.productName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getProductName()));
	   	}			
	
        Page<Product> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Product product : pages.getData()) {
            ProductDTO productDTO = new ProductDTO();
            
             // 将domain转成VO
            try {
            	BeanUtilsExtends.copyProperties(productDTO, product);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(productDTO);
        }
        return new Page<ProductDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByProduct(Long id) {
		String jpql = "select e from Product o right join o.createUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findLastModifyUserByProduct(Long id) {
		String jpql = "select e from Product o right join o.lastModifyUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CustomerDTO findCustomerByProduct(Long id) {
		String jpql = "select e from Product o right join o.customer e where o.id=?";
		Customer result = (Customer) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		CustomerDTO  dto = new CustomerDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ProductDTO> findProductByCustomerId(Long id) {
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		String jpql = "select o from Product o right join o.customer e where e.id=?";
		List<Product> result = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).list();
		for (Product product : result) {
			ProductDTO productDTO = new ProductDTO();
			// 将domain转成VO
			try {
				BeanUtilsExtends.copyProperties(productDTO, product);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(productDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<PackDTO> getPackByProductId(Long id) {
		List<PackDTO> list = new ArrayList<PackDTO>();
		String jpql = "select o from Pack o where o.product.id=?";
		List<Pack> all = (List<Pack>) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).list();
		for (Pack pack : all) {
			PackDTO packDTO = new PackDTO();
			try {
				BeanUtils.copyProperties(packDTO, pack);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			packDTO.setMaterialName(.getMaterialName().getMaterialName());
//			packDTO.setMaterialType(pack.getMaterialName().getMaterialType());
			list.add(packDTO);
		}
		return list;
	}
	
}
