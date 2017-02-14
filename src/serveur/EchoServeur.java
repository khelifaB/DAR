package serveur;

public class EchoServeur {

	public static ReponseHttp echoServeur(RequeteHttp requete) {
		
		ReponseHttp reponse = new ReponseHttp();
		
		if(requete.getParametre("type")==null){
			reponse.setStatut(400);
			reponse.setCorps("parametre type attendut");
			return reponse;
		}
		switch (requete.getParametre("type")) {
		case "text":
			reponse.setEntete("Content-type","text/plain");
			reponse.setCorps(requete.toString());
			return reponse;
		case "html":
			reponse.setEntete("Content-type","text/html");
			reponse.setCorps(requete.toHTML());
			return reponse;
			case "json":
				reponse.setEntete("Content-type","application/json");
				reponse.setCorps(requete.toJSON());
				return reponse;
		default:
			reponse.setStatut(400);
			reponse.setCorps("format inconnu");
			return reponse;
			
		}
	
	}
	
}
