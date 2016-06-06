package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.framework.i18n.I18NManager;
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

import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/SubstrateProcess")
public class SubstrateProcessController {

	@Inject
	private SubstrateProcessApplication substrateProcessApplication;

	@Inject
	private WaferProcessApplication waferProcessApplication;

	@Inject
	private UserApplication userApplication;

	private static final String		LOCAL			= Locale.getDefault().toString();
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(SubstrateProcessDTO substrateProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substrateProcessApplication.saveSubstrateProcess(substrateProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(SubstrateProcessDTO substrateProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		substrateProcessApplication.updateSubstrateProcess(substrateProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubstrateProcessDTO substrateProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<SubstrateProcessDTO> all = substrateProcessApplication
				.pageQuerySubstrateProcess(substrateProcessDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageTotal")
	public  Map<String, Object> pageTotal(SubstrateProcessDTO substrateProcessDTO) {
		Map<String, Object> result = substrateProcessApplication
				.pageQuerySubstrateProcessTotal(substrateProcessDTO);
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++) {

			idArrs[i] = Long.parseLong(value[i]);

		}
		substrateProcessApplication.removeSubstrateProcesss(idArrs);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateProcessApplication.getSubstrateProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCreateUserBySubstrateProcess/{id}")
	public Map<String, Object> findCreateUserBySubstrateProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateProcessApplication
				.findCreateUserBySubstrateProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserBySubstrateProcess/{id}")
	public Map<String, Object> findLastModifyUserBySubstrateProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateProcessApplication
				.findLastModifyUserBySubstrateProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findStationBySubstrateProcess/{id}")
	public Map<String, Object> findStationBySubstrateProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				substrateProcessApplication.findStationBySubstrateProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLocationsBySubstrateProcess/{id}")
	public Page findLocationsBySubstrateProcess(@PathVariable Long id,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<LocationDTO> all = substrateProcessApplication
				.findLocationsBySubstrateProcess(id, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/findEquipmentBySubstrateProcess/{id}")
	public Map<String, Object> findEquipmentBySubstrateProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				substrateProcessApplication.findEquipmentBySubstrateProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findSubstrateCompanyLotBySubstrateProcess/{id}")
	public Map<String, Object> findSubstrateCompanyLotBySubstrateProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateProcessApplication
				.findSubstrateCompanyLotBySubstrateProcess(id));
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
				SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
				Date nowDate = new Date();
				substrateProcessDTO.setCreateDate(nowDate);
				substrateProcessDTO.setLastModifyDate(nowDate);
				substrateProcessDTO.setShippingDate(nowDate);
				UserDTO optUser = new UserDTO();
				optUser.setUserAccount(AuthUserUtil.getLoginUserName());
				// optUser.setUserAccount("1");
				substrateProcessDTO.setUserDTO(optUser);
				substrateProcessDTO.setSubstrateCustomerId(id);
				substrateProcessDTO.setLocationIds(locationIds);
				substrateProcessDTO.setUseraccount(useraccount);
				substrateProcessDTO = substrateProcessApplication
						.saveSubstrateProcessReceive(substrateProcessDTO, id,
								locationIds);
			}
			// result.put("data", substrateProcessDTO);
			result.put("result", "success");
		} else {
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

	@ResponseBody
	@RequestMapping("/Receive/pageJson")
	public Page receivePageJson(SubstrateProcessDTO substrateProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		substrateProcessDTO.setStationId((long) 102);
		Page<SubstrateProcessDTO> all = substrateProcessApplication
				.pageQuerySubstrateProcess(substrateProcessDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/Process/pageJson/{id}")
	public Page PageJsonByStationId(@PathVariable Long id,
			SubstrateProcessDTO substrateProcessDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		substrateProcessDTO.setStationId(id);
		Page<SubstrateProcessDTO> all = substrateProcessApplication
				.pageQuerySubstrateProcess(substrateProcessDTO, page, pagesize);
		return all;
	}

	
	@ResponseBody
	@RequestMapping("/Process/nextProcess/{id}")
	public Map<String, Object> nextProcess(@PathVariable Long id,
			@RequestParam String data,SubstrateProcessDTO substrateProcessDTO) throws JsonParseException, JsonMappingException, IOException {
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
				substrateProcessDTO.setNextStationId(nextStationId);
			}
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setSubstrateCustomerLotId(id);
			substrateProcessDTO.setId(id);
			if(maps.get("startFlow") != null)
				substrateProcessDTO.setStartFlow(Long.parseLong(maps.get("startFlow").toString()));
			substrateProcessApplication.saveNextProcess(substrateProcessDTO);
			result.put("data", "");
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getProcesses")
	public Map<String, Object> get(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idArray = ids.split(",");
		List<SubstrateProcessDTO> list = new ArrayList<SubstrateProcessDTO>();
		for (String id : idArray) {
			list.add(substrateProcessApplication.getSubstrateProcess(Long.parseLong(id)));
		}
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		//optUser.setUserAccount("admin");
		substrateProcessDTO.setUserDTO(optUser);
		substrateProcessDTO = substrateProcessApplication.getMergeSubstrateProcess(substrateProcessDTO,ids);
		result.put("data", list);
		result.put("mergeData", substrateProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/split")
	public Map<String, Object> splitSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"stripQty\":\"1\",\"qty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessDTO.setLocationIds(String.valueOf(splitInfo.get("locationIds")));
			String res = substrateProcessApplication.saveSplitSubstrateProcess(
					substrateProcessDTO, useraccount,
					Integer.valueOf(splitInfo.get("stripQty")),
					Integer.valueOf(splitInfo.get("qty")),
					String.valueOf(splitInfo.get("comments")));
			if(res.equals("success"))
			{
				result.put("result", res);
			}
			else
			{
				result.put("actionError", I18NManager.getMessage(res, LOCAL));
			}
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getSplit")
	public Map<String, Object> getSplitSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"stripQty\":\"1\",\"qty\":\"100\",\"comments\":\"comments\",\"loctionIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> splitInfo = (Map<String, String>) maps.get("data");
		Long id = Long.parseLong(splitInfo.get("id"));
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		Date nowDate = new Date();
//		substrateProcessDTO.setCreateDate(nowDate);
		substrateProcessDTO.setLastModifyDate(nowDate);
//		substrateProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		substrateProcessDTO.setUserDTO(optUser);
		substrateProcessDTO.setId(id);
		substrateProcessDTO.setLocationIds(splitInfo.get("locationIds"));
		List<SubstrateProcessDTO> res = substrateProcessApplication
				.getSplitSubstrateProcess(substrateProcessDTO,
						Integer.valueOf(splitInfo.get("stripQty")),
						Integer.valueOf(splitInfo.get("qty")));
		result.put("data", splitInfo);
		result.put("splitDate", res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/hold")
	public Map<String, Object> holdSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate;
			if (info.get("holdTime") != null) {
				nowDate = MyDateUtils.str2Date(info.get("holdTime").toString(),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			} else {
				nowDate = new Date();
			}
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.holdSubstrateProcess(
					substrateProcessDTO, holdCode, holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/engDisp")
	public Map<String, Object> engDispSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.engDispSubstrateProcess(
					substrateProcessDTO, holdCode, holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/merge")
	public Map<String, Object> mergeSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"566,570\",\"comments\":\"comments\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setLocationIds(String.valueOf(maps.get("locationIds")));
			String res = substrateProcessApplication.saveMergeSubstrateProcess(
					substrateProcessDTO, String.valueOf(maps.get("ids")),
					useraccount, String.valueOf(maps.get("comments")));
			if(res.equals("success"))
			{
				result.put("result", res);
			}
			else
			{
				result.put("actionError", I18NManager.getMessage(res, LOCAL));
			}
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/getMerge")
	public Map<String, Object> getMergeSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		//data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"1129,1128\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = String.valueOf(maps.get("ids"));
		String[] idArray = ids.split(",");
		List<SubstrateProcessDTO> list = new ArrayList<SubstrateProcessDTO>();
		for (String id : idArray) {
			list.add(substrateProcessApplication.getSubstrateProcess(Long.parseLong(id)));
		}
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		//optUser.setUserAccount("admin");
		substrateProcessDTO.setUserDTO(optUser);
		substrateProcessDTO = substrateProcessApplication.getMergeSubstrateProcess(substrateProcessDTO,ids);
		result.put("data", list);
		result.put("mergeData", substrateProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/changeLotType")
	public Map<String, Object> changeLotType(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// String data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"lotTypeId\":\"RTV\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String lotType = splitInfo.get("lotTypeId").toString();
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.changeLotType(substrateProcessDTO,
					lotType, useraccount,
					String.valueOf(splitInfo.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/future")
	public Map<String, Object> futureHoldSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCode\":\"RTV\",\"futureStationId\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessDTO.setFutureHoldStationId(Long.valueOf(info
					.get("futureStationId")));
			substrateProcessApplication.holdSubstrateProcess(
					substrateProcessDTO, holdCode, holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getLotTypes")
	public Map<String, Object> getLotTypes() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication.getLotTypes());
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/changeLocations")
	public Map<String, Object> changeLocations(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// String data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"locationIds\":\"427,428\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String locationIds = splitInfo.get("locationIds").toString();
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.changeLocations(substrateProcessDTO,
					locationIds, useraccount,
					String.valueOf(maps.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getCusDisps")
	public Map<String, Object> getCusDisps() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.getResourceByParentIdentifier("CusDisp"));
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getEngDisps")
	public Map<String, Object> getEngDisps() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.getResourceByParentIdentifier("EngDisp"));
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/trackIn")
	public Map<String, Object> trackInSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessDTO.setEquipmentId( Long.parseLong(info.get("equipmentId")));
			substrateProcessApplication.trackIn(substrateProcessDTO,
					useraccount, String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/abortStep")
	public Map<String, Object> abortStepSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.abortStep(substrateProcessDTO,
					useraccount, String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/release")
	public Map<String, Object> releaseSubstrateLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCodeId\":\"RTV\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = new Date();
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.abortStep(substrateProcessDTO,
					useraccount, String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/getHoldTime/{id}")
	public Map<String, Object> findOptLogBySubstrateProcess(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("holdTime", MyDateUtils.formaterDate(substrateProcessApplication.findSubstrateStatusOptLogByProcess(id).getCreateDate(),MyDateUtils.formater));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getHoldReason/{id}")
	public Map<String, Object> getHoldReason(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateProcessApplication.findSubstrateStatusOptLogByProcess(id));
		return result;
	}
}
