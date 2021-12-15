package M9Projecte1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EscriureFitxer {
	
	private File arxiu;
	private PrintWriter pw;
	private FileWriter fw;
	
	//propietats llegir
	private BufferedReader br;
	private FileReader fr;
	
	
	//constructor
	public EscriureFitxer (String nom) {
		this.arxiu = new File(nom);
	}
	//metode escriure text
	public void escriu(String linia) throws IOException {
		//creem un filWriter
		this.fw = new FileWriter(this.arxiu);
	
		//creem el printWriter (sobreescriu si)
		this.pw = new PrintWriter(this.fw);
		//this.pw = new PrintWriter(this.arxiu);
		
		//escriu la linia a l'arxiu
		this.pw.println(linia);
		
		//activar el flush de la classe
		//this.pw.flush();
		
		//tancar el printWriter
		this.pw.close();
		
		//tancar fileWriter
		this.fw.close();
	}
	
	//metode per llegir text
//	public String llegir() throws IOException {
//		String linia;
//		String dades = "";
//		
//		//iniciar classes
//		this.fr = new FileReader(this.arxiu);
//		this.br = new BufferedReader(this.fr);
//	
//		while( (linia = br.readLine())!=null) {
//			dades += linia + "\n";
//		}
//		return dades;
//	}

	

}
