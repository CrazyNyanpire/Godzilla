package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface BomListItemApplication {

	public BomListItemDTO getBomListItem(Long id);

	public BomListItemDTO saveBomListItem(BomListItemDTO bomListItem);

	public void updateBomListItem(BomListItemDTO bomListItem);

	public void removeBomListItem(Long id);

	public void removeBomListItems(Long[] ids);

	public List<BomListItemDTO> findAllBomListItem();

	public Page<BomListItemDTO> pageQueryBomListItem(
			BomListItemDTO bomListItem, int currentPage, int pageSize);

	public UserDTO findCreateUserByBomListItem(Long id);

	public UserDTO findLastModifyUserByBomListItem(Long id);

	public MaterialDTO findMaterialByBomListItem(Long id);

	public BomListDTO findBomListByBomListItem(Long id);

	public BomListItemDTO findBomListItemByManufactureProcessAndMaterial(
			Long manufactureProcessId, Long materialProcessId);
	public List<BomListItemDTO> findBomListItemByProduct(Long productId,String bomListType,String materialName);
}
