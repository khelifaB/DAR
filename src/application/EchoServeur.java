package application;

import serveur.ReponseHttp;
import serveur.RequeteHttp;

public class EchoServeur extends Service{

	@Override
	protected ReponseHttp doGet(RequeteHttp requete) {
		ReponseHttp reponse = new ReponseHttp();
		String enteteAccept = requete.getEntete("Accept");
		String[] typesAccept = enteteAccept.split(",");
		
		for(int i=0; i< typesAccept.length;i++){
			String type = typesAccept[i];
			switch (type) {
			case "text/plain":
				reponse.setEntete("Content-type","text/plain");
				reponse.setCorps(requete.toString());
				return reponse;
			case "text/html":
				reponse.setEntete("Content-type","text/html");
				reponse.setCorps(requete.toHTML());
				return reponse;
			case "application/json":
				reponse.setEntete("Content-type","application/json");
				reponse.setCorps(requete.toJSON());
				return reponse;
			}
		}

		reponse.setStatut(400);
		reponse.setCorps("Format non supporte ");
		return reponse;
	}
}