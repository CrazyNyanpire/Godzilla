package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.BeanUtilsExtends;
import com.acetecsemi.godzilla.Godzilla.application.utils.MyDateUtils;
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryManufactureApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class LotSummaryManufactureApplicationImpl implements LotSummaryManufactureApplication {

	private QueryChannelService queryChannel;

	public static final String formater = "yyyy-MM-dd HH:mm";

	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map.Entry<String, List<Map<String, Object>>>> getLotSummary(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.get(ManufactureProcess.class, id);

		String jpql = "select o from ManufactureProcess o where o.manufactureLot.lotNo=? order by o.station.sequence";
		List<ManufactureProcess> result = (List<ManufactureProcess>) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(
						new Object[] { manufactureProcess.getManufactureLot()
								.getLotNo() }).list();
		Map<String, List<Map<String, Object>>> stationParent = new HashMap<String, List<Map<String, Object>>>();
		if (result != null) {
			for (ManufactureProcess mp : result) {
				//if(wp.getManufactureLot().getParentManufactureLot() != null){
				//	break;
				//}
				List<Map<String, Object>> list = stationParent.get(mp
						.getStation().getParentStation().getStationName());
				if (list == null) {
					list = new ArrayList<Map<String, Object>>();
				}
				Map<String, Object> station = new HashMap<String, Object>();
				station.put("id", mp.getId());
				station.put("name",
						mp.getStation().getStationName().split("-")[1]);
				if (this.getLotHold(mp.getId()).size() > 0) {
					station.put("isHold", "1");
				} else {
					station.put("isHold", "0");
				}
				if (mp.getStatus().equals("Split")) {
					station.put("isSplit", "1");
				} else {
					station.put("isSplit", "0");
				}
				if (mp.getStatus().equals("Merge")) {
					station.put("isMerge", "1");
				} else {
					station.put("isMerge", "0");
				}
				if(this.getLotDebit(mp.getId()).size() > 0 )
					station.put("isConsume", "1");
				else
					station.put("isConsume", "0");
				Map<String, String> input = new HashMap<String, String>();
				input.put("inDate",
						MyDateUtils.formaterDate(mp.getCreateDate(), formater));
				input.put("TrackIn", MyDateUtils.formaterDate(this
						.findManufactureTrackInOptLogByProcess(mp.getId())
						.getCreateDate(), formater));
				input.put("InQty", String.valueOf(mp.getQtyIn()));
				station.put("inPut", input);
				Map<String, String> output = new HashMap<String, String>();
				output.put("outDate", MyDateUtils.formaterDate(
						mp.getLastModifyDate(), formater));
				if (mp.getEquipment() != null) {
					output.put("equip", mp.getEquipment().getEquipment());
				} else {
					output.put("equip", "");
				}
				if(mp.getQtyOut() != null)
					output.put("outQty", mp.getQtyOut().toString());
				else
					output.put("outQty", "-");
				if (mp.getScrapsQtyOut() != null) {
					output.put("rejQty", mp.getScrapsQtyOut().toString());
				} else {
					output.put("rejQty", "-");
				}
				station.put("outPut", output);
				list.add(station);
				stationParent.put(mp.getStation().getParentStation()
						.getStationName(), list);
			}

		}
		return this.sortMap(stationParent);
	}
	
	private List<Map.Entry<String, List<Map<String, Object>>>> sortMap(
			Map<String, List<Map<String, Object>>> stationParent) {
		List<Map.Entry<String, List<Map<String, Object>>>> mappingList = null;

		// 通过ArrayList构造函数把map.entrySet()转换成list
		mappingList = new ArrayList<Map.Entry<String, List<Map<String, Object>>>>(
				stationParent.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(mappingList,
				new Comparator<Map.Entry<String, List<Map<String, Object>>>>() {
					public int compare(
							Map.Entry<String, List<Map<String, Object>>> mapping1,
							Map.Entry<String, List<Map<String, Object>>> mapping2) {
						return mapping1.getValue().get(0).get("id").toString()
								.compareTo(mapping2.getValue().get(0).get("id").toString());
					}
				});
		return mappingList;
	}


	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ManufactureStatusOptLogDTO findManufactureTrackInOptLogByProcess(Long id) {
		String jpql = "select o from ManufactureStatusOptLog o inner join o.manufactureProcess e where e.id=? and o.holdCode = 'Running' ";
		jpql += "order by o.createDate desc";
		ManufactureStatusOptLog result = (ManufactureStatusOptLog) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		ManufactureStatusOptLogDTO dto = new ManufactureStatusOptLogDTO();
		if (result != null) {
			try {
				BeanUtilsExtends.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotHold(Long id) {
		String jpql = "select o from ManufactureStatusOptLog o inner join o.manufactureProcess e where e.id=? and (o.holdCode = 'Customer Disposition' or o.holdCode = 'Engin. Disposition' or o.holdCode = 'Waiting')";
		jpql += "order by o.createDate asc";
		List<ManufactureStatusOptLog> result = (List<ManufactureStatusOptLog>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.list();
		List<Map<String, Object>> holdList = new ArrayList<Map<String, Object>>();
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> hold = new HashMap<String, Object>();
				ManufactureStatusOptLog wol = result.get(i);
				if (wol.getHoldCode().equals("Customer Disposition")
						|| wol.getHoldCode().equals("Engin. Disposition")) {
					hold.put("holdDate", MyDateUtils.formaterDate(
							wol.getCreateDate(), formater));
					hold.put("holdUser", wol.getOptLog().getRightUserUser().getName());
					hold.put("holdComments", wol.getOptLog().getComments());
					i = i + 1;
					if (i < result.size()) {
						ManufactureStatusOptLog wolRelease = result.get(i);
						hold.put("releaseDate", MyDateUtils.formaterDate(
								wolRelease.getCreateDate(), formater));
						hold.put("releaseUser", wolRelease.getOptLog()
								.getRightUserUser().getName());
						hold.put("releaseComments", wolRelease.getOptLog()
								.getComments());
					}
					holdList.add(hold);
				}
			}
		}
		return holdList;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotMerge(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.get(ManufactureProcess.class, id);
		String jpql = "select o from ManufactureLot o  where o.mergeManufactureLot.id=? ";
		List<ManufactureLot> result = (List<ManufactureLot>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { manufactureProcess.getManufactureLot().getMergeManufactureLot().getId() }).list();
		List<Map<String, Object>> mergeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> merge = new HashMap<String, Object>();
		for(ManufactureLot wcl : result){
			ManufactureProcessDTO manufactureProcessDTO = this.findManufactureProcessByManufactureLotId(wcl.getId());
			merge.put("id", manufactureProcessDTO.getId());
			merge.put("mergeLot", wcl.getLotNo());
			merge.put("user", this.findMergeUser(id));
			merge.put("mergeDate", MyDateUtils.formaterDate(
					wcl.getLastModifyDate(), formater));
			merge.put("mergeQty", manufactureProcessDTO.getQtyIn());
			mergeList.add(merge);
		}
		return mergeList;
	}
	
	private String findMergeUser(Long id){
		String jpql = "select o from ManufactureOptLog o  where o.manufactureProcess.id=? and (o.type = 'Merge' or o.type = 'merge') ";
		ManufactureOptLog result = (ManufactureOptLog) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		return result.getOptLog().getRightUserUser().getName();
	}

	private ManufactureProcessDTO findManufactureProcessByManufactureLotId(Long id){
		String jpql = "select o from ManufactureProcess o  where o.manufactureLot.id=? order by o.createDate asc";
		ManufactureProcess result = (ManufactureProcess) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		ManufactureProcessDTO  manufactureProcessDTO =  new ManufactureProcessDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(manufactureProcessDTO, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return manufactureProcessDTO;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotSplit(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.load(ManufactureProcess.class, id);
		String jpql = "select o from ManufactureLot o  where o.parentManufactureLot.id=? ";
		List<ManufactureLot> result = (List<ManufactureLot>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { manufactureProcess.getManufactureLot().getId() }).list();
		List<Map<String, Object>> splitList = new ArrayList<Map<String, Object>>();
		
		for(ManufactureLot wcl : result){
			Map<String, Object> split = new HashMap<String, Object>();
			ManufactureProcessDTO manufactureProcessDTO = this.findManufactureProcessByManufactureLotId(wcl.getId());
			split.put("id", manufactureProcessDTO.getId());
			split.put("splitLot", wcl.getLotNo());
			split.put("user", this.findSplitUser(id));
			split.put("splitDate", MyDateUtils.formaterDate(
					wcl.getLastModifyDate(), formater));
			split.put("splitQty", manufactureProcessDTO.getQtyIn());
			splitList.add(split);
		}
		return splitList;
	}
	
	private String findSplitUser(Long id){
		String jpql = "select o from ManufactureOptLog o  where o.manufactureProcess.id=? and (o.type = 'Split' or o.type = 'split') ";
		ManufactureOptLog result = (ManufactureOptLog) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		return result.getOptLog().getRightUserUser().getName();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotReject(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.load(ManufactureProcess.class, id);
		String jpql = "select distinct o from ManufactureLot o  where o.fromManufactureProcess.id=? ";
		List<ManufactureLot> result = (List<ManufactureLot>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { manufactureProcess.getId() }).list();
		List<Map<String, Object>> rejectList = new ArrayList<Map<String, Object>>();
		for(ManufactureLot wcl : result){
			Map<String, Object> rejectMap = new HashMap<String, Object>();
			List<Map<String, Object>> manufactureInfoList = new ArrayList<Map<String, Object>>();
			rejectMap.put("lotNo", wcl.getLotNo());
			for(RejectCodeItem rejectCodeItem : manufactureProcess.getRejectCodeItems()){
				Map<String, Object> rejectCodeItemMap = new HashMap<String, Object>();
				rejectCodeItemMap.put("itemName", rejectCodeItem.getItemName());
				rejectCodeItemMap.put("qty", rejectCodeItem.getQty());
				manufactureInfoList.add(rejectCodeItemMap);
			}
			rejectMap.put("rejectCodeItems", manufactureInfoList);
			rejectList.add(rejectMap);
		}
		return rejectList;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotDebit(Long id) {
		ManufactureProcess manufactureProcess = ManufactureProcess.load(ManufactureProcess.class, id);
		String jpql = "select o from ManufactureDebitWaferProcess o  where o.manufactureProcess.id=? ";
		List<ManufactureDebitWaferProcess> result = (List<ManufactureDebitWaferProcess>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { manufactureProcess.getId() }).list();
		List<Map<String, Object>> debitList = new ArrayList<Map<String, Object>>();
		for(ManufactureDebitWaferProcess mdwp : result){
			Map<String, Object> debitMap = new HashMap<String, Object>();
			debitMap.put("id", mdwp.getWaferProcess().getId());
			debitMap.put("lotNo", mdwp.getWaferProcess().getWaferCompanyLot().getLotNo());
			debitMap.put("materialType", "Wafer");
			debitMap.put("qty", mdwp.getQty());
			debitMap.put("user", mdwp.getQty());
			debitMap.put("date", MyDateUtils.formaterDate(
					mdwp.getCreateDate(), formater));
			debitMap.put("partN.", mdwp.getWaferProcess().getWaferCompanyLot().getWaferCustomerLot().getPart().getPartNo());
			debitList.add(debitMap);
		}
		for(ManufactureDebitProcess mdp : manufactureProcess.getManufactureDebitProcesses()){
			Map<String, Object> debitMap = new HashMap<String, Object>();
			debitMap.put("id", mdp.getMaterialProcess().getId());
			debitMap.put("lotNo", mdp.getMaterialProcess().getMaterialCompanyLot().getLotNo());
			debitMap.put("materialType", "Material");
			debitMap.put("qty", mdp.getQty());
			debitMap.put("user", mdp.getQty());
			debitMap.put("date",  MyDateUtils.formaterDate(
					mdp.getCreateDate(), formater));
			debitMap.put("partN.", mdp.getMaterialProcess().getMaterialCompanyLot().getMaterialLot().getMaterial().getPartId());
			debitList.add(debitMap);
		}
		return debitList;
	}

}
