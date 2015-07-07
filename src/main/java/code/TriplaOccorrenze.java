package code;


public class TriplaOccorrenze {
	private String artista;
	private String titolo;
	private int occorrenze;
	
	public TriplaOccorrenze(String a, String t, int o){
		artista = a;
		titolo = t;
		occorrenze = o;
	}
	public String getArtista(){
		return artista;
	}
	public String getTitolo(){
		return titolo;
	}
	public int getOccorrenze(){
		return occorrenze;
	}
	public void setTitolo(String t){
		titolo=t;
	}
	public void setArtista(String a){
		artista = a;
	}
	public void setPunteggio(int o){
		occorrenze = o;
	}
}
