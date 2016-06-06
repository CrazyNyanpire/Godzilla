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
import org.junit.Before;
import org.junit.Test;
import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.acetecsemi.godzilla.Godzilla.application.core.MaterialProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.MaterialProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

public class TestMaterialProcessController extends KoalaBaseSpringTestCase {

	@Inject
	private MaterialProcessApplication materialProcessApplication;

	@Inject
	private UserApplication userApplication;

	@Before
	public void before() throws SQLException {
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
	public void testNextProcess() throws JsonParseException,
			JsonMappingException, IOException {
		Long id = (long) 762;
		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		Date nowDate = new Date();
		materialProcessDTO.setCreateDate(nowDate);
		materialProcessDTO.setLastModifyDate(nowDate);
		materialProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("1");
		materialProcessDTO.setUserDTO(optUser);
		materialProcessDTO.setId(id);
		materialProcessApplication.saveNextProcess(materialProcessDTO);
		result.put("data", "");
		result.put("result", "success");

	}

	@Test
	public void testSplitMaterial() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"720\",\"materialQty\":\"1\",\"comments\":\"comments\",\"locationIds\":\"1\"}}";
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
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessDTO.setLocationIds(splitInfo.get("locationIds"));
			String res = materialProcessApplication.saveSplitMaterialProcess(
					materialProcessDTO, useraccount,
					Integer.valueOf(splitInfo.get("materialQty")),
					String.valueOf(splitInfo.get("comments")));
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testMergeMaterial() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"717,720\",\"comments\":\"comments\"}";
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
			// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			String res = materialProcessApplication.saveMergeMaterialProcess(
					materialProcessDTO, String.valueOf(maps.get("ids")),
					useraccount, String.valueOf(maps.get("comments")));
			result.put("result", res);
		} else {
			result.put("data", "");
			result.put("result", "fail");
		}
	}

	@Test
	public void testHoldMaterial() throws JsonParseException,
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
			MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			materialProcessDTO.setCreateDate(nowDate);
			materialProcessDTO.setLastModifyDate(nowDate);
			materialProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			//optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			optUser.setUserAccount("admin");
			materialProcessDTO.setUserDTO(optUser);
			materialProcessDTO.setId(id);
			materialProcessApplication.holdMaterialProcess(
					materialProcessDTO, holdCode, useraccount,
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
		String ids = "1320,1322";
		String[] idArray = ids.split(",");
		List<MaterialProcessDTO> list = new ArrayList<MaterialProcessDTO>();
		for (String id : idArray) {
			list.add(materialProcessApplication.getMaterialProcess(Long.parseLong(id)));
		}
		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
		UserDTO optUser = new UserDTO();
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("admin");
		materialProcessDTO.setUserDTO(optUser);
		materialProcessDTO = materialProcessApplication.getMergeMaterialProcess(
				materialProcessDTO, ids);
		result.put("data", list);
		result.put("mergeData", materialProcessDTO);
		result.put("result", "success");
	}

	@Test
	public void testGetSplitLot() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"1283\",\"materialQty\":\"1\",\"comments\":\"comments\",\"locationIds\":\"1\"}}";
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
		// optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		optUser.setUserAccount("admin");
		materialProcessDTO.setUserDTO(optUser);
		materialProcessDTO.setId(id);
		materialProcessDTO.setLocationIds(splitInfo.get("locationIds"));
		List<MaterialProcessDTO> res = materialProcessApplication
				.getSplitMaterialProcess(materialProcessDTO,
						Integer.valueOf(splitInfo.get("materialQty")));
		result.put("data", splitInfo);
		result.put("splitDate", res);
	}
	
//	@Test
//	public void testQueryTotal() throws JsonParseException, JsonMappingException,
//			IOException {
//		Map<String, Object> result = new HashMap<String, Object>();
//		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
//		materialProcessDTO.setStationId((long) 102);
//		result = materialProcessApplication
//				.pageQueryMaterialProcessTotal(materialProcessDTO);
//		result.put("data", result);
//		result.put("result", "success");
//	}

}