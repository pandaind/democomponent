package com.demo.component;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DemoComponent extends DefaultComponent {

	private final static Logger LOGGER = LoggerFactory.getLogger(DemoComponent.class);

	public DemoComponent() {
		LOGGER.info("Creating a Demo Camel Component");
	}

	public DemoComponent(CamelContext camelContext) {
		super(camelContext);
		LOGGER.info("Creating a Demo Camel Component");
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		LOGGER.info("Creating a Demo Camel Component");
		DemoEndpoint demoEndpoint = new DemoEndpoint(uri, this);
		setProperties(demoEndpoint, parameters);
		return demoEndpoint;
	}

}
