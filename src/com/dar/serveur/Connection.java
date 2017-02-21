package com.dar.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Connection extends Thread {

	private Socket socketClient;

	public Connection(Socket socketClient) {
		this.socketClient=socketClient;
	}

	@Override
	public void run() {


		BufferedReader br;
		PrintStream ps;
		StringBuffer requeteRecu = new StringBuffer();
		RequeteHttp requete=new RequeteHttp();
		ReponseHttp reponse = new ReponseHttp();
		String tailleCorps="0";

		System.out.println("Nouvelle connection");

		try{

			br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			ps = new PrintStream(socketClient.getOutputStream());
			String ligne = "";


			//System.out.println("Avant readLine");
			ligne = br.readLine();
			//System.out.println("Apres readLine text=#"+textRequete+"#");
			
			// Lecture des entetes
			while (ligne != null && !ligne.equals("")){
				requeteRecu.append(ligne).append(System.lineSeparator());
				//System.out.println("Avant readLine");
				ligne = br.readLine();
//				System.out.println("Apres readLine text=#"+ligne+"#");
			}
			
			// Analyse de la requete  et creation des entetes
			requete.parse(requeteRecu.toString());	
			
			// Corps
			tailleCorps=requete.getEntete("Content-Length");
			if(tailleCorps==null)
				tailleCorps="0";
			
			try{
				int iTailleCorps=Integer.parseInt(tailleCorps);
				char[]corps = new char[iTailleCorps];
				br.read(corps, 0, iTailleCorps);
				requete.setCorps(new String(corps));
			}
			catch(Exception e){
				e.printStackTrace();
				reponse.setStatut(400);
				ps.println(reponse.toString());
				ps.flush();
				ps.close();
				return;
			}
			
			System.out.println("#########################\n"+requete+"##################\n");
			System.out.println("************** "+requete.getUrl()+" ********************");
			//String pageResponse = Fichier.lectureFichier("ressources/test-0.txt");

			//ps.println(pageResponse);

		
			if(!requete.estValide()){
				reponse.setStatut(400);
				ps.println(reponse.toString());
				ps.flush();
				ps.close();
				return;
			}
			reponse.setCorps("<html><title>reponse</title><h>example</h></html>");	

			Dispatcher dispatcher = new Dispatcher(requete);
			reponse = dispatcher.process();

//			ps.println(reponse.toString());
//			reponse = EchoServeur.echoServeur(requete);
			ps.print(reponse.toString());
			System.out.println("REPONSE HTTP :\n"+reponse.toString()); // TODO remove
			ps.flush();
			ps.close();
		}
		catch(Exception e){
			try {
				e.printStackTrace();
				socketClient.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
	}


}
