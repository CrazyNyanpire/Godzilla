
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferOptLogApplication {

	public WaferOptLogDTO getWaferOptLog(Long id);
	
	public WaferOptLogDTO saveWaferOptLog(WaferOptLogDTO waferOptLog);
	
	public void updateWaferOptLog(WaferOptLogDTO waferOptLog);
	
	public void removeWaferOptLog(Long id);
	
	public void removeWaferOptLogs(Long[] ids);
	
	public List<WaferOptLogDTO> findAllWaferOptLog();
	
	public Page<WaferOptLogDTO> pageQueryWaferOptLog(WaferOptLogDTO waferOptLog, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByWaferOptLog(Long id);


		public UserDTO findLastModifyUserByWaferOptLog(Long id);


		public OptLogDTO findOptLogByWaferOptLog(Long id);

	

		public WaferProcessDTO findWaferProcessByWaferOptLog(Long id);


	
}

