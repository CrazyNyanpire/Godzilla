
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.PackApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Pack")
public class PackController {
		
	@Inject
	private PackApplication packApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(PackDTO packDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date date = new Date();
		packDTO.setCreateDate(date);
		packDTO.setLastModifyDate(date);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		packDTO.setUser(optUser);
		packApplication.savePack(packDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(PackDTO packDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		packApplication.updatePack(packDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(PackDTO packDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<PackDTO> all = packApplication.pageQueryPack(packDTO, page, pagesize);
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
        packApplication.removePacks(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", packApplication.getPack(id));
		return result;
	}
	
//		@ResponseBody
//	@RequestMapping("/findCreateUserByPack/{id}")
//	public Map<String, Object> findCreateUserByPack(@PathVariable Long id) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("data", packApplication.findCreateUserByPack(id));
//		return result;
//	}
//
//	@ResponseBody
//	@RequestMapping("/findLastModifyUserByPack/{id}")
//	public Map<String, Object> findLastModifyUserByPack(@PathVariable Long id) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("data", packApplication.findLastModifyUserByPack(id));
//		return result;
//	}

	@ResponseBody
	@RequestMapping("/findProductByPack/{id}")
	public Map<String, Object> findProductByPack(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", packApplication.findProductByPack(id));
		return result;
	}

	
}
