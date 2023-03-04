package date;

public class Medici {
	private int id;
	private String nume, prenume, cnp, specializare;
	
	
	public Medici (int id, String nume, String prenume, String cnp, String specializare) {
		setId(id);
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSpecializare(specializare);
	}
	
	public Medici (int id, String nume, String prenume, String specializare) {
		setId(id);
		setNume(nume);
		setPrenume(prenume);
		setSpecializare(specializare);
	}
	
	public Medici(String nume, String prenume, String cnp, String specializare) {
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSpecializare(specializare);
	}

	public Medici() {	}

	public Medici(String nume, String prenume, String specializare) {
	
		setNume(nume);
		setPrenume(prenume);
		setSpecializare(specializare);
	}

	public Medici(int int1, String string, String string2) {
		
		setId(int1);
		setNume(string);
		setPrenume(string2);
	}

	public String getNume() {
		return nume;
	}
	
	public void setNume(String nume) {
		this.nume = nume;
	}
	
	public String getPrenume() {
		return prenume;
	}
	
	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}
	
	public String getCnp() {
		return cnp;
	}
	
	public void setCnp(String cnp) {
		this.cnp = cnp;
	}
	
	public String getSpecializare() {
		return specializare;
	}
	
	public void setSpecializare(String specializare) {
		this.specializare = specializare;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
