package serveur.dispatcher;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import serveur.ReponseHttp;
import serveur.RequeteHttp;
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
			List<String> doublan = new ArrayList<>();
			for(Object o : methodes){
				JSONObject oj = (JSONObject) o;
				String verbe = (String) oj.get("type");
				String urli = (String) oj.get("url");
				doublan.add(verbe+urli);
			}
			
			// Detection de conflit d'urls
			List<String> doublan1 = doublan.stream().distinct().collect(Collectors.toList());
			if(doublan.size()!=doublan1.size()){
				ReponseHttp reponse = new ReponseHttp();
				reponse.setStatut(404);
				doublan = removeAll(doublan, doublan1);
				reponse.setCorps("Conflit des urls "+doublan);
				return reponse;
			}
			
			boolean index = false;
			String methodeIndex ="";
			for(Object o : methodes){
				JSONObject oj = (JSONObject) o;
				String urli = (String) oj.get("url");
				String verbe = (String) oj.get("type");
				String methodeJava = (String)oj.get("methode");

				if(urli.equals("/")){
					index = true;
					methodeIndex = methodeJava;
					
				}
				if(verbe.equals(requete.getVerbe().toString())) {
					if(requete.getUrl().getChemin().indexOf('/', 1)!=-1){
						String chemin = requete.getUrl().getChemin().substring(requete.getUrl().getChemin().indexOf('/', 1));

						if(chemin.matches(urli)){

							Pattern p = Pattern.compile(urli);
							Matcher m = p.matcher(chemin);
							String[] paramatere=null;
							
							// extraction des parametre de l'url
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

			if(index){
				ReponseHttp reponse =(ReponseHttp) Reflexion.invokeMethod(classe, methodeIndex,null, requete);
				return reponse;
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
	
	public static List<String> removeAll(List<String> l1, List<String> l2){
		for (int i = 0; i < l2.size(); i++) {
			for (int j = 0; j < l1.size(); j++) {
				if(l2.get(i).equals(l1.get(j))){
					l1.remove(j);
				}
			}
		}
		return l1;
	}

}