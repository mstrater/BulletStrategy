
package bulletstrategy;

import java.util.List;
import java.util.ArrayList;

public class Grid {
    private int numTilesX;
    private int numTilesY;
    private float tileSizeX;
    private float tileSizeY;

    Actor[][] actorGrid;
    List<Actor> actorList;

    public Grid(int numTilesX, int numTilesY, float tileSizeX, float tileSizeY) {
        this.numTilesX = numTilesX;
        this.numTilesY = numTilesY;
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
        actorGrid = new Actor[numTilesX][numTilesY];
        actorList = new ArrayList<Actor>();

        // TODO: MAKE A LOAD LEVEL METHOD.

        // TEMP CODE: ADD SOME ACTORS HERE
        createActor(ActorType.PLAYER, 0, 0);
        createActor(ActorType.BULLET, 10, 10);
    }

    public void createActor(ActorType actorType, int x, int y) {
        // TODO: scale the actor to the tileSize here.

        Actor newActor = null;
        switch (actorType) {
            case PLAYER:
                newActor = new Player(x, y, tileSizeX, tileSizeY);
                break;
            case BULLET:
                newActor = new Bullet(x, y, tileSizeX, tileSizeY);
                break;
            default:
                break;
        }
        actorList.add(newActor);
        actorGrid[x][y] = newActor;
    }

    public void render() {
        for (Actor actor : actorList) {
            actor.render();
        }
    }
}
