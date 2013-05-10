package edu.ksu.cis.bnj.gui.tools;
import java.util.ArrayList;
import java.util.Iterator;
import edu.ksu.cis.bnj.gui.rendering.RenderNode;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;
public class Layouts
{
	static public void ArrangeCircle(Graph G, Constants C)
	{
		Vertex[] V = G.getVertices();
		double r = V.length * C.CircleLayoutLen;
		for (int i = 0; i < V.length; i++)
		{
			double a = (Math.PI * 2 * i) / V.length;
			RenderNode rnode = (RenderNode) V[i].getObject();
			int x = (int) (Math.cos(a) * r);
			int y = (int) (Math.sin(a) * r);
			rnode.move(x, y);
		}
	}
	public ArrayList	_result;
	public double		rad;
	public RenderNode	ren;
	static public int[]	Marked;
	public Layouts(RenderNode R)
	{
		rad = 0;
		_result = new ArrayList();
		ren = R;
	}
	static public double solve(Layouts comb, Layouts add, double a)
	{
		double start = 0;
		boolean gold = false;
		while (!gold && start <= 25000)
		{
			start += 25;
			boolean c = true;
			int xx = (int) (start * Math.cos(a));
			int yy = (int) (start * Math.sin(a));
			for (Iterator it = comb._result.iterator(); it.hasNext() && c;)
			{
				Vertex V = (Vertex) it.next();
				double x = xx - V.getx();
				double y = yy - V.gety();
				boolean good = Math.sqrt((x * x + y * y)) >= (add.rad + 75);
				c = good && c;
			}
			if (c) gold = true;
		}
		return 1000;
	}
	public void add(Layouts add, double a)
	{
		if (add == null)
		{
			return;
		}
		double dist = 250;
		double ang = a;
		for (int t = 0; t < 100; t++)
		{
			double h = a + Math.random();
			double dSo = solve(this, add, h);
			if (dSo < dist)
			{
				dist = dSo;
				ang = h;
			}
		}
		int x = (int) (dist * Math.cos(ang));
		int y = (int) (dist * Math.sin(ang));
		//System.out.println("add: translating and adding by " + x + ":" + y);
		for (Iterator it = add._result.iterator(); it.hasNext();)
		{
			Vertex V = (Vertex) it.next();
			RenderNode rnode = (RenderNode) V.getObject();
			rnode.translate(x, y, null);
			double distz = Math.sqrt(rnode.x() * rnode.x() + rnode.y() * rnode.y());
			if (rad <= distz) rad = distz + 50;;
			_result.add(V);
		}
	}
	static public Layouts ArrangeDepthCircle(Graph G, Vertex V, Constants C)
	{
		if (Marked[V.loc()] > 0) return null;
		Marked[V.loc()] = 1;
		Vertex[] Child = G.getChildren(V);
		Vertex[] Paren = G.getParents(V);
		Layouts Z = new Layouts((RenderNode) V.getObject());
		Z._result.add(V);
		int N = Paren.length + Child.length;
		double AngleOff = Math.PI * 2 * Math.random();
		//System.out.println("" + V.getName());
		for (int i = 0; i < Child.length; i++)
		{
			double a = Math.PI * 2 * i / N + AngleOff;
			Layouts Lay = ArrangeDepthCircle(G, Child[i], C);
			Z.add(Lay, a);
		}
		for (int i = 0; i < Paren.length; i++)
		{
			double a = Math.PI * 2 * (i + Child.length) / N + AngleOff;
			Layouts Lay = ArrangeDepthCircle(G, Paren[i], C);
			Z.add(Lay, a);
		}
		return Z;
	}
	static public void ArrangeDepthCircleRandom(Graph G, Constants C)
	{
		Vertex[] V = G.getVertices();
		Marked = new int[V.length];
		for (int i = 0; i < V.length; i++)
		{
			RenderNode rnode = (RenderNode) V[i].getObject();
			rnode.move(0, 0);
			Marked[i] = 0;
		}
		ArrangeDepthCircle(G, V[(int) (Math.random() * V.length)], C);
	}
}