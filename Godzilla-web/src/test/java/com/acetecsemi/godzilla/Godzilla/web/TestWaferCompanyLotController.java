package com.acetecsemi.godzilla.Godzilla.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.acetecsemi.godzilla.Godzilla.application.core.WaferCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCompanyLotDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCustomerLotDTO;
import com.acetecsemi.godzilla.Godzilla.web.controller.core.WaferCustomerLotController;



public class TestWaferCompanyLotController extends KoalaBaseSpringTestCase { 
  
    @Inject
	private WaferCompanyLotApplication waferCompanyLotApplication;

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
    public void test() throws JsonParseException, JsonMappingException, IOException{ 
    	Long id = (long) 351;
    	Map<String, Object> result = new HashMap<String, Object>();
		//WaferCompanyLotDTO waferCompanyLotDTO = new WaferCompanyLotDTO();
		WaferCompanyLotDTO waferCompanyLotDTO = waferCompanyLotApplication.findWaferCompanyLotByCustomerId(id);
		if(waferCompanyLotDTO.getLotNo() == null || "".equals(waferCompanyLotDTO.getLotNo())){
			waferCompanyLotDTO = new WaferCompanyLotDTO();
			Date nowDate = new Date();
			waferCompanyLotDTO.setCreateDate(nowDate);
			waferCompanyLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount("1");
			waferCompanyLotDTO.setOptUser(optUser);
			waferCompanyLotDTO.setWaferCustomerLotId(id);
			waferCompanyLotDTO.setLotNo(waferCompanyLotApplication.getNewLotNo(id));
			waferCompanyLotDTO = waferCompanyLotApplication.saveWaferCompanyLot(waferCompanyLotDTO);
		}		
		result.put("data", waferCompanyLotDTO);
		
    } 
 }