package code;


public class TriplaPunteggio {
	private String artista;
	private String titolo;
	private float punteggio;
	
	public TriplaPunteggio(String a, String t, float p){
		artista = a;
		titolo = t;
		punteggio = p;
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
	public float getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(float punteggio) {
		this.punteggio = punteggio;
	}
	
	
}
