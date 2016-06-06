
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface SpartPartApplication {

	public SpartPartDTO getSpartPart(Long id);
	
	public SpartPartDTO saveSpartPart(SpartPartDTO spartPart);
	
	public void updateSpartPart(SpartPartDTO spartPart);
	
	public void removeSpartPart(Long id);
	
	public void removeSpartParts(Long[] ids);
	
	public List<SpartPartDTO> findAllSpartPart();
	
	public Page<SpartPartDTO> pageQuerySpartPart(SpartPartDTO spartPart, int currentPage, int pageSize);
	
	Map<String, Object> pageQuerySpartPartTotal(SpartPartDTO queryVo);
	public UserDTO findCreateUserBySpartPart(Long id);

		public UserDTO findLastModifyUserBySpartPart(Long id);
	public Page<ProductDTO> findProductsBySpartPart(Long id, int currentPage, int pageSize);		
	public Page<StationDTO> findStationsBySpartPart(Long id, int currentPage, int pageSize);		
		public ResourceDTO findResLotTypeBySpartPart(Long id);

		public VenderDTO findVenderBySpartPart(Long id);
		public DefineStationProcessDTO findDefineStationProcessBySpartPart(Long id);
	SpartPartCompanyLotDTO getSpartPartCompanyLot(Long id);
	SpartPartCompanyLotDTO getSpartPartCompanyLotBySpartPart(Long id);
	String getNewLotNo();
	SpartPartCompanyLotDTO saveSpartPartCompanyLot(
			SpartPartCompanyLotDTO spartPartCompanyLotDTO);

	
}

