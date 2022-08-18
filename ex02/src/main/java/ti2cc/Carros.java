package ti2cc;

public class Carros {
	private int id;
	private String modelo;
	private String cor;
	private String dono;
	
	public Carros() {
		this.id = -1;
		this.modelo = "";
		this.cor = "";
		this.dono = "";
	}
	
	public Carros(int id, String modelo, String cor, String dono) {
		this.id = id;
		this.modelo = modelo;
		this.cor = cor;
		this.dono = dono;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getDono() {
		return dono;
	}

	public void setDono(String dono) {
		this.dono = dono;
	}

	@Override
	public String toString() {
		return "Carros [id=" + id + ", modelo=" + modelo + ", cor=" + cor + ", dono=" + dono + "]";
	}	
}
