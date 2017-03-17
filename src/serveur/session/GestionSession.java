package serveur.session;


import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import serveur.ReponseHttp;
import serveur.RequeteHttp;

public class GestionSession {
	private static HashMap<String, SessionHttp> sessions;
	
	public GestionSession() {
		sessions = new HashMap<String, SessionHttp>();
	}
	
	public boolean existeSession(String coockie) {
//		System.out.println(sessions.containsKey(coockie));
//		System.out.println(sessions.keySet());
		return sessions.containsKey(coockie);
	}
	public SessionHttp getSession(String coockie) {
		return sessions.get(coockie);
	}
	
	public void ajouterSession(String adresseIP, RequeteHttp requete, ReponseHttp reponse) {
		String coockie = adresseIP+" "+requete.getEntetes().get("User-Agent").replace(';', ':');
		reponse.setEntete("Set-Cookie", coockie);
		SessionHttp session = requete.getSession();
		session.setID(coockie);
		requete.setSession(session);
		sessions.put(coockie, session);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				supprimerSession(coockie);
				System.out.println("------------- suppression session -------------");
			}
		},1000*60*60*24); // supprime la session au bout de 24 heures 
		
	}
	public void supprimerSession(String coockie) {
		sessions.remove(coockie);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String coockie: sessions.keySet()){
			sb.append(coockie +" : "+sessions.get(coockie)+System.lineSeparator());
		}
		return sb.toString();
	}
}
