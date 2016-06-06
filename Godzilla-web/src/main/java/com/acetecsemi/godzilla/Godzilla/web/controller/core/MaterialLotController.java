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
import org.codehaus.jackson.map.JsonMappingException.Reference;
import org.codehaus.jackson.map.ObjectMapper;
import org.dayatang.querychannel.Page;
import org.dayatang.utils.DateUtils;

import com.acetecsemi.godzilla.Godzilla.application.core.CustomerLotOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/MaterialLot")
public class MaterialLotController {

	@Inject
	private MaterialLotApplication materialLotApplication;
	@Inject
	private CustomerLotOptLogApplication customerLotOptLogApplication;

	@Inject
	private UserApplication userApplication;
	private static final String LOCAL = Locale.getDefault().toString();
	private static final int DIRECT = 1; //直接材料
	private static final int INDIRECT = 2; //间接材料
	private static final int COMPONENT = 3; //被动元器件
	private static final int CONSUMABLES = 4; //消耗品
	
	@ResponseBody
	@RequestMapping("/direct/add")
	public Map<String, Object> directAdd(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.Add(data, DIRECT);
	}

	@ResponseBody
	@RequestMapping("/direct/update")
	public Map<String, Object> directUpdate(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.update(data, DIRECT);
	}

	@ResponseBody
	@RequestMapping("/direct/pageJson")
	public Page directPageJson(MaterialLotDTO materialLotDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		materialLotDTO.setMaterialType(DIRECT);
		Page<MaterialLotDTO> all = materialLotApplication.pageQueryMaterialLot(
				materialLotDTO, page, pagesize);
		Date date = new Date();
		long time = 0;
		long expireTime = 0;
		Date standardTime = null ;
		for(MaterialLotDTO w:all.getData())
		{
			if(w.getDeliveryDate() == null || w.getExpiryDate() == null)
			{
				continue;
			}
			if(w.getCurrStatus().equals("Hold"))
			{
				continue;
			}
			standardTime = w.getDeliveryDate();
			time = date.getTime() - standardTime.getTime();
			if(time > 0 || expireTime > 0 )
			{
				if(time > 0)
				{
					w.setDelayTime(time);
					w.setShippingDelayTime(MyDateUtils.getDayHour(standardTime, date));
				}
				w.setCurrStatus("Hold");
				materialLotApplication.updateMaterialLot(w);;
			}
		}
		return all;
	}

	@ResponseBody
	@RequestMapping("/direct/pageTotal")
	public  Map<String, Object> pageTotal(MaterialLotDTO materialLotDTO) {
		materialLotDTO.setMaterialType(DIRECT);
		Map<String, Object> result = materialLotApplication
				.pageQueryMaterialLotTotal(materialLotDTO);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/direct/delete")
	public Map<String, Object> directDelete(@RequestParam String ids,@RequestParam String userName,@RequestParam String password) {
		return this.delete(ids,userName,password);
	}

	@ResponseBody
	@RequestMapping("/direct/get/{id}")
	public Map<String, Object> directGet(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLotApplication.getMaterialLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/indirect/add")
	public Map<String, Object> indirectAdd(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.Add(data, INDIRECT);
	}

	@ResponseBody
	@RequestMapping("/indirect/update")
	public Map<String, Object> indirectUpdate(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.update(data, INDIRECT);
	}

	@ResponseBody
	@RequestMapping("/indirect/pageJson")
	public Page indirectPageJson(MaterialLotDTO materialLotDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		materialLotDTO.setMaterialType(INDIRECT);
		Page<MaterialLotDTO> all = materialLotApplication.pageQueryMaterialLot(
				materialLotDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/indirect/pageTotal")
	public  Map<String, Object> indirectPageTotal(MaterialLotDTO materialLotDTO) {
		materialLotDTO.setMaterialType(INDIRECT);
		Map<String, Object> result = materialLotApplication
				.pageQueryMaterialLotTotal(materialLotDTO);
		return result;
	}

	@ResponseBody
	@RequestMapping("/indirect/delete")
	public Map<String, Object> indirectDelete(@RequestParam String ids,@RequestParam String userName,@RequestParam String password) {
		return this.delete(ids,userName,password);
	}

	@ResponseBody
	@RequestMapping("/indirect/get/{id}")
	public Map<String, Object> indirectGet(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLotApplication.getMaterialLot(id));
		return result;
	}

	/**
	 * 
	 * @param data
	 * @param type
	 *            :1直接材料 2间接材料3被动元器件4消耗品
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public Map<String, Object> Add(String data, int type)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
//		ObjectMapper mapper = new ObjectMapper();
		MaterialLotDTO materialLotDTO = checkVo(data);
		String actionError = materialLotDTO.getActionError();
		if(actionError != null && actionError.length() > 0)
		{
			result.put("actionError", actionError);
			return result;
		}
		String useraccount = materialLotDTO.getUserName();
		String password = materialLotDTO.getPassword();
		if(!this.passwordCheck(useraccount, password)){
			result.put("result", "fail");
			return result;
		}
		Date nowDate = new Date();
		materialLotDTO.setCreateDate(nowDate);
		materialLotDTO.setLastModifyDate(nowDate);
		MaterialDTO materialDTO = new MaterialDTO();
		materialDTO.setId(materialLotDTO.getMaterialId());
		materialDTO.setPartId(materialLotDTO.getPartId());
		materialDTO.setPartNameCN(materialLotDTO.getPartNameCN());
		materialDTO.setUnit(materialLotDTO.getUnit());
		materialDTO.setMaterialType(type);
		materialDTO.setCreateDate(nowDate);
		materialDTO.setLastModifyDate(nowDate);
		materialDTO.setMaterialName(materialLotDTO.getMaterialName());
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		materialLotDTO.setOptUser(optUser);
		Resource resource = Resource.findByProperty(Resource.class,
				"identifier", "Waiting").get(0);
		materialLotDTO.setCurrStatus(resource.getName());
		materialLotDTO.setMaterialDTO(materialDTO);
		materialLotDTO.setManufactureDate(materialLotDTO.getProductionDate());
		materialLotDTO.setExpiryDate(materialLotDTO.getGuaranteePeriod());
		materialLotApplication.saveMaterialLot(materialLotDTO);
		saveLog(nowDate,optUser,useraccount,password,materialLotDTO.getId(),"add","add");
		result.put("result", "success");
		return result;
	}

	public Map<String, Object> update(@RequestParam String data, int type)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
//		ObjectMapper mapper = new ObjectMapper();
		MaterialLotDTO materialLotDTO = checkVo(data);
		String actionError = materialLotDTO.getActionError();
		if(actionError != null && actionError.length() > 0)
		{
			result.put("actionError", actionError);
			return result;
		}
		String useraccount = materialLotDTO.getUserName();
		String password = materialLotDTO.getPassword();
		if(!this.passwordCheck(useraccount, password)){
			result.put("result", "fail");
			return result;
		}
		
		MaterialLotDTO prevCustomerLot = materialLotApplication.getMaterialLot(materialLotDTO.getId());
		Date nowDate = new Date();
		materialLotDTO.setCreateDate(nowDate);
		materialLotDTO.setLastModifyDate(nowDate);
		MaterialDTO materialDTO = new MaterialDTO();
		materialDTO.setId(materialLotDTO.getMaterialId());
		materialDTO.setPartId(materialLotDTO.getPartId());
		materialDTO.setPartNameCN(materialLotDTO.getPartNameCN());
		materialDTO.setUnit(materialLotDTO.getUnit());
		materialDTO.setMaterialType(type);
		materialDTO.setCreateDate(nowDate);
		materialDTO.setLastModifyDate(nowDate);
		materialDTO.setMaterialName(materialLotDTO.getMaterialName());
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		materialLotDTO.setOptUser(optUser);
		Resource resource = Resource.findByProperty(Resource.class,
				"identifier", "Waiting").get(0);
		materialLotDTO.setCurrStatus(resource.getName());
		materialLotDTO.setMaterialDTO(materialDTO);
		materialLotDTO.setManufactureDate(materialLotDTO.getProductionDate());
		materialLotDTO.setExpiryDate(materialLotDTO.getGuaranteePeriod());
		materialLotApplication.updateMaterialLot(materialLotDTO);
		
		String description = "modify";
		if(prevCustomerLot != null && !(prevCustomerLot.getCurrStatus().equalsIgnoreCase(materialLotDTO.getCurrStatus())))
		{
			description = prevCustomerLot.getCurrStatus() + " to " + materialLotDTO.getCurrStatus();
		}
//		UserDTO optUser = new UserDTO();
//		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		saveLog(nowDate,optUser,useraccount,password,materialLotDTO.getId(),"modify",description);
		result.put("result", "success");
		return result;
	}

	public Map<String, Object> delete(String ids,String userName, String password) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(!this.passwordCheck(userName, password)){
			result.put("result", "fail");
			return result;
		}
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		for (int i = 0; i < value.length; i++) {

			idArrs[i] = Long.parseLong(value[i]);
			saveLog(new Date(),optUser,userName, password,idArrs[i],"delete","delete");
		}
		materialLotApplication.removeMaterialLots(idArrs);
		result.put("result", "success");
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
	private MaterialLotDTO checkVo(String data)
	{
		ObjectMapper mapper = new ObjectMapper();
		MaterialLotDTO materialLotDTO = new MaterialLotDTO();
		try
		{
			materialLotDTO = mapper.readValue(data, MaterialLotDTO.class);
		}
		catch(JsonMappingException jme)
		{
			List<Reference> list = jme.getPath();
			if(list != null && list.size() > 0)
			{
				materialLotDTO.setActionError(list.get(0).getFieldName() + I18NManager.getMessage("supply.addWafer.checknumber", LOCAL));
			}
				
		}
		catch (JsonParseException e) 
		{
			materialLotDTO.setActionError(I18NManager.getMessage("supply.addWafer.jsonParse", LOCAL));
		}
		catch (IOException e)
		{
			materialLotDTO.setActionError(I18NManager.getMessage("supply.addWafer.ioError", LOCAL));
		}
		
		return materialLotDTO;
	}
	
	@ResponseBody
	@RequestMapping("/component/add")
	public Map<String, Object> componentAdd(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.Add(data, COMPONENT);
	}

	@ResponseBody
	@RequestMapping("/component/update")
	public Map<String, Object> componentUpdate(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.update(data, COMPONENT);
	}

	@ResponseBody
	@RequestMapping("/component/pageJson")
	public Page componentPageJson(MaterialLotDTO materialLotDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		materialLotDTO.setMaterialType(COMPONENT);
		Page<MaterialLotDTO> all = materialLotApplication.pageQueryMaterialLot(
				materialLotDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/component/pageTotal")
	public  Map<String, Object> componentPageTotal(MaterialLotDTO materialLotDTO) {
		materialLotDTO.setMaterialType(COMPONENT);
		Map<String, Object> result = materialLotApplication
				.pageQueryMaterialLotTotal(materialLotDTO);
		return result;
	}

	@ResponseBody
	@RequestMapping("/component/delete")
	public Map<String, Object> componentDelete(@RequestParam String ids,@RequestParam String userName,@RequestParam String password) {
		return this.delete(ids,userName,password);
	}

	@ResponseBody
	@RequestMapping("/component/get/{id}")
	public Map<String, Object> componentGet(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLotApplication.getMaterialLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/consumables/add")
	public Map<String, Object> consumablesAdd(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.Add(data, CONSUMABLES);
	}

	@ResponseBody
	@RequestMapping("/consumables/update")
	public Map<String, Object> consumablesUpdate(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException {
		return this.update(data, CONSUMABLES);
	}

	@ResponseBody
	@RequestMapping("/consumables/pageJson")
	public Page consumablesPageJson(MaterialLotDTO materialLotDTO,
			@RequestParam int page, @RequestParam int pagesize) {
		materialLotDTO.setMaterialType(CONSUMABLES);
		Page<MaterialLotDTO> all = materialLotApplication.pageQueryMaterialLot(
				materialLotDTO, page, pagesize);
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/consumables/pageTotal")
	public  Map<String, Object> consumablesPageTotal(MaterialLotDTO materialLotDTO) {
		materialLotDTO.setMaterialType(CONSUMABLES);
		Map<String, Object> result = materialLotApplication
				.pageQueryMaterialLotTotal(materialLotDTO);
		return result;
	}

	@ResponseBody
	@RequestMapping("/consumables/delete")
	public Map<String, Object> consumablesDelete(@RequestParam String ids,@RequestParam String userName,@RequestParam String password) {
		return this.delete(ids,userName,password);
	}

	@ResponseBody
	@RequestMapping("/consumables/get/{id}")
	public Map<String, Object> consumablesGet(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLotApplication.getMaterialLot(id));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/add/{type}")
	public Map<String, Object> AddType(@RequestParam String data,@PathVariable Integer type)
			throws JsonParseException, JsonMappingException, IOException {
		return this.Add(data, type);
	}

	@ResponseBody
	@RequestMapping("/update/{type}")
	public Map<String, Object> UpdateType(@RequestParam String data,@PathVariable Integer type)
			throws JsonParseException, JsonMappingException, IOException {
		return this.update(data, type);
	}

	@ResponseBody
	@RequestMapping("/pageJson/{type}")
	public Page pageJsonType(MaterialLotDTO materialLotDTO,
			@RequestParam int page, @RequestParam int pagesize,@PathVariable Integer type) {
		materialLotDTO.setMaterialType(type);
		Page<MaterialLotDTO> all = materialLotApplication.pageQueryMaterialLot(
				materialLotDTO, page, pagesize);
		
		Date date = new Date();
		long time = 0;
		long expireTime = 0;
		Date standardTime = null ;
		for(MaterialLotDTO w:all.getData())
		{
			
			if(w.getDeliveryDate() == null || w.getExpiryDate() == null)
			{
				
				continue;
			}
			standardTime =w.getDeliveryDate();
			time = date.getTime() - standardTime.getTime();
			expireTime = date.getTime() - w.getExpiryDate().getTime();
			//由hold状态修改为wating状态后，不再检查其是否过期
			if(customerLotOptLogApplication.getCustomerLotOptLogDTOByProperties(w.getId(), "godzilla_materialLot"))
			{
				if(DateUtils.isDateBefore(standardTime, date))
				{
					w.setShippingDelayTime(MyDateUtils.getDayHour(standardTime, date));
					materialLotApplication.updateMaterialLot(w);
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
				materialLotApplication.updateMaterialLot(w);
			}
		}
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageTotal/{type}")
	public  Map<String, Object> pageTotalType(MaterialLotDTO materialLotDTO,@PathVariable Integer type) {
		materialLotDTO.setMaterialType(type);
		Map<String, Object> result = materialLotApplication
				.pageQueryMaterialLotTotal(materialLotDTO);
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> deleteType(@RequestParam String ids,@RequestParam String userName,@RequestParam String password,@RequestParam String comments) {
		return this.delete(ids,userName,password);
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", materialLotApplication.getMaterialLot(id));
		return result;
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
		customerLotOptLog.setCustlotTableName("godzilla_materialLot");
		customerLotOptLog.setOptName(optName);
		customerLotOptLog.setDescription(description);
		customerLotOptLogApplication.saveCustomerLotOptLog(customerLotOptLog);
	}
}
