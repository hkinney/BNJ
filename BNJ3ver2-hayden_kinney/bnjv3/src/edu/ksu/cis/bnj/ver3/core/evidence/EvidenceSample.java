package edu.ksu.cis.bnj.ver3.core.evidence;
import java.util.Iterator;
import java.util.TreeMap;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.Discrete;
/*!
 * \file EvidenceSample.java
 * \author Jeffrey M. Barber
 */
public class EvidenceSample
{
	private TreeMap	_Evidence;
	private int		_Time;
	private int		_Loc;
	private int[]	DiscreteCache;
	/*! create a new sample at this time
	 * \param[in] time
	 */
	public EvidenceSample(int time)
	{
		_Evidence = new TreeMap();
		_Time = time;
		_Loc = -1;
	}
	/*! map key to value
	 * \param[in] key
	 * \param[in] value
	 */
	public void map(String key, String value)
	{
		_Evidence.put(key, value);
	}
	/*! get the key iterator
	 * \return an iterator for the keys
	 */
	public Iterator getKeyIterator()
	{
		return _Evidence.keySet().iterator();
	}
	/*! extract the key
	 * \param[in] it the iterator
	 * \return the key
	 */
	public String getKey(Iterator it)
	{
		return (String) it.next();
	}
	/*! get the value associated to the key: key
	 * \param[in] key the key to value
	 * \return the value associated to key
	 */
	public String getValue(String key)
	{
		return (String) _Evidence.get(key);
	}
	/*! given an array of belief nodes with discrete domains, build a discrete cache (subject to change)
	 * \param[in] Nodes array of belief nodes
	 */
	public void createDiscreteCache(BeliefNode[] Nodes)
	{
		DiscreteCache = new int[Nodes.length];
		for (int i = 0; i < Nodes.length; i++)
		{
			DiscreteCache[i] = ((Discrete) Nodes[i].getDomain()).findName(getValue(Nodes[i].getName()));
		}
	}
	/*! get the discrete cache (subject to change)
	 * \return
	 */
	public int[] getDiscreteCache()
	{
		return DiscreteCache;
	}
	/*! write a discrete cache out
	 * \param[in] cache
	 */
	public void writeDiscreteCache(int[] cache)
	{
		DiscreteCache = cache;
	}
	/*! query the discrete cache
	 * \param[in] K
	 * \return
	 */
	public int getDiscreteValue(int K)
	{
		return DiscreteCache[K];
	}
	/*! set the location of this sample
	 * \param[in] k the location
	 */
	public void setLOC(int k)
	{
		_Loc = k;
	}
	/*! query the location
	 * \return
	 */
	public int loc()
	{
		return _Loc;
	}
}