package crear.llegir.xml;

//CREEM LA CLASSE DE LA QUAL VOLEM CREAR EL XML AMB TOTES LES DADES QUE VOLEM AFEGIR I AMB ELS GETTERS I SETTERS

public class Paquet {
	private int id_paquet;
	private int bultos;
	private String direccio;
	private String poblacio;
	private int codi_postal;
	private String num_envio;
	private boolean entregat;
	private String latitud;
	private String longitud;
	
	public Paquet(int id_paquet, int bultos, String direccio, String poblacio, int codi_postal, String num_envio, Boolean entregat, String latitud, String longitud) {
		super();
		this.id_paquet = id_paquet;
		this.bultos = bultos;
		this.direccio = direccio;
		this.poblacio = poblacio;
		this.codi_postal = codi_postal;
		this.num_envio = num_envio;
		this.entregat = entregat;
	}

	public int getId_paquet() {
		return id_paquet;
	}

	public void setId_paquet(int id_paquet) {
		this.id_paquet = id_paquet;
	}

	public int getBultos() {
		return bultos;
	}

	public void setBultos(int bultos) {
		this.bultos = bultos;
	}

	public String getDireccio() {
		return direccio;
	}

	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}

	public String getPoblacio() {
		return poblacio;
	}

	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	public int getCodi_postal() {
		return codi_postal;
	}

	public void setCodi_postal(int codi_postal) {
		this.codi_postal = codi_postal;
	}

	public String getNum_envio() {
		return num_envio;
	}

	public void setNum_envio(String num_envio) {
		this.num_envio = num_envio;
	}

	public boolean isEntregat() {
		return entregat;
	}

	public void setEntregat(boolean entregat) {
		this.entregat = entregat;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}	
}
