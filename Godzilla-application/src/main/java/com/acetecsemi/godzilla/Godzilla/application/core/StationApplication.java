package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface StationApplication {

	public StationDTO getStation(Long id);

	public StationDTO saveStation(StationDTO station);

	public void updateStation(StationDTO station);

	public void removeStation(Long id);

	public void removeStations(Long[] ids);

	public List<StationDTO> findAllStation();

	public Page<StationDTO> pageQueryStation(StationDTO station,
			int currentPage, int pageSize);

	public UserDTO findCreateUserByStation(Long id);

	public UserDTO findLastModifyUserByStation(Long id);
	
	public List<EquipmentDTO> findEquipmentsByStation(Long id);
	
	List<StationAlertDTO> findAllStationAlert();
	public List<StationDTO> findConsumeStation();
}
