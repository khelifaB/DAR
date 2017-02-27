package serveur;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;


public class RequeteHttp {

	private HashMap<String, String> entetes;
	private String corps;
	private VerbeHttp verbe;
	private URLHttp url;
	private String version;
	private boolean valide;
	private SessionHttp session;


	public RequeteHttp() {
		entetes = new HashMap<String,String>();
		corps="";
		verbe=VerbeHttp.NONE;
		url=new URLHttp();
		version="";
		valide =false;
		session=new SessionHttp();
	}

	public void ajouteEntete(String nomAttribut, String valeur) {
		entetes.put(nomAttribut, valeur);
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append(verbe).append(" ").append(url.getUrl()).append(" ").append(version);
		res.append(System.lineSeparator());

		for(String entete : entetes.keySet()) {
			res.append(entete).append(": ").append(entetes.get(entete)).append(System.lineSeparator());
		}
		res.append(System.lineSeparator());
		res.append(corps);

		return res.toString();
	}

	public String toHTML() {
		StringBuffer sb = new StringBuffer();
		sb.append("	<!DOCTYPE html>"+
				"<html>"+
				"	<head>"+
				"		<meta charset='ISO-8859-1'>"+
				"		<title>Requete HTTP</title>"+
				"	</head>"+
				"	<body>"
				+ "<h1 style='text-align:center' > Requete HTTP</h1>");

		sb.append(verbe+" ").append(url.getUrl()+" ").append(version+"<br>");

		for(String k:entetes.keySet())
			sb.append(k+": "+entetes.get(k)+"<br>");
		sb.append(corps);
		sb.append("		</body>"+
				"</html>");
		return sb.toString();
	}

	public String toJSON() {
		JSONObject json = new JSONObject();
		try{
			json.put("method", verbe.toString());
			json.put("url", url.getUrl());
			json.put("version", version);
			
			for(String k:entetes.keySet())
				json.put(k, entetes.get(k));
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		
		return json.toString();
	}

	public void parse(String requete)  {

		if(requete==null || requete.equals(""))
			valide=false;

		try{
			String[] lignes = requete.split(System.lineSeparator());

			// premiére ligne method /chemin version
			String[] ligne1 = lignes[0].split(" ");
			setVerbe(VerbeHttp.valueOf(ligne1[0]));
			url.setUrl(ligne1[1]);url.parse(ligne1[1]);
			setVersion(ligne1[2]);

			// entetes
			Pattern p = Pattern.compile("^([^:]+):\\s(.+)");
			Matcher m;
			for (int i = 1; i < lignes.length; i++) {
				
				if (!"".equals(lignes[i])) {
					m = p.matcher(lignes[i]);
					m.matches();
					ajouteEntete(m.group(1), m.group(2));
				}
			}



		}
		catch(Exception e){
			valide=false;
			e.printStackTrace();
			System.out.println("requette mal formée "+e.getMessage());
			return;
		}

		valide=true;
	}

	public HashMap<String, String> getEntetes() {
		return entetes;
	}

	public String getEntete(String nomEntete){
		return entetes.get(nomEntete);
	}

	public String getCorps() {
		return corps;
	}

	public VerbeHttp getVerbe() {
		return verbe;
	}

	public URLHttp getUrl() {
		return url;
	}

	public String getParametre(String parametre) {
		return url.getParametre(parametre);
	}

	public String getVersion() {
		return version;
	}

	public boolean estValide() {
		return valide;
	}

	public void setEntetes(HashMap<String, String> entetes) {
		this.entetes = entetes;
	}

	public void setCorps(String corps) {
		this.corps = corps;
	}

	public void setVerbe(VerbeHttp verbe) {
		this.verbe = verbe;
	}

	public void setUrl(URLHttp url) {
		this.url = url;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setSession(SessionHttp session) {
		this.session = session;
	}
	public SessionHttp getSession() {
		return session;
	}


}
