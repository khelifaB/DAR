package com.dar.serveur;

public class EchoServeur {

	public static ReponseHttp echoServeur(RequeteHttp requete) {

		ReponseHttp reponse = new ReponseHttp();

		//		if(requete.getParametre("type")==null){
		//			reponse.setStatut(400);
		//			reponse.setCorps("parametre type attendut");
		//			return reponse;
		//		}
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
		reponse.setCorps("Format inconnu");
		return reponse;

	}

}
