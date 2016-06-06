package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface MaterialApplication {

	public MaterialDTO getMaterial(Long id);

	public MaterialDTO saveMaterial(MaterialDTO material);

	public void updateMaterial(MaterialDTO material);

	public void removeMaterial(Long id);

	public void removeMaterials(Long[] ids);

	public List<MaterialDTO> findAllMaterial();

	public Page<MaterialDTO> pageQueryMaterial(MaterialDTO material,
			int currentPage, int pageSize);

	public UserDTO findCreateUserByMaterial(Long id);

	public UserDTO findLastModifyUserByMaterial(Long id);

	public List<MaterialDTO> findMaterialByType(int type);
	
	public List<MaterialDTO> findMaterialNameByType(int type);
	
	public List<MaterialDTO> getByMaterialNameId(Long id);

}
