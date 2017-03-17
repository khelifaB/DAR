package application;

import java.util.HashMap;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import exception.DarException;
import serveur.ReponseHttp;
import serveur.Service;
import serveur.template.Template;
import tools.Fichier;


public class NotificationPresence extends Service {

	private static HashMap<String, Utilisateur> utilisateurs = new HashMap<String, Utilisateur>();

	public class Utilisateur{
		private int id;
		private String nom;
		private String motDePasse;
		private String disponibilite;
		private boolean connecte;
		private boolean disponible;
		public Utilisateur(String nom, String motDePasse) {
			this.nom = nom;
			this.motDePasse = motDePasse;
			this.id=-1;
			this.connecte=false;
			this.disponible=false;
			this.disponibilite="Indisponible";
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public void setMotDePasse(String motDePasse) {
			this.motDePasse = motDePasse;
		}
		public String getMotDePasse() {
			return motDePasse;
		}
		public String getNom() {
			return nom;
		}
		public JSONObject toJSON() {
			JSONObject jo = new JSONObject();
			jo.put("id", id);
			jo.put("nom", nom);
			jo.put("connecte", connecte);
			jo.put("disponible", disponible);
			jo.put("disponibilite", disponibilite);
			return jo;
		}
		@Override
		public String toString() {
			return "Utilisateur [id="+id+
					", nom=" + nom + 
					", motDePasse=" + 
					motDePasse + 
					" connecte="+connecte+
					", disponible="+disponible+
					"]";
		}

	}
	public NotificationPresence() {
	}

	public ReponseHttp inscription(){
		ReponseHttp reponse = new ReponseHttp();
		String nom = requete.getParametre("nom");
		String motDePasse = requete.getParametre("motDePasse");

		JSONObject jo = new JSONObject();

		reponse.setEntete("Content-type", "application/json");
		boolean valide=true;
		if(nom==null || nom==""){
			jo.put("KO", "parametre nom obligatoire");
			valide=false;
		}
		if(motDePasse==null || motDePasse==""){
			if(jo.has("KO"))
				jo.put("KO", jo.getString("KO")+" parametre motDePasse obligatoire");
			else
				jo.put("KO","parametre motDePasse obligatoire");
			valide=false;
		}

		if(valide){
			Utilisateur u = new Utilisateur(nom, motDePasse);
			if(utilisateurs.containsKey(nom)){
				jo.put("KO", "Utilisateur "+nom+" est deja inscrit");
				valide=false;
				reponse.setCorps(jo.toString());
				return reponse;
			}
			u.id=utilisateurs.size()+1;
			utilisateurs.put(nom, u);
			jo.put("OK", nom+" vous etes bien inscrit ");
			jo.put("nom", u.getNom());
		}
		reponse.setCorps(jo.toString());
		return reponse;
	}
	public ReponseHttp connection() {
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "application/json");
		String nom = requete.getParametre("nom");
		String motDePasse = requete.getParametre("motDePasse");
		JSONObject jo = new JSONObject();

		boolean valide=true;
		if(nom==null || nom==""){
			jo.put("KO", "parametre nom obligatoire");
			valide=false;
		}
		if(motDePasse==null || motDePasse==""){
			if(jo.has("KO"))
				jo.put("KO",jo.getString("KO")+" parametre motDePasse obligatoire");
			else
				jo.put("KO","parametre motDePasse obligatoire");
			valide=false;
		}

		if(valide){
			if(requete.getSession().existeAttribut("utilisateur")){
				//if( ((Utilisateur)(requete.getSession().getAttribut("utilisateur"))).getNom().equals(requete.getParametre("nom"))){
				
				jo.put("KO", "Il y a deja une connection");
				reponse.setCorps(jo.toString());
				return reponse;
				//}
			}
			else{
				
				if(utilisateurs.containsKey(nom) && utilisateurs.get(nom).getMotDePasse().equals(motDePasse)){
					Utilisateur u = utilisateurs.get(nom);
					u.connecte=true;
					requete.getSession().setAttribut("utilisateur", u);
					jo.put("OK", "Connection reussie Bienvenue "+nom);
					try {
						reponse.setEntete("Content-type", "text/html");
						reponse.setCorps(Template.transform("ressources/notificationPresence.html", u));
						return reponse;
					} catch (DarException e) {
						
						e.printStackTrace();
					}
//					jo.put("nom", u.getNom());
//					jo.put("utilisateur", u.toJSON());
//					reponse.setCorps(jo.toString());
					return reponse;
				}
				else{
					//reponse.setCorps("nom d'utilisateur et/ou mot de passe incorrecte");
					jo.put("KO", "nom d'utilisateur et/ou mot de passe incorrecte");
					reponse.setCorps(jo.toString());
					return reponse;
				}
			}
		}
		reponse.setCorps(jo.toString());
		return reponse;
	}

	public ReponseHttp deconnection(){
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "application/json");
		JSONObject jo = new JSONObject();
		if(!requete.getSession().existeAttribut("utilisateur")){
			jo.put("KO", "Vous n'etes pas connecter");
			reponse.setCorps(jo.toString());
			return reponse;
		}
		else{
			Utilisateur u = (Utilisateur)requete.getSession().getAttribut("utilisateur");
			u.connecte=false;
			u.disponible=false;
			u.disponibilite="Indisponible";
			requete.getSession().supprimerAttribut("utilisateur");
			jo.put("OK", "A bientot "+u.nom);
			jo.put("nom", u.getNom());
			reponse.setCorps(jo.toString());
		}
		return reponse;
	}
	// GET /notificationPresence/disponible 
	public ReponseHttp disponible(String nom){
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "application/json");
		utilisateurs.get(nom).disponible=true;
		utilisateurs.get(nom).disponibilite="Disponible";
		reponse.setCorps(utilisateurs.get(nom).toJSON().toString());
		return reponse;
	}
	
	// GET /notificationPresence/indisponible 
		public ReponseHttp indisponible(String nom){
			ReponseHttp reponse = new ReponseHttp();
			reponse.setEntete("Content-type", "application/json");
			utilisateurs.get(nom).disponible=false;
			utilisateurs.get(nom).disponibilite="Indisponible";
			reponse.setCorps(utilisateurs.get(nom).toJSON().toString());
			return reponse;
		}
	
	// GET /notificationPresence/utilisateurs
	public ReponseHttp utilisateurs(){
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "application/json");
		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		for( String u: utilisateurs.keySet() ){
			jo = new JSONObject();
			jo.put("id", utilisateurs.get(u).id);
			jo.put("nom", u);
			jo.put("connecte", utilisateurs.get(u).connecte);
			jo.put("disponible", utilisateurs.get(u).disponible);
			ja.add(jo);
		}
		reponse.setCorps(ja.toJSONString());
		return reponse;
	}
	public ReponseHttp index(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<  INDEX >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "text/html");
		if(requete.getSession().existeAttribut("utilisateur")){
			
			try {
				System.out.println( "+++++++++++++++++++++++++++++ "+(Utilisateur)(requete.getSession().getAttribut("utilisateur")));
				reponse.setCorps(Template.transform("ressources/notificationPresenceConnecte.html", (Utilisateur)(requete.getSession().getAttribut("utilisateur"))));
			} catch (DarException e) {
				e.printStackTrace();
			}
		}
		else{
			try {
				reponse.setCorps(Fichier.lectureFichier("ressources/notificationPresence.html"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return reponse;
	}
	
}

