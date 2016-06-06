package com.acetecsemi.godzilla.Godzilla.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.openkoala.koala.util.KoalaBaseSpringTestCase;

import com.acetecsemi.godzilla.Godzilla.application.core.StationApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.EquipmentDTO;

public class TestStationController extends KoalaBaseSpringTestCase {

	@Inject
	private StationApplication stationApplication;

	@Before
	public void before() throws SQLException {
	}

	@Test
	public void testQuery() throws JsonParseException, JsonMappingException,
			IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		List<EquipmentDTO> list = stationApplication.findEquipmentsByStation((long) 401);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
	}
}