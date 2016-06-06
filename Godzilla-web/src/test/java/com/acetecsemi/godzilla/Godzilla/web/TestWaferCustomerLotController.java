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
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.acetecsemi.godzilla.Godzilla.application.core.WaferCustomerLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCustomerLotDTO;
import com.acetecsemi.godzilla.Godzilla.web.controller.core.WaferCustomerLotController;



public class TestWaferCustomerLotController extends KoalaBaseSpringTestCase { 
  
    @Inject
	private WaferCustomerLotApplication waferCustomerLotApplication;

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
    	String data = "{\"customerId\":\"1\",\"customerLotNo\":\"customerLotNo\",\"productId\":\"1\",\"qty\":\"1\",\"firstPQty\":\"1\",\"secondPQty\":\"1\",\"thirdPQty\":\"1\",\"wafer\":\"1\",\"customerOrder\":\"231\",\"shippingDate\":\"2014-07-31T12:23:00.000+0800\",\"userName\":\"123\",\"remark\":\"123\",\"currStatus\":\"Waiting\"}";
    	Map<String, Object> result = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();  
		WaferCustomerLotDTO waferCustomerLotDTO = mapper.readValue(data, WaferCustomerLotDTO.class);  
		Date nowDate = new Date();
		waferCustomerLotDTO.setCreateDate(nowDate);
		waferCustomerLotDTO.setLastModifyDate(nowDate);
		waferCustomerLotApplication.saveWaferCustomerLot(waferCustomerLotDTO);
		result.put("result", "success");
    	//dayAttendanceConfirmationCreateApplication.createDayAttendanceConfirmation(0, 5);
    	//monthlyAttendanceConfirmationCreateApplication.createMonthlyAttendanceConfirmation(2014, 5);
    	//Assert.isTrue(pages.getPageCount()==2);
    	//Assert.isTrue(pages.getStart()==10);
    	//Assert.isTrue(pages.getData().size()==1);
    } 
 }