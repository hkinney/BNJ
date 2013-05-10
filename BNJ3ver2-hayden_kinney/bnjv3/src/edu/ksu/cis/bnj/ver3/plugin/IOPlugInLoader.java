package edu.ksu.cis.bnj.ver3.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import edu.ksu.cis.bnj.ver3.streams.Exporter;
import edu.ksu.cis.bnj.ver3.streams.Importer;



/*!
 * \file IOPlugInLoader.java
 * \author Jeffrey M. Barber
 */
public class IOPlugInLoader
{
	private static IOPlugInLoader instance = new IOPlugInLoader();
	
	private ArrayList _importers;
	private ArrayList _exporters;

	public static IOPlugInLoader getInstance()
	{
		return instance;
	}
	
	public ArrayList getImporters()
	{
		return _importers;
	}
	public ArrayList getExporters()
	{
		return _exporters;
	}
	
	private IOPlugin getIOPlugin(String jarFile) throws Exception 
	{
		ClassLoader URLCL = new URLClassLoader(new URL[] { new File(jarFile).toURL() });
		return (IOPlugin) URLCL.loadClass("PlugIn").newInstance();
	}
	
	private Importer getImporter(String jarFile, String className) throws Exception 
	{
		ClassLoader URLCL = new URLClassLoader(new URL[] { new File(jarFile).toURL() });
		return (Importer) URLCL.loadClass(className).newInstance();
	}
	private Exporter getExporter(String jarFile, String className) throws Exception 
	{
		ClassLoader URLCL = new URLClassLoader(new URL[] { new File(jarFile).toURL() });
		return (Exporter) URLCL.loadClass(className).newInstance();
	}
	
	public String GetExt(String filename)
	{
		int k = filename.lastIndexOf(".");
		String ext = filename.substring(k);		
		return ext;
	}
	
	public Importer GetImporterByExt(String ext)
	{
		for(Iterator it = _importers.iterator(); it.hasNext(); )
		{
			Importer IMP = (Importer) it.next();
			if(IMP.getExt().indexOf(ext) > 0)
			{
				return IMP;
			}
		}
		return null;
	}
	public Exporter GetExportersByExt(String ext)
	{
		for(Iterator it = _exporters.iterator(); it.hasNext(); )
		{
			Exporter EXP = (Exporter) it.next();
			if(EXP.getExt().indexOf(ext) > 0)
			{
				return EXP;
			}
		}
		return null;
	}
	
	public void loadPlugin(String jarFile)
	{
		try
		{
			IOPlugin iop = getIOPlugin(jarFile);
			if( iop.getType() == 0 )
			{
				Importer imp = getImporter(jarFile, iop.getClassName(0));
				_importers.add(imp);
				System.out.println("loaded bn-importer for: " + imp.getExt());
			}
			if( iop.getType() == 1)
			{
				Exporter exp = getExporter(jarFile, iop.getClassName(1));
				_exporters.add(exp);
				System.out.println("loaded bn-exporter for: " + exp.getExt());
			}
			if( iop.getType() == 2)
			{
				Importer imp = getImporter(jarFile, iop.getClassName(0));
				_importers.add(imp);
				System.out.println("loaded bn-importer: " + imp.getExt());
				
				Exporter exp = getExporter(jarFile, iop.getClassName(1));
				_exporters.add(exp);
				System.out.println("loaded bn-exporter for: " + exp.getExt());
			}
		} catch (Exception e)
		{
			
		}
	}
	
	public IOPlugInLoader()
	{
		_importers = new ArrayList();
		_exporters = new ArrayList();
		try
		{
			File F = new File("./plugins");
			File[] _Files = F.listFiles();
			for(int i = 0; i < _Files.length; i++)
			{
				loadPlugin("./plugins/" + _Files[i].getName());
			}
			
			/*
			IOPlugin hugin = getIOPlugin("../hugin_import/");
			Importer IMP = getImporter("../hugin_import/", hugin.getClassName(0));
			Converter_xmlbif xcml = new Converter_xmlbif();
			xcml.save(new FileOutputStream("TestTest.xml"));
			IMP.load(new FileInputStream("Barley.hugin"),xcml);
			System.out.println("worked?");
			*/
			
		} catch (Exception e)
		{
			System.out.println("FAILED" + e.getMessage() + " " + e.getStackTrace()[0].getClassName() + e.getStackTrace()[0].getLineNumber());
		}
		// scan
	}
	
	
}
