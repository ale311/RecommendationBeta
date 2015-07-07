package code;


public class TriplaSingoloTAG {
	private String artista;
	private String titolo;
	
	public TriplaSingoloTAG(String a, String t){
		artista = a;
		titolo = t;
	}
	public String getArtista(){
		return artista;
	}
	public String getTitolo(){
		return titolo;
	}
	
	public void setTitolo(String t){
		titolo=t;
	}
	public void setArtista(String a){
		artista = a;
	}
	
}
