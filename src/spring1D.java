import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class spring1D extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame = new JFrame("Simulation");
	static int width = 1600, height = 900;
	static double hinge = 50, nl = 100;
	boolean yes = true;
	static double x = 0, v = 0, ag =50, a, t = 0.01, m = 10, k = 2, time = 0, st, mv = Integer.MIN_VALUE, tp, mx = Integer.MIN_VALUE, radius = 25, ground = 800, offset=800;
	public static void main(String args[]) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0,0,width+6+6+6,height+29+29+3);
		frame.getContentPane().add(new spring1D());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setVisible(true);
		x = hinge + nl;
		st = System.currentTimeMillis();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2D = (Graphics2D) g;
		//g2D.translate(frame.getWidth()/2, frame.getHeight()/2);		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    	g2D.setRenderingHints(rh);
    	a = ag - k * (x-(hinge+nl))/m;
    	v = v + a*t;
    	mv = Math.max(v, mv);
    	x = x + v*t;
    	mx = Math.max(x, mx);
    	g2D.setColor(Color.RED);
		g2D.setFont(new Font("Monospaced", Font.BOLD, 30));
		g2D.drawString("Displacement: "+Math.round(x-hinge-nl)+"m",1200,65);
		g2D.drawString("Velocity: "+Math.round(v*100.0)/100.0+"m/s",1200,85);
		g2D.drawString("Acceleration: "+(int)a+"m/s^2",1200,105);
		g2D.drawString("Time: "+Math.round(time*100.0)/100.0+"s",1200,125);
		time+=t;
		if(yes == true) {
			tp = Math.max(tp, time);
			if(Math.round(x*100.0)/100.0 == hinge+nl) {
    			tp = Math.round(time*100.0)/100.0-t;
    			yes = false;
    		}
		}
    	g2D.drawString("Max Disp.: "+(int)(mx-hinge-nl)+"m",1200,145);
		g2D.drawString("Max Vel.: "+Math.round(mv*100.0)/100.0+"m/s",1200,165);
		g2D.drawString("Time Period: "+Math.round(tp*100.0)/100.0+"s",1200,185);
    	g2D.setColor(Color.DARK_GRAY);
    	g2D.fill(new Ellipse2D.Double(offset-radius,Math.round(x)-radius,radius*2,radius*2));
    	g2D.draw(new Line2D.Double(offset,hinge,offset,x));
    	g2D.fill(new Rectangle2D.Double(0,ground+radius,1700,150));
    	g2D.setColor(Color.RED);
    	g2D.draw(new Line2D.Double(offset-radius,x,offset+radius,x));
    	g2D.draw(new Line2D.Double(offset,x-radius,offset,x+radius));
    	g2D.draw(new Line2D.Double(0,hinge+nl,offset-radius,hinge+nl));
    	g2D.drawString("Natural len.",(int) (offset-radius)-300,(int) (hinge+nl));
    	g2D.draw(new Line2D.Double(0,hinge+nl+m*ag/k,offset-radius,hinge+nl+m*ag/k));
    	g2D.drawString("Mean Pos.: mg/k",(int) (offset-radius)-300,(int) (hinge+nl+m*ag/k));
    	g2D.draw(new Line2D.Double(0,hinge+nl+2*m*ag/k,offset-radius,hinge+nl+2*m*ag/k));
    	g2D.drawString("Amplitude: 2mg/k",(int) (offset-radius)-300,(int) (hinge+nl+2*m*ag/k));
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
