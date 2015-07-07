package code;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import code.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.naming.ldap.UnsolicitedNotificationListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import de.umass.lastfm.Library;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Period;
import de.umass.lastfm.Tag;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
import au.com.bytecode.opencsv.CSVReader;

public class PrincipaleParteB {
	public static void main(String[] args) throws IOException {
		
		FileOutputStream out;
		PrintStream ps;
		out = new FileOutputStream("myfile.txt");
		ps = new PrintStream(out);
		
		
		BufferedReader reader = new BufferedReader(new FileReader("datiutente.txt"));
		
		String line = reader.readLine();
		String username = line;
		System.out.println(username);
		line = reader.readLine();
		int numSongInt = Integer.parseInt(line);
		System.out.println(numSongInt);
		line = reader.readLine();
		String periodString = line;
		System.out.println(periodString);
		line = reader.readLine();
		int numeroConsigliVisualizzati = Integer.parseInt(line);
		System.out.println(numeroConsigliVisualizzati);
		String codice = "95f57bc8e14bd2eee7f1df8595291493";
		Period periodMacro = AusiliariParte1.periodoSelezionato(periodString);
		
		//memorizzo la MAPPA di TAG e PUNTEGGI relativi ai TAG
		HashMap<String, Float> associazioneTAGePUNTEGGIOdaCSV = AusiliariParte2.ottieniTAGePunteggioDaCSV();
		
		//memorizzo il TAG che ha il maggior PUNTEGGIO dopo l'elaborazione in GEPHI
		String tagVincente = associazioneTAGePUNTEGGIOdaCSV.keySet().iterator().next();

		//questo insieme contiene le tracce già ascoltate dall'utente username
		HashSet<Traccia> canzoniAscoltate = AusiliariParte2.ottieniTracceAscoltate(username);
		
		//questo oggetto contiene le TRACK suggerite per l'UTENTE (SONO TRACK!!). è una mappa con il PUNTEGGIO associato
		HashMap<Track, Float> trackSuggerite = AusiliariParte2.ottieniTRACKsuggerite (associazioneTAGePUNTEGGIOdaCSV);
		
		//stabilisco l'ordine in base ai punteggi ma sempre delle TRACK!! è una mappa con TRACK e PUNTEGGI
		trackSuggerite = (HashMap<Track, Float>) Ausiliari3.sortByValues(trackSuggerite);
		
		//è la MAPPA che contiene le INFO da VISUALIZZARE come RISULTATO _ METODO 3
		HashMap<Traccia, Float> tracceNUOVE_PUNTEGGIO = AusiliariParte2.ottieniSuggerimenti_PUNTEGGIO(username, trackSuggerite,canzoniAscoltate);
		
		//è la MAPPA che contiene le INFO da VISUALIZZARE come RISULTATO _ METODO 2
		HashMap<Traccia, Integer> tracceNUOVE_OCCORRENZE = AusiliariParte2.ottieniSuggerimenti_OCCORRENZE(username, trackSuggerite,canzoniAscoltate);
		
		//è la mappa che memorizza tutte le tracce consigliate per SINGOLO TAG e come valore il TAG Corrispondente
		HashMap<Traccia, String> tracceNUOVE_SINGOLO_TAG = AusiliariParte2.ottieniSuggerimentiDaSingoloTAG(username, tagVincente, canzoniAscoltate);
				
		//TODO ristabilire codice dell'interfaccia, funziona la parte sul secondo metodo (occorrenze non è stato modificato)
		
		//è l'arraylist che devo fornire alla GUI per la visualizzazione del metodo1
		ArrayList<TriplaSingoloTAG> listaPerVisualizzareMetodo1 = (ArrayList<TriplaSingoloTAG>) AusiliariParte2.ottieniArrayListConsigliMetodo1(tracceNUOVE_SINGOLO_TAG, numeroConsigliVisualizzati);
		
		//è l'arraylist che devo fornire alla GUI per la visualizzazione del metodo2
		ArrayList<TriplaOccorrenze> listaPerVisualizzareMetodo2 = (ArrayList<TriplaOccorrenze>) AusiliariParte2.ottieniArrayListConsigliMetodo2(tracceNUOVE_OCCORRENZE, numeroConsigliVisualizzati);
		
		//è l'arraylist che devo fornire alla GUI per la visualizzazione del metodo3
		ArrayList<TriplaPunteggio> listaPerVisualizzareMetodo3 = (ArrayList<TriplaPunteggio>) AusiliariParte2.ottieniArrayListConsigliMetodo3(tracceNUOVE_PUNTEGGIO, numeroConsigliVisualizzati);
		
		FinestraFinale finestraFinale = new FinestraFinale();
		finestraFinale.main(listaPerVisualizzareMetodo1, listaPerVisualizzareMetodo2, listaPerVisualizzareMetodo3);
		
	}
}
