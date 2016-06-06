package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface ManufactureLotApplication {

	public ManufactureLotDTO getManufactureLot(Long id);

	public ManufactureLotDTO saveManufactureLot(ManufactureLotDTO manufactureLot);

	public void updateManufactureLot(ManufactureLotDTO manufactureLot);

	public void removeManufactureLot(Long id);

	public void removeManufactureLots(Long[] ids);

	public List<ManufactureLotDTO> findAllManufactureLot();

	public Page<ManufactureLotDTO> pageQueryManufactureLot(
			ManufactureLotDTO manufactureLot, int currentPage, int pageSize);

	public UserDTO findCreateUserByManufactureLot(Long id);

	public UserDTO findLastModifyUserByManufactureLot(Long id);

	public ManufactureLotDTO findMergeManufactureLotByManufactureLot(Long id);

	public ManufactureLotDTO findParentManufactureLotByManufactureLot(Long id);

	public Page<ManufactureLotDTO> findManufactureLotsByManufactureLot(Long id,
			int currentPage, int pageSize);

	public ManufactureProcessDTO findNowManufactureProcessByManufactureLot(
			Long id);

	public Page<ManufactureProcessDTO> findManufactureProcessesByManufactureLot(
			Long id, int currentPage, int pageSize);

	public Page<WaferCompanyLotDTO> findWaferCompanyLotsByManufactureLot(
			Long id, int currentPage, int pageSize);

	public String getNewLotNo(Long customerId);
	
	public ManufactureLotDTO findManufactureLotByCompanyId(Long id);
}
