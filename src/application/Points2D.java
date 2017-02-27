package application;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONObject;

import serveur.Dispatcher;
import serveur.ReponseHttp;
import serveur.RequeteHttp;
import serveur.Service;

public class Points2D extends Service{

	private static HashMap<String, Point> points = new HashMap<String, Point>();

	public Points2D() {

	}

	public ReponseHttp list() {
		ReponseHttp reponse = new ReponseHttp();
		StringBuilder sb =new StringBuilder();
		JSONObject jo = new JSONObject();
		for(String id : points.keySet()){
			JSONObject jp = new JSONObject();
			jp.put("x", points.get(id).x);
			jp.put("y", points.get(id).y);
			jo.put(id, jp);
		}
		reponse.setCorps(jo.toString());
		return reponse;
	}

	public ReponseHttp getXbyId(String id){
		ReponseHttp reponse = new ReponseHttp();
		Point p = points.get(id);
		if(p==null){
			reponse.setCorps("id:"+ id +" de point inconnu");
			return reponse;
		}

		reponse.setCorps(p.x+"");
		return reponse;
	}

	public ReponseHttp getYbyId(String id){
		ReponseHttp reponse = new ReponseHttp();
		Point p = points.get(id);
		if(p==null){
			reponse.setCorps("id:"+ id +" de point inconnu");
			return reponse;
		}

		reponse.setCorps(p.y+"");
		return reponse;
	}

	public ReponseHttp putPointById(String id){
		ReponseHttp reponse = new ReponseHttp();
		Point p = points.get(id);
		if(p==null){
			reponse.setCorps("id:"+ id +" de point inconnu");
			return reponse;
		}

		int x,y;
		if(!"".equals(requete.getParametre("x")) && requete.getParametre("x")!=null){
			x = Integer.valueOf(requete.getParametre("x"));
			p.x=x;
			reponse.setCorps("point id:"+id+" modification de x");
		}
		
		if(!"".equals(requete.getParametre("y")) && requete.getParametre("y")!=null ){
			y = Integer.valueOf(requete.getParametre("y"));
			p.y=y;
			reponse.setCorps(reponse.getCorps()+" point id:"+id+" modification de y");
		}
		

		points.put(id, p);
		return reponse;
	}

	public ReponseHttp creerPoint(){
		ReponseHttp reponse = new ReponseHttp();
		try{
			String[] parametre = requete.getCorps().split("&");
			
			HashMap<String, String> parametres= new HashMap<String, String>();

			for(int i=0;i<parametre.length; i++){
				System.out.println("parametre : "+Arrays.toString(parametre));
				parametres.put(parametre[i].split("=")[0], parametre[i].split("=")[1]);
			}
			
			String x = parametres.get("x");
			String y = parametres.get("y");
			
			Point p = new Point(Integer.parseInt(x), Integer.parseInt(y));
			int id = (points.size()+1);
			points.put(id+"", p);
			
			reponse.setCorps(id+"");

			return reponse;
		}catch(Exception e){
			e.printStackTrace();
			reponse.setStatut(400);
			reponse.setCorps(e.getMessage());
			return reponse;
		}
	}

	public ReponseHttp supprimerPoint(String id){
		ReponseHttp reponse = new ReponseHttp();
		if(points.remove(id)!=null)
			reponse.setCorps("ok");
		else
			reponse.setCorps("ko");
		return reponse;
	}
}
