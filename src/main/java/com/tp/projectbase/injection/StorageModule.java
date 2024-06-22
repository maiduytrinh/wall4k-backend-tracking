package com.tp.projectbase.injection;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.tp.projectbase.storage.*;
import com.tp.projectbase.storage.impl.*;

public class StorageModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(DataStorage.class).to(DataStorageImpl.class);
		binder.bind(TrendingStorage.class).to(TrendingStorageImpl.class);
	}
}