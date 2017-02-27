package application;

import java.util.HashMap;

import serveur.Service;

public class Authentification extends Service {

	private static HashMap<String, Utilisateur> utilisateurs;
	
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
	public Authentification() {
		utilisateurs = new HashMap<String, Utilisateur>();
	}
	
	
}

