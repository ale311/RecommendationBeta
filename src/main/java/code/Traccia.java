package code;

public class Traccia {
	private String artista;
	private String titolo;
	public Traccia(String artist, String title) {
		// TODO Auto-generated constructor stub
		artista = artist;
		titolo = title;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	
	public String toString(){
		return this.artista+ " - "+this.titolo;
	}
	
	public boolean equals(Object t){
		if (t.toString().equals(this.toString())){
			return true;
		}
		return false;
	}
	public int hashCode(){
		return(artista.hashCode()+titolo.hashCode());
	}
}
