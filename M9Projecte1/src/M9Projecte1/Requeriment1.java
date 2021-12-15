package M9Projecte1;

import java.io.File;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import M9Projecte1.LlegirFitxer;

//guardar clau publica i signatura
public class Requeriment1 {

	public static void main(String[] args) throws IOException {
		File carpeta;
		carpeta=new File(File.listRoots()[0]+"GuardarSigPub");
		carpeta.mkdir();
		
		   Scanner sn = new Scanner(System.in);
		   Scanner sp = new Scanner(System.in);
		   Scanner sv = new Scanner(System.in);
		   Scanner xf = new Scanner(System.in);
		   System.out.println("ruta "+ carpeta.getAbsolutePath());
		   
		   //Signatura
		    KeyPair claus;			
			claus = randomGenerate(2048);
			PrivateKey privada = claus.getPrivate();
			PublicKey publica = claus.getPublic();
			byte[] privadaBytes = privada.getEncoded();
			byte[] publicaBytes = publica.getEncoded();
			System.out.println("La clau privada és: " + Base64.getEncoder().encodeToString(privadaBytes));
			System.out.println("La clau pública és: " + Base64.getEncoder().encodeToString(publicaBytes));

			//
			 SecretKey key;
			   key = keygenKeyGeneration(128);
			//
	       boolean sortir = false;
	       int opcio;
	       
	       do {
	    	   System.out.println("-----------------------------");
	    	   System.out.println("--     MENU PRINCIPAL:     --");
	    	   System.out.println("--    1.Signar document    --");
	    	   System.out.println("--   2.Validar signatura   --");
	    	   System.out.println("--   3.Encriptar fitxer    --");
	    	   System.out.println("--  4.Desencriptar fitxer  --");
	    	   System.out.println("--         5.EXIT          --");
	    	   System.out.println("-----------------------------");
	    	   
	    	   System.out.println("Selecciona opció a fer:");
	    	   opcio = sn.nextInt();
	    	   
	    	   switch(opcio) {
	    	   case 1:
	    		  System.out.println("Intrdoueix la ruta del document a signar: "); 
	    		  String ruta = sp.nextLine();
	    		  
	    		  LlegirFitxer fitxerSignar = new LlegirFitxer(ruta);
	    			String textSignar = fitxerSignar.llegir();
	    			byte[] textSignat;
	    		  try {
	    			  textSignat = signData(textSignar.getBytes(), privada);
	    			  String guardarsignatura = Base64.getEncoder().encodeToString(textSignat);
	    			  String guardarpublica = Base64.getEncoder().encodeToString(publicaBytes);
	    			  System.out.println("signatura: "+ guardarsignatura);
	    			  System.out.println("publica: "+ guardarpublica);
	    			  
	    			  EscriureFitxer fitxersign = new EscriureFitxer(carpeta.getAbsolutePath()+"\\signatura.sign");
	    			  fitxersign.escriu(guardarsignatura);
	    			  EscriureFitxer fitxerpub = new EscriureFitxer(carpeta.getAbsolutePath()+"\\publica.pub");
	    			  fitxerpub.escriu(guardarpublica);
	    			  System.out.println("Arxius generats a "+carpeta.getAbsolutePath());
	    			  
	    		  }catch (Exception e) {
	  				e.printStackTrace();
	  			}
	    					  
	    		  break;
	    	   case 2:
	    		   System.out.println("Valida la signatura");
	    		   System.out.println("Ubica el directori on es guarda la clau pública i signatura: ");
	    		   String rutavalidar = sv.nextLine();
	    		   LlegirFitxer llegirSignatura = new LlegirFitxer(rutavalidar+"\\signatura.sign");
	    		   LlegirFitxer llegirClauPublica = new LlegirFitxer(rutavalidar+"\\publica.pub");
	    		   try {
	    		   String llegirClauGuardada = llegirClauPublica.llegir();
	    		   String llegirSigGuardada = llegirSignatura.llegir();
	    		   System.out.println("Clau Guardada: "+llegirClauGuardada);
	    		   System.out.println("Signatura Guardada: "+llegirSigGuardada);
	    		   
	    		  byte[] arraySign = Base64.getDecoder().decode(llegirSigGuardada);
	    		  
	    		  byte[] publicBytes2 = Base64.getDecoder().decode(llegirClauGuardada);
	    		  X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes2);
	    		  KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	    		  PublicKey pubKey = keyFactory.generatePublic(keySpec) ;
	    		 
	    		  System.out.println("Ruta del arixu a validar, previament utilitzat: ");
	    		  String rutafinal = sv.nextLine();
	    		  LlegirFitxer fitxerSignar2 = new LlegirFitxer(rutafinal);
	    		  String textSignar2 = fitxerSignar2.llegir();
	    		  
	    		  
	    		  boolean validarSignatura = validateSignature(textSignar2.getBytes(), arraySign, pubKey);
	    		  System.out.println("Resultat de validacio: "+validarSignatura);
	    		   }catch(Exception e) {
		  			e.printStackTrace();
	    		  }
	    		   break;
	    		   
	    	   case 3:
	    		   	System.out.println("Encriptar fitxer");
	    		   
	    		   //CREAR CLAU
	    		   String clauAES = Base64.getEncoder().encodeToString(key.getEncoded());
	    		   
	    		   System.out.println("S'ha generat la clau correctament");
	    		   
	    		   //ESCRIC LA CLAU A UN FITXER DE NOM clau.txt
	    		   EscriureFitxer clauFitxer = new EscriureFitxer(carpeta.getAbsolutePath() + "\\clau.txt");
	    		   clauFitxer.escriu(clauAES);
	    		   
	    		   //ANEM A BUSCAR L'ARXIU PER XIFRAR
	    		   System.out.println("Intrdoueix la ruta del document a Xifrar: "); 
	    		   String rutaFitxer = sp.nextLine();
	    		   
	    		   //LLEGIM EL FITXER
	    		   LlegirFitxer llegirFitxer = new LlegirFitxer(rutaFitxer);
	    		   
	    		   //ENCRIPTO EL FITXER AMB LA CLAU CREADA
	    		   byte[] textXifrat = encryptData(key,llegirFitxer.llegir().getBytes());
	    		   
	    		   //SOBRESCRIC EL FITXER ENCRIPTAT
	    		   EscriureFitxer fitxerXifratEscrit = new EscriureFitxer(rutaFitxer);
	    		   fitxerXifratEscrit.escriu(Base64.getEncoder().encodeToString(textXifrat));
	    		   System.out.println("S'ha generat el fitxer: " + fitxerXifratEscrit + " a la ruta " + carpeta.getAbsolutePath());	  
	    		   break;
	    		   
	    		   
	    		   
	    		
	    	   case 4: 
	    		   System.out.println("Desencriptar fitxer");
    		   
    		   //LLEGEIXO EL FITXER ON HI HA CLA CLAU
    		   LlegirFitxer keyLlegida = new LlegirFitxer(carpeta.getAbsolutePath() + "\\clau.txt");
    		   
    		   //ANEM A BUSCAR EL DOCUMENT A DESXIFRAR
    		   System.out.println("Intrdoueix la ruta del document a desencriptar: "); 
    		   String ruta2 = sp.nextLine();
	    		  
    		   //LLEGIM EL FITXER
    		   LlegirFitxer fitxerDesxifrar = new LlegirFitxer(ruta2);
    		   String llTextXifrat = fitxerDesxifrar.llegir();
    		   
    		   byte[] contingutXifrat = Base64.getDecoder().decode(llTextXifrat);
    		   
    		   //DESENCRIPTEM EL TEXT LLEGIT
    		   byte[] textDesxifrat = decryptData(key,contingutXifrat);
    		   
    		   FileOutputStream fileOuputStream = new FileOutputStream(ruta2);
   			   fileOuputStream.write(textDesxifrat);
   			   fileOuputStream.close();
    		   
    		   System.out.println("El text esta desencriptat.");
	    		   break;
	    		   
	    	   case 5:
	    		   sortir = true;
	    		   break;
	    	   default:
	    		   	System.out.println("Sol permes entre 1-5");
	    	   
	    	   
	    	   }
	    	   
	    	   
	       }
	       while(!sortir);
}

	public static KeyPair randomGenerate(int len) {
		KeyPair keys = null;
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(len);
			keys = keyGen.genKeyPair();
		} catch (Exception ex) {
			System.err.println("Generador no disponible.");
		}
		return keys;
	}

	public static byte[] signData(byte[] data, PrivateKey priv) {
		byte[] signature = null;
		try {
			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initSign(priv);
			signer.update(data);
			signature = signer.sign();
		} catch (Exception ex) {
			System.err.println("Error signant les dades: " + ex);
		}
		return signature;

	}

	public static boolean validateSignature(byte[] data, byte[] signature, PublicKey pub) {
		boolean isValid = false;
		try {
			Signature signer = Signature.getInstance("SHA1withRSA");
			signer.initVerify(pub);
			signer.update(data);
			isValid = signer.verify(signature);
		} catch (Exception ex) {
			System.err.println("Error validant les dades: " + ex);
		}
		return isValid;
	}
	public static byte[] encryptData(byte[] data, PublicKey pub) {
		byte[] encryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
			cipher.init(Cipher.ENCRYPT_MODE, pub);
			encryptedData = cipher.doFinal(data);
		} catch (Exception ex) {
			System.err.println("Error xifrant: " + ex);
		}
		return encryptedData;
	}
	
	//desxifrar RSA directe
	public static byte[] decryptData(byte[] data, PrivateKey pri) {
		byte[] decryptedData = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
			cipher.init(Cipher.DECRYPT_MODE, pri);
			decryptedData = cipher.doFinal(data);
		} catch (Exception ex) {
			System.err.println("Error xifrant: " + ex);
		}
		return decryptedData;
	}
	 public static byte[] encryptData(SecretKey sKey, byte[] data) {
			
			byte[] encryptedData = null;
			
			try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, sKey);
				encryptedData = cipher.doFinal(data);
			} catch (Exception ex) {
				System.err.println("Error xifrant les dades: " + ex);
			}
		return encryptedData;
		}
		
		//DESXIFRAR FRASES/PARAULA/DADES
		public static byte[] decryptData(SecretKey sKey, byte[] data) {
			
			byte[] decryptedData = null;
			
			try {
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, sKey);
				decryptedData = cipher.doFinal(data);
			} catch (Exception ex) {
				System.err.println("Error xifrant les dades: " + ex);
			}
		return decryptedData;
		}
	
	public static byte[]decryptDataProva(String llTextXifrat, PrivateKey pri) {
		byte[] decryptDataProva = null;
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
			cipher.init(Cipher.DECRYPT_MODE, pri);
			decryptDataProva = cipher.doFinal(decryptDataProva);
		} catch (Exception ex) {
			System.err.println("Error xifrant: " + ex);
		}
		return decryptDataProva;
	}
	
	//GENERAR CLAU ALEATORIA
	public static SecretKey keygenKeyGeneration(int keySize) {
		
		SecretKey sKey = null;
		
		if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
			try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(keySize);
			sKey = kgen.generateKey();
			
			} catch (NoSuchAlgorithmException ex) {
				System.err.println("Generador no disponible.");
			}
		}
		return sKey;
	}

}
