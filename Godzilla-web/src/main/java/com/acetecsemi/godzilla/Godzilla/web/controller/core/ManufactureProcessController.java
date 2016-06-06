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

import com.acetecsemi.godzilla.Godzilla.application.core.BomListApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitWaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/ManufactureProcess")
public class ManufactureProcessController {

	@Inject
	private ManufactureProcessApplication manufactureProcessApplication;

	@Inject
	private ManufactureLotApplication manufactureLotApplication;

	@Inject
	private WaferProcessApplication waferProcessApplication;

	@Inject
	private SubstrateProcessApplication substrateProcessApplication;
	
	@Inject
	private BomListApplication bomListApplication;

	@Inject
	private UserApplication userApplication;
	
	@Inject
	private ManufactureDebitProcessApplication manufactureDebitProcessApplication;
	
	@Inject
	private ManufactureDebitWaferProcessApplication manufactureDebitWaferProcessApplication;
	private static final String		LOCAL			= Locale.getDefault()
			.toString();
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ManufactureProcessDTO manufactureProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureProcessApplication
				.saveManufactureProcess(manufactureProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(
			ManufactureProcessDTO manufactureProcessDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		manufactureProcessApplication
				.updateManufactureProcess(manufactureProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ManufactureProcessDTO manufactureProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<ManufactureProcessDTO> all = manufactureProcessApplication
				.pageQueryManufactureProcess(manufactureProcessDTO, page,
						pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/pageTotal")
	public Map<String, Object> pageTotal(
			ManufactureProcessDTO manufactureProcessDTO) {
		Map<String, Object> result = manufactureProcessApplication
				.pageQueryManufactureProcessTotal(manufactureProcessDTO);
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
		manufactureProcessApplication.removeManufactureProcesss(idArrs);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				manufactureProcessApplication.getManufactureProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCreateUserByManufactureProcess/{id}")
	public Map<String, Object> findCreateUserByManufactureProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication
				.findCreateUserByManufactureProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByManufactureProcess/{id}")
	public Map<String, Object> findLastModifyUserByManufactureProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication
				.findLastModifyUserByManufactureProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findStationByManufactureProcess/{id}")
	public Map<String, Object> findStationByManufactureProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication
				.findStationByManufactureProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLocationsByManufactureProcess/{id}")
	public Page findLocationsByManufactureProcess(@PathVariable Long id,
			@RequestParam int page, @RequestParam int pagesize) {
		Page<LocationDTO> all = manufactureProcessApplication
				.findLocationsByManufactureProcess(id, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/findEquipmentByManufactureProcess/{id}")
	public Map<String, Object> findEquipmentByManufactureProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication
				.findEquipmentByManufactureProcess(id));
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
	public Page receivePageJson(ManufactureProcessDTO manufactureProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		manufactureProcessDTO.setStationId((long) 102);
		Page<ManufactureProcessDTO> all = manufactureProcessApplication
				.pageQueryManufactureProcess(manufactureProcessDTO, page,
						pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/Process/pageJson/{id}")
	public Page PageJsonByStationId(@PathVariable Long id,
			ManufactureProcessDTO manufactureProcessDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		manufactureProcessDTO.setStationId(id);
		Page<ManufactureProcessDTO> all = manufactureProcessApplication
				.pageQueryManufactureProcess(manufactureProcessDTO, page,
						pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/Process/nextProcess/{id}")
	public Map<String, Object> nextProcess(@PathVariable Long id,
			@RequestParam String data,
			ManufactureProcessDTO manufactureProcessDTO)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setUseraccount(useraccount);
			// manufactureProcessDTO.setManufactureLotId(id);
			manufactureProcessDTO.setId(id);
			if (maps.get("startFlow") != null)
				manufactureProcessDTO.setStartFlow(Long.parseLong(maps.get(
						"startFlow").toString()));
			Map<String, Object> info = (Map<String, Object>) maps.get("data");
			if(info != null  && info.get("StripQtyOut") != null){
				Map<String, Object> debit = (Map<String, Object>) info.get("debit");
				String resCode = checkDebit(id,debit);
				if(!resCode.equals("success"))
				{
					result.put("actionError", I18NManager.getMessage(resCode, LOCAL));
					return result;
				}
				resCode = manufactureProcessApplication.nextProcessVerify(info, id);
				if(!resCode.equals("success"))
				{
					result.put("actionError", I18NManager.getMessage(resCode, LOCAL));
					return result;
				}
				manufactureProcessDTO.setStripQtyOut(Integer.parseInt(info.get("StripQtyOut").toString()));
				if(info.get("GoodQtyOut") != null)
					manufactureProcessDTO.setGoodQtyOut(Integer.parseInt(info.get("GoodQtyOut").toString()));
				if(info.get("ScrapsQtyOut") != null)
					manufactureProcessDTO.setScrapsQtyOut(Integer.parseInt(info.get("ScrapsQtyOut").toString()));
				float goodRate = (float)manufactureProcessDTO.getGoodQtyOut()/((float)manufactureProcessDTO.getGoodQtyOut() + (float)manufactureProcessDTO.getScrapsQtyOut());//计算良率
				if(goodRate < 0.99 && this.manufactureProcessApplication.checkOutSpecManufactureProcessTime(id) == 0){
					return this.outSpec(id, useraccount, "");
				}
				if(info.get("SampleQtyOut") != null)
					manufactureProcessDTO.setSampleQtyOut(Integer.parseInt(info.get("SampleQtyOut").toString()));
				List<Map<String,String>> rejectCodeItemMaps = (List<Map<String,String>>) info.get("rejectCode");
				List<RejectCodeItemDTO> rejectCodeItems = new ArrayList<RejectCodeItemDTO>();
				for(Map<String,String> rejectCodeItemMap : rejectCodeItemMaps){
					RejectCodeItemDTO rejectCodeItemDTO = new RejectCodeItemDTO();
					rejectCodeItemDTO.setItemName(rejectCodeItemMap.get("id").toString());
					if("".equals(rejectCodeItemMap.get("value").toString())){
						rejectCodeItemDTO.setQty(0);
					}else
						rejectCodeItemDTO.setQty(Integer.parseInt(rejectCodeItemMap.get("value").toString()));
					rejectCodeItemDTO.setCreateDate(nowDate);
					rejectCodeItemDTO.setLastModifyDate(nowDate);
					rejectCodeItems.add(rejectCodeItemDTO);
				}
				
				manufactureProcessDTO.setRejectCodeItemDTOs(rejectCodeItems);
				List<ManufactureDebitWaferProcessDTO> manufactureDebitWaferProcessDTOs = this.getMDWP(debit, nowDate, id);
				if(manufactureDebitWaferProcessDTOs.size() > 0 && manufactureDebitWaferProcessDTOs.get(0).getActionError() != null){
					result.put("actionError", I18NManager.getMessage(manufactureDebitWaferProcessDTOs.get(0).getActionError(), LOCAL));
					return result;
				}
				manufactureProcessDTO.setManufactureDebitProcessDTOs(this.getMDP(debit, nowDate, id));
				manufactureProcessDTO.setRejectCodeItemDTOs(rejectCodeItems);
			}
			manufactureProcessApplication
					.saveNextProcess(manufactureProcessDTO);
			result.put("data", "");
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	private List<ManufactureDebitProcessDTO> getMDP(Map<String, Object> info,Date nowDate,Long manufactureProcessId){
		List<ManufactureDebitProcessDTO> manufactureDebitProcessDTOs = new ArrayList<ManufactureDebitProcessDTO>();
		manufactureDebitProcessDTOs.addAll(this.getMDPByType(info, nowDate, manufactureProcessId, "directDebit"));
		manufactureDebitProcessDTOs.addAll(this.getMDPByType(info, nowDate, manufactureProcessId, "indirectDebit"));
		return manufactureDebitProcessDTOs;
	}
	
	private List<ManufactureDebitProcessDTO> getMDPByType(Map<String, Object> info,Date nowDate,Long manufactureProcessId,String type){
		List<Map<String,String>> materialMaps = (List<Map<String,String>>) info.get(type);
		List<ManufactureDebitProcessDTO> manufactureDebitProcessDTOs = new ArrayList<ManufactureDebitProcessDTO>();
		if(materialMaps != null){
			for(Map<String,String> manufactureDebitProcessMap : materialMaps){
				ManufactureDebitProcessDTO manufactureDebitProcessDTO = new ManufactureDebitProcessDTO();
				if(manufactureDebitProcessMap.get("percent") != null)
					manufactureDebitProcessDTO.setPercent(Double.valueOf(manufactureDebitProcessMap.get("percent").toString()));
				if(manufactureDebitProcessMap.get("qty") != null)
					manufactureDebitProcessDTO.setBalance(Double.valueOf(manufactureDebitProcessMap.get("qty").toString()));
				if(manufactureDebitProcessMap.get("balance") != null)
					manufactureDebitProcessDTO.setBalance(Double.valueOf(manufactureDebitProcessMap.get("balance").toString()));
				manufactureDebitProcessDTO.setMaterialProcessId(Long.parseLong(manufactureDebitProcessMap.get("lotId").toString()));
				manufactureDebitProcessDTO.setManufactureProcessId(manufactureProcessId);
				manufactureDebitProcessDTO.setCreateDate(nowDate);
				manufactureDebitProcessDTO.setLastModifyDate(nowDate);
				this.manufactureDebitProcessApplication.saveManufactureDebitProcess(manufactureDebitProcessDTO);
				manufactureDebitProcessDTOs.add(manufactureDebitProcessDTO);
			}
		}
		return manufactureDebitProcessDTOs;
	}
	
	private List<ManufactureDebitWaferProcessDTO> getMDWP(Map<String, Object> info,Date nowDate,Long manufactureProcessId){
		List<Map<String,String>> materialMaps = (List<Map<String,String>>) info.get("waferDebit");
		List<ManufactureDebitWaferProcessDTO> manufactureDebitProcessDTOs = new ArrayList<ManufactureDebitWaferProcessDTO>();
		if(materialMaps != null){
			for(Map<String,String> manufactureDebitWaferProcessMap : materialMaps){
				ManufactureDebitWaferProcessDTO manufactureDebitProcessDTO = new ManufactureDebitWaferProcessDTO();
				manufactureDebitProcessDTO.setQty(Integer.valueOf(manufactureDebitWaferProcessMap.get("qty").toString()));
				if(manufactureDebitWaferProcessMap.get("percent") != null)
					manufactureDebitProcessDTO.setPercent(Double.valueOf(manufactureDebitWaferProcessMap.get("percent").toString()));
				manufactureDebitProcessDTO.setWaferProcessId(Long.parseLong(manufactureDebitWaferProcessMap.get("lotId").toString()));
				manufactureDebitProcessDTO.setWaferInfoId(Long.parseLong(manufactureDebitWaferProcessMap.get("waferId").toString()));
				manufactureDebitProcessDTO.setManufactureProcessId(manufactureProcessId);
				manufactureDebitProcessDTO.setCreateDate(nowDate);
				manufactureDebitProcessDTO.setLastModifyDate(nowDate);
				manufactureDebitProcessDTO = this.manufactureDebitWaferProcessApplication.saveManufactureDebitWaferProcess(manufactureDebitProcessDTO);
				if(manufactureDebitProcessDTO.getActionError() != null){
					manufactureDebitProcessDTOs.add(manufactureDebitProcessDTO);
					return manufactureDebitProcessDTOs;
				}
				manufactureDebitProcessDTOs.add(manufactureDebitProcessDTO);
			}
		}
		return manufactureDebitProcessDTOs;
	}
	
	
	private Map<String, Object> outSpec(long manufactureProcessId,String useraccount,String comments){
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		Date nowDate = new Date();
		manufactureProcessDTO.setCreateDate(nowDate);
		manufactureProcessDTO.setLastModifyDate(nowDate);
		manufactureProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		manufactureProcessDTO.setUserDTO(optUser);
		manufactureProcessDTO.setId(manufactureProcessId);
		manufactureProcessApplication.outSpecManufactureProcess(manufactureProcessDTO, useraccount,
				comments);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "Out-Spec");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/getProcesses")
	public Map<String, Object> get(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idArray = ids.split(",");
		List<ManufactureProcessDTO> list = new ArrayList<ManufactureProcessDTO>();
		for (String id : idArray) {
			list.add(manufactureProcessApplication.getManufactureProcess(Long
					.parseLong(id)));
		}
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		manufactureProcessDTO.setUserDTO(optUser);
		manufactureProcessDTO = manufactureProcessApplication
				.getMergeManufactureProcess(manufactureProcessDTO, ids);
		result.put("data", list);
		result.put("mergeData", manufactureProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/split")
	public Map<String, Object> splitManufactureLot(@RequestParam String data)
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessDTO.setLocationIds(String.valueOf(splitInfo
					.get("locationIds")));
			String res = manufactureProcessApplication
					.saveSplitManufactureProcess(manufactureProcessDTO,
							useraccount,
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
	public Map<String, Object> getSplitManufactureLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"stripQty\":\"1\",\"qty\":\"100\",\"comments\":\"comments\",\"loctionIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> splitInfo = (Map<String, String>) maps.get("data");
		Long id = Long.parseLong(splitInfo.get("id"));
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		Date nowDate = new Date();
		manufactureProcessDTO.setCreateDate(nowDate);
		manufactureProcessDTO.setLastModifyDate(nowDate);
		manufactureProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		manufactureProcessDTO.setUserDTO(optUser);
		manufactureProcessDTO.setId(id);
		manufactureProcessDTO.setLocationIds(splitInfo.get("locationIds"));
		List<ManufactureProcessDTO> res = manufactureProcessApplication
				.getSplitManufactureProcess(manufactureProcessDTO,
						Integer.valueOf(splitInfo.get("stripQty")),
						Integer.valueOf(splitInfo.get("qty")));
		result.put("data", splitInfo);
		result.put("splitDate", res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/hold")
	public Map<String, Object> holdManufactureLot(@RequestParam String data)
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate;
			if (info.get("holdTime") != null) {
				nowDate = MyDateUtils.str2Date(info.get("holdTime").toString(),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			} else {
				nowDate = new Date();
			}
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessApplication.holdManufactureProcess(
					manufactureProcessDTO, holdCode, holdCodeId, useraccount,
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
	public Map<String, Object> engDispManufactureLot(@RequestParam String data)
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessApplication.engDispManufactureProcess(
					manufactureProcessDTO, holdCode, holdCodeId, useraccount,
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
	public Map<String, Object> mergeManufactureLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"566,570\",\"comments\":\"comments\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setUseraccount(useraccount);
			manufactureProcessDTO.setLocationIds(String.valueOf(maps
					.get("locationIds")));
			String res = manufactureProcessApplication
					.saveMergeManufactureProcess(manufactureProcessDTO,
							String.valueOf(maps.get("ids")), useraccount,
							String.valueOf(maps.get("comments")));
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getMerge")
	public Map<String, Object> getMergeManufactureLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"1129,1128\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = String.valueOf(maps.get("ids"));
		String[] idArray = ids.split(",");
		List<ManufactureProcessDTO> list = new ArrayList<ManufactureProcessDTO>();
		for (String id : idArray) {
			list.add(manufactureProcessApplication.getManufactureProcess(Long
					.parseLong(id)));
		}
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		manufactureProcessDTO.setUserDTO(optUser);
		manufactureProcessDTO = manufactureProcessApplication
				.getMergeManufactureProcess(manufactureProcessDTO, ids);
		result.put("data", list);
		result.put("mergeData", manufactureProcessDTO);
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessApplication.changeLotType(manufactureProcessDTO,
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
	public Map<String, Object> futureHoldManufactureLot(
			@RequestParam String data) throws JsonParseException,
			JsonMappingException, IOException {
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			if(info.get("holdTime") == null){
				nowDate = new Date();
			}else{
				nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			}
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessDTO.setFutureHoldStationId(Long.valueOf(info
					.get("futureStationId")));
			manufactureProcessApplication.futureHoldManufactureProcess(
					manufactureProcessDTO, holdCode, holdCodeId, useraccount,
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessApplication.changeLocations(
					manufactureProcessDTO, locationIds, useraccount,
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
	public Map<String, Object> trackInManufactureLot(@RequestParam String data)
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessDTO.setEquipmentId(Long.parseLong(info
					.get("equipmentId")));
			String res = manufactureProcessApplication.verifyEquipmentRunningStatusById(manufactureProcessDTO.getEquipmentId());
			if(!res.equalsIgnoreCase("success"))
			{
				result.put("actionError",I18NManager.getMessage(res, LOCAL));
				return result;
			}
			manufactureProcessApplication.trackIn(manufactureProcessDTO,
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
	public Map<String, Object> abortStepManufactureLot(@RequestParam String data)
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessApplication.abortStep(manufactureProcessDTO,
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
	public Map<String, Object> releaseManufactureLot(@RequestParam String data)
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
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			Date nowDate = new Date();
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			String engineerName =  String.valueOf(maps.get("engineerName"));
			if(engineerName != null){
				manufactureProcessDTO.setEngineerName(engineerName);
			}
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setId(id);
			manufactureProcessApplication.release(manufactureProcessDTO, useraccount, String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getHoldTime/{id}")
	public Map<String, Object> findOptLogByManufactureProcess(
			@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("holdTime", MyDateUtils.formaterDate(
				manufactureProcessApplication
						.findManufactureStatusOptLogByProcess(id,false)
						.getCreateDate(), MyDateUtils.formater));
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/start")
	public Map<String, Object> start(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		//String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"2458\",\"waferProcessId\":\"2446\"}}";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> map = (Map<String, String>) maps.get("data");
			Long substrateProcessId = Long.parseLong(map.get("id"));
			Long productId = Long.parseLong(map.get("productId"));
			Long customerId = Long.parseLong(map.get("customerId"));
			Long packSizeId = Long.parseLong(map.get("packSizeId"));
			ManufactureLotDTO manufactureLotDTO = new ManufactureLotDTO();
			if (manufactureLotDTO.getLotNo() == null
					|| "".equals(manufactureLotDTO.getLotNo())) {
				manufactureLotDTO = new ManufactureLotDTO();
				Date nowDate = new Date();
				manufactureLotDTO.setCreateDate(nowDate);
				manufactureLotDTO.setLastModifyDate(nowDate);
				UserDTO optUser = new UserDTO();
				optUser.setUserAccount(AuthUserUtil.getLoginUserName());
				manufactureLotDTO.setOptUser(optUser);
				manufactureLotDTO.setSubstrateProcessId(substrateProcessId);
				manufactureLotDTO.setProductId(productId);
				manufactureLotDTO.setPackSizeId(packSizeId);
				manufactureLotDTO.setCustomerId(customerId);
				manufactureLotDTO.setLotNo(manufactureLotApplication
						.getNewLotNo(customerId));
				manufactureLotDTO = manufactureLotApplication
						.saveManufactureLot(manufactureLotDTO);
				ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
				manufactureProcessDTO.setCreateDate(nowDate);
				manufactureProcessDTO.setLastModifyDate(nowDate);
				manufactureProcessDTO.setUserDTO(optUser);
				manufactureProcessDTO.setPackSizeId(packSizeId);
				if (map.get("startFlow") != null)
					manufactureProcessDTO.setStartFlow(Long.parseLong(map.get(
							"startFlow").toString()));
				manufactureProcessDTO.setComments(map.get("comments"));
				manufactureProcessDTO = manufactureProcessApplication
						.saveManufactureProcess(manufactureLotDTO.getId(),
								substrateProcessId, manufactureProcessDTO);
				result.put("data", manufactureProcessDTO);
			}
			result.put("result", "success");

		} else {
			result.put("data", "");
			result.put("result", "fail");
		}

		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getFutureStations/{id}")
	public Map<String, Object> getFutureStations(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication.findAfterStationsByProcessId(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getHoldReason/{id}")
	public Map<String, Object> getHoldReason(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication.findManufactureStatusOptLogByProcess(id,false));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getFutureHoldReason/{id}")
	public Map<String, Object> getFutureHoldReason(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", manufactureProcessApplication.findManufactureStatusOptLogByProcess(id,true));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getMaterialDebitList/{id}")
	public Map<String, Object> getMaterialDebitList(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		StationDTO stationDTO = manufactureProcessApplication.findStationByManufactureProcess(id);
		Map<String, List<SelectItemDTO>> map = bomListApplication.findMaterialByManuProcess(id,stationDTO.getId(),true);
		result.put("data", map);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/Process/semiStart")
	public Map<String, Object> semiStart(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		//String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"2458\",\"waferProcessId\":\"2446\"}}";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> map = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(map.get("id"));
			Long nextStationId = Long.parseLong(map.get("startFlow"));
			Date nowDate = new Date();
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount("admin");
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
			manufactureProcessDTO.setId(id);
			manufactureProcessDTO.setCreateDate(nowDate);
			manufactureProcessDTO.setLastModifyDate(nowDate);
			manufactureProcessDTO.setShippingDate(nowDate);
			manufactureProcessDTO.setUserDTO(optUser);
			manufactureProcessDTO.setNextStationId(nextStationId);
			manufactureProcessApplication
			.saveNextProcess(manufactureProcessDTO);
			result.put("data", manufactureProcessDTO);
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	private String checkDebit(Long manufactId,Map<String, Object> debitFromUI)
	{
		Map<String, Object> data = getMaterialDebitList(manufactId);
		Map<String,List<SelectItemDTO>> map = (Map<String,List<SelectItemDTO>>)data.get("data");
		if(map != null && map.size() > 0)
		{
			List<SelectItemDTO> direct = (List<SelectItemDTO>)map.get("direct");
			List<Map<String,String>> materialMaps = (List<Map<String,String>>) debitFromUI.get("directDebit");
			//如果从后台查到该站必须扣减材料，但是从前面传来的扣减信息为空，则提示必须扣减材料
			if((direct != null && direct.size() > 0) && !(materialMaps != null && materialMaps.size() > 0))
			{
				return "manufactureProcess.debit";
			}
			direct = (List<SelectItemDTO>)map.get("indirect");
			materialMaps = (List<Map<String,String>>) debitFromUI.get("indirectDebit");
			if((direct != null && direct.size() > 0) && !(materialMaps != null && materialMaps.size() > 0))
			{
				return "manufactureProcess.debit";
			}
			direct = (List<SelectItemDTO>)map.get("wafer");
			materialMaps = (List<Map<String,String>>) debitFromUI.get("waferDebit");
			if((direct != null && direct.size() > 0) && !(materialMaps != null && materialMaps.size() > 0))
			{
				return "manufactureProcess.debit";
			}
		}
		return "success";
	}
}
