
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
import com.acetecsemi.godzilla.Godzilla.application.core.CustomerApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class CustomerApplicationImpl implements CustomerApplication {


	private QueryChannelService queryChannel;

    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CustomerDTO getCustomer(Long id) {
		Customer customer = Customer.load(Customer.class, id);
		CustomerDTO customerDTO = new CustomerDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(customerDTO, customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		customerDTO.setId((java.lang.Long)customer.getId());
		return customerDTO;
	}
	
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		try {
        	BeanUtils.copyProperties(customer, customerDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		customer.save();
		customerDTO.setId((java.lang.Long)customer.getId());
		return customerDTO;
	}
	
	public void updateCustomer(CustomerDTO customerDTO) {
		Customer customer = Customer.get(Customer.class, customerDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(customer, customerDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeCustomer(Long id) {
		this.removeCustomers(new Long[] { id });
	}
	
	public void removeCustomers(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			Customer customer = Customer.load(Customer.class, ids[i]);
			customer.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CustomerDTO> findAllCustomer() {
		List<CustomerDTO> list = new ArrayList<CustomerDTO>();
		List<Customer> all = Customer.findAll(Customer.class);
		for (Customer customer : all) {
			CustomerDTO customerDTO = new CustomerDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(customerDTO, customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(customerDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CustomerDTO> pageQueryCustomer(CustomerDTO queryVo, int currentPage, int pageSize) {
		List<CustomerDTO> result = new ArrayList<CustomerDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _customer from Customer _customer   left join _customer.createUser  left join _customer.lastModifyUser  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _customer.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _customer.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getCustomerCode() != null && !"".equals(queryVo.getCustomerCode())) {
	   		jpql.append(" and _customer.customerCode like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerCode()));
	   	}		
	
	   	if (queryVo.getCustomerName() != null && !"".equals(queryVo.getCustomerName())) {
	   		jpql.append(" and _customer.customerName like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getCustomerName()));
	   	}		
        Page<Customer> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (Customer customer : pages.getData()) {
            CustomerDTO customerDTO = new CustomerDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(customerDTO, customer);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                                              result.add(customerDTO);
        }
        return new Page<CustomerDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByCustomer(Long id) {
		String jpql = "select e from Customer o right join o.createUser e where o.id=?";
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
	public UserDTO findLastModifyUserByCustomer(Long id) {
		String jpql = "select e from Customer o right join o.lastModifyUser e where o.id=?";
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
