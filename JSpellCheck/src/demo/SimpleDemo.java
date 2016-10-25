package demo;

import java.awt.event.*;
import javax.swing.*;
import com.jspell.domain.*;
import com.jspell.gui.*;

public class SimpleDemo extends JFrame {

	JSpellParser parser;
	JSpellChecker dialog;
	JSpellDictionaryManager manager;
	JButton check = new JButton("Spell Check");
	JTextArea text = new JTextArea("Type some missspelled text here!");

	public SimpleDemo() {
		getContentPane().add("North", check);
		getContentPane().add("Center", text);
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				{
					manager = JSpellDictionaryManager.getJSpellDictionaryManager();
					manager.setDictionaryDirectory("./lexicons/");
					parser = new JSpellParser(manager.getJSpellDictionaryLocal("English (US)"));
					dialog = new JSpellChecker();
					dialog.setParser(parser);
					dialog.addComponent(text);
					dialog.check();
				}
			}
		});
		setSize(400, 400);
		setVisible(true);
	}

	public static void main(String[] args) {
		new SimpleDemo();
	}
}