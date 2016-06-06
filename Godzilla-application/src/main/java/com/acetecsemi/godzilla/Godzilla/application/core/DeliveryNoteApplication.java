
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface DeliveryNoteApplication {

	public DeliveryNoteDTO getDeliveryNote(Long id);
	
	public DeliveryNoteDTO saveDeliveryNote(DeliveryNoteDTO deliveryNote);
	
	public void updateDeliveryNote(DeliveryNoteDTO deliveryNote);
	
	public void removeDeliveryNote(Long id);
	
	public void removeDeliveryNotes(Long[] ids);
	
	public List<DeliveryNoteDTO> findAllDeliveryNote();
	
	public Page<DeliveryNoteDTO> pageQueryDeliveryNote(DeliveryNoteDTO deliveryNote, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByDeliveryNote(Long id);


		public UserDTO findLastModifyUserByDeliveryNote(Long id);


		public CustomerDTO findCustomerByDeliveryNote(Long id);


		public ProductDTO findProductByDeliveryNote(Long id);


		public PartDTO findPartByDeliveryNote(Long id);


	
}

