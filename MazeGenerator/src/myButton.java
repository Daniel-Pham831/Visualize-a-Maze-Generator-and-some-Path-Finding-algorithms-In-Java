import java.awt.Color;

import javax.swing.*;

public class myButton extends JButton{
	myButton(String name,int x,int y,int width,int height,Color color){
		this.setText(name);
		this.setBounds(x, y, width, height);
		this.setLocation(x, y);
		this.setBackground(color);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		this.setFocusable(false);
	}
}
