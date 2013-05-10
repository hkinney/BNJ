package edu.ksu.cis.bnj.ver3.streams;
import java.io.InputStream;
/**
 * file: Importer.java
 * 
 * @author Jeffrey M. Barber
 */
public interface Importer
{
	/**
	 * load from a stream and a format writer
	 * 
	 * @param stream
	 * @param writer
	 */
	public void load(InputStream stream, OmniFormatV1 writer);
	
	public String getExt();
	public String getDesc();
}