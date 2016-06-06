
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
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Location")
public class LocationController {
		
	@Inject
	private LocationApplication locationApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(LocationDTO locationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		locationApplication.saveLocation(locationDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(LocationDTO locationDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		locationApplication.updateLocation(locationDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(LocationDTO locationDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<LocationDTO> all = locationApplication.pageQueryLocation(locationDTO, page, pagesize);
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
        locationApplication.removeLocations(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", locationApplication.getLocation(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByLocation/{id}")
	public Map<String, Object> findCreateUserByLocation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", locationApplication.findCreateUserByLocation(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByLocation/{id}")
	public Map<String, Object> findLastModifyUserByLocation(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", locationApplication.findLastModifyUserByLocation(id));
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
