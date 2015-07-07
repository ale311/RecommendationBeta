package code;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import de.umass.lastfm.Library;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Tag;
import de.umass.lastfm.Track;
import au.com.bytecode.opencsv.CSVReader;

public class AusiliariParte2 {
	static String codice = "95f57bc8e14bd2eee7f1df8595291493";
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////// METODI UTILI PER LA LETTURA DA FILE ////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////

	//metodo che legge il csv e cre una mappa di TAG e PUNTEGGI corrispondenti
	public static HashMap<String, Float> ottieniTAGePunteggioDaCSV() throws IOException{
		HashMap<String, Float> unsortedMap = new HashMap<>();
		HashMap<String, String> temp = new HashMap<>();
		HashMap<String, Float> result = new HashMap<>();
		CSVReader reader = new CSVReader(new FileReader("Output.csv"));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
		    // nextLine[] is an array of values from the line
			if(nextLine[2].equals("tag")){
		    	unsortedMap.put(nextLine[0], Float.parseFloat(nextLine[3]));
		    }
		}
		for(String s : unsortedMap.keySet()){
			result.put(s, unsortedMap.get(s));
		}
		result = (HashMap<String, Float>) Solution.sortByValues(result);
		return result;
	}
	
	
	//codice per restituire le tracce ascoltate
	public static HashSet<Traccia> ottieniTracceAscoltate (String username){
		HashSet<Traccia> result = new HashSet<>();
		PaginatedResult<Track> canzoniAscoltate = Library.getTracks(username,1, 500,codice);
		for(Track t : canzoniAscoltate){
			Traccia daInserire = new Traccia(t.getArtist(), t.getName());
			result.add(daInserire);
		}
		
		for(Traccia t : result){
			System.out.println(t);
		}
		return result;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// METODI UTILI PER CREARE MAPPE //////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////



	//metodo che restituisce una MAPPA con le TRACK suggerite per l'UTENTE con i PUNTEGGI RELATIVI
	public static HashMap<Track, Float> ottieniTRACKsuggerite(
			HashMap<String, Float> associazioneTAGePUNTEGGIOdaCSV) {
		// TODO Auto-generated method stub
		HashMap<Track, Float> trackSuggerite = new HashMap<>();
		for(String tagCorrente : associazioneTAGePUNTEGGIOdaCSV.keySet()){
			Collection<Track> canzoniSuggeriteDaSingoloTag = Tag.getTopTracks(tagCorrente, codice);
			for(Track trackCorrente : canzoniSuggeriteDaSingoloTag){
				trackSuggerite.put(trackCorrente, associazioneTAGePUNTEGGIOdaCSV.get(tagCorrente));
			}
		}
		return trackSuggerite;
	}

	//metodo che restituisce una MAPPA di TRACCE con PUNTEGGIO = SOMMA DEI PUNTEGGI DEL TAG CHE HANNO CONSIGLIATO LA TRACCIA
	//è il metodo risolutivo del metodo di consigli numero 3
	public static HashMap<Traccia, Float> ottieniSuggerimenti_PUNTEGGIO(
			String username, HashMap<Track, Float> trackSuggerite,
			HashSet<Traccia> canzoniAscoltate) throws FileNotFoundException {
		
		HashMap<Traccia, Float> tracceSuggerite = new HashMap<>();
		HashMap<Traccia, Float> risultati = new HashMap<>();
		for(Track trackCorrente : trackSuggerite.keySet()){
			float punteggioCorrente = trackSuggerite.get(trackCorrente);
			Traccia tracciaCorrente = new Traccia(trackCorrente.getArtist(),trackCorrente.getName());
			if(!canzoniAscoltate.contains(tracciaCorrente)){
				if(risultati.containsKey(tracciaCorrente)){
					float vecchioPunteggio = risultati.get(tracciaCorrente);
					float punteggioDaInserire = vecchioPunteggio+punteggioCorrente;
					risultati.put(tracciaCorrente, punteggioDaInserire);
				}
				else{
					risultati.put(tracciaCorrente, punteggioCorrente);
				}
			}
		}
		risultati = (HashMap<Traccia, Float>) Ausiliari3.sortByValues(risultati);
		return risultati;
	}

	//metodo che restituisce una MAPPA di TRACCE con OCCORRENZE = SOMMA DELLE OCCORRENZE DELLA CANZONE CONSIGLIATA DAL TAG I-MO
	//è il metodo risolutivo del metodo di consigli numero 2

	public static HashMap<Traccia, Integer> ottieniSuggerimenti_OCCORRENZE(
			String username, HashMap<Track, Float> trackSuggerite,
			HashSet<Traccia> canzoniAscoltate) throws FileNotFoundException {
		

		FileOutputStream out;
		PrintStream ps;
		out = new FileOutputStream("myfile.txt");
		ps = new PrintStream(out);
		
		HashMap<Traccia, Float> tracceSuggerite = new HashMap<>();
		HashMap<Traccia, Integer> risultati = new HashMap<>();
		for(Track trackCorrente : trackSuggerite.keySet()){
			int occorrenzaCorrente = 1;
			Traccia tracciaCorrente = new Traccia(trackCorrente.getArtist(),trackCorrente.getName());
			if(!canzoniAscoltate.contains(tracciaCorrente)){
				if(risultati.containsKey(tracciaCorrente)){
					int vecchiaOccorrenza = risultati.get(tracciaCorrente);
					int occorrenzaDaInserire = vecchiaOccorrenza+occorrenzaCorrente;
					risultati.put(tracciaCorrente, occorrenzaDaInserire);
				}
				else{
					risultati.put(tracciaCorrente, occorrenzaCorrente);
				}
			}
		}
		risultati = (HashMap<Traccia, Integer>) Ausiliari3.sortByValues(risultati);
		for(Traccia t : risultati.keySet()){
			ps.println(t.getArtista()+","+t.getTitolo()+","+risultati.get(t));
		}

		return risultati;
	}

	//metodo che torna una MAPPA con i consigli provenienti da un SINGOLO TAG (Metodo 1) e associa il tag vincente
	//è il metodo risolutivo del metodo 1

	public static HashMap<Traccia, String> ottieniSuggerimentiDaSingoloTAG(
			String username, String tagVincente,
			HashSet<Traccia> canzoniAscoltate) throws FileNotFoundException {
		
		/*
		FileOutputStream out;
		PrintStream ps;
		out = new FileOutputStream("myfile.txt");
		ps = new PrintStream(out);
		*/
		
		// TODO Auto-generated method stub
		HashMap<Traccia, String> risultati = new HashMap<>();
		Collection<Track> suggerimentiDaTagVincente = Tag.getTopTracks(tagVincente, codice);
		for(Track t : suggerimentiDaTagVincente){
			Traccia tracciaCorrente = new Traccia(t.getArtist(),t.getName());
			if(!canzoniAscoltate.contains(tracciaCorrente)){
				risultati.put(tracciaCorrente, tagVincente);
			}
		}
		/*
		for(Traccia t : risultati.keySet()){
			ps.println(t.getArtista()+","+t.getTitolo()+","+risultati.get(t));
		}
		*/
		return risultati;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// METODI UTILI PER LA GUI ////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	//metodo che a partire da una MAPPA restituisce una LISTA 
	//serve per la visualizzazione _ metodo1

	public static List ottieniArrayListConsigliMetodo1(
			HashMap<Traccia, String> mappaConsigliDaSingoloTag,
			int numeroConsigliVisualizzati) {
		// TODO Auto-generated method stub
		ArrayList<TriplaSingoloTAG> result = new ArrayList<>();
		int i =0;
		for(Traccia t : mappaConsigliDaSingoloTag.keySet()){
			if(i == numeroConsigliVisualizzati){
				break;
			}
			result.add(new TriplaSingoloTAG(t.getArtista(), t.getTitolo()));
			i++;
		}
		return result;
	}
	
	
	//metodo che a partire da una MAPPA restituisce una LISTA 
	//serve per la visualizzazione _ metodo2

	public static List ottieniArrayListConsigliMetodo2(
			HashMap<Traccia, Integer> mappaConsigliOccorrenze,
			int numeroConsigliVisualizzati) {
		// TODO Auto-generated method stub
		ArrayList<TriplaOccorrenze> result = new ArrayList<>();
		int i =0;
		for(Traccia t : mappaConsigliOccorrenze.keySet()){
			if(i++ == numeroConsigliVisualizzati){
				break;
			}
			result.add(new TriplaOccorrenze(t.getArtista(), t.getTitolo(),mappaConsigliOccorrenze.get(t)));
			
		}
		
		return result;
	}

	
	//metodo che a partire da una MAPPA restituisce una LISTA 
	//serve per la visualizzazione _ metodo3

	public static List ottieniArrayListConsigliMetodo3(
			HashMap<Traccia, Float> mappaConsigliPunteggioTAG,
			int numeroConsigliVisualizzati) {
		// TODO Auto-generated method stub
		ArrayList<TriplaPunteggio> result = new ArrayList<>();
		int i =0;
		for(Traccia t : mappaConsigliPunteggioTAG.keySet()){
			if(i == numeroConsigliVisualizzati){
				break;
			}
			result.add(new TriplaPunteggio(t.getArtista(), t.getTitolo(),mappaConsigliPunteggioTAG.get(t)));
			i++;
		}
		return result;
	}
	
	
	
}
