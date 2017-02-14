package routage;

import java.awt.Point;
import java.awt.image.RescaleOp;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

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
		String content = Fichier
				.lectureFichier("C:\\Users\\khelifa.berrefas\\DAR\\workspace\\DAR1\\src\\routage\\routage.txt");
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

}
