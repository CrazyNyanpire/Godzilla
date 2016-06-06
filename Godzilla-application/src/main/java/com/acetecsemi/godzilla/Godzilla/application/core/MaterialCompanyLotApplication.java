package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialCompanyLotApplication {

	public MaterialCompanyLotDTO getMaterialCompanyLot(Long id);

	public MaterialCompanyLotDTO saveMaterialCompanyLot(
			MaterialCompanyLotDTO materialCompanyLot);

	public void updateMaterialCompanyLot(
			MaterialCompanyLotDTO materialCompanyLot);

	public void removeMaterialCompanyLot(Long id);

	public void removeMaterialCompanyLots(Long[] ids);

	public List<MaterialCompanyLotDTO> findAllMaterialCompanyLot();

	public Page<MaterialCompanyLotDTO> pageQueryMaterialCompanyLot(
			MaterialCompanyLotDTO materialCompanyLot, int currentPage,
			int pageSize);

	public UserDTO findCreateUserByMaterialCompanyLot(Long id);

	public UserDTO findLastModifyUserByMaterialCompanyLot(Long id);

	public MaterialCompanyLotDTO findMargeMaterialCompanyLotByMaterialCompanyLot(
			Long id);

	public MaterialCompanyLotDTO findParentMaterialCompanyLotByMaterialCompanyLot(
			Long id);

	public MaterialCompanyLotDTO findMaterialCompanyLotByCustomerId(Long id);

	public String getNewLotNo();
}
