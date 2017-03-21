package serveur;

import java.util.HashMap;

/**
 * @author khelifa.berrefas
 *
 */

public class URLHttp {

	private String url; // url complete chemin et parametres
	private String chemin;
	private HashMap<String, String> parametres;
	private String fragment;

	public URLHttp() {
		url="";
		chemin="";
		parametres=new HashMap<String, String>();
	}
	public URLHttp(String urlComplete) {
		this.url = urlComplete;
		chemin="";
		parametres=new HashMap<String, String>();
		fragment="";
	}

	public void parse(String urlComplete) {
		if(urlComplete.contains("#")){
			fragment=urlComplete.split("#").length==2?urlComplete.split("#")[1]:"";
			urlComplete = urlComplete.split("#")[0];
		}
		
		String[] urlTab = urlComplete.split("\\?");
		if(urlTab.length==2){ // il y a des parametres
			chemin=urlTab[0];
			String[] parametresTab = urlTab[1].split("\\&");
			if(parametresTab.length>=1){
				for(String parametre:parametresTab){
					String[] nomValeurParam =parametre.split("="); 
					if(nomValeurParam.length==2)
						parametres.put(nomValeurParam[0] ,nomValeurParam[1]);
					else if (nomValeurParam.length==1)
						parametres.put(nomValeurParam[0] ,null);
				}
			}
			else if(urlTab[1].split("=").length==2){
				parametres.put(urlTab[1].split("=")[0], urlTab[1].split("=")[1]);
			}
		}
		else{
			chemin=urlComplete;
		}
	}

	public String getUrl() {
		return url;
	}

	public String getChemin() {
		return chemin;
	}

	public HashMap<String, String> getParametres() {
		return parametres;
	}
	
	public String getParametre(String parametre) {
		return parametres.get(parametre);
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
	

	@Override
	public String toString() {
		return "URLHttp [chemin=" + chemin + " parametres="+parametres+"] urlComplete="+url;
	}

}
