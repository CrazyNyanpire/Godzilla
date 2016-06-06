
package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface EquipmentApplication {

	public EquipmentDTO getEquipment(Long id);
	
	public EquipmentDTO saveEquipment(EquipmentDTO equipment);
	
	public void updateEquipment(EquipmentDTO equipment);
	
	public void removeEquipment(Long id);
	
	public void removeEquipments(Long[] ids);
	
	public List<EquipmentDTO> findAllEquipment();
	
	public Page<EquipmentDTO> pageQueryEquipment(EquipmentDTO equipment, int currentPage, int pageSize);
	
	public UserDTO findCreateUserByEquipment(Long id);


		public UserDTO findLastModifyUserByEquipment(Long id);


		public WaferProcessDTO findWaferProcessByEquipment(Long id);


		public StationDTO findStationByEquipment(Long id);

		ManufactureProcessDTO findManufactureProcessByEquipment(Long id);
	
}

