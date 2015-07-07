package code;

public class Coppia {
	private String tag;
    private float valore;
   
    public Coppia(String tag, float valore){
        this.tag = tag;
        this.valore = valore;
    }
    public String getTag(){
    	return tag;
    }
    public float getValore(){
    	return valore;
    }
    public void setTag(String t){
    	tag = t;
    }
    public void setValore(float v){
    	valore = v;
    }
    public java.lang.String toString(){
        return "("+ tag + "," + valore + ")";
    }
}