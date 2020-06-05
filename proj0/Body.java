public class Body{
	double xxPos;
	double yyPos;
	double xxVel;
	double yyVel;
	double mass;
	String imgFileName;

	static final double g = 6.67e-11;

	public Body(double xp, double yp, double xV, double yV, double m, String img){
		xxPos = xp;
		yyPos = yp;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b){
		return Math.sqrt((xxPos-b.xxPos)*(xxPos-b.xxPos)+(yyPos-b.yyPos)*(yyPos-b.yyPos));
	}

	public double calcForceExertedBy(Body b){
		return g*mass*b.mass/(calcDistance(b)*calcDistance(b));

	}

	public double calcForceExertedByX(Body b){
		return calcForceExertedBy(b)*(b.xxPos-xxPos)/calcDistance(b);
	}

	public double calcForceExertedByY(Body b){
		return calcForceExertedBy(b)*(b.yyPos-yyPos)/calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] bs){
		double xforce = 0;
		for(Body i :bs){
			if(this.equals(i)){
				continue;
			}
			xforce = xforce+ calcForceExertedByX(i);
		}
		return xforce;
	}

	public double calcNetForceExertedByY(Body[] bs){
		double yforce = 0;
		for(Body i :bs){
			if(this.equals(i)){
				continue;
			}
			yforce = yforce+ calcForceExertedByY(i);
		}
		return yforce;
	}

	public void update(double dt, double fX, double fY){
		double aX = fX/mass;
		double aY = fY/mass;

		xxVel = xxVel + aX*dt;
		yyVel = yyVel + aY*dt;

		xxPos = xxPos + xxVel*dt;
		yyPos = yyPos + yyVel*dt; 
	}

	public void draw(){
		StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
	}

}