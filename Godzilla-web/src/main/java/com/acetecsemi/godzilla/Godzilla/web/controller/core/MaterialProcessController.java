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

import com.acetecsemi.godzilla.Godzilla.application.core.MaterialProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/MaterialProcess")
public class MaterialProcessController {

	@Inject
	private MaterialProcessApplication materialProcessApplication;

	@Inject
	private UserApplication userApplication;
	private static final String		LOCAL			= Locale.getDefault().toString();
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(MaterialProcessDTO materialProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialProcessApplication.saveMaterialProcess(materialProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(MaterialProcessDTO materialProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		materialProcessApplication.updateMaterialProcess(materialProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/direct/pageJson")
	public Page directPageJson(MaterialProcessDTO materialProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		materialProcessDTO.setStationId(Long.valueOf(103));
		materialProcessDTO.setMaterialType(Integer.valueOf(1));
		Page<MaterialProcessDTO> all = materialProcessApplication
				.pageQueryMaterialProcess(materialProcessDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/indirect/pageJson")
	public Page indirectPageJson(MaterialProcessDTO materialProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		materialProcessDTO.setStationId(Long.valueOf(104));
		materialProcessDTO.setMaterialType(Integer.valueOf(2));
		Page<MaterialProcessDTO> all = materialProcessApplication
				.pageQueryMaterialProcess(materialProcessDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(MaterialProcessDTO materialProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialProcessDTO> all = materialProcessApplication
				.pageQueryMaterialProcess(materialProcessDTO, page, pagesize);
		return all;
	}
	@ResponseBody
	@RequestMapping("/pageTotal")
	public  Map<String, Object> pageTotal(MaterialProcessDTO materialProcessDTO) {
		Map<String, Object> result = materialProcessApplication
				.pageQueryMaterialProcessTotal(materialProcessDTO);
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
		materialProcessApplication.removeMaterialProcesss(idArrs);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialProcessApplication.getMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCreateUserByMaterialProcess/{id}")
	public Map<String, Object> findCreateUserByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				materialProcessApplication.findCreateUserByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByMaterialProcess/{id}")
	public Map<String, Object> findLastModifyUserByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialProcessApplication
				.findLastModifyUserByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findStationByMaterialProcess/{id}")
	public Map<String, Object> findStationByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				materialProcessApplication.findStationByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findEquipmentByMaterialProcess/{id}")
	public Map<String, Object> findEquipmentByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				materialProcessApplication.findEquipmentByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findResLotTypeByMaterialProcess/{id}")
	public Map<String, Object> findResLotTypeByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				materialProcessApplication.findResLotTypeByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findResHoldByMaterialProcess/{id}")
	public Map<String, Object> findResHoldByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				materialProcessApplication.findResHoldByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMaterialCompanyLotByMaterialProcess/{id}")
	public Map<String, Object> findMaterialCompanyLotByMaterialProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialProcessApplication
				.findMaterialCompanyLotByMaterialProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findMaterialLocationsByMaterialProcess/{id}")
	public Page findMaterialLocationsByMaterialProcess(@PathVariable Long id,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<MaterialLocationDTO> all = materialProcessApplication
				.findMaterialLocationsByMaterialProcess(id, page, pagesize);
		return all;
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
				MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
				Date nowDate = new Date();
				materialProcessDTO.setCreateDate(nowDate);
				materialProcessDTO.setLastModifyDate(nowDate);
				materialProcessDTO.setShippingDate(nowDate);
				UserDTO optUser = new UserDTO();
				optUser.setUserAccount(AuthUserUtil.getLoginUserName());
				// optUser.setUserAccount("1");
				materialProcessDTO.setUserDTO(optUser);
				materialProcessDTO.setMaterialLotId(id);
				materialProcessDTO.setLocationIds(locationIds);
				materialProcessDTO.setUseraccount(useraccount);
				materialProcessDTO = materialProcessApplication
						.saveMaterialProcessReceive(materialProcessDTO, id,
								locationIds);
			}
			// result.put("data", materialProcessDTO);
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	private boolean passwordCheck(String useraccount, String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password);
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

	@ResponseBody
	@RequestMapping("/Process/pageJson/{id}")
	public Page PageJsonByStationId(@PathVariable Long id,
			MaterialProcessDTO materialProcessDTO, @RequestParam int page,
			@RequestParam int pagesize) {
		materialProcessDTO.setStationId(id);
		Page<MaterialProcessDTO> all = materialProcessApplication
				.pageQueryMaterialProcess(materialProcessDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/Process/nextProcess/{id}")
	public Map<String, Object> nextProcess(@PathVariable Long id,
			@RequestParam String data,MaterialProcessDTO materialProcessDTO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Object currStatus = maps.get("currStatus");
		String comments = maps.get("comments")==null?"":maps.get("comments").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			if(maps.get("nextStationId") != null){
				Long nextStationId = Long.valueOf(maps.get("nextStationId").toString());
				materialProcessDTO.setNextStationId(nextStationId);
			}
			Date nowDate = new Date();
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setUseraccount(useraccount);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setComments(comments);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setMaterialLotId(id);
			materialProcessDTO.setId(id);
			if(currStatus != null){
				materialProcessDTO.setCurrStatus(currStatus.toString());
			}
			materialProcessApplication.saveNextProcess(materialProcessDTO);
			result.put("data", "");
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getMore")
	public Map<String, Object> get(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idArray = ids.split(",");
		List<MaterialProcessDTO> list = new ArrayList<MaterialProcessDTO>();
		for (String id : idArray) {
			list.add(materialProcessApplication.getMaterialProcess(Long
					.parseLong(id)));
		}
		result.put("data", list);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/split")
	public Map<String, Object> splitMaterialLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"materialQty\":\"1\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			Date nowDate = new Date();
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessDTO.setLocationIds(splitInfo.get("locationIds"));
			materialProcessDTO.setInCapacity(Double.parseDouble(splitInfo.get("inCapacity")));
			String res = materialProcessApplication.saveSplitMaterialProcess(
					materialProcessDTO, useraccount,
					Integer.valueOf(splitInfo.get("materialQty")),
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
	public Map<String, Object> getSplitMaterialLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"materialQty\":\"1\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> splitInfo = (Map<String, String>) maps.get("data");
		Long id = Long.parseLong(splitInfo.get("id"));
		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
		Date nowDate = new Date();
		materialProcessDTO.setCreateDate(nowDate);
		materialProcessDTO.setLastModifyDate(nowDate);
		materialProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		materialProcessDTO.setUserDTO(optUser);
		materialProcessDTO.setId(id);
		materialProcessDTO.setLocationIds(splitInfo.get("locationIds"));
		materialProcessDTO.setInCapacity(Double.parseDouble(splitInfo.get("inCapacity")));
		List<MaterialProcessDTO> res = materialProcessApplication
				.getSplitMaterialProcess(materialProcessDTO,
						Integer.valueOf(splitInfo.get("materialQty"))
						);
		result.put("data", splitInfo);
		result.put("splitDate", res);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/hold")
	public Map<String, Object> holdMaterialLot(@RequestParam String data)
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
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			Date nowDate;
			if (info.get("holdTime") != null) {
				nowDate = MyDateUtils.str2Date(info.get("holdTime").toString(),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			} else {
				nowDate = new Date();
			}

			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessApplication.holdMaterialProcess(materialProcessDTO,
					holdCode, holdCodeId, useraccount,
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
	public Map<String, Object> engDispMaterialLot(@RequestParam String data)
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
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessApplication.engDispMaterialProcess(
					materialProcessDTO, holdCode, holdCodeId, useraccount,
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
	public Map<String, Object> mergeMaterialLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"566,570\",\"comments\":\"comments\",\"locationIds\":\"comments\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			Date nowDate = new Date();
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setLocationIds(String.valueOf(maps.get("locationIds")));
			String res = materialProcessApplication.saveMergeMaterialProcess(
					materialProcessDTO, String.valueOf(maps.get("ids")),
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
	@RequestMapping("/Process/getProcess")
	public Map<String, Object> getMergeMaterialLot(@RequestParam String ids)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idArray = ids.split(",");
		List<MaterialProcessDTO> list = new ArrayList<MaterialProcessDTO>();
		for (String id : idArray) {
			list.add(materialProcessApplication.getMaterialProcess(Long.parseLong(id)));
		}
		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		//optUser.setUserAccount("admin");
		materialProcessDTO.setUserDTO(optUser);
		materialProcessDTO = materialProcessApplication.getMergeMaterialProcess(materialProcessDTO,ids);
		result.put("data", list);
		result.put("mergeData", materialProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/future")
	public Map<String, Object> futureHoldMaterialLot(@RequestParam String data)
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
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessDTO.setFutureHoldStationId(Long.valueOf(info
					.get("futureStationId")));
			materialProcessApplication.holdMaterialProcess(materialProcessDTO,
					holdCode, holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
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
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			Date nowDate = new Date();
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessApplication.changeLocations(materialProcessDTO,
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
	@RequestMapping("/Process/getHoldTime/{id}")
	public Map<String, Object> findWaferProcessByWaferOptLog(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("holdTime", MyDateUtils.formaterDate(materialProcessApplication.findMaterialStatusOptLogByProcess(id).getCreateDate(),MyDateUtils.formater));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/release")
	public Map<String, Object> releaseMaterialLot(@RequestParam String data)
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
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			Date nowDate = new Date();
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessApplication.abortStep(materialProcessDTO,
					useraccount, String.valueOf(maps.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getHoldReason/{id}")
	public Map<String, Object> getHoldReason(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialProcessApplication.findMaterialStatusOptLogByProcess(id));
		return result;
	}
}
