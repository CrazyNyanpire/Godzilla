
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.SpartPartApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.SpartPartProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/SpartPartProcess")
public class SpartPartProcessController {
		
	@Inject
	private SpartPartProcessApplication spartPartProcessApplication;
	
	@Inject
	private UserApplication userApplication;
	
	@Inject
	private SpartPartApplication spartPartApplication;
	
	@Inject
	private WaferProcessApplication waferProcessApplication;
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(SpartPartProcessDTO spartPartProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		spartPartProcessApplication.saveSpartPartProcess(spartPartProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(SpartPartProcessDTO spartPartProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		spartPartProcessApplication.updateSpartPartProcess(spartPartProcessDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SpartPartProcessDTO spartPartProcessDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SpartPartProcessDTO> all = spartPartProcessApplication.pageQuerySpartPartProcess(spartPartProcessDTO, page, pagesize);
		return all;
	}
	@ResponseBody
	@RequestMapping("/pageTotal")
	public Map<String, Object> pageTotal(SpartPartProcessDTO spartPartProcessDTO)
	{
		Map<String, Object> result = spartPartProcessApplication.pageQuerySpartPartProcessTotal(spartPartProcessDTO);
		return result;
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
        spartPartProcessApplication.removeSpartPartProcesss(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", spartPartProcessApplication.getSpartPartProcess(id));
		return result;
	}
		
	@ResponseBody
	@RequestMapping("/Process/getRepair")
	public Map<String, Object> getRepair()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.getResourceByParentIdentifier("Repairing"));
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/getScrap")
	public Map<String, Object> getScrap()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.getResourceByParentIdentifier("Scraped"));
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Receive/save")
	public Map<String, Object> receive(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			List<Map<String, String>> lots = (List<Map<String, String>>) maps
					.get("data");
			for (Map<String, String> map : lots) {
				Long id = Long.parseLong(map.get("id"));
				String locationIds = map.get("kucun");
				SpartPartProcessDTO spartPartProcessDTO = new SpartPartProcessDTO();
				Date nowDate = new Date();
				spartPartProcessDTO.setCreateDate(nowDate);
				spartPartProcessDTO.setLastModifyDate(nowDate);
				spartPartProcessDTO.setShippingDate(nowDate);
				UserDTO optUser = new UserDTO();
				optUser.setUserAccount(AuthUserUtil.getLoginUserName());
				// optUser.setUserAccount("1");
				spartPartProcessDTO.setUserDTO(optUser);
				SpartPartDTO spartPartDTO = new SpartPartDTO();
				spartPartDTO.setId(id);
				spartPartProcessDTO.setSpartPartDTO(spartPartDTO);
				spartPartProcessDTO.setLocationIds(locationIds);
				spartPartProcessDTO.setUseraccount(useraccount);
				spartPartProcessDTO.setSpartPartCompanyLotDTO(spartPartApplication.getSpartPartCompanyLotBySpartPart(id));
				spartPartProcessDTO = this.spartPartProcessApplication
						.saveSpartPartProcessReceive(spartPartProcessDTO, id, locationIds);
			}
			// result.put("data", spartPartProcessDTO);
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}


	
	@ResponseBody
	@RequestMapping("/Process/pageJson/{id}")
	public Page PageJsonByStationId(@PathVariable Long id,
			SpartPartProcessDTO spartPartProcessDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		spartPartProcessDTO.setStationId(id);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		spartPartProcessDTO.setUserDTO(optUser);
		Page<SpartPartProcessDTO> all = spartPartProcessApplication
				.pageQuerySpartPartProcess(spartPartProcessDTO, page, pagesize);
		return all;
	}

	
	@ResponseBody
	@RequestMapping("/Process/nextProcess/{id}")
	public Map<String, Object> nextProcess(@PathVariable Long id,
			@RequestParam String data,SpartPartProcessDTO spartPartProcessDTO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			if(maps.get("nextStationId") != null){
				Long nextStationId = Long.valueOf(maps.get("nextStationId").toString());
				spartPartProcessDTO.setNextStationId(nextStationId);
			}
			Date nowDate = new Date();
			spartPartProcessDTO.setCreateDate(nowDate);
			spartPartProcessDTO.setLastModifyDate(nowDate);
			spartPartProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			spartPartProcessDTO.setUserDTO(optUser);
			spartPartProcessDTO.setId(id);
			if(maps.get("startFlow") != null)
				spartPartProcessDTO.setStartFlow(Long.parseLong(maps.get("startFlow").toString()));
			spartPartProcessApplication.saveNextProcess(spartPartProcessDTO);
			result.put("data", "");
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/getNewLotNo/{id}")
	public Map<String, Object> getNewLotNo(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		SpartPartCompanyLotDTO spartPartCompanyLotDTO = spartPartApplication.getSpartPartCompanyLotBySpartPart(id);
		
//		SpartPartDTO spartPartDTO = spartPartApplication.getSpartPart(id);
		if(spartPartCompanyLotDTO == null || spartPartCompanyLotDTO.getId() == null || spartPartCompanyLotDTO.getLotNo() == null){
			Date nowDate = new Date();
			spartPartCompanyLotDTO = new SpartPartCompanyLotDTO();
			spartPartCompanyLotDTO.setCreateDate(nowDate);
			spartPartCompanyLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			spartPartCompanyLotDTO.setOptUser(optUser);
			spartPartCompanyLotDTO.setSpartPartId(id);
			spartPartCompanyLotDTO.setLotNo(spartPartApplication.getNewLotNo());
			spartPartCompanyLotDTO = spartPartApplication.saveSpartPartCompanyLot(spartPartCompanyLotDTO);
		}
		result.put("data", spartPartCompanyLotDTO);
		return result;
	}
	@ResponseBody
	@RequestMapping("/Process/changeLocations")
	public Map<String, Object> changeLocations(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// String data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"locationIds\":\"427,428\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String locationIds = splitInfo.get("locationIds").toString();
			SpartPartProcessDTO spartPartProcessDTO = new SpartPartProcessDTO();
			Date nowDate = new Date();
//			spartPartProcessDTO.setCreateDate(nowDate);
			spartPartProcessDTO.setLastModifyDate(nowDate);
//			spartPartProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			spartPartProcessDTO.setUserDTO(optUser);
			spartPartProcessDTO.setId(id);
			spartPartProcessApplication.changeLocations(spartPartProcessDTO,
					locationIds, useraccount,
					String.valueOf(maps.get("comment")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/hold")
	public Map<String, Object> changeStatus(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCodeId\":\"RTV\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			SpartPartProcessDTO spartPartProcessDTO = new SpartPartProcessDTO();
			Date nowDate;
			if (info.get("holdTime") != null)
			{
				nowDate = MyDateUtils.str2Date(info.get("holdTime").toString(),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			}
			else
			{
				nowDate = new Date();
			}

			spartPartProcessDTO.setCreateDate(nowDate);
			spartPartProcessDTO.setLastModifyDate(nowDate);
			spartPartProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			spartPartProcessDTO.setUserDTO(optUser);
			spartPartProcessDTO.setId(id);
			spartPartProcessDTO.setUseraccount(useraccount);
			spartPartProcessApplication.changeStatus(spartPartProcessDTO, holdCode,
					holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	private boolean isUserExisted(String useraccount) {
		UserVO result = userApplication.findByUserAccount(useraccount);
		return result == null ? false : true;
	}

	private boolean userCheck(String useraccount, String password) {
		UserVO result = userApplication.findByUserAccount(useraccount);
		if (result == null) {
			return false;
		}
		if (result.getUserPassword().equals(password)) {
			return true;
		}
		return false;
	}

	private boolean passwordCheck(String useraccount, String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password);
	}
	
}
