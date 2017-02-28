package application;

import java.util.HashMap;

import serveur.ReponseHttp;
import serveur.Service;

public class GestionUtilisateur extends Service {

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
	public GestionUtilisateur() {
	}

	public ReponseHttp inscription(){
		ReponseHttp reponse = new ReponseHttp();
		String nom = requete.getParametre("nom");
		String motDePasse = requete.getParametre("motDePasse");
		
		reponse.setEntete("Content-type", "text/plain");
		boolean valide=true;
		if(nom==null || nom==""){
			reponse.setCorps("parametre nom obligatoire");
			valide=false;
		}
		if(motDePasse==null || motDePasse==""){
			reponse.setCorps(reponse.getCorps()+"\nparametre motDePasse obligatoire");
			valide=false;
		}

		if(valide){
			Utilisateur u = new Utilisateur(nom, motDePasse);
			if(utilisateurs.containsKey(nom)){
				reponse.setCorps("utilisateur "+nom+" est deja inscrit");
				valide=false;
				return reponse;
			}
			utilisateurs.put(nom, u);
			reponse.setCorps(nom+" vous etes bien inscrit ");
		}
		return reponse;
	}
	public ReponseHttp connection() {
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "text/plain");
		String nom = requete.getParametre("nom");
		String motDePasse = requete.getParametre("motDePasse");
		
		boolean valide=true;
		if(nom==null || nom==""){
			reponse.setCorps("parametre nom obligatoire");
			valide=false;
		}
		if(motDePasse==null || motDePasse==""){
			reponse.setCorps(reponse.getCorps()+"\nparametre motDePasse obligatoire");
			valide=false;
		}

		if(valide){
			if(requete.getSession().existeAttribut("utilisateur")){
				reponse.setCorps("Vous etes deja connecter");
				return reponse;
			}
			else{
				if(utilisateurs.containsKey(nom) && utilisateurs.get(nom).getMotDePasse().equals(motDePasse)){
					Utilisateur u = utilisateurs.get(nom);
					requete.getSession().setAttribut("utilisateur", u);
					reponse.setCorps("Connection reussit Bienvenue "+nom);
					return reponse;
				}
				else{
					reponse.setCorps("nom d'utilisateur et/ou mot de passe incorrecte");
					return reponse;
				}
			}
		}
		return reponse;
	}
	
	public ReponseHttp deconnection(){
		ReponseHttp reponse = new ReponseHttp();
		reponse.setEntete("Content-type", "text/plain");
		
		if(!requete.getSession().existeAttribut("utilisateur")){
			reponse.setCorps("Vous n'etes pas connecter");
			return reponse;
		}
		else{
			Utilisateur u = (Utilisateur)requete.getSession().getAttribut("utilisateur");
			requete.getSession().supprimerAttribut("utilisateur");
			reponse.setCorps("A bientot "+u.nom);
		}
		return reponse;
	}
}

