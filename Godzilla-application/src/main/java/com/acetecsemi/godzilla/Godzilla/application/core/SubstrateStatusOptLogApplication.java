
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SubstrateStatusOptLogApplication {

	public SubstrateStatusOptLogDTO getSubstrateStatusOptLog(Long id);
	
	public SubstrateStatusOptLogDTO saveSubstrateStatusOptLog(SubstrateStatusOptLogDTO substrateStatusOptLog);
	
	public void updateSubstrateStatusOptLog(SubstrateStatusOptLogDTO substrateStatusOptLog);
	
	public void removeSubstrateStatusOptLog(Long id);
	
	public void removeSubstrateStatusOptLogs(Long[] ids);
	
	public List<SubstrateStatusOptLogDTO> findAllSubstrateStatusOptLog();
	
	public Page<SubstrateStatusOptLogDTO> pageQuerySubstrateStatusOptLog(SubstrateStatusOptLogDTO substrateStatusOptLog, int currentPage, int pageSize);
	
	public UserDTO findCreateUserBySubstrateStatusOptLog(Long id);


		public UserDTO findLastModifyUserBySubstrateStatusOptLog(Long id);


		public OptLogDTO findOptLogBySubstrateStatusOptLog(Long id);

	

		public SubstrateProcessDTO findSubstrateStatusOptLogBySubstrateProcess(Long id);


	
}

