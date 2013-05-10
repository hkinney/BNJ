package edu.ksu.cis.util.graph.layout;

import edu.ksu.cis.util.graph.algorithms.TopologicalSort;
import edu.ksu.cis.util.graph.core.Graph;
import edu.ksu.cis.util.graph.core.Vertex;

/*!
 * \file LayGAPT.java
 * \author Jeffrey M. Barber
 */
public class LayRandomPolyTree
{
	
	public static void mutate(int[] order, int d)
	{
		for(int i = 0; i < d; i++)
		{
			int a = (int)(Math.random() * order.length);
			int b = (int)(Math.random() * order.length);
			int t = order[a];
					order[a] = order[b];
							   order[b] = t;
		}
	}
	
	public static double area(Graph G)
	{
		double A = 0;
		double x0 = 10000,y0 = 10000,x1 = -10000,y1 = -10000;
		Vertex[] V = G.getVertices();
		for(int i = 0; i < V.length; i++)
		{
			if(V[i].getx() < x0) x0 = V[i].getx();
			if(V[i].getx() > x1) x1 = V[i].getx();
			if(V[i].gety() < y0) y0 = V[i].gety();
			if(V[i].gety() > y1) y1 = V[i].gety();
		}
		return (y1-y0)*(x1-x0);
	}
	
	public static int[] init(Graph G)
	{
		int[] W = new int[G.getNumberOfVertices()];
		for(int i = 0; i < W.length; i++)
		{
			W[i] = i;
		}
		return W;
	}
	
	public static void copy(int[] src, int [] dest)
	{
		for(int i = 0; i < src.length; i++)
		{
			dest[i] = src[i];
		}
	}
	public static void apply(Graph G)
	{
		int[] win = init(G);
		int[] order = init(G);
		/*
		double winner = area(G);
		for(int gen = 0; gen < 100; gen++)
		{
			mutate(order, 50);
			Graph G2 = G.copy();
			G2.applyOrdering(order);
			LayPolyTree.apply(G2);
			double aG2 = area(G2);
			if(aG2 < winner)
			{
				copy(order,win);
				winner = aG2;
			}
		}
		*/
		mutate(win, 100);
		G.applyOrdering(win);
		LayPolyTree.apply(G);
		
	}	
}
