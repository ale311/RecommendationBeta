package code;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.internal.dnd.SwtUtil;

public class FinestraAccesso_new {

	
	protected Shell shell;
	private Text txtUsername;

	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		try {
			FinestraAccesso_new window = new FinestraAccesso_new();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(340, 217);
		shell.setText("Benvenuto");
		shell.setLayout(new FormLayout());
		
		Composite composite = new Composite(shell, SWT.NONE);
		FormData fd_composite = new FormData();
		fd_composite.bottom = new FormAttachment(100, -10);
		fd_composite.top = new FormAttachment(0, 10);
		fd_composite.right = new FormAttachment(100, -10);
		fd_composite.left = new FormAttachment(0, 10);
		composite.setLayoutData(fd_composite);
		composite.setLayout(new GridLayout(2, false));
		
		Label lblUsername = new Label(composite, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblUsername.setText("Username");
		
		txtUsername = new Text(composite, SWT.BORDER);
		txtUsername.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		final String username = txtUsername.getText();
		System.out.println(username);
		
		Label lblPeriodo = new Label(composite, SWT.NONE);
		lblPeriodo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblPeriodo.setText("Periodo");
		
		final CCombo combo_1 = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		combo_1.setVisibleItemCount(6);
		combo_1.setItems(new String[] {"1 settimana", "1 mese", "3 mesi", "6 mesi", "1 anno", "Overall"});
		
		Label lblNumeroCanzoni = new Label(composite, SWT.NONE);
		lblNumeroCanzoni.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNumeroCanzoni.setText("Numero canzoni da analizzare");
		
		final CCombo combo = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		combo.setItems(new String[] {"25", "50"});
		combo.setVisibleItemCount(2);
		combo.setToolTipText("Numero canzoni da analizzare");
		
		Label lblNumeroCanzoniDa = new Label(composite, SWT.NONE);
		lblNumeroCanzoniDa.setText("Numero canzoni da consigliare");
		
		final CCombo combo_2 = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		combo_2.setVisibleItemCount(3);
		combo_2.setToolTipText("Numero canzoni da consigliare");
		combo_2.setItems(new String[] {"5", "10", "20"});
		
		Button btnAvviaRicerca = new Button(composite, SWT.NONE);
		btnAvviaRicerca.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				final String username = txtUsername.getText();
				final String numSong = combo.getText();
				final String period = combo_1.getText();
				final String numConsigli = combo_2.getText();
				if(username.isEmpty() || numSong.isEmpty() || period.isEmpty() || numConsigli.isEmpty()){
					System.out.println("si prega di compilare tutti i campi");
				}
				if(!username.isEmpty()  && !numSong.isEmpty() && !period.isEmpty() && !numConsigli.isEmpty()){
					System.out.println("tutto ok");
					
					FileOutputStream out;
					PrintStream ps;
					try {
						out = new FileOutputStream("datiutente.txt");
						ps = new PrintStream(out);
						ps.println(username);
						ps.println(numSong);
						ps.println(period);
						ps.println(numConsigli);
						ps.close();
					} catch (Exception e1) {
						System.err.println("Errore nella scrittura del file");
					}
					try {
						PrincipaleParteA.main();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		    }
			
		});
		GridData gd_btnAvviaRicerca = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_btnAvviaRicerca.heightHint = 31;
		btnAvviaRicerca.setLayoutData(gd_btnAvviaRicerca);
		btnAvviaRicerca.setText("Avvia Ricerca");
	}
}