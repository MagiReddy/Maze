public class Hero
{
	int r, c, dim;
	int numMoves = 0;
	String[][] maze;
	String direction = "right";
	int battery = 100;
	boolean flashlight;
	int vision;
	boolean isDead = false;
	public Hero(int r, int c, int dim, String[][] maze, boolean flashlight, int vision)
	{
		this.r = r;
		this.c = c;
		this.dim = dim;
		this.maze = maze;
		this.flashlight = flashlight;
		this.vision = vision;
	}

	public int getR()
	{
		return r;
	}

	public int getC()
	{
		return c;
	}

	public int getDim()
	{
		return dim;
	}

	public int numMoves()
	{
		return numMoves;
	}

	public boolean isDead()
	{
		if(getBattery() <= 0)
		{
			isDead = true;
		}
		return isDead;
	}
	public void setFlashlight(boolean flash)
	{
		flashlight=flash;
	}
	public boolean getFlash()
	{
		return flashlight;
	}

	public void reduceBattery()
	{
		if(getFlash())
		{
			battery-=1;
		}
	}

	public int getBattery()
	{
		return battery;
	}

	public void move(int key)
	{
		if (key == 39)
		{
			if (direction.equals("up"))
				direction = "right";
			else if (direction.equals("right"))
				direction = "down";
			else if (direction.equals("down"))
				direction = "left";
			else if (direction.equals("left"))
				direction = "up";
		}
		if (key == 37)
		{
			if (direction.equals("up"))
				direction = "left";
			else if (direction.equals("left"))
				direction = "down";
			else if (direction.equals("down"))
				direction = "right";
			else if (direction.equals("right"))
				direction = "up";
		}
		if (key == 38)
		{
			if (direction.equals("up"))
			{
				if (maze[r-1][c].equals(" "))
				{
					r--;
					numMoves++;
					reduceBattery();
				}
			}
			else if (direction.equals("right"))
			{
				if (maze[r][c+1].equals(" "))
				{
					c++;
					numMoves++;
					reduceBattery();
				}
			}
			else if (direction.equals("left"))
			{
				if (maze[r][c-1].equals(" "))
				{
					c--;
					numMoves++;
					reduceBattery();
				}
			}
			else if (direction.equals("down"))
			{
				if (maze[r+1][c].equals(" "))
				{
					r++;
					numMoves++;
					reduceBattery();
				}
			}
		}
	}

	public String getDirection()
	{
		return direction;
	}
}