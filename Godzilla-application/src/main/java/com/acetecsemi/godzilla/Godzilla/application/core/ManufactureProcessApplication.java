package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ManufactureProcessApplication {

	public ManufactureProcessDTO getManufactureProcess(Long id);

	public ManufactureProcessDTO saveManufactureProcess(
			ManufactureProcessDTO manufactureProcess);

	public void updateManufactureProcess(
			ManufactureProcessDTO manufactureProcess);

	public void removeManufactureProcess(Long id);

	public void removeManufactureProcesss(Long[] ids);

	public List<ManufactureProcessDTO> findAllManufactureProcess();

	public Page<ManufactureProcessDTO> pageQueryManufactureProcess(
			ManufactureProcessDTO manufactureProcess, int currentPage,
			int pageSize);

	public UserDTO findCreateUserByManufactureProcess(Long id);

	public UserDTO findLastModifyUserByManufactureProcess(Long id);

	public StationDTO findStationByManufactureProcess(Long id);

	public Page<LocationDTO> findLocationsByManufactureProcess(Long id,
			int currentPage, int pageSize);

	public EquipmentDTO findEquipmentByManufactureProcess(Long id);

	public ManufactureLotDTO findManufactureLotByManufactureProcess(Long id);

	public ManufactureProcessDTO saveManufactureProcessReceive(
			ManufactureProcessDTO manufactureProcessDTO,
			Long manufactureCustomerLotId, String locationIds);

	public ManufactureProcessDTO saveNextProcess(
			ManufactureProcessDTO manufactureProcessDTO);

	public String saveSplitManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String useraccount,
			Integer stripQty, Integer qty, String comments);

	public List<ManufactureProcessDTO> getSplitManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, Integer stripQty,
			Integer qty);

	public void changeStatusManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public void holdManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String useraccount, String comments);

	public void engDispManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String useraccount, String comments);

	public void holdManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public void engDispManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public String saveMergeManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String mergeIds,
			String useraccount, String comments);

	public ManufactureProcessDTO getMergeManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String mergeIds);

	public void futureHoldManufactureProcess(
			ManufactureProcessDTO manufactureProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public void changeLotType(ManufactureProcessDTO manufactureProcessDTO,
			String lotType, String useraccount, String comments);

	public void changeLocations(ManufactureProcessDTO manufactureProcessDTO,
			String locationIds, String useraccount, String comments);

	public void trackIn(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount, String comments);

	public void abortStep(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount, String comments);

	public ManufactureProcessDTO trackOut(
			ManufactureProcessDTO manufactureProcessDTO);

	public Map<String, Object> pageQueryManufactureProcessTotal(
			ManufactureProcessDTO manufactureProcessDTO);

	public ManufactureStatusOptLogDTO findManufactureStatusOptLogByProcess(
			Long id, boolean isFuture);

	public ManufactureProcessDTO saveManufactureProcess(Long manufactureLotId,
			Long substrateProcessId, ManufactureProcessDTO manufactureProcessDTO);

	public List<StationDTO> findAfterStationsByProcessId(Long id);
	
	public void release(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount, String comments);
	
	String nextProcessVerify(Map<String, Object> info,Long waferProcessId);
	String verifyEquipmentRunningStatusById(Long id);
	void updateManufactureProcessDelayTime(List<ManufactureProcessDTO> list,int batchSize);
	
	public void outSpecManufactureProcess(ManufactureProcessDTO manufactureProcessDTO,
			String useraccount,String comments);
	
	public int checkOutSpecManufactureProcessTime(Long manufactureProcessId);
}
