
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface CustomerApplication {

	public CustomerDTO getCustomer(Long id);
	
	public CustomerDTO saveCustomer(CustomerDTO customer);
	
	public void updateCustomer(CustomerDTO customer);
	
	public void removeCustomer(Long id);
	
	public void removeCustomers(Long[] ids);
	
	public List<CustomerDTO> findAllCustomer();
	
	public Page<CustomerDTO> pageQueryCustomer(CustomerDTO customer, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByCustomer(Long id);


		public UserDTO findLastModifyUserByCustomer(Long id);


	
}

