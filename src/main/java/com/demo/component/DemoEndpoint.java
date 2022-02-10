package com.demo.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

public class DemoEndpoint extends DefaultEndpoint {

	public DemoEndpoint(String uri, DemoComponent demoComponent) {
		super(uri, demoComponent);
	}

	public Consumer createConsumer(Processor processor) throws Exception {

		return new DemoConsumer(this, processor);
	}

	public Producer createProducer() throws Exception {

		return new DemoProducer(this);
	}

	public boolean isSingleton() {

		return false;
	}

}
