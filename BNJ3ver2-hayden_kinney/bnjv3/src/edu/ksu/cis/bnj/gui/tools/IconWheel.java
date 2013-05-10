package edu.ksu.cis.bnj.gui.tools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
public class IconWheel
{
	public Image	Save;
	public Image	NodeAdd;
	public Image 	DescAdd;
	public Image 	UtilAdd;
	public Image	NodeDel;
	public Image	EdgeAdd;
	public Image	EdgeDel;
	public Image	Center;
	public Image	Zoom2Fit;
	public Image	ZoomIn;
	public Image	ZoomOut;
	public Image	Zoom100;
	public Image    Cutset;

	public Image	VisStop;
	public Image	VisSkipBack;
	public Image	VisBack;
	public Image	VisForward;
	public Image	VisPause;
	public Image	VisSkipForward;
	
	public Image	RunBegin;
	public Image	RunClear;
	public Image	RunSnapshot;
	public Image	RunSave;
	public Image	VisStepForward;
	public Image	VisStepBack;
	
	public Image	Ico16;
	
	public void create(Display disp)
	{
		DescAdd = new Image(disp, "imgs/desc_add.gif");
		UtilAdd = new Image(disp, "imgs/util_add.gif");
		Ico16 = new Image(disp, "imgs/ico16.gif");
		VisStepForward = new Image(disp, "imgs/vis_stepforward.gif");
		VisStepBack = new Image(disp, "imgs/vis_stepback.gif");
		Save = new Image(disp, "imgs/save.gif");
		NodeAdd = new Image(disp, "imgs/node_add.gif");
		NodeDel = new Image(disp, "imgs/node_del.gif");
		EdgeAdd = new Image(disp, "imgs/edge_add.gif");
		EdgeDel = new Image(disp, "imgs/edge_del.gif");
		Center = new Image(disp, "imgs/center.gif");
		Zoom2Fit = new Image(disp, "imgs/zoom2fit.gif");
		ZoomIn = new Image(disp, "imgs/zoomin.gif");
		ZoomOut = new Image(disp, "imgs/zoomout.gif");
		Zoom100 = new Image(disp, "imgs/zoom100.gif");
		Cutset = new Image(disp, "imgs/cutset.gif");
		
		VisStop = new Image(disp, "imgs/vis_stop.gif");
		VisSkipBack = new Image(disp, "imgs/vis_skipback.gif");
		VisBack = new Image(disp, "imgs/vis_back.gif");
		VisForward = new Image(disp, "imgs/vis_forward.gif");
		VisPause = new Image(disp, "imgs/vis_pause.gif");
		VisSkipForward = new Image(disp, "imgs/vis_skipforward.gif");
		
		RunBegin = new Image(disp, "imgs/run_begin.gif");
		RunClear = new Image(disp, "imgs/run_clear.gif");
		RunSnapshot = new Image(disp, "imgs/run_snapshot.gif");
		RunSave = new Image(disp, "imgs/run_save.gif");
		
		
	}
	public void clean()
	{
		Save.dispose();
		NodeAdd.dispose();
		NodeDel.dispose();
		EdgeAdd.dispose();
		EdgeDel.dispose();
		Center.dispose();
		Zoom2Fit.dispose();
		ZoomIn.dispose();
		ZoomOut.dispose();
		Zoom100.dispose();
		Cutset.dispose();
		DescAdd.dispose();
		UtilAdd.dispose();
		
		VisStop.dispose();
		VisSkipBack.dispose();
		VisBack.dispose();
		VisForward.dispose();
		VisPause.dispose();
		VisSkipForward.dispose();
		
		RunBegin.dispose();
		RunClear.dispose();
		RunSnapshot.dispose();
		RunSave.dispose();
		VisStepForward.dispose();
		Ico16.dispose();
	}
}