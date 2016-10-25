package demo;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import com.jspell.domain.*;
import com.jspell.gui.*;

/**
 * An example of using JSpell to enable spell checking in a standalone application.
 */
public class JSpellAppTestLocal extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JSpellDictionaryLocal jdLocal;
	JSpellParser parser;
	JSpellChecker dialog;
	JSpellDictionaryManager manager;
	JButton check;
	JComboBox languages;
	JTextArea text = new JTextArea(), text2 = new JTextArea();

	/**
	 * An example of using JSpell to enable spell checking in a standalone application.
	 * 
	 * @param directory
	 *           The directory containing the JSpell dictionary files.
	 */
	public JSpellAppTestLocal(String directory) {
		super("JSpell SWING Application Test (Local)");
		manager = JSpellDictionaryManager.getJSpellDictionaryManager();
		if (!manager.setDictionaryDirectory(directory)) {
			System.out.println("Directory: " + directory + " does not contain valid .jdx files. Please specify");
			System.out.println("a valid directory. Note: .jdx files must have read/write access.");
			System.exit(-1);
		}

		Vector langs = manager.getLanguages();
		languages = new JComboBox(langs);
		DemoStrings.setGUI(((Language) langs.elementAt(0)).getDescription(), text, text2);
		// DemoStrings.setGUI("English (US)", text, text2);
		Container contentPane = getContentPane();
		setSize(400, 300);
		check = new JButton("Spell Check");
		check.addActionListener(this);
		JScrollPane scrollPane = new JScrollPane(text);
		JScrollPane scrollPane2 = new JScrollPane(text2);
		contentPane.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new java.awt.BorderLayout());
		topPanel.add("Center", check);
		languages.addActionListener(this);
		topPanel.add("East", languages);
		contentPane.add("North", topPanel);
		JPanel holder = new JPanel();
		holder.setLayout(new GridLayout(2, 2));
		holder.add(scrollPane);
		holder.add(scrollPane2);
		contentPane.add("Center", holder);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Window w = e.getWindow();
				w.setVisible(false);
				w.dispose();
				System.exit(0);
			}
		});
		setVisible(true);
	}

	/**
	 * Event handler interface for the com.jspell.demo application.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == check) {
			jdLocal = manager.getJSpellDictionaryLocal(languages.getSelectedItem().toString());
			jdLocal.setForceUpperCase(false);
			jdLocal.setIgnoreUpper(false);
			jdLocal.setIgnoreIrregularCaps(false);
			jdLocal.setIgnoreFirstCaps(false);
			jdLocal.setIgnoreDoubleWords(false);
			jdLocal.setLearnWords(false);
			parser = new JSpellParser(jdLocal);
			dialog = new JSpellChecker(Util.getFrame(this));
			dialog.setLocale(new java.util.Locale(jdLocal.getDictionaryLanguage(), jdLocal.getDictionaryCountry()));
			dialog.setParser(parser);
			dialog.addComponent(text);
			dialog.addComponent(text2);
			dialog.check();
		} else if (e.getSource() == languages) {
			DemoStrings.setGUI(languages.getSelectedItem().toString(), text, text2);
		}
	}

	/**
	 * Start the com.jspell.demo application.
	 * 
	 * @param args
	 *           Parameters for the com.jspell.demo application.
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			new JSpellAppTestLocal("./lexicons/");
		} else {
			new JSpellAppTestLocal(args[0]);
		}
	}
}
