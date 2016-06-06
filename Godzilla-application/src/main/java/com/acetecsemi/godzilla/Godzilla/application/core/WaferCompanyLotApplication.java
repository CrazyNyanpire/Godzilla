package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferCompanyLotApplication {

	public WaferCompanyLotDTO getWaferCompanyLot(Long id);

	public WaferCompanyLotDTO saveWaferCompanyLot(
			WaferCompanyLotDTO waferCompanyLot);

	public void updateWaferCompanyLot(WaferCompanyLotDTO waferCompanyLot);

	public void removeWaferCompanyLot(Long id);

	public void removeWaferCompanyLots(Long[] ids);

	public List<WaferCompanyLotDTO> findAllWaferCompanyLot();

	public Page<WaferCompanyLotDTO> pageQueryWaferCompanyLot(
			WaferCompanyLotDTO waferCompanyLot, int currentPage, int pageSize);

	public UserDTO findCreateUserByWaferCompanyLot(Long id);

	public UserDTO findLastModifyUserByWaferCompanyLot(Long id);

	public WaferCustomerLotDTO findWaferCustomerLotByWaferCompanyLot(Long id);

	public WaferCompanyLotDTO findMargeWaferCompanyLotByWaferCompanyLot(Long id);

	public WaferCompanyLotDTO findParentWaferCompanyLotByWaferCompanyLot(Long id);

	public Page<WaferCompanyLotDTO> findWaferCompanyLotsByWaferCompanyLot(
			Long id, int currentPage, int pageSize);

	public WaferProcessDTO findNowProcessByWaferCompanyLot(Long id);

	public Page<WaferProcessDTO> findProcessesByWaferCompanyLot(Long id,
			int currentPage, int pageSize);
	
	public String getNewLotNo(Long id);
	
	public WaferCompanyLotDTO findWaferCompanyLotByCustomerId(Long id);
}
