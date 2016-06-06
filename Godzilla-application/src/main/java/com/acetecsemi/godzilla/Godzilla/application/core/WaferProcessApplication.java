package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferProcessApplication {

	public WaferProcessDTO getWaferProcess(Long id);

	public WaferProcessDTO saveWaferProcess(WaferProcessDTO waferProcess);

	public void updateWaferProcess(WaferProcessDTO waferProcess);

	public void removeWaferProcess(Long id);

	public void removeWaferProcesss(Long[] ids);

	public List<WaferProcessDTO> findAllWaferProcess();

	public Page<WaferProcessDTO> pageQueryWaferProcess(
			WaferProcessDTO waferProcess, int currentPage, int pageSize);

	public UserDTO findCreateUserByWaferProcess(Long id);

	public UserDTO findLastModifyUserByWaferProcess(Long id);

	public StationDTO findStationByWaferProcess(Long id);

	public UserDTO findOptUserByWaferProcess(Long id);

	public Page<LocationDTO> findLocationsByWaferProcess(Long id,
			int currentPage, int pageSize);

	public EquipmentDTO findEquipmentByWaferProcess(Long id);

	public WaferCompanyLotDTO findWaferCompanyLotByWaferProcess(Long id);

	public WaferProcessDTO saveWaferProcessReceive(
			WaferProcessDTO waferProcessDTO, Long waferCustomerLotId,
			String locationIds, List <WaferInfoDTO> waferInfoList);

	public WaferProcessDTO saveNextProcess(WaferProcessDTO waferProcessDTO);
	public String nextProcessVerify(Map<String, Object> info, Long waferProcessId);
	
	public String saveSplitWaferProcess(
			WaferProcessDTO waferProcessDTO, String useraccount, Integer waferQty,
			Integer dieQty, String comments, String locationIds,List<String> waferIds);
	
	public List<WaferProcessDTO> getSplitWaferProcess(WaferProcessDTO waferProcessDTO,
			Integer waferQty, Integer dieQty);
	
	public void changeStatusWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount, String comments);
	
	public void holdWaferProcess(WaferProcessDTO waferProcessDTO,String holdCode, String holdCodeId, String useraccount,String comments);
	
	public void engDispWaferProcess(WaferProcessDTO waferProcessDTO,String holdCode,String holdCodeId, String useraccount,String comments,String engineerName);
	
	public String saveMergeWaferProcess(WaferProcessDTO waferProcessDTO,
			String mergeIds, String useraccount, String comments, String locationIds);
	
	public WaferProcessDTO getMergeWaferProcess(WaferProcessDTO waferProcessDTO,
			String mergeIds);
	
	public void futureHoldWaferProcess(WaferProcessDTO waferProcessDTO,
			String holdCode, String holdCodeId, String useraccount, String comments);
	
	public void changeLotType(WaferProcessDTO waferProcessDTO,String lotType, String useraccount,String comments);
	
	public void changeLocations(WaferProcessDTO waferProcessDTO, String locationIds,
			String useraccount, String comments) ;
	
	public List<Map<String,String>> getLotTypes() ;
	
	public List<Map<String,String>> getResourceByParentIdentifier(String identifier);
	
	public void trackIn(WaferProcessDTO waferProcessDTO, String useraccount,String comments);
	
	public String verifyEquipmentRunningStatusById(Long id);
	
	public void abortStep(WaferProcessDTO waferProcessDTO, String useraccount,String comments);
	
	public WaferProcessDTO trackOut(WaferProcessDTO waferProcessDTO);
	
	public Map<String, Object> pageQueryWaferProcessTotal(WaferProcessDTO waferProcessDTO);

	public WaferStatusOptLogDTO findWaferStatusOptLogByProcess(Long id,boolean isFuture);

	public List<StationDTO> findAfterStationsByProcessId(Long id) ;

	public List<WaferProcessDTO> findWaferLotBeforeDA(Long id);
	
	public String getProductNameByWaferProcessId(Long id);
	
	String checkProcess(Long waferCustomerLotId,List<Map<String, Object>> waferInfo);
	
	public void release(WaferProcessDTO waferProcessDTO, String useraccount,String comments);
	
	void updateWaferProcessDTODelayTime(List<WaferProcessDTO> list,int batchSize);

	public void outSpecWaferProcess(WaferProcessDTO waferProcessDTO,
			String useraccount,String comments);
	
	public int checkOutSpecWaferProcessTime(Long waferProcessId);
}
