
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.framework.i18n.I18NManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.LocationApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialLocationApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/MaterialLocation")
public class MaterialLocationController {
		
	@Inject
	private MaterialLocationApplication materialLocationApplication;
	
	@Inject
	private LocationApplication locationApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MaterialLocationDTO materialLocationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialLocationApplication.saveMaterialLocation(materialLocationDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MaterialLocationDTO materialLocationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialLocationApplication.updateMaterialLocation(materialLocationDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MaterialLocationDTO materialLocationDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialLocationDTO> all = materialLocationApplication.pageQueryMaterialLocation(materialLocationDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	
        	        					idArrs[i] = Long.parseLong(value[i]);
						        	
        }
        materialLocationApplication.removeMaterialLocations(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLocationApplication.getMaterialLocation(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByMaterialLocation/{id}")
	public Map<String, Object> findCreateUserByMaterialLocation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLocationApplication.findCreateUserByMaterialLocation(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByMaterialLocation/{id}")
	public Map<String, Object> findLastModifyUserByMaterialLocation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLocationApplication.findLastModifyUserByMaterialLocation(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMaterialCompanyLotByMaterialLocation/{id}")
	public Map<String, Object> findMaterialCompanyLotByMaterialLocation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLocationApplication.findMaterialCompanyLotByMaterialLocation(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllLocation() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<LocationDTO> list = locationApplication.findAllLocation();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else{
			result.put("result", "fail");
			result.put("data", "");
			result.put("actionError", I18NManager.getMessage("location.noLocation", Locale.getDefault()
					.toString()));
		}
			
		return result;
	}
	
}
