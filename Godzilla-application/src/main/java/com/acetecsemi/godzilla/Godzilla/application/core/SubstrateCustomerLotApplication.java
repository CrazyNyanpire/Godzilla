package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SubstrateCustomerLotApplication {

	public SubstrateCustomerLotDTO getSubstrateCustomerLot(Long id);

	public SubstrateCustomerLotDTO saveSubstrateCustomerLot(
			SubstrateCustomerLotDTO substrateCustomerLot);

	public void updateSubstrateCustomerLot(
			SubstrateCustomerLotDTO substrateCustomerLot);

	public void removeSubstrateCustomerLot(Long id);

	public void removeSubstrateCustomerLots(Long[] ids);

	public List<SubstrateCustomerLotDTO> findAllSubstrateCustomerLot();

	public Page<SubstrateCustomerLotDTO> pageQuerySubstrateCustomerLot(
			SubstrateCustomerLotDTO substrateCustomerLot, int currentPage,
			int pageSize);

	public UserDTO findCreateUserBySubstrateCustomerLot(Long id);

	public UserDTO findLastModifyUserBySubstrateCustomerLot(Long id);

	public VenderDTO findVenderBySubstrateCustomerLot(Long id);

	public ProductDTO findProductBySubstrateCustomerLot(Long id);

	public Page<DefineStationProcessDTO> findDefineStationProcessesBySubstrateCustomerLot(
			Long id, int currentPage, int pageSize);

	public RunCardTemplateDTO findRunCardTemplateBySubstrateCustomerLot(Long id);

	public Map<String, Object> pageQuerySubstrateCustomerLotTotal(
			SubstrateCustomerLotDTO substrateCustomerLotDTO);

}
