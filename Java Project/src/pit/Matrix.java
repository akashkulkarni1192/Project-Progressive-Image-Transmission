package pit;

public class Matrix {
	public double redMatrix[][];
	public double greenMatrix[][];
	public double blueMatrix[][];
	int ROW,COLUMN;

	public Matrix(int noOfRows, int noOfColumns) {
		this.ROW=noOfRows;
		this.COLUMN=noOfColumns;
		redMatrix = new double[noOfRows][noOfColumns];
		greenMatrix = new double[noOfRows][noOfColumns];
		blueMatrix = new double[noOfRows][noOfColumns];
	}

	public double[] getRow(char color,int row) { 
		// Input row number
		if(color=='r')
			return redMatrix[row];
		else if(color=='g'){
			return greenMatrix[row];
		}
		else if(color=='b')
			return blueMatrix[row];
		return null;
	}

	public void transpose() {
		double[][] tempR = new double[ROW][COLUMN];
		double[][] tempG = new double[ROW][COLUMN];
		double[][] tempB = new double[ROW][COLUMN];
		// Transpose the redMatrix
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COLUMN; j++) {
				tempR[j][i] = redMatrix[i][j];
				tempG[j][i] = greenMatrix[i][j];
				tempB[j][i] = blueMatrix[i][j];
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				redMatrix[i][j] = tempR[i][j];
				greenMatrix[i][j] = tempG[i][j];
				blueMatrix[i][j] = tempB[i][j];
			}
		}
		
	}
	public void input(double[][] arrayR,double[][] arrayG,double[][] arrayB){
		redMatrix=arrayR;
		greenMatrix=arrayG;
		blueMatrix=arrayB;
	}
	public void display(){
		for(int i=0;i<ROW;i++){
			for(int j=0;j<COLUMN;j++)
				System.out.print(redMatrix[i][j]+"R ");
			System.out.print("\t");
			for(int j=0;j<COLUMN;j++)
				System.out.print(greenMatrix[i][j]+"G ");
			System.out.print("\t");
			for(int j=0;j<COLUMN;j++)
				System.out.print(blueMatrix[i][j]+"B ");
			System.out.println();
		}
	}
	public void update(char color,double row[], int rowToBeUpdated) {
		if(color=='r'){
			for (int j = 0; j < COLUMN; j++) {
				redMatrix[rowToBeUpdated][j] = row[j];
			}
		}
		else if(color=='g'){
			for (int j = 0; j < COLUMN; j++) {
				greenMatrix[rowToBeUpdated][j] = row[j];
			}
		}
		else if(color=='b'){
			for (int j = 0; j < COLUMN; j++) {
				blueMatrix[rowToBeUpdated][j] = row[j];
			}
		}
	}
}
