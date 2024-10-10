import java.util.*;

public class JKUMap extends G implements ShortestPathCalculator {

    public JKUMap() {
        V spar = insertVertex("Spar");
        V lit = insertVertex("LIT");
		V openLab = insertVertex("Open Lab");
        V bank = insertVertex("Bank");
        V khg = insertVertex("KHG");
        V porter = insertVertex("Porter");
        V chat = insertVertex("Chat");
        V library = insertVertex("Library");
        V lui = insertVertex("LUI");
        V teichwerk = insertVertex("Teichwerk");
        V parking = insertVertex("Parking");
        V sp1 = insertVertex("SP1");
        V sp3 = insertVertex("SP3");
        V bellaCasa = insertVertex("Bella Casa");
        V castle = insertVertex("Castle");
        V papaya = insertVertex("Papaya");
        V jkh = insertVertex("JKH");

        insertEdge(spar, lit, 50);
        insertEdge(spar, khg, 165);
        insertEdge(spar, porter, 103);
        insertEdge(lit, porter, 80);
        insertEdge(porter, openLab, 70);
        insertEdge(porter, bank, 100);
        insertEdge(bank, khg, 150);
        insertEdge(bank, chat, 115);
        insertEdge(chat, library, 160);
        insertEdge(chat, lui, 240);
        insertEdge(library, lui, 90);
        insertEdge(lui, teichwerk, 135);
        insertEdge(lui, sp1, 175);
        insertEdge(sp1, parking, 240);
        insertEdge(parking, bellaCasa, 145);
        insertEdge(parking, khg, 190);
        insertEdge(sp1, sp3, 130);
        insertEdge(castle, papaya, 85);
        insertEdge(papaya, jkh, 80);
    }

    @Override
    public ArrayList<Step> getShortestPathFromTo(V from, V to) throws IllegalArgumentException {
        if(from == null || to == null || from.equals(to)) throw new IllegalArgumentException("from or to is null or from equals to");

        HashSet<V> visited = new HashSet<>();
        HashMap<V, Integer> distances = new HashMap<>();
        HashMap<V, ArrayList<V>> paths = new HashMap<>();

        dijkstra(from, visited, distances, paths);

        if(paths.get(to)  == null) return null;

        ArrayList<Step> steps = new ArrayList<>();

        for(V v : paths.get(to)){
            steps.add(new Step(v, distances.get(v)));
        }
        Collections.reverse(steps);
        steps.add(new Step (to, distances.get(to)));
        return steps;
    }

    @Override
    public HashMap<String, Integer> getStepsForShortestPathsFrom(V from) {
        if(from == null) throw new IllegalArgumentException("from is null");

        HashSet<V> visited = new HashSet<>();
        HashMap<V, Integer> distances = new HashMap<>();
        HashMap<V, ArrayList<V>> paths = new HashMap<>();

        dijkstra(from, visited, distances, paths);

        HashMap<String, Integer> map = new HashMap<>();

        for(V v : getVertices()){
            if(paths.get(v) == null){
                map.put(v.name, -1);
            } else {
                map.put(v.name, paths.get(v).size());
            }
        }

        return map;
    }

    @Override
    public HashMap<String, Integer> getShortestDistancesFrom(V from) {
        if(from == null) throw new IllegalArgumentException("from is null");

        HashSet<V> visited = new HashSet<>();
        HashMap<V, Integer> distances = new HashMap<>();
        HashMap<V, ArrayList<V>> paths = new HashMap<>();

        dijkstra(from, visited, distances, paths);

        HashMap<String, Integer> p = new HashMap<>();

        for(V v : getVertices()){
            if(distances.get(v) == null){
                p.put(v.name, -1);
            } else {
                p.put(v.name, distances.get(v));
            }
        }

        return p;
    }

	// This (or a similar method) is not mandatory, but a recommendation by us
    /**
     * This method is expected to be called with correctly initialized data structures and recursivly calls itself.
     *
     * @param cur Current vertex being processed
     * @param visited Set which notes already visited vertices.
     * @param distances Map (nVertices entries) which stores the min. distance to each vertex.
     * @param paths Map (nVertices entries) which stores the shortest path to each vertex .
     */
    private void dijkstra(V cur, HashSet<V> visited, HashMap<V, Integer> distances, HashMap<V, ArrayList<V>> paths) {
        if(visited.isEmpty()){              //for the first vertex
            for(V v : getVertices()){
                distances.put(v, -1);
            }
            distances.put(cur, 0);
            ArrayList<V> alv = new ArrayList<>();
            paths.put(cur, alv);
        }
        visited.add(cur);
        for(E e : getEdges()){
            if(cur != e.first || cur != e.second){
                ArrayList<V> alv = new ArrayList<>();
                if(cur.equals(e.first) && !visited.contains(e.second)){
                    if(distances.get(e.second) > findEdge(cur, e.second).weight + distances.get(cur) || distances.get(e.second) == -1){
                        distances.put(e.second, findEdge(cur, e.second).weight + distances.get(cur));

                        alv.add(e.first);
                        alv.addAll(paths.get(e.first));
                        paths.put(e.second, alv);
                    }
                } else if(cur.equals(e.second) && !visited.contains(e.first)){
                    if(distances.get(e.first) > findEdge(e.first, cur).weight + distances.get(cur) || distances.get(e.first) == -1){
                        distances.put(e.first, findEdge(e.first, cur).weight + distances.get(cur));

                        alv.add(e.second);
                        alv.addAll(paths.get(e.second));
                        paths.put(e.first, alv);
                    }
                }
            }
        }
        V next = null;
        int help = Integer.MAX_VALUE;
        for(Map.Entry<V, Integer> entry : distances.entrySet()){
            if(entry.getValue() < help && entry.getValue() != -1 && entry.getValue() != 0 && !visited.contains(entry.getKey())){
                next = entry.getKey();
                help = entry.getValue();
            }
        }
        if(visited.size() != getNumberOfVertices() && next != null){
            dijkstra(next, visited, distances, paths);
        }
    }
}
