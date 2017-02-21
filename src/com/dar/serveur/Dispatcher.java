package com.dar.serveur;

import com.dar.exception.DarException;
import com.dar.tools.Fichier;
import com.dar.tools.Reflexion;

public class Dispatcher {
	private RequeteHttp requete;
	private URLHttp url;

	public Dispatcher(RequeteHttp requete) {
		this.requete = requete;
	}

	public ReponseHttp process() {
		String content = Fichier
				.lectureFichier("C:\\Users\\khelifa.berrefas\\DAR\\workspace\\DAR1\\src\\routage\\routage.txt");

		String nameMethodWithClass = ServeurHttp.mapUrlToMethod.get(requete.getUrl().getChemin());

		ReponseHttp reponse = new ReponseHttp();
		String methode = nameMethodWithClass.substring(nameMethodWithClass.lastIndexOf('.') + 1,
				nameMethodWithClass.length());
		String classe = nameMethodWithClass.substring(0, nameMethodWithClass.lastIndexOf('.'));

		try {
			reponse = (ReponseHttp) Reflexion.invokeMethod(classe, methode, new Object[] { requete, reponse });

		} catch (DarException e) {
			e.printStackTrace(System.out);
			reponse.setStatut(400);
			reponse.setCorps(e.getMessage());
			return reponse;
		}

		return reponse;
	}

}
