
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

import com.acetecsemi.godzilla.Godzilla.application.core.ProductApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/Product")
public class ProductController {
		
	@Inject
	private ProductApplication productApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ProductDTO productDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		productApplication.saveProduct(productDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(ProductDTO productDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		productApplication.updateProduct(productDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(ProductDTO productDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<ProductDTO> all = productApplication.pageQueryProduct(productDTO, page, pagesize);
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
        productApplication.removeProducts(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", productApplication.getProduct(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByProduct/{id}")
	public Map<String, Object> findCreateUserByProduct(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", productApplication.findCreateUserByProduct(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByProduct/{id}")
	public Map<String, Object> findLastModifyUserByProduct(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", productApplication.findLastModifyUserByProduct(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCustomerByProduct/{id}")
	public Map<String, Object> findCustomerByProduct(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", productApplication.findCustomerByProduct(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/all")
	public Map<String, Object> findAllProducts() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProductDTO> list = productApplication.findAllProduct();
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findProductByCustomerId/{id}")
	public Map<String, Object> findProductByCustomerId(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ProductDTO> list = productApplication.findProductByCustomerId(id);
		if(list!=null && list.size() > 0){
			result.put("data", list);
			result.put("result", "success");
		}else
			result.put("result", "fail");
		return result;
	}

	@ResponseBody
	@RequestMapping("/getPackByProductId/{id}")
	public Map<String, Object> getPackByProductId(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", productApplication.getPackByProductId(id));
		return result;
	}
	
}
