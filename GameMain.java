import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameMain extends JPanel implements Runnable {

    private final int B_WIDTH = 350;
    private final int B_HEIGHT = 350;
    private final int INITIAL_X = 40;
    private final int INITIAL_Y = 40;
    private final int DELAY = 100;

    //declare Image variables to eventually hold images for animation
    private Image walkRight1, walkRight2, walkRight3, walkRight4, walkRight5, walkRight6, walkRight7,walkRight8, walkRight9;
    private Thread animator;
    private int x, y;
    Image[] walkRight; //array "flipbook" for images
    Image currentCharacterFrame;  //this will be used to keep track of which IMAGE of the current animation character to show 
    int currentFrame;  //tracks what current frame number is showing (reset to 0 at every new action!)

    
    /**
     * Constructor to create GameMain objects
     */
    public GameMain() {

        initGameMain();
    }
    
    
    /**
     * loadImage creates ImageIcons which will then be saved in separate Image variables then to be placed into arrays for the "flipbook"
     */
    private void loadImage() {

      
    	//declare and assign each ImageIcon and assign to Image variables
    	ImageIcon i = new ImageIcon("assets/jughead_walking0001.png");
        walkRight1 = i.getImage();
        ImageIcon ii = new ImageIcon("assets/jughead_walking0002.png");
        walkRight2 = ii.getImage();
        ImageIcon iii = new ImageIcon("assets/jughead_walking0003.png");
        walkRight3 = iii.getImage();
        ImageIcon iv = new ImageIcon("assets/jughead_walking0004.png");
        walkRight4 = iv.getImage();
        ImageIcon v = new ImageIcon("assets/jughead_walking0005.png");
        walkRight5 = v.getImage();
        ImageIcon vi = new ImageIcon("assets/jughead_walking0006.png");
        walkRight6 = vi.getImage();
        ImageIcon vii = new ImageIcon("assets/jughead_walking0007.png");
        walkRight7 = vii.getImage();
        ImageIcon viii = new ImageIcon("assets/jughead_walking0008.png");
        walkRight8 = viii.getImage();
        ImageIcon viv = new ImageIcon("assets/jughead_walking0009.png");
        walkRight9 = viv.getImage();
        	
        
        //create an array of images, akin to a flip-book, to more easily "flip" through each one.
        walkRight = new Image[] {walkRight1, walkRight2, walkRight3, walkRight4, walkRight5, walkRight6, walkRight7, walkRight8, walkRight9};
        
        //set an image to paint so we don't crash on start
        currentCharacterFrame = walkRight[0];
    }
    
    /**
     * Used to update the character frame.  This may need to eventually be updated to handle keys OR other methods to be added
     */
    public void characterUpdate() {
    	
    	if(currentFrame <= walkRight.length-1) {
    		
    		
    		currentCharacterFrame = walkRight[currentFrame];
    		
    		System.out.println("walkRight is "+currentFrame);
    		
    		currentFrame++;
    	}
    	else {
    		
    		currentFrame = 0;
    		
    	}
    	
    }

    
    /**
     * initializes the GameMain (sets it all up) and calls the loadImage()
     */
    private void initGameMain() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;
    }

    /**
     * Starts the main game thread
     * 
     */
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    /**
     * Paints the screen and repaints it with a call to update the character animation via drawUpdate()
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawUpdate(g);
    }

    
    /**
     * 
     * updates the character frame with a call to characterUpdate then repaints the image at x,y
     * @param g
     */
    private void drawUpdate(Graphics g) {
   	
    	characterUpdate();
        g.drawImage(currentCharacterFrame, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

   

    /**
     * creates the game loop that never ends
     */
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

                        
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 200;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}