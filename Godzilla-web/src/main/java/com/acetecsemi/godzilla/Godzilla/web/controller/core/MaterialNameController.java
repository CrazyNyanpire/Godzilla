
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.MaterialNameApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/MaterialName")
public class MaterialNameController {
		
	@Inject
	private MaterialNameApplication materialNameApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MaterialNameDTO materialNameDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialNameApplication.saveMaterialName(materialNameDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MaterialNameDTO materialNameDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialNameApplication.updateMaterialName(materialNameDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MaterialNameDTO materialNameDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialNameDTO> all = materialNameApplication.pageQueryMaterialName(materialNameDTO, page, pagesize);
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
        materialNameApplication.removeMaterialNames(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialNameApplication.getMaterialName(id));
		return result;
	}
	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllMaterialName() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialNameDTO> list = materialNameApplication.findAllMaterialName();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
		
}
