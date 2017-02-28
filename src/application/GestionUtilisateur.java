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
	
}

