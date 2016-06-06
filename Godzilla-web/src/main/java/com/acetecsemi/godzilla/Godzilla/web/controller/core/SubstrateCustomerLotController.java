
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.framework.i18n.I18NManager;
import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dayatang.querychannel.Page;
import org.dayatang.utils.DateUtils;

import com.acetecsemi.godzilla.Godzilla.application.core.CustomerLotOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.SubstrateCustomerLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

import org.codehaus.jackson.map.JsonMappingException.Reference;
@Controller
@RequestMapping("/SubstrateCustomerLot")
public class SubstrateCustomerLotController {
		
	@Inject
	private SubstrateCustomerLotApplication substrateCustomerLotApplication;
	
	@Inject
	private UserApplication userApplication;
	
	@Inject
	private CustomerLotOptLogApplication customerLotOptLogApplication;
	
	private static final String LOCAL = Locale.getDefault().toString();
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(@RequestParam String data) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
//		ObjectMapper mapper = new ObjectMapper();  
		SubstrateCustomerLotDTO substrateCustomerLotDTO = checkVo(data);
		String actionError = substrateCustomerLotDTO.getActionError();
		if(actionError != null && actionError.length() > 0)
		{
			result.put("actionError", actionError);
			return result;
		}
		String useraccount = substrateCustomerLotDTO.getUserName();
		String password = substrateCustomerLotDTO.getPassword();
		if(!this.passwordCheck(useraccount, password)){
			result.put("result", "fail");
			return result;
		}
		Date nowDate = new Date();
		substrateCustomerLotDTO.setCreateDate(nowDate);
		substrateCustomerLotDTO.setLastModifyDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		substrateCustomerLotDTO.setOptUser(optUser);
		Resource resource = Resource.findByProperty(Resource.class, "identifier", "Waiting").get(0);
		substrateCustomerLotDTO.setCurrStatus(resource.getName());
		if(substrateCustomerLotDTO.getProductionDate() == null){
			substrateCustomerLotDTO.setProductionDate(nowDate);
		}
		substrateCustomerLotDTO.setManufactureDate(substrateCustomerLotDTO.getProductionDate());
		substrateCustomerLotDTO.setExpiryDate(substrateCustomerLotDTO.getGuaranteePeriod());
		substrateCustomerLotApplication.saveSubstrateCustomerLot(substrateCustomerLotDTO);
		saveLog(nowDate,optUser,useraccount,password,substrateCustomerLotDTO.getId(),"add","add");
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(@RequestParam String data) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
//		ObjectMapper mapper = new ObjectMapper();  
		SubstrateCustomerLotDTO substrateCustomerLotDTO = checkVo(data);
		String actionError = substrateCustomerLotDTO.getActionError();
		if(actionError != null && actionError.length() > 0)
		{
			result.put("actionError", actionError);
			return result;
		}  
		String useraccount = substrateCustomerLotDTO.getUserName();
		String password = substrateCustomerLotDTO.getPassword();
		if(!this.passwordCheck(useraccount, password)){
			result.put("result", "fail");
			return result;
		}
		SubstrateCustomerLotDTO prevCustomerLot = substrateCustomerLotApplication.getSubstrateCustomerLot(substrateCustomerLotDTO.getId());
		Date nowDate = new Date();
		substrateCustomerLotDTO.setLastModifyDate(nowDate);
		substrateCustomerLotDTO.setManufactureDate(substrateCustomerLotDTO.getProductionDate());
		substrateCustomerLotDTO.setExpiryDate(substrateCustomerLotDTO.getGuaranteePeriod());
		substrateCustomerLotApplication.updateSubstrateCustomerLot(substrateCustomerLotDTO);
		
		String description = "modify";
		if(prevCustomerLot != null && !(prevCustomerLot.getCurrStatus().equalsIgnoreCase(substrateCustomerLotDTO.getCurrStatus())))
		{
			description = prevCustomerLot.getCurrStatus() + " to " + substrateCustomerLotDTO.getCurrStatus();
		}
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		saveLog(nowDate,optUser,useraccount,password,substrateCustomerLotDTO.getId(),"modify",description);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(SubstrateCustomerLotDTO substrateCustomerLotDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<SubstrateCustomerLotDTO> all = substrateCustomerLotApplication.pageQuerySubstrateCustomerLot(substrateCustomerLotDTO, page, pagesize);
		Date date = new Date();
		long time = 0;
		long expireTime = 0;
		Date standardTime = null ;
		for(SubstrateCustomerLotDTO w:all.getData())
		{
			if(w.getDeliveryDate() == null || w.getExpiryDate() == null)
			{
				
				continue;
			}
			standardTime = w.getDeliveryDate();
			time = date.getTime() - standardTime.getTime();
			expireTime = date.getTime() - w.getExpiryDate().getTime();
			//由hold状态修改为wating状态后，不再检查其是否过期
			if(customerLotOptLogApplication.getCustomerLotOptLogDTOByProperties(w.getId(), "godzilla_substratecustomerlot"))
			{
				if(DateUtils.isDateBefore(standardTime, date))
				{
					w.setShippingDelayTime(MyDateUtils.getDayHour(standardTime, date));
					substrateCustomerLotApplication.updateSubstrateCustomerLot(w);
				}
				
				continue;
			}
			
			if(time > 0 || expireTime > 0)
			{
				if(time > 0)
				{
					w.setDelayTime(time);
					w.setShippingDelayTime(MyDateUtils.getDayHour(standardTime, date));
				}
				w.setCurrStatus("Hold");
				substrateCustomerLotApplication.updateSubstrateCustomerLot(w);
			}
		}
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageTotal")
	public  Map<String, Object> pageTotal(SubstrateCustomerLotDTO substrateCustomerLotDTO) {
		Map<String, Object> result = substrateCustomerLotApplication
				.pageQuerySubstrateCustomerLotTotal(substrateCustomerLotDTO);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids,@RequestParam String userName,@RequestParam String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(!this.passwordCheck(userName, password)){
			result.put("result", "fail");
			return result;
		}
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	
        	 idArrs[i] = Long.parseLong(value[i]);
        	 saveLog(new Date(),optUser,userName, password,idArrs[i],"delete","deleteSubstrate");			        	
        }
        substrateCustomerLotApplication.removeSubstrateCustomerLots(idArrs);
		result.put("result", "success");
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCustomerLotApplication.getSubstrateCustomerLot(id));
		return result;
	}
	
		@ResponseBody
	@RequestMapping("/findCreateUserBySubstrateCustomerLot/{id}")
	public Map<String, Object> findCreateUserBySubstrateCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCustomerLotApplication.findCreateUserBySubstrateCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserBySubstrateCustomerLot/{id}")
	public Map<String, Object> findLastModifyUserBySubstrateCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCustomerLotApplication.findLastModifyUserBySubstrateCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findVenderBySubstrateCustomerLot/{id}")
	public Map<String, Object> findVenderBySubstrateCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCustomerLotApplication.findVenderBySubstrateCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findProductBySubstrateCustomerLot/{id}")
	public Map<String, Object> findProductBySubstrateCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCustomerLotApplication.findProductBySubstrateCustomerLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findDefineStationProcessesBySubstrateCustomerLot/{id}")
	public Page findDefineStationProcessesBySubstrateCustomerLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<DefineStationProcessDTO> all = substrateCustomerLotApplication.findDefineStationProcessesBySubstrateCustomerLot(id, page, pagesize);
		return all;
	}		
	@ResponseBody
	@RequestMapping("/findRunCardTemplateBySubstrateCustomerLot/{id}")
	public Map<String, Object> findRunCardTemplateBySubstrateCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", substrateCustomerLotApplication.findRunCardTemplateBySubstrateCustomerLot(id));
		return result;
	}
	
	private boolean passwordCheck(String useraccount, String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password);
	}
	
	private boolean isUserExisted(String useraccount) {
		UserVO result = userApplication.findByUserAccount(useraccount);
		return result == null ? false : true;
	}

	private boolean userCheck(String useraccount, String password) {
		UserVO result = userApplication.findByUserAccount(useraccount);
		if (result == null) {
			return false;
		}
		if (result.getUserPassword().equals(password)) {
			return true;
		}
		return false;
	}
	private SubstrateCustomerLotDTO checkVo(String data)
	{
		ObjectMapper mapper = new ObjectMapper();
		SubstrateCustomerLotDTO substrateCustomerLotDTO = new SubstrateCustomerLotDTO();
		try
		{
			substrateCustomerLotDTO = mapper.readValue(data, SubstrateCustomerLotDTO.class);
		}
		catch(JsonMappingException jme)
		{
			List<Reference> list = jme.getPath();
			if(list != null && list.size() > 0)
			{
				substrateCustomerLotDTO.setActionError(list.get(0).getFieldName() + I18NManager.getMessage("supply.addWafer.checknumber", LOCAL));
			}
				
		}
		catch (JsonParseException e) 
		{
			substrateCustomerLotDTO.setActionError(I18NManager.getMessage("supply.addWafer.jsonParse", LOCAL));
		}
		catch (IOException e)
		{
			substrateCustomerLotDTO.setActionError(I18NManager.getMessage("supply.addWafer.ioError", LOCAL));
		}
		
		return substrateCustomerLotDTO;
	}
	private void saveLog(Date nowDate, UserDTO optUser, String useraccount,String password,Long id,String optName,String description)
	{
		CustomerLotOptLogDTO customerLotOptLog = new CustomerLotOptLogDTO();
		customerLotOptLog.setCreateDate(nowDate);
		customerLotOptLog.setLastModifyDate(nowDate);
		customerLotOptLog.setLoginUser(optUser);
		customerLotOptLog.setOptUser(useraccount);
		customerLotOptLog.setOptUserPasswd(password);
		customerLotOptLog.setCustlotId(id);
		customerLotOptLog.setCustlotTableName("godzilla_substratecustomerlot");
		customerLotOptLog.setOptName(optName);
		customerLotOptLog.setDescription(description);
		customerLotOptLogApplication.saveCustomerLotOptLog(customerLotOptLog);
	}
}
