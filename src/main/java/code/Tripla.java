package code;

public class Tripla {
	private String primo;
    private String secondo;
    private int terzo;

    public Tripla(String artista, String titolo, int count){
        this.primo = artista;
        this.secondo = titolo;
        this.terzo = count;
    }

    public String getArtista(){
    	return primo;
    }
    public String getTitolo(){
    	return secondo;
    }
    public int getCount(){
    	return terzo;
    }
    public void setArtista(String a){
    	primo = a;
    }
    public void setTitolo(String t){
    	secondo = t;
    }
    public void setCount(int n){
    	terzo = n;
    }
    public java.lang.String toString(){
        return "("+ primo + "," + secondo + ")";
    }
}

