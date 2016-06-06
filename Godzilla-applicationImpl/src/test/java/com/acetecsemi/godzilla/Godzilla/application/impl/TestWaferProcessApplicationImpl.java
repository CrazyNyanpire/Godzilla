package com.acetecsemi.godzilla.Godzilla.application.impl;

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
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferProcessDTO;


public class TestWaferProcessApplicationImpl extends KoalaBaseSpringTestCase { 
  
    @Inject
	private WaferProcessApplication waferProcessApplication;

    @Before
    public void before() throws SQLException {
//        Statement stmt  = null;
//        try{
//            String create = " CREATE TABLE `Employee` ( `id` int(11) DEFAULT NULL, `name` varchar(255) DEFAULT NULL, `age` varchar(255) DEFAULT NULL, `birthDate` date DEFAULT NULL, `gender` varchar(255) DEFAULT NULL )";
//            DataSource dataSource = InstanceFactory.getInstance(DataSource.class);
//            stmt = dataSource.getConnection().createStatement();
//            stmt.execute(create);
//        }catch(Exception e){
//        }finally{
//            stmt.close();
//        }
    }

    @Test
    public void test(){ 
    	
    	WaferProcessDTO waferProcessDTO = waferProcessApplication.getWaferProcess((long)467);
    	System.out.println(waferProcessDTO.getCurrStatus());
    	//Assert.a.isTrue("1".equals(waferProcessDTO.getCustomerCode()));
    	//dayAttendanceConfirmationCreateApplication.createDayAttendanceConfirmation(0, 5);
    	//monthlyAttendanceConfirmationCreateApplication.createMonthlyAttendanceConfirmation(2014, 5);
    	//Assert.isTrue(pages.getPageCount()==2);
    	//Assert.isTrue(pages.getStart()==10);
    	//Assert.isTrue(pages.getData().size()==1);
    } 
    
    @Test
    public void testQuery(){ 
    	
    	WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
    	waferProcessDTO.setStationId((long) 1);	
    	Page<WaferProcessDTO> res = waferProcessApplication.pageQueryWaferProcess(waferProcessDTO, 0, 10);
    	Assert.isTrue(res.getData().size() > 0);
    	//dayAttendanceConfirmationCreateApplication.createDayAttendanceConfirmation(0, 5);
    	//monthlyAttendanceConfirmationCreateApplication.createMonthlyAttendanceConfirmation(2014, 5);
    	//Assert.isTrue(pages.getPageCount()==2);
    	//Assert.isTrue(pages.getStart()==10);
    	//Assert.isTrue(pages.getData().size()==1);
    } 
    
	@Test
	public void testSplitWafer() throws JsonParseException,
			JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String data = "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"469\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		//Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		//password = md5PasswordEncoder.encodePassword(password, "");
		Map<String, Object> result = new HashMap<String, Object>();
		//if (this.isUserExisted(useraccount)
		//		&& this.userCheck(useraccount, password)) {
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
			waferProcessApplication.saveSplitWaferProcess(
					waferProcessDTO, useraccount, Integer.valueOf(splitInfo.get("waferQty")),
					Integer.valueOf(splitInfo.get("dieQty")), String.valueOf(splitInfo.get("comments")), String.valueOf(splitInfo.get("locationIds")),null);
			//waferProcessApplication.saveSplitWaferProcess(waferProcessDTO);
			//result.put("data", list);
			result.put("result", "success");
		//} else {
		//	result.put("data", "");
		//	result.put("result", "fail");
		//}
	}
 }