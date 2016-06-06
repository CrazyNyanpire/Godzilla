
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.dayatang.querychannel.Page;
import com.acetecsemi.godzilla.Godzilla.application.core.PurchaseOrderApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/PurchaseOrder")
public class PurchaseOrderController {
		
	@Inject
	private PurchaseOrderApplication purchaseOrderApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(PurchaseOrderDTO purchaseOrderDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		purchaseOrderApplication.savePurchaseOrder(purchaseOrderDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(PurchaseOrderDTO purchaseOrderDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		purchaseOrderApplication.updatePurchaseOrder(purchaseOrderDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(PurchaseOrderDTO purchaseOrderDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<PurchaseOrderDTO> all = purchaseOrderApplication.pageQueryPurchaseOrder(purchaseOrderDTO, page, pagesize);
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
        purchaseOrderApplication.removePurchaseOrders(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", purchaseOrderApplication.getPurchaseOrder(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByPurchaseOrder/{id}")
	public Map<String, Object> findCreateUserByPurchaseOrder(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", purchaseOrderApplication.findCreateUserByPurchaseOrder(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByPurchaseOrder/{id}")
	public Map<String, Object> findLastModifyUserByPurchaseOrder(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", purchaseOrderApplication.findLastModifyUserByPurchaseOrder(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCustomerByPurchaseOrder/{id}")
	public Map<String, Object> findCustomerByPurchaseOrder(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", purchaseOrderApplication.findCustomerByPurchaseOrder(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findProductByPurchaseOrder/{id}")
	public Map<String, Object> findProductByPurchaseOrder(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", purchaseOrderApplication.findProductByPurchaseOrder(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findPartByPurchaseOrder/{id}")
	public Map<String, Object> findPartByPurchaseOrder(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", purchaseOrderApplication.findPartByPurchaseOrder(id));
		return result;
	}

	
}
