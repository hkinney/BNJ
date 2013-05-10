package edu.ksu.cis.bnj.gui.dialogs;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
public class AboutDlg
{
	private Text	text;
	protected Shell	shell;
	private Color	backGround;
	private Color	foreGround;
	public static void main(String[] args)
	{
		AboutDlg window = new AboutDlg();
		window.open();
	}
	public String License()
	{
		return "The GNU General Public License (GPL)\nVersion 2, June 1991\nCopyright (C) 1989, 1991 Free Software Foundation, Inc.\n59 Temple Place, Suite 330, Boston, MA 02111-1307 USA\n\nEveryone is permitted to copy and distribute verbatim copies\nof this license document, but changing it is not allowed.\n\nPreamble\n\nThe licenses for most software are designed to take away your freedom\nto share and change it. By contrast, the GNU General Public License\nis intended to guarantee your freedom to share and change free\nsoftware--to make sure the software is free for all its users. This\nGeneral Public License applies to most of the Free Software\nFoundation's software and to any other program whose authors commit\nto using it. (Some other Free Software Foundation software is covered\nby the GNU Library General Public License instead.) You can apply it\nto your programs, too.\n\nWhen we speak of free software, we are referring to freedom, not\nprice. Our General Public Licenses are designed to make sure that you\nhave the freedom to distribute copies of free software (and charge\nfor this service if you wish), that you receive source code or can\nget it if you want it, that you can change the software or use pieces\nof it in new free programs; and that you know you can do these\nthings.\n\nTo protect your rights, we need to make restrictions that forbid\nanyone to deny you these rights or to ask you to surrender the\nrights. These restrictions translate to certain responsibilities for\nyou if you distribute copies of the software, or if you modify it.\n\nFor example, if you distribute copies of such a program, whether\ngratis or for a fee, you must give the recipients all the rights that\nyou have. You must make sure that they, too, receive or can get the\nsource code. And you must show them these terms so they know their\nrights.\n\nWe protect your rights with two steps: (1) copyright the software,\nand (2) offer you this license which gives you legal permission to\ncopy, distribute and/or modify the software.\n\nAlso, for each author's protection and ours, we want to make certain\nthat everyone understands that there is no warranty for this free\nsoftware. If the software is modified by someone else and passed on,\nwe want its recipients to know that what they have is not the\noriginal, so that any problems introduced by others will not reflect\non the original authors' reputations.\n\nFinally, any free program is threatened constantly by software\npatents. We wish to avoid the danger that redistributors of a free\nprogram will individually obtain patent licenses, in effect making\nthe program proprietary. To prevent this, we have made it clear that\nany patent must be licensed for everyone's free use or not licensed\nat all.\n\nThe precise terms and conditions for copying, distribution and\nmodification follow.\n\nTERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION\n\n0. This License applies to any program or other work which contains a\nnotice placed by the copyright holder saying it may be distributed\nunder the terms of this General Public License. The \"Program\", below,\nrefers to any such program or work, and a \"work based on the Program\"\nmeans either the Program or any derivative work under copyright law:\nthat is to say, a work containing the Program or a portion of it,\neither verbatim or with modifications and/or translated into another\nlanguage. (Hereinafter, translation is included without limitation in\nthe term \"modification\".) Each licensee is addressed as \"you\".\n\nActivities other than copying, distribution and modification are not\ncovered by this License; they are outside its scope. The act of\nrunning the Program is not restricted, and the output from the\nProgram is covered only if its contents constitute a work based on\nthe Program (independent of having been made by running the Program).\nWhether that is true depends on what theProgram does.\n\n1. You may copy and distribute verbatim copies of the Program's\nsource code as you receive it, in any medium, provided that you\nconspicuously and appropriately publish on each copy an appropriate\ncopyright notice and disclaimer of warranty; keep intact all the\nnotices that refer to this License and to the absence of any\nwarranty; and give any other recipients of the Program a copy of this\nLicense along with the Program.\n\nYou may charge a fee for the physical act of transferring a copy, and\nyou may at your option offer warranty protection in exchange for a\nfee.\n\n2. You may modify your copy or copies of the Program or any portion\nof it, thus forming a work based on the Program, and copy and\ndistribute such modifications or work under the terms of Section 1\nabove, provided that you also meet all of these conditions:\n\na) You must cause the modified files to carry prominent notices\nstating that you changed the files and the date of any change.\n\nb) You must cause any work that you distribute or publish, that in\nwhole or in part contains or is derived from the Program or any part\nthereof, to be licensed as a whole at no charge to all third parties\nunder the terms of this License.\n\nc) If the modified program normally reads commands interactively when\nrun, you must cause it, when started running for such interactive use\nin the most ordinary way, to print or display an announcement\nincluding an appropriate copyright notice and a notice that there is\nno warranty (or else, saying that you provide a warranty) and that\nusers may redistribute the program under these conditions, and\ntelling the user how to view a copy of this License. (Exception: if\nthe Program itself is interactive but does not normally print such an\nannouncement, your work based on the Program is not required to print\nan announcement.)\n\nThese requirements apply to the modified work as a whole. If\nidentifiable sections of that work are not derived from the Program,\nand can be reasonably considered independent and separate works in\nthemselves, then this License, and its terms, do not apply to those\nsections when you distribute them as separate works. But when you\ndistribute the same sections as part of a whole which is a work based\non the Program, the distribution of the whole must be on the terms of\nthis License, whose permissions for other licensees extend to the\nentire whole, and thus to each and every part regardless of who wrote\nit.\n\nThus, it is not the intent of this section to claim rights or contest\nyour rights to work written entirely by you; rather, the intent is to\nexercise the right to control the distribution of derivative or\ncollective works based on the Program.\n\nIn addition, mere aggregation of another work not based on the\nProgram with the Program (or with a work based on the Program) on a\nvolume of a storage or distribution medium does not bring the other\nwork under the scope of this License.\n\n3. You may copy and distribute the Program (or a work based on it,\nunder Section 2) in object code or executable form under the terms of\nSections 1 and 2 above provided that you also do one of the\nfollowing:\n\na) Accompany it with the complete corresponding machine-readable\nsource code, which must be distributed under the terms of Sections 1\nand 2 above on a medium customarily used for software interchange;\nor,\n\nb) Accompany it with a written offer, valid for at least three years,\nto give any third party, for a charge no more than your cost of\nphysically performing source distribution, a complete\nmachine-readable copy of the corresponding source code, to be\ndistributed under the terms of Sections 1 and 2 above on a medium\ncustomarily used for software interchange; or,\n\nc) Accompany it with the information you received as to the offer to\ndistribute corresponding source code. (This alternative is allowed\nonly for noncommercial distribution and only if you received the\nprogram in object code or executable form with such an offer, in\naccord with Subsection b above.)\n\nThe source code for a work means the preferred form of the work for\nmaking modifications to it. For an executable work, complete source\ncode means all the source code for all modules it contains, plus any\nassociated interface definition files, plus the scripts used to\ncontrol compilation and installation of the executable. However, as a\nspecial exception, the source code distributed need not include\nanything that is normally distributed (in either source or binary\nform) with the major components (compiler, kernel, and so on) of the\noperating system on which the executable runs, unless that component\nitself accompanies the executable.\n\nIf distribution of executable or object code is made by offering\naccess to copy from a designated place, then offering equivalent\naccess to copy the source code from the same place counts as\ndistribution of the source code, even though third parties are not\ncompelled to copy the source along with the object code.\n\n4. You may not copy, modify, sublicense, or distribute the Program\nexcept as expressly provided under this License. Any attempt\notherwise to copy, modify, sublicense or distribute the Program is\nvoid, and will automatically terminate your rights under this\nLicense. However, parties who have received copies, or rights, from\nyou under this License will not have their licenses terminated so\nlong as such parties remain in full compliance.\n\n5. You are not required to accept this License, since you have not\nsigned it. However, nothing else grants you permission to modify or\ndistribute the Program or its derivative works. These actions are\nprohibited by law if you do not accept this License. Therefore, by\nmodifying or distributing the Program (or any work based on the\nProgram), you indicate your acceptance of this License to do so, and\nall its terms and conditions for copying, distributing or modifying\nthe Program or works based on it.\n\n6. Each time you redistribute the Program (or any work based on the\nProgram), the recipient automatically receives a license from the\noriginal licensor to copy, distribute or modify the Program subject\nto these terms and conditions. You may not impose any further\nrestrictions on the recipients' exercise of the rights granted\nherein. You are not responsible for enforcing compliance by third\nparties to this License.\n\n7. If, as a consequence of a court judgment or allegation of patent\ninfringement or for any other reason (not limited to patent issues),\nconditions are imposed on you (whether by court order, agreement or\notherwise) that contradict the conditions of this License, they do\nnot excuse you from the conditions of this License. If you cannot\ndistribute so as to satisfy simultaneously your obligations under\nthis License and any other pertinent obligations, then as a\nconsequence you may not distribute the Program at all. For example,\nif a patent license would not permit royalty-free redistribution of\nthe Program by all those who receive copies directly or indirectly\nthrough you, then the only way you could satisfy both it and this\nLicense would be to refrain entirely from distribution of the\nProgram.\n\nIf any portion of this section is held invalid or unenforceable under\nany particular circumstance, the balance of the section is intended\nto apply and the section as a whole is intended to apply in other\ncircumstances.\n\nIt is not the purpose of this section to induce you to infringe any\npatents or other property right claims or to contest validity of any\nsuch claims; this section has the sole purpose of protecting the\nintegrity of the free software distribution system, which is\nimplemented by public license practices. Many people have made\ngenerous contributions to the wide range of software distributed\nthrough that system in reliance on consistent application of that\nsystem; it is up to the author/donor to decide if he or she is\nwilling to distribute software through any other system and a\nlicensee cannot impose that choice.\n\nThis section is intended to make thoroughly clear what is believed to\nbe a consequence of the rest of this License.\n\n8. If the distribution and/or use of the Program is restricted in\ncertain countries either by patents or by copyrighted interfaces, the\noriginal copyright holder who places the Program under this License\nmay add an explicit geographical distribution limitation excluding\nthose countries, so that distribution is permitted only in or among\ncountries not thus excluded. In such case, this License incorporates\nthe limitation as if written in the body of this License.\n\n9. The Free Software Foundation may publish revised and/or new\nversions of the General Public License from time to time. Such new\nversions will be similar in spirit to the present version, but may\ndiffer in detail to address new problems or concerns.\n\nEach version is given a distinguishing version number. If the Program\nspecifies a version number of this License which applies to it and\n\"any later version\", you have the option of following the terms and\nconditions either of that version or of any later version published\nby the Free Software Foundation. If the Program does not specify a\nversion number of this License, you may choose any version ever\npublished by the Free Software Foundation.\n\n10. If you wish to incorporate parts of the Program into other free\nprograms whose distribution conditions are different, write to the\nauthor to ask for permission. For software which is copyrighted by\nthe Free Software Foundation, write to the Free Software Foundation;\nwe sometimes make exceptions for this. Our decision will be guided by\nthe two goals of preserving the free status of all derivatives of our\nfree software and of promoting the sharing and reuse of software\ngenerally.\n\nNO WARRANTY\n\n11. BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO\nWARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW.\nEXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR\nOTHER PARTIES PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY\nKIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE\nIMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR\nPURPOSE. THE ENTIRE RISK AS TO THE QUALITY AND PERFORMANCE OF THE\nPROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME\nTHE COST OF ALL NECESSARY SERVICING, REPAIR OR CORRECTION.\n\n12. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN\nWRITING WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY\nAND/OR REDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU\nFOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR\nCONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR INABILITY TO USE THE\nPROGRAM (INCLUDING BUT NOT LIMITED TO LOSS OF DATA OR DATA BEING\nRENDERED INACCURATE OR LOSSES SUSTAINED BY YOU OR THIRD PARTIES OR A\nFAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER PROGRAMS), EVEN IF\nSUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE POSSIBILITY OF\nSUCH DAMAGES.\n\nEND OF TERMS AND CONDITIONS\n\n";
	}
	public void open()
	{
		final Display display = new Display();
		backGround = new Color(display, 255, 255, 255);
		foreGround = new Color(display, 0, 0, 128);
		shell = new Shell(SWT.BORDER | SWT.CLOSE | SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL | SWT.TITLE);
		createContents();
		shell.open();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch()) display.sleep();
		}
	}
	public void show(Shell parent)
	{
		backGround = new Color(parent.getDisplay(), 255, 255, 255);
		foreGround = new Color(parent.getDisplay(), 0, 0, 128);
		shell = new Shell(parent, SWT.PRIMARY_MODAL | SWT.BORDER | SWT.CLOSE | SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL
				| SWT.TITLE);
		shell.addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				backGround.dispose();
				foreGround.dispose();
			}
		});
		int w = 440;
		int h = 320;
		shell.setBounds(parent.getBounds().x + parent.getBounds().width / 2 - w / 2, parent.getBounds().y
				+ parent.getBounds().height / 2 - h / 2, w, h);
		createContents();
		shell.open();
	}
	protected void createContents()
	{
		shell.setText("Bayesian Network tools in Java: 3.1");
		{
			final Label label = new Label(shell, SWT.NONE);
			label.setBounds(5, 45, 245, 15);
			label.setText("Presents Bayesian Network tools in Java 3.1");
		}
		{
			final Button button = new Button(shell, SWT.NONE);
			button.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent e)
				{
					shell.close();
				}
			});
			button.setBounds(370, 255, 55, 25);
			button.setText("Ok");
		}
		{
			final Label label = new Label(shell, SWT.NONE);
			label.setBounds(5, 25, 320, 15);
			label.setText("The Laboratory for Knowledge Discovery in Databases (KDD)");
		}
		{
			final Label label = new Label(shell, SWT.NONE);
			label.setBounds(5, 5, 405, 20);
			label.setText("From the Department of Computer Information Science, Kansas State University");
		}
		{
			text = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL);
			text.setForeground(foreGround);
			text.setBackground(backGround);
			text.setBounds(6, 65, 415, 185);
			text.setText(License());
		}
	}
}