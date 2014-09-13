
package bulletstrategy;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.PixelFormat;

public class Main {
    public int WIDTH = 1280;
    public int HEIGHT = 1024;
    public float FOV = 90.0f;

    public int fps;
    public long lastFPS;
    public Lighting lighting;

    public Grid levelGrid;

    public void init() {
        setupOpenGL();

        //lighting.setPosition(cam.getPosition().x, cam.getPosition().y, cam.getPosition().z, 1.0f);
        lighting.setPosition(25f, 52f, 47f, 1f);
        lighting.setDiffuseLight(0.74f, 0.92f, 0.76f, 0f);
        lighting.setAmbientLight(0.1f, 0.25f, 0.1f, 0f);

        levelGrid = new Grid(20, 20, 1.0f, 1.0f);

        while (!Display.isCloseRequested()) {
            updateFPS();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glLoadIdentity();

            render();

            Display.update();
            Display.sync(60);
        }
    }

    public void render() {
        //        // set the color of the quad (R,G,B,A)
        //        GL11.glColor3f(0.5f, 0.5f, 1.0f);
        //
        //        // draw quad
        //        GL11.glBegin(GL11.GL_TRIANGLES);
        //        GL11.glVertex3f(0, 0, -10);
        //        GL11.glVertex3f(1, 0, -10);
        //        GL11.glVertex3f(1, 1, -10);
        //        GL11.glVertex3f(1, 1, -10);
        //        GL11.glVertex3f(0, 1, -10);
        //        GL11.glVertex3f(0, 0, -10);
        //        GL11.glEnd();

        levelGrid.render();
    }

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    /**
     * Set the display mode to be used
     *
     * @param width The width of the display required
     * @param height The height of the display required
     * @param fullscreen True if we want fullscreen mode
     */
    public void setDisplayMode(int width, int height, boolean fullscreen) {

        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
                && (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i = 0; i < modes.length; i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null)
                                    || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequence against the
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
                                && (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width, height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);

        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + fullscreen + e);
        }
    }

    public void setupOpenGL() {
        lastFPS = getTime();

        try {
            setDisplayMode(800, 600, false);
            Display.create(new PixelFormat(8, 0, 0, 4));
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL
        GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables depth testing
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        setPerspective(FOV, WIDTH / HEIGHT, 0.1, 100.0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        lighting = new Lighting();
    }

    // replacement for gluPerspective
    public void setPerspective(double fovy, double aspect, double zNear, double zFar) {
        double pi180 = Math.PI / 180.0;
        double top, bottom, left, right;
        top = zNear * Math.tan(pi180 * fovy / 2);
        bottom = -top;
        right = aspect * top;
        left = -right;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glFrustum(left, right, bottom, top, zNear, zFar);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public static void main(String[] argv) {
        Main mainprog = new Main();
        mainprog.init();
    }
}
