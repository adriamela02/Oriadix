package M9Projecte1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LlegirFitxer {
	
	private BufferedReader br;
	private FileReader fr;
	
	private File arxiu;

	public LlegirFitxer (String nom) {
		this.arxiu = new File(nom);
	}
	public String llegir() throws IOException {
		String linia;
		String dades = "";
		
		//iniciar classes
		this.fr = new FileReader(this.arxiu);
		this.br = new BufferedReader(this.fr);
	
		//
		while( (linia = br.readLine())!=null) {
			dades += linia;
		}
		return dades;
	}
	
}
