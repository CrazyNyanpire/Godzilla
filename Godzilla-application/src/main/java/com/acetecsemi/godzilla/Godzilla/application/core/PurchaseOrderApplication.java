
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface PurchaseOrderApplication {

	public PurchaseOrderDTO getPurchaseOrder(Long id);
	
	public PurchaseOrderDTO savePurchaseOrder(PurchaseOrderDTO purchaseOrder);
	
	public void updatePurchaseOrder(PurchaseOrderDTO purchaseOrder);
	
	public void removePurchaseOrder(Long id);
	
	public void removePurchaseOrders(Long[] ids);
	
	public List<PurchaseOrderDTO> findAllPurchaseOrder();
	
	public Page<PurchaseOrderDTO> pageQueryPurchaseOrder(PurchaseOrderDTO purchaseOrder, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByPurchaseOrder(Long id);


		public UserDTO findLastModifyUserByPurchaseOrder(Long id);


		public CustomerDTO findCustomerByPurchaseOrder(Long id);


		public ProductDTO findProductByPurchaseOrder(Long id);


		public PartDTO findPartByPurchaseOrder(Long id);


	
}

