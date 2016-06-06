
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SubstratePartApplication {

	public SubstratePartDTO getSubstratePart(Long id);
	
	public SubstratePartDTO saveSubstratePart(SubstratePartDTO substratePart);
	
	public void updateSubstratePart(SubstratePartDTO substratePart);
	
	public void removeSubstratePart(Long id);
	
	public void removeSubstrateParts(Long[] ids);
	
	public List<SubstratePartDTO> findAllSubstratePart();
	
	public Page<SubstratePartDTO> pageQuerySubstratePart(SubstratePartDTO substratePart, int currentPage, int pageSize);
	
	public UserDTO findCreateUserBySubstratePart(Long id);


		public UserDTO findLastModifyUserBySubstratePart(Long id);


	
}

