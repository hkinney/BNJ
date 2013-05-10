package edu.ksu.cis.bnj.gui.tools;

import java.io.FileOutputStream;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.core.BeliefNode;
import edu.ksu.cis.bnj.ver3.core.Discrete;
import edu.ksu.cis.bnj.ver3.core.DiscreteEvidence;
import edu.ksu.cis.bnj.ver3.core.Domain;
import edu.ksu.cis.bnj.ver3.streams.Evidence_Reader;
import edu.ksu.cis.bnj.ver3.streams.Evidence_Writer;
import edu.ksu.cis.bnj.ver3.streams.xml.Converter_xmlevidence;

/*!
 * \file EvidenceRecorder.java
 * \author Jeffrey M. Barber
 */
public class EvidenceRecorder
{
	private BeliefNetwork _Network;
	private Evidence_Writer _writer;
	private Evidence_Reader _storage;
	private int _timeStamp;
	public void Start(BeliefNetwork _bn)
	{
		_Network = _bn;
		_writer = new Evidence_Writer();
		_storage = new Evidence_Reader();
		_writer.BeginCollectSamples(_storage);
		_timeStamp = 0;
		
	}
	
	public void Snap()
	{
		_writer.WriteSample(_Network, _storage, _timeStamp);
	}
	
	public void Randomize()
	{
		BeliefNode[] _nodes = _Network.getNodes();
		for(int i = 0; i < _nodes.length; i++)
		{
			Domain D = _nodes[i].getDomain();
			if(D instanceof Discrete)
			{
				//todo replace with a better random number generate
				int r = ((int)((Math.random() * D.getOrder() * D.getOrder()))) % D.getOrder();
				_nodes[i].setEvidence(new DiscreteEvidence(r));
			}
		}
	}
	
	public void Save(String name)
	{
		try
		{
			_writer.EndCollectSamples(_storage);
			_storage.End();

			// open the file
			FileOutputStream FOS = new FileOutputStream(name);
			// load the format
			Converter_xmlevidence cxmlevidence = new Converter_xmlevidence();
			// select the file to save tp
			cxmlevidence.save(FOS);
			// write it out
			_writer.WriteEvidenceList(_storage.get(0), cxmlevidence);
			
			
/*		
			// open the file
			FileInputStream FIS = new FileInputStream(name);
			// create reader
			Evidence_Reader ER = new Evidence_Reader();
			// set the file & load
			cxmlevidence.load(FIS, ER);
			// read it by doing something with
			// ER.get(0),

			cxmlevidence.save(new FileOutputStream("test2.xml"));
			_writer.WriteEvidenceList(ER.get(0), cxmlevidence);
*/			
			
			// we write write to a file
		} catch (Exception e)
		{
			System.out.println("can't save");
			System.out.println(e.getMessage());
		}
	}
	
	public void Clear()
	{
		BeliefNode[] _nodes = _Network.getNodes();
		for(int i = 0; i < _nodes.length; i++)
		{
			_nodes[i].setEvidence(null);
		}		
	}
}
