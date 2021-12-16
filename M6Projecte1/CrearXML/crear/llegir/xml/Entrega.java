package crear.llegir.xml;

//CREEM LA CLASSE DE LA QUAL VOLEM CREAR EL XML AMB TOTES LES DADES QUE VOLEM AFEGIR I AMB ELS GETTERS I SETTERS

import java.time.LocalDate;

public class Entrega {
	private int id_entrega;
	private int id_treballador;
	private int id_paquet;
	private int id_client;
	private LocalDate data_entrega;
	private String uuid;
	
	public Entrega(int id_entrega, int id_treballador, int id_paquet, int id_client, LocalDate data_entrega, String uuid) {
		super();
		this.id_entrega = id_entrega;
		this.id_treballador = id_treballador;
		this.id_paquet = id_paquet;
		this.id_client = id_client;
		this.data_entrega = data_entrega;
		this.uuid = uuid;
	}

	public int getId_entrega() {
		return id_entrega;
	}

	public void setId_entrega(int id_entrega) {
		this.id_entrega = id_entrega;
	}

	public int getId_treballador() {
		return id_treballador;
	}

	public void setId_treballador(int id_treballador) {
		this.id_treballador = id_treballador;
	}

	public int getId_paquet() {
		return id_paquet;
	}

	public void setId_paquet(int id_paquet) {
		this.id_paquet = id_paquet;
	}

	public int getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public LocalDate getData_entrega() {
		return data_entrega;
	}

	public void setData_entrega(LocalDate data_entrega) {
		this.data_entrega = data_entrega;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
