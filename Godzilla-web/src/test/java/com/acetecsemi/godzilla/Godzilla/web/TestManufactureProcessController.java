package com.acetecsemi.godzilla.Godzilla.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dayatang.querychannel.Page;
import org.dayatang.utils.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureDebitWaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.ManufactureDebitProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.ManufactureDebitWaferProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.ManufactureLotDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.ManufactureProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.RejectCodeItemDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.SubstrateProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.web.controller.core.WaferCustomerLotController;

public class TestManufactureProcessController extends KoalaBaseSpringTestCase {

	@Inject
	private SubstrateProcessApplication substrateProcessApplication;
	
	@Inject
	private ManufactureProcessApplication manufactureProcessApplication;
	
	@Inject
	private ManufactureLotApplication manufactureLotApplication;
	
	@Inject
	private ManufactureDebitWaferProcessApplication manufactureDebitWaferProcessApplication;

	@Inject
	private ManufactureDebitProcessApplication manufactureDebitProcessApplication;
	
	@Inject
	private UserApplication userApplication;

	@Before
	public void before() throws SQLException {
	}

	@Test
	public void testSave() throws JsonParseException, JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"2458\",\"waferProcessId\":\"2446\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> map = (Map<String, String>) maps.get("data");
			Long substrateProcessId = Long.parseLong(map.get("id"));
			Long waferProcessId = Long.parseLong(map.get("waferProcessId"));
			Long customerId = Long.parseLong(map.get("customerId"));
			ManufactureLotDTO manufactureLotDTO = new ManufactureLotDTO();
			if (manufactureLotDTO.getLotNo() == null
					|| "".equals(manufactureLotDTO.getLotNo())) {
				manufactureLotDTO = new ManufactureLotDTO();
				Date nowDate = new Date();
				manufactureLotDTO.setCreateDate(nowDate);
				manufactureLotDTO.setLastModifyDate(nowDate);
				UserDTO optUser = new UserDTO();
				//optUser.setUserAccount(AuthUserUtil.getLoginUserName());
				optUser.setUserAccount("admin");
				manufactureLotDTO.setOptUser(optUser);
				manufactureLotDTO.setSubstrateProcessId(substrateProcessId);
				manufactureLotDTO.setLotNo(manufactureLotApplication
						.getNewLotNo(customerId));
				manufactureLotDTO = manufactureLotApplication
						.saveManufactureLot(manufactureLotDTO);
				ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
				manufactureProcessDTO.setCreateDate(nowDate);
				manufactureProcessDTO = manufactureProcessApplication
						.saveManufactureProcess(manufactureLotDTO.getId(),
								substrateProcessId, manufactureProcessDTO);
			}
			// result.put("data", substrateProcessDTO);
			result.put("result", "success");

		} else {
			result.put("data", "");
			result.put("result", "fail");
		}

	}
	
	@Test
	public void testNextProcess() throws JsonParseException, JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"StripQtyOut\":\"1\",\"DieQtyOut\":\"1\",\"rejectCode\":[{\"id\":\"DA01\",\"value\":\"1\"},{\"id\":\"DA02\",\"value\":\"1\"},{\"id\":\"DA03\",\"value\":\"0\"},{\"id\":\"DA04\",\"value\":\"0\"},{\"id\":\"DA05\",\"value\":\"0\"},{\"id\":\"DA06\",\"value\":\"0\"},{\"id\":\"DA07\",\"value\":\"0\"},{\"id\":\"DA08\",\"value\":\"0\"},{\"id\":\"DA09\",\"value\":\"0\"},{\"id\":\"DA10\",\"value\":\"0\"},{\"id\":\"DA11\",\"value\":\"0\"},{\"id\":\"DA12\",\"value\":\"0\"},{\"id\":\"DA13\",\"value\":\"0\"},{\"id\":\"DA14\",\"value\":\"0\"},{\"id\":\"DA15\",\"value\":\"0\"},{\"id\":\"DA16\",\"value\":\"0\"},{\"id\":\"DA17\",\"value\":\"0\"},{\"id\":\"DA18\",\"value\":\"0\"},{\"id\":\"DA19\",\"value\":\"0\"},{\"id\":\"DA20\",\"value\":\"0\"},{\"id\":\"DA21\",\"value\":\"0\"},{\"id\":\"DA22\",\"value\":\"0\"},{\"id\":\"DA23\",\"value\":\"0\"},{\"id\":\"DA24\",\"value\":\"0\"},{\"id\":\"DA25\",\"value\":\"0\"},{\"id\":\"DA26\",\"value\":\"0\"},{\"id\":\"DA27\",\"value\":\"0\"},{\"id\":\"DA28\",\"value\":\"0\"},{\"id\":\"DA29\",\"value\":\"0\"},{\"id\":\"DA30\",\"value\":\"0\"},{\"id\":\"DA31\",\"value\":\"0\"},{\"id\":\"DA32\",\"value\":\"0\"}],\"debit\":{\"waferDebit\":[{\"lotId\":\"2974\",\"waferId\":\"2936\",\"qty\":\"1\"},{\"lotId\":\"2974\",\"waferId\":\"2937\",\"qty\":\"333\"}],\"directDebit\":[],\"indirectDebit\":[]}}}";
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		Long id = (long)3178;
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
			//optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			manufactureProcessDTO.setUserDTO(optUser);
			// manufactureProcessDTO.setManufactureLotId(id);
			manufactureProcessDTO.setId(id);
			if (maps.get("startFlow") != null)
				manufactureProcessDTO.setStartFlow(Long.parseLong(maps.get(
						"startFlow").toString()));
			Map<String, Object> info = (Map<String, Object>) maps.get("data");
			if(info != null  && info.get("StripQtyOut") != null){
				manufactureProcessDTO.setStripQtyOut(Integer.parseInt(info.get("StripQtyOut").toString()));
				if(info.get("GoodQtyOut") != null)
					manufactureProcessDTO.setGoodQtyOut(Integer.parseInt(info.get("GoodQtyOut").toString()));
				if(info.get("ScrapsQtyOut") != null)
					manufactureProcessDTO.setScrapsQtyOut(Integer.parseInt(info.get("ScrapsQtyOut").toString()));
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
				Map<String, Object> debit = (Map<String, Object>) info.get("debit");
				manufactureProcessDTO.setRejectCodeItemDTOs(rejectCodeItems);
				manufactureProcessDTO.setManufactureDebitProcessDTOs(this.getMDP(debit, nowDate, id));
				this.getMDWP(debit, nowDate, id);
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
				manufactureDebitProcessDTO.setPercent(Double.valueOf(manufactureDebitProcessMap.get("percent").toString()));
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
				this.manufactureDebitWaferProcessApplication.saveManufactureDebitWaferProcess(manufactureDebitProcessDTO);
				manufactureDebitProcessDTOs.add(manufactureDebitProcessDTO);
			}
		}
		return manufactureDebitProcessDTOs;
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
	
	@Test
	public void semiStart() throws JsonParseException, JsonMappingException,
			IOException {
		String data = "{\"startFlow\":\"1101\",\"comments\":\"test\",\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"comments\":\"test\",\"startFlow\":\"1101\",\"id\":\"5187\"},\"id\":\"5187\"}";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> map = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(map.get("id"));
			Long nextStationId = Long.parseLong(map.get("startFlow"));
			ManufactureLotDTO manufactureLotDTO = new ManufactureLotDTO();
			if (manufactureLotDTO.getLotNo() == null
					|| "".equals(manufactureLotDTO.getLotNo())) {
				manufactureLotDTO = new ManufactureLotDTO();
				Date nowDate = new Date();
				manufactureLotDTO.setCreateDate(nowDate);
				manufactureLotDTO.setLastModifyDate(nowDate);
				UserDTO optUser = new UserDTO();
				optUser.setUserAccount("admin");
				manufactureLotDTO.setOptUser(optUser);;
				ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
				manufactureProcessDTO.setId(id);
				manufactureProcessDTO.setCreateDate(nowDate);
				manufactureProcessDTO.setLastModifyDate(nowDate);
				manufactureProcessDTO.setUserDTO(optUser);
				manufactureProcessDTO.setNextStationId(nextStationId);
				manufactureProcessApplication
				.saveNextProcess(manufactureProcessDTO);
				result.put("data", manufactureProcessDTO);
			}
			result.put("result", "success");

		} else {
			result.put("data", "");
			result.put("result", "fail");
		}

	}


}