package application;



import serveur.ReponseHttp;
import serveur.RequeteHttp;
import serveur.VerbeHttp;



public abstract class Service {

	public ReponseHttp traitementRequete(RequeteHttp requete) {
    	
    	if(!requete.estValide()){
    		ReponseHttp reponse = new ReponseHttp();
    		reponse.setStatut(400);
    		reponse.setCorps("Requete non valide");
    			return reponse;
    	}
    	
    	VerbeHttp verbe = requete.getVerbe();
    	
    	switch(verbe) {
    	case GET : 
    		return doGet(requete);
    	case HEAD : 
    		return doHead(requete);
    	case POST : 
    		return doPost(requete);
    	case OPTIONS : 
    		return doOptions(requete);
    	case PUT : 
    		return doPut(requete);
    	case DELETE : 
    		return doDelete(requete);
    	case TRACE : 
    		return doTrace(requete);
    	case  CONNECT:
    		return doConnect(requete);
    	default : 
    		return nonImplemente( verbe);
    		
    	}
    }

	protected ReponseHttp nonImplemente(VerbeHttp verbe) {
		ReponseHttp reponse = new ReponseHttp();
		reponse.setStatut(501);
		reponse.setCorps("Verbe "+verbe+" Http non implemente");
		return reponse;
	}
	
	protected  ReponseHttp doGet(RequeteHttp requete){
		return nonImplemente(VerbeHttp.GET);
	}
	protected  ReponseHttp doHead(RequeteHttp requete){
		return nonImplemente(VerbeHttp.HEAD);
	}
	protected  ReponseHttp doPost(RequeteHttp requete){
		return nonImplemente(VerbeHttp.POST);
	}
	protected  ReponseHttp doOptions(RequeteHttp requete){
		return nonImplemente(VerbeHttp.OPTIONS);
	}
	protected  ReponseHttp doPut(RequeteHttp requete){
		return nonImplemente(VerbeHttp.PUT);
	}
	protected  ReponseHttp doDelete(RequeteHttp requete){
		return nonImplemente(VerbeHttp.DELETE);
	}
	protected  ReponseHttp doTrace(RequeteHttp requete){
		return nonImplemente(VerbeHttp.TRACE);
	}
	protected  ReponseHttp doConnect(RequeteHttp requete){
		return nonImplemente(VerbeHttp.CONNECT);
	}
}
