package com.demo.component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoProducer extends DefaultProducer {

	private final static Logger LOGGER = LoggerFactory.getLogger(DemoProducer.class);

	public DemoProducer(Endpoint endpoint) {
		super(endpoint);
		LOGGER.debug("Creating Democomponent Producer");
	}

	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("Processing Exchange");
		String input = exchange.getIn().getBody(String.class);
		LOGGER.debug("Get input {}:", input);
		LOGGER.debug("Connecting to the socket on localhost:4444");
		Socket socket = new Socket("localhost", 4444);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out.println(input);
		String fromServer = in.readLine();
		LOGGER.debug("Get reply from server: {}", fromServer);
		LOGGER.debug("Populating the exchange");
		exchange.getIn().setBody(fromServer, String.class);
		socket.close();
	}

}
