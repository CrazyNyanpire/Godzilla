
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.core.DefineStationProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/DefineStationProcess")
public class DefineStationProcessController {
		
	@Inject
	private DefineStationProcessApplication defineStationProcessApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(DefineStationProcessDTO defineStationProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		defineStationProcessApplication.saveDefineStationProcess(defineStationProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(DefineStationProcessDTO defineStationProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		defineStationProcessApplication.updateDefineStationProcess(defineStationProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(DefineStationProcessDTO defineStationProcessDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<DefineStationProcessDTO> all = defineStationProcessApplication.pageQueryDefineStationProcess(defineStationProcessDTO, page, pagesize);
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
        defineStationProcessApplication.removeDefineStationProcesss(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", defineStationProcessApplication.getDefineStationProcess(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByDefineStationProcess/{id}")
	public Map<String, Object> findCreateUserByDefineStationProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", defineStationProcessApplication.findCreateUserByDefineStationProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByDefineStationProcess/{id}")
	public Map<String, Object> findLastModifyUserByDefineStationProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", defineStationProcessApplication.findLastModifyUserByDefineStationProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferCustomerLotByDefineStationProcess/{id}")
	public Map<String, Object> findWaferCustomerLotByDefineStationProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", defineStationProcessApplication.findWaferCustomerLotByDefineStationProcess(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findStationsByDefineStationProcess/{id}")
	public Page findStationsByDefineStationProcess(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<StationDTO> all = defineStationProcessApplication.findStationsByDefineStationProcess(id, page, pagesize);
		return all;
	}		
	
	@ResponseBody
	@RequestMapping("/findDefineStationProcessesByType/{id}")
	public Map<String, Object> findDefineStationProcessesByType(@PathVariable Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", defineStationProcessApplication.findDefineStationProcessByType(id));
		return result;
	}
	
}
