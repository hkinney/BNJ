package edu.ksu.cis.bnj.ver3.streams;

import java.util.ArrayList;
import edu.ksu.cis.bnj.ver3.core.evidence.EvidenceList;
import edu.ksu.cis.bnj.ver3.core.evidence.EvidenceSample;

/*!
 * \file Evidence_Reader.java
 * \author Jeffrey M. Barber
 */
public class Evidence_Reader implements EvidenceStream
{
	ArrayList _EvidenceSets;
	
	EvidenceList _CurrentEvidence;
	EvidenceSample _CurrentSample;
	
	public int size()
	{
		return _EvidenceSets.size();
	}
	
	public EvidenceList get(int k)
	{
		return (EvidenceList) _EvidenceSets.get(k);
	}
	
	public Evidence_Reader()
	{
		_EvidenceSets = new ArrayList();
	}
	
	public void BeginEvidence()
	{
		System.out.println("begin evidence");
		_CurrentEvidence = new EvidenceList();
	}
	
	public void BeginSample(int time)
	{
		System.out.println("begin sample");
		_CurrentSample = new EvidenceSample(time);
	}
	public void Witness(String name, String value)
	{
		System.out.println("witness " + name + " = " + value);
		_CurrentSample.map(name,value);
	}
	public void EndSample()
	{
		System.out.println("end sample");
		_CurrentEvidence.add(_CurrentSample);
	}

	public void EndEvidence()
	{
		System.out.println("end evidence");
		_EvidenceSets.add(_CurrentEvidence);
	}
	
	public void Start()
	{
		_EvidenceSets = new ArrayList();
	}
	
	public void End()
	{
	}
}
