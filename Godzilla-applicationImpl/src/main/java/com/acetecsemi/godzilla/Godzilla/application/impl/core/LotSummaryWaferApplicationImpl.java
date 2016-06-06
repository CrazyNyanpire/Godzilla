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
import com.acetecsemi.godzilla.Godzilla.application.core.LotSummaryWaferApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class LotSummaryWaferApplicationImpl implements LotSummaryWaferApplication {

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
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class, id);

		String jpql = "select o from WaferProcess o where o.waferCompanyLot.lotNo=? order by o.station.sequence";
		List<WaferProcess> result = (List<WaferProcess>) getQueryChannelService()
				.createJpqlQuery(jpql)
				.setParameters(
						new Object[] { waferProcess.getWaferCompanyLot()
								.getLotNo() }).list();
		Map<String, List<Map<String, Object>>> stationParent = new HashMap<String, List<Map<String, Object>>>();
		if (result != null) {
			for (WaferProcess wp : result) {
				//if(wp.getWaferCompanyLot().getParentWaferCompanyLot() != null){
				//	break;
				//}
				List<Map<String, Object>> list = stationParent.get(wp
						.getStation().getParentStation().getStationName());
				if (list == null) {
					list = new ArrayList<Map<String, Object>>();
				}
				Map<String, Object> station = new HashMap<String, Object>();
				station.put("id", wp.getId());
				station.put("name",
						wp.getStation().getStationName().split("-")[1]);
				if (this.getLotHold(wp.getId()).size() > 0) {
					station.put("isHold", "1");
				} else {
					station.put("isHold", "0");
				}
				if (wp.getStatus().equals("Split")) {
					station.put("isSplit", "1");
				} else {
					station.put("isSplit", "0");
				}
				if (wp.getStatus().equals("Merge")) {
					station.put("isMerge", "1");
				} else {
					station.put("isMerge", "0");
				}
				station.put("isConsume", "0");
				Map<String, String> input = new HashMap<String, String>();
				input.put("inDate",
						MyDateUtils.formaterDate(wp.getCreateDate(), formater));
				input.put("TrackIn", MyDateUtils.formaterDate(this
						.findWaferTrackInOptLogByProcess(wp.getId())
						.getCreateDate(), formater));
				input.put("InQty", String.valueOf(wp.getQtyIn()));
				station.put("inPut", input);
				Map<String, String> output = new HashMap<String, String>();
				output.put("outDate", MyDateUtils.formaterDate(
						wp.getLastModifyDate(), formater));
				if (wp.getEquipment() != null) {
					output.put("equip", wp.getEquipment().getEquipment());
				} else {
					output.put("equip", "");
				}
				output.put("outQty", wp.getQtyOut().toString());
				if (wp.getScrapsQtyOut() != null) {
					output.put("rejQty", wp.getScrapsQtyOut().toString());
				} else {
					output.put("rejQty", "-");
				}
				station.put("outPut", output);
				list.add(station);
				stationParent.put(wp.getStation().getParentStation()
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
	public WaferStatusOptLogDTO findWaferTrackInOptLogByProcess(Long id) {
		String jpql = "select o from WaferStatusOptLog o inner join o.waferProcess e where e.id=? and o.holdCode = 'Running' ";
		jpql += "order by o.createDate desc";
		WaferStatusOptLog result = (WaferStatusOptLog) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.singleResult();
		WaferStatusOptLogDTO dto = new WaferStatusOptLogDTO();
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
		String jpql = "select o from WaferStatusOptLog o inner join o.waferProcess e where e.id=? and (o.holdCode = 'Customer Disposition' or o.holdCode = 'Engin. Disposition' or o.holdCode = 'Waiting')";
		jpql += "order by o.createDate asc";
		List<WaferStatusOptLog> result = (List<WaferStatusOptLog>) getQueryChannelService()
				.createJpqlQuery(jpql).setParameters(new Object[] { id })
				.list();
		List<Map<String, Object>> holdList = new ArrayList<Map<String, Object>>();
		if (result != null) {
			for (int i = 0; i < result.size(); i++) {
				Map<String, Object> hold = new HashMap<String, Object>();
				WaferStatusOptLog wol = result.get(i);
				if (wol.getHoldCode().equals("Customer Disposition")
						|| wol.getHoldCode().equals("Engin. Disposition")) {
					hold.put("holdDate", MyDateUtils.formaterDate(
							wol.getCreateDate(), formater));
					hold.put("holdUser", wol.getOptLog().getRightUserUser().getName());
					hold.put("holdComments", wol.getOptLog().getComments());
					i = i + 1;
					if (i < result.size()) {
						WaferStatusOptLog wolRelease = result.get(i);
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
		WaferProcess waferProcess = WaferProcess.get(WaferProcess.class, id);
		String jpql = "select o from WaferCompanyLot o  where o.mergeWaferCompanyLot.id=? ";
		List<WaferCompanyLot> result = (List<WaferCompanyLot>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { waferProcess.getWaferCompanyLot().getMergeWaferCompanyLot().getId() }).list();
		List<Map<String, Object>> mergeList = new ArrayList<Map<String, Object>>();
		for(WaferCompanyLot wcl : result){
			Map<String, Object> merge = new HashMap<String, Object>();
			WaferProcessDTO waferProcessDTO = this.findWaferProcessByWaferCompanyLotId(wcl.getId());
			merge.put("id", waferProcessDTO.getId());
			merge.put("mergeLot", wcl.getLotNo());
			merge.put("user", this.findMergeUser(id));
			merge.put("mergeDate", MyDateUtils.formaterDate(
					wcl.getLastModifyDate(), formater));
			merge.put("mergeQty", waferProcessDTO.getQtyOut());
			mergeList.add(merge);
		}
		return mergeList;
	}
	
	private String findMergeUser(Long id){
		String jpql = "select o from WaferOptLog o  where o.waferProcess.id=? and (o.type = 'Merge' or o.type = 'merge') ";
		WaferOptLog result = (WaferOptLog) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		return result.getOptLog().getRightUserUser().getName();
	}

	private WaferProcessDTO findWaferProcessByWaferCompanyLotId(Long id){
		String jpql = "select o from WaferProcess o  where o.waferCompanyLot.id=? order by o.createDate asc";
		WaferProcess result = (WaferProcess) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		WaferProcessDTO  waferProcessDTO =  new WaferProcessDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(waferProcessDTO, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return waferProcessDTO;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotSplit(Long id) {
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, id);
		String jpql = "select o from WaferCompanyLot o  where o.parentWaferCompanyLot.id=? ";
		List<WaferCompanyLot> result = (List<WaferCompanyLot>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { waferProcess.getWaferCompanyLot().getId() }).list();
		List<Map<String, Object>> splitList = new ArrayList<Map<String, Object>>();
		
		for(WaferCompanyLot wcl : result){
			Map<String, Object> split = new HashMap<String, Object>();
			WaferProcessDTO waferProcessDTO = this.findWaferProcessByWaferCompanyLotId(wcl.getId());
			split.put("id", waferProcessDTO.getId());
			split.put("splitLot", wcl.getLotNo());
			split.put("user", this.findSplitUser(id));
			split.put("splitDate", MyDateUtils.formaterDate(
					wcl.getLastModifyDate(), formater));
			split.put("splitQty", waferProcessDTO.getQtyIn());
			splitList.add(split);
		}
		return splitList;
	}
	
	private String findSplitUser(Long id){
		String jpql = "select o from WaferOptLog o  where o.waferProcess.id=? and (o.type = 'Split' or o.type = 'split') ";
		WaferOptLog result = (WaferOptLog) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { id }).singleResult();
		return result.getOptLog().getRightUserUser().getName();
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map<String, Object>> getLotReject(Long id) {
		WaferProcess waferProcess = WaferProcess.load(WaferProcess.class, id);
		String jpql = "select distinct o from WaferCompanyLot o  where o.fromWaferProcess.id=? ";
		List<WaferCompanyLot> result = (List<WaferCompanyLot>) getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[] { waferProcess.getId() }).list();
		List<Map<String, Object>> rejectList = new ArrayList<Map<String, Object>>();
		for(WaferCompanyLot wcl : result){
			Map<String, Object> rejectMap = new HashMap<String, Object>();
			List<Map<String, Object>> waferInfoList = new ArrayList<Map<String, Object>>();
			rejectMap.put("lotNo", wcl.getLotNo());
			for(WaferInfo wi : wcl.getWaferInfos()){
				Map<String, Object> waferInfoMap = new HashMap<String, Object>();
				waferInfoMap.put("order", wi.getWaferNumber());
				waferInfoMap.put("waferId", wi.getWaferId());
				waferInfoMap.put("dieQty", wi.getDieQty());
				waferInfoList.add(waferInfoMap);
			}
			rejectMap.put("waferInfos", waferInfoList);
			rejectList.add(rejectMap);
		}
		return rejectList;
	}

}
