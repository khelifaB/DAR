package com.dar.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Fichier {
	public static String readPage(String path) throws IOException {
		String content = Files.lines(Paths.get(path)).collect(Collectors.joining());
		return content;
	}

	public static String lectureFichier(String path) {
		String lines = "";
		try {
			File f = new File(path);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			try {
				String line = br.readLine();
				
				while (line != null) {

					lines += line + "\n";
					line = br.readLine();	
				}

				br.close();
				fr.close();

			} catch (IOException exception) {
				System.out.println("Erreur lors de la lecture : " + exception.getMessage());

			}
		} catch (FileNotFoundException exception) {
			System.out.println("Le fichier n'a pas �t� trouv�");
		}
		return lines;
	}
}
