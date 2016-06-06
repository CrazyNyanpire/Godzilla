package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialProcessApplication {

	public MaterialProcessDTO getMaterialProcess(Long id);

	public MaterialProcessDTO saveMaterialProcess(
			MaterialProcessDTO materialProcess);

	public void updateMaterialProcess(MaterialProcessDTO materialProcess);

	public void removeMaterialProcess(Long id);

	public void removeMaterialProcesss(Long[] ids);

	public List<MaterialProcessDTO> findAllMaterialProcess();

	public Page<MaterialProcessDTO> pageQueryMaterialProcess(
			MaterialProcessDTO materialProcess, int currentPage, int pageSize);

	public UserDTO findCreateUserByMaterialProcess(Long id);

	public UserDTO findLastModifyUserByMaterialProcess(Long id);

	public StationDTO findStationByMaterialProcess(Long id);

	public EquipmentDTO findEquipmentByMaterialProcess(Long id);

	public ResourceDTO findResLotTypeByMaterialProcess(Long id);

	public ResourceDTO findResHoldByMaterialProcess(Long id);

	public MaterialCompanyLotDTO findMaterialCompanyLotByMaterialProcess(Long id);

	public Page<MaterialLocationDTO> findMaterialLocationsByMaterialProcess(
			Long id, int currentPage, int pageSize);

	public MaterialProcessDTO saveMaterialProcessReceive(
			MaterialProcessDTO materialProcessDTO, Long materialCustomerLotId,
			String locationIds);

	public MaterialProcessDTO saveNextProcess(MaterialProcessDTO materialProcessDTO);

	public String saveSplitMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String useraccount, Integer materialQty,String comments);

	public void changeStatusMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments);

	public void holdMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String useraccount, String comments);

	public void holdMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments);

	public void engDispMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments);
	
	public String saveMergeMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String mergeIds, String useraccount, String comments);

	public void futureHoldMaterialProcess(MaterialProcessDTO materialProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments);

	public void changeLocations(MaterialProcessDTO materialProcessDTO,
			String locationIds, String useraccount, String comments);

	public List<MaterialProcessDTO> getSplitMaterialProcess(
			MaterialProcessDTO materialProcessDTO,Integer materialQty);
	
	public MaterialProcessDTO getMergeMaterialProcess(
			MaterialProcessDTO materialProcessDTO, String mergeIds);
	
	public MaterialStatusOptLogDTO findMaterialStatusOptLogByProcess(Long id);
	
	public Map<String, Object> pageQueryMaterialProcessTotal(MaterialProcessDTO materialProcessDTO);
	
	public void abortStep(MaterialProcessDTO materialProcessDTO, String useraccount,
			String comments);
}
