package edu.ksu.cis.bnj.gui.tools.undo;


import edu.ksu.cis.bnj.gui.tools.UndoContext;
import edu.ksu.cis.bnj.ver3.core.CPF;
import edu.ksu.cis.bnj.ver3.core.Value;

/*!
 * \file UndoCPFEdit.java
 * \author Jeffrey M. Barber
 */
public class UndoCPFEdit implements Undo
{
	private CPF	_CPF;
	private Value _OldValue;
	private int _Position;
	
	public UndoCPFEdit(CPF cpf, int pos)
	{
		_CPF = cpf;
		_OldValue = cpf.get(pos);
		_Position = pos;
	}

	public void apply(UndoContext UC)
	{
		_CPF.put(_Position, _OldValue);
	}	
}
