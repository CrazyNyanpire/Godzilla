
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
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitWaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/ManufactureDebitWaferProcess")
public class ManufactureDebitWaferProcessController {
		
	@Inject
	private ManufactureDebitWaferProcessApplication manufactureDebitWaferProcessApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureDebitWaferProcessApplication.saveManufactureDebitWaferProcess(manufactureDebitWaferProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureDebitWaferProcessApplication.updateManufactureDebitWaferProcess(manufactureDebitWaferProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ManufactureDebitWaferProcessDTO manufactureDebitWaferProcessDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureDebitWaferProcessDTO> all = manufactureDebitWaferProcessApplication.pageQueryManufactureDebitWaferProcess(manufactureDebitWaferProcessDTO, page, pagesize);
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
        manufactureDebitWaferProcessApplication.removeManufactureDebitWaferProcesss(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitWaferProcessApplication.getManufactureDebitWaferProcess(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByManufactureDebitWaferProcess/{id}")
	public Map<String, Object> findCreateUserByManufactureDebitWaferProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitWaferProcessApplication.findCreateUserByManufactureDebitWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByManufactureDebitWaferProcess/{id}")
	public Map<String, Object> findLastModifyUserByManufactureDebitWaferProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitWaferProcessApplication.findLastModifyUserByManufactureDebitWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findManufactureProcessByManufactureDebitWaferProcess/{id}")
	public Map<String, Object> findManufactureProcessByManufactureDebitWaferProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitWaferProcessApplication.findManufactureProcessByManufactureDebitWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferProcessByManufactureDebitWaferProcess/{id}")
	public Map<String, Object> findWaferProcessByManufactureDebitWaferProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitWaferProcessApplication.findWaferProcessByManufactureDebitWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferInfoByManufactureDebitWaferProcess/{id}")
	public Map<String, Object> findWaferInfoByManufactureDebitWaferProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureDebitWaferProcessApplication.findWaferInfoByManufactureDebitWaferProcess(id));
		return result;
	}

	
}
