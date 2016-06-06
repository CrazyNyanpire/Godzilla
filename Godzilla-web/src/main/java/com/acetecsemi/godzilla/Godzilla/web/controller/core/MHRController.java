
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
import com.acetecsemi.godzilla.Godzilla.application.core.MHRApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/MHR")
public class MHRController {
		
	@Inject
	private MHRApplication mHRApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MHRDTO mHRDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		mHRApplication.saveMHR(mHRDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MHRDTO mHRDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		mHRApplication.updateMHR(mHRDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MHRDTO mHRDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MHRDTO> all = mHRApplication.pageQueryMHR(mHRDTO, page, pagesize);
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
        mHRApplication.removeMHRs(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", mHRApplication.getMHR(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByMHR/{id}")
	public Map<String, Object> findCreateUserByMHR(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", mHRApplication.findCreateUserByMHR(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByMHR/{id}")
	public Map<String, Object> findLastModifyUserByMHR(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", mHRApplication.findLastModifyUserByMHR(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/getNewMHRNo")
	public Map<String, Object> get() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", mHRApplication.getNewMHRNo());
		return result;
	}
}
