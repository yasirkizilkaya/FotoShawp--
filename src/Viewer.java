import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

public class Viewer extends JFrame {
	private BufferedImage image;
	private BufferedImage currentImage;
	private JFileChooser imageChooser = new JFileChooser();
	private static Viewer view = new Viewer();
	private final int SCALE = 900;
	private LinkedList<int[]> bounds = new LinkedList<int[]>();

	//TRANSFORMATION OBJECTS
	Brightness bright;
	Sharpen sharp;
	Contrast con;
	Invert inv;
	Sepia sep;
	Dither dit = new Dither();
	Mosaic mos = new Mosaic();
	Blur blurObj = new Blur();
	Saturation saturation = new Saturation();
<<<<<<< HEAD
	BW blackAndWhite = new BW();
	boolean isBW = false;
	JavaCVFaceDetect haarFeatureDetector;
=======
	
>>>>>>> Sepia and Invert
	
	//DISPLAY VARS
	private JFrame frame = new JFrame("FotoShawp--");
	private JMenuBar menuBar = new JMenuBar();
	
	//CENTER PANEL
	private JPanel centerPanel = new JPanel();
	private JLabel imageLabel = new JLabel();

	//SOUTH PANEL
	private JPanel southPanel = new JPanel();
	
	private JSlider sharpness = new JSlider(100,200,100);
	private JLabel sharpLabel = new JLabel("Sharpness->");
	
	private JSlider contrast = new JSlider(0,200);
	private JLabel contrastLabel = new JLabel("Contrast->");
	
	private JSlider brightness = new JSlider(0,200);
	private JLabel brightLabel = new JLabel("Brightness->");

	private JSlider blurLevel = new JSlider(0,1000,0);
	private JLabel blurLabel = new JLabel("Blur->");

	private JSlider satLevel = new JSlider(0,200,100);
	private JLabel satLabel = new JLabel("Saturation->");
	
<<<<<<< HEAD
// HEAD
=======
>>>>>>> Sepia and Invert
	private JLabel xrayLabel = new JLabel("X-Ray->");
	
	private JLabel bwLabel = new JLabel("Black and White->");
	
	private JLabel sepiaLabel = new JLabel("Sepia->");
	
	private JLabel popartLabel = new JLabel("Pop Art->");
	
	private JLabel warpLabel = new JLabel("Warp->");
	
	private JLabel bulgeLabel = new JLabel("Bulge->");
<<<<<<< HEAD

	private JButton blWh = new JButton("Black and White");

=======
	
	private JLabel invertLabel = new JLabel("Invert Colors->");
	
	
>>>>>>> Sepia and Invert
	private JButton faceQ = new JButton("Face Quantization");
	
	private JButton faceBoxes = new JButton("Face Detection Boxes");

	private JButton apply = new JButton("Apply Change");
<<<<<<< HEAD
	
	private JButton reset = new JButton("Reset Image");
=======
	private JButton invert = new JButton("Invert Colors");
	private JButton sepia = new JButton("Sepia");
>>>>>>> Sepia and Invert

	//FILE MENU
	private JMenu file = new JMenu("File");
	private JMenuItem quitMenu = new JMenuItem("Quit");
	private JMenuItem loadMenu = new JMenuItem("Load");
	private JMenuItem saveMenu = new JMenuItem("Save");
	private JMenuItem resetMenu = new JMenuItem("Reset Image");



	//DITHER MENU
	private JMenu dither = new JMenu("Dither");
	private JMenuItem oDither = new JMenuItem("Ordered");
	private JMenuItem rDither = new JMenuItem("Random");


	//FILTERS MENU
	private JMenu filters = new JMenu("Filters");
	private JMenuItem sharpenMenu = new JMenuItem("Sharpen");
	private JMenuItem blurMenu = new JMenuItem("Blur");
	private JMenuItem saturationMenu = new JMenuItem("Saturation");
	private JMenuItem brightMenu = new JMenuItem("Brightness");
	private JMenuItem conMenu = new JMenuItem("Contrast");
	private JMenuItem bwMenu = new JMenuItem("Black and White");
	private JMenuItem sepiaMenu = new JMenuItem("Sepia");
	private JMenuItem xrayMenu = new JMenuItem("X-Ray");
	private JMenuItem popartMenu = new JMenuItem("Pop-Art");
	private JMenuItem bulgeMenu = new JMenuItem("Bulge");
	private JMenuItem warpMenu = new JMenuItem("Warp");
	private JMenuItem invertMenu = new JMenuItem("Invert Color");
	
	//MOSAIC MENU
	private JMenu mosaic = new JMenu("Photo Mosaic");
	private JMenuItem music = new JMenuItem("Music");
	private JMenuItem paints = new JMenuItem("Paintings");
	private JMenuItem movies = new JMenuItem("Movies");
	
	
	//FACE MENU
	private JMenu face = new JMenu("Face Detection");
	private JMenuItem faceQuan = new JMenuItem("Quantization");
	private JMenuItem faceBoxMenu = new JMenuItem("Face Detection Boxes");
	private JMenuItem setBoundsMenu = new JMenuItem("Set Face Bounds");
	

	
	
	//START VIEWER
	public static void main(String args[]){
		view.start();
	}
	//APPLY CHANGES
	public void apply(){
		Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(sImage));
		centerPanel.repaint();
		southPanel.setVisible(false);
		southPanel.removeAll();
		repaint();
		
	}
	//INITIALIZE GUI AND ACTION LISTENERS
	public Viewer(){
		//APPLY MENUBAR
		frame.setJMenuBar(menuBar);
		//FILE MENU
		file.add(loadMenu);
		file.add(saveMenu);
		file.add(resetMenu);
		file.add(quitMenu);
		menuBar.add(file);
	
		//FILTERS MENU
		filters.add(sharpenMenu);
		filters.add(blurMenu);
		filters.add(brightMenu);
		filters.add(saturationMenu);
		filters.add(conMenu);
		filters.add(bwMenu);
		filters.add(sepiaMenu);
		filters.add(xrayMenu);
		filters.add(invertMenu);
		filters.add(popartMenu);
		filters.add(bulgeMenu);
		filters.add(warpMenu);
		menuBar.add(filters);
		
		
		//DITHER MENU
		dither.add(oDither);
		dither.add(rDither);
		menuBar.add(dither);
		
		//MOSAIC MENU
		mosaic.add(music);
		mosaic.add(paints);
		menuBar.add(mosaic);
		
		//FACE MENU
		face.add(faceQuan);
		face.add(faceBoxMenu);
		face.add(setBoundsMenu);
		menuBar.add(face);
		
		filters.enable(false);
		dither.enable(false);
		mosaic.enable(false);
		face.enable(false);
		
		//CENTER PANEL (IMAGE)
		centerPanel.add(imageLabel);

		//STRUCTURE GUI
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(centerPanel , BorderLayout.CENTER);
		frame.getContentPane().add(southPanel , BorderLayout.SOUTH);

		
		//ACTIONLISTENER FOR LOAD / QUIT
		frame.addWindowListener(new WindowQuit());	
		quitMenu.addActionListener(new MenuQuit());
		loadMenu.addActionListener(new Load());
		saveMenu.addActionListener(new Save());
		
		//MENU LISTENERS
		setBoundsMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);
				BufferedImage temp = image;
				haarFeatureDetector = new JavaCVFaceDetect(temp);
				bounds = haarFeatureDetector.setFaceBounds();
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		sharpenMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(sharpLabel);
				southPanel.add(sharpness);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		resetMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(true);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				southPanel.setVisible(false);
				southPanel.removeAll();
				repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				currentImage = image;
		
				sharpness.setValue(0);
				contrast.setValue(100);
				brightness.setValue(100);
				blurLevel.setValue(100);
				satLevel.setValue(100);
				bounds = new LinkedList<int[]>();
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		faceQuan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(faceQ);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
<<<<<<< HEAD
		faceBoxMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(faceBoxes);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		bw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(blWh);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
=======
		
>>>>>>> Sepia and Invert
		
		saturationMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(satLabel);
				southPanel.add(satLevel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		
		blurMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(blurLabel);
				southPanel.add(blurLevel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		brightMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(brightLabel);
				southPanel.add(brightness);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		conMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(contrastLabel);
				southPanel.add(contrast);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
	
		
		rDither.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				currentImage = dit.ditherR(currentImage);
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();			
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		oDither.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				currentImage = dit.ditherO(currentImage);
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();			
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		music.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				currentImage = mos.mosaicIt(currentImage , "music");
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();			
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		
		
		//SLIDER / BUTTON LISTENERS
		brightness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);

				
				update();

				/*
				image = bright.brighten( brightness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
<<<<<<< HEAD
		faceBoxes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				southPanel.setVisible(true);
				BufferedImage temp = image;
				haarFeatureDetector = new JavaCVFaceDetect(temp);
				temp = haarFeatureDetector.detectWBoxes(true);
				Image sImage = temp.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				southPanel.setVisible(false);
				southPanel.removeAll();
				repaint();
				
			}	
		});
		
		
		
		blWh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				southPanel.setVisible(true);
				if(isBW){
					isBW = false;
				}else{
					isBW = true;
				}
				
				update();
				
				/*
				image = bright.brighten( brightness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		
=======
>>>>>>> Sepia and Invert
		satLevel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);
				update();

				/*
				image = bright.brighten( brightness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		sharpness.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);

				update();

				/*
				image = sharp.sharpen(sharpness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		blurLevel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);

				update();

				/*
				image = sharp.sharpen(sharpness.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		
		contrast.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);
				update();
				/*
				image = con.contr(contrast.getValue()/100.f);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				*/
			}
		});
		
		invert.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);
				//update();

				currentImage = inv.invert(currentImage);
				
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
			}
		});
		
		sepia.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent c) {
				southPanel.setVisible(true);
				//update();
				
				currentImage = sep.brownIt(currentImage);
				
				Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
			}
		});
		
		apply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				apply();
			}	
		});

		faceQ.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FaceDetect theFace = new FaceDetect();
				currentImage = theFace.skinQuantize(currentImage);
				apply();
				
			}	
		});
		
		bwMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(bwLabel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		invertMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(invertLabel);
				southPanel.add(invert);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		sepiaMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(sepiaLabel);
				southPanel.add(sepia);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		xrayMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(xrayLabel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		popartMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(popartLabel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		warpMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(warpLabel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
		bulgeMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				southPanel.setVisible(false);
				southPanel.removeAll();
				southPanel.setVisible(true);

				southPanel.add(bulgeLabel);
				southPanel.add(apply);
				//southPanel.repaint();
				frame.getContentPane().add(southPanel , BorderLayout.SOUTH);
				
				EventQueue.invokeLater(new Runnable(){
					public void run(){
						repaint();
					}
				});
				
			}
		});
		
	}
	/*
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(image.getScaledInstance(400, -1, image.SCALE_SMOOTH), 500, 400,this);
		repaint();
	}
	*/
	public void update(){
<<<<<<< HEAD

		currentImage = sharp.sharpen(sharpness.getValue()/100.f, image);
		currentImage = bright.brighten( brightness.getValue()/100.f , currentImage ,bounds);
		currentImage = con.contr(contrast.getValue()/100.f, currentImage ,bounds);
		currentImage = blurObj.blur(currentImage, blurLevel.getValue()/100.0f);
		if(!isBW){
			currentImage = saturation.saturate(currentImage, satLevel.getValue()/100.0f);
		}
		if(isBW){
			currentImage = blackAndWhite.blackWhite(currentImage , bounds);
		}
=======
		//currentImage = 
		currentImage = bright.brighten( brightness.getValue()/100.f , image);
		currentImage = sharp.sharpen(sharpness.getValue()/100.f, currentImage);
		currentImage = con.contr(contrast.getValue()/100.f, currentImage);
		currentImage = blurObj.blur(currentImage, blurLevel.getValue()/100.0f);
		currentImage = saturation.saturate(currentImage, satLevel.getValue()/100.0f);
		//currentImage = inv.invert(currentImage);
//		currentImage = sep.brownIt(currentImage);

		
		
>>>>>>> Sepia and Invert
		Image sImage = currentImage.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
		imageLabel.setIcon(new ImageIcon(sImage));
		centerPanel.repaint();


	}
	
	
	public class Load implements ActionListener{
		public void actionPerformed(ActionEvent event){
			southPanel.setVisible(false);
			southPanel.removeAll();
			southPanel.setVisible(true);
			
			imageChooser.showOpenDialog(null);
			File file = imageChooser.getSelectedFile();
			try{
				image = ImageIO.read(file);
				Image sImage = image.getScaledInstance(SCALE, -1, image.SCALE_SMOOTH);
				imageLabel.setIcon(new ImageIcon(sImage));
				centerPanel.repaint();
				currentImage = image;
				//Initialize Transformation Objects With Loaded Image
				sharp = new Sharpen(image);
				bright = new Brightness(image);
				con = new Contrast(image);
<<<<<<< HEAD

				filters.enable(true);
				dither.enable(true);
				mosaic.enable(true);
				face.enable(true);
				reset.enable(true);
				bounds = new LinkedList<int[]>();

=======
				inv = new Invert(image);
				sep = new Sepia(image);
				
>>>>>>> Sepia and Invert
				
			} catch (IOException e) {
				System.out.println(e);
			}
			
			
		}
	}
	public class Save implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String fileName = new String("");
			String dir = new String("");
		    JFileChooser c = new JFileChooser();
		      // Demonstrate "Save" dialog:
		      int rVal = c.showSaveDialog(Viewer.this);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        fileName = (c.getSelectedFile().getName());
		        dir = (c.getCurrentDirectory().toString());
		      }
		      if (rVal == JFileChooser.CANCEL_OPTION) {
		       
		      }
		      System.out.println(dir);
		      try {
		    	    BufferedImage bi = currentImage;
		    	    File outputfile = new File(fileName + ".png");
		    	    ImageIO.write(bi, "png", outputfile);
		    	} catch (IOException e) {
		    	   
		    	}
		} 
	}
	public class MenuQuit implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.exit(0);
		}
	}
	
	public class WindowQuit extends WindowAdapter{
		public void windowClosing(WindowEvent event){
			System.exit(0);
		}
	}
	
	public void start(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setSize(1000,700);

		frame.setVisible(true);
	}
	
}
