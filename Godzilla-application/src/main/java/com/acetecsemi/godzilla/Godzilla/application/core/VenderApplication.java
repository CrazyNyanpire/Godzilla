
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface VenderApplication {

	public VenderDTO getVender(Long id);
	
	public VenderDTO saveVender(VenderDTO vender);
	
	public void updateVender(VenderDTO vender);
	
	public void removeVender(Long id);
	
	public void removeVenders(Long[] ids);
	
	public List<VenderDTO> findAllVender();
	
	public Page<VenderDTO> pageQueryVender(VenderDTO vender, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByVender(Long id);


		public UserDTO findLastModifyUserByVender(Long id);


	
}

