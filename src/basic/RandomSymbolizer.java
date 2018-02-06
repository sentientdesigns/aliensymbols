package basic;

import processing.core.*;

import utils.FloodErase2D;
import utils.Matrix2D;

import java.util.Random;

public class RandomSymbolizer {
	int sizeX = 30;
	int sizeY = 30;
	
	int edgeX = 2;
	int edgeY = 2;
	
	int imageX = 300;
	int imageY = 300;
	
	int caIterations=10;
	boolean shadingEnabled = false;
	
	PApplet p;
	
	Random r;
	
	
	public RandomSymbolizer(){
		p = new PApplet();
		p.init();
		
		r = new Random();
	}
	
	public PImage produceSymbol(){
		float[][] matrix = Matrix2D.initialize(sizeX, sizeY, 0.f);
		while(Matrix2D.count(matrix)<sizeX*sizeY/5 || Matrix2D.count(matrix)>sizeX*sizeY*4/5){
			matrix = initNoise(matrix);
			
			// PRE-COMPUTED SYMMETRY STEP
			float roll = r.nextFloat();
			if(roll>=0.2f && roll<0.25f){ matrix = getVerticallyOffset(matrix); }
			if(roll>=0.25f && roll<0.5f){ matrix = getVerticallySymmetrical(matrix); }
			if(roll>=0.5f && roll<0.7f){ matrix = getQuadSymmetrical(matrix); }
			if(roll>=0.7f && roll<0.85f){ matrix = getHalfRotationalSymmetry(matrix); }
			if(sizeX==sizeY){
				if(roll>=0.85f && roll<0.88f){ matrix = getDiagonalSymmetry(matrix); }
				if(roll>=0.88f && roll<0.9f){ matrix = getLSymmetry(matrix); }
			}
			if(roll>=0.9f){ matrix = getQuasiSymmetrical(matrix); }
			
			// CELLULAR AUTOMATA STEP
			for(int i=0;i<caIterations;i++){
				matrix = applyCellularAutomata(matrix);
			}
			
			// POST-APPLIED SYMMETRY STEP
			float post_roll = r.nextFloat();
			if(post_roll<0.10f){ matrix = getVerticallySymmetrical(matrix); }
			if(post_roll>=0.10f && post_roll<0.15f){ matrix = getHorizontallySymmetrical(matrix); }
			
		}
		if(shadingEnabled){
			matrix = applyInteriorCA(matrix);
			for(int i=0;i<5;i++){
				matrix = applyShadingCA(matrix);
			}
		}
		PImage result = render(matrix);
		return result;
	}
	
	public float[][] initNoise(float[][] matrix){
		//beginDraw();
		float[][] result = Matrix2D.initialize(matrix.length, matrix[0].length, 0.f);
		for(int x=edgeX;x<result.length-edgeX;x++){
			for(int y=edgeY;y<result[0].length-edgeY;y++){
				result[x][y] = r.nextInt()>=0.5 ? 0 : 1.f;
			}
		}
		return result;
	}
	public float[][] getHorizontallySymmetrical(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		for(int x=edgeX;x<result.length-edgeX;x++){
			for(int y=result[0].length/2;y<result[0].length-edgeY;y++){
				result[x][y] = result[x][(result[0].length-1)-y];
			}
		}
		return result;
	}	
	public float[][] getVerticallySymmetrical(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		for(int x=result.length/2;x<result.length-edgeX;x++){
			for(int y=edgeY;y<result[0].length-edgeY;y++){
				result[x][y] = result[(result.length-1)-x][y];
			}
		}
		return result;
	}
	public float[][] getVerticallyOffset(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		for(int x=result.length/2-edgeX;x<result.length-edgeX;x++){
			for(int y=edgeY;y<result[0].length-edgeY;y++){
				if(x<result.length/2){ 
					result[x][y]=0.f;
				} else {
					result[x][y] = result[x-result.length/2][y];
				}
			}
		}
		return result;
	}
	public float[][] getQuadSymmetrical(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		for(int x=result.length/2;x<result.length-edgeX;x++){
			for(int y=edgeY;y<result[0].length-edgeY;y++){
				result[x][y] = result[(result.length-1)-x][y];
			}
		}
		for(int x=edgeX;x<result.length-edgeX;x++){
			for(int y=result[0].length/2;y<result[0].length-edgeY;y++){
				result[x][y] = result[x][(result[0].length-1)-y];
			}
		}
		return result;
	}
	public float[][] getHalfRotationalSymmetry(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		for(int x=result.length/2;x<result.length-edgeX;x++){
			for(int y=edgeY;y<result[0].length-edgeY;y++){
				result[x][y] = result[(result.length-1)-x][(result[0].length-1)-y];
			}
		}
		return result;
	}
	public float[][] getLSymmetry(float[][] matrix){
		float[][] result = Matrix2D.initialize(matrix.length, matrix[0].length, 0.f);
		for(int x=0;x<result.length/2;x++){
			for(int y=0;y<result[0].length;y++){
				result[x][y] = matrix[x][y];
			}
		}
		for(int x=0;x<result.length/2;x++){
			for(int y=0;y<result[0].length;y++){
				if(result[y][x]<0.9f){ 
					result[y][x] = result[x][y];
				}
			}
		}
		return result;
	}
	public float[][] getDiagonalSymmetry(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[0].length;y++){
				if(result[x][y]!=result[y][x]){
					result[x][y]=result[y][x];
				}
			}
		}
		return result;
	}
	//FUNNY RESULTS 
	public float[][] getQuasiSymmetrical(float[][] matrix){
		float[][] result = Matrix2D.copy(matrix);
		float roll = r.nextFloat();
		if(roll<0.33f){
			for(int x=result.length/2;x<result.length-edgeX;x++){
				for(int y=edgeY;y<result[0].length-edgeY;y++){
					result[x][y] = result[(result.length-1)-x][(result[0].length-1)-y];
				}
			}
			for(int x=edgeX;x<result.length-edgeX;x++){
				for(int y=result[0].length/2;y<result[0].length-edgeY;y++){
					result[x][y] = result[(result.length-1)-x][y];
				}
			}
		} else if(roll<0.66f){
			for(int x=result.length/2;x<result.length-edgeX;x++){
				for(int y=edgeY;y<result[0].length-edgeY;y++){
					result[x][y] = result[(result.length+edgeX-1)-x][y];
				}
			}
		} else {
			for(int x=result.length/2;x<result.length-edgeX;x++){
				for(int y=edgeY;y<result[0].length-edgeY;y++){
					result[x][y] = result[x-result.length/2][y];
				}
			}
		}
		return result;
	}
	
	public float[][] applyCellularAutomata(float[][] matrix){
		Random r = new Random();
		float[][] result = Matrix2D.initialize(matrix.length, matrix[0].length, 0.f);
		int width = matrix.length;
		int height = matrix[0].length;
			
		for(int x=0;x<width;x++){
		  for(int y=0;y<height;y++){
			int blackCount = 0;
			// VON NEUMAN
			for(int i=x-1;i<=x+1;i++){
			  for(int j=y-1;j<=y+1;j++){
				if(i<0||i>=width||j<0||j>=height){ 
				  //blackCount++; 
				} else {
				  if(matrix[i][j]>0.5f){
					blackCount++;
				  }
				}
			  }
			}
			result[x][y]=(blackCount>=5) ? 1.f : 0.f;
		  }
		}
		return result;
	}
		
	public float[][] applyInteriorCA(float[][] matrix){
		Random r = new Random();
		float[][] result = Matrix2D.copy(matrix);
		int width = matrix.length;
		int height = matrix[0].length;
			
		for(int x=0;x<width;x++){
		  for(int y=0;y<height;y++){
			int blackCount = 0;
			// VON NEUMAN
			for(int i=x-1;i<=x+1;i++){
			  for(int j=y-1;j<=y+1;j++){
				if(i<0||i>=width||j<0||j>=height){ 
				  //blackCount++; 
				} else {
				  if(matrix[i][j]>0.5f){
					blackCount++;
				  }
				}
			  }
			}
			if(blackCount==9){ result[x][y] = 0.5f; }
		  }
		}
		return result;
	}
	public float[][] applyShadingCA(float[][] matrix){
		Random r = new Random();
		float[][] result = Matrix2D.copy(matrix);
		int width = matrix.length;
		int height = matrix[0].length;
			
		for(int x=0;x<width;x++){
		  for(int y=0;y<height;y++){
			int blackCount = 0;
			float shade = 0;
			// VON NEUMAN
			for(int i=x-1;i<=x+1;i++){
			  for(int j=y-1;j<=y+1;j++){
				if(i<0||i>=width||j<0||j>=height){ 
				  //blackCount++; 
				} else {
				  if(matrix[i][j]>0.2f){
					blackCount++;
					shade+=matrix[i][j];
				  }
				}
			  }
			}
			if(blackCount==9){ result[x][y]= shade/9.f; }
		  }
		}
		return result;
	}
	
	public PImage render(float[][] matrix){
		int[] gridSize = { imageX/matrix.length, imageY/matrix[0].length };
		PGraphics pg = p.createGraphics(imageX, imageY, p.P2D);
		pg.background(p.color(255));
		for(int x=0;x<matrix.length;x++){
			for(int y=0;y<matrix[0].length;y++){
				for(int i=0;i<gridSize[0];i++){
					for(int j=0;j<gridSize[1];j++){
						int localX = x*gridSize[0]+j;
						int localY = y*gridSize[1]+i;
						pg.set(localX,localY,p.color((1-matrix[x][y])*255));	
					}
				}
			}
		}
		PImage img = pg.get();
		return img;
	}
	
	public static void main(String[] args) {
		RandomSymbolizer sz = new RandomSymbolizer();
		for(int i=0;i<200;i++){
			sz.produceSymbol().save("data/symbol"+i+".png");		
		}
	}
}