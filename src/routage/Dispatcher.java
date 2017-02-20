package routage;

import exception.DarException;
import serveur.ReponseHttp;
import serveur.RequeteHttp;
import serveur.URLHttp;
import tools.Fichier;
import tools.Reflexion;

public class Dispatcher {
	private RequeteHttp requete;
	private URLHttp url;

	public Dispatcher(RequeteHttp requete) {
		this.requete = requete;
	}

	public ReponseHttp process() {
		String content = Fichier
				.lectureFichier("C:\\Users\\khelifa.berrefas\\DAR\\workspace\\DAR1\\src\\routage\\routage.txt");
		
		String classe = "application.WebJouet";
		String methode = "getListPoint";

		ReponseHttp reponse = new ReponseHttp();

		try {
			Reflexion.invokeMethod(classe, methode);
		} catch (DarException e) {
			e.printStackTrace(System.out);
			reponse.setStatut(400);
			reponse.setCorps(e.getMessage());
			return reponse;
		}

		return reponse;
	}

}
