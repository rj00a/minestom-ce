package net.minestom.server.entity.pathfinding;

import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class PPath {
    private final Consumer<Void> onComplete;
    private final BoundingBox boundingBox;
    private final Instance instance;
    private final List<PNode> nodes = new ArrayList<>();
    private int index = 0;
    private final Pos initialPosition;

    public List<PNode> getNodes() {
        return nodes;
    }

    public PPath(Pos point, Instance instance, BoundingBox boundingBox, Consumer<Void> onComplete) {
        this.onComplete = onComplete;
        this.initialPosition = point;
        this.instance = instance;
        this.boundingBox = boundingBox;
    }

    void runComplete() {
        if (onComplete != null) onComplete.accept(null);
    }

    @Override
    public String toString() {
        return nodes.toString();
    }

    PNode.NodeType getCurrentType() {
        if (index >= nodes.size()) return null;
        var current = nodes.get(index);
        return current.getType();
    }

    @Nullable
    Point getCurrent() {
        if (index >= nodes.size()) return null;
        var current = nodes.get(index);
        return current.point;
    }

    void next() {
        if (index >= nodes.size()) return;
        index++;
    }

    void fixJumps() {
        for (int i = 0; i < nodes.size(); i++) {
            var node = nodes.get(i);

            if (node.getType() == PNode.NodeType.JUMP || node.getType() == PNode.NodeType.FALL) {
                Pos previous = i > 0 ? nodes.get(i - 1).point : initialPosition;
                Pos moved = PNode.moveTowards(instance, previous, node.point, boundingBox);

                var toInsert = new PNode(moved, node.g, node.h, null);
                toInsert.setType(node.getType());
                nodes.add(i, toInsert);
                i++;
            }
        }
    }
}
