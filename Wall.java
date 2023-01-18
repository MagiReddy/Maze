import java.awt.Polygon;
import java.awt.Color;
import java.awt.GradientPaint;

public class Wall
{
	private int[] x;
	private int[] y;
	private int r;
	private int g;
	private int b;
	private String type;
	private int dist;
	public Wall(int[] x, int[] y, int r, int g, int b, String type, int dist)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		this.g = g;
		this.b = b;
		this.type = type;
		this.dist = dist;
	}
	public Polygon getPolygon()
	{
		return new Polygon(x, y, 4);
	}

	public GradientPaint getPaint()
	{
		int endR = r-dist;
		int endG = g-dist;
		int endB = b-dist;
		if(r < 0)
			r = 0;
		if(g < 0)
			g = 0;
		if(b < 0)
			b = 0;
		if(endR < 0)
			endR = 0;
		if(endG < 0)
			endG = 0;
		if(endB < 0)
			endB = 0;

		switch(type)
		{
			case "right":
			case "left":
				return new GradientPaint(x[0], y[1], new Color(r,g,b), x[1], y[1], new Color(endR, endG, endB));
		}

		return new GradientPaint(x[1], y[0], new Color(r,g,b), x[1], y[1], new Color(endR, endG, endB));
	}
}