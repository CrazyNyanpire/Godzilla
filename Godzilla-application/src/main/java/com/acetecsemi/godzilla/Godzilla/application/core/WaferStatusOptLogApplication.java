package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferStatusOptLogApplication {

	public WaferStatusOptLogDTO getWaferStatusOptLog(Long id);

	public WaferStatusOptLogDTO saveWaferStatusOptLog(
			WaferStatusOptLogDTO waferStatusOptLog);

	public void updateWaferStatusOptLog(WaferStatusOptLogDTO waferStatusOptLog);

	public void removeWaferStatusOptLog(Long id);

	public void removeWaferStatusOptLogs(Long[] ids);

	public List<WaferStatusOptLogDTO> findAllWaferStatusOptLog();

	public Page<WaferStatusOptLogDTO> pageQueryWaferStatusOptLog(
			WaferStatusOptLogDTO waferStatusOptLog, int currentPage,
			int pageSize);

	public UserDTO findCreateUserByWaferStatusOptLog(Long id);

	public UserDTO findLastModifyUserByWaferStatusOptLog(Long id);

	public OptLogDTO findOptLogByWaferStatusOptLog(Long id);

	public WaferStatusOptLogDTO findWaferStatusOptLogByWaferProcess(Long id);

}
