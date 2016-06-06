
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ManufactureLocationApplication {

	public ManufactureLocationDTO getManufactureLocation(Long id);
	
	public ManufactureLocationDTO saveManufactureLocation(ManufactureLocationDTO manufactureLocation);
	
	public void updateManufactureLocation(ManufactureLocationDTO manufactureLocation);
	
	public void removeManufactureLocation(Long id);
	
	public void removeManufactureLocations(Long[] ids);
	
	public List<ManufactureLocationDTO> findAllManufactureLocation();
	
	public Page<ManufactureLocationDTO> pageQueryManufactureLocation(ManufactureLocationDTO manufactureLocation, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByManufactureLocation(Long id);


		public UserDTO findLastModifyUserByManufactureLocation(Long id);


		public ManufactureLotDTO findManufactureLotByManufactureLocation(Long id);


	
}

