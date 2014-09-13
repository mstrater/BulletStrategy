
package bulletstrategy;

import org.lwjgl.opengl.GL11;

public class Actor {
    protected int tileX;
    protected int tileY;
    protected float tileSizeX;
    protected float tileSizeY;
    protected float x;
    protected float y;

    public Actor(int startX, int startY, float tileSizeX, float tileSizeY) {
        this.tileX = startX;
        this.tileY = startY;
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
        this.x = tileX * tileSizeX;
        this.y = tileY * tileSizeY;
    }

    public void render() {

    }
}
