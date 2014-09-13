
package bulletstrategy;

public enum ActorType {
    PLAYER(0), BULLET(1);

    private int type;

    ActorType(int type) {
        this.type = type;
    }
}
