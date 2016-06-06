
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ManufactureDebitProcessApplication {

	public ManufactureDebitProcessDTO getManufactureDebitProcess(Long id);
	
	public ManufactureDebitProcessDTO saveManufactureDebitProcess(ManufactureDebitProcessDTO manufactureDebitProcess);
	
	public void updateManufactureDebitProcess(ManufactureDebitProcessDTO manufactureDebitProcess);
	
	public void removeManufactureDebitProcess(Long id);
	
	public void removeManufactureDebitProcesss(Long[] ids);
	
	public List<ManufactureDebitProcessDTO> findAllManufactureDebitProcess();
	
	public Page<ManufactureDebitProcessDTO> pageQueryManufactureDebitProcess(ManufactureDebitProcessDTO manufactureDebitProcess, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByManufactureDebitProcess(Long id);


		public UserDTO findLastModifyUserByManufactureDebitProcess(Long id);


		public ManufactureProcessDTO findManufactureProcessByManufactureDebitProcess(Long id);


		public MaterialProcessDTO findMaterialProcessByManufactureDebitProcess(Long id);


	
}

