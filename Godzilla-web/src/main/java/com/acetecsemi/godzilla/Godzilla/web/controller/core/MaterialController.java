
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.MaterialApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Material")
public class MaterialController {
		
	@Inject
	private MaterialApplication materialApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MaterialDTO materialDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date date = new Date();
		materialDTO.setCreateDate(date);
		materialDTO.setLastModifyDate(date);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		materialDTO.setUserDTO(optUser);
		materialApplication.saveMaterial(materialDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MaterialDTO materialDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date date = new Date();
		materialDTO.setLastModifyDate(date);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		materialDTO.setUserDTO(optUser);
		materialApplication.updateMaterial(materialDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MaterialDTO materialDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialDTO> all = materialApplication.pageQueryMaterial(materialDTO, page, pagesize);
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
        materialApplication.removeMaterials(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialApplication.getMaterial(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getByMaterialNameId/{id}")
	public Map<String, Object> getByMaterialNameId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialApplication.getByMaterialNameId(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findCreateUserByMaterial/{id}")
	public Map<String, Object> findCreateUserByMaterial(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialApplication.findCreateUserByMaterial(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByMaterial/{id}")
	public Map<String, Object> findLastModifyUserByMaterial(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialApplication.findLastModifyUserByMaterial(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/Direct/all")
	public Map<String, Object> findDirectAllMaterial() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialDTO> list = materialApplication.findMaterialNameByType(1);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Indirect/all")
	public Map<String, Object> findIndirectAllMaterial() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialDTO> list = materialApplication.findMaterialNameByType(2);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Component/all")
	public Map<String, Object> findComponentAllMaterial() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialDTO> list = materialApplication.findMaterialNameByType(3);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Consumables/all")
	public Map<String, Object> findconsumablesAllMaterial() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialDTO> list = materialApplication.findMaterialNameByType(4);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/all/{type}")
	public Map<String, Object> findAllByMaterialType(@PathVariable Integer type) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialDTO> list = materialApplication.findMaterialNameByType(type);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	@ResponseBody
	@RequestMapping("/findAllByMaterialNameId/{id}")
	public Map<String, Object> findAllByMaterialNameId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MaterialDTO> list = materialApplication.getByMaterialNameId(id);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
}
