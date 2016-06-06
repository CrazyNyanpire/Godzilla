package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SubstrateProcessApplication {

	public SubstrateProcessDTO getSubstrateProcess(Long id);

	public SubstrateProcessDTO saveSubstrateProcess(
			SubstrateProcessDTO substrateProcess);

	public void updateSubstrateProcess(SubstrateProcessDTO substrateProcess);

	public void removeSubstrateProcess(Long id);

	public void removeSubstrateProcesss(Long[] ids);

	public List<SubstrateProcessDTO> findAllSubstrateProcess();

	public Page<SubstrateProcessDTO> pageQuerySubstrateProcess(
			SubstrateProcessDTO substrateProcess, int currentPage, int pageSize);

	public UserDTO findCreateUserBySubstrateProcess(Long id);

	public UserDTO findLastModifyUserBySubstrateProcess(Long id);

	public StationDTO findStationBySubstrateProcess(Long id);

	public Page<LocationDTO> findLocationsBySubstrateProcess(Long id,
			int currentPage, int pageSize);

	public EquipmentDTO findEquipmentBySubstrateProcess(Long id);

	public SubstrateCompanyLotDTO findSubstrateCompanyLotBySubstrateProcess(
			Long id);

	public SubstrateProcessDTO saveSubstrateProcessReceive(
			SubstrateProcessDTO substrateProcessDTO,
			Long substrateCustomerLotId, String locationIds);

	public SubstrateProcessDTO saveNextProcess(
			SubstrateProcessDTO substrateProcessDTO);

	public String saveSplitSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String useraccount,
			Integer stripQty, Integer qty, String comments);
	
	public List<SubstrateProcessDTO> getSplitSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO,
			Integer stripQty, Integer qty);

	public void changeStatusSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public void holdSubstrateProcess(SubstrateProcessDTO substrateProcessDTO,
			String holdCode, String useraccount, String comments);

	public void engDispSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,
			String useraccount, String comments);

	public void holdSubstrateProcess(SubstrateProcessDTO substrateProcessDTO,
			String holdCode, String holdCodeId, String useraccount,
			String comments);

	public void engDispSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public String saveMergeSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String mergeIds,
			String useraccount, String comments);
	
	public SubstrateProcessDTO getMergeSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String mergeIds) ;

	public void futureHoldSubstrateProcess(
			SubstrateProcessDTO substrateProcessDTO, String holdCode,
			String holdCodeId, String useraccount, String comments);

	public void changeLotType(SubstrateProcessDTO substrateProcessDTO,
			String lotType, String useraccount, String comments);

	public void changeLocations(SubstrateProcessDTO substrateProcessDTO,
			String locationIds, String useraccount, String comments);

	public void trackIn(SubstrateProcessDTO substrateProcessDTO,
			String useraccount, String comments);

	public void abortStep(SubstrateProcessDTO substrateProcessDTO,
			String useraccount, String comments);

	public SubstrateProcessDTO trackOut(SubstrateProcessDTO substrateProcessDTO);
	
	public Map<String, Object> pageQuerySubstrateProcessTotal(SubstrateProcessDTO substrateProcessDTO);

	public SubstrateStatusOptLogDTO findSubstrateStatusOptLogByProcess(Long id);
}
