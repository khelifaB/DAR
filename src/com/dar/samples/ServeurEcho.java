package com.dar.samples;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.dar.serveur.ReponseHttp;
import com.dar.serveur.RequeteHttp;
import com.dar.serveur.ServeurHttp;

public class ServeurEcho extends ServeurHttp {
	private final static Logger log = Logger.getLogger(ServeurEcho.class.getName());

	public ServeurEcho(int port) {
		super(port);
	}
	public ServeurEcho() {
	}

	public static void main(String[] args) {
		ServeurEcho serv = new ServeurEcho(8080);
		serv.setName(ServeurEcho.class.getSimpleName());
		Map<String, String> mapUrlToMethod = new HashMap<>();
		mapUrlToMethod.put("/echo", "com.dar.samples.ServeurEcho.echo");
		serv.setMapUrlToMethod(mapUrlToMethod);
		serv.start();
	}

	public ReponseHttp echo(RequeteHttp req, ReponseHttp rep) {
		log.info("msg in ");
		rep.setCorps( "Voila test echo");
		return rep;
	}

}
