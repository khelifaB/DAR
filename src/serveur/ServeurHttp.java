package serveur;


import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


public class ServeurHttp {

	public static void main(String[] args) {
		int port=0;
		Socket socketClient = null;
		ServerSocket serveur = null;
		

		if(args.length<1){
			System.out.println("numero de port manquant");
			return;
		}
		
		try{
			port = Integer.parseInt(args[0]);
		}catch (Exception e) {
			port = 8080;
			//System.out.println("numero de port non valide");
			
			return;
		}
		
		try {
			System.out.println("Lancement du serveur Http ... sur le port " + port);
			serveur = new ServerSocket(port);

			while (true) {
				
				System.out.println("Serveur Http en attente d'une connection");
				socketClient = serveur.accept();
				new Connection(socketClient).start();
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socketClient.close();
				serveur.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
