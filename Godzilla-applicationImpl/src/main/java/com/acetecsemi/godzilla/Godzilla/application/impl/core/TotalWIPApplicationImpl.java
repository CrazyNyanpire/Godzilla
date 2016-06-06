package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.acetecsemi.godzilla.Godzilla.application.core.TotalWIPApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class TotalWIPApplicationImpl implements TotalWIPApplication {

	private QueryChannelService queryChannel;

	public static final String formater = "yyyy-MM-dd HH:mm";
	
	public static final String out1stStart = " 07:30:00";
	public static final String out1stEnd = " 19:29:59";
	public static final String out2ndStart = " 19:30:00";
	public static final String out2ndEnd = " 07:29:59";
	
	private QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(
					QueryChannelService.class, "queryChannel");
		}
		return queryChannel;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map.Entry<String, List<Map<String, Object>>>> getNowTotalWIP(Map<String, Object> maps) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select b.ID,c.STATIONNAME PSTATIONNAME,b.STATIONNAME,b.SEQUENCE,")
			.append("SUM (CASE WHEN STATUS = 'Waiting' THEN a.QTYIN  ELSE 0 END) Waiting,")
			.append("SUM (CASE WHEN STATUS = 'Running' THEN a.QTYIN  ELSE 0 END) Running,")
			.append("SUM (CASE WHEN STATUS = 'Out-Spec' THEN a.QTYIN  ELSE 0 END) OutSpec,")
			.append("SUM (CASE WHEN STATUS = 'Cus. Disp' THEN a.QTYIN  ELSE 0 END) CusDisp,")
			.append("SUM (CASE WHEN STATUS = 'Eng. Disp' THEN a.QTYIN  ELSE 0 END) EngDisp,")
			.append("SUM (CASE WHEN a.RESOURCE_LOTTYPE_ID = 826 OR a.RESOURCE_LOTTYPE_ID = 828 THEN a.QTYIN  ELSE 0 END) SWR,")
			.append("SUM (CASE WHEN STATUS = 'Finish' and to_char (a.LAST_MODIFY_DATE,'YYYY-MM-DD HH12:MI:SS') BETWEEN ? and ? THEN a.QTYOUT  ELSE 0 END) OutYesAll,")
			.append("SUM (CASE WHEN STATUS = 'Finish' and to_char (a.LAST_MODIFY_DATE,'YYYY-MM-DD HH12:MI:SS') BETWEEN ? and ? THEN a.QTYOUT  ELSE 0 END) Out1ST,")
			.append("SUM (CASE WHEN STATUS = 'Finish' and to_char (a.LAST_MODIFY_DATE,'YYYY-MM-DD HH12:MI:SS') BETWEEN ? and ? THEN a.QTYOUT  ELSE 0 END) Out2ST,")
			.append("SUM (CASE WHEN STATUS = 'Pass' THEN a.QTYIN  ELSE 0 END) IQCPass," )
			.append("SUM (CASE WHEN STATUS = 'Hold' THEN a.QTYIN  ELSE 0 END) Hold,")
			.append("SUM (CASE WHEN STATUS = 'Finish' THEN 1  ELSE NULL END) Finish    ")
			.append("from GODZILLA_PROCESS a INNER JOIN GODZILLA_STATION b on a.STATION_ID = b.ID INNER JOIN GODZILLA_STATION c on b.PARENT_STATION_ID = c.ID ")
			.append("GROUP BY c.STATIONNAME,b.ID,b.STATIONNAME,b.SEQUENCE ")
			.append("ORDER BY b.SEQUENCE");
		List<Object[]> result = (List<Object[]>) getQueryChannelService()
				.createSqlQuery(sql.toString())
				.setParameters(this.getDateList()).list();
		Map<String, List<Map<String, Object>>> stationParent = new HashMap<String, List<Map<String, Object>>>();
		if (result != null) {
			for (Object[] objects : result) {
				List<Map<String, Object>> list = stationParent.get(objects[1].toString());
				if (list == null) {
					list = new ArrayList<Map<String, Object>>();
				}
				Map<String, Object> station = new HashMap<String, Object>();
				station.put("id", objects[0]);
				station.put("step", objects[2].toString().split("-")[1]);
				station.put("waiting", objects[4]);
				station.put("running", objects[5]);
				station.put("outSpec", objects[6]);
				station.put("cusHold", objects[7]);
				station.put("engHold", objects[8]);
				station.put("swr", objects[9]);
				station.put("outYest", objects[10]);
				station.put("out1st", objects[11]);
				station.put("out2end", objects[12]);
				station.put("iqcPass", objects[13]);
				station.put("hold", objects[14]);
				list.add(station);
				stationParent.put(objects[1].toString(), list);
			}
		}
		return this.sortMap(stationParent);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<Map.Entry<String, List<Map<String, Object>>>> getNowTotalWIP(Map<String, Object> maps,String table) {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.ID,c.STATIONNAME PSTATIONNAME,b.STATIONNAME,b.SEQUENCE,")
			.append("SUM (CASE WHEN STATUS = 'Waiting' THEN a.QTYIN  ELSE 0 END) Waiting,")
			.append("SUM (CASE WHEN STATUS = 'Running' THEN a.QTYIN  ELSE 0 END) Running,")
			.append("SUM (CASE WHEN STATUS = 'Out-Spec' THEN a.QTYIN  ELSE 0 END) OutSpec,")
			.append("SUM (CASE WHEN STATUS = 'Cus. Disp' THEN a.QTYIN  ELSE 0 END) CusDisp,")
			.append("SUM (CASE WHEN STATUS = 'Eng. Disp' THEN a.QTYIN  ELSE 0 END) EngDisp,")
			.append("SUM (CASE WHEN a.RESOURCE_LOTTYPE_ID = 826 OR a.RESOURCE_LOTTYPE_ID = 828 THEN a.QTYIN  ELSE 0 END) SWR,")
			.append("SUM (CASE WHEN STATUS = 'Finish' and to_char (a.LAST_MODIFY_DATE,'YYYY-MM-DD HH12:MI:SS') BETWEEN ? and ? THEN a.QTYOUT  ELSE 0 END) OutYesAll,")
			.append("SUM (CASE WHEN STATUS = 'Finish' and to_char (a.LAST_MODIFY_DATE,'YYYY-MM-DD HH12:MI:SS') BETWEEN ? and ? THEN a.QTYOUT  ELSE 0 END) Out1ST,")
			.append("SUM (CASE WHEN STATUS = 'Finish' and to_char (a.LAST_MODIFY_DATE,'YYYY-MM-DD HH12:MI:SS') BETWEEN ? and ? THEN a.QTYOUT  ELSE 0 END) Out2ST,")
			.append("SUM (CASE WHEN STATUS = 'Pass' THEN a.QTYIN  ELSE 0 END) IQCPass," )
			.append("SUM (CASE WHEN STATUS = 'Hold' THEN a.QTYIN  ELSE 0 END) Hold,")
			.append("SUM (CASE WHEN STATUS = 'Finish' THEN 1  ELSE NULL END) Finish    ")
			.append("from ").append(table)
			.append(" a INNER JOIN GODZILLA_STATION b on a.STATION_ID = b.ID INNER JOIN GODZILLA_STATION c on b.PARENT_STATION_ID = c.ID ")
			.append("GROUP BY c.STATIONNAME,b.ID,b.STATIONNAME,b.SEQUENCE ")
			.append("ORDER BY b.SEQUENCE");
		List<Object[]> result = (List<Object[]>) getQueryChannelService()
				.createSqlQuery(sql.toString())
				.setParameters(this.getDateList()).list();
		Map<String, List<Map<String, Object>>> stationParent = new HashMap<String, List<Map<String, Object>>>();
		if (result != null) {
			for (Object[] objects : result) {
				List<Map<String, Object>> list = stationParent.get(objects[1].toString());
				if (list == null) {
					list = new ArrayList<Map<String, Object>>();
				}
				Map<String, Object> station = new HashMap<String, Object>();
				station.put("id", objects[0]);
				station.put("step", objects[2].toString().split("-")[1]);
				station.put("waiting", objects[4]);
				station.put("running", objects[5]);
				station.put("outSpec", objects[6]);
				station.put("cusHold", objects[7]);
				station.put("engHold", objects[8]);
				station.put("swr", objects[9]);
				station.put("outYest", objects[10]);
				station.put("out1st", objects[11]);
				station.put("out2end", objects[12]);
				station.put("iqcPass", objects[13]);
				station.put("hold", objects[14]);
				list.add(station);
				stationParent.put(objects[1].toString(), list);
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
						return Integer.valueOf(mapping1.getValue().get(0).get("id").toString())
								.compareTo(Integer.valueOf(mapping2.getValue().get(0).get("id").toString()));
					}
				});
		return mappingList;
	}
	
	private List<Object> getDateList(){
		List<Object> conditionVals = new ArrayList<Object>();
		Date nowDate = new Date();
		
		if(MyDateUtils.formaterDate(nowDate,"HH:mm:ss").compareTo(out2ndEnd) < 0){
			nowDate = MyDateUtils.add(nowDate, Calendar.DAY_OF_MONTH, -1);
		}
		String nowDateStr = MyDateUtils.formaterDate(nowDate, MyDateUtils.DefFormatString);
		String nextDateStr =MyDateUtils.formaterDate(MyDateUtils.add(nowDate, Calendar.DAY_OF_MONTH, 1), MyDateUtils.DefFormatString);
		String lastDateStr =MyDateUtils.formaterDate(MyDateUtils.add(nowDate, Calendar.DAY_OF_MONTH, -1), MyDateUtils.DefFormatString);
		conditionVals.add(lastDateStr + out1stStart);//2015-01-13 07:30:00
		conditionVals.add(nowDateStr + out2ndEnd);  //2015-01-14 07:29:59
		conditionVals.add(nowDateStr + out1stStart);//2015-01-14 07:30:00
		conditionVals.add(nowDateStr + out1stEnd);//2015-01-14 19:29:59
		conditionVals.add(nowDateStr + out2ndStart);//2015-01-14 19:30:00
		conditionVals.add(nextDateStr + out2ndEnd);//2015-01-15 07:29:59
		return conditionVals;
	}
}
