package com.acetecsemi.godzilla.Godzilla.infra.quartz.demo;

import java.util.Date;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.dayatang.querychannel.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acetecsemi.godzilla.Godzilla.application.core.WaferCustomerLotApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.WaferCustomerLotDTO;

@Component
public class WaferCustomerLotDelayTime {

	Logger log = Logger.getLogger(WaferCustomerLotDelayTime.class);
	
	@Inject
	WaferCustomerLotApplication waferCustomerLotApplication;
	
	//@Scheduled(cron="40 32 14 * * ?") 
	public void countDelay(){
		System.out.println("================" + System.currentTimeMillis() + " <<<===>>> helloQuartz ================");
		WaferCustomerLotDTO waferCustomerLot = new WaferCustomerLotDTO(); 
		waferCustomerLot.setCurrStatus("'Waiting'");
		int totalSize = waferCustomerLotApplication.waferCustomerLotTotal(waferCustomerLot);
		int pageSize = 2;
		Page<WaferCustomerLotDTO> pg = null;
		Date date = new Date();
		long time = 0;
		for(int i = 0; i < totalSize/pageSize; i++)
		{
			pg = waferCustomerLotApplication.pageQueryWaferCustomerLot(waferCustomerLot, i, pageSize);
			for(WaferCustomerLotDTO w:pg.getData())
			{
//				Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。   
//				cal.setTime(w.getShippingDate());
//				cal.add(Calendar.DAY_OF_MONTH, +5);
				time = (date.getTime() - w.getShippingDate().getTime())/1000/3600;
				w.setDelayTime(time);
				w.setCurrStatus("Eng. Disp");
				updateDelayTime(w);
			}
		}
		
	}
	@Transactional(propagation = Propagation.REQUIRED)
	private void updateDelayTime(WaferCustomerLotDTO w)
	{
		waferCustomerLotApplication.updateWaferCustomerLot(w);
	}
}
