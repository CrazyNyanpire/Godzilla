
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/Date")
public class DateController {
		
	@ResponseBody
	@RequestMapping("/nowDate")
	public Map<String, Object> nowDate() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("nowDateTime", MyDateUtils.formaterDate(new Date(),"EEE dd-MM-yyyy HH:mm",Locale.ENGLISH));
		return result;
	}
}
