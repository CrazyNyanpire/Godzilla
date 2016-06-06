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

import com.acetecsemi.godzilla.Godzilla.application.core.DefineStationProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.StationDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.UserDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferProcessDTO;


public class TestDefineStationProcessApplicationImpl extends KoalaBaseSpringTestCase { 
  
    @Inject
	private DefineStationProcessApplication defineStationProcessApplication;

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
    	
    	Page<StationDTO>  page = defineStationProcessApplication.findStationsByDefineStationProcess(Long.valueOf(1), 0, 100);
    	System.out.println(page.getData().size());
    	//Assert.a.isTrue("1".equals(waferProcessDTO.getCustomerCode()));
    	//dayAttendanceConfirmationCreateApplication.createDayAttendanceConfirmation(0, 5);
    	//monthlyAttendanceConfirmationCreateApplication.createMonthlyAttendanceConfirmation(2014, 5);
    	//Assert.isTrue(pages.getPageCount()==2);
    	//Assert.isTrue(pages.getStart()==10);
    	//Assert.isTrue(pages.getData().size()==1);
    } 
    
    @Test
    public void testQuery(){ 
    	
    } 
    
 }