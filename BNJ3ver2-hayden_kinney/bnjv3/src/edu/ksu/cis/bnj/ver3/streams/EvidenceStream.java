package edu.ksu.cis.bnj.ver3.streams;
/*!
 * \file EvidenceStream.java
 * \author Jeffrey M. Barber
 */
public interface EvidenceStream
{
	public void Start();
	public void BeginEvidence();
	public void BeginSample(int time);
	public void Witness(String name, String value);
	public void EndSample();
	public void EndEvidence();
	public void End();
}
