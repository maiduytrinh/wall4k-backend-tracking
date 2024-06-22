package com.tp.projectbase.injection;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.tp.projectbase.schedule.ScheduleService;
import com.tp.projectbase.service.DataService;
import com.tp.projectbase.service.impl.DataServiceImpl;

import io.vertx.core.Vertx;

/**
 *
 */
public class ServiceModule implements Module {

	private Vertx vertx;
	public ServiceModule(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(DataService.class).to(DataServiceImpl.class);
		binder.bind(ScheduleService.class).toInstance(new ScheduleService());

	}
}
