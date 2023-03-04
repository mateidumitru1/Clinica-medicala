package date;

public class Tratamente {
	private int id, idCategorie;
	private String afectiune;
	
	public Tratamente(int id, String afectiune, int idCategorie) {
		setId(id);
		setAfectiune(afectiune);
		setIdCategorie(idCategorie);
	}
	
	public int getIdCategorie() {
		return idCategorie;
	}
	
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}
	
	public String getAfectiune() {
		return afectiune;
	}
	
	public void setAfectiune(String afectiune) {
		this.afectiune = afectiune;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
