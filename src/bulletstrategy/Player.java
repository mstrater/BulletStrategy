
package bulletstrategy;

import org.lwjgl.opengl.GL11;

public class Player extends Actor {
    public Player(int startX, int startY, float tileSizeX, float tileSizeY) {
        super(startX, startY, tileSizeX, tileSizeY);
    }

    @Override
    public void render() {

        GL11.glPushMatrix();
        GL11.glTranslatef(this.x, this.y, -10);

        GL11.glColor3f(0.0f, 0.0f, 1.0f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f(0, 0, -10);
        GL11.glVertex3f(1, 0, -10);
        GL11.glVertex3f(1, 1, -10);
        GL11.glVertex3f(1, 1, -10);
        GL11.glVertex3f(0, 1, -10);
        GL11.glVertex3f(0, 0, -10);
        GL11.glEnd();

        GL11.glPopMatrix();
    }
}
