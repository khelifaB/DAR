package tools;

import java.lang.reflect.Field;

public class TestReflexion {
	

	public static void main(String[] args) {
		try {
			Persone obj = new Persone();

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				System.out.println(field.getName() + " -> " + field.get(obj));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}

/*
 * Classe à traiter
 */
class Persone {
	private String nom;
	private String prenom;
	private String adresse;
	private String ville;
	private String cp;

	public Persone() {
		super();
		nom = "LE NOM";
		prenom = "Prénom";
		adresse = "une adresse";
		cp = "68000";
		ville = "VILLE";
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}
}