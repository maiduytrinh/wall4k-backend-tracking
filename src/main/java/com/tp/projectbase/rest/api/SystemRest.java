package com.tp.projectbase.rest.api;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

import com.tp.projectbase.ServerVerticle;
import com.tp.projectbase.rest.common.Response;
import com.tp.projectbase.rest.writers.MyResponseWriter;
import com.zandero.rest.annotation.ResponseWriter;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(value = ServerVerticle.API_ROOT, tags = "phase 1 - system")
@Path(ServerVerticle.API_ROOT + "/system")
@Produces(MediaType.APPLICATION_JSON)
@ResponseWriter(MyResponseWriter.class)
public class SystemRest extends BaseRest {

	private static final Logger LOG = LoggerFactory.getLogger(SystemRest.class);

	@HEAD
	@Path("heartbeat")
	@ApiOperation(value = "Allows the HAProxy to check the server OK or NOK.", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	public Response<String> ping() {
		return Response.ok("OK");
	}

	@ApiOperation(value = "Log level 1: INFO, 2: WARNING 3: DEBUG")
	@GET
	@Path("logLevel")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success.") })
	public Response<String> log(@ApiParam(value = "level", required = true) @QueryParam("level") int level) {
		switch (level) {
			case 1:
				setLogLevel(Level.INFO);
				LOG.info("Set log level= INFO");
				return Response.ok("OK: Log.Level = INFO");
			case 2:
				setLogLevel(Level.WARN);
				LOG.info("Set log level= WARN");
				return Response.ok("OK: Log.Level = WARN");
			default:
				setLogLevel(Level.DEBUG);
				return Response.ok("OK: Log.Level = DEBUG");
		}
	}

	/**
	 * Set log level for all logger
	 * @param level
	 */
	private void setLogLevel(Level level) {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
		for(ch.qos.logback.classic.Logger logger : loggerList) {
			logger.setLevel(level);
		}
	}

}
