package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface LocationApplication {

	public LocationDTO getLocation(Long id);

	public LocationDTO saveLocation(LocationDTO location);

	public void updateLocation(LocationDTO location);

	public void removeLocation(Long id);

	public void removeLocations(Long[] ids);

	public List<LocationDTO> findAllLocation();

	public Page<LocationDTO> pageQueryLocation(LocationDTO location,
			int currentPage, int pageSize);

	public UserDTO findCreateUserByLocation(Long id);

	public UserDTO findLastModifyUserByLocation(Long id);

	public WaferProcessDTO findNowProcessByLocation(Long id);

	public void updateLocationsWaferProcess(String locationIds, Long processId);
	
	public void updateLocationsSubstrateProcess(String locationIds,
			Long processId) ;

	public void updateLocationsMaterialProcess(String locationIds,
			Long processId) ;

	public void updateLocationsManufactureProcess(String locationIds,
			Long processId) ;
	
	public void clearLocationsProcess(String locationIds);

}
