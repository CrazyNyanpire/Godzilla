
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;

import com.acetecsemi.godzilla.Godzilla.application.core.CustomerApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Customer")
public class CustomerController {
		
	@Inject
	private CustomerApplication customerApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(CustomerDTO customerDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		customerApplication.saveCustomer(customerDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(CustomerDTO customerDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		customerApplication.updateCustomer(customerDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(CustomerDTO customerDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<CustomerDTO> all = customerApplication.pageQueryCustomer(customerDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	
        	        					idArrs[i] = Long.parseLong(value[i]);
						        	
        }
        customerApplication.removeCustomers(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", customerApplication.getCustomer(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByCustomer/{id}")
	public Map<String, Object> findCreateUserByCustomer(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", customerApplication.findCreateUserByCustomer(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByCustomer/{id}")
	public Map<String, Object> findLastModifyUserByCustomer(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", customerApplication.findLastModifyUserByCustomer(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllCustomers() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CustomerDTO> list = customerApplication.findAllCustomer();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
}
