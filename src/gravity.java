import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class gravity extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame = new JFrame("Simulation");
	static int width = 1600, height = 900;
	static double start = 150;
	static double x = 0, v = 0, a =50, t = 0.01, m = 10, mu = 1, dir = 1, k = 3, time = 0, st, mv = Integer.MIN_VALUE, tp, mx = Integer.MIN_VALUE, radius = 25, ground = 800;
	boolean yes = true;
	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,width+6+6+6,height+29+29+3);
		frame.getContentPane().add(new gravity());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setVisible(true);
		x = start;
		st = System.currentTimeMillis();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		//g2D.translate(frame.getWidth()/2, frame.getHeight()/2);		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2D.setRenderingHints(rh);
    	
    	v = v + a*t;
    	mv = Math.max(v, mv);
    	x = x + v*t;
    	mx = Math.max(x, mx);
    	g2D.setColor(Color.RED);
		g2D.setFont(new Font("Monospaced", Font.BOLD, 30));
		g2D.drawString("Displacement: "+(int)(x-start)+"m",1200,65);
		g2D.drawString("Velocity: "+Math.round(v*100.0)/100.0+"m/s",1200,85);
		g2D.drawString("Acceleration: "+(int)a+"m/s^2",1200,105);
		g2D.drawString("Time: "+Math.round(time*100.0)/100.0+"s",1200,125);
		time+=t;
		if(x>=800) {
    		v = -mu*v - a*t;
		}
		if(yes == true) {
			tp = Math.max(tp, time);
			if(Math.round(v*100.0)/100.0 == 0) {
    			tp = Math.round(time*100.0)/100.0-t;
    			yes = false;
    		}
		}
    	g2D.drawString("Max Disp.: "+(int)(mx-start)+"m",1200,145);
		g2D.drawString("Max Vel.: "+Math.round(mv*100.0)/100.0+"m/s",1200,165);
		g2D.drawString("Time Period: "+Math.round(tp*100.0)/100.0+"s",1200,185);
		g2D.drawString("Restitution: "+Math.round(mu*100.0)/100.0,1200,205);
    	//time = (System.currentTimeMillis()-st)/1000.0;
    	g2D.setColor(Color.DARK_GRAY);
    	g2D.fill(new Ellipse2D.Double(ground-radius,x-radius,radius*2,radius*2));
    	g2D.fill(new Rectangle2D.Double(0,ground+radius,1700,150));
    	g2D.draw(new Line2D.Double(0,start-radius,ground,start-radius));
		run();
		//repaint();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(1);
			repaint();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
