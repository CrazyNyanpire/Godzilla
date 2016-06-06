package com.acetecsemi.godzilla.Godzilla.application.core;

import com.acetecsemi.godzilla.Godzilla.application.dto.CustomerLotOptLogDTO;

public interface CustomerLotOptLogApplication {
	CustomerLotOptLogDTO saveCustomerLotOptLog(CustomerLotOptLogDTO customerLotOptLogDTO);
	boolean getCustomerLotOptLogDTOByProperties(Long id, String tableName);
}
