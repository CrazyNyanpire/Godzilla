package com.acetecsemi.godzilla.Godzilla.application.impl;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.dayatang.utils.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.acetecsemi.godzilla.Godzilla.application.core.StationApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.EquipmentDTO;


public class TestStationApplicationImpl extends KoalaBaseSpringTestCase { 
  
    @Inject
	private StationApplication stationApplication;

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
    	List<EquipmentDTO> equipmentList = stationApplication.findEquipmentsByStation((long)401);
    	System.out.println(equipmentList.size());
    	Assert.isTrue(equipmentList.size()==1);
    	//Assert.isTrue(pages.getStart()==10);
    	//Assert.isTrue(pages.getData().size()==1);
    } 
 }