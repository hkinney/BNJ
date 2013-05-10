package edu.ksu.cis.bnj.gui.tools;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
public class Transformation
{
	private Point	pan;
	private Point	window;
	private double	scale;
	private Point	temp;

	public void settemp(int x, int y)
	{
		temp.x = x;
		temp.y = y;
	}
	public void commit()
	{
		translate(temp.x,temp.y);
		temp.x = 0;
		temp.y = 0;
	}
	
	public int x()
	{
		return pan.x + temp.x;
	}
	public int y()
	{
		return pan.y + temp.y;
	}
	
	public void move(int x, int y)
	{
		pan.x = x;
		pan.y = y;
	}
	public int w()
	{
		return window.x;
	}
	public int h()
	{
		return window.y;
	}
	public Transformation()
	{
		pan = new Point(0, 0);
		temp = new Point(0,0);
		window = new Point(1, 1);
		scale = 1;
	}
	public void setWindow(int w, int h)
	{
		window.x = w;
		window.y = h;
	}
	public void translate(int x, int y)
	{
		pan.x += x;
		pan.y += y;
	}
	public double is(int v)
	{
		return (v / scale);
	}
	public int s(int v)
	{
		return (int) (v * scale);
	}
	public int tx(double x)
	{
		return (int) (x * scale + x());
	}
	public int ty(double y)
	{
		return (int) (y * scale + y());
	}
	public int itx(int x)
	{
		return (int) ((x - x()) / scale);
	}
	public int ity(int y)
	{
		return (int) ((y - y()) / scale);
	}
	public void scale(double factor)
	{
		if (scale >= 15.0 && factor > 1.0) return;
		if (scale < 0.05 && factor < 1.0) return;
		scale *= factor;
		pan.x = (int) (window.x / 2.0 - factor * (window.x / 2.0 - x()));
		pan.y = (int) (window.y / 2.0 - factor * (window.y / 2.0 - y()));
	}
	public void normalizescale()
	{
		scale(1.0 / scale);
	}
	public void Center(Rectangle R)
	{
		int idealX = window.x / 2 - R.width / 2;
		int idealY = window.y / 2 - R.height / 2;
		int cX = tx(itx(idealX) - itx(R.x));
		int cY = ty(ity(idealY) - ity(R.y));
		commit();
		pan.x = cX;
		pan.y = cY;
	}
	public void Zoom2Fit(Rectangle R)
	{
		double W = R.width;
		double H = R.height;
		double ratio = 1;
		double xR = window.x / W;
		double yR = window.y / H;
		
		ratio = xR;
		if(H * xR > window.y)
		{
			ratio *= (window.y / (H * xR));
		}
		scale = ratio;
	}
}