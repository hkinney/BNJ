package edu.ksu.cis.bnj.ver3.drivers;

import java.io.FileOutputStream;
import edu.ksu.cis.bnj.ver3.core.BeliefNetwork;
import edu.ksu.cis.bnj.ver3.dynamic.UnRoll;
import edu.ksu.cis.bnj.ver3.plugin.IOPlugInLoader;
import edu.ksu.cis.bnj.ver3.streams.Exporter;
import edu.ksu.cis.bnj.ver3.streams.OmniFormatV1_Writer;
import edu.ksu.cis.util.GlobalOptions;
import edu.ksu.cis.util.driver.Options;
/*!
 * \file DBN.java
 * \author Jeffrey M. Barber
 */
public class DBN
{
	public static void onbn(Options opt, BeliefNetwork bn, String file, int maxtime)
	{
		BeliefNetwork res = UnRoll.execute(bn, maxtime);
		
		try
		{
			IOPlugInLoader pil = IOPlugInLoader.getInstance();
			Exporter EXP = pil.GetExportersByExt(pil.GetExt(file));
			EXP.save(new FileOutputStream(file));
			OmniFormatV1_Writer.Write( res , EXP.getStream1());
			
		} catch (Exception e)
		{
			System.out.println("could not write file: " + file);
		}
	}
	
	public static void exec(Options opt)
	{
		GlobalOptions GO = GlobalOptions.getInstance();
		String f;
		opt.begin();
		
		while ((f = opt.file()) != null)
		{
			BeliefNetwork bn = Options.load(f);
			String outFile = opt.get("out");
			onbn(opt, bn, outFile, opt.getInteger("maxtime", 10));
		}
	}
	
}
