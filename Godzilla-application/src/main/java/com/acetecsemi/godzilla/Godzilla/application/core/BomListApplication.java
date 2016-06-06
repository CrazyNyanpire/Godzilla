package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface BomListApplication {

	public BomListDTO getBomList(Long id);

	public BomListDTO saveBomList(BomListDTO bomList);

	public void updateBomList(BomListDTO bomList);

	public void removeBomList(Long id);

	public void removeBomLists(Long[] ids);

	public List<BomListDTO> findAllBomList();

	public Page<BomListDTO> pageQueryBomList(BomListDTO bomList,
			int currentPage, int pageSize);

	public UserDTO findCreateUserByBomList(Long id);

	public UserDTO findLastModifyUserByBomList(Long id);

	public Page<BomListItemDTO> findBomListItemsByBomList(Long id,
			int currentPage, int pageSize);

	public BomListDTO findBomListByProduct(Long productId,String bomListType);

	public Map<String, List<BomListItemDTO>> findBomListItemsByBomList(Long id,boolean isMonitor);
	
	public BomListDTO findBomListByProcess(Long processId,String type);
	
	public Map<String, List<SelectItemDTO>> findMaterialByManuProcess(Long manuProcessid,Long stationId,boolean isMonitor) ;
}
