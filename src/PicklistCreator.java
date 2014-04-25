import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class PicklistCreator extends JFrame implements DocumentListener, MouseListener, ActionListener{
	
	private static final long serialVersionUID = -4174040857613584619L;

	private JPanel panel;
	
	private JTextField fieldProjectFilePath;
	
	private JTextField fieldFormName;
	private JTextField fieldFormNamePlural;
	private JTextField fieldFormNameSpaces;
	private JTextField fieldFormNamePluralSpaces;
	
	private JList<Picklist> picklists;
	private DefaultListModel<Picklist> listModel;
	
	private JButton buttonCreate;
	private JButton buttonClear;
	private JButton buttonAdd;
	
	public PicklistCreator()
	{
		super("Picklist Creator");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new JPanel();
		setupKeybindings();
		
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(8,8,8,8);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty  = 0;

		c.gridy = 0;
		//file path 
		JLabel labelProjectFilePath = new JLabel("File Path:");
		c.gridx = 0;
		c.weightx = 0;
		panel.add(labelProjectFilePath, c);
		
		fieldProjectFilePath = new JTextField();
		fieldProjectFilePath.addMouseListener(this);
		c.gridx = 1;
		c.weightx = 1;
		panel.add(fieldProjectFilePath, c);
		
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		JSeparator separator= new JSeparator();
		panel.add(separator, c);
		
		c.gridwidth = 1;
		c.weightx = 0;
		
				
		c.gridy = 2;
		//form name with spaces
		JLabel labelFormNameSpaces = new JLabel("Form Name:");
		c.gridx = 0;
		c.weightx = 0;
		panel.add(labelFormNameSpaces, c);
		
		fieldFormNameSpaces = new JTextField();
		fieldFormNameSpaces.getDocument().addDocumentListener(this);
		c.gridx = 1;
		c.weightx = 1;
		panel.add(fieldFormNameSpaces, c);
		
		c.gridy = 3;
		//form name plural with spaces
		JLabel labelFormNamePluralSpaces = new JLabel("Form Plural Name:");
		c.gridx = 0;
		c.weightx = 0;
		panel.add(labelFormNamePluralSpaces, c);
		
		fieldFormNamePluralSpaces = new JTextField();
		fieldFormNamePluralSpaces.getDocument().addDocumentListener(this);
		c.gridx = 1;
		c.weightx = 1;
		panel.add(fieldFormNamePluralSpaces, c);
		
		c.gridy = 4;
		//form name 
		JLabel labelFormName = new JLabel("FormName:");
		c.gridx = 0;
		c.weightx = 0;
		panel.add(labelFormName, c);
		
		fieldFormName = new JTextField();
		c.gridx = 1;
		c.weightx = 1;
		panel.add(fieldFormName, c);
		
		c.gridy = 5;
		//form name plural
		JLabel labelFormNamePlural = new JLabel("FormNamePlural:");
		c.gridx = 0;
		c.weightx = 0;
		panel.add(labelFormNamePlural, c);
		
		fieldFormNamePlural = new JTextField();
		c.gridx = 1;
		c.weightx = 1;
		panel.add(fieldFormNamePlural, c);
		
		c.gridy = 6;
		c.gridwidth = 2;
		c.gridx = 0;
		buttonAdd = new JButton("Add");
		buttonAdd.addActionListener(this);
		panel.add(buttonAdd, c);
		
		c.gridy = 7;
		listModel = new DefaultListModel<Picklist>();
		picklists = new JList<Picklist>(listModel);
		picklists.setLayoutOrientation(JList.VERTICAL);
		
		c.gridx = 0;
		c.gridwidth = 2;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		JScrollPane scrollPicklists = new JScrollPane(picklists);
		panel.add(scrollPicklists, c);

		c.gridy = 8;
		c.gridx = 0;
		c.weighty = 0;
		c.weightx = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		buttonCreate = new JButton("Create");
		buttonCreate.addActionListener(this);
		
		buttonClear = new JButton("Clear");
		buttonClear.addActionListener(this);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0,2));
		buttonPanel.add(buttonCreate);
		buttonPanel.add(buttonClear);
		panel.add(buttonPanel, c);
		
		this.add(panel);
		this.pack();
		this.setSize(400, this.getHeight());
		setVisible(true);
		
	}
	private void setupKeybindings()
	{
		/**enter button pressed**/
		Action enterAction = new AbstractAction("Enter Pressed") {
			private static final long serialVersionUID = -5296291750560505194L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				addPicklist();
			}
		};
		// Create KeyStroke that will be used to invoke the action.
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

		// Register Action in component's ActionMap.
		panel.getActionMap().put("Enter Pressed", enterAction);

		// Now register KeyStroke used to fire the action.  I am registering this with the
		// InputMap used when the component's parent window has focus.
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "Enter Pressed");
		
		

		/**delete button pressed**/
		Action deleteAction = new AbstractAction("Delete Pressed") {
			private static final long serialVersionUID = 7986118972317153662L;

			@Override
			public void actionPerformed(ActionEvent evt) {
				int[] selectedIndices = picklists.getSelectedIndices();
				for(int i=selectedIndices.length-1;i>=0; i--)
				{
					listModel.remove(selectedIndices[i]);
				}
			}
		};
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		panel.getActionMap().put("Delete Pressed", deleteAction);
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "Delete Pressed");
	}
	public void addPicklist()
	{
		listModel.addElement(new Picklist(fieldFormName.getText(), fieldFormNamePlural.getText(), fieldFormNameSpaces.getText(), fieldFormNamePluralSpaces.getText()));
		fieldFormName.setText("");
		fieldFormNamePlural.setText("");
		fieldFormNameSpaces.setText("");
		fieldFormNamePluralSpaces.setText("");
		fieldFormNameSpaces.requestFocus();
		
	}
	@Override
	public void mouseClicked(MouseEvent evt) {
		if(evt.getSource() == this.fieldProjectFilePath)
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	fieldProjectFilePath.setText( chooser.getSelectedFile().getAbsolutePath());
		    }
		}
		
	}
	public void insertUpdate(DocumentEvent evt) {
		fieldsUpdated(evt);
	}
	public void removeUpdate(DocumentEvent evt) {
		fieldsUpdated(evt);
	}
	public void fieldsUpdated(DocumentEvent evt)
	{
		if(evt.getDocument() == this.fieldFormNameSpaces.getDocument())
		{

			String formNameSpaces = fieldFormNameSpaces.getText();
			String formNamePluralSpaces = formNameSpaces;
			if(formNamePluralSpaces.length()>0)
				formNamePluralSpaces = formNamePluralSpaces+ "s";
			String formName = formNameSpaces.replace(" ", "");
			String formNamePlural = formNamePluralSpaces.replace(" ", "");
			
			fieldFormNamePluralSpaces.setText(formNamePluralSpaces);
			fieldFormName.setText(formName);
			fieldFormNamePlural.setText(formNamePlural);
		}
		else if(evt.getDocument() == fieldFormNamePluralSpaces.getDocument())
		{
			String formNamePluralSpaces = fieldFormNamePluralSpaces.getText();
			String formNamePlural = formNamePluralSpaces.replace(" ", "");
			fieldFormNamePlural.setText(formNamePlural);
		}
	}
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == buttonCreate)
		{
			createPicklists();
		}
		else if(evt.getSource() == buttonClear)
		{
			listModel.removeAllElements();
		}
		else if(evt.getSource() == buttonAdd)
		{
			addPicklist();
		}
		
	}
	
	public void createPicklists()
	{
		String projectFilePath = fieldProjectFilePath.getText();
		for(int i=0;i<listModel.getSize();i++)
		{
			Picklist picklist = listModel.get(i);
			picklist.create(projectFilePath);
		}
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void changedUpdate(DocumentEvent arg0) {}
	public static void main(String args[])
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new PicklistCreator();
		
	}
}
