package webBoltOns.client.clientUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.border.LineBorder;

public class RdBorder extends LineBorder  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3510189069791342821L;

	public RdBorder() {
		super(Color.BLACK, 5, false);	
	}

	
	public RdBorder(Color c) {
		super(c, 5, false);	
	}
	
	public RdBorder(Color c, int s) {
		super(c, s, false);	
	}	
	
	public boolean isBorderOpaque() { return true; }
			
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Color oldColor = g.getColor(); 
		g.setColor(lineColor);
		g.drawRoundRect(x, y, width-thickness/2, height-thickness/2, thickness, thickness);
		g.setColor(oldColor);
	}
		   		   	
}

