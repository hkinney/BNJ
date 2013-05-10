package edu.ksu.cis.bnj.ver3.streams;
/**
 * file: BeliefNodeStream.java
 * 
 * @author Jeffrey M. Barber
 */
public interface OmniFormatV1
{
	/**
	 * @param idx
	 */
	public void CreateBeliefNetwork(int idx);
	/**
	 * @param idx
	 * @param name
	 */
	public void SetBeliefNetworkName(int idx, String name);
	/**
	 * @param idx
	 */
	public void BeginBeliefNode(int idx);
	/**
	 * @param type
	 */
	public void SetType(String type);
	/**
	 * @param var
	 */
	public void MakeContinuous(String var);
	/**
	 * @param outcome
	 */
	public void BeliefNodeOutcome(String outcome);
	/**
	 * @param name
	 */
	public void SetBeliefNodePosition(int x, int y);
	/**
	 * @param name
	 */
	public void SetBeliefNodeName(String name);
	/**
	 * 
	 */
	public void EndBeliefNode();
	/**
	 * @param par_idx
	 * @param chi_idx
	 */
	public void Connect(int par_idx, int chi_idx);
	/**
	 * @param idx
	 */
	public void BeginCPF(int idx);
	/**
	 * @param x
	 */
	public void ForwardFlat_CPFWriteValue(String x);
	/**
	 * 
	 */
	public void EndCPF();
	/**
	 * 
	 */
	public void Start();
	/**
	 * 
	 */
	public void Finish();
}