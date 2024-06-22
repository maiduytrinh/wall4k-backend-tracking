package com.tp.projectbase.rest.api;

import com.tp.projectbase.ServerVerticle;
import com.tp.projectbase.entity.Data;
import com.tp.projectbase.rest.common.Response;
import com.tp.projectbase.rest.writers.MyResponseWriter;
import com.tp.projectbase.service.DataService;
import com.zandero.rest.annotation.BodyParam;
import com.zandero.rest.annotation.ResponseWriter;
import io.swagger.annotations.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Api(value = ServerVerticle.API_ROOT, tags = "phase 1 - data")
@Path(ServerVerticle.API_ROOT + "/tracking")
@Produces(MediaType.APPLICATION_JSON)
@ResponseWriter(MyResponseWriter.class)
public class TrackingRest extends BaseRest {

	private static final Logger LOG = LoggerFactory.getLogger(TrackingRest.class);

	@Inject
	private DataService dataService;

	@POST
	@Path("/")
	public Response<String> recordAction(
			@ApiParam(value = "Data object", required = true) @BodyParam Data data,
			@Context RoutingContext context) {
		handle(context);
		LOG.info(data.toString());

		dataService.recordAction(data);

		return Response.ok(null);
	}

	@GET
	@Path("/update-trending")
	public Response<String> updateTrending(
			@Context RoutingContext context) throws Exception {
		handle(context);
		LOG.info("========Start update trending=========");

		dataService.updateTrending();
		return Response.ok(null);
	}

	@GET
	@Path("/get-trending")
	public Response<String> getTrending(
			@ApiParam(value = "The country value", defaultValue = "VN", required = true) @HeaderParam("country") String country,
			@ApiParam(value = "Page number value", defaultValue = "1", required = true) @QueryParam("pagenumber") @DefaultValue("1") Integer pageNumber,
			@ApiParam(value = "Size config value", defaultValue = "40") @QueryParam("sizeconfig") @DefaultValue("40") Integer sizeConfig,
			@Context RoutingContext context) {
		LOG.info("getTrending()-----------------------------------------------------------------------------");
		LOG.info("country: {}, pageNumber: {}, sizeConfig: {}", country, pageNumber, sizeConfig);
		handle(context);
		return Response.ok(dataService.getDataTrending(country, pageNumber, sizeConfig));
	}

	@GET
	@Path("/get-topdown")
	public Response<String> getTopDown(
			@ApiParam(value = "The country value", defaultValue = "VN", required = true) @HeaderParam("country") String country,
			@ApiParam(value = "Page number value", defaultValue = "1", required = true) @QueryParam("pagenumber") @DefaultValue("1") Integer pageNumber,
			@ApiParam(value = "Size config value", defaultValue = "40") @QueryParam("sizeconfig") @DefaultValue("40") Integer sizeConfig,
			@Context RoutingContext context) {
		LOG.info("getTopDown()-----------------------------------------------------------------------------");
		LOG.info("country: {}, pageNumber: {}, sizeConfig: {}", country, pageNumber, sizeConfig);
		handle(context);
		return Response.ok(dataService.getDataTopDown(country, pageNumber, sizeConfig));
	}
}
