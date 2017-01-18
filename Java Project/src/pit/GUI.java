package pit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends Working3 implements ActionListener{
	JFrame senderFrame,receiverFrame;
	int imageNo=-1,k,r=-1,j=0;
	ImageIcon senderImage,receivedImage,progressionIcon;
	ImageIcon[] thumbnail,Rthumbnail;
	ArrayList<String> pathName = new ArrayList<String>();
	JPanel buttonPanel,imagePanel,detailsPanel,RdetailsPanel,RimagePanel,RButtonPanel,metricPanel;
	JLabel[] imageLabel,RimageLabel;
	JLabel receiverImageLabel,progressionLabel;
	JLabel senderImageLabel;
	JLabel imageName,imageSize,imageResolution,imageType;
	JLabel RimageName,RimageSize,RimageResolution,RimageType;
	Image image,imageR,progressionImage;
	JButton sendButton,loadButton;
	JButton stopButton,nextButton,saveButton;
	JScrollPane scrollbar,Rscrollbar;
	JFileChooser fileChooser;
	Timer timer;
	public static void main(String args[]){
		GUI gui = new GUI();
		gui.setGUI();
	}
	public void setGUI(){
		senderFrame = new JFrame("SENDER"); //frame created
		imagePanel = new JPanel();  //Panel created
		imagePanel.setLayout(new FlowLayout()); 
		thumbnail = new ImageIcon[50];  //array of imageIcons created
		imageLabel = new JLabel[50]; //array of label created
		detailsPanel = new JPanel();

		detailsPanel.setLayout(new BorderLayout());
		detailsPanel.setPreferredSize(null);
		
		imageName = new JLabel();
		imageName.setPreferredSize(new Dimension(256,10));
		imageSize = new JLabel();
		imageResolution = new JLabel();
		imageType = new JLabel();
				
		
		progressionLabel = new JLabel();
	
		detailsPanel.add(progressionLabel,BorderLayout.CENTER);
		
		metricPanel = new JPanel();
		metricPanel.setLayout(new BoxLayout(metricPanel,BoxLayout.Y_AXIS));
		
		metricPanel.add(imageName);
		metricPanel.add(imageSize);
		metricPanel.add(imageResolution);
		metricPanel.add(imageType);
		
		detailsPanel.add(metricPanel,BorderLayout.SOUTH);
		
		senderFrame.getContentPane().add(BorderLayout.EAST,detailsPanel);
		
		imageName.setText("Name : ");
		imageSize.setText("Size : ");
		imageResolution.setText("Resolution : ");
		imageType.setText("Type : ");
		
		senderImageLabel = new JLabel();
		senderFrame.getContentPane().add(BorderLayout.WEST,senderImageLabel);
		
		
		buttonPanel = new JPanel();
		/*--------------Sender Button---------------*/
		sendButton = new JButton("Send"); 
		sendButton.addActionListener(this);
		sendButton.setEnabled(false);
		loadButton = new JButton("Load");
		loadButton.addActionListener(this);
		buttonPanel.add(sendButton);
		buttonPanel.add(loadButton);
		senderFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		
		
		/*--------------Receiver Buttons--------------*/
		stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		nextButton = new JButton("Start");
		nextButton.addActionListener(this);
		nextButton.setEnabled(false);
		saveButton = new JButton("Select");
		saveButton.addActionListener(this);
		saveButton.setEnabled(false);
		
		scrollbar = new JScrollPane(imagePanel);
		scrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		senderFrame.getContentPane().add(BorderLayout.NORTH,scrollbar);
		
		
		senderFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		senderFrame.setSize(750,730);
		senderFrame.setVisible(true);
		/*-----------------RECEIVER-----------------*/
		
		receiverFrame = new JFrame("RECEIVER");
		RButtonPanel = new JPanel();
		RButtonPanel.add(stopButton);
		RButtonPanel.add(nextButton);
		RButtonPanel.add(saveButton);
		receiverFrame.getContentPane().add(BorderLayout.SOUTH,RButtonPanel);
		
		RdetailsPanel = new JPanel();
		
		RimageName = new JLabel();
		RimageType = new JLabel();
		RimageSize = new JLabel();
		RimageResolution = new JLabel();
		
		RdetailsPanel.add(RimageName);
		RdetailsPanel.add(RimageSize);
		RdetailsPanel.add(RimageResolution);
		RdetailsPanel.add(RimageType);
		receiverFrame.getContentPane().add(BorderLayout.NORTH,RdetailsPanel);
		
		RimageName.setText("Name : ");
		RimageSize.setText("Size : ");
		RimageResolution.setText("Resolution : ");
		RimageType.setText("Type : ");
		
		Rthumbnail = new ImageIcon[50];
		RimageLabel = new JLabel[50];
		RimagePanel = new JPanel();
		RimagePanel.setLayout(new BoxLayout(RimagePanel,BoxLayout.Y_AXIS));
		receiverFrame.getContentPane().add(BorderLayout.EAST,RimagePanel);
		
		receiverImageLabel = new JLabel();
		receiverFrame.getContentPane().add(BorderLayout.WEST,receiverImageLabel);
		
		receiverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		receiverFrame.setLocation(750, 0);
		receiverFrame.setSize(615,730);
		receiverFrame.setVisible(true);
		
		Handler handler = new Handler();
		timer = new Timer(500,handler);
	}
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("No :"+imageNo);
		System.out.println("Listening : "+arg0.getActionCommand());
		int j=0;
		
		if(arg0.getActionCommand().equals("Load")){
			fileChooser = new JFileChooser("G:\\PRO-gaams\\HeadFirstSpace\\Finale");
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG, PNG & BMP", "jpg", "png","bmp");
		    fileChooser.setFileFilter(filter);
		    int returnVal = fileChooser.showOpenDialog(senderFrame);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	pathName.add(fileChooser.getSelectedFile().getName());
		    	System.out.println("Pathname = "+pathName.get(pathName.size()-1));
		    
		    	try {
					image = ImageIO.read(new File(pathName.get(pathName.size()-1))).getScaledInstance(64,64 , BufferedImage.SCALE_SMOOTH);
					thumbnail[j] = new ImageIcon(image);
				}catch (IOException e){
					e.printStackTrace();
				}
				imageLabel[j] = new JLabel(thumbnail[j],JLabel.CENTER);
				imagePanel.add(imageLabel[j++]);
			    
				if(!sendButton.isEnabled()){
			    	sendButton.setText("Send");
			    	sendButton.setEnabled(true);
			    }
				nextButton.setEnabled(false);
				senderFrame.setVisible(true);
				
		    }
		    
		    else if(returnVal == JFileChooser.CANCEL_OPTION){
		    	System.out.println("Image Cancelled");
		    	if(imageNo==pathName.size()){
		    		sendButton.setEnabled(false);
		    	}
		    }
			
		    return;
		}
		else if(arg0.getActionCommand().equals("Send")){
			nextButton.setEnabled(true);
			if(imageNo!=-1){
				nextButton.setText("Next");
			}
			sendButton.setText("Sent");
			sendButton.setEnabled(false);
			saveButton.setEnabled(false);
			return;
		}
		else if(arg0.getActionCommand().equals("Stop")){
			timer.stop();
			saveButton.setEnabled(true);
			stopButton.setEnabled(false);
			if(!loadButton.isEnabled()){
				loadButton.setEnabled(true);
			}
			imageNo++;
			if(imageNo==pathName.size()){
				nextButton.setText("Done");
				nextButton.setEnabled(false);
				imageNo--;
				return;
			}
			imageNo--;
			return;
		}
		else if(arg0.getActionCommand().equals("Start") || arg0.getActionCommand().equals("Next")){
			nextButton.setText("Next");
			imageNo = imageNo+1;
			stopButton.setEnabled(true);
			saveButton.setEnabled(false);
			loadButton.setEnabled(false);
		
			
			if(timer.isRunning()){
				timer.stop();
			}
			System.out.println("next no :"+imageNo);
			if(imageNo+1==pathName.size()){
				nextButton.setText("Done");
				nextButton.setEnabled(false);
			}
			
			System.out.println("---------------");
		}
		else if(arg0.getActionCommand().equals("Select")){
			r++;
			imageR = imageR.getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
			Rthumbnail[r] = new ImageIcon(imageR);
			RimageLabel[r] = new JLabel(Rthumbnail[r]);
			RimagePanel.add(RimageLabel[r]);
			if(r==0){
				Rscrollbar = new JScrollPane(RimagePanel);
				Rscrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				Rscrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				receiverFrame.getContentPane().add(BorderLayout.EAST,Rscrollbar);
			}
			saveButton.setEnabled(false);
			receiverFrame.setVisible(true);
			loadButton.setEnabled(true);
			if(imageNo+1==pathName.size()){
				nextButton.setText("Done");
				nextButton.setEnabled(false);
			}
			return;
		}
		
		try {
			image = ImageIO.read(new File(pathName.get(imageNo))).getScaledInstance(468,468 , BufferedImage.SCALE_SMOOTH);
		}catch (IOException e) {
			e.printStackTrace();
		}
		senderImage = new ImageIcon(image);
		senderImageLabel.setIcon(senderImage);
		
		ImageToMatrix(getImage(pathName.get(imageNo)));
		
		Compress(); 
		
		/*----------Setting Details-----------*/
		k=senderMatrix.length-1;
		k=k-2;
		imageName.setText("Name : "+ pathName.get(imageNo));
		imageSize.setText("Size : "+originalHeight*originalWidth*3/(8*1024.0)+" Kb");
		imageResolution.setText("Resolution : "+originalHeight + " X " + originalWidth );
		imageType.setText("Type : "+pathName.get(imageNo).substring(pathName.get(imageNo).indexOf(".")+1).toUpperCase());
		
		//Receiver
		RimageName.setText("Name :\t"+pathName.get(imageNo));
		RimageSize.setText("Size :\t"+Math.pow(senderMatrix[k].redMatrix.length,2)*3/(8*1024.0)+" Kb");
		RimageResolution.setText("Resolution :\t"+senderMatrix[k].redMatrix.length + " X " + senderMatrix[k].redMatrix[0].length );
		RimageType.setText("Type :\t"+pathName.get(imageNo).substring(pathName.get(imageNo).indexOf(".")+1).toUpperCase());
		
		imageR=Decompress(k);
		imageR=imageR.getScaledInstance(512, 512, BufferedImage.SCALE_SMOOTH);
		receivedImage = new ImageIcon(imageR);
		receiverImageLabel.setIcon(receivedImage);
		
		
		BufferedImage img  = (BufferedImage)matrixToImage(senderMatrix[k]);
		progressionImage = img.getScaledInstance(img.getHeight()/4, img.getWidth()/4, BufferedImage.SCALE_SMOOTH);
		progressionIcon = new ImageIcon(progressionImage);
		progressionLabel.setIcon(progressionIcon);
		senderFrame.setVisible(true);
		
		timer.start();
	}
	class Handler implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("image: "+imageNo+"  matrix:"+k );
			k-=1;

			RimageSize.setText("Size : "+Math.pow(senderMatrix[k].redMatrix.length,2)*3/(8*1024.0)+" Kb");
			RimageResolution.setText("Resolution : "+senderMatrix[k].redMatrix.length + " X " + senderMatrix[k].redMatrix[0].length );
			
			BufferedImage img  = (BufferedImage)matrixToImage(senderMatrix[k]);
			progressionImage = img.getScaledInstance(img.getHeight()/4, img.getWidth()/4, BufferedImage.SCALE_SMOOTH);
			progressionIcon = new ImageIcon(progressionImage);
			progressionLabel.setIcon(progressionIcon);
			
			imageR=Decompress(k);
			imageR=imageR.getScaledInstance(512, 512, BufferedImage.SCALE_SMOOTH);
			receivedImage = new ImageIcon(imageR);
			receiverImageLabel.setIcon(receivedImage);
			receiverFrame.getContentPane().add(BorderLayout.WEST,receiverImageLabel);
			receiverFrame.setVisible(true);
			if(k==0){
				timer.stop();
				stopButton.setEnabled(false);
				saveButton.setEnabled(true);
				loadButton.setEnabled(true);
			}
		}
	}
	
	
}
