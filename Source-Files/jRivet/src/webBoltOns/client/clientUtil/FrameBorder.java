package webBoltOns.client.clientUtil;

import java.awt.Color;

import javax.swing.border.TitledBorder;

public class FrameBorder extends TitledBorder {
	public FrameBorder() {
	super(new RdBorder(Color.BLACK), "^", TitledBorder.LEFT, TitledBorder.TOP);
	setTitleColor(Color.BLACK);
	}

	private static final long serialVersionUID = 1L;

}
