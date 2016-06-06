
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ManufactureStatusOptLogApplication {

	public ManufactureStatusOptLogDTO getManufactureStatusOptLog(Long id);
	
	public ManufactureStatusOptLogDTO saveManufactureStatusOptLog(ManufactureStatusOptLogDTO manufactureStatusOptLog);
	
	public void updateManufactureStatusOptLog(ManufactureStatusOptLogDTO manufactureStatusOptLog);
	
	public void removeManufactureStatusOptLog(Long id);
	
	public void removeManufactureStatusOptLogs(Long[] ids);
	
	public List<ManufactureStatusOptLogDTO> findAllManufactureStatusOptLog();
	
	public Page<ManufactureStatusOptLogDTO> pageQueryManufactureStatusOptLog(ManufactureStatusOptLogDTO manufactureStatusOptLog, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByManufactureStatusOptLog(Long id);


		public UserDTO findLastModifyUserByManufactureStatusOptLog(Long id);


		public OptLogDTO findOptLogByManufactureStatusOptLog(Long id);

	

		public ManufactureProcessDTO findManufactureProcessByManufactureStatusOptLog(Long id);


	
}

