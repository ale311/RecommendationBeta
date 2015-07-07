package code;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;
import de.umass.lastfm.Library;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Period;
import de.umass.lastfm.Tag;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;

public class AusiliariParte1 {
	
	private static String codice = "95f57bc8e14bd2eee7f1df8595291493";
	
	
	//metodo per ordinare una collezione in base al playcount
	
	public static void ordinaPerPlayCount (Collection<Track> topTracksDisordinate){
		LinkedList<Track>result = new LinkedList<>();
	}
	
	
	//metodo che restituisce a partire da un tag
	//una collezione di tracce consigliate in base a quel tag
	
	public static Collection<Track> consigliDaSingoloTag(String tag){
		Collection<Track> completeResult = Tag.getTopTracks(tag, codice);
		LinkedList<String> result = new LinkedList<>();
		for(Track track : completeResult){
			result.add(track.getName());
		}
		return completeResult;
	}
	
	
	//metodo che a partire da un tag
	//restituisce una lista di Stringhe che sono i nomi delle canzoni consigliate
	
	public static LinkedList<String> listaConsigliDaSingoloTag(String tag){
		LinkedList<String> result = new LinkedList<>();
		for(Track track : AusiliariParte1.consigliDaSingoloTag(tag)){
			result.add(track.getName());
		}
		return result;
	}

	
	//metodo per convertire in "macro" il periodo selezionato dalla window
	//TODO verificare la consistenza di "1 mese" che non funziona proprio in last.fm
	
	public static Period periodoSelezionato (String p){
		Period result = null;
		if(p.equals("1 settimana"))
			result = Period.WEEK;
		if(p.equals("1 mese"))
			result = Period.ONE_MONTH;
		if(p.equals("3 mesi"))
			result = Period.THREE_MONTHS;
		if(p.equals("3 mesi"))
			result = Period.THREE_MONTHS;
		if(p.equals("6 mesi"))
			result = Period.SIX_MONTHS;
		if(p.equals("1 anno"))
			result = Period.TWELVE_MONTHS;
		if(p.equals("Overall"))
			result = Period.OVERALL;
		return result;
	}
	
	public static Map<Tripla, HashSet<String>> costruisciMappa(Collection<Track> risultatiTop, int numSongInt) throws FileNotFoundException{
		

		FileOutputStream out;
		PrintStream ps;
		out = new FileOutputStream("myfile.txt");
		ps = new PrintStream(out);
		
		
		Map<Tripla, HashSet<String>> result = new HashMap<Tripla, HashSet<String>>();
		Iterator<Track> it = risultatiTop.iterator();
		int i = 0;
		while(it.hasNext() && i<numSongInt){
			Track currentTrack = it.next();
			String currentArtist = currentTrack.getArtist();
			String currentTitle = currentTrack.getName();
					//.replace(',',' ').toUpperCase();
			//String currentAlbum = currentTrack.getAlbum();
			//int currentPlayCount = 0;
			int currentPlayCount = currentTrack.getPlaycount();
			System.out.println(currentArtist+currentTitle+currentPlayCount);
			Tripla currentTripla = new Tripla(currentArtist,currentTitle,currentPlayCount);
			HashSet<String> currentTotalTags = new HashSet<String>();
			Iterator<Tag> itTag = Track.getTopTags(currentArtist,currentTitle,codice).iterator();
			
			//TODO metti la macro per il numero di tag da prendere

			while (itTag.hasNext() && currentTotalTags.size()<5){
				Tag currentTag = itTag.next();
				int currentTagCount = currentTag.getCount();
				String currentTagName = currentTag.getName().toLowerCase();
				currentTotalTags.add(currentTagName);
			}
			
			result.put(currentTripla,currentTotalTags);
			i++;
		}
		
		/*
		System.out.println(result.size());
		for(Entry<Tripla, HashSet<String>> currentEntry : result.entrySet()){
			System.out.println(currentEntry.getKey().getTitolo());
			System.out.println(currentEntry.getValue().toString());
		}
		*/
		return result;
	}
	
	
	
	//metodo che a partire dagli ascolti dell'utente
	//costruisce un file GDF che può essere letto da Gephi
	//IMPORTANTE: viene fatta qui la struttura del GDF
	
	public static void costruisciGDF (HashMap<Tripla, HashSet<String>> mappaCount) throws FileNotFoundException{
		PrintStream perGephi = new PrintStream(new FileOutputStream("perGephi.gdf"));
		perGephi.println("nodedef>name VARCHAR, label VARCHAR, tipo VARCHAR");
		for(Entry<Tripla, HashSet<String>> currentEntry : mappaCount.entrySet()){
			for(String s : mappaCount.get(currentEntry.getKey())){
				perGephi.println(currentEntry.getKey().getTitolo().replace(',',' ').toUpperCase()+","+currentEntry.getKey().getTitolo().replace(',',' ').toUpperCase()+","+"song");
			}
		}
		for(Entry<Tripla, HashSet<String>> currentEntry : mappaCount.entrySet()){
			for(String s : mappaCount.get(currentEntry.getKey())){
				perGephi.println(s+","+s+","+"tag");
			}
		}
		perGephi.println("edgedef>node1 VARCHAR"+","+"node2 VARCHAR");
		for(Entry<Tripla, HashSet<String>> currentEntry : mappaCount.entrySet()){
			for(String s : mappaCount.get(currentEntry.getKey())){
				perGephi.println(currentEntry.getKey().getTitolo().replace(',',' ').toUpperCase()+","+s);
			}
		}
	}
	
	
	//metodo che a partire dal file CSV in uscita da Gephi
	//costruisce una mappa con K=TAG
	//V=PageRank del tag
	//NOTA: la mappa è costituita da String,Float
	
	public static HashMap<String, Float> ottieniTagUtenteConRelativoPunteggioSorted () throws IOException{
		HashMap<String, String> unsortedMap = new HashMap<>();
		HashMap<String, String> temp = new HashMap<>();
		HashMap<String, Float> result = new HashMap<>();
		CSVReader reader = new CSVReader(new FileReader("Output.csv"));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			if(nextLine[2].equals("tag")){
		    	unsortedMap.put(nextLine[0], nextLine[3]);
		    }
		}
		temp = (HashMap<String, String>) Solution.sortByValues(unsortedMap);
		for(String s : temp.keySet()){
			result.put(s, Float.parseFloat(temp.get(s)));
		}
		result = (HashMap<String, Float>) Solution.sortByValues(result);
		System.out.println(result);
		return result;
	}
	
	//metodo che a partire da una mappa con i tag e il pagerank relativo
	//costruisce una mappa che ha come chiave lo stesso tag
	//e come valore una mappa che ha come chiave la canzone i-ma consigliata dal tag
	//e come valore il pagerank relativo al tag

	public static HashMap<String, HashMap<String, Float>> associaPunteggioACanzoni (Map<String, Float> mappaPunteggioTag){
		HashMap<String, HashMap<String, Float>> result = new HashMap<>();
		for(String currentTag : mappaPunteggioTag.keySet()){
			HashMap<String, Float> canzoniConsigliateDaSingoloTag = new HashMap<>();
			Collection<Track> canzoniConsPaginatedResult = Tag.getTopTracks(currentTag , codice);
			for(Track track : canzoniConsPaginatedResult){
				canzoniConsigliateDaSingoloTag.put(track.getName(), (mappaPunteggioTag.get(currentTag)));
			}
		}
		return result;
	}
	
	
	//metodo che a partire dal nome utente
	//restituisce un insieme di canzoni ascoltate in precedenza
	
	public static HashSet<String> ottieniCanzoniAscoltate (String utente){
		HashSet<String> result = new HashSet<>();
		PaginatedResult<Track> canzoniAscoltate = Library.getTracks(utente, 50, 50, codice);
		for(Track track : canzoniAscoltate){
			result.add(track.getName());
		}
		return result;
	}
}
