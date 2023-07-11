package net.minestom.server.entity.pathfinding;

import net.minestom.server.MinecraftServer;
import net.minestom.server.collision.BoundingBox;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;

import java.util.*;
import java.util.function.Consumer;

public class PathGenerator {
    public static double heuristic (Point node, Point target) {
        return node.distance(target);
    }

    static Comparator<PNode> pNodeComparator = (s1, s2) -> (int) (((s1.g + s1.h) - (s2.g + s2.h)) * 1000);
    public static PPath generate(Instance instance, Pos orgStart, Point orgTarget, double closeDistance, double maxDistance, double pathVariance, BoundingBox boundingBox, Consumer<Void> onComplete) {
        closeDistance = Math.max(0.8, closeDistance);

        long time = System.currentTimeMillis();

        Pos start = PNode.gravitySnap(instance, orgStart, boundingBox, 100);
        Pos target = PNode.gravitySnap(instance, orgTarget, boundingBox, 100);

        List<PNode> closestFoundNodes = List.of();
        double closestDistance = Double.MAX_VALUE;

        if (start == null || target == null) return null;
        double straightDistance = heuristic(start, target);

        PPath path = new PPath(start, instance, boundingBox, maxDistance, pathVariance, onComplete);
        Set<PNode> closed = new HashSet<>();

        int maxSize = (int) Math.floor(maxDistance * 8);
        PNode pStart = new PNode(start, 0, heuristic(start, target), null);

        TreeSet<PNode> open = new TreeSet<>(pNodeComparator);
        open.add(pStart);

        while (!open.isEmpty() && closed.size() < maxSize) {
            PNode current = open.pollFirst();

            var chunk = instance.getChunkAt(current.point);
            if (chunk == null) continue;
            if (!chunk.isLoaded()) continue;

            if (((current.g + current.h) - straightDistance) > pathVariance) continue;
            if (!withinDistance(current.point, start, maxDistance)) break;
            if (withinDistance(current.point, target, closeDistance)) break;

            if (current.h < closestDistance) {
                closestDistance = current.h;
                closestFoundNodes = List.of(current);
            }

            var packet = ParticleCreator.createParticlePacket(Particle.FLAME, current.point.x(), current.point.y(), current.point.z(), 0, 0, 0, 0);
            MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(p -> p.sendPacket(packet));

            current.getNearby(instance, closed, target, boundingBox).forEach(p -> {
                if (p.point.distance(start) <= maxDistance) {
                    open.add(p);
                    closed.add(p);
                }
            });
        }

        PNode current = open.pollFirst();

        System.out.println(closestDistance);

        System.out.println(closestFoundNodes);
        if (open.isEmpty()) return null;

        if (current == null || !withinDistance(current.point, target, closeDistance)) {
            if (closestFoundNodes.size() == 0) return null;
            current = closestFoundNodes.get(closestFoundNodes.size() - 1);
            current.setType(PNode.NodeType.REPATH);
        }

        while (current.parent != null) {
            path.getNodes().add(current);
            current = current.parent;
        }

        Collections.reverse(path.getNodes());

        if (withinDistance(current.point, target, closeDistance)) {
            PNode pEnd = new PNode(target, 0, 0, path.getNodes().get(path.getNodes().size() - 1));
            path.getNodes().add(pEnd);
        }

        path.fixJumps();

        long e = System.currentTimeMillis();

        PNode finalCurrent = current;
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(p -> {
            p.sendMessage("Found path in " + (e - time) + "ms | Path cost is " + (finalCurrent.g + finalCurrent.h));
        });

        return path;
    }

    private static boolean withinDistance(Pos point, Pos target, double closeDistance) {
        return point.distanceSquared(target) < (closeDistance * closeDistance);

    }
}
