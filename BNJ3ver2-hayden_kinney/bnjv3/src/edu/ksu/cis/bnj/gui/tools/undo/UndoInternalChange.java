package edu.ksu.cis.bnj.gui.tools.undo;
import edu.ksu.cis.bnj.gui.tools.UndoContext;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Domain;
public class UndoInternalChange implements Undo
{
	private CPF[]			_oldCPF;
	private Domain[]		_oldDomain;
	private BeliefNode[]	cpy;
	private String[]		str;
	public UndoInternalChange(BeliefNetwork bn)
	{
		cpy = bn.getNodes();
		_oldCPF = new CPF[cpy.length];
		str = new String[cpy.length];
		_oldDomain = new Domain[cpy.length];
		for (int i = 0; i < cpy.length; i++)
		{
			_oldCPF[i] = cpy[i].getCPF();
			_oldDomain[i] = cpy[i].getDomain();
			str[i] = cpy[i].getName();
		}
	}
	public void apply(UndoContext UC)
	{
		for (int i = 0; i < cpy.length; i++)
		{
			cpy[i].setCPF(_oldCPF[i]);
			cpy[i].setDomain(_oldDomain[i]);
			cpy[i].setName(str[i]);
		}
	}
}