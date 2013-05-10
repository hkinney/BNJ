package edu.ksu.cis.bnj.gui.tools;
import org.eclipse.swt.graphics.Rectangle;
import edu.ksu.cis.bnj.gui.rendering.RenderCPF;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.util.graph.core.Edge;
import edu.ksu.cis.util.graph.core.Vertex;
public class SelectionContext
{
	// inputs
	public int			x;
	public int			y;
	public boolean		multi		= false;
	public boolean		found		= false;
	// outputs
	public CPF			selectedCPF	= null;
	public int			RegIdx		= -1;
	public Rectangle	Container;
	public BeliefNode	foundNode;
	public RenderCPF	cpfresult;
	public Vertex		foundNodeContainer;
	public Edge			foundEdge;
	public boolean		lookingforEdge = false;
}