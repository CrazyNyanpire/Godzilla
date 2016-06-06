
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialLocationApplication {

	public MaterialLocationDTO getMaterialLocation(Long id);
	
	public MaterialLocationDTO saveMaterialLocation(MaterialLocationDTO materialLocation);
	
	public void updateMaterialLocation(MaterialLocationDTO materialLocation);
	
	public void removeMaterialLocation(Long id);
	
	public void removeMaterialLocations(Long[] ids);
	
	public List<MaterialLocationDTO> findAllMaterialLocation();
	
	public Page<MaterialLocationDTO> pageQueryMaterialLocation(MaterialLocationDTO materialLocation, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByMaterialLocation(Long id);


		public UserDTO findLastModifyUserByMaterialLocation(Long id);


		public MaterialCompanyLotDTO findMaterialCompanyLotByMaterialLocation(Long id);


	
}

