import java.awt.*;
import java.util.*;

public class myMaze extends Rectangle{
	private final int MAZE_SIZE;
	private int CELL_SIZE;
	private final int w ;
	private Cell[][] grids;
	private Cell current;
	private Cell next;
	private Stack<Cell> visitedStack;
	private boolean running = false;
	private int countVisited=0;
	public boolean finish = false;
	private ArrayList<Cell> pathsFromAtoB;
	
	private Cell start,end;
	private Queue<Cell> visitedQueue;

	
	myMaze(int mazeSize,int cellSize){
		MAZE_SIZE = mazeSize;
		CELL_SIZE = cellSize;
		w = (int)(MAZE_SIZE/CELL_SIZE);
		init();
	}
	
	private void init() {
		grids = new Cell[w][w];
		for (int row=0;row<w;row++) {
			for (int col =0;col<w;col++) {
				grids[row][col] = new Cell(row,col,CELL_SIZE);
			}
		}
		current = grids[new Random().nextInt(w)][new Random().nextInt(w)];
		current.visited = true;
		visitedStack = new Stack<Cell>();
		running = true;
		
	}
	public void resetMaze(){
		for (int row=0;row<w;row++) {
			for (int col =0;col<w;col++) {
				grids[row][col].resetCell();
			}
		}
		finish = false;
	}
	
	public void drawMaze(Graphics g) {
		for(int i = 0;i<w;i++) {
			for(int j = 0;j<w;j++) {
				grids[i][j].drawBox(g, new Color(0,0,255,100));
				grids[i][j].drawCell(g);
			}
		}
	}
	public void mazeAlgorithm(Graphics g) {
		if(running) {
			if(countVisited<=w*w)
				current.drawBox(g,new Color(0,255,0,100));
			update();
			System.out.println(countVisited);
		}
	}
	public void drawMazeInstantly() {	
		mazeAlgorithm();
		running = false;
	}
	
	public void drawPathFinder(Graphics g) {

		mazeFinderBFS(g);
		
		for(Cell x: pathsFromAtoB) {
			x.drawPath(g, Color.pink);
		}
		
		start.drawBox(g,new Color(252, 118, 106));
		end.drawBox(g,new Color(91, 132, 177));
	
		
		for(int i = 0;i<w;i++) {
			for(int j = 0;j<w;j++) {
				grids[i][j].drawCell(g);
			}
		}	

	}
	public boolean checkFinished(){
		if (running)
			return false;
		return true;
	}
	
	public void mazeAlgorithm() {
		Stack<Cell> visitedList = new Stack<>();
		current.visited = true;
		visitedList.push(current);
		while(!visitedList.isEmpty()) {
			current = visitedList.pop();
			if (hasNeighbor(current)) {
				visitedList.push(current);
				next = getOneRandomNeighbor(current);
				wallBreaker(current, next);
				next.visited = true;
				visitedList.push(next);	
			}
		}
	}
	

	
	public void update() {
		if (running == false)
			return;
		
		next = getOneRandomNeighbor(current);
		if(next != null) {
			visitedStack.push(next);
			countVisited++;
			next.visited = true;
			wallBreaker(current, next);
			current = next;
		}else {
			while(!hasNeighbor(current)) {
				if(!visitedStack.empty())
					current = visitedStack.pop();
				else {
					running = false;
					countVisited++;
					return;
				}
			}
			update();
		}
	}
	
	public void mazeFinderBFS(Graphics g) {

		next = getOneNeighbor(current);
		luuVetFromAtoB(current,next);
		if(next == end) {
			System.out.println("Finished");
			finish = true;
			taoPathTuEndToiStart();
			return;
		}
		
		if(next != end && next != null) {
			visitedQueue.offer(next);
			next.visitedPath = true;
			next.visited = true;
			next.drawPath(g, Color.RED);
		}else {
			if(!visitedQueue.isEmpty())
				current = visitedQueue.poll();
			else {
				return;
			}
		}
	}
	
	
	private void taoPathTuEndToiStart() {
		pathsFromAtoB.add(end);
		Cell tempParent = end.parent;
		while(tempParent != start) {
			pathsFromAtoB.add(tempParent);
			tempParent = tempParent.parent;
		}
	}
	
	
	private void luuVetFromAtoB(Cell A,Cell B) {
		if(B!=null) {
			B.parent = A;
		}
		if(A!=null)
			A.next.add(B);
	}
	

	public void initStartAndEnd() {

		
		start = grids[new Random().nextInt(w)][new Random().nextInt(w)];
		end = grids[new Random().nextInt(w)][new Random().nextInt(w)];
		
		if (start == end){
			end = grids[new Random().nextInt(w)][new Random().nextInt(w)];
		}
		
		for(int i = 0;i<w;i++) {
			for(int j = 0;j<w;j++) {
				grids[i][j].visited = false;
			}
		}
		
		
		start.visited = true;
		end.visited = true;
		visitedQueue = new LinkedList<Cell>();
		pathsFromAtoB = new ArrayList<Cell>();
		current = start;
		next = current;

	}
		
	
	
	public Cell getOneNeighbor(Cell currentCell){ //for pathfinder
		ArrayList<Cell> neighbors = new ArrayList<>();
		
		if (currentCell.row-1 >= 0 && grids[currentCell.row-1][currentCell.col].visitedPath == false && currentCell.Walls[0] == false) {
			neighbors.add(grids[currentCell.row-1][currentCell.col]); // top
		}
		
		if (currentCell.col+1 < w && grids[currentCell.row][currentCell.col+1].visitedPath == false && currentCell.Walls[1] == false) {
			neighbors.add(grids[currentCell.row][currentCell.col+1]); //right
		}
		
		if (currentCell.row + 1 < w && grids[currentCell.row+1][currentCell.col].visitedPath == false && currentCell.Walls[2] == false) {
			neighbors.add(grids[currentCell.row+1][currentCell.col]); // bot
		}
		
		if (currentCell.col-1 >= 0 && grids[currentCell.row][currentCell.col-1].visitedPath == false && currentCell.Walls[3] == false) {
			neighbors.add(grids[currentCell.row][currentCell.col-1]); //left
		}
		
		
		if (neighbors.isEmpty())
			return null;
		
		return  neighbors.get(0);
	}
	
	public Cell getOneRandomNeighbor(Cell currentCell) { //for maze generator
		ArrayList<Cell> neighbors = new ArrayList<>();
		
		if (currentCell.row-1 >= 0 && grids[currentCell.row-1][currentCell.col].visited == false) {
			neighbors.add(grids[currentCell.row-1][currentCell.col]); // top
		}
		
		if (currentCell.col+1 < w && grids[currentCell.row][currentCell.col+1].visited == false) {
			neighbors.add(grids[currentCell.row][currentCell.col+1]); //right
		}
		
		if (currentCell.row + 1 < w && grids[currentCell.row+1][currentCell.col].visited == false) {
			neighbors.add(grids[currentCell.row+1][currentCell.col]); // bot
		}
		
		if (currentCell.col-1 >= 0 && grids[currentCell.row][currentCell.col-1].visited == false) {
			neighbors.add(grids[currentCell.row][currentCell.col-1]); //left
		}
		
		
		if (neighbors.isEmpty())
			return null;
		
		return  neighbors.get(new Random().nextInt(neighbors.size()));
		
	}
	public boolean hasNeighbor(Cell currentCell) {
		if (getOneRandomNeighbor(currentCell)!=null)
			return true;
		return false;
	}
	public void wallBreaker(Cell cellA,Cell cellB) {
		if (cellA.row == cellB.row+1) { // B top A
			cellA.Walls[0] = false;
			cellB.Walls[2] = false;
			return;
		}
		
		if (cellA.row == cellB.row-1) { // B bot A
			cellA.Walls[2] = false;
			cellB.Walls[0] = false;
			return;
		}
		
		if (cellA.col == cellB.col+1) { // B left A
			cellA.Walls[3] = false;
			cellB.Walls[1] = false;
			return;
		}
		
		if (cellA.col == cellB.col-1) { // B right A
			cellA.Walls[1] = false;
			cellB.Walls[3] = false;
			return;
		}
		
		
		
	}

	public int getCellSize() {
		return CELL_SIZE;
	}

	public void setCellSize(int cellSize) {
		CELL_SIZE = cellSize;
	}
	
	
}
