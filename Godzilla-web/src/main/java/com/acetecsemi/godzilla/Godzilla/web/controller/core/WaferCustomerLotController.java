
package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.openkoala.auth.application.RoleApplication;
import org.openkoala.auth.application.UserApplication;
import org.openkoala.auth.application.vo.ResourceVO;
import org.openkoala.auth.application.vo.RoleVO;
import org.openkoala.auth.application.vo.UserVO;
import org.openkoala.framework.i18n.I18NManager;
import org.openkoala.koala.auth.core.domain.Resource;
import org.openkoala.koala.auth.ss3adapter.AuthUserUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonMappingException.Reference;
import org.codehaus.jackson.map.ObjectMapper;
import org.dayatang.querychannel.Page;
import org.dayatang.utils.DateUtils;

import com.acetecsemi.godzilla.Godzilla.application.core.CustomerLotOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferCustomerLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.web.controller.auth.ParamsPojo;

@Controller
@Transactional
@RequestMapping("/WaferCustomerLot")
public class WaferCustomerLotController {
		
	private static final String RIGHT_ADD = "001-add";
	
	private static final String RIGHT_MODIFY = "001-modify";
	
	private static final String RIGHT_DELETE = "001-delete";
	
	private static final String MSG_SUCCESS = "success";
	
	private static final String MSG_FAIL = "fail";
	
	private static final String MSG_NO_RIGHT = "noRight";
	
	private static final String LOCAL = Locale.getDefault().toString();
	
	@Inject
	private WaferCustomerLotApplication waferCustomerLotApplication;
	
	@Inject
	private UserApplication userApplication;
	
	@Inject
	private RoleApplication roleApplication;
	
	@Inject
	private CustomerLotOptLogApplication customerLotOptLogApplication;
	Logger log = Logger.getLogger(WaferCustomerLotController.class);
	
	
	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(ParamsPojo params,@RequestParam String data) throws JsonParseException, JsonMappingException, IOException {
		//data = "{\"customerId\":\"1\",\"customerLotNo\":\"customerLotNo\",\"productId\":\"1\",\"qty\":\"1\",\"firstPQty\":\"1\",\"secondPQty\":\"1\",\"thirdPQty\":\"1\",\"wafer\":\"1\",\"customerOrder\":\"231\",\"shippingDate\":\"2014-07-31T12:23:00.000+0800\",\"userName\":\"123\",\"remark\":\"123\"}";
		Map<String, Object> result = new HashMap<String, Object>();
		WaferCustomerLotDTO waferCustomerLotDTO = checkVo(data);
		String actionError = waferCustomerLotDTO.getActionError();
		if(actionError != null && actionError.length() > 0)
		{
			result.put("actionError", actionError);
			return result;
		}
		result = checkWaferQty(waferCustomerLotDTO);
		if(result.containsKey("actionError"))
		{
			return result;
		}
		
		String useraccount = waferCustomerLotDTO.getUserName();
		String password = waferCustomerLotDTO.getPassword();
		String resCheckMsg = this.resCheckMsg(useraccount, password, RIGHT_ADD);
		if(MSG_SUCCESS.equals(resCheckMsg)){
			Date nowDate = new Date();
			waferCustomerLotDTO.setCreateDate(nowDate);
			waferCustomerLotDTO.setLastModifyDate(nowDate);
			waferCustomerLotDTO.setManufactureDate(waferCustomerLotDTO.getProductionDate());
			waferCustomerLotDTO.setExpiryDate(waferCustomerLotDTO.getGuaranteePeriod());
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			waferCustomerLotDTO.setOptUser(optUser);
			Resource resource = Resource.findByProperty(Resource.class, "identifier", "Waiting").get(0);
			waferCustomerLotDTO.setCurrStatus(resource.getName());
			waferCustomerLotApplication.saveWaferCustomerLot(waferCustomerLotDTO);
			
			saveLog(nowDate,optUser,useraccount,password,waferCustomerLotDTO.getId(),"add","add");
			result.put("result", resCheckMsg);
		}else{
			result.put("result", resCheckMsg);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(@RequestParam String data) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> result = new HashMap<String, Object>();
//		ObjectMapper mapper = new ObjectMapper();  
		WaferCustomerLotDTO waferCustomerLotDTO = checkVo(data);
		String actionError = waferCustomerLotDTO.getActionError();
		if(actionError != null && actionError.length() > 0)
		{
			result.put("actionError", actionError);
			return result;
		}
		result = checkWaferQty(waferCustomerLotDTO);
		if(result.containsKey("actionError"))
		{
			return result;
		}
		//验证日期
		//TODO  
		String useraccount = waferCustomerLotDTO.getUserName();
		String password = waferCustomerLotDTO.getPassword();
		String resCheckMsg = this.resCheckMsg(useraccount, password, RIGHT_MODIFY);
		if(MSG_SUCCESS.equals(resCheckMsg)){
			
			WaferCustomerLotDTO prevCustomerLot = waferCustomerLotApplication.getWaferCustomerLot(waferCustomerLotDTO.getId());
			Date nowDate = new Date();
			waferCustomerLotDTO.setLastModifyDate(nowDate);
			waferCustomerLotDTO.setManufactureDate(waferCustomerLotDTO.getProductionDate());
			waferCustomerLotDTO.setExpiryDate(waferCustomerLotDTO.getGuaranteePeriod());
			waferCustomerLotApplication.updateWaferCustomerLot(waferCustomerLotDTO);
			
			String description = "modify";
			if(prevCustomerLot != null && !(prevCustomerLot.getCurrStatus().equalsIgnoreCase(waferCustomerLotDTO.getCurrStatus())))
			{
				description = prevCustomerLot.getCurrStatus() + " to " + waferCustomerLotDTO.getCurrStatus();
			}
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			saveLog(nowDate,optUser,useraccount,password,waferCustomerLotDTO.getId(),"modify",description);
			result.put("result", MSG_SUCCESS);
		}else{
			result.put("result", resCheckMsg);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferCustomerLotDTO waferCustomerLotDTO, @RequestParam int page, @RequestParam int pagesize) {
		Page<WaferCustomerLotDTO> all = waferCustomerLotApplication.pageQueryWaferCustomerLot(waferCustomerLotDTO, page, pagesize);
		Date date = new Date();
		long time = 0;
		long expireTime = 0;
		Date standardTime = null ;

		
		for(WaferCustomerLotDTO w:all.getData())
		{
			
			if(w.getDeliveryDate() == null || w.getExpiryDate() == null)
			{
				
				continue;
			}
			//standardTime = MyDateUtils.add(w.getShippingDate(), 5, 5);//天数加5
			standardTime = w.getDeliveryDate();
			time = date.getTime() - standardTime.getTime();
			expireTime = date.getTime() - w.getExpiryDate().getTime();
			//由hold状态修改为wating状态后，不再检查其是否过期
			if(customerLotOptLogApplication.getCustomerLotOptLogDTOByProperties(w.getId(), "godzilla_wafercustomerlot"))
			{
				if(DateUtils.isDateBefore(standardTime, date))
				{
					w.setShippingDelayTime(MyDateUtils.getDayHour(standardTime, date));
					waferCustomerLotApplication.updateWaferCustomerLot(w);
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
				waferCustomerLotApplication.updateWaferCustomerLot(w);
			}
		}
		return all;
	}
	
	@ResponseBody
	@RequestMapping("/pageTotal")
	public  Map<String, Object> pageTotal(WaferCustomerLotDTO waferCustomerLotDTO) {
		Map<String, Object> result = waferCustomerLotApplication
				.pageQueryWaferCustomerLotTotal(waferCustomerLotDTO);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids,@RequestParam String userName,@RequestParam String password,@RequestParam String comments) {
		Map<String, Object> result = new HashMap<String, Object>();
		String resCheckMsg = this.resCheckMsg(userName, password, RIGHT_DELETE);
		if(!MSG_SUCCESS.equals(resCheckMsg)){
			result.put("result", resCheckMsg);
			return result;
		}
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		String[] value = ids.split(",");
        Long[] idArrs = new Long[value.length];
        for (int i = 0; i < value.length; i ++) {
        	  idArrs[i] = Long.parseLong(value[i]);
        	  saveLog(new Date(),optUser,userName, password,idArrs[i],"delete",comments);
        }
        waferCustomerLotApplication.removeWaferCustomerLots(idArrs);
        
		result.put("result", MSG_SUCCESS);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCustomerLotApplication.getWaferCustomerLot(id));
		result.put("result", MSG_SUCCESS);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/findCreateUserByWaferCustomerLot/{id}")
	public Map<String, Object> findCreateUserByWaferCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCustomerLotApplication.findCreateUserByWaferCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByWaferCustomerLot/{id}")
	public Map<String, Object> findLastModifyUserByWaferCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCustomerLotApplication.findLastModifyUserByWaferCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCustomerByWaferCustomerLot/{id}")
	public Map<String, Object> findCustomerByWaferCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCustomerLotApplication.findCustomerByWaferCustomerLot(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findProductByWaferCustomerLot/{id}")
	public Map<String, Object> findProductByWaferCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCustomerLotApplication.findProductByWaferCustomerLot(id));
		return result;
	}


	@ResponseBody
	@RequestMapping("/findDefineStationProcessesByWaferCustomerLot/{id}")
	public Page findDefineStationProcessesByWaferCustomerLot(@PathVariable Long id, @RequestParam int page, @RequestParam int pagesize) {
		Page<DefineStationProcessDTO> all = waferCustomerLotApplication.findDefineStationProcessesByWaferCustomerLot(id, page, pagesize);
		return all;
	}		
	@ResponseBody
	@RequestMapping("/findRunCardTemplateByWaferCustomerLot/{id}")
	public Map<String, Object> findRunCardTemplateByWaferCustomerLot(@PathVariable Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferCustomerLotApplication.findRunCardTemplateByWaferCustomerLot(id));
		return result;
	}

	private boolean passwordCheck(String useraccount, String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password);
	}
	
	private boolean rightCheck(String useraccount,String identifier){
		List<RoleVO> roleList = roleApplication.findRoleByUserAccount(useraccount);
		for(RoleVO roleVO : roleList){
			List<ResourceVO> resList = roleApplication.findResourceByRole(roleVO);
			for(ResourceVO resourceVO : resList){
				if(resourceVO.getIdentifier().equals(identifier)){
					return true;
				}
			}
		}
		return false;
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
	
	private String resCheckMsg(String useraccount,String password,String identifier){
		if(!this.passwordCheck(useraccount, password)){
			return MSG_FAIL;
		}
		if("admin".equals(useraccount)){
			return MSG_SUCCESS;
		}
		if(!this.rightCheck(useraccount, identifier)){
			return MSG_NO_RIGHT;
		}
		return MSG_SUCCESS;
	}
	
	private WaferCustomerLotDTO checkVo(String data)
	{
		ObjectMapper mapper = new ObjectMapper();
		WaferCustomerLotDTO waferCustomerLotDTO = new WaferCustomerLotDTO();
		try
		{
			waferCustomerLotDTO = mapper.readValue(data, WaferCustomerLotDTO.class);
		}
		catch(JsonMappingException jme)
		{
			List<Reference> list = jme.getPath();
			if(list != null && list.size() > 0)
			{
				waferCustomerLotDTO.setActionError(list.get(0).getFieldName() + I18NManager.getMessage("supply.addWafer.checknumber", LOCAL));
			}
				
		}
		catch (JsonParseException e) 
		{
			waferCustomerLotDTO.setActionError(I18NManager.getMessage("supply.addWafer.jsonParse", LOCAL));
		}
		catch (IOException e)
		{
			waferCustomerLotDTO.setActionError(I18NManager.getMessage("supply.addWafer.ioError", LOCAL));
		}
		
		return waferCustomerLotDTO;
	}
	
	private Map<String, Object> checkWaferQty(WaferCustomerLotDTO waferCustomerLotDTO)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		if(waferCustomerLotDTO == null)
		{
			result.put("actionError", I18NManager.getMessage("supply.addWafer.jsonParse", LOCAL));
			return result;
		}
		//验证die的qty总数是qty=1stP.Qty+2ndP.Qty+3rdP.Qty
		int first = checkQty(waferCustomerLotDTO.getFirstPQty());
		int sec = checkQty(waferCustomerLotDTO.getSecondPQty());
		int third = checkQty(waferCustomerLotDTO.getThirdPQty());
		if(waferCustomerLotDTO.getQty() < first + sec + third)
		{
			result.put("actionError", I18NManager.getMessage("supply.addWafer.sumQty", LOCAL));
		}
		return result;
	}
	
	private int checkQty(Integer qty)
	{
		if(qty != null && qty > 0)
		{
			return qty;
		}
		return 0;
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
		customerLotOptLog.setCustlotTableName("godzilla_wafercustomerlot");
		customerLotOptLog.setOptName(optName);
		customerLotOptLog.setDescription(description);
		customerLotOptLogApplication.saveCustomerLotOptLog(customerLotOptLog);
	}
}
