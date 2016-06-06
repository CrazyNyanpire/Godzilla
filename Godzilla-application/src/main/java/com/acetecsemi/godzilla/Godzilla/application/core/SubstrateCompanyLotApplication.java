package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SubstrateCompanyLotApplication {

	public SubstrateCompanyLotDTO getSubstrateCompanyLot(Long id);

	public SubstrateCompanyLotDTO saveSubstrateCompanyLot(
			SubstrateCompanyLotDTO substrateCompanyLot);

	public void updateSubstrateCompanyLot(
			SubstrateCompanyLotDTO substrateCompanyLot);

	public void removeSubstrateCompanyLot(Long id);

	public void removeSubstrateCompanyLots(Long[] ids);

	public List<SubstrateCompanyLotDTO> findAllSubstrateCompanyLot();

	public Page<SubstrateCompanyLotDTO> pageQuerySubstrateCompanyLot(
			SubstrateCompanyLotDTO substrateCompanyLot, int currentPage,
			int pageSize);

	public UserDTO findCreateUserBySubstrateCompanyLot(Long id);

	public UserDTO findLastModifyUserBySubstrateCompanyLot(Long id);

	public SubstrateCustomerLotDTO findSubstrateCustomerLotBySubstrateCompanyLot(
			Long id);

	public SubstrateCompanyLotDTO findMargeSubstrateCompanyLotBySubstrateCompanyLot(
			Long id);

	public SubstrateCompanyLotDTO findParentSubstrateCompanyLotBySubstrateCompanyLot(
			Long id);

	public Page<SubstrateCompanyLotDTO> findSubstrateCompanyLotsBySubstrateCompanyLot(
			Long id, int currentPage, int pageSize);

	public SubstrateProcessDTO findNowSubstrateProcessBySubstrateCompanyLot(
			Long id);

	public Page<SubstrateProcessDTO> findSubstrateProcessesBySubstrateCompanyLot(
			Long id, int currentPage, int pageSize);

	public String getNewLotNo();
	
	public SubstrateCompanyLotDTO findSubstrateCompanyLotByCustomerId(Long id);
}
