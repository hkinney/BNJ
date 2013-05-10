package edu.ksu.cis.util.graph.visualization;
/*!
 * \file Annotation.java
 * \author Jeffrey M. Barber
 */
public class Annotation
{
	String	oldAnnotation;
	String	newAnnotation;
	/*! create an annotation
	 * \param[in] note
	 */
	public Annotation(String note)
	{
		newAnnotation = note;
	}
	/*! apply this
	 * \param[in] note
	 * \return
	 */
	public String apply(String note)
	{
		oldAnnotation = note;
		return newAnnotation;
	}
	/*! apply the inverse (change back)
	 * \return the old annotation
	 */
	public String applyInverse()
	{
		return oldAnnotation;
	}
}