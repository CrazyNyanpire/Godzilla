package com.acetecsemi.godzilla.Godzilla.infra.quartz.demo;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.dayatang.querychannel.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.acetecsemi.godzilla.Godzilla.application.core.ManufactureProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.MaterialProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.StationApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.WaferProcessApplication;
import com.acetecsemi.godzilla.Godzilla.application.dto.ManufactureProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.MaterialProcessDTO;
import com.acetecsemi.godzilla.Godzilla.application.dto.StationAlertDTO;

@Component
public class ProcessDelayTime
{
	Logger							log	= Logger.getLogger(ProcessDelayTime.class);

	@Inject
	ManufactureProcessApplication	manufactureProcessApplication;
	@Inject
	WaferProcessApplication			waferProcessApplication;
	@Inject
	StationApplication stationApplication;
	@Inject
	MaterialProcessApplication materialProcessApplication;

	/**
	 * 更新批次信息的状态，如果超时则hold
	 * 比较预设的时间
	 * 1、如果是供料时，比较shippingtime超时N天，N=5，则自动hold,即将其状态修改为hold
	 * 2、如果是晶圆预制程即preassy，比较进站时间即creattime
	 * 3、如果是生产流程即manufactureProcess，首先根据stationAlert做为外循环，
	 * 3.1根据进站stationAlert.outid、查找当前状态为waiting\running\merge\split状态的manufactureProcess，
	 * 3.2然后遍历这些manufactureProcess做为第二层循环，取出manufactureProcess。createtime保存，
	 * 3.3然后根据manufactureProcess.lotno,stationAlert.inid,取出上一站的createtime
	 * 3.4计算2次的createtime的值与startAlert.alerttime比较，如果超时则hold
	 * **/
	//@Scheduled(cron = "40 52 14 * * ?")
	public void countDelay()
	{
		System.out.println("================" + System.currentTimeMillis()
				+ " <<<===>>> helloQuartz ================");

		
		List<StationAlertDTO> alertList = getStationAlerts();
		for(StationAlertDTO alert:alertList)
		{
			if(alert.getType().equalsIgnoreCase("assly"))
			{
				updateManufactureProcess(alert.getStationOutId(),alert.getStationInId(),alert.getAlertHour());
			}
			if(alert.getType().equalsIgnoreCase("material"))
			{
				updateMaterialProcess(alert.getMaterialPartType(),alert.getStationOutId(),alert.getStationInId(),alert.getAlertHour());
			}
		}
	}
	
	private List<StationAlertDTO> getStationAlerts()
	{
		List<StationAlertDTO> list = stationApplication.findAllStationAlert();
		return list;
	}

	private void updateMaterialProcess(String materialType, Long stationOutId,Long stationInId,int alertTime)
	{
		MaterialProcessDTO materialProcessDTO = new MaterialProcessDTO();
		materialProcessDTO.setCurrStatus("'Waiting','Running','Merge','Split'");
		materialProcessDTO.setStationId(stationOutId);
		Page<MaterialProcessDTO> materialProcesses = materialProcessApplication.pageQueryMaterialProcess(materialProcessDTO,0,10);
		Date date = new Date();
		for(MaterialProcessDTO process: materialProcesses.getData())
		{
			String partType = process.getPartId().substring(2, 3);//根据物料编码规则，第3-4位代表物料类型，银胶EY，锡膏SP
			if(partType.equalsIgnoreCase(materialType))
			{
				long time = (date.getTime() - process.getCreateDate().getTime())/1000/3600;
				if(time > alertTime)
				{
					process.setCurrStatus("Eng. Disp");
					process.setDelayTime((int)time);
					materialProcessApplication.updateMaterialProcess(process);
				}
			}
		}
	}
	private void updateManufactureProcess(Long stationOutId,Long stationInId,int alertTime)
	{
		ManufactureProcessDTO manufactureProcessDTO = new ManufactureProcessDTO();
		Page<ManufactureProcessDTO> manufactureProcessPg = manufactureProcessApplication
				.pageQueryManufactureProcess(manufactureProcessDTO, 0, 1);
		manufactureProcessDTO.setCurrStatus("'Waiting','Running','Merge','Split'");
		manufactureProcessDTO.setStationId(stationInId);
		manufactureProcessPg = manufactureProcessApplication
				.pageQueryManufactureProcess(manufactureProcessDTO, 0, (int)manufactureProcessPg.getResultCount());
		
		long recordSizePerPage = 10;
//		long totalSize = manufactureProcessPg.getResultCount();
		//long pageSize = totalSize > recordSizePerPage? (totalSize-1)/recordSizePerPage+1:1;
		
		for(ManufactureProcessDTO mp:manufactureProcessPg.getData())
		{
			ManufactureProcessDTO manufactureProcessDTOPrev = new ManufactureProcessDTO();
			manufactureProcessDTOPrev.setStationId(stationOutId);
			manufactureProcessDTOPrev.setLotNo(mp.getLotNo());
			manufactureProcessDTOPrev.setCurrStatus("'Finish'");
			Page<ManufactureProcessDTO> manufactureProcessPrevPg = manufactureProcessApplication
					.pageQueryManufactureProcess(manufactureProcessDTOPrev, 0, (int)recordSizePerPage);
			if(manufactureProcessPrevPg.getData() != null && manufactureProcessPrevPg.getData().size() == 1)
			{
				ManufactureProcessDTO prev = manufactureProcessPrevPg.getData().get(0);
				long hours = ( mp.getCreateDate().getTime() - prev.getCreateDate().getTime())/1000/3600;
//				hours = Math.abs(hours);
				if(hours > alertTime)
				{
					mp.setCurrStatus("Eng. Disp");
					mp.setDelayTime((int)hours);
					manufactureProcessApplication.updateManufactureProcess(mp);
				}
			}
		}
	}
}
