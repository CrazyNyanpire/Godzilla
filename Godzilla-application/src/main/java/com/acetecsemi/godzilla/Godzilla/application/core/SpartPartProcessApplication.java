package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SpartPartProcessApplication {

	public SpartPartProcessDTO getSpartPartProcess(Long id);

	public SpartPartProcessDTO saveSpartPartProcess(
			SpartPartProcessDTO spartPartProcess);

	public void updateSpartPartProcess(SpartPartProcessDTO spartPartProcess);

	public void removeSpartPartProcess(Long id);

	public void removeSpartPartProcesss(Long[] ids);

	public List<SpartPartProcessDTO> findAllSpartPartProcess();

	public Page<SpartPartProcessDTO> pageQuerySpartPartProcess(
			SpartPartProcessDTO spartPartProcess, int currentPage, int pageSize);
	Map<String, Object> pageQuerySpartPartProcessTotal(SpartPartProcessDTO queryVo);
	public UserDTO findCreateUserBySpartPartProcess(Long id);

	public UserDTO findLastModifyUserBySpartPartProcess(Long id);

	public SpartPartDTO findSpartPartBySpartPartProcess(Long id);

	public Page<LocationDTO> findLocationsBySpartPartProcess(Long id,
			int currentPage, int pageSize);

	public StationDTO findStationBySpartPartProcess(Long id);

	public ResourceDTO findResLotTypeBySpartPartProcess(Long id);

	public SpartPartProcessDTO saveSpartPartProcessReceive(
			SpartPartProcessDTO spartPartProcessDTO, Long spartPartId,
			String locationIds);

	public SpartPartProcessDTO saveNextProcess(
			SpartPartProcessDTO spartPartProcessDTO);
	void changeLocations(SpartPartProcessDTO spartPartProcessDTO,
			String locationIds, String useraccount, String comments);
	void changeStatus(SpartPartProcessDTO spartPartProcessDTO, String holdCode, String holdCodeId, String useraccount,
			String comments);
}
