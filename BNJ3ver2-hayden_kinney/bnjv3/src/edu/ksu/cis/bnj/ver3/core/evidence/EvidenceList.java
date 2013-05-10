package edu.ksu.cis.bnj.ver3.core.evidence;
/*!
 * \file EvidenceList.java
 * \author Jeffrey M. Barber
 */
public class EvidenceList
{
	private EvidenceSample[]	_Samples;
	private int					_SampleSize;
	/*! construct an empty evidence list
	 * 
	 */
	public EvidenceList()
	{
		_Samples = new EvidenceSample[2000];
		_SampleSize = 0;
	}
	/*! augment the buffer
	 * \param[in] b
	 */
	private void buffer(int b)
	{
		int newSize = _Samples.length + b;
		EvidenceSample[] newSamples = new EvidenceSample[newSize];
		for (int i = 0; i < _Samples.length; i++)
			newSamples[i] = _Samples[i];
		_Samples = newSamples;
	}
	/*! add a sample to the list
	 * \param[in] ev evidence sample
	 */
	public void add(EvidenceSample ev)
	{
		if (_SampleSize + 2 >= _Samples.length) buffer(1000);
		_Samples[_SampleSize] = ev;
		_Samples[_SampleSize].setLOC(_SampleSize);
		_SampleSize++;
	}
	/*! remove a sample
	 * \param[in] ev evidence sample
	 */
	public void remove(EvidenceSample ev)
	{
		if (ev.loc() < 0) return;
		for (int k = ev.loc(); k < size() - 1; k++)
		{
			_Samples[k] = _Samples[k + 1];
			_Samples[k].setLOC(k);
		}
		_Samples[size() - 1] = null;
		_SampleSize--;
	}
	/*! query on a sample's id
	 * \param[in] k the k'th sample
	 * \return the sample at k
	 */
	public EvidenceSample getSample(int k)
	{
		return _Samples[k];
	}
	/*! process all samples, and cache their discrete values according to this structure
	 * \param[in] ES
	 */
	public void CacheDiscrete(EvidenceStructure ES)
	{
		int N = size();
		for (int k = 0; k < N; k++)
		{
			_Samples[k].createDiscreteCache(ES.getNodes());
		}
	}
	/*! query the number of samples
	 * \return the number of samples
	 */
	public int size()
	{
		return _SampleSize;
	}
}