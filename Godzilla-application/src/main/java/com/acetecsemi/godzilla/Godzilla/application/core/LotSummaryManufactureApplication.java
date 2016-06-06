package com.acetecsemi.godzilla.Godzilla.application.core;

import java.util.List;
import java.util.Map;

public interface LotSummaryManufactureApplication extends LotSummaryApplication{
	public List<Map<String, Object>> getLotDebit(Long id);
}
