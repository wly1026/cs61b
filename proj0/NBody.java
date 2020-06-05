public class NBody{

	public static double readRadius(String fileName){
		In in = new In(fileName);
			
		int num = in.readInt();
		double rad = in.readDouble();

		return rad;
	}

	public static Body[] readBodies(String fileName){
		In in = new In(fileName);

		int num = in.readInt();
		double rad = in.readDouble();

		Body[] bodies = new Body[num]; 

		for(int i =0; i< num; i++){
			double xp= in.readDouble();
			double yp= in.readDouble();
			double xv= in.readDouble();
			double yv= in.readDouble();
			double m= in.readDouble();
			String name= in.readString();
			bodies[i]= new Body(xp,yp,xv,yv,m,name);
		}

		return bodies;
	}

	public static void main(String[] args) {
		/** Read bodies and universe radius from a file*/
		double t = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		
		String filename = args[2];

		double radius = readRadius(filename);
		Body[] universe= readBodies(filename);

		/** Draw the background*/
		String imageToDraw = "images/starfield.jpg";
		
		StdDraw.setScale(-radius,radius);

		StdDraw.picture(0,0,imageToDraw,2*radius,2*radius);

		/** Draw bodies*/
		for(Body b:universe){
			b.draw();
		}

		StdDraw.enableDoubleBuffering();

		int n = universe.length;
		
		for (double time=0; time<=t; time= time+dt){
			double[] xForces = new double[n];
			double[] yForces = new double[n];
			for(int i=0; i<n; i++){
				xForces[i]=universe[i].calcNetForceExertedByX(universe);  
				yForces[i]=universe[i].calcNetForceExertedByY(universe);
			}
			for(int i=0; i<n; i++){
				universe[i].update(dt,xForces[i],yForces[i]);
			}
			
			StdDraw.clear();
			StdDraw.picture(0,0,imageToDraw,2*radius,2*radius);
			for(Body b:universe){
				b.draw();
			StdDraw.show();
			//StdDraw.pause(10);
			}

		} 			
		
		StdOut.printf("%d\n", n);
		StdOut.printf("%.2e\n",radius);
		for(Body b:universe){
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",b.xxPos,b.yyPos,b.xxVel,b.yyVel,b.mass,b.imgFileName);
		}

	}

}