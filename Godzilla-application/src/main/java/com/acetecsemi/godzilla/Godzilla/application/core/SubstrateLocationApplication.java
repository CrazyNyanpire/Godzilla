
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SubstrateLocationApplication {

	public SubstrateLocationDTO getSubstrateLocation(Long id);
	
	public SubstrateLocationDTO saveSubstrateLocation(SubstrateLocationDTO substrateLocation);
	
	public void updateSubstrateLocation(SubstrateLocationDTO substrateLocation);
	
	public void removeSubstrateLocation(Long id);
	
	public void removeSubstrateLocations(Long[] ids);
	
	public List<SubstrateLocationDTO> findAllSubstrateLocation();
	
	public Page<SubstrateLocationDTO> pageQuerySubstrateLocation(SubstrateLocationDTO substrateLocation, int currentPage, int pageSize);
	
	public UserDTO findCreateUserBySubstrateLocation(Long id);


		public UserDTO findLastModifyUserBySubstrateLocation(Long id);


		public SubstrateCompanyLotDTO findSubstrateCompanyLotBySubstrateLocation(Long id);


	
}

