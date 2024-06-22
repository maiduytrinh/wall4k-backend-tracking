package com.tp.projectbase.index;

import com.tp.projectbase.entity.Data;
import com.tp.projectbase.index.service.DataIndexService;

public class IndexingService {

	public static void initData() {
		final AbstractIndex<Data, Data> dataIndex = new DataIndexService();
		dataIndex.init();
	}

}
