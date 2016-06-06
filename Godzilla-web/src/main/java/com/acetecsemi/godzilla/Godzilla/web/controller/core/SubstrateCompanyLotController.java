
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

import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/SubstrateCompanyLot")
public class SubstrateCompanyLotController {
		
	@Inject
	private SubstrateCompanyLotApplication substrateCompanyLotApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(SubstrateCompanyLotDTO substrateCompanyLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substrateCompanyLotApplication.saveSubstrateCompanyLot(substrateCompanyLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(SubstrateCompanyLotDTO substrateCompanyLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substrateCompanyLotApplication.updateSubstrateCompanyLot(substrateCompanyLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubstrateCompanyLotDTO substrateCompanyLotDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SubstrateCompanyLotDTO> all = substrateCompanyLotApplication.pageQuerySubstrateCompanyLot(substrateCompanyLotDTO, page, pagesize);
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
        substrateCompanyLotApplication.removeSubstrateCompanyLots(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.getSubstrateCompanyLot(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserBySubstrateCompanyLot/{id}")
	public Map<String, Object> findCreateUserBySubstrateCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.findCreateUserBySubstrateCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserBySubstrateCompanyLot/{id}")
	public Map<String, Object> findLastModifyUserBySubstrateCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.findLastModifyUserBySubstrateCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findSubstrateCustomerLotBySubstrateCompanyLot/{id}")
	public Map<String, Object> findSubstrateCustomerLotBySubstrateCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.findSubstrateCustomerLotBySubstrateCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMargeSubstrateCompanyLotBySubstrateCompanyLot/{id}")
	public Map<String, Object> findMargeSubstrateCompanyLotBySubstrateCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.findMargeSubstrateCompanyLotBySubstrateCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findParentSubstrateCompanyLotBySubstrateCompanyLot/{id}")
	public Map<String, Object> findParentSubstrateCompanyLotBySubstrateCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.findParentSubstrateCompanyLotBySubstrateCompanyLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findSubstrateCompanyLotsBySubstrateCompanyLot/{id}")
	public Page findSubstrateCompanyLotsBySubstrateCompanyLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<SubstrateCompanyLotDTO> all = substrateCompanyLotApplication.findSubstrateCompanyLotsBySubstrateCompanyLot(id, page, pagesize);
		return all;
	}		
	@ResponseBody
	@RequestMapping("/findNowSubstrateProcessBySubstrateCompanyLot/{id}")
	public Map<String, Object> findNowSubstrateProcessBySubstrateCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCompanyLotApplication.findNowSubstrateProcessBySubstrateCompanyLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findSubstrateProcessesBySubstrateCompanyLot/{id}")
	public Page findSubstrateProcessesBySubstrateCompanyLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<SubstrateProcessDTO> all = substrateCompanyLotApplication.findSubstrateProcessesBySubstrateCompanyLot(id, page, pagesize);
		return all;
	}		
	
	@ResponseBody
	@RequestMapping("/getNewLotNoByCustomerLot/{id}")
	public Map<String, Object> getNewLotNo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		//WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
		
		SubstrateCompanyLotDTO substrateCompanyLotDTO = substrateCompanyLotApplication.findSubstrateCompanyLotByCustomerId(id);
		if(substrateCompanyLotDTO.getLotNo() == null || "".equals(substrateCompanyLotDTO.getLotNo())){
			substrateCompanyLotDTO = new SubstrateCompanyLotDTO();
			Date nowDate = new Date();
			substrateCompanyLotDTO.setCreateDate(nowDate);
			substrateCompanyLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			substrateCompanyLotDTO.setOptUser(optUser);
			substrateCompanyLotDTO.setSubstrateCustomerLotId(id);
			substrateCompanyLotDTO.setLotNo(substrateCompanyLotApplication.getNewLotNo());
			substrateCompanyLotDTO = substrateCompanyLotApplication.saveSubstrateCompanyLot(substrateCompanyLotDTO);
		}

		result.put("data", substrateCompanyLotDTO);
		return result;
	}
}
