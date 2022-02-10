package com.demo.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoConsumer extends DefaultConsumer {

	private final static Logger LOGGER = LoggerFactory.getLogger(DemoConsumer.class);

	private ServerSocket serverSocket;

	public DemoConsumer(DemoEndpoint endpoint, Processor processor) throws IOException {
		super(endpoint, processor);
		serverSocket = new ServerSocket(4444);
		LOGGER.debug("Creating Democomponent Consumer");
	}

	@Override
	protected void doStart() throws Exception {
		LOGGER.debug("Staring the democomponent Consumer");
		new Thread(new AcceptThread()).start();
		super.doStart();
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
		LOGGER.debug("Stoping the democomponent Consumer");
		if (serverSocket != null) {
			serverSocket.close();
		}
	}

	class AcceptThread implements Runnable {

		public void run() {
			while (true) {
				Exchange exchange = new DefaultExchange(getEndpoint(), ExchangePattern.InOut);
				Socket clientSocket = null;
				try {
					clientSocket = serverSocket.accept();
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String inputLine = in.readLine();

					if (inputLine != null) {
						LOGGER.debug("Get input line: {}", inputLine);
						exchange.getIn().setBody(inputLine, String.class);
						getProcessor().process(exchange);
						String response = exchange.getOut().getBody(String.class);
						
						if (response == null) {
							response = exchange.getIn().getBody(String.class);
						}
						
						if (response != null) {
							out.println(response);
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (clientSocket != null) {
						try {
							clientSocket.close();
						} catch (IOException e) {
							// do nothing
						}
					}
				}
			}
		}

	}
}
