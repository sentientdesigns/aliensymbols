package utils;

import java.util.Vector;
import java.util.Arrays;
import java.util.LinkedList;

import utils.Point2D;

public class FloodErase2D {
	class SeperateSegment2D {
		boolean[][] pixels;
		int area;

		public SeperateSegment2D(int sizeX, int sizeY){
			area = 0;
			pixels = new boolean[sizeX][sizeY];
			for(int x=0;x<pixels.length;x++){
				for(int y=0;y<pixels[0].length;y++){
					pixels[x][y]=false;
				}
			}
		}

		public void assignPixel(int x, int y, boolean value){ pixels[x][y]=value; }
		public boolean[][] getPixels(){ return pixels; }

		public void addToArea(){ area++; }
		public void addToArea(int value){ area+=value; }
		public int getArea(){ return area; }
	}

	public enum FloodDir { DIAGONAL, HORIZONTAL };

	Vector<SeperateSegment2D> segments;
	int largestSegment;

	public FloodErase2D(boolean[][] pixels, FloodDir directions){
		segments = new Vector<SeperateSegment2D>();
		largestSegment = -1;
		runAlgorithm(pixels, directions);
	}
	
	protected void runAlgorithm(boolean[][] pixels, FloodDir directions){
		Point2D firstNonEmpty = getFirstNonEmpty(pixels);
		while(firstNonEmpty!=null){
			SeperateSegment2D currSegment = new SeperateSegment2D(pixels.length,pixels[0].length);
			floodEraseFrom(pixels,firstNonEmpty,directions,currSegment);
			// this should be before the vector add function
			if(largestSegment<currSegment.getArea()){ largestSegment = segments.size(); }
			// this should be after the largestSegment check
			segments.add(currSegment);
			firstNonEmpty = getFirstNonEmpty(pixels);
		}
	}

	public int getNumberOfSegments(){ return segments.size(); }
	public int getAreaOfLargestSegment(){
		if(largestSegment==-1 || segments.isEmpty()){ return 0; }
		return segments.get(largestSegment).getArea();
	}
	public int getAreaOfSmallerSegments(){
		if(largestSegment==-1 || segments.isEmpty()){ return 0; }
		int result = 0;
		for(int i=0;i<segments.size();i++){
			if(i!=largestSegment){ result+=segments.get(largestSegment).getArea(); }
		}
		return result;
	}

	public int getAreaOfSegment(int index){
		if(index<0 || index>=segments.size()){
			System.out.println("segment index out of bounds");
		}
		return segments.get(index).getArea();
	}
	public boolean[][] getSegment(int index){
		if(index<0 || index>=segments.size()){
			System.out.println("segment index out of bounds");
			return null;
		}
		return segments.get(index).getPixels();
	}
	protected void floodEraseFrom(boolean[][] pixels, Point2D startingPixel, FloodDir directions, SeperateSegment2D segment){
		floodEraseFrom(pixels,(int)(startingPixel.x),(int)(startingPixel.y),directions,segment);
	}
	protected void floodEraseFrom(boolean[][] pixels, int start_x, int start_y, FloodDir directions, SeperateSegment2D segment){
		int x=start_x;
		int y=start_y;
		if(pixels[x][y]){
			LinkedList<Point2D> queue = new LinkedList<Point2D>();
			queue.add(new Point2D(x,y));

			int pixelCount = 0;
			while (!queue.isEmpty()) {
				Point2D point = queue.remove();
				if(point.x>=0 && point.y>=0 && point.x<pixels.length && point.y<pixels[0].length && pixels[(int)(point.x)][(int)(point.y)]){
					pixels[(int)(point.x)][(int)(point.y)]=false;

					segment.addToArea();
					segment.assignPixel((int)(point.x),(int)(point.y),true);

					if(directions==FloodDir.HORIZONTAL){
						queue.add(new Point2D(point.x+1, point.y));
						queue.add(new Point2D(point.x-1, point.y));
						queue.add(new Point2D(point.x, point.y+1));
						queue.add(new Point2D(point.x, point.y-1));
					} else if(directions==FloodDir.DIAGONAL){
						queue.add(new Point2D(point.x+1, point.y+1));
						queue.add(new Point2D(point.x+1, point.y));
						queue.add(new Point2D(point.x+1, point.y-1));
						queue.add(new Point2D(point.x-1, point.y+1));
						queue.add(new Point2D(point.x-1, point.y));
						queue.add(new Point2D(point.x-1, point.y-1));
						queue.add(new Point2D(point.x, point.y+1));
						queue.add(new Point2D(point.x, point.y-1));
					}
				}
			}
		}
	}
	
	public static boolean[][] floodCoverage(boolean[][] pixels, int start_x, int start_y, FloodDir directions){
		boolean[][] result = new boolean[pixels.length][pixels[0].length];
		for(int x=0;x<result.length;x++){
			for(int y=0;y<result[x].length;y++){
				result[x][y]=false;
			}
		}
		
		if(!pixels[start_x][start_y]){ return result; }
		boolean[][] pixelsCopy = new boolean[pixels.length][pixels[0].length];
		for(int x=0;x<pixels.length;x++){
			pixelsCopy[x]=Arrays.copyOf(pixels[x], pixels[x].length);
		}
		
		LinkedList<Point2D> queue = new LinkedList<Point2D>();
		queue.add(new Point2D(start_x,start_y));
		int pixelCount = 0;
		while (!queue.isEmpty()) {
			Point2D point = queue.remove();
			if(point.x>=0 && point.y>=0 && point.x<pixelsCopy.length && point.y<pixelsCopy[0].length && pixelsCopy[(int)(point.x)][(int)(point.y)]){
				pixelsCopy[(int)(point.x)][(int)(point.y)]=false;
				result[(int)(point.x)][(int)(point.y)]=true;
				if(directions==FloodDir.HORIZONTAL){
					queue.add(new Point2D(point.x+1, point.y));
					queue.add(new Point2D(point.x-1, point.y));
					queue.add(new Point2D(point.x, point.y+1));
					queue.add(new Point2D(point.x, point.y-1));
				} else if(directions==FloodDir.DIAGONAL){
					queue.add(new Point2D(point.x+1, point.y+1));
					queue.add(new Point2D(point.x+1, point.y));
					queue.add(new Point2D(point.x+1, point.y-1));
					queue.add(new Point2D(point.x-1, point.y+1));
					queue.add(new Point2D(point.x-1, point.y));
					queue.add(new Point2D(point.x-1, point.y-1));
					queue.add(new Point2D(point.x, point.y+1));
					queue.add(new Point2D(point.x, point.y-1));
				}
			}
		}
		return result;
	}
/*
	protected void floodEraseFrom(boolean[][] pixels, int[] startingPixel, FloodDir directions, SeperateSegment2D segment){
		int x = startingPixel[0];
		int y = startingPixel[1];
		if(x>=0 && y>=0 && x<pixels.length && y<pixels[0].length && pixels[x][y]){
			segment.addToArea();
			segment.assignPixel(x,y,true);
			pixels[x][y]=false;
			if(directions==FloodDir.HORIZONTAL){
				floodEraseFrom(pixels,new int[]{x+1,y }, directions, segment);
				floodEraseFrom(pixels,new int[]{x-1,y }, directions, segment);
				floodEraseFrom(pixels,new int[]{x,y+1 }, directions, segment);
				floodEraseFrom(pixels,new int[]{x,y-1 }, directions, segment);
			} else if(directions==FloodDir.DIAGONAL){
				// same layer
				floodEraseFrom(pixels,new int[]{x+1,y-1 }, directions, segment);
				floodEraseFrom(pixels,new int[]{x+1,y }, directions, segment);
				floodEraseFrom(pixels,new int[]{x+1,y+1 }, directions, segment);
				floodEraseFrom(pixels,new int[]{x,y-1 }, directions, segment);
				floodEraseFrom(pixels,new int[]{x,y+1 }, directions, segment);
				floodEraseFrom(pixels,new int[]{x-1,y-1 }, directions, segment);
				floodEraseFrom(pixels,new int[]{x-1,y }, directions, segment);
				floodEraseFrom(pixels,new int[]{x-1,y+1 }, directions, segment);
			}
		}
	}
*/
	protected Point2D getFirstNonEmpty(boolean[][] pixels){
		for(int x=0;x<pixels.length;x++){
			for(int y=0;y<pixels[0].length;y++){
				if(pixels[x][y]){ return new Point2D(x,y); }
			}
		}
		return null;
	}
}

