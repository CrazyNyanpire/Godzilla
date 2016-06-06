package com.acetecsemi.godzilla.Godzilla.web;

import java.io.IOException;
import java.sql.SQLException;
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

import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.SubstrateProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.web.controller.core.WaferCustomerLotController;

public class TestSubstrateProcessController extends KoalaBaseSpringTestCase {

	@Inject
	private SubstrateProcessApplication substrateProcessApplication;

	@Inject
	private UserApplication userApplication;

	@Before
	public void before() throws SQLException {
	}

	@Test
	public void testSave() throws JsonParseException, JsonMappingException,
			IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":[{\"id\":\"256\",\"kucun\":\"1\"}]}";
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
				// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
				optUser.setUserAccount("1");
				substrateProcessDTO.setUserDTO(optUser);
				substrateProcessDTO.setSubstrateCustomerId(id);
				substrateProcessDTO.setLocationIds(locationIds);
				substrateProcessDTO = substrateProcessApplication
						.saveSubstrateProcessReceive(substrateProcessDTO, id,
								locationIds);
			}
			// result.put("data", waferProcessDTO);
			result.put("result", "success");
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
	public void testQuery() throws JsonParseException, JsonMappingException,
			IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		substrateProcessDTO.setStationId((long) 102);
		Page<SubstrateProcessDTO> all = substrateProcessApplication
				.pageQuerySubstrateProcess(substrateProcessDTO, 1, 10);
		result.put("data", all);
		result.put("result", "success");

	}

	@Test
	public void testNextProcess() throws JsonParseException,
			JsonMappingException, IOException {
		Long id = (long) 762;
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		Date nowDate = new Date();
		substrateProcessDTO.setCreateDate(nowDate);
		substrateProcessDTO.setLastModifyDate(nowDate);
		substrateProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("1");
		substrateProcessDTO.setUserDTO(optUser);
		substrateProcessDTO.setId((long) 762);
		substrateProcessApplication.saveNextProcess(substrateProcessDTO);
		result.put("data", "");
		result.put("result", "success");

	}

	@Test
	public void testSplitSubstrate() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"720\",\"stripQty\":\"1\",\"qty\":\"100\",\"comments\":\"comments\",\"loctionIds\":\"comments\"}}";
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
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			String res = substrateProcessApplication.saveSplitSubstrateProcess(
					substrateProcessDTO, useraccount,
					Integer.valueOf(splitInfo.get("stripQty")),
					Integer.valueOf(splitInfo.get("qty")),
					String.valueOf(splitInfo.get("comments")));
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testMergeSubstrate() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"717,720\",\"comments\":\"comments\"}";
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
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			String res = substrateProcessApplication.saveMergeSubstrateProcess(
					substrateProcessDTO, String.valueOf(maps.get("ids")),
					useraccount, String.valueOf(maps.get("comments")));
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testHoldSubstrate() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data =
		 "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"720\",\"holdCode\":\"RTV\",\"comments\":\"comments\",\"holdTime\":\"2014-08-20T12:00:00.000+0800\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			//optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.holdSubstrateProcess(
					substrateProcessDTO, holdCode, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testEngDispSubstrate() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"720\",\"holdCode\":\"RTV\",\"comments\":\"comments\",\"holdTime\":\"2014-08-20T12:00:00.000+0800\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password)) {
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			substrateProcessDTO.setCreateDate(nowDate);
			substrateProcessDTO.setLastModifyDate(nowDate);
			substrateProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			//optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			substrateProcessDTO.setUserDTO(optUser);
			substrateProcessDTO.setId(id);
			substrateProcessApplication.engDispSubstrateProcess(
					substrateProcessDTO, holdCode, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}
	
	@Test
	public void testQueryTotal() throws JsonParseException, JsonMappingException,
			IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		SubstrateProcessDTO substrateProcessDTO = new SubstrateProcessDTO();
		substrateProcessDTO.setStationId((long) 102);
		result = substrateProcessApplication
				.pageQuerySubstrateProcessTotal(substrateProcessDTO);
		result.put("data", result);
		result.put("result", "success");
	}

}