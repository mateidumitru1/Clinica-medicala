package date;

public class RapoarteMedicale {
	private int id, idPacient, idMedic, idTratament;
	private String obs, data;

	
	public RapoarteMedicale(int id, int idPacient, int idMedic, int idTratament, String date, String obs) {
		setId(id);
		setIdPacient(idPacient);
		setIdMedic(idMedic);
		setIdTratament(idTratament);
		setData(date);
		setObs(obs);
	}
	
	public int getIdPacient() {
		return idPacient;
	}
	
	public void setIdPacient(int idPacient) {
		this.idPacient = idPacient;
	}
	
	public int getIdMedic() {
		return idMedic;
	}
	
	public void setIdMedic(int idMedic) {
		this.idMedic = idMedic;
	}
	
	public int getIdTratament() {
		return idTratament;
	}
	
	public void setIdTratament(int idTratament) {
		this.idTratament = idTratament;
	}
	
	public String getObs() {
		return obs;
	}
	
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String date) {
		this.data = date;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
