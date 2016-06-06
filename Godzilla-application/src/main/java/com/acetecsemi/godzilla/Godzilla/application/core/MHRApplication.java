package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MHRApplication {

	public MHRDTO getMHR(Long id);

	public MHRDTO saveMHR(MHRDTO mHR);

	public void updateMHR(MHRDTO mHR);

	public void removeMHR(Long id);

	public void removeMHRs(Long[] ids);

	public List<MHRDTO> findAllMHR();

	public Page<MHRDTO> pageQueryMHR(MHRDTO mHR, int currentPage, int pageSize);

	public UserDTO findCreateUserByMHR(Long id);

	public UserDTO findLastModifyUserByMHR(Long id);

	public String getNewMHRNo();

}
