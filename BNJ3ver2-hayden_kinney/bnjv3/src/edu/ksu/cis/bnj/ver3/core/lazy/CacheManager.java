package edu.ksu.cis.bnj.ver3.core.lazy;
import edu.ksu.cis.util.GlobalOptions;
/*!
 * \file CacheManager.java
 * \author Jeffrey M. Barber
 */
public class CacheManager
{
	static private CacheManager	_instance	= new CacheManager();
	private int					units;
	private int					max;
	/*! singleton access controller
	 * \return
	 */
	static public CacheManager getInstance()
	{
		return _instance;
	}
	/*! construct the cache manager
	 */
	public CacheManager()
	{
		reset();
	}
	/*! reset the cache according to the GlobalOptions
	 */
	public void reset()
	{
		units = GlobalOptions.getInstance().getInteger("lazyeval_totalcpfvalues", 20000000);
		max = GlobalOptions.getInstance().getInteger("lazyeval_maxpercpf", 2000000);
	}
	/*! get the maximum number of units available for grabbing
	 * \return the number of units (rep. num of cpf cells)
	 */
	public int getMax()
	{
		if (max < units) return max;
		return units;
	}
	/*! grab du number of units
	 * \param du
	 */
	public void grab(int du)
	{
		units -= du;
	}
	/*! grab du number of units
	 * \param du
	 */
	public void give(int du)
	{
		units += du;
	}
}