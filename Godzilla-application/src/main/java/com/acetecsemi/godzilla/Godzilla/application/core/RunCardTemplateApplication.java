package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;

public interface RunCardTemplateApplication {

	public RunCardTemplateDTO getRunCardTemplate(Long id);

	public RunCardTemplateDTO saveRunCardTemplate(
			RunCardTemplateDTO runCardTemplate);

	public void updateRunCardTemplate(RunCardTemplateDTO runCardTemplate);

	public void removeRunCardTemplate(Long id);

	public void removeRunCardTemplates(Long[] ids);

	public List<RunCardTemplateDTO> findAllRunCardTemplate();

	public Page<RunCardTemplateDTO> pageQueryRunCardTemplate(
			RunCardTemplateDTO runCardTemplate, int currentPage, int pageSize);

	public UserDTO findCreateUserByRunCardTemplate(Long id);

	public UserDTO findLastModifyUserByRunCardTemplate(Long id);

	public ProductDTO findProductByRunCardTemplate(Long id);

	public Page<RunCardItemDTO> findRunCardItemsByRunCardTemplate(Long id,
			int currentPage, int pageSize);
	
	public Map<String,Object> findRunCardByProcessAndType(Long id,String type);

}
