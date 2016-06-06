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

import com.acetecsemi.godzilla.Godzilla.application.core.WaferCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.RejectCodeItemDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCompanyLotDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCustomerLotDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.web.controller.core.WaferCustomerLotController;

public class TestWaferProcessController extends KoalaBaseSpringTestCase {

	@Inject
	private WaferProcessApplication waferProcessApplication;

	@Inject
	private UserApplication userApplication;

	@Before
	public void before() throws SQLException {
		// Statement stmt = null;
		// try{
		// String create =
		// " CREATE TABLE `Employee` ( `id` int(11) DEFAULT NULL, `name` varchar(255) DEFAULT NULL, `age` varchar(255) DEFAULT NULL, `birthDate` date DEFAULT NULL, `gender` varchar(255) DEFAULT NULL )";
		// DataSource dataSource =
		// InstanceFactory.getInstance(DataSource.class);
		// stmt = dataSource.getConnection().createStatement();
		// stmt.execute(create);
		// }catch(Exception e){
		// }finally{
		// stmt.close();
		// }
	}

	@Test
	public void testSave() throws JsonParseException, JsonMappingException,
			IOException {
		Long id = (long) 351;
		String locationIds = "427";
		Map<String, Object> result = new HashMap<String, Object>();
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		Date nowDate = new Date();
		waferProcessDTO.setCreateDate(nowDate);
		waferProcessDTO.setLastModifyDate(nowDate);
		waferProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("1");
		waferProcessDTO.setUserDTO(optUser);

		waferProcessDTO.setWaferCustomerLotId(id);
		waferProcessDTO.setLocationIds(locationIds);
		//waferProcessApplication.saveWaferProcessReceive(waferProcessDTO, id,
		//		locationIds);
		result.put("data", "");
		result.put("result", "success");

	}

	@Test
	public void testQuery() throws JsonParseException, JsonMappingException,
			IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		waferProcessDTO.setStationId((long) 1);
		Page<WaferProcessDTO> all = waferProcessApplication
				.pageQueryWaferProcess(waferProcessDTO, 1, 10);
		result.put("data", all);
		result.put("result", "success");

	}

	@Test
	public void testNextProcess() throws JsonParseException,
			JsonMappingException, IOException {
		Long id = (long) 3290;
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"WaferQtyOut\":\"1\",\"GoodQtyOut\":\"1\",\"ScrapsQtyOut\":\"1\",\"rejectCode\":[{\"id\":\"DS01\",\"value\":\"1\"},{\"id\":\"DS02\",\"value\":\"1\"},{\"id\":\"DS03\",\"value\":\"\"},{\"id\":\"DS04\",\"value\":\"\"},{\"id\":\"DS05\",\"value\":\"\"},{\"id\":\"DS06\",\"value\":\"\"},{\"id\":\"DS07\",\"value\":\"\"},{\"id\":\"DS08\",\"value\":\"\"},{\"id\":\"DS09\",\"value\":\"\"},{\"id\":\"DS10\",\"value\":\"\"}]}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Long startFlow;
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, Object> info = (Map<String, Object>) maps.get("data");
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			//optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setWaferCustomerLotId(id);
			waferProcessDTO.setId(id);
			if(maps.get("startFlow") != null){
				startFlow = Long.parseLong(maps.get("startFlow").toString());
				waferProcessDTO.setStartFlow(startFlow);
			}
			if(info.get("WaferQtyOut") != null){
				waferProcessDTO.setWaferQtyOut(Integer.parseInt(info.get("WaferQtyOut").toString()));
				waferProcessDTO.setGoodQtyOut(Integer.parseInt(info.get("GoodQtyOut").toString()));
				waferProcessDTO.setScrapsQtyOut(Integer.parseInt(info.get("ScrapsQtyOut").toString()));
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
				waferProcessDTO.setRejectCodeItemDTOs(rejectCodeItems);
			}
			waferProcessApplication.saveNextProcess(waferProcessDTO);
			result.put("data", "");
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}

	}

	@Test
	public void testSplitWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"1129\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"427,428\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			String res = waferProcessApplication.saveSplitWaferProcess(
					waferProcessDTO, useraccount,
					Integer.valueOf(splitInfo.get("waferQty")),
					Integer.valueOf(splitInfo.get("dieQty")),
					String.valueOf(splitInfo.get("comments")),
					String.valueOf(splitInfo.get("locationIds")),null);
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testHoldWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCodeId\":\"\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate;
			if (info.get("holdTime") != null) {
				nowDate = MyDateUtils.str2Date(info.get("holdTime").toString(),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			} else {
				nowDate = new Date();
			}

			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.holdWaferProcess(waferProcessDTO, holdCode,
					holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testEngDispWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"holdCode\":\"RTV\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.engDispWaferProcess(waferProcessDTO,
					holdCode, holdCodeId, useraccount,
					String.valueOf(info.get("comments")),String.valueOf(info.get("engineerName")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testMergeWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"1129,1128\",\"comments\":\"comments\",\"locationIds\":\"427,428\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			String res = waferProcessApplication.saveMergeWaferProcess(
					waferProcessDTO, String.valueOf(maps.get("ids")),
					useraccount, String.valueOf(maps.get("comments")),
					String.valueOf(maps.get("locationIds")));
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
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
	public void testChangeLotTypeWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"lotType\":\"845\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String lotType = splitInfo.get("lotType").toString();
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.changeLotType(waferProcessDTO, lotType,
					useraccount, String.valueOf(splitInfo.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testChangeLocationWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"locationIds\":\"427,428\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String locationIds = splitInfo.get("locationIds").toString();
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.changeLocations(waferProcessDTO,
					locationIds, useraccount,
					String.valueOf(splitInfo.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testAbortStepWaferLot() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCodeId\":\"RTV\",\"holdCode\":\"RTV\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.abortStep(waferProcessDTO, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testGetMergeLot() throws JsonParseException,
			JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = "1129,1128";
		String[] idArray = ids.split(",");
		List<WaferProcessDTO> list = new ArrayList<WaferProcessDTO>();
		for (String id : idArray) {
			list.add(waferProcessApplication.getWaferProcess(Long.parseLong(id)));
		}
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		UserDTO optUser = new UserDTO();
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("admin");
		waferProcessDTO.setUserDTO(optUser);
		waferProcessDTO = waferProcessApplication.getMergeWaferProcess(
				waferProcessDTO, ids);
		result.put("data", list);
		result.put("mergeData", waferProcessDTO);
		result.put("result", "success");
	}

	@Test
	public void testGetSplitLot() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"1129\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> splitInfo = (Map<String, String>) maps.get("data");
		Long id = Long.parseLong(splitInfo.get("id"));
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		Date nowDate = new Date();
		waferProcessDTO.setCreateDate(nowDate);
		waferProcessDTO.setLastModifyDate(nowDate);
		waferProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("admin");
		waferProcessDTO.setUserDTO(optUser);
		waferProcessDTO.setId(id);
		List<WaferProcessDTO> res = waferProcessApplication
				.getSplitWaferProcess(waferProcessDTO,
						Integer.valueOf(splitInfo.get("waferQty")),
						Integer.valueOf(splitInfo.get("dieQty")));
		result.put("data", splitInfo);
		result.put("splitDate", res);
	}

	@Test
	public void testQueryTotal() throws JsonParseException, JsonMappingException,
			IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		waferProcessDTO.setStationId((long) 101);
		result = waferProcessApplication
				.pageQueryWaferProcessTotal(waferProcessDTO);
		result.put("data", result);
		result.put("result", "success");
	}
	
	@Test
	public void testGetFatureStations() throws JsonParseException, JsonMappingException,
			IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication.findAfterStationsByProcessId((long)1818));
		result.put("result", "success");
	}
}