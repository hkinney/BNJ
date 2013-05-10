package edu.ksu.cis.bnj.ver3.streams;
/*!
 * \file OmniFormatV1_Console.java
 * \author Jeffrey M. Barber
 */
public class OmniFormatV1_Console implements OmniFormatV1
{
	/**
	 * @param idx
	 */
	public void CreateBeliefNetwork(int idx)
	{
		System.out.println("cbn "+idx);
	}
	/**
	 * @param idx
	 * @param name
	 */
	public void SetBeliefNetworkName(int idx, String name)
	{
		System.out.println("nam " + idx + " " + name);
	}
	/**
	 * @param idx
	 */
	public void BeginBeliefNode(int idx)
	{
		System.out.println("bbn " + idx);
	}
	/**
	 * @param type
	 */
	public void SetType(String type)
	{
		System.out.println("typ " + type);
	}
	/**
	 * @param var
	 */
	public void MakeContinuous(String var)
	{
		System.out.println("cts " + var);
	}
	/**
	 * @param outcome
	 */
	public void BeliefNodeOutcome(String outcome)
	{
		System.out.println("bno " + outcome);
	}
	/**
	 * @param name
	 */
	public void SetBeliefNodePosition(int x, int y)
	{
		System.out.println("pos " + x + " " + y);
	}
	/**
	 * @param name
	 */
	public void SetBeliefNodeName(String name)
	{
		System.out.println("sbn " + name);
	}
	/**
	 * 
	 */
	public void EndBeliefNode()
	{
		System.out.println("ebn");
	}
	/**
	 * @param par_idx
	 * @param chi_idx
	 */
	public void Connect(int par_idx, int chi_idx)
	{
		System.out.println("con " + par_idx + " " + chi_idx);
	}
	/**
	 * @param idx
	 */
	public void BeginCPF(int idx)
	{
		System.out.println("cpf " + idx);
	}
	/**
	 * @param x
	 */
	public void ForwardFlat_CPFWriteValue(String x)
	{
		System.out.println("flat " + x);
	}
	/**
	 * 
	 */
	public void EndCPF()
	{
		System.out.println("com");
	}
	/**
	 * 
	 */
	public void Start()
	{
		System.out.println("beg");
	}
	/**
	 * 
	 */
	public void Finish()
	{
		System.out.println("fin");
	}
}
