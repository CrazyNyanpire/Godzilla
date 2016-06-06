
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface PackApplication {

	public PackDTO getPack(Long id);
	
	public PackDTO savePack(PackDTO pack);
	
	public void updatePack(PackDTO pack);
	
	public void removePack(Long id);
	
	public void removePacks(Long[] ids);
	
	public List<PackDTO> findAllPack();
	
	public Page<PackDTO> pageQueryPack(PackDTO pack, int currentPage, int pageSize);
	
//	public UserDTO findCreateUserByPack(Long id);
//
//
//		public UserDTO findLastModifyUserByPack(Long id);


		public ProductDTO findProductByPack(Long id);


	
}

