
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
import com.acetecsemi.godzilla.Godzilla.application.core.WaferInfoApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/WaferInfo")
public class WaferInfoController {
		
	@Inject
	private WaferInfoApplication waferInfoApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(WaferInfoDTO waferInfoDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferInfoApplication.saveWaferInfo(waferInfoDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(WaferInfoDTO waferInfoDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferInfoApplication.updateWaferInfo(waferInfoDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferInfoDTO waferInfoDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferInfoDTO> all = waferInfoApplication.pageQueryWaferInfo(waferInfoDTO, page, pagesize);
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
        waferInfoApplication.removeWaferInfos(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoApplication.getWaferInfo(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByWaferInfo/{id}")
	public Map<String, Object> findCreateUserByWaferInfo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoApplication.findCreateUserByWaferInfo(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByWaferInfo/{id}")
	public Map<String, Object> findLastModifyUserByWaferInfo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoApplication.findLastModifyUserByWaferInfo(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferCompanyLotByWaferInfo/{id}")
	public Map<String, Object> findWaferCompanyLotByWaferInfo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoApplication.findWaferCompanyLotByWaferInfo(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/getWaferInfo/{id}")
	public Map<String, Object> find(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferInfoApplication.findWaferInfoByWaferCompanyLotId(id));
		return result;
	}
	
}
