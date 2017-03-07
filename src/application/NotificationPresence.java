package application;

import java.util.HashMap;

import org.json.JSONObject;

import serveur.ReponseHttp;
import serveur.Service;

public class NotificationPresence extends Service {

	private static HashMap<String, Utilisateur> utilisateurs = new HashMap<String, Utilisateur>();

	public class Utilisateur{
		private String nom;
		private String motDePasse;
		public Utilisateur(String nom, String motDePasse) {
			this.nom = nom;
			this.motDePasse = motDePasse;
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
		@Override
		public String toString() {
			return "Utilisateur [nom=" + nom + ", motDePasse=" + motDePasse + "]";
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
					requete.getSession().setAttribut("utilisateur", u);
					jo.put("OK", "Connection reussit Bienvenue "+nom);
					jo.put("nom", u.getNom());
					reponse.setCorps(jo.toString());
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
			requete.getSession().supprimerAttribut("utilisateur");
			jo.put("OK", "A bientot "+u.nom);
			jo.put("nom", u.getNom());
			reponse.setCorps(jo.toString());
		}
		return reponse;
	}
}

