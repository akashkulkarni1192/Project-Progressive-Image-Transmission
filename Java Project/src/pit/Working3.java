package pit;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Working3 {
	int originalHeight,originalWidth;
	BufferedImage image;
	Image compressedImage;
	SampleModel sampleModel;
	double redPixels[][];
    double greenPixels[][];
    double bluePixels[][];
    int imageWidth ,imageHeight;
    Matrix[] senderMatrix ;
	public File getImage(String path){
		File file = new File(path);
		return file;
		
	}
	public void ImageToMatrix(File imagefile){
		try{
			BufferedImage img= ImageIO.read(imagefile);
		    Raster raster=img.getData();
		    sampleModel = raster.getSampleModel();
		    imageWidth=raster.getWidth();
		    imageHeight=raster.getHeight();
		    System.out.println("imageHeight ="+imageHeight+"\t"+"imageWidth ="+imageWidth);
		    originalHeight = imageHeight;
		    originalWidth = imageWidth;
		    int iw=imageWidth,ih=imageHeight;
			iw--;
			iw|=iw>>1;
			iw|=iw>>2;
			iw|=iw>>4;
			iw|=iw>>8;
			iw|=iw>>16;
			iw++;
			
			ih--;
			ih|=ih>>1;
			ih|=ih>>2;
			ih|=ih>>4;
			ih|=ih>>8;
			ih|=ih>>16;
			ih++;
			
			System.out.println("Im width=" + iw);
			System.out.println("Im height=" + ih);
		   
		    redPixels	= new double[ih][iw];
		    greenPixels	= new double[ih][iw];
		    bluePixels	= new double[ih][iw];
		    
		    for(int i=0;i<ih;i++){
				for(int j=0;j<iw;j++){
					if(j>=imageWidth && i<imageHeight){
						redPixels[i][j]=0;
						greenPixels[i][j]=0;
						bluePixels[i][j]=0;
					}else if(i>=imageHeight){
						greenPixels[i][j]=0;
						bluePixels[i][j]=0;
					}
					
				}
			}
		    
		    for (int x=0;x<imageWidth;x++){
		        for(int y=0;y<imageHeight;y++){
		            redPixels[x][y]=raster.getSample(x,y,0);
		            greenPixels[x][y]=raster.getSample(x,y,1);
		            bluePixels[x][y]=raster.getSample(x,y,2);
		        }
		    }
			imageWidth=iw;
			imageHeight=ih;
		}catch (Exception e){
		    e.printStackTrace();
		}
	}
	public void Compress(){
		/*n--;           // 1000 0011 --> 1000 0010
		n |= n >> 1;   // 1000 0010 | 0100 0001 = 1100 0011
		n |= n >> 2;   // 1100 0011 | 0011 0000 = 1111 0011
		n |= n >> 4;   // 1111 0011 | 0000 1111 = 1111 1111
		n |= n >> 8;   // ... (At this point all bits are 1, so further bitwise-or
		n |= n >> 16;  //      operations produce no effect.)
		n++;           // 1111 1111 --> 1 0000 0000
		And indeed, 256 is the next highest power of 2 from 131.
		*/
		
		
		int arrayMatrixSize = (int)(Math.log10(imageWidth)/Math.log10(2));
		senderMatrix = new Matrix[arrayMatrixSize+1];
		senderMatrix[0] = new Matrix(imageHeight,imageWidth);
		senderMatrix[0].input(redPixels,greenPixels,bluePixels);
		int p,q,k,n;
		p=q=k=0;
		while(k<arrayMatrixSize){
			n = senderMatrix[k].redMatrix[0].length;
			Matrix tempMatrix = new Matrix(n/2,n/2);
			p=q=0;
			for(int i=0;i<n;i+=2){
				for(int j=0;j<n;j+=2){
					tempMatrix.redMatrix[p][q] = (senderMatrix[k].redMatrix[i][j] + senderMatrix[k].redMatrix[i][j+1] + senderMatrix[k].redMatrix[i+1][j] + senderMatrix[k].redMatrix[i+1][j+1])/4.0;
					tempMatrix.greenMatrix[p][q] = (senderMatrix[k].greenMatrix[i][j] + senderMatrix[k].greenMatrix[i][j+1] + senderMatrix[k].greenMatrix[i+1][j] + senderMatrix[k].greenMatrix[i+1][j+1])/4.0;
					tempMatrix.blueMatrix[p][q++] = (senderMatrix[k].blueMatrix[i][j] + senderMatrix[k].blueMatrix[i][j+1] + senderMatrix[k].blueMatrix[i+1][j] + senderMatrix[k].blueMatrix[i+1][j+1])/4.0;
				}
				p++;
				q=0;
			
			}
		
			senderMatrix[++k] = new Matrix(n/2,n/2);
			senderMatrix[k]= tempMatrix;
		}
	}
	public Image Decompress(int k){
		int p,q,iCounter,jCounter,n=imageWidth;
		int step;
		Matrix decodedMatrix = new Matrix(imageHeight,imageWidth);
		
			p=q=iCounter=jCounter=0;
			step=n/senderMatrix[k].redMatrix[0].length;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					decodedMatrix.redMatrix[i][j]=senderMatrix[k].redMatrix[p][q];
					decodedMatrix.greenMatrix[i][j]=senderMatrix[k].greenMatrix[p][q];
					decodedMatrix.blueMatrix[i][j]=senderMatrix[k].blueMatrix[p][q];
					
					jCounter++;
					if(jCounter==step){
						q++;
						jCounter=0;
					}
					
				}
				q=0;
				iCounter++;
				if(iCounter==step){
					p++;
					iCounter=0;
				}
			}
			return matrixToImage(decodedMatrix);
	}
	
	
	public void displayImage(Image compressedImage){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    JFrame frame = new JFrame("uff");
	    ImageIcon icon = new ImageIcon(compressedImage);
	    JLabel label = new JLabel(icon);
	    frame.setContentPane(label);
	    frame.setVisible(true);
	    frame.setSize(2024,800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Image matrixToImage(Matrix operationalMatrix){
		WritableRaster raster = Raster.createBandedRaster(DataBuffer.TYPE_BYTE, imageWidth, imageHeight, 3, new Point(0,0));
		for(int i=0;i<operationalMatrix.redMatrix.length;i++){
			for(int j=0;j<operationalMatrix.redMatrix[0].length;j++){
				 raster.setSample(i,j,0,operationalMatrix.redMatrix[i][j]);
				 raster.setSample(i,j,1,operationalMatrix.greenMatrix[i][j]);
				 raster.setSample(i,j,2,operationalMatrix.blueMatrix[i][j]);
			 }
		 }
		 BufferedImage image=new BufferedImage(operationalMatrix.redMatrix.length,operationalMatrix.redMatrix[0].length,BufferedImage.TYPE_INT_RGB);
		 image.setData(raster);
		 File output=new File("check.jpg");
		 try {
			 ImageIO.write(image,"jpg",output);
		 }catch (Exception e){
			 e.printStackTrace();
		 }
		 return image;
	}
}
