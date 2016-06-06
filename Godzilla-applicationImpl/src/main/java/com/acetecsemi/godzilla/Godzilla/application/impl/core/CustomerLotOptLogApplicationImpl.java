package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acetecsemi.godzilla.Godzilla.application.core.CustomerLotOptLogApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.CustomerLotOptLogDTO;
import com.acetecsemi.godzilla.Godzilla.core.CustomerLotOptLog;

@Named
@Transactional
public class CustomerLotOptLogApplicationImpl implements CustomerLotOptLogApplication {
	
	private QueryChannelService queryChannel;

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}
	
	public CustomerLotOptLogDTO saveCustomerLotOptLog( CustomerLotOptLogDTO customerLotOptLogDTO) {
		CustomerLotOptLog customerLotOptLog = new CustomerLotOptLog();
		User user = null;
		if(customerLotOptLogDTO.getLoginUser() == null ){
			user = User.findByUserAccount("1");
		}else
			user = User.findByUserAccount(customerLotOptLogDTO.getLoginUser().getUserAccount());
		customerLotOptLog.setCreateUser(user);
		try {
			BeanUtils.copyProperties(customerLotOptLog,
					customerLotOptLogDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		customerLotOptLog.setOptUser(User.findByUserAccount(customerLotOptLogDTO.getOptUser()).getId());
		customerLotOptLog.save();
		customerLotOptLogDTO.setId((java.lang.Long) customerLotOptLog
				.getId());
		return customerLotOptLogDTO;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean getCustomerLotOptLogDTOByProperties(Long id, String tableName) {
		Map<String,Object> conditionVals = new HashMap<String,Object>();
		conditionVals.put("custlotId", id);
		conditionVals.put("custlotTableName", tableName);
		conditionVals.put("description", "Hold to Waiting");
		conditionVals.put("optName", "modify");
		boolean flag = false;
		List<CustomerLotOptLog> list = CustomerLotOptLog.findByProperties(CustomerLotOptLog.class, conditionVals);
		if(list != null && list.size() > 0)
		{
			flag = true;
		}
		return flag;
	}
}
