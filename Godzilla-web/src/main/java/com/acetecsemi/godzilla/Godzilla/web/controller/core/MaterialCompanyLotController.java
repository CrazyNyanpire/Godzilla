
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

import com.acetecsemi.godzilla.Godzilla.application.core.MaterialCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/MaterialCompanyLot")
public class MaterialCompanyLotController {
		
	@Inject
	private MaterialCompanyLotApplication materialCompanyLotApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MaterialCompanyLotDTO materialCompanyLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialCompanyLotApplication.saveMaterialCompanyLot(materialCompanyLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MaterialCompanyLotDTO materialCompanyLotDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialCompanyLotApplication.updateMaterialCompanyLot(materialCompanyLotDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MaterialCompanyLotDTO materialCompanyLotDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialCompanyLotDTO> all = materialCompanyLotApplication.pageQueryMaterialCompanyLot(materialCompanyLotDTO, page, pagesize);
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
        materialCompanyLotApplication.removeMaterialCompanyLots(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialCompanyLotApplication.getMaterialCompanyLot(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByMaterialCompanyLot/{id}")
	public Map<String, Object> findCreateUserByMaterialCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialCompanyLotApplication.findCreateUserByMaterialCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByMaterialCompanyLot/{id}")
	public Map<String, Object> findLastModifyUserByMaterialCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialCompanyLotApplication.findLastModifyUserByMaterialCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMargeMaterialCompanyLotByMaterialCompanyLot/{id}")
	public Map<String, Object> findMargeMaterialCompanyLotByMaterialCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialCompanyLotApplication.findMargeMaterialCompanyLotByMaterialCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findParentMaterialCompanyLotByMaterialCompanyLot/{id}")
	public Map<String, Object> findParentMaterialCompanyLotByMaterialCompanyLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialCompanyLotApplication.findParentMaterialCompanyLotByMaterialCompanyLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/getNewLotNoByCustomerLot/{id}")
	public Map<String, Object> getNewLotNo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		//MaterialCompanyLotDTO materialCompanyLotDTO = new MaterialCompanyLotDTO();
		
		MaterialCompanyLotDTO materialCompanyLotDTO = materialCompanyLotApplication.findMaterialCompanyLotByCustomerId(id);
		if(materialCompanyLotDTO.getId() == null || materialCompanyLotDTO.getId() == 0 || materialCompanyLotDTO.getLotNo() == null){
			materialCompanyLotDTO = new MaterialCompanyLotDTO();
			Date nowDate = new Date();
			materialCompanyLotDTO.setCreateDate(nowDate);
			materialCompanyLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			materialCompanyLotDTO.setOptUser(optUser);
			materialCompanyLotDTO.setMaterialLotId(id);
			materialCompanyLotDTO.setLotNo(materialCompanyLotApplication.getNewLotNo());
			materialCompanyLotDTO = materialCompanyLotApplication.saveMaterialCompanyLot(materialCompanyLotDTO);
		}
		result.put("data", materialCompanyLotDTO);
		return result;
	}
	
}
