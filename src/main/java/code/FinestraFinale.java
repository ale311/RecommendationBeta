package code;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class FinestraFinale{
	private static Collection consigliMetodo1;
	private static Collection consigliMetodo2;
	private static Collection consigliMetodo3;
	private Shell shell;

	
	public void run() {
		Display display = new Display();
		shell = new Shell(display);
		shell.setSize(506, 116);
		shell.setText("Risultati");
		createContents(shell);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	private void createContents(Composite composite) {
		shell.setLayout(new GridLayout(4, false));
		new Label(shell, SWT.NONE);
		
		Label lblMetodo = new Label(shell, SWT.NONE);
		lblMetodo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblMetodo.setText("metodo1");
		
		Label lblMetodo_1 = new Label(shell, SWT.NONE);
		lblMetodo_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblMetodo_1.setText("metodo2");
		
		Label lblMetodo_2 = new Label(shell, SWT.NONE);
		lblMetodo_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblMetodo_2.setText("metodo3");
		
		new Label(shell, SWT.NONE);
		
		Label lblConsigliDaSingolo = new Label(shell, SWT.NONE);
		lblConsigliDaSingolo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblConsigliDaSingolo.setText("Consigli da singolo TAG");
		
		Label lblConsigliConOccorrenze = new Label(shell, SWT.NONE);
		lblConsigliConOccorrenze.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblConsigliConOccorrenze.setText("Consigli con Occorrenze");
		
		Label lblConsigliConPunteggio = new Label(shell, SWT.NONE);
		lblConsigliConPunteggio.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblConsigliConPunteggio.setText("consigli con Punteggio");
		new Label(shell, SWT.NONE);
		final Table tableMetodo1 = new Table(composite, SWT.BORDER);
		tableMetodo1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		final Table tableMetodo2 = new Table(composite, SWT.BORDER);
		tableMetodo2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		final Table tableMetodo3 = new Table(composite, SWT.BORDER);
		tableMetodo3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		//imposto il contenuto della tabella1
		tableMetodo1.setHeaderVisible(true);
		tableMetodo1.setLinesVisible(true);
		TableColumn[] columnMetodo1 = new TableColumn[2];
		columnMetodo1[0] = new TableColumn(tableMetodo1, SWT.NONE);
		columnMetodo1[0].setText("Artista");

		columnMetodo1[1] = new TableColumn(tableMetodo1, SWT.NONE);
		columnMetodo1[1].setText("Titolo");
		//fine tabella 1
		
		//imposto il contenuto della tabella 2
		
		tableMetodo2.setHeaderVisible(true);
		tableMetodo2.setLinesVisible(true);
		TableColumn[] columnMetodo2 = new TableColumn[3];
		columnMetodo2[0] = new TableColumn(tableMetodo2, SWT.NONE);
		columnMetodo2[0].setText("Artista");

		columnMetodo2[1] = new TableColumn(tableMetodo2, SWT.NONE);
		columnMetodo2[1].setText("Titolo");
		
		columnMetodo2[2] = new TableColumn(tableMetodo2, SWT.NONE);
		columnMetodo2[2].setText("Occorrenze");
		
		//FINE tabella 2
		
		//imposto il contenuto della tabella 3
		
		
		tableMetodo3.setHeaderVisible(true);
		tableMetodo3.setLinesVisible(true);
		TableColumn[] columnMetodo3 = new TableColumn[3];
		columnMetodo3[0] = new TableColumn(tableMetodo3, SWT.NONE);
		columnMetodo3[0].setText("Artista");
		
		columnMetodo3[1] = new TableColumn(tableMetodo3, SWT.NONE);
		columnMetodo3[1].setText("Titolo");
		
		columnMetodo3[2] = new TableColumn(tableMetodo3, SWT.NONE);
		columnMetodo3[2].setText("Punteggio");
		
		//fine tabella 3
		
		
		//invio comando per riempire la tabella1
		
		fillTable_1(tableMetodo1);
		for (int i = 0, n = columnMetodo1.length; i < n; i++) {
			columnMetodo1[i].pack();
		}
		
		
		//invio comando per riempire la tabella2
		fillTable_2(tableMetodo2);
		for(int i = 0, n = columnMetodo2.length; i < n; i++){
			columnMetodo2[i].pack();
		}
		
		
		//invio comando per riempire la tabella3
		
		fillTable_3(tableMetodo3);
		for(int i = 0, n = columnMetodo3.length; i < n; i++){
			columnMetodo3[i].pack();
		}
		
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(java.util.List consigliMetodo12, java.util.List consigliMetodo22, java.util.List consigliMetodo32) {
		consigliMetodo1 = consigliMetodo12;
		consigliMetodo2 = consigliMetodo22;
		consigliMetodo3 = consigliMetodo32;
		new FinestraFinale().run();
	}
	
	//metodo fillTable che riempie la tabella 1
	private void fillTable_1(Table table1) {
		table1.setRedraw(false);

		for(Iterator iterator = consigliMetodo1.iterator();iterator.hasNext();){
			TriplaSingoloTAG tripla = (TriplaSingoloTAG) iterator.next();
			TableItem item = new TableItem(table1, SWT.NONE);
			int c = 0;
			item.setText(c++, tripla.getArtista());
			item.setText(c++, tripla.getTitolo());
		}
		table1.setRedraw(true);
	}
	
	//metodo filltable che riempie la tabella 1
	private void fillTable_2(Table table2) {
		table2.setRedraw(false);

		for(Iterator iterator = consigliMetodo2.iterator();iterator.hasNext();){
			TriplaOccorrenze tripla = (TriplaOccorrenze) iterator.next();
			TableItem item = new TableItem(table2, SWT.NONE);
			int c = 0;
			item.setText(c++, tripla.getArtista());
			item.setText(c++, tripla.getTitolo());
			item.setText(c++, Integer.toString(tripla.getOccorrenze()));
		}
		table2.setRedraw(true);
	}
	
	private void fillTable_3(Table table3) {
		table3.setRedraw(false);

		for(Iterator iterator = consigliMetodo3.iterator();iterator.hasNext();){
			TriplaPunteggio tripla = (TriplaPunteggio) iterator.next();
			TableItem item = new TableItem(table3, SWT.NONE);
			int c = 0;
			
			item.setText(c++, tripla.getArtista());
			item.setText(c++, tripla.getTitolo());
			item.setText(c++, Float.toString(tripla.getPunteggio()));
		}
		table3.setRedraw(true);
	}
	
}
