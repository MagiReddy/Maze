import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.GradientPaint;

public class MazeProgram extends JPanel implements KeyListener
{
	JFrame frame;
	Hero hero;
	String[][] maze;
	int x = 100; int y = 100;
	ArrayList<Wall> walls;
	int battery = 100;
	boolean draw3D = false;
	boolean flashlight = false;
	int vision = 4;
	int shrink = 50;
	public MazeProgram()
	{
		walls = new ArrayList<Wall>();
		setBoard();

		frame = new JFrame("A-mazing Program!");
		frame.add(this);

		setWalls();
		frame.addKeyListener(this);
		frame.setSize(1200,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;

		g.setColor(new Color(225, 148, 255)); //Can use new Color(0,0,0) OR Color.GREEN

		g.fillRect(0,0,frame.getWidth(),frame.getHeight());
		//g.setColor(Color.BLACK);
		//g.drawRect(100,100,500,500); // Just draws the edge of Rectangle
		//g.fillRect(100,100,500,500); //Fills a rectangle with color
		//g.fillOval(100,100,500,500);
		if(!draw3D)
		{
			for (int r=0; r<maze.length; r++)
			{
				for (int c=0; c<maze[0].length; c++)
				{
					if (maze[r][c].equals("$"))
					{
						g.setColor(Color.BLACK);
						g.fillRect(c*10+3,r*10+20,10,10);
						g.setColor(Color.WHITE);
						g.drawRect(c*10+3,r*10+20,10,10);
					}
				}
			}
			g.setColor(Color.CYAN);
			g.fillOval(hero.getC()*10+3,hero.getR()*10+20,hero.getDim(),hero.getDim());
		}
		else
		{
			g.setColor(Color.BLACK); //Can use new Color(0,0,0) OR Color.GREEN

			g.fillRect(0,0,frame.getWidth(),frame.getHeight());
			for(Wall wall:walls)
			{
				//g.setColor(new Color(100,100,100));
				g2.setPaint(wall.getPaint());
				g2.fillPolygon(wall.getPolygon());
				//g2.setColor(Color.DARK_GRAY);
				//g2.drawPolygon(wall.getPolygon());
			}
		}

		g.setColor(Color.WHITE);
		if(hero.getR() == 34 && hero.getC() == 3) //34, 104
		{
			g.drawString("Great Job!", 500, 75);
		}
		String moves = "Number of Moves: " + hero.numMoves();
		g.drawString(moves, 500,650);

		String dead = "Battery in Flashlight: " + hero.getBattery();
		if(hero.isDead())
		{
			dead = "Flashlight is dead!";
		}
		g.drawString(dead, 500,666);
	}

	public void setWalls()
	{
		walls = new ArrayList<Wall>();
		int r = hero.getR();
		int c = hero.getC();

		if(hero.getFlash() && !(hero.isDead()))
			vision = 5;
		else
		{
			vision = 2;
		}

		for(int i = 0; i<vision; i++) // vision is the length of the hallway
		{
			walls.add(wallOnLeft(i));
			walls.add(wallOnRight(i));
			walls.add(ceiling(i));
			walls.add(floor(i));
			walls.add(leftRect(i));
			walls.add(rightRect(i));

			if (hero.getDirection().equals("up"))
			{
				try
				{
					if (maze[r-i][c-1].equals("$"))
						walls.add(wallOnLeft(i));

					if (maze[r-i][c+1].equals("$"))
						walls.add(wallOnRight(i));
				}catch(ArrayIndexOutOfBoundsException e){}

			}

			if (hero.getDirection().equals("right"))
			{
				try
				{
					if (maze[r-1][c+i].equals("$"))
						walls.add(wallOnLeft(i));

					if (maze[r+1][c+i].equals("$"))
						walls.add(wallOnRight(i));
				}catch(ArrayIndexOutOfBoundsException e){}
			}

			if (hero.getDirection().equals("left"))
			{
				try
				{
					if (maze[r+1][c-i].equals("$"))
						walls.add(wallOnLeft(i));

					if (maze[r-1][c-i].equals("$"))
						walls.add(wallOnRight(i));
				}catch(ArrayIndexOutOfBoundsException e){}
			}

			if (hero.getDirection().equals("down"))
			{
				try
				{

					if (maze[r+i][c+1].equals("$"))
						walls.add(wallOnLeft(i));
					if (maze[r+i][c-1].equals("$"))
						walls.add(wallOnRight(i));
				}catch(ArrayIndexOutOfBoundsException e){}
			}
	}
	for(int i=vision; i>0; i--)
	{
		if (hero.getDirection().equals("up"))
		{
			try
			{
				if (maze[r-i][c].equals("$"))
					walls.add(frontWall(i));
			}catch(ArrayIndexOutOfBoundsException e){}
		}

		if (hero.getDirection().equals("right"))
		{
			try
			{
				if (maze[r][c+i].equals("$"))
					walls.add(frontWall(i));
			}catch(ArrayIndexOutOfBoundsException e){}
		}

		if (hero.getDirection().equals("left"))
		{
			try
			{
				if (maze[r][c-i].equals("$"))
					walls.add(frontWall(i));
			}catch(ArrayIndexOutOfBoundsException e){}
		}

		if (hero.getDirection().equals("down"))
		{
			try
			{
				if (maze[r+i][c].equals("$"))
					walls.add(frontWall(i));
			}catch(ArrayIndexOutOfBoundsException e){}
		}
		}

	}

	public Wall frontWall(int i)
	{
		int[] x = {200+(shrink*i), 900-(shrink*i), 900-(shrink*i), 200+(shrink*i)};
		int[] y = {35+(shrink*i), 35+(shrink*i), 635-(shrink*i), 635-(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "front", shrink);
	}

	public Wall leftRect(int i)
	{
		int[] x = {200+(shrink*i), 250+(shrink*i), 250+(shrink*i), 200+(shrink*i)};
		int[] y = {85+(shrink*i), 85+(shrink*i), 585-(shrink*i), 585-(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "leftRect", shrink);
	}

	public Wall rightRect(int i)
	{
		int[] x = {900-(shrink*i), 850-(shrink*i), 850-(shrink*i), 900-(shrink*i)};
		int[] y = {85+(shrink*i), 85+(shrink*i), 585-(shrink*i), 585-(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "rightRect", shrink);
	}

	public Wall wallOnLeft(int i)
	{
		int[] x = {200+(shrink*i), 250+(shrink*i), 250+(shrink*i), 200+(shrink*i)};
		int[] y = {35+(shrink*i), 85+(shrink*i), 585-(shrink*i), 635-(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "left", shrink);
	}

	public Wall wallOnRight(int i)
	{
		int[] x = {900-(shrink*i), 850-(shrink*i), 850-(shrink*i), 900-(shrink*i)};
		int[] y = {35+(shrink*i), 85+(shrink*i), 585-(shrink*i), 635-(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "right", shrink);
	}

	public Wall ceiling(int i)
	{
		int[] x = {200+(shrink*i), 250+(shrink*i), 850-(shrink*i), 900-(shrink*i)};
		int[] y = {35+(shrink*i), 85+(shrink*i), 85+(shrink*i), 35+(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "ceiling", shrink);
	}

	public Wall floor(int i)
	{
		int[] x = {200+(shrink*i), 250+(shrink*i), 850-(shrink*i), 900-(shrink*i)};
		int[] y = {635-(shrink*i), 585-(shrink*i), 585-(shrink*i), 635-(shrink*i)};
		return new Wall(x, y, 255-shrink*i,255-shrink*i,255-shrink*i, "floor", shrink);
	}

	public void keyPressed(KeyEvent e)
	{
		//System.out.println(e.getKeyCode());
		if(e.getKeyCode()==32) // 32 = spacebar
			draw3D = !draw3D;
		if(e.getKeyCode()== 70)
		{
			//System.out.println(hero.getBattery());
			hero.setFlashlight(!(hero.getFlash()));

		}
		else
			hero.move(e.getKeyCode());


		if(draw3D)
			setWalls();
		repaint();
	}

	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	//public void gradientPaint(Graphics )

	public void setBoard()
	{
		maze = new String[35][119]; // Dimensions of maze(the text file)
		File name = new File("MazeProgram.txt");
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			String text;
			int r = 0;
			while ((text=input.readLine())!= null)
			{
				maze[r] = text.split("");
				r++;
			}
		}catch (IOException e){}

		hero = new Hero(1,0,10,maze,flashlight,vision);

		if(draw3D)
			setWalls();
	}

	public static void main(String[] args)
	{
		MazeProgram app = new MazeProgram();
	}
}