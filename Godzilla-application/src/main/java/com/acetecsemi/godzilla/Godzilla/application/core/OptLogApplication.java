
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface OptLogApplication {

	public OptLogDTO getOptLog(Long id);
	
	public OptLogDTO saveOptLog(OptLogDTO optLog);
	
	public void updateOptLog(OptLogDTO optLog);
	
	public void removeOptLog(Long id);
	
	public void removeOptLogs(Long[] ids);
	
	public List<OptLogDTO> findAllOptLog();
	
	public Page<OptLogDTO> pageQueryOptLog(OptLogDTO optLog, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByOptLog(Long id);


		public UserDTO findLastModifyUserByOptLog(Long id);


	
}

