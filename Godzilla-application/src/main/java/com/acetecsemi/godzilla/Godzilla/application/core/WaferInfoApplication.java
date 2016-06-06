package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface WaferInfoApplication {

	public WaferInfoDTO getWaferInfo(Long id);

	public WaferInfoDTO saveWaferInfo(WaferInfoDTO waferInfo);

	public void updateWaferInfo(WaferInfoDTO waferInfo);

	public void removeWaferInfo(Long id);

	public void removeWaferInfos(Long[] ids);

	public List<WaferInfoDTO> findAllWaferInfo();

	public Page<WaferInfoDTO> pageQueryWaferInfo(WaferInfoDTO waferInfo,
			int currentPage, int pageSize);

	public UserDTO findCreateUserByWaferInfo(Long id);

	public UserDTO findLastModifyUserByWaferInfo(Long id);

	public WaferCompanyLotDTO findWaferCompanyLotByWaferInfo(Long id);

	public List<WaferInfoDTO> findWaferInfoByWaferCompanyLotId(Long id);
	
	public void updateWaferInfos(List<WaferInfoDTO> waferInfos);
}
