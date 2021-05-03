import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class myPanel extends JPanel implements ActionListener,ChangeListener{
	
	private final int mazeSize = 600;
	private int cellSize = 40;
	private int slowestDelay = 110;
	private int delay = 10;
	private int windowW,windowH;
	private int running = -1;

	private int switchCaseVar = 0;
	
	private myMaze maze;
	private Timer tm;
	
	
	private JPanel smallPanel;
	private myButton startButton;
	private myButton resetButton;
	private myButton reMazeButton;
	
	private JLabel cellLabel;
	private JSlider cellSlider;
	
	private JLabel speedLabel;
	private JSlider speedSlider;
	
	private JCheckBox mazeCheckbox;
	
	private JCheckBox BFSCheckbox;
	
	private myButton BFSButton;
	private boolean flag = true;


	
	
	myPanel(int windowW,int windowH){
		this.windowW = windowW;
		this.windowH = windowH;
		
		tm = new Timer(delay,this);
		
		this.setPreferredSize(new Dimension(windowW,windowH));
		this.setBounds(0, 0, windowW, windowH);
		this.setBackground(new Color(250,250,250));
		this.setLayout(null);
		
		
		//cái bảng bên phải
		smallPanel = new JPanel();
		smallPanel.setPreferredSize(new Dimension((windowW-mazeSize),windowH));
		smallPanel.setBounds(mazeSize, 0, windowW-mazeSize, windowH);
		smallPanel.setLayout(null);
		this.add(smallPanel);
		
		
		//nút start
		startButton = new myButton("Start",(int)(smallPanel.getSize().width/2)-120-30, 30, 120, 50,Color.PINK);
		smallPanel.add(startButton);
		startButton.addActionListener(this);
		
		
		
		//nút re-maze
		reMazeButton = new myButton("Re-Maze",(int)(smallPanel.getSize().width/2)+30, 30, 120, 50,Color.CYAN);
		smallPanel.add(reMazeButton);
		reMazeButton.addActionListener(this);
		reMazeButton.setEnabled(false);
		
		
		
		
		//Bảng lựa chọn cellSlider + Label của nó
		cellSlider = new JSlider(10,100,40);
		cellSlider.setBounds((windowW-mazeSize)/2-(350/2), 150, 350, 50);
		cellSlider.setPaintTicks(true);
		cellSlider.setMinorTickSpacing(5);
		cellSlider.setPaintTrack(true);
		cellSlider.setMajorTickSpacing(10);
		cellSlider.setSnapToTicks(true);
		cellSlider.setPaintLabels(true);
		cellSlider.addChangeListener(this);

		cellLabel = new JLabel();
		cellLabel.setText("Cell's size adjustment: " + cellSlider.getValue());
		cellLabel.setBounds(30, 130, 250, 20);
		cellLabel.setFont(new Font("",Font.BOLD,18));
		
		smallPanel.add(cellLabel);
		smallPanel.add(cellSlider);
		
		
		//Bảng lựa chọn speed va label cua no
		speedSlider = new JSlider(1,5,5);
		speedSlider.setBounds((windowW-mazeSize)/2-(350/2), 230, 350, 40);
		speedSlider.setPaintTrack(true);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setSnapToTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.addChangeListener(this);

		speedLabel = new JLabel();
		speedLabel.setText("Speed: " + speedSlider.getValue());
		speedLabel.setBounds(30, 210, 180, 20);
		speedLabel.setFont(new Font("",Font.BOLD,18));
				
		smallPanel.add(speedLabel);
		smallPanel.add(speedSlider);
		
		
		//generate Check box
		mazeCheckbox = new JCheckBox();
		mazeCheckbox.setBounds((int)(smallPanel.getSize().width/2)-120-30, 95, 250, 20);
		mazeCheckbox.setText("Generate instantly");
		mazeCheckbox.setFont(new Font("",Font.BOLD,15));
		mazeCheckbox.setFocusable(false);
	
	
		smallPanel.add(mazeCheckbox);
		
		
		//Check box
		BFSCheckbox = new JCheckBox();
		BFSCheckbox.setBounds(smallPanel.getSize().width/2-30, 285, 250, 30);
		BFSCheckbox.setText("Breadth First Search");
		BFSCheckbox.setFont(new Font("",Font.BOLD,15));
		BFSCheckbox.setFocusable(false);
		smallPanel.add(BFSCheckbox);
		
		
		//BFS button
		BFSButton = new myButton("Start",40, 290, 100, 130,new Color(255, 231, 122));
		smallPanel.add(BFSButton);
		BFSButton.addActionListener(this);
		BFSButton.setEnabled(false);
		
		//nút resetMaze
		resetButton = new myButton("Reset Maze",40, 440, 100, 130,new Color(44, 95, 45));
		smallPanel.add(resetButton);
		resetButton.addActionListener(this);
				
		
		
		
		
		
		
		initMaze();
		tm.start();
	}
	
	
	private void initMaze() {
		maze = new myMaze(mazeSize,cellSize);	
	}

	public void paintComponent(Graphics g) {
		
		if(maze.checkFinished()) {
			reMazeButton.setEnabled(true);
			BFSButton.setEnabled(true);
			
		}else {
			BFSButton.setEnabled(false);
			resetButton.setEnabled(false);
		}
		
		super.paintComponent(g);
		
		
		
		
		maze.drawMaze(g);
		
	
		if(!flag) {
			maze.drawPathFinder(g);
			if(maze.finish) {
				mazeCheckbox.setEnabled(true);
				BFSButton.setEnabled(true);
				BFSCheckbox.setEnabled(true);
				resetButton.setEnabled(true);
				tm.stop();
			}
		}
		
		if(!mazeCheckbox.isSelected()) {
			maze.mazeAlgorithm(g);
		}	
		else {
			maze.drawMazeInstantly();
		}
		
		
		
		
		
	}
	private void reset() {
		tm.start();
		running = -1;	
		startButton.setEnabled(true);
		repaint();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//nhan nut re-maze, tạo lại maze mới
		if(e.getSource()==reMazeButton) {
			if(BFSCheckbox.isSelected()) {
				BFSCheckbox.setSelected(false);
				flag = true;
			}
			mazeCheckbox.setEnabled(true);
			initMaze();
			reset();
			startButton.setText("Start");
		}
		
		
			
		//nhấn nút start 
		if (e.getSource()==startButton) {
			running *= -1;
			if(running == 1)
				startButton.setText("Pause");
			else
				startButton.setText("Start");
		}
		
		//nút reset maze
		if(e.getSource()==resetButton) {
			flag = true;
			BFSCheckbox.setEnabled(true);
			maze.resetMaze();
			tm.start();
		}
		
	
		//start của bên thuật toán tìm đường
		if(e.getSource() == BFSButton) {
			mazeCheckbox.setSelected(false);
			mazeCheckbox.setEnabled(false);
			
			if(BFSCheckbox.isSelected()) {
				System.out.println("Start running maze finder");
				BFSCheckbox.setEnabled(false);
				if(flag) {
					maze.initStartAndEnd();
					flag = false;
				}
			}	
		}
		
		
		
		
		
		
		if(running==1)
			repaint();
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
		
		//cellSlider
		if(e.getSource()==cellSlider) {
			if(cellSlider.getValue()%5==0) {
				mazeCheckbox.setEnabled(true);
				BFSCheckbox.setEnabled(true);
				cellLabel.setText("Cell's size adjustment: " + cellSlider.getValue());
				cellSize = cellSlider.getValue();
				startButton.setText("Start");
				if(BFSCheckbox.isSelected()) {
					BFSCheckbox.setSelected(false);
					flag = true;
				}
				initMaze();
				reset();
			}
		}
		
		
		
		//SpeedSlider
		if(e.getSource()==speedSlider) {
				speedLabel.setText("Speed: " + speedSlider.getValue());
				delay = slowestDelay - (speedSlider.getValue()-1)* ((slowestDelay-10)/4) ;
				tm.setDelay(delay);
				System.out.println(delay);
		}
		
		
		
		
			
	}


	


}
