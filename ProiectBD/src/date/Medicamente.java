package date;

public class Medicamente {
	private int id;
	private String denumire, substantaactiva;
	
	public Medicamente(int id, String denumire, String substantaactiva) {
		setId(id);
		setDenumire(denumire);
		setSubstantaActiva(substantaactiva);
	}
	
	public Medicamente(int int1, String string) {
		setId(int1);
		setDenumire(string);
	}

	public int getId() {
		
		return id;
	}
	
	public void setId(int iDMedicamente) {
		
		id = iDMedicamente;
	}
	
	public String getDenumire() {
		
		return denumire;
	}
	
	public void setDenumire(String denumire) {
		
		this.denumire = denumire;
	}
	
	public String getSubstantaActiva() {
		
		return substantaactiva;
	}
	
	public void setSubstantaActiva(String substantaActiva) {
		
		substantaactiva = substantaActiva;
	}

}
