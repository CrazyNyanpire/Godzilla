
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.koala.auth.core.domain.User;
import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.WaferCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.core.WaferCompanyLot;

@Controller
@RequestMapping("/WaferCompanyLot")
public class WaferCompanyLotController {
		
	@Inject
	private WaferCompanyLotApplication waferCompanyLotApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(WaferCompanyLotDTO waferCompanyLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferCompanyLotApplication.saveWaferCompanyLot(waferCompanyLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(WaferCompanyLotDTO waferCompanyLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		waferCompanyLotApplication.updateWaferCompanyLot(waferCompanyLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferCompanyLotDTO waferCompanyLotDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferCompanyLotDTO> all = waferCompanyLotApplication.pageQueryWaferCompanyLot(waferCompanyLotDTO, page, pagesize);
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
        waferCompanyLotApplication.removeWaferCompanyLots(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.getWaferCompanyLot(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByWaferCompanyLot/{id}")
	public Map<String, Object> findCreateUserByWaferCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.findCreateUserByWaferCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByWaferCompanyLot/{id}")
	public Map<String, Object> findLastModifyUserByWaferCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.findLastModifyUserByWaferCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferCustomerLotByWaferCompanyLot/{id}")
	public Map<String, Object> findWaferCustomerLotByWaferCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.findWaferCustomerLotByWaferCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMargeWaferCompanyLotByWaferCompanyLot/{id}")
	public Map<String, Object> findMargeWaferCompanyLotByWaferCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.findMargeWaferCompanyLotByWaferCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findParentWaferCompanyLotByWaferCompanyLot/{id}")
	public Map<String, Object> findParentWaferCompanyLotByWaferCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.findParentWaferCompanyLotByWaferCompanyLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findWaferCompanyLotsByWaferCompanyLot/{id}")
	public Page findWaferCompanyLotsByWaferCompanyLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferCompanyLotDTO> all = waferCompanyLotApplication.findWaferCompanyLotsByWaferCompanyLot(id, page, pagesize);
		return all;
	}		
	@ResponseBody
	@RequestMapping("/findNowProcessByWaferCompanyLot/{id}")
	public Map<String, Object> findNowProcessByWaferCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCompanyLotApplication.findNowProcessByWaferCompanyLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findProcessesByWaferCompanyLot/{id}")
	public Page findProcessesByWaferCompanyLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferProcessDTO> all = waferCompanyLotApplication.findProcessesByWaferCompanyLot(id, page, pagesize);
		return all;
	}		
	
	@ResponseBody
	@RequestMapping("/getNewLotNoByCustomerLot/{id}")
	public Map<String, Object> getNewLotNo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		//WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
		
		WaferCompanyLotDTO waferCompanyLotDTO = waferCompanyLotApplication.findWaferCompanyLotByCustomerId(id);
		if(waferCompanyLotDTO.getLotNo() == null || "".equals(waferCompanyLotDTO.getLotNo())){
			waferCompanyLotDTO = new WaferCompanyLotDTO();
			Date nowDate = new Date();
			waferCompanyLotDTO.setCreateDate(nowDate);
			waferCompanyLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			waferCompanyLotDTO.setOptUser(optUser);
			waferCompanyLotDTO.setWaferCustomerLotId(id);
			waferCompanyLotDTO.setLotNo(waferCompanyLotApplication.getNewLotNo(id));
			waferCompanyLotDTO = waferCompanyLotApplication.saveWaferCompanyLot(waferCompanyLotDTO);
		}

		result.put("data", waferCompanyLotDTO);
		return result;
	}
	
}
