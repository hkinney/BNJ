package edu.ksu.cis.bnj.gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import edu.ksu.cis.bnj.gui.rendering.RenderNetwork;
import edu.ksu.cis.bnj.gui.tools.EvidenceRecorder;
import edu.ksu.cis.bnj.gui.tools.SelectionContext;
import edu.ksu.cis.bnj.gui.tools.Transformation;
import edu.ksu.cis.bnj.ver3.core.Discrete;
import edu.ksu.cis.bnj.ver3.core.DiscreteEvidence;
import edu.ksu.cis.bnj.ver3.core.Evidence;
import edu.ksu.cis.bnj.ver3.core.values.ValueUnity;
import edu.ksu.cis.bnj.ver3.dynamic.DynamicTyping;
import edu.ksu.cis.bnj.ver3.influence.Solver;
import edu.ksu.cis.bnj.ver3.influence.nrar.VariableElimination;
import edu.ksu.cis.bnj.ver3.influence.simple.SimpleSolve;
public class NetworkRun extends KDDCanvas
{
	RenderNetwork			REN					= null;

	private Solver		_Solve;
	private boolean			layoutCallCenter	= false;

	public void Compile()
	{
		if(REN._bn.getGraph().getNumberOfVertices() > 1)
		{
			
			if (REN._bn.isInfluenceDiagram()) {
				_Solve = new VariableElimination(REN._bn);
				_Solve.solve(REN._bn);
				REN.Switch2Run(_Solve);
			}
				
			else {
			
			
			
			_Solve = new SimpleSolve();
			_Solve.solve(REN._bn);
//			REN.construct(((SimpleSolve)_Solve)._TransformedNetwork);
			REN.Switch2Run(_Solve);
			/*
			_LSR = new LSonline();
			_LSR.run(REN._bn);
			REN.Switch2Run(_LSR);
			*/
			}
		}
		layoutCallCenter = true;
		ourRedraw();
	}
	public void RunAgain()
	{
		if(REN._bn.getGraph().getNumberOfVertices() > 1)
			_Solve.solve(REN._bn);

//		if(REN._bn.getGraph().getNumberOfVertices() > 1)
	//		_LSR.run(REN._bn);
	}	
	private EvidenceRecorder _Recorder = null;
	
	public void mapStart()
	{
		_Recorder = new EvidenceRecorder();
		_Recorder.Start(REN._bn);
	}
	
	public void mapRandom()
	{
		if(_Recorder == null) return;
		_Recorder.Randomize();
		RunAgain();
		ourRedraw();
	}

	public void mapSnapshot()
	{
		if(_Recorder == null) return;
		_Recorder.Snap();
	}
	
	public boolean canSaveEvidence()
	{
		if(_Recorder == null) return false;
		return true;
	}

	public void mapSave(String name)
	{
		if(_Recorder == null) return;
		_Recorder.Save(name);
	}
	
	
	public void mapClearEvidence()
	{
		if(_Recorder == null) return;
		_Recorder.Clear();
		RunAgain();
		ourRedraw();
	}
	
	
	public void SetTransformation(Transformation T)
	{
		_Transform = T;
	}
	
	public void SetNetwork(RenderNetwork ren)
	{
		REN = ren;
	}
	
	public void event_widgetDisposed(DisposeEvent e)
	{
		_Constants.clean();
		if (backbuffer != null) backbuffer.dispose();
		if (back_gc != null) back_gc.dispose();
	}
	public void event_paintControl(PaintEvent e)
	{
		_Transform.setWindow(e.width, e.height);

		if(!super.backbuffer(e)) return;

		// begin rendering on the back plane
		// paint the background color
		setupRenderingContext();

		drawBack(e);
		// draw the tree
		REN.Render(_RenderingContext);
		if (_OnRenderLoopZoom2Fit && REN._network.getNumberOfVertices() > 0)
		{
			_Transform.normalizescale();
			REN.Render(_RenderingContext);
			_Transform.Zoom2Fit(_RenderingContext.Bounds);
			REN.Render(_RenderingContext);
			_Transform.Center(_RenderingContext.Bounds);
			drawBack(e);
			REN.Render(_RenderingContext);
			//		ourRedraw();
		}
		_OnRenderLoopZoom2Fit = false;
		
		if (layoutCallCenter && REN._network.getNumberOfVertices() > 0)
		{
			_Transform.Center(_RenderingContext.Bounds);
			drawBack(e);
			REN.Render(_RenderingContext);
		}
		layoutCallCenter = false;
		// render to primary surface
		e.gc.drawImage(backbuffer, 0, 0);
		SyncSliders();
		drawFinished();

	}
	public void fireLeftClick(int x, int y, boolean shift)
	{
		
		SelectionContext SC = new SelectionContext();
		SC.x = x;
		SC.y = y;
		SC.multi = shift;
		REN.fireSelelection(SC);
		if (SC.RegIdx >= 0)
		{
			if(SC.foundNode == null) return;
			Evidence ev = SC.foundNode.getEvidence();
			if (ev != null)
			{
				if (ev.getEvidenceValue(SC.RegIdx) instanceof ValueUnity)
				{
					SC.foundNode.setEvidence(null);
					RunAgain();
					ourRedraw();
				}
				else
				{
					if (SC.foundNode.getDomain() instanceof Discrete)
					{
						SC.foundNode.setEvidence(new DiscreteEvidence(SC.RegIdx));
						RunAgain();
						ourRedraw();
					}
				}
			}
			else
			{
				if (SC.foundNode.getDomain() instanceof Discrete)
				{
					SC.foundNode.setEvidence(new DiscreteEvidence(SC.RegIdx));
					RunAgain();
					ourRedraw();
				}
			}
			/*
			 * commitCPF(); _spot.setBounds(SC.Container);
			 * beginCPFedit(SC.selectedCPF, SC.RegIdx); resultrendering =
			 * SC.cpfresult;
			 */
		}
		else
		{
			//commitCPF();
		}
		ourRedraw();
	}
	public void event_mouseDown(MouseEvent e)
	{
		translate_mousedown(e);
		if (_DragMode)
		{
			_Click2DragMonitor = _Constants.DragDifferential;
			_DragMode = false;
			ourRedraw();
		}
	}
	public void event_mouseUp(MouseEvent e)
	{
		if (_LeftMouseDown && !_RightMouseDown && _MouseEventFire == 1) fireLeftClick(_XCapture, _YCapture, (e.stateMask & SWT.SHIFT) > 0);
		translate_mouseup(e);
		if (_DragMode)
		{
			ourRedraw();
		}
		_Click2DragMonitor = _Constants.DragDifferential;
		_DragMode = false;
	}
	public void event_mouseDoubleClick(MouseEvent e)
	{}
	public void event_mouseMove(MouseEvent e)
	{
		if(enterDragmode())
		{
			SelectionContext SC = new SelectionContext();
			SC.x = _XCapture;
			SC.y = _YCapture;
			SC.multi = (e.stateMask & SWT.SHIFT) > 0;
			REN.fireSelelection(SC);
			ourRedraw();
		}
		if (_DragMode)
		{
			int dX = e.x - _XCapture;
			int dY = e.y - _YCapture;
			_XCapture = e.x;
			_YCapture = e.y;
			REN.translate(dX, dY, _Transform);
			ourRedraw();
		}
		if (_TranslationOn)
		{
			int dX = e.x - _XCapture;
			int dY = e.y - _YCapture;
			_XCapture = e.x;
			_YCapture = e.y;
			_Transform.translate(dX,dY);
			transformRedraw();
		}
	}
	public void event_focusGained(FocusEvent e)
	{SendStart();mapGainFocus();}
	public void event_focusLost(FocusEvent e)
	{killMessage();mapLostFocus();}
	public void event_controlResized(ControlEvent e)
	{
		ourRedraw();	
	}
	public void event_keyPressed(KeyEvent e)
	{
		keyMovement(e);
	}
	public void event_keyReleased(KeyEvent e)
	{
		invertKeyMovement(e);
	}
	public void event_mouseEnter(MouseEvent e)
	{
	ourRedraw();	
	}
	public void event_mouseExit(MouseEvent e)
	{
		ourRedraw();	
	}
	public void event_mouseHover(MouseEvent e)
	{
		ourRedraw();	
	}
	public NetworkRun(Composite parent, int style)
	{
		//NO_BACKGROUND, NO_REDRAW_RESIZE and NO_MERGE_PAINTS
		//SWT.NO_BACKGROUND
		super(parent, style | SWT.NO_BACKGROUND);
		addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				event_widgetDisposed(e);
			}
		});
		addPaintListener(new PaintListener()
		{
			public void paintControl(PaintEvent e)
			{
				event_paintControl(e);
			}
		});
		addMouseListener(new MouseAdapter()
		{
			public void mouseDown(MouseEvent e)
			{
				event_mouseDown(e);
			}
			public void mouseUp(MouseEvent e)
			{
				event_mouseUp(e);
			}
			public void mouseDoubleClick(MouseEvent e)
			{
				event_mouseDoubleClick(e);
			}
		});
		addMouseMoveListener(new MouseMoveListener()
		{
			public void mouseMove(MouseEvent e)
			{
				event_mouseMove(e);
			}
		});
		addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent e)
			{
				event_focusGained(e);
			}
			public void focusLost(FocusEvent e)
			{
				event_focusLost(e);
			}
		});
		addControlListener(new ControlAdapter()
		{
			public void controlResized(ControlEvent e)
			{
				event_controlResized(e);
			}
		});
		addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				event_keyPressed(e);
			}
			public void keyReleased(KeyEvent e)
			{
				event_keyReleased(e);
			}
		});
		addMouseTrackListener(new MouseTrackAdapter()
		{
			public void mouseEnter(MouseEvent e)
			{
				event_mouseEnter(e);
			}
			public void mouseExit(MouseEvent e)
			{
				event_mouseExit(e);
			}
			public void mouseHover(MouseEvent e)
			{
				event_mouseHover(e);
			}
		});
	}
}