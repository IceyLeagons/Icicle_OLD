package net.iceyleagons.icicle.common.pathing;

import com.google.common.base.Preconditions;
import net.iceyleagons.icicle.common.heap.BasicHeap;
import net.iceyleagons.icicle.common.heap.Heap;
import org.bukkit.Location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AStar implements Pathfinder {

    private final Map<Location, Node> nodes = new ConcurrentHashMap<>();

    @Override
    public Location[] findPath(Location startingPoint, Location goal, int distance) {
        Preconditions.checkState(Objects.equals(startingPoint.getWorld(), goal.getWorld()), new IllegalArgumentException("Start and Goal must be in the same world!"));
        if (startingPoint.distance(goal) > distance) return new Location[0];
        nodes.clear();

        Heap<Node> openSet = new BasicHeap<>(Node.class, distance);
        Set<Node> closedSet = new HashSet<>();

        Node node = new Node(startingPoint);
        openSet.add(node);
        nodes.put(startingPoint, node);

        Node goalNode = new Node(goal);
        nodes.put(goal, goalNode);


        return calculate(node, goalNode, openSet, closedSet, distance);
    }

    private Location[] calculate(Node start, Node goal, Heap<Node> openSet, Set<Node> closedSet, int distance) {

        while (openSet.getSize() > 0) {
            Node current = openSet.pop();
            closedSet.add(current);

            if (current.getPosition().equals(goal.getPosition())) {
                return backTrack(start, goal);
            }

            for (Node neighbor : getNeighbors(current, start.getPosition(), distance)) {
                //if (neighbor.getPosition().getY() >= current.getPosition().getY() && !neighbor.isWalkable()) continue;
                if (closedSet.contains(neighbor) || !neighbor.isWalkable()) continue;

                neighbor.calculatePenalty(current.getPosition());
                int cost = current.getGCost() + distance(current, neighbor) + neighbor.getPenalty();
                if (cost < neighbor.getGCost() || !openSet.contains(neighbor)) {
                    neighbor.setGCost(cost);
                    neighbor.setHCost(distance(neighbor, goal));
                    neighbor.setParent(current);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }

                }
            }
        }
        return new Location[0];
    }

    private Collection<Node> getNeighbors(Node node, Location start, int distance) {
        Collection<Node> neighbors = new HashSet<>();

        for (Location neighborLocation : getNeighborLocations(node)) {
            if (neighborLocation.distance(start) > distance) continue;

            if (nodes.containsKey(neighborLocation)) {
                neighbors.add(nodes.get(neighborLocation));
            } else {
                Node node1 = new Node(neighborLocation);
                nodes.put(neighborLocation, node1);
                neighbors.add(node1);
            }
        }

        return neighbors;
    }

    private Set<Location> getNeighborLocations(Node node) {
        Set<Location> locations = new HashSet<>();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && z == 0) continue;
                    Location location = node.getPosition().clone().add(x, y, z);
                    locations.add(location);
                }
            }
        }
        return locations;
    }

    private Location[] backTrack(Node start, Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;

        while (!current.getPosition().equals(start.getPosition())) {
            path.add(current);
            current = current.getParent();
        }

        Collections.reverse(path);
        return path.stream().map(Node::getPosition).toArray(Location[]::new);
    }

    private int distance(Node a, Node b) {
        return (int) a.getPosition().distance(b.getPosition()); //Bukkit
    }
}
