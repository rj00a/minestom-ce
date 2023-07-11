package net.minestom.server.entity.pathfinding;

import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;

import java.util.*;
import java.util.function.Consumer;

public class PathGenerator {
    public static double heuristic (Point node, Point target) {
        return node.distance(target);
    }

    static Comparator<PNode> pNodeComparator = (s1, s2) -> (int) (((s1.g + s1.h) - (s2.g + s2.h)) * 1000);
    public static PPath generate(Instance instance, Pos orgStart, Point orgTarget, double maxDistance, double closeDistance, BoundingBox boundingBox, Consumer<Void> onComplete) {
        Pos start = PNode.gravitySnap(instance, orgStart, boundingBox, 20);
        Pos target = PNode.gravitySnap(instance, orgTarget, boundingBox, 20);

        Point closestFound = null;
        double closestDistance = Double.MAX_VALUE;

        if (start == null || target == null) return null;

        PPath path = new PPath(start, instance, boundingBox, onComplete);
        Set<PNode> closed = new HashSet<>();

        int maxSize = (int) Math.floor(maxDistance * 7);
        PNode pStart = new PNode(start, 0, heuristic(start, target), null);

        TreeSet<PNode> open = new TreeSet<>(pNodeComparator);
        open.add(pStart);

        while (!open.isEmpty() && !withinDistance(open.first().point, target, closeDistance) && closed.size() < maxSize) {
            PNode current = open.pollFirst();

            var chunk = instance.getChunkAt(current.point);
            if (chunk == null) continue;
            if (!chunk.isLoaded()) continue;

            if (current.h < closestDistance) {
                closestDistance = current.h;
                closestFound = current.point;
            }

            if (current.g > 20) break;

            current.getNearby(instance, closed, target, boundingBox).forEach(p -> {
                if (p.point.distance(target) <= maxDistance) {
                    open.add(p);
                    closed.add(p);
                }
            });
        }

        PNode current = open.pollFirst();

        if (current == null || open.isEmpty() || current.point.distance(target) > closeDistance) {
            if (closestFound == null) return null;

            path = PathGenerator.generate(instance, orgStart, Pos.fromPoint(closestFound), maxDistance, closeDistance, boundingBox, onComplete);
            if (path == null) return null;

            var pathNodes = path.getNodes();
            var newNode = new PNode(Pos.fromPoint(closestFound), 0, 0, pathNodes.get(pathNodes.size() - 1));
            newNode.setType(PNode.NodeType.REPATH);
            pathNodes.add(newNode);

            return path;
        }

        while (current.parent != null) {
            path.getNodes().add(current);
            current = current.parent;
        }

        Collections.reverse(path.getNodes());

        if (path.getNodes().size() > 0) {
            PNode pEnd = new PNode(target, 0, 0, path.getNodes().get(path.getNodes().size() - 1));
            path.getNodes().add(pEnd);
        }

        path.fixJumps();
        // System.out.println(path);

        return path;
    }

    private static boolean withinDistance(Pos point, Pos target, double closeDistance) {
        return point.distanceSquared(target) < closeDistance;

    }
}
