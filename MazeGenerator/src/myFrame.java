import javax.swing.*;

public class myFrame extends JFrame{
	public myPanel panel;

	public final int windowWidthSize = 1000;
	public final int windowHeightSize = 600;
	myFrame(){
		
		this.setTitle("Maze Generator and Pathfinding Algorithms");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(windowWidthSize, windowHeightSize);
		this.setResizable(false);
		panel = new myPanel(windowWidthSize,windowHeightSize);
		this.add(panel);
		this.pack();
		this.setLayout(null);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		

		
		
		
	}

}