
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

import com.acetecsemi.godzilla.Godzilla.application.core.VenderApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Vender")
public class VenderController {
		
	@Inject
	private VenderApplication venderApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(VenderDTO venderDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		venderApplication.saveVender(venderDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(VenderDTO venderDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		venderApplication.updateVender(venderDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(VenderDTO venderDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<VenderDTO> all = venderApplication.pageQueryVender(venderDTO, page, pagesize);
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
        venderApplication.removeVenders(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", venderApplication.getVender(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByVender/{id}")
	public Map<String, Object> findCreateUserByVender(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", venderApplication.findCreateUserByVender(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByVender/{id}")
	public Map<String, Object> findLastModifyUserByVender(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", venderApplication.findLastModifyUserByVender(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllVenders() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<VenderDTO> list = venderApplication.findAllVender();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
}
