package com.dar.serveur;

import java.util.HashMap;
import java.util.Map;

public class ReponseHttp {

	public static Map<Integer, String> codes = new HashMap<Integer, String>();
	static{
		codes.put(100, "Continue");									codes.put(408, "Request Timeout");
		codes.put(101, "Switching Protocols");						codes.put(409, "Conflict");
		codes.put(410, "Gone");
		codes.put(200, "OK");										codes.put(411, "Length Required");
		codes.put(201, "Created");									codes.put(412, "Precondition Failed");
		codes.put(202, "Accepted");									codes.put(413, "Request Entity Too Large");
		codes.put(203, "Non-Authoritative Information");			codes.put(414, "Request Uri Too Long");
		codes.put(204, "No Content");								codes.put(415, "Unsupported Media Type");
		codes.put(205, "Reset Content");							codes.put(416, "Request Range Not Satisfied");
		codes.put(206, "Partial Content");							codes.put(417, "Expectation Failed");

		codes.put(300, "Multiple Choices");							codes.put(500, "Internal Server Error");
		codes.put(301, "Moved Permanently");						codes.put(501, "Not Implemented");
		codes.put(302, "Found");									codes.put(502, "Bad Gateway");
		codes.put(303, "See Other");								codes.put(503, "Service Unavailable");
		codes.put(304, "Not Modified");								codes.put(504, "Gateway Timeout");
		codes.put(305, "Use Proxy");								codes.put(505, "HTTP Version Not Supported");

		codes.put(400, "Bad Request");
		codes.put(401, "Unauthorized");
		codes.put(403, "Forbidden");
		codes.put(404, "Not Found");
		codes.put(405, "Method Not Allowed");
		codes.put(406, "Not Acceptable");
		codes.put(407, "Proxy Authentification Required");
	}
	
	private String version;
	private int statut;
	private HashMap<String, String> entetes;
	private String corps;
	
	public ReponseHttp() {
	
		version="HTTP/1.0";
		statut=200;
		entetes = new HashMap<String, String>();
		corps="";
	}
	
	
	public String toString() {
		
		StringBuilder res = new StringBuilder();
    	res.append(version).append(" ").append(statut).append(" ").append(codes.get(statut));
    	res.append(System.lineSeparator());
    	
    	for(String entete : entetes.keySet()) {
			res.append(entete).append(": ").append(entetes.get(entete)).append(System.lineSeparator());
		}
		res.append(System.lineSeparator());
		res.append(corps);
		
		return res.toString();
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public void setStatut(int statut) {
		this.statut = statut;
	}


	public void setEntetes(HashMap<String, String> entetes) {
		this.entetes = entetes;
	}


	public void setCorps(String corps) {
		this.corps = corps;
	}


	public String getVersion() {
		return version;
	}


	public int getStatut() {
		return statut;
	}


	public HashMap<String, String> getEntetes() {
		return entetes;
	}


	public String getCorps() {
		return corps;
	}


	public void setEntete(String entete, String valeur) {
		entetes.put(entete, valeur);
		
	}

	
	
}
