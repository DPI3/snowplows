package src;

public class NodePosition {
    private final Node node;
    private final int x;
    private final int y;

    public NodePosition(Node node, int x, int y) {
        this.node = node;
        this.x = x;
        this.y = y;
    }

    public Node getNode() { return node; }
    public int getX() { return x; }
    public int getY() { return y; }
}
