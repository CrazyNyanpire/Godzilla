
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface RightLogApplication {

	public RightLogDTO getRightLog(Long id);
	
	public RightLogDTO saveRightLog(RightLogDTO rightLog);
	
	public void updateRightLog(RightLogDTO rightLog);
	
	public void removeRightLog(Long id);
	
	public void removeRightLogs(Long[] ids);
	
	public List<RightLogDTO> findAllRightLog();
	
	public Page<RightLogDTO> pageQueryRightLog(RightLogDTO rightLog, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByRightLog(Long id);


		public UserDTO findLastModifyUserByRightLog(Long id);


		public ResourceDTO findResourceByRightLog(Long id);


	
}

