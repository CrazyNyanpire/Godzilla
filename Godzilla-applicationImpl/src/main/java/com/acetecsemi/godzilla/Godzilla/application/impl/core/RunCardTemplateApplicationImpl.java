
package com.acetecsemi.godzilla.Godzilla.application.impl.core;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.text.MessageFormat;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.openkoala.koala.auth.core.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.Page;
import org.dayatang.querychannel.QueryChannelService;

import com.acetecsemi.godzilla.Godzilla.application.dto.*;
import com.acetecsemi.godzilla.Godzilla.application.utils.StaticValue;
import com.acetecsemi.godzilla.Godzilla.application.core.BomListItemApplication;
import com.acetecsemi.godzilla.Godzilla.application.core.RunCardTemplateApplication;
import com.acetecsemi.godzilla.Godzilla.core.*;

@Named
@Transactional
public class RunCardTemplateApplicationImpl implements RunCardTemplateApplication {


	private QueryChannelService queryChannel;

	@Inject
	private BomListItemApplication bomListItemApplication;
	
    private QueryChannelService getQueryChannelService(){
       if(queryChannel==null){
          queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
       }
     return queryChannel;
    }
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public RunCardTemplateDTO getRunCardTemplate(Long id) {
		RunCardTemplate runCardTemplate = RunCardTemplate.load(RunCardTemplate.class, id);
		RunCardTemplateDTO runCardTemplateDTO = new RunCardTemplateDTO();
		// 将domain转成VO
		try {
			BeanUtils.copyProperties(runCardTemplateDTO, runCardTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		runCardTemplateDTO.setId((java.lang.Long)runCardTemplate.getId());
		return runCardTemplateDTO;
	}
	
	public RunCardTemplateDTO saveRunCardTemplate(RunCardTemplateDTO runCardTemplateDTO) {
		RunCardTemplate runCardTemplate = new RunCardTemplate();
		try {
        	BeanUtils.copyProperties(runCardTemplate, runCardTemplateDTO);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		runCardTemplate.save();
		runCardTemplateDTO.setId((java.lang.Long)runCardTemplate.getId());
		return runCardTemplateDTO;
	}
	
	public void updateRunCardTemplate(RunCardTemplateDTO runCardTemplateDTO) {
		RunCardTemplate runCardTemplate = RunCardTemplate.get(RunCardTemplate.class, runCardTemplateDTO.getId());
		// 设置要更新的值
		try {
			BeanUtils.copyProperties(runCardTemplate, runCardTemplateDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeRunCardTemplate(Long id) {
		this.removeRunCardTemplates(new Long[] { id });
	}
	
	public void removeRunCardTemplates(Long[] ids) {
		for (int i = 0; i < ids.length; i++) {
			RunCardTemplate runCardTemplate = RunCardTemplate.load(RunCardTemplate.class, ids[i]);
			runCardTemplate.remove();
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<RunCardTemplateDTO> findAllRunCardTemplate() {
		List<RunCardTemplateDTO> list = new ArrayList<RunCardTemplateDTO>();
		List<RunCardTemplate> all = RunCardTemplate.findAll(RunCardTemplate.class);
		for (RunCardTemplate runCardTemplate : all) {
			RunCardTemplateDTO runCardTemplateDTO = new RunCardTemplateDTO();
			// 将domain转成VO
			try {
				BeanUtils.copyProperties(runCardTemplateDTO, runCardTemplate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			list.add(runCardTemplateDTO);
		}
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RunCardTemplateDTO> pageQueryRunCardTemplate(RunCardTemplateDTO queryVo, int currentPage, int pageSize) {
		List<RunCardTemplateDTO> result = new ArrayList<RunCardTemplateDTO>();
		List<Object> conditionVals = new ArrayList<Object>();
	   	StringBuilder jpql = new StringBuilder("select _runCardTemplate from RunCardTemplate _runCardTemplate   left join _runCardTemplate.createUser  left join _runCardTemplate.lastModifyUser  left join _runCardTemplate.product  where 1=1 ");
	
	
	
	   	if (queryVo.getCreateDate() != null) {
	   		jpql.append(" and _runCardTemplate.createDate between ? and ? ");
	   		conditionVals.add(queryVo.getCreateDate());
	   		conditionVals.add(queryVo.getCreateDateEnd());
	   	}	
	
	
	   	if (queryVo.getLastModifyDate() != null) {
	   		jpql.append(" and _runCardTemplate.lastModifyDate between ? and ? ");
	   		conditionVals.add(queryVo.getLastModifyDate());
	   		conditionVals.add(queryVo.getLastModifyDateEnd());
	   	}	
	
	   	if (queryVo.getRunCardType() != null && !"".equals(queryVo.getRunCardType())) {
	   		jpql.append(" and _runCardTemplate.runCardType like ?");
	   		conditionVals.add(MessageFormat.format("%{0}%", queryVo.getRunCardType()));
	   	}		
	
	
        Page<RunCardTemplate> pages = getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
        for (RunCardTemplate runCardTemplate : pages.getData()) {
            RunCardTemplateDTO runCardTemplateDTO = new RunCardTemplateDTO();
            
             // 将domain转成VO
            try {
            	BeanUtils.copyProperties(runCardTemplateDTO, runCardTemplate);
            } catch (Exception e) {
            	e.printStackTrace();
            } 
            
                                                               result.add(runCardTemplateDTO);
        }
        return new Page<RunCardTemplateDTO>(pages.getPageIndex(), pages.getResultCount(), pageSize, result);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findCreateUserByRunCardTemplate(Long id) {
		String jpql = "select e from RunCardTemplate o right join o.createUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserDTO findLastModifyUserByRunCardTemplate(Long id) {
		String jpql = "select e from RunCardTemplate o right join o.lastModifyUser e where o.id=?";
		User result = (User) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		UserDTO  dto = new UserDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ProductDTO findProductByRunCardTemplate(Long id) {
		String jpql = "select e from RunCardTemplate o right join o.product e where o.id=?";
		Product result = (Product) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).singleResult();
		ProductDTO  dto = new ProductDTO();
		if (result != null) {
			try {
				BeanUtils.copyProperties(dto, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<RunCardItemDTO> findRunCardItemsByRunCardTemplate(Long id, int currentPage, int pageSize) {
		List<RunCardItemDTO> result = new ArrayList<RunCardItemDTO>();
		String jpql = "select e from RunCardTemplate o right join o.runCardItems e where o.id=?";
		Page<RunCardItem> pages = getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id }).setPage(currentPage, pageSize).pagedList();
        for (RunCardItem entity : pages.getData()) {
            RunCardItemDTO dto = new RunCardItemDTO();
            try {
				BeanUtils.copyProperties(dto, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
            result.add(dto);
        }
        return new Page<RunCardItemDTO>(Page.getStartOfPage(currentPage, pageSize), pages.getResultCount(), pageSize, result);
	}		


	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Map<String,Object> findRunCardByProcessAndType(Long id,String type) {
		Product product = null;
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> start = new HashMap<String,Object>();
		DefineStationProcess defineStationProcess = null;
		if(type.equals(StaticValue.BOMLIST_TYPE_PREASSY)){
			WaferProcess wp = WaferProcess.get(WaferProcess.class, id);
			if(wp == null)
				return null;
			
			defineStationProcess = wp.getWaferCompanyLot().getWaferCustomerLot().getDefineStationProcess();
			//如果当前晶圆在线边仓，则runcard只显示头部信息
			if(wp.getStation().getId().intValue() == 201)
			{
				defineStationProcess = null;
			}
//			if (defineStationProcess == null) {
//				defineStationProcess = DefineStationProcess.load(
//						DefineStationProcess.class, Long.valueOf(1));
//			}
			product = wp.getWaferCompanyLot().getWaferCustomerLot().getProduct();
			start.put("lotNumber", wp.getWaferCompanyLot().getLotNo());
			start.put("unitsQty", wp.getQtyIn());
			start.put("waferQty", wp.getWaferQtyIn());
			start.put("productName", product.getProductName());
			start.put("customerLotNumber", wp.getWaferCompanyLot().getWaferCustomerLot().getCustomerLotNo());
			start.put("lotType", wp.getResLotType().getIdentifier());
			start.put("partNumber", wp.getWaferCompanyLot().getWaferCustomerLot().getPart().getPartNo());
			start.put("isoutsourcing",wp.getWaferCompanyLot().getWaferCustomerLot().getDefineStationProcess() == null? "" :wp.getWaferCompanyLot()
					.getWaferCustomerLot().getDefineStationProcess().getId());
			//start.put("dateCode", wp.getWaferCompanyLot().getLotNo().split("-")[1].substring(0,4));
		}
		else if(type.equals(StaticValue.BOMLIST_TYPE_SUBSTRATE))
		{
			SubstrateProcess sp = SubstrateProcess.get(SubstrateProcess.class, id);
			start.put("lotNumber", sp.getSubstrateCompanyLot().getLotNo());
//			start.put("unitsQty", mp.getManufactureLot().getSubstrateProcess().getQtyOut());
//			start.put("stripsQty", mp.getManufactureLot().getSubstrateProcess().getStripQtyOut());
			start.put("unitsQty", sp.getQtyIn());
			start.put("stripsQty", sp.getStripQtyIn());
//			start.put("productName", product.getProductName());
			start.put("customerLotNumber", sp.getSubstrateCompanyLot().getSubstrateCustomerLot().getCustomerLotNo());
			start.put("lotType", sp.getResLotType().getIdentifier());
			start.put("batchNo", sp.getSubstrateCompanyLot().getSubstrateCustomerLot().getBatchNo());
			start.put("partNo", sp.getSubstrateCompanyLot().getSubstrateCustomerLot().getSubstratePart().getPartNo());
			start.put("venderName", sp.getSubstrateCompanyLot().getSubstrateCustomerLot().getVender().getVenderName());
			start.put("expireDate", sp.getSubstrateCompanyLot().getSubstrateCustomerLot().getExpiryDate().toString());
			
		}
		else{
			ManufactureProcess mp = ManufactureProcess.get(ManufactureProcess.class, id);
			if(mp == null)
				return null;
			
			product = mp.getManufactureLot().getProduct();
			start.put("lotNumber", mp.getManufactureLot().getLotNo());
//			start.put("unitsQty", mp.getManufactureLot().getSubstrateProcess().getQtyOut());
//			start.put("stripsQty", mp.getManufactureLot().getSubstrateProcess().getStripQtyOut());
			start.put("unitsQty", mp.getQtyIn());
			start.put("stripsQty", mp.getStripQtyIn());
			start.put("productName", product.getProductName());
			start.put("customerLotNumber", mp.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getSubstrateCustomerLot().getCustomerLotNo());
			start.put("lotType", mp.getResLotType().getIdentifier());
			start.put("dateCode", mp.getManufactureLot().getLotNo().split("-")[1].substring(0,4));
			double componentTotal = getComponentAmount(mp.getStripQtyIn(),product.getId(),type);
			start.put("component", componentTotal);
			
			//if 基板委外，则要查询基板的批次信息
			start.put("substrateLotNumber",mp.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getLotNo());
			start.put("batchNo", mp.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getSubstrateCustomerLot().getBatchNo());
			start.put("partNo", mp.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getSubstrateCustomerLot().getSubstratePart().getPartNo());
			start.put("venderName", mp.getManufactureLot().getSubstrateProcess().getSubstrateCompanyLot().getSubstrateCustomerLot().getVender().getVenderName());
			start.put("isoutsourcing", mp.getManufactureLot().getDefineStationProcess().getId());
			defineStationProcess = mp.getManufactureLot().getDefineStationProcess();
			
		}
		//RunCardTemplate runCardTemplate = this.findRunCardTemplateByProductAndType(product.getId(), type);
		result.put("start", start);
		if (defineStationProcess == null) {
//			defineStationProcess = DefineStationProcess.load(
//					DefineStationProcess.class, Long.valueOf(1));
			return result;
		}
		result.put("process", this.getRunCardItemListByStations(defineStationProcess.getStations(),product));
		return result;
	}
	private double getComponentAmount(Integer substrateQty, Long productId,String type)
	{
		List<BomListItemDTO> bomListItemDTOs = bomListItemApplication.findBomListItemByProduct(productId, type,"Component");//被动组件数量
		int useage = 0;
		double attritionRate = 0;
		for(BomListItemDTO bomListItemDTO:bomListItemDTOs)
		{
			useage += bomListItemDTO.getBomUsage();
			attritionRate += bomListItemDTO.getAttritionRate();
		}
		
		double componentTotal = substrateQty * (useage*(1+attritionRate));
		return componentTotal;
	}
	private List<Map<String,Object>> getRunCardItemRemarkList(List<RunCardItemRemark> runCardItemRemarks){
		List<Map<String,Object>> runCardItemRemarkList =  new ArrayList<Map<String,Object>>();
		for(RunCardItemRemark runCardItemRemark: runCardItemRemarks){
			Map<String,Object> runCardItemRemarkMap = new HashMap<String,Object>();
			runCardItemRemarkMap.put("title", runCardItemRemark.getTitle());
			runCardItemRemarkMap.put("value", runCardItemRemark.getValue());
			runCardItemRemarkList.add(runCardItemRemarkMap);
		}
		return runCardItemRemarkList;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private RunCardTemplate findRunCardTemplateByProductAndType(Long id,String type) {
		String jpql = "select o from RunCardTemplate o where o.product.id=? and o.runCardType=?";
		RunCardTemplate result = (RunCardTemplate) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { id,type }).singleResult();
		return result;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	private List <Map<String,Object>> getRunCardItemListByStations(Set<Station> stations,Product product) {
		List <Map<String,Object>> processes = new ArrayList<Map<String,Object>>();
		for(Station station : stations){
			RunCardItem ranCardItem = station.getRunCardItem();
			if(ranCardItem == null){
				continue;
			}
			Map<String,Object> process = new HashMap<String,Object>();
			String stationNameEn = ranCardItem.getStation().getStationNameEn()==null ? "": ranCardItem.getStation().getStationNameEn();
			process.put("title",stationNameEn+ "  "+ ranCardItem.getStation().getStationName().split("-")[1]);
			process.put("haveMagIn", ranCardItem.getHaveMagIn());
			process.put("haveMagOut", ranCardItem.getHaveMagOut());
			String jpql = "select o from RunCardItemRemark o where o.runCardItem.id=? and o.product.id=? order by o.title";
			List<RunCardItemRemark> runCardItemRemarks = (List<RunCardItemRemark>) getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { ranCardItem.getId(), product.getId()}).list();
			if(runCardItemRemarks != null && runCardItemRemarks.size() > 0){
				process.put("haveDetail", "1");
				process.put("detail", this.getRunCardItemRemarkList(runCardItemRemarks));
			}else
				process.put("haveDetail", "0");
			processes.add(process);
			
		}		
		return processes;
	}
}
