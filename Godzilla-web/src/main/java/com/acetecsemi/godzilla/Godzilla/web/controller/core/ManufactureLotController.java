
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

import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/ManufactureLot")
public class ManufactureLotController {
		
	@Inject
	private ManufactureLotApplication manufactureLotApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ManufactureLotDTO manufactureLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureLotApplication.saveManufactureLot(manufactureLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(ManufactureLotDTO manufactureLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureLotApplication.updateManufactureLot(manufactureLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ManufactureLotDTO manufactureLotDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureLotDTO> all = manufactureLotApplication.pageQueryManufactureLot(manufactureLotDTO, page, pagesize);
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
        manufactureLotApplication.removeManufactureLots(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureLotApplication.getManufactureLot(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByManufactureLot/{id}")
	public Map<String, Object> findCreateUserByManufactureLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureLotApplication.findCreateUserByManufactureLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByManufactureLot/{id}")
	public Map<String, Object> findLastModifyUserByManufactureLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureLotApplication.findLastModifyUserByManufactureLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMergeManufactureLotByManufactureLot/{id}")
	public Map<String, Object> findMergeManufactureLotByManufactureLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureLotApplication.findMergeManufactureLotByManufactureLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findParentManufactureLotByManufactureLot/{id}")
	public Map<String, Object> findParentManufactureLotByManufactureLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureLotApplication.findParentManufactureLotByManufactureLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findManufactureLotsByManufactureLot/{id}")
	public Page findManufactureLotsByManufactureLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureLotDTO> all = manufactureLotApplication.findManufactureLotsByManufactureLot(id, page, pagesize);
		return all;
	}		
	@ResponseBody
	@RequestMapping("/findNowManufactureProcessByManufactureLot/{id}")
	public Map<String, Object> findNowManufactureProcessByManufactureLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureLotApplication.findNowManufactureProcessByManufactureLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findManufactureProcessesByManufactureLot/{id}")
	public Page findManufactureProcessesByManufactureLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureProcessDTO> all = manufactureLotApplication.findManufactureProcessesByManufactureLot(id, page, pagesize);
		return all;
	}		

	@ResponseBody
	@RequestMapping("/findWaferCompanyLotsByManufactureLot/{id}")
	public Page findWaferCompanyLotsByManufactureLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferCompanyLotDTO> all = manufactureLotApplication.findWaferCompanyLotsByManufactureLot(id, page, pagesize);
		return all;
	}		
	
	@ResponseBody
	@RequestMapping("/getNewLotNoByCustomerLot/{id}")
	public Map<String, Object> getNewLotNo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		//WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
		
		ManufactureLotDTO manufactureLotDTO = manufactureLotApplication.findManufactureLotByCompanyId(id);
		if(manufactureLotDTO.getLotNo() == null || "".equals(manufactureLotDTO.getLotNo())){
			manufactureLotDTO = new ManufactureLotDTO();
			Date nowDate = new Date();
			manufactureLotDTO.setCreateDate(nowDate);
			manufactureLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			manufactureLotDTO.setOptUser(optUser);
			manufactureLotDTO.setSubstrateCompanyLotId(id);
			//manufactureLotDTO.setLotNo(manufactureLotApplication.getNewLotNo(id));
			manufactureLotDTO = manufactureLotApplication.saveManufactureLot(manufactureLotDTO);
		}

		result.put("data", manufactureLotDTO);
		return result;
	}
	
}
