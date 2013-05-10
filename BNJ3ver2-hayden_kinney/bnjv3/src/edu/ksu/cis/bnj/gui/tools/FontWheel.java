package edu.ksu.cis.bnj.gui.tools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import edu.ksu.cis.util.GlobalOptions;
public class FontWheel
{
	public Font	CPF_Body;
	public Font	Node_Body;
	public Font	RoadMap;
	public Font NodeOrder;
	public Font VisMarking;
	public Font CodePageActive;
	public Font CodePageInactive;
	public Font VisHeader;
	public Font ColorLegend;
	
	public void init(Display disp)
	{
		GlobalOptions GO = GlobalOptions.getInstance();
		//CPF_Header = new Font(disp, "Arial", 12, SWT.NORMAL);
		CPF_Body = new Font(disp, GO.getString("font_cpf", "Verdana"), GO.getInteger("fontsize_cpf", 9), SWT.NORMAL);
		Node_Body = new Font(disp, GO.getString("font_node", "Verdana"), GO.getInteger("fontsize_node", 10), SWT.NORMAL);
		RoadMap = new Font(disp, GO.getString("font_roadmap", "Verdana"), GO.getInteger("fontsize_roadmap", 8),	SWT.NORMAL);
		
		NodeOrder = new Font(disp, GO.getString("font_nodeorder", "Times New Roman Unicode"), GO.getInteger("fontsize_nodeorder", 8),	SWT.NORMAL);
		VisMarking = new Font(disp, GO.getString("font_marking", "Times New Roman Unicode"), GO.getInteger("fontsize_marking", 8),	SWT.NORMAL);
		CodePageActive = new Font(disp, GO.getString("font_codepage_active", "Times New Roman Unicode"), GO.getInteger("fontsize_codepage_active", 8),	SWT.NORMAL);
		CodePageInactive = new Font(disp, GO.getString("font_codepage_inactive", "Times New Roman Unicode"), GO.getInteger("fontsize_codepage_inactive", 8),	SWT.NORMAL);
		VisHeader = new Font(disp, GO.getString("font_vis_header", "Times New Roman Unicode"), GO.getInteger("fontsize_vis_header", 16),	SWT.NORMAL);
		
		ColorLegend = new Font(disp, GO.getString("font_vis_header", "Times New Roman Unicode"), GO.getInteger("fontsize_vis_colorlegend", 10),	SWT.NORMAL);
	}
	public void clean()
	{
		ColorLegend.dispose();
		CPF_Body.dispose();
		Node_Body.dispose();
		RoadMap.dispose();
		NodeOrder.dispose();
		VisMarking.dispose();
		CodePageActive.dispose();
		CodePageInactive.dispose();
		VisHeader.dispose();
	}
}