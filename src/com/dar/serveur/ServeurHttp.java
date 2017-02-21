package com.dar.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

public class ServeurHttp extends Thread {
	private final static Logger log = Logger.getLogger(ServeurHttp.class.getName());

	private int port;
	public static Map<String, String> mapUrlToMethod;

	public ServeurHttp() {
		this.port = 8080;
	}

	public ServeurHttp(int port) {
		super();
		this.port = port;
		// config log
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");

	}

	public int getPort() {
		return port;
	}

	public Map<String, String> getMapUrlToMethod() {
		return mapUrlToMethod;
	}

	public void setMapUrlToMethod(Map<String, String> mapUrlToMethod) {
		ServeurHttp.mapUrlToMethod = mapUrlToMethod;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		Socket socketClient = null;
		ServerSocket serveur = null;

		try {
			log.info("Lancement du serveur " + getName() + " en ecoute ... sur le port " + port);
			serveur = new ServerSocket(port);

			while (true) {

				log.info(getName() + " est en attente d'une connection");
				socketClient = serveur.accept();
				new Connection(socketClient).start();

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socketClient.close();
				serveur.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
