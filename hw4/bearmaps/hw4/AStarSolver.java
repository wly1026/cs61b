package bearmaps.hw4;

import bearmaps.proj2ab.*;
import edu.princeton.cs.algs4.Stopwatch;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex>{
    SolverOutcome outcome;
    int numStatesExplored;

    HashMap<Vertex, Double> distTo;
    HashMap<Vertex, Vertex> edgeTo;
    ExtrinsicMinPQ<Vertex> pq;
    AStarGraph<Vertex> input;
    Vertex end;
    Vertex start;
    double timeout;
    double timeSpend;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        distTo.put(start, 0.00);
        this.input = input;
        this.end = end;
        this.start = start;
        this.timeout = timeout;

        pq = new ArrayHeapMinPQ<>();
        pq.add(start, input.estimatedDistanceToGoal(start, end));

        while (pq.size() > 0) {
            timeSpend = sw.elapsedTime();
            if (explorationTime() > timeout) {
                return;
            }
            if (pq.getSmallest().equals(end)) {
                outcome = SolverOutcome.SOLVED;
                return;
            }
            Vertex p = pq.removeSmallest();
            numStatesExplored += 1;

            for (WeightedEdge<Vertex> edge: input.neighbors(p)) {
                Vertex q = edge.to();
                if (!distTo.containsKey(q)) {
                    distTo.put(q, Double.MAX_VALUE);
                    edgeTo.put(q, null);
                }
                relax(edge);
            }
        }
        outcome = SolverOutcome.UNSOLVABLE;
    }

    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (distTo.get(p) + w < distTo.get(q)) {
            distTo.replace(q, distTo.get(p) + w);
            edgeTo.replace(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                pq.add(q,distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        LinkedList<Vertex> r = new LinkedList<>();
        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome.equals(SolverOutcome.UNSOLVABLE)) {
            return r;
        } else {
            Vertex curr = end;
            while (!curr.equals(start)) {
                r.addFirst(curr);
                curr = edgeTo.get(curr);
            }
            r.addFirst(start);
        }
        return r;
    }

    @Override
    public double solutionWeight() {
        if (outcome().equals(SolverOutcome.TIMEOUT) || outcome.equals(SolverOutcome.UNSOLVABLE)) {
            return 0;
        }
        return distTo.get(end);
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        if (timeSpend > timeout) {
            outcome = SolverOutcome.TIMEOUT;
        }
        return timeSpend;
    }
}
