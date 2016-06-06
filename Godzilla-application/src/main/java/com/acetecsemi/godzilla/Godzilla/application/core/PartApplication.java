
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface PartApplication {

	public PartDTO getPart(Long id);
	
	public PartDTO savePart(PartDTO part);
	
	public void updatePart(PartDTO part);
	
	public void removePart(Long id);
	
	public void removeParts(Long[] ids);
	
	public List<PartDTO> findAllPart();
	
	public Page<PartDTO> pageQueryPart(PartDTO part, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByPart(Long id);


		public UserDTO findLastModifyUserByPart(Long id);


	
}

