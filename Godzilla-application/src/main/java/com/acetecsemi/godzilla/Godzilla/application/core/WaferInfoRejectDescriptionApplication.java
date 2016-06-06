package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferInfoRejectDescriptionApplication {

	public WaferInfoRejectDescriptionDTO getWaferInfoRejectDescription(Long id);

	public WaferInfoRejectDescriptionDTO saveWaferInfoRejectDescription(
			WaferInfoRejectDescriptionDTO waferInfoRejectDescription);

	public void updateWaferInfoRejectDescription(
			WaferInfoRejectDescriptionDTO waferInfoRejectDescription);

	public void removeWaferInfoRejectDescription(Long id);

	public void removeWaferInfoRejectDescriptions(Long[] ids);

	public List<WaferInfoRejectDescriptionDTO> findAllWaferInfoRejectDescription();

	public Page<WaferInfoRejectDescriptionDTO> pageQueryWaferInfoRejectDescription(
			WaferInfoRejectDescriptionDTO waferInfoRejectDescription,
			int currentPage, int pageSize);

	public WaferProcessDTO findWaferProcessByWaferInfoRejectDescription(Long id);

	public WaferInfoDTO findWaferInfoByWaferInfoRejectDescription(Long id);

	public void saveWaferInfoRejectDescriptions(
			List<WaferInfoRejectDescriptionDTO> waferInfoRejectDescriptionDTOs);

}
