import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Cell extends Rectangle{
	public int row,r,col,c,cellSize;
	public boolean[] Walls = {true,true,true,true}; //top  right  bottom  left
	public ArrayList<Cell> next;
	public Cell parent;
	public boolean visited = false; //for mazeDrawer
	private int weight = 1;
	private int fontWeight;
	private int strokeSize;
	public boolean visitedPath = false; //for pathFinder
	
	
	Cell(int row,int col,int cellSize){
		this.row = row;
		this.col = col;
		r = row*cellSize;
		c = col*cellSize;
		this.cellSize = cellSize;
		next = new ArrayList<Cell>();
		parent = null;
		fontWeight = (int)(10+0.3*(cellSize-20)); //cái này để khi cellSize bự thì font bự theo
		strokeSize = (int)((5.0/9 * ((cellSize/10)-1) )+1); // cái này để khi cellsize bự thì stroke bự theo
	}
	
	public void drawCell(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor(new Color(0,0,0));
		g2.setStroke(new BasicStroke(strokeSize));
		
		if(Walls[0] == true)
			g2.drawLine(c, r, c+cellSize, r);  // top
		if(Walls[1] == true)
			g2.drawLine(c+cellSize, r, c+cellSize, r+cellSize); //right
		if(Walls[2] == true)
			g2.drawLine(c+cellSize, r+cellSize, c, r+cellSize); //bottom
		if(Walls[3] == true)
			g2.drawLine(c, r+cellSize, c, r); //left
		
		g2.setStroke(new BasicStroke(1));
	}

	public void drawWeight(Graphics g) {
		g.setFont(new Font("",Font.BOLD,fontWeight));
		g.drawString(""+weight, (int)(c+cellSize/2)-(int)(fontWeight/3), (int)(r+cellSize/2)+(int)(fontWeight/3));
		
	}

	public void drawBox(Graphics g,Color color) {
		Graphics2D g2 = (Graphics2D) g;
		if(this.visited == true) {
			g2.setColor(color);
			g2.fillRect(c, r, cellSize, cellSize);
		}
	}
	
	public void drawPath(Graphics g,Color color) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		if(parent!=null) {
			if(this.visitedPath == true) {
				g2.fillRect(c+(int)(cellSize/3), r+(int)(cellSize/3), (int)(cellSize/3), (int)(cellSize/3));
			}
			if(row - 1 == parent.row) { //top
				g2.fillRect(c+(int)(cellSize/3), r, (int)(cellSize/3), (int)(cellSize/3));
				g2.fillRect(parent.c+(int)(cellSize/3), parent.r+((int)(cellSize/3)*2), (int)(cellSize/3), (int)(cellSize/3));
			}
			
			if(col + 1 == parent.col) { //right
				g2.fillRect(c+((int)(cellSize/3)*2), r+(int)(cellSize/3), (int)(cellSize/3), (int)(cellSize/3));
				g2.fillRect(parent.c, parent.r+(int)(cellSize/3), (int)(cellSize/3), (int)(cellSize/3));
			}
			
			if(row + 1 == parent.row) { //bot
				g2.fillRect(c+(int)(cellSize/3), r+((int)(cellSize/3)*2), (int)(cellSize/3), (int)(cellSize/3));
				g2.fillRect(parent.c+(int)(cellSize/3), parent.r, (int)(cellSize/3), (int)(cellSize/3));
			}
			
			if(col - 1 == parent.col) { //left
				g2.fillRect(c, r+(int)(cellSize/3), (int)(cellSize/3), (int)(cellSize/3));
				g2.fillRect(parent.c+((int)(cellSize/3)*2), parent.r+(int)(cellSize/3), (int)(cellSize/3), (int)(cellSize/3));
			}
			
			
		}
	}
	
	
	public void resetCell() {
		visited = false;
		visitedPath = false;
		parent = null;
		next = new ArrayList<>();
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight() {
		this.weight = new Random().nextInt(9)+1;
	}
	
	
}
