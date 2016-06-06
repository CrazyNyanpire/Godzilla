
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface RejectCodeItemApplication {

	public RejectCodeItemDTO getRejectCodeItem(Long id);
	
	public RejectCodeItemDTO saveRejectCodeItem(RejectCodeItemDTO rejectCodeItem);
	
	public void updateRejectCodeItem(RejectCodeItemDTO rejectCodeItem);
	
	public void removeRejectCodeItem(Long id);
	
	public void removeRejectCodeItems(Long[] ids);
	
	public List<RejectCodeItemDTO> findAllRejectCodeItem();
	
	public Page<RejectCodeItemDTO> pageQueryRejectCodeItem(RejectCodeItemDTO rejectCodeItem, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByRejectCodeItem(Long id);


		public UserDTO findLastModifyUserByRejectCodeItem(Long id);


		public StationDTO findStationByRejectCodeItem(Long id);


		public ManufactureProcessDTO findManufactureProcessByRejectCodeItem(Long id);


		public WaferProcessDTO findWaferProcessByRejectCodeItem(Long id);


	
}

