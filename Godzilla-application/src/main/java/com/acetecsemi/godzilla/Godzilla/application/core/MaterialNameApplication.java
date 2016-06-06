
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialNameApplication {

	public MaterialNameDTO getMaterialName(Long id);
	
	public MaterialNameDTO saveMaterialName(MaterialNameDTO materialName);
	
	public void updateMaterialName(MaterialNameDTO materialName);
	
	public void removeMaterialName(Long id);
	
	public void removeMaterialNames(Long[] ids);
	
	public List<MaterialNameDTO> findAllMaterialName();
	
	public Page<MaterialNameDTO> pageQueryMaterialName(MaterialNameDTO materialName, int currentPage, int pageSize);
	

}

