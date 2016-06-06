
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialStatusOptLogApplication {

	public MaterialStatusOptLogDTO getMaterialStatusOptLog(Long id);
	
	public MaterialStatusOptLogDTO saveMaterialStatusOptLog(MaterialStatusOptLogDTO materialStatusOptLog);
	
	public void updateMaterialStatusOptLog(MaterialStatusOptLogDTO materialStatusOptLog);
	
	public void removeMaterialStatusOptLog(Long id);
	
	public void removeMaterialStatusOptLogs(Long[] ids);
	
	public List<MaterialStatusOptLogDTO> findAllMaterialStatusOptLog();
	
	public Page<MaterialStatusOptLogDTO> pageQueryMaterialStatusOptLog(MaterialStatusOptLogDTO materialStatusOptLog, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByMaterialStatusOptLog(Long id);


		public UserDTO findLastModifyUserByMaterialStatusOptLog(Long id);


		public OptLogDTO findOptLogByMaterialStatusOptLog(Long id);

	

		public MaterialProcessDTO findMaterialProcessByMaterialStatusOptLog(Long id);


	
}

