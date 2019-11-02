package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Gui extends JPanel{
//ahmed yasser
	//hossam el kordi
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String name = "Paint";
	private static JFrame frame;
	private JPanel btnPanel;
	private ButtonGroup bg;
	private ArrayList<JToggleButton> btnArray;
	private JToggleButton selectBtn;
	private JToggleButton brushBtn;
	private JToggleButton deleteBtn;
	private JFileChooser fileChooser;
	private JComboBox<String> suppotedClsBox;
	private JToggleButton drawLoadedBtn;
	private char shapeChar = ' ';
	private boolean brush;
	private boolean select;
	private boolean delete;
	private Color fillColor;
	private Color strokeColor;
	private Controller control;
	private boolean moveFlag =false;
	
	private int x2, y2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Gui();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		bg = new ButtonGroup();
		btnArray = new ArrayList<JToggleButton>();
		control = new Controller();
		setFrame(name);
		setMenuBar();
		setBtnPanel();
		setDrawingCanvas();
	}
	
	public void setFrame(String name) {
		frame = new JFrame(name);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Icons/paint.png")));
		frame.setSize(1080, 720);
		frame.setLocationRelativeTo(null); // to be relative to the center of the screen
		frame.setExtendedState(Frame.MAXIMIZED_BOTH); // full screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
				
	}
	
	private void setMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(SystemColor.controlShadow);
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFileChooser("Pick a directory", "");
				if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					control.keepOnDisk(fileChooser.getSelectedFile().getAbsolutePath());
					return;
				}
				JOptionPane.showMessageDialog(frame, "File already exists!");
			}
		});
		mntmSave.setIcon(new ImageIcon(Gui.class.getResource("/Icons/save.png")));
		mnFile.add(mntmSave);
		
		JMenuItem mntmOpen = new JMenuItem("open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFileChooser("Choose a file", "xml,jason");
				if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					control.getFromDisk(fileChooser.getSelectedFile().getAbsolutePath());
					bg.clearSelection();
					shapeChar = ' ';
					update(getPanelGraphics());

				}
			}
		});
		mntmOpen.setIcon(new ImageIcon(Gui.class.getResource("/Icons/load.png")));
		mnFile.add(mntmOpen);
	}
	
	public void setBtnPanel() {
		btnPanel = new JPanel();
		btnPanel.setBackground(SystemColor.activeCaption);
		btnPanel.setBounds(0, 0, 1362, 105);
	    frame.getContentPane().add(btnPanel);
	    btnPanel.setLayout(null);
	    /*
	     * Setting each group of buttons together(drawing, colors, selection)	    	    
	     */
	    setShapesBtns();
	    setSelectBtn();
	    setFillColorBtn();
	    setStrokeColorBtn();
	    setBrushBtn();
	    setUndoBtn();
	    setRedoBtn();
	    setDeleteBtn();
	    setClsLoadingStuff();
	}
	
	private void setShapesBtns() {
		JLabel lblShapes = new JLabel("Shapes");
	    lblShapes.setHorizontalAlignment(SwingConstants.CENTER);
	    lblShapes.setBounds(10, 86, 139, 14);
	    btnPanel.add(lblShapes);
	    
		
	    /*
	     * Every shape drawing button has the same functionality:
	     * 1- if it's already selected (shape and shapeChar are set to a certain object and character depending on the selected button) 
	     *    so we clear all selections of the group and set shape and shapeChar to their initial values
	     * 2- if it isn't selected we set shape and shapeChar to their correct values and remove the selection of any other button 
	     *    not in the group button (brush and select)   
	     */
	    JToggleButton lineBtn = new JToggleButton("");
	    lineBtn.setToolTipText("Line");
	    lineBtn.setName("lineBtn");
	    lineBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/line.png")));
	    lineBtn.setBounds(10, 11, 40, 30);
	    btnPanel.add(lineBtn);
	    bg.add(lineBtn);
	    btnArray.add(lineBtn);
	    bg.add(drawLoadedBtn);
	    
	    JToggleButton rectBtn = new JToggleButton("");
	    rectBtn.setToolTipText("Rectangle");
	    rectBtn.setName("rectBtn");
	    rectBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/rect.png")));
	    rectBtn.setBounds(59, 11, 40, 30);
	    btnPanel.add(rectBtn);
	    bg.add(rectBtn);
	    btnArray.add(rectBtn);
	    
	    JToggleButton sqrBtn = new JToggleButton("");
	    sqrBtn.setToolTipText("Square");
	    sqrBtn.setName("sqrBtn");
	    sqrBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/square.png")));
	    sqrBtn.setBounds(109, 11, 40, 30);
	    btnPanel.add(sqrBtn);
	    bg.add(sqrBtn);
	    btnArray.add(sqrBtn);
	    
	    JToggleButton ellipseBtn = new JToggleButton("");
	    ellipseBtn.setToolTipText("Ellipse");
	    ellipseBtn.setName("ellipseBtn");
	    ellipseBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/ellipse.png")));
	    ellipseBtn.setBounds(10, 52, 40, 30);
	    btnPanel.add(ellipseBtn);
	    bg.add(ellipseBtn);
	    btnArray.add(ellipseBtn);
	    
	    JToggleButton circleBtn = new JToggleButton("");
	    circleBtn.setToolTipText("Circle");
	    circleBtn.setName("circleBtn");
	    circleBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/circle.png")));
	    circleBtn.setBounds(59, 52, 40, 30);
	    btnPanel.add(circleBtn);
	    bg.add(circleBtn);
	    btnArray.add(circleBtn);
	    
	    JToggleButton triangleBtn = new JToggleButton("");
	    triangleBtn.setToolTipText("Triangle");
	    triangleBtn.setName("triangleBtn");
	    triangleBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/triangle.png")));
	    triangleBtn.setBounds(109, 52, 40, 30);
	    btnPanel.add(triangleBtn);
	    bg.add(triangleBtn);
	    btnArray.add(triangleBtn);
	    
	    for (int i = 0; i < btnArray.size(); i++) {
	    	JToggleButton tb = btnArray.get(i);
	    	tb.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			if(tb.isSelected()) {
	    				if(getShapeChar(tb.getName()) == shapeChar) {
	    					bg.clearSelection();
	    					shapeChar = ' ';
	    				}else {
	    					shapeChar = getShapeChar(tb.getName());
	    					selectBtn.setSelected(false);
							brushBtn.setSelected(false);
							deleteBtn.setSelected(false);
							brush = false;
							select = false;
			    			delete = false;
	    				}
	    			}
	    		}
	    	});
	    }
	    
	    setFreeDrawingBtn();
	}
	
	public void setFreeDrawingBtn() {
		JToggleButton freeDrawBtn = new JToggleButton("");
		freeDrawBtn.setName("freeDrawBtn");
		freeDrawBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(freeDrawBtn.isSelected()) {
	    			if(getShapeChar(freeDrawBtn.getName()) == shapeChar) {
	    				bg.clearSelection();
	    				shapeChar = ' ';
	    			}else {
	    				shapeChar = getShapeChar(freeDrawBtn.getName());
						selectBtn.setSelected(false);
						brushBtn.setSelected(false);
						deleteBtn.setSelected(false);
						brush = false;
						select = false;
		    			delete = false;
	    			}
	    		}
	    	}
	    });
		freeDrawBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/freeDrawing.png")));
		freeDrawBtn.setBounds(159, 11, 50, 71);
	    btnPanel.add(freeDrawBtn);
	    bg.add(freeDrawBtn);
	    
	    JLabel lblFreeDrawing = new JLabel("Free Drawing");
	    lblFreeDrawing.setHorizontalAlignment(SwingConstants.CENTER);
	    lblFreeDrawing.setBounds(146, 86, 77, 14);
	    btnPanel.add(lblFreeDrawing);
	}
	
	private char getShapeChar(String buttonName) {
		switch(buttonName) {
			case "lineBtn":
				return 'l';
			case "rectBtn":
				return 'r';
			case "sqrBtn":
				return 's';	
			case "ellipseBtn":
				return 'e';
			case "circleBtn":
				return 'c';
			case "triangleBtn":
				return 't';
			case "freeDrawBtn":
				return 'f';
			case"drawLoadedBtn":
				return 'z';
		}
		return ' ';
	}
		
	private void setSelectBtn() {
		JLabel lblSelect = new JLabel("Select");
	    lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
	    lblSelect.setBounds(285, 86, 50, 14);
	    btnPanel.add(lblSelect);
		
	    selectBtn = new JToggleButton("");
	    selectBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(selectBtn.isSelected()) {
					select = true;
					bg.clearSelection();
					shapeChar = ' ';
					brushBtn.setSelected(false);
					brush = false;
					deleteBtn.setSelected(false);
	    			delete = false;
				}else {
					select = false;
				}
	    	}
	    });
	    selectBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/select.png")));
	    selectBtn.setBounds(285, 11, 50, 71);
	    btnPanel.add(selectBtn);
	}
	
	private void setFillColorBtn() {
		fillColor = Color.WHITE;
		JLabel lblFillColor = new JLabel("Fill Color");
	    lblFillColor.setHorizontalAlignment(SwingConstants.CENTER);
	    lblFillColor.setBounds(345, 86, 50, 14);
	    btnPanel.add(lblFillColor);
	    
	    JButton fillColorBtn = new JButton("");
	    fillColorBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fillColor = JColorChooser.showDialog(frame, "Pick a Color", fillColor);
				if(fillColor == null) {
					fillColor = Color.WHITE;
				}
	    	}
	    });
	    fillColorBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/fillColor.png")));
	    fillColorBtn.setBounds(345, 11, 50, 71);
	    btnPanel.add(fillColorBtn);
	}
	
	private void setStrokeColorBtn() {
		strokeColor = Color.BLACK;
		JLabel lblStroksColor = new JLabel("Stroks Color");
	    lblStroksColor.setBounds(402, 86, 58, 14);
	    btnPanel.add(lblStroksColor);
	    
	    JButton strokeColorBtn = new JButton("");
	    strokeColorBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		strokeColor = JColorChooser.showDialog(frame, "Pick a Color", strokeColor);
				if(strokeColor == null) {
					strokeColor = Color.BLACK;
				}
	    	}
	    });
	    strokeColorBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/strokeColor.png")));
	    strokeColorBtn.setBounds(405, 11, 50, 71);
	    btnPanel.add(strokeColorBtn);
	}
	
	public void setBrushBtn() {
		brushBtn = new JToggleButton("");
		brushBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(brushBtn.isSelected()) {
	    			brush = true;
	    			bg.clearSelection();
	    			shapeChar = ' ';
	    			selectBtn.setSelected(false);
	    			select = false;
	    			deleteBtn.setSelected(false);
	    			delete = false;
	    		}else {
	    			brush = false;
	    		}
	    	}
	    });
		brushBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/paintBrush.png")));
		brushBtn.setBounds(225, 11, 50, 71);
	    btnPanel.add(brushBtn);
	    
	    JLabel lblBrush = new JLabel("Brush");
	    lblBrush.setHorizontalAlignment(SwingConstants.CENTER);
	    lblBrush.setBounds(233, 86, 46, 14);
	    btnPanel.add(lblBrush);
	    }
	
	private void setUndoBtn() {
		JButton undoBtn = new JButton("");
		undoBtn.setToolTipText("Undo");
		undoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeChar = ' ';
				bg.clearSelection();
				brush = false;
				brushBtn.setSelected(false);
				select = false;
				selectBtn.setSelected(false);
				deleteBtn.setSelected(false);
    			delete = false;
				control.getPreviousState();
				update(getPanelGraphics());
			}
		});
	    undoBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/undo.png")));
	    undoBtn.setBounds(927, 11, 40, 40);
	    btnPanel.add(undoBtn);
	}
	
	private void setRedoBtn() {
		JButton redoBtn = new JButton("");
		redoBtn.setToolTipText("Redo");
		redoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shapeChar = ' ';
				bg.clearSelection();
				brush = false;
				brushBtn.setSelected(false);
				select = false;
				selectBtn.setSelected(false);
				deleteBtn.setSelected(false);
    			delete = false;
				control.getNextState();
				update(getPanelGraphics());
			}
		});
	    redoBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/redo.png")));
	    redoBtn.setBounds(977, 11, 40, 40);
	    btnPanel.add(redoBtn);
	}
		
	private void setDeleteBtn() {
		deleteBtn = new JToggleButton("");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(deleteBtn.isSelected()) {
					delete = true;
					bg.clearSelection();
	    			shapeChar = ' ';
	    			brushBtn.setSelected(false);
	    			brush = false;
	    			selectBtn.setSelected(false);
	    			select = false;
				}else {
					delete = false;
				}
			}
		});
	    deleteBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/delete.png")));
	    deleteBtn.setBounds(465, 11, 50, 71);
	    btnPanel.add(deleteBtn);
	    
	    JLabel lblDelete = new JLabel("Delete");
	    lblDelete.setHorizontalAlignment(SwingConstants.CENTER);
	    lblDelete.setBounds(465, 86, 46, 14);
	    btnPanel.add(lblDelete);
	}
	
	private void setClsLoadingStuff() {
		JButton clsLoadBtn = new JButton("");
		clsLoadBtn.setToolTipText("Load New Class");
		clsLoadBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/clsLoad.png")));
	    clsLoadBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		setFileChooser("Choose a jar file", "jar");
	    		if(fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
	    			String s = control.addNewShape(fileChooser.getSelectedFile().getAbsolutePath());
	    			if(s != null) {
	    				suppotedClsBox.addItem(s);
	    			}else {
	    				JOptionPane.showMessageDialog(frame, "Shape is not Supported!");
	    			}
//					System.out.println(control.engine.getaddedShapes().size());
//					System.out.println(control.engine.getaddedShapes().get(0).getSimpleName());
//					suppotedClsBox.addItem(control.engine.getaddedShapes().get(0).getSimpleName());
//					System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
//	    			if(control.engine.getaddedShapes().size()!=0) {
//	    				suppotedClsBox.removeAllItems();
//	    				for(int i=0;i<control.engine.getaddedShapes().size();i++){
//							suppotedClsBox.addItem(control.engine.getaddedShapes().get(i).getSimpleName());
//						}
//
//					}
	    		}
	    		
	    	}
	    });
	    clsLoadBtn.setBounds(581, 21, 30, 30);
	    btnPanel.add(clsLoadBtn);
	    
	    suppotedClsBox = new JComboBox<String>();
	    suppotedClsBox.addItemListener(new ItemListener() {
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getStateChange() == 1) {
	    			control.setType(e.getItem().toString());
	    		}
	    	}
	    });
	    suppotedClsBox.addItem("Select a Shape");
	    suppotedClsBox.setBounds(620, 21, 149, 30);
	    btnPanel.add(suppotedClsBox);
	    
	    drawLoadedBtn = new JToggleButton("");
	    drawLoadedBtn.setName("drawLoadedBtn");
	    drawLoadedBtn.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(drawLoadedBtn.isSelected()) {
	    			if(getShapeChar(drawLoadedBtn.getName()) == shapeChar) {
	    				bg.clearSelection();
	    				shapeChar = ' ';
	    			}else {
	    				shapeChar = getShapeChar(drawLoadedBtn.getName());
						selectBtn.setSelected(false);
						brushBtn.setSelected(false);
						deleteBtn.setSelected(false);
						brush = false;
						select = false;
		    			delete = false;
	    			}
	    		}	    		
	    	}
	    });
	    drawLoadedBtn.setToolTipText("Draw Loaded Shape");
	    drawLoadedBtn.setIcon(new ImageIcon(Gui.class.getResource("/Icons/drawLoaded.png")));
	    drawLoadedBtn.setBounds(779, 21, 30, 30);
	    btnPanel.add(drawLoadedBtn);
	}

	public void setDrawingCanvas() {
		this.setBounds(0, 105, btnPanel.getWidth(), frame.getHeight()-105);
	    this.setBackground(Color.WHITE);
	    /*
	     * creating MouseListener and MouseMotionListener object from their parent class MouseAdapter
	     * and adding them to the drawingPanel (this)
	     */
	    MouseListener mouseHandler = new MouseAdapter() {
	    	// mouseClicked is only executed when we're using the brush
	    	public void mouseClicked(MouseEvent e) {
	    		if(brush) {
	    			control.brush(getPanelGraphics(), e.getPoint(), fillColor, strokeColor);
	    			return;
	    		}
	    		if(delete) {
	    			control.delete(e.getPoint());
	    			update(getPanelGraphics());
	    		}
	    	}

	    	public void mousePressed(MouseEvent e) {
				if(select) { // to identify the shape to be moved(or resized)
                     control.setFirstPoint(e.getPoint());
					if(!control.isinsideresizerect(e.getPoint())) control.setSelectedShape(e.getPoint());
					else{
					    control.setFirst(e.getPoint());
					    moveFlag=true;
                    }
					control.shapeLimitAdder(getPanelGraphics());
				}else { // base case : identify the first point of the shape to be drawn
					if(shapeChar != ' ') {
						x2 = e.getX();
						y2 = e.getY();
						control.shapeCreator(shapeChar, e.getX(), e.getY(), fillColor, strokeColor);
					}
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (select) {
					control.setFirstPoint(null); // deleting the point i'm moving a certain shape of it
					control.resizeFinalizer();
					if(control.resizeFlag || control.moving) {
						control.saveChanges();
					}
				}else {
					if(shapeChar != ' ') {
						control.shapeFinisher(shapeChar, x2, y2);
					}
				}
				moveFlag = false;
				control.resizeFlag = false;
				control.moving = false;
			}
	    };
	    
	    MouseMotionListener mouseMotionHandler = new MouseAdapter() {
	    	public void mouseDragged(MouseEvent e) {
				if(select) {
				    if(!moveFlag) {
                        control.move(getPanelGraphics(), e.getPoint());
                    }
				    else{control.ShapeResize(e.getPoint());}

				}else {
					if(shapeChar != ' ') {
						x2 = e.getX();
						y2 = e.getY();
					}
				}
				update(getPanelGraphics());	// to draw while dragging
				control.keepShapes(getPanelGraphics()); // to keep the saved shapes while dragging
			}
	    };
	    
	    this.addMouseListener(mouseHandler); // adding the mouse listener object to th drawing panel
	    this.addMouseMotionListener(mouseMotionHandler); // adding the mouse motion listener object to th drawing panel
	    frame.getContentPane().add(this); // adding the drawing panel (this) to the frame
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(shapeChar != ' ') {
			 control.shapeDrawer(g, x2, y2);// drawing the desired shape
			 control.keepShapes(getPanelGraphics());
		}else {
			control.keepShapes(getPanelGraphics());
		}
	}
	
	public Graphics getPanelGraphics() {
		return this.getGraphics();
	}
	
	private void setFileChooser(String title, String extension) {
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		if(extension.length() == 0) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}else {
			FileNameExtensionFilter filter;
			if(extension.equals("jar")) {
				filter = new FileNameExtensionFilter("Files", "jar");
			}else {
				filter = new FileNameExtensionFilter("Files", "xml", "json");
			}
			fileChooser.setFileFilter(filter);
		}
	}
}
