
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ManufactureDebitWaferProcessApplication {

	public ManufactureDebitWaferProcessDTO getManufactureDebitWaferProcess(Long id);
	
	public ManufactureDebitWaferProcessDTO saveManufactureDebitWaferProcess(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcess);
	
	public void updateManufactureDebitWaferProcess(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcess);
	
	public void removeManufactureDebitWaferProcess(Long id);
	
	public void removeManufactureDebitWaferProcesss(Long[] ids);
	
	public List<ManufactureDebitWaferProcessDTO> findAllManufactureDebitWaferProcess();
	
	public Page<ManufactureDebitWaferProcessDTO> pageQueryManufactureDebitWaferProcess(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcess, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByManufactureDebitWaferProcess(Long id);


		public UserDTO findLastModifyUserByManufactureDebitWaferProcess(Long id);


		public ManufactureProcessDTO findManufactureProcessByManufactureDebitWaferProcess(Long id);


		public WaferProcessDTO findWaferProcessByManufactureDebitWaferProcess(Long id);


		public WaferInfoDTO findWaferInfoByManufactureDebitWaferProcess(Long id);


	
}

