package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferCustomerLotApplication {

	public WaferCustomerLotDTO getWaferCustomerLot(Long id);

	public WaferCustomerLotDTO saveWaferCustomerLot(
			WaferCustomerLotDTO waferCustomerLot);

	public void updateWaferCustomerLot(WaferCustomerLotDTO waferCustomerLot);

	public void removeWaferCustomerLot(Long id);

	public void removeWaferCustomerLots(Long[] ids);

	public List<WaferCustomerLotDTO> findAllWaferCustomerLot();

	public Page<WaferCustomerLotDTO> pageQueryWaferCustomerLot(
			WaferCustomerLotDTO waferCustomerLot, int currentPage, int pageSize);

	public UserDTO findCreateUserByWaferCustomerLot(Long id);

	public UserDTO findLastModifyUserByWaferCustomerLot(Long id);

	public CustomerDTO findCustomerByWaferCustomerLot(Long id);

	public ProductDTO findProductByWaferCustomerLot(Long id);

	public Page<DefineStationProcessDTO> findDefineStationProcessesByWaferCustomerLot(
			Long id, int currentPage, int pageSize);

	public RunCardTemplateDTO findRunCardTemplateByWaferCustomerLot(Long id);

	public Map<String, Object> pageQueryWaferCustomerLotTotal(WaferCustomerLotDTO waferCustomerLotDTO);
	int waferCustomerLotTotal(WaferCustomerLotDTO queryVo);
}
