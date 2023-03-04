package date;

public class Categorie {
	private int id;
	private String denumire;
	
	public Categorie(int id, String denumire) {
		setId(id);
		setDenumire(denumire);
	}
	
	public String getDenumire() {
		return denumire;
	}
	
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
