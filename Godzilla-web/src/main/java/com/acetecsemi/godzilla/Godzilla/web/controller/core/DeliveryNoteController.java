
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
import com.acetecsemi.godzilla.Godzilla.application.core.DeliveryNoteApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;

@Controller
@RequestMapping("/DeliveryNote")
public class DeliveryNoteController {
		
	@Inject
	private DeliveryNoteApplication deliveryNoteApplication;
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(DeliveryNoteDTO deliveryNoteDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		deliveryNoteApplication.saveDeliveryNote(deliveryNoteDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(DeliveryNoteDTO deliveryNoteDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		deliveryNoteApplication.updateDeliveryNote(deliveryNoteDTO);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(DeliveryNoteDTO deliveryNoteDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<DeliveryNoteDTO> all = deliveryNoteApplication.pageQueryDeliveryNote(deliveryNoteDTO, page, pagesize);
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
        deliveryNoteApplication.removeDeliveryNotes(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", deliveryNoteApplication.getDeliveryNote(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserByDeliveryNote/{id}")
	public Map<String, Object> findCreateUserByDeliveryNote(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", deliveryNoteApplication.findCreateUserByDeliveryNote(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByDeliveryNote/{id}")
	public Map<String, Object> findLastModifyUserByDeliveryNote(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", deliveryNoteApplication.findLastModifyUserByDeliveryNote(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCustomerByDeliveryNote/{id}")
	public Map<String, Object> findCustomerByDeliveryNote(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", deliveryNoteApplication.findCustomerByDeliveryNote(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findProductByDeliveryNote/{id}")
	public Map<String, Object> findProductByDeliveryNote(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", deliveryNoteApplication.findProductByDeliveryNote(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findPartByDeliveryNote/{id}")
	public Map<String, Object> findPartByDeliveryNote(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", deliveryNoteApplication.findPartByDeliveryNote(id));
		return result;
	}

	
}
