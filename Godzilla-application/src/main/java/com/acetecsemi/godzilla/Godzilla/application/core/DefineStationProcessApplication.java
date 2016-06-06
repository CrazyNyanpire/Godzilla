package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface DefineStationProcessApplication {

	public DefineStationProcessDTO getDefineStationProcess(Long id);

	public DefineStationProcessDTO saveDefineStationProcess(
			DefineStationProcessDTO defineStationProcess);

	public void updateDefineStationProcess(
			DefineStationProcessDTO defineStationProcess);

	public void removeDefineStationProcess(Long id);

	public void removeDefineStationProcesss(Long[] ids);

	public List<DefineStationProcessDTO> findAllDefineStationProcess();

	public Page<DefineStationProcessDTO> pageQueryDefineStationProcess(
			DefineStationProcessDTO defineStationProcess, int currentPage,
			int pageSize);

	public UserDTO findCreateUserByDefineStationProcess(Long id);

	public UserDTO findLastModifyUserByDefineStationProcess(Long id);

	public WaferCustomerLotDTO findWaferCustomerLotByDefineStationProcess(
			Long id);

	public Page<StationDTO> findStationsByDefineStationProcess(Long id,
			int currentPage, int pageSize);
	
	public List<DefineStationProcessDTO> findDefineStationProcessByType(
			Integer id);

}
