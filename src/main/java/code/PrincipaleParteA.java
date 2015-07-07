package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Library;
import de.umass.lastfm.PaginatedResult;
import de.umass.lastfm.Period;
import de.umass.lastfm.Session;
import de.umass.lastfm.Tag;
import de.umass.lastfm.Track;
import de.umass.lastfm.User;
public class PrincipaleParteA {
	@SuppressWarnings("unchecked")
	public static void main() throws IOException {
		
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
		int numConsigliInt = Integer.parseInt(line);
		String codice = "95f57bc8e14bd2eee7f1df8595291493";
		Period periodMacro = AusiliariParte1.periodoSelezionato(periodString);
		
		HashMap<Tripla, HashSet<String>> mappaCount = new HashMap<Tripla, HashSet<String>>();
		Collection<Track> risultatiTop = User.getTopTracks(username, periodMacro, codice);
		mappaCount=(HashMap<Tripla, HashSet<String>>) AusiliariParte1.costruisciMappa(risultatiTop, numSongInt);
		AusiliariParte1.costruisciGDF(mappaCount);
	}
}

