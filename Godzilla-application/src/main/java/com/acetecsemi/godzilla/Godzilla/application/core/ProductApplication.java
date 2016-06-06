package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ProductApplication {

	public ProductDTO getProduct(Long id);

	public ProductDTO saveProduct(ProductDTO product);

	public void updateProduct(ProductDTO product);

	public void removeProduct(Long id);

	public void removeProducts(Long[] ids);

	public List<ProductDTO> findAllProduct();

	public Page<ProductDTO> pageQueryProduct(ProductDTO product,
			int currentPage, int pageSize);

	public UserDTO findCreateUserByProduct(Long id);

	public UserDTO findLastModifyUserByProduct(Long id);

	public CustomerDTO findCustomerByProduct(Long id);

	public List<ProductDTO> findProductByCustomerId(Long id);
	List<PackDTO> getPackByProductId(Long id);

}
