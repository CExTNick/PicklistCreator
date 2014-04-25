import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


public class PicklistRenderer extends JLabel implements ListCellRenderer<Picklist>{

	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Picklist> list, Picklist picklist, int index,
	        boolean isSelected, boolean cellHasFocus) {
		
		setText(picklist.formNamePlural + "~" + picklist.formName);
		
		return this;
	}


}
