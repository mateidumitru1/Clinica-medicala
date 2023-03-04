package date;


public class Pacienti {
	
	private int id, varsta, greutate, numar;
	private String nume, prenume, cnp, sex, oras, strada;
	
	
	public Pacienti (int id, String nume, String prenume, String cnp, String sex, int varsta, int greutate, String oras, String strada, int numar) {
		setId(id);
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSex(sex);
		setVarsta(varsta);
		setGreutate(greutate);
		setOras(oras);
		setStrada(strada);
		setNumar(numar);
	}
	
	public Pacienti (String nume, String prenume, String cnp, String sex, int varsta, int greutate, String oras, String strada, int numar) {
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSex(sex);
		setVarsta(varsta);
		setGreutate(greutate);
		setOras(oras);
		setStrada(strada);
		setNumar(numar);
	}
	
	public Pacienti (String nume, String prenume, String cnp, String sex, int varsta, int greutate) {
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSex(sex);
		setVarsta(varsta);
		setGreutate(greutate);
	}
	
	public Pacienti (String nume, String prenume, String cnp, String sex, int varsta, int greutate, String oras, String strada) {
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSex(sex);
		setVarsta(varsta);
		setGreutate(greutate);
		setOras(oras);
		setStrada(strada);
	}
	
	public Pacienti (String nume, String prenume, String cnp, String sex, int varsta, int greutate, String oras) {
		setNume(nume);
		setPrenume(prenume);
		setCnp(cnp);
		setSex(sex);
		setVarsta(varsta);
		setGreutate(greutate);
		setOras(oras);
	}
	
	public Pacienti() { }

	public Pacienti(int int1, String string, String string2) {
		setId(int1);
		setNume(string);
		setPrenume(string2);
	}

	public Pacienti(int int1, String string, String string2, String string3) {
		setId(int1);
		setNume(string);
		setPrenume(string2);
		setOras(string3);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		if(sex.charAt(0) == 'M' || sex.charAt(0) == 'F')
			this.sex = sex;
	}

	public int getId() {
		return id;
	
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getVarsta() {
		return varsta;
	}
	
	public void setVarsta(int varsta) {
		if(varsta > 0)
			this.varsta = varsta;
	}
	
	public int getGreutate() {
		return greutate;
	}
	
	public void setGreutate(int greutate) {
		if(greutate > 0)
			this.greutate = greutate;
	}
	
	public int getNumar() {
		return numar;
	}
	
	public void setNumar(int numar) {
		if(numar > 0)
			this.numar = numar;
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
	
	public String getOras() {
		return oras;
	}
	
	public void setOras(String oras) {
		this.oras = oras;
	}
	
	public String getStrada() {
		return strada;
	}
	
	public void setStrada(String strada) {
		this.strada = strada;
	}

}
