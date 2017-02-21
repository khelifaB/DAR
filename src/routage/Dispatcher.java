package routage;

import java.awt.Point;
import java.awt.image.RescaleOp;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import exception.DarException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import application.Service;
import serveur.ReponseHttp;
import serveur.RequeteHttp;
import serveur.URLHttp;
import tools.Fichier;

public class Dispatcher {
	private RequeteHttp requete;
	private URLHttp url;

	public Dispatcher(RequeteHttp requete) {
		this.requete = requete;
	}

	public ReponseHttp process() {
		String content = Fichier.lectureFichier("C:\\Users\\khelifa.berrefas\\DAR\\workspace\\DAR1\\src\\routage\\routage.txt");
		String classe = "application.WebJouet";
		String methode = "getListPoint";

		ReponseHttp reponse = new ReponseHttp();

		try {
			Class c = Class.forName(classe); // Accès à la classe Livre

			System.out.println(c.getConstructors());

			Constructor constr = c.getConstructor(); // Obtenir le
			// constructeur
			// (RequeteHttp)
			Object o = constr.newInstance(); // -> new Livre("Programmation
			// Java", 120);

			Method method = c.getMethod(methode); // Obtenir la méthode
			// getNombreDeFeuilles(int)
			List<Point> pts = (List<Point>) method.invoke(o); // ->
			// o.getNombreDeFeuilles(2);
			System.out.println("#########################");
			System.out.println(pts);
			reponse.setEntete("Content-type", "text/html");
			reponse.setCorps(pts.toString());
			System.out.println("#########################");
		} catch (Exception e) {
			e.printStackTrace(System.out);
			reponse.setStatut(400);
			reponse.setCorps(e.getMessage());
			return reponse;
		}

		return reponse;
	}

	public static Service getService(RequeteHttp requete) {

		// url /nom application/...

		String nomApplication = requete.getUrl().getChemin().split("/")[1];
//		System.out.println(nomApplication);

		JSONParser parser = new JSONParser();

		try {     
			JSONObject jsonObject =  (JSONObject)  parser.parse(new FileReader("mappingJSON/"+nomApplication+".json"));

			String url = (String) jsonObject.get("url");
			System.out.println(url);

			String classe = (String) jsonObject.get("classe");
//			System.out.println(classe);

			Class<?> serviceClasse = Class.forName(classe);
			return (Service) serviceClasse.newInstance();
			 

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
