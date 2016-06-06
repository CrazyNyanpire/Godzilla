
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
import com.acetecsemi.godzilla.Godzilla.application.core.RejectCodeItemApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/RejectCodeItem")
public class RejectCodeItemController {
		
	@Inject
	private RejectCodeItemApplication rejectCodeItemApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(RejectCodeItemDTO rejectCodeItemDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		rejectCodeItemApplication.saveRejectCodeItem(rejectCodeItemDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(RejectCodeItemDTO rejectCodeItemDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		rejectCodeItemApplication.updateRejectCodeItem(rejectCodeItemDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(RejectCodeItemDTO rejectCodeItemDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<RejectCodeItemDTO> all = rejectCodeItemApplication.pageQueryRejectCodeItem(rejectCodeItemDTO, page, pagesize);
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
        rejectCodeItemApplication.removeRejectCodeItems(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rejectCodeItemApplication.getRejectCodeItem(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByRejectCodeItem/{id}")
	public Map<String, Object> findCreateUserByRejectCodeItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rejectCodeItemApplication.findCreateUserByRejectCodeItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByRejectCodeItem/{id}")
	public Map<String, Object> findLastModifyUserByRejectCodeItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rejectCodeItemApplication.findLastModifyUserByRejectCodeItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findStationByRejectCodeItem/{id}")
	public Map<String, Object> findStationByRejectCodeItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rejectCodeItemApplication.findStationByRejectCodeItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findManufactureProcessByRejectCodeItem/{id}")
	public Map<String, Object> findManufactureProcessByRejectCodeItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rejectCodeItemApplication.findManufactureProcessByRejectCodeItem(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferProcessByRejectCodeItem/{id}")
	public Map<String, Object> findWaferProcessByRejectCodeItem(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", rejectCodeItemApplication.findWaferProcessByRejectCodeItem(id));
		return result;
	}

	
}
