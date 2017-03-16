package serveur;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tools.Fichier;
import tools.Reflexion;

public class Dispatcher {

	public static ReponseHttp getService(RequeteHttp requete) {

		// url /nom application/...

		String[] paramTab = requete.getUrl().getChemin().split("/");
		String nomApplication = paramTab[1];

		JSONParser parser = new JSONParser();

		try {     
			JSONObject jsonObject =  (JSONObject)  parser.parse(new FileReader("mappingJSON/"+nomApplication+".json"));

			String url = (String) jsonObject.get("url");
			System.out.println(url);

			String classe = (String) jsonObject.get("classe");

			JSONArray methodes = (JSONArray)jsonObject.get("methodes");

			for(Object o : methodes){
				JSONObject oj = (JSONObject) o;
				String urli = (String) oj.get("url");
				String verbe = (String) oj.get("type");
				String methodeJava = (String)oj.get("methode");

				if(verbe.equals(requete.getVerbe().toString())) {
					if(requete.getUrl().getChemin().indexOf('/', 1)!=-1){
						String chemin = requete.getUrl().getChemin().substring(requete.getUrl().getChemin().indexOf('/', 1));

						if(chemin.matches(urli)){

							Pattern p = Pattern.compile(urli);
							Matcher m = p.matcher(chemin);
							String[] paramatere=null;

							if(m.matches()){
								paramatere = new String[m.groupCount()];
								for(int i=1; i<= m.groupCount(); i++){
									paramatere[i-1]=m.group(i);
//									System.out.println(paramatere[i-1]);
								}
							}
							System.out.println(Arrays.toString(paramatere));
							ReponseHttp reponse =(ReponseHttp) Reflexion.invokeMethod(classe, methodeJava, paramatere, requete);
							return reponse;

						}
					}
				}
			}

			// si pas de methode specifique 
			// Construire et renvoyre la page d'accueil de l'application

			String cheminAccueil = "ressources/"+nomApplication+".html";
			File f = new File("ressources/"+nomApplication+".html");
			if(f.exists()){
				ReponseHttp reponse = new ReponseHttp();
				reponse.setCorps(Fichier.lectureFichier(cheminAccueil));
				return reponse;

			}


		} catch (Exception e) {
			ReponseHttp reponse = new ReponseHttp();
			reponse.setStatut(404);
			reponse.setCorps(e.getMessage());
			e.printStackTrace();
			return reponse;
		}

		ReponseHttp reponse = new ReponseHttp();
		reponse.setStatut(404);
		reponse.setCorps("url non trouvee");
		return reponse;
	}

}