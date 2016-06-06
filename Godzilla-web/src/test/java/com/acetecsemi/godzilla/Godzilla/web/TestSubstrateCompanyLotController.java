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

import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferCompanyLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.SubstrateCompanyLotDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCompanyLotDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCustomerLotDTO;
import com.acetecsemi.godzilla.Godzilla.web.controller.core.WaferCustomerLotController;



public class TestSubstrateCompanyLotController extends KoalaBaseSpringTestCase { 
  
    @Inject
	private SubstrateCompanyLotApplication substrateCompanyLotApplication;

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
    	Long id = (long) 256;
    	Map<String, Object> result = new HashMap<String, Object>();
		SubstrateCompanyLotDTO substrateCompanyLotDTO = substrateCompanyLotApplication.findSubstrateCompanyLotByCustomerId(id);
		if(substrateCompanyLotDTO.getLotNo() == null || "".equals(substrateCompanyLotDTO.getLotNo())){
			substrateCompanyLotDTO = new SubstrateCompanyLotDTO();
			Date nowDate = new Date();
			substrateCompanyLotDTO.setCreateDate(nowDate);
			substrateCompanyLotDTO.setLastModifyDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount("1");
			substrateCompanyLotDTO.setOptUser(optUser);
			substrateCompanyLotDTO.setSubstrateCustomerLotId(id);
			substrateCompanyLotDTO.setLotNo(substrateCompanyLotApplication.getNewLotNo());
			substrateCompanyLotDTO = substrateCompanyLotApplication.saveSubstrateCompanyLot(substrateCompanyLotDTO);
		}		
		result.put("data", substrateCompanyLotDTO);
		
    } 
 }