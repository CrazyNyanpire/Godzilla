package com.acetecsemi.godzilla.Godzilla.web.controller.core;

import java.io.IOException;
import java.util.ArrayList;
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

import com.acetecsemi.godzilla.Godzilla.application.core.LocationApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferInfoApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferInfoRejectDescriptionApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;

@Controller
@RequestMapping("/WaferProcess")
public class WaferProcessController
{

	private static final String		MSG_SUCCESS		= "success";

	private static final String		MSG_FAIL		= "fail";

	private static final String		MSG_NO_RIGHT	= "noRight";

	private static final String		LOCAL			= Locale.getDefault()
															.toString();
	@Inject
	private WaferProcessApplication	waferProcessApplication;

	@Inject
	private UserApplication			userApplication;

	@Inject
	private RoleApplication			roleApplication;

	@Inject
	private WaferInfoApplication	waferInfoApplication;
	
	@Inject
	WaferInfoRejectDescriptionApplication waferInfoRejectDescriptionApplication;
	
	@Inject
	private LocationApplication	locationApplication;

	@ResponseBody
	@RequestMapping("/add")
	public Map<String, Object> add(WaferProcessDTO waferProcessDTO)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		waferProcessApplication.saveWaferProcess(waferProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(WaferProcessDTO waferProcessDTO)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		waferProcessApplication.updateWaferProcess(waferProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/pageJson")
	public Page pageJson(WaferProcessDTO waferProcessDTO,
			@RequestParam int page, @RequestParam int pagesize)
	{
		Page<WaferProcessDTO> all = waferProcessApplication
				.pageQueryWaferProcess(waferProcessDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/pageTotal")
	public Map<String, Object> pageTotal(WaferProcessDTO waferProcessDTO)
	{
		Map<String, Object> result = waferProcessApplication
				.pageQueryWaferProcessTotal(waferProcessDTO);
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam String ids)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String[] value = ids.split(",");
		Long[] idArrs = new Long[value.length];
		for (int i = 0; i < value.length; i++)
		{

			idArrs[i] = Long.parseLong(value[i]);

		}
		waferProcessApplication.removeWaferProcesss(idArrs);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/get/{id}")
	public Map<String, Object> get(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication.getWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findCreateUserByWaferProcess/{id}")
	public Map<String, Object> findCreateUserByWaferProcess(
			@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findCreateUserByWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLastModifyUserByWaferProcess/{id}")
	public Map<String, Object> findLastModifyUserByWaferProcess(
			@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findLastModifyUserByWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findStationByWaferProcess/{id}")
	public Map<String, Object> findStationByWaferProcess(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findStationByWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findOptUserByWaferProcess/{id}")
	public Map<String, Object> findOptUserByWaferProcess(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findOptUserByWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findLocationsByWaferProcess/{id}")
	public Page findLocationsByWaferProcess(@PathVariable Long id,
			@RequestParam int page, @RequestParam int pagesize)
	{
		Page<LocationDTO> all = waferProcessApplication
				.findLocationsByWaferProcess(id, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/findEquipmentByWaferProcess/{id}")
	public Map<String, Object> findEquipmentByWaferProcess(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findEquipmentByWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/findWaferCompanyLotByWaferProcess/{id}")
	public Map<String, Object> findWaferCompanyLotByWaferProcess(
			@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findWaferCompanyLotByWaferProcess(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/Receive/save")
	public Map<String, Object> receive(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data="{\"userName\":\"admin\",\"password\":\"admin\",\"data\":[{\"kucun\":\"427\",\"id\":\"1801\",\"lotno\":\"F002-A00002.1\",\"waferinfo\":[{\"number\":1,\"wafer\":\"武器而气温\",\"waferQty\":\"21\"},{\"number\":2,\"wafer\":\"秦莞尔王企鹅\",\"waferQty\":\"11\"}]}]}";
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> maps = null;
		try
		{
			maps = mapper.readValue(data, Map.class);
		}
		catch (JsonMappingException jme)
		{
			List<Reference> list = jme.getPath();
			if (list != null && list.size() > 0)
			{
				result.put(
						"actionError",
						list.get(0).getFieldName()
								+ I18NManager.getMessage(
										"supply.addWafer.checknumber", LOCAL));
			}
			return result;
		}
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();

		if (!this.passwordCheck(useraccount, password))
		{
			result.put("data", "");
			result.put("result", "fail");
			return result;
		}
		List<Map<String, Object>> lots = (List<Map<String, Object>>) maps
				.get("data");
		for (Map<String, Object> map : lots)
		{
			Long id = Long.parseLong(map.get("id").toString());
			Date nowDate = new Date();
			List<Map<String, Object>> waferInfo = (List<Map<String, Object>>) map
					.get("waferinfo");
			String resultCode = waferProcessApplication.checkProcess(id, waferInfo);
			if (!resultCode.equals("success"))
			{
				result.put("actionError",
						I18NManager.getMessage(resultCode, LOCAL));
				return result;
			}
			List<WaferInfoDTO> waferInfoList = new ArrayList<WaferInfoDTO>();
			int i = 0;
			for (Map<String, Object> wafer : waferInfo)
			{
				WaferInfoDTO waferInfoDTO = new WaferInfoDTO();
				waferInfoDTO.setWaferNumber((Integer) wafer.get("number"));
				if(wafer.get("wafer") != null && !"".equals(wafer.get("wafer").toString()))
				{
					waferInfoDTO.setWaferId(wafer.get("wafer").toString());
				}
				else
				{
//					result.put("actionError",
//							I18NManager.getMessage("waferIn.0004", LOCAL));
//					return result;
					i++;
					waferInfoDTO.setWaferId("Dummy" + i);
				}
				waferInfoDTO.setDieQty(Integer.parseInt(wafer.get("waferQty")
						.toString()));
				waferInfoDTO.setCreateDate(nowDate);
				waferInfoDTO.setLastModifyDate(nowDate);
				waferInfoList.add(waferInfoDTO);
			}

			String locationIds = map.get("kucun").toString();
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			// TODO这个地方保存登录用户的帐号不对吧，应该保存操作者的帐号
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("1");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setUseraccount(useraccount);
			waferProcessDTO.setWaferCustomerLotId(id);
			waferProcessDTO.setLocationIds(locationIds);

			waferProcessDTO = waferProcessApplication.saveWaferProcessReceive(
					waferProcessDTO, id, locationIds, waferInfoList);
		}
		// result.put("data", waferProcessDTO);
		result.put("result", "success");

		return result;
	}

	@ResponseBody
	@RequestMapping("/Receive/pageJson")
	public Page receivePageJson(WaferProcessDTO waferProcessDTO,
			@RequestParam int page, @RequestParam int pagesize)
	{
		waferProcessDTO.setStationId((long) 101);
		Page<WaferProcessDTO> all = waferProcessApplication
				.pageQueryWaferProcess(waferProcessDTO, page, pagesize);
		return all;
	}

	@ResponseBody
	@RequestMapping("/Receive/pageTotal")
	public Map<String, Object> receivePageTotal(WaferProcessDTO waferProcessDTO)
	{
		waferProcessDTO.setStationId((long) 101);
		Map<String, Object> result = waferProcessApplication
				.pageQueryWaferProcessTotal(waferProcessDTO);
		return result;
	}

	private boolean passwordCheck(String useraccount, String password)
	{
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		password = md5PasswordEncoder.encodePassword(password, "");
		return this.isUserExisted(useraccount)
				&& this.userCheck(useraccount, password);
	}

	private boolean isUserExisted(String useraccount)
	{
		UserVO result = userApplication.findByUserAccount(useraccount);
		return result == null ? false : true;
	}

	private boolean userCheck(String useraccount, String password)
	{
		UserVO result = userApplication.findByUserAccount(useraccount);
		if (result == null)
		{
			return false;
		}
		if (result.getUserPassword().equals(password))
		{
			return true;
		}
		return false;
	}

	@ResponseBody
	@RequestMapping("/Process/pageJson/{id}")
	public Page PageJsonByStationId(@PathVariable Long id,
			WaferProcessDTO waferProcessDTO, @RequestParam int page,
			@RequestParam int pagesize)
	{
		waferProcessDTO.setStationId(id);
		// if(currtStatus != null && !"".equals(currtStatus)){
		// waferProcessDTO.setCurrStatus(currtStatus);
		// }
		Page<WaferProcessDTO> all = waferProcessApplication
				.pageQueryWaferProcess(waferProcessDTO, page, pagesize);
		return all;
	}

	
	/** 在preassy站别的页面上单击下一步时调用*/
	@ResponseBody
	@RequestMapping("/Process/nextProcess/{id}")
	public Map<String, Object> nextProcess(@PathVariable Long id,
			@RequestParam String data, WaferProcessDTO waferProcessDTO)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// String data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"WaferQtyOut\":\"1\",\"GoodQtyOut\":\"1\",\"ScrapsQtyOut\":\"1\",\"rejectCode\":[{\"id\":\"DS01\",\"value\":\"1\"},{\"id\":\"DS02\",\"value\":\"1\"},{\"id\":\"DS03\",\"value\":\"\"},{\"id\":\"DS04\",\"value\":\"\"},{\"id\":\"DS05\",\"value\":\"\"},{\"id\":\"DS06\",\"value\":\"\"},{\"id\":\"DS07\",\"value\":\"\"},{\"id\":\"DS08\",\"value\":\"\"},{\"id\":\"DS09\",\"value\":\"\"},{\"id\":\"DS10\",\"value\":\"\"}]}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Long startFlow;
		// WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, Object> info = (Map<String, Object>) maps.get("data");
			if(maps.get("nextStationId") != null){
				Long nextStationId = Long.valueOf(maps.get("nextStationId").toString());
				waferProcessDTO.setNextStationId(nextStationId);
			}
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setWaferCustomerLotId(id);
			waferProcessDTO.setUseraccount(useraccount);
			waferProcessDTO.setComments(maps.get("comments") == null?"":maps.get("comments").toString());
			waferProcessDTO.setId(id);
			if (maps.get("startFlow") != null)
			{
				startFlow = Long.parseLong(maps.get("startFlow").toString());
				waferProcessDTO.setStartFlow(startFlow);
			}
			if (info != null && info.get("waferinfo") != null)
			{
				List<Map<String, Object>> waferInfo = (List<Map<String, Object>>) info
						.get("waferinfo");
				List<WaferInfoDTO> waferInfoList = new ArrayList<WaferInfoDTO>();
				for (Map<String, Object> wafer : waferInfo)
				{
					WaferInfoDTO waferInfoDTO = new WaferInfoDTO();
					waferInfoDTO.setId(Long.parseLong(wafer.get("id")
							.toString()));
					waferInfoDTO.setWaferId(wafer.get("waferId").toString());
					waferInfoDTO.setDieQty(Integer.parseInt(wafer.get(
							"waferQty").toString()));
					waferInfoDTO.setLastModifyDate(nowDate);

					waferInfoList.add(waferInfoDTO);
				}
				waferInfoApplication.updateWaferInfos(waferInfoList);
			}
			if (info != null && info.get("waferInfos") != null)
			{
				List<Map<String, String>> waferInfos = (List<Map<String, String>>) info
						.get("waferInfos");
				List<WaferInfoDTO> waferInfoList = new ArrayList<WaferInfoDTO>();
				int WaferQtyOut = 0;
				int GoodQtyOut = 0;
				int ScrapsQtyOut = 0;
				List<WaferInfoRejectDescriptionDTO> waferInfoRejectDesList = new ArrayList<WaferInfoRejectDescriptionDTO>();
				for(Map<String, String> waferInfo : waferInfos){
					WaferInfoDTO waferInfoDTO = new WaferInfoDTO();
					waferInfoDTO.setId(Long.valueOf(waferInfo.get("id")));
					waferInfoDTO.setDieQty(Integer.valueOf(waferInfo.get("GoodQtyOut")));
					waferInfoDTO.setScrapsQty(Integer.valueOf(waferInfo.get("ScrapsQtyOut")));
					waferInfoList.add(waferInfoDTO);
					WaferQtyOut += 1;
					GoodQtyOut += waferInfoDTO.getDieQty();
					ScrapsQtyOut += Integer.valueOf(waferInfo.get("ScrapsQtyOut"));
					if(waferInfo.get("rejectDes") != null){
						WaferInfoRejectDescriptionDTO waferInfoRejectDescriptionDTO = new WaferInfoRejectDescriptionDTO();
						waferInfoRejectDescriptionDTO.setWaferInfoId(waferInfoDTO.getId());
						waferInfoRejectDescriptionDTO.setWaferProcessId(id);
						waferInfoRejectDescriptionDTO.setRejcetDescription(waferInfo.get("rejectDes").toString());
						waferInfoRejectDesList.add(waferInfoRejectDescriptionDTO);
					}
					if(waferInfoRejectDesList.size() > 0)
						waferInfoRejectDescriptionApplication.saveWaferInfoRejectDescriptions(waferInfoRejectDesList);
				}
				waferProcessDTO.setWaferQtyOut(WaferQtyOut);
				waferProcessDTO.setGoodQtyOut(GoodQtyOut);
				waferProcessDTO.setScrapsQtyOut(ScrapsQtyOut);
				info.put("WaferQtyOut", WaferQtyOut);
				info.put("GoodQtyOut", GoodQtyOut);
				info.put("ScrapsQtyOut", ScrapsQtyOut);
				float goodRate = (float)GoodQtyOut/((float)GoodQtyOut + (float)ScrapsQtyOut);//计算良率
				if(goodRate < 0.99 && waferProcessApplication.checkOutSpecWaferProcessTime(id) == 0){
					return this.outSpec(id, useraccount, "");
				}
				String resCode = waferProcessApplication.nextProcessVerify(info, id);
				if(!resCode.equals("success"))
				{
					result.put("actionError", I18NManager.getMessage(resCode, LOCAL));
					return result;
				}
				
				List<Map<String, String>> rejectCodeItemMaps = (List<Map<String, String>>) info
						.get("rejectCode");
				List<RejectCodeItemDTO> rejectCodeItems = new ArrayList<RejectCodeItemDTO>();
				if(rejectCodeItemMaps != null){
					for (Map<String, String> rejectCodeItemMap : rejectCodeItemMaps)
					{
						RejectCodeItemDTO rejectCodeItemDTO = new RejectCodeItemDTO();
						rejectCodeItemDTO.setItemName(rejectCodeItemMap.get("id")
								.toString());
						if ("".equals(rejectCodeItemMap.get("value").toString()))
						{
							rejectCodeItemDTO.setQty(0);
						}
						else
							rejectCodeItemDTO.setQty(Integer
									.parseInt(rejectCodeItemMap.get("value")
											.toString()));
						rejectCodeItemDTO.setCreateDate(nowDate);
						rejectCodeItemDTO.setLastModifyDate(nowDate);
						rejectCodeItems.add(rejectCodeItemDTO);
					}
					waferProcessDTO.setRejectCodeItemDTOs(rejectCodeItems);
				}
				waferProcessDTO.setWaferInfoDTOs(waferInfoList);
			}
			waferProcessApplication.saveNextProcess(waferProcessDTO);
			result.put("data", "");
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}
	
	private Map<String, Object> outSpec(long waferProcessId,String useraccount,String comments){
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		Date nowDate = new Date();
		waferProcessDTO.setCreateDate(nowDate);
		waferProcessDTO.setLastModifyDate(nowDate);
		waferProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		waferProcessDTO.setUserDTO(optUser);
		waferProcessDTO.setId(waferProcessId);
		waferProcessApplication.outSpecWaferProcess(waferProcessDTO, useraccount,				comments);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", "Out-Spec");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getProcesses")
	public Map<String, Object> get(@RequestParam String ids)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idArray = ids.split(",");
		List<WaferProcessDTO> list = new ArrayList<WaferProcessDTO>();
		for (String id : idArray)
		{
			list.add(waferProcessApplication.getWaferProcess(Long.parseLong(id)));
		}
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		waferProcessDTO.setUserDTO(optUser);
		waferProcessDTO = waferProcessApplication.getMergeWaferProcess(
				waferProcessDTO, ids);
		result.put("data", list);
		result.put("mergeData", waferProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/split")
	public Map<String, Object> splitWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"427\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, Object> splitInfo = (Map<String, Object>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id").toString());
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setUseraccount(useraccount);
//			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			List<String> list = new ArrayList<String>();
			if (splitInfo.get("waferinfo") != null)
			{
				list = (List<String>) splitInfo.get("waferinfo");
			}
			String res = waferProcessApplication.saveSplitWaferProcess(
					waferProcessDTO, useraccount,
					(Integer) splitInfo.get("waferQty"),
					(Integer) splitInfo.get("dieQty"),
					String.valueOf(splitInfo.get("comments")),
					String.valueOf(splitInfo.get("locationIds")), list);
			if(res.equals("success"))
			{
				result.put("result", res);
			}
			else
			{
				result.put("actionError", I18NManager.getMessage(res, LOCAL));
			}
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getSplit")
	public Map<String, Object> getSplitWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"waferQty\":\"1\",\"dieQty\":\"100\",\"comments\":\"comments\",\"locationIds\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> splitInfo = (Map<String, Object>) maps.get("data");
		Long id = Long.valueOf(splitInfo.get("id").toString());
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		Date nowDate = new Date();
		waferProcessDTO.setCreateDate(nowDate);
		waferProcessDTO.setLastModifyDate(nowDate);
		waferProcessDTO.setShippingDate(nowDate);
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		waferProcessDTO.setUserDTO(optUser);
		waferProcessDTO.setId(id);
		waferProcessDTO.setLocationIds(splitInfo.get("locationIds").toString());
		List<WaferProcessDTO> res = waferProcessApplication
				.getSplitWaferProcess(waferProcessDTO,
						(Integer) splitInfo.get("waferQty"),
						(Integer) splitInfo.get("dieQty"));
		result.put("data", splitInfo);
		result.put("splitDate", res);
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/hold")
	public Map<String, Object> holdWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCodeId\":\"RTV\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate;
			if (info.get("holdTime") != null)
			{
				nowDate = MyDateUtils.str2Date(info.get("holdTime").toString(),
						"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			}
			else
			{
				nowDate = new Date();
			}

			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.holdWaferProcess(waferProcessDTO, holdCode,
					holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/engDisp")
	public Map<String, Object> engDispWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			// Date nowDate = new Date();
			Date nowDate = MyDateUtils.str2Date(
					info.get("holdTime").toString(),
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.engDispWaferProcess(waferProcessDTO,
					holdCode, holdCodeId, useraccount,
					String.valueOf(info.get("comments")),
					String.valueOf(info.get("engineerName")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/merge")
	public Map<String, Object> mergeWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"566,570\",\"comments\":\"comments\",\"locationIds\":\"427,428\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
//			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
//			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			String res = waferProcessApplication.saveMergeWaferProcess(
					waferProcessDTO, String.valueOf(maps.get("ids")),
					useraccount, String.valueOf(maps.get("comments")),
					String.valueOf(maps.get("locationIds")));
			if(res.equals("success"))
			{
				result.put("result", res);
			}
			else
			{
				result.put("actionError", I18NManager.getMessage(res, LOCAL));
			}
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getMerge")
	public Map<String, Object> getMergeWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"ids\":\"1129,1128\"}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		Map<String, Object> result = new HashMap<String, Object>();
		String ids = String.valueOf(maps.get("ids"));
		String[] idArray = ids.split(",");
		List<WaferProcessDTO> list = new ArrayList<WaferProcessDTO>();
		for (String id : idArray)
		{
			list.add(waferProcessApplication.getWaferProcess(Long.parseLong(id)));
		}
		WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
		UserDTO optUser = new UserDTO();
		optUser.setUserAccount(AuthUserUtil.getLoginUserName());
		// optUser.setUserAccount("admin");
		waferProcessDTO.setUserDTO(optUser);
		waferProcessDTO = waferProcessApplication.getMergeWaferProcess(
				waferProcessDTO, ids);
		result.put("data", list);
		result.put("mergeData", waferProcessDTO);
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/changeLotType")
	public Map<String, Object> changeLotType(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// String data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"lotTypeId\":\"RTV\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String lotType = splitInfo.get("lotTypeId").toString();
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.changeLotType(waferProcessDTO, lotType,
					useraccount, String.valueOf(splitInfo.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/future")
	public Map<String, Object> futureHoldWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCode\":\"RTV\",\"futureStationId\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			String holdCode = info.get("holdCode").toString();
			String holdCodeId = info.get("holdCodeId");
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			// Date nowDate = MyDateUtils.str2Date(
			// info.get("holdTime").toString(),
			// "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessDTO.setFutureHoldStationId(Long.valueOf(info
					.get("futureStationId")));
			waferProcessApplication.holdWaferProcess(waferProcessDTO, holdCode,
					holdCodeId, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getLotTypes")
	public Map<String, Object> getLotTypes()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication.getLotTypes());
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/changeLocations")
	public Map<String, Object> changeLocations(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// String data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"locationIds\":\"427,428\",\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> splitInfo = (Map<String, String>) maps
					.get("data");
			Long id = Long.parseLong(splitInfo.get("id"));
			String locationIds = splitInfo.get("locationIds").toString();
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.changeLocations(waferProcessDTO,
					locationIds, useraccount,
					String.valueOf(maps.get("comment")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getCusDisps")
	public Map<String, Object> getCusDisps()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.getResourceByParentIdentifier("CusDisp"));
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getEngDisps")
	public Map<String, Object> getEngDisps()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.getResourceByParentIdentifier("EngDisp"));
		result.put("result", "success");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/trackIn")
	public Map<String, Object> trackInWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessDTO.setEquipmentId(Long.parseLong(info
					.get("equipmentId")));
			String res = waferProcessApplication.verifyEquipmentRunningStatusById(waferProcessDTO.getEquipmentId());
			if(!res.equalsIgnoreCase("success"))
			{
				result.put("actionError",I18NManager.getMessage(res, LOCAL));
				return result;
			}
			waferProcessApplication.trackIn(waferProcessDTO, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/abortStep")
	public Map<String, Object> abortStepWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.abortStep(waferProcessDTO, useraccount,
					String.valueOf(info.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/release")
	public Map<String, Object> releaseWaferLot(@RequestParam String data)
			throws JsonParseException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		// data =
		// "{\"userName\":\"admin\",\"password\":\"admin\",\"data\":{\"id\":\"565\",\"holdCodeId\":\"RTV\",\"holdCode\":\"RTV\",\"comments\":\"comments\"}}";
		Map<String, Object> maps = mapper.readValue(data, Map.class);
		String useraccount = maps.get("userName").toString();
		String password = maps.get("password").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		if (this.passwordCheck(useraccount, password))
		{
			Map<String, String> info = (Map<String, String>) maps.get("data");
			Long id = Long.parseLong(info.get("id"));
			WaferProcessDTO waferProcessDTO = new WaferProcessDTO();
			Date nowDate = new Date();
			waferProcessDTO.setCreateDate(nowDate);
			waferProcessDTO.setLastModifyDate(nowDate);
			waferProcessDTO.setShippingDate(nowDate);
			UserDTO optUser = new UserDTO();
			optUser.setUserAccount(AuthUserUtil.getLoginUserName());
			// optUser.setUserAccount("admin");
			waferProcessDTO.setUserDTO(optUser);
			waferProcessDTO.setId(id);
			waferProcessApplication.release(waferProcessDTO, useraccount, String.valueOf(info.get("comments")));
			result.put("result", "success");
		}
		else
		{
			result.put("data", "");
			result.put("result", "fail");
		}
		return result;
	}

	private boolean rightCheck(String useraccount, String identifier)
	{
		List<RoleVO> roleList = roleApplication
				.findRoleByUserAccount(useraccount);
		for (RoleVO roleVO : roleList)
		{
			List<ResourceVO> resList = roleApplication
					.findResourceByRole(roleVO);
			for (ResourceVO resourceVO : resList)
			{
				if (resourceVO.getIdentifier().equals(identifier))
				{
					return true;
				}
			}
		}
		return false;
	}

	private String resCheckMsg(String useraccount, String password,
			String identifier)
	{
		if (!this.passwordCheck(useraccount, password))
		{
			return MSG_FAIL;
		}
		if ("admin".equals(useraccount))
		{
			return MSG_SUCCESS;
		}
		if (!this.rightCheck(useraccount, identifier))
		{
			return MSG_NO_RIGHT;
		}
		return MSG_SUCCESS;
	}

	@ResponseBody
	@RequestMapping("/Process/getHoldTime/{id}")
	public Map<String, Object> findOptLogByWaferProcess(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("holdTime", MyDateUtils.formaterDate(waferProcessApplication
				.findWaferStatusOptLogByProcess(id, false).getCreateDate(),
				MyDateUtils.formater));
		return result;
	}

	@ResponseBody
	@RequestMapping("/getFutureStations/{id}")
	public Map<String, Object> getFutureStations(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data",
				waferProcessApplication.findAfterStationsByProcessId(id));
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getWaferLot")
	public Map<String, Object> getWaferLotBeforeDA()
	{
		Map<String, Object> result = new HashMap<String, Object>();
		Long id = (long) 203;
		List<WaferProcessDTO> list = waferProcessApplication
				.findWaferLotBeforeDA(id);
		if (list != null && list.size() > 0)
		{
			result.put("data", list);
			result.put("result", "success");
		}
		else
			result.put("result", "fail");
		return result;
	}

	@ResponseBody
	@RequestMapping("/Process/getProductNameByWaferProcessId/{id}")
	public Map<String, Object> getProductNameByWaferProcessId(
			@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		String productName = waferProcessApplication
				.getProductNameByWaferProcessId(id);
		if (productName != null)
		{
			result.put("data", productName);
			result.put("result", "success");
		}
		else
			result.put("result", "fail");
		return result;
	}

	@ResponseBody
	@RequestMapping("/getHoldReason/{id}")
	public Map<String, Object> getHoldReason(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.findWaferStatusOptLogByProcess(id, false));
		return result;
	}

	@ResponseBody
	@RequestMapping("/getFutureHoldReason/{id}")
	public Map<String, Object> getFutureHoldReason(@PathVariable Long id)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", waferProcessApplication
				.findWaferStatusOptLogByProcess(id, true));
		return result;
	}

}
