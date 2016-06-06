package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialLotApplication {

	public MaterialLotDTO getMaterialLot(Long id);

	public MaterialLotDTO saveMaterialLot(MaterialLotDTO materialLot);

	public void updateMaterialLot(MaterialLotDTO materialLot);

	public void removeMaterialLot(Long id);

	public void removeMaterialLots(Long[] ids);

	public List<MaterialLotDTO> findAllMaterialLot();

	public Page<MaterialLotDTO> pageQueryMaterialLot(
			MaterialLotDTO materialLot, int currentPage, int pageSize);

	public UserDTO findCreateUserByMaterialLot(Long id);

	public UserDTO findLastModifyUserByMaterialLot(Long id);

	public CustomerDTO findCustomerByMaterialLot(Long id);

	public MaterialDTO findMaterialByMaterialLot(Long id);

	public Map<String, Object> pageQueryMaterialLotTotal(
			MaterialLotDTO materialLotDTO);

}
