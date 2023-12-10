import java.util.ArrayList;
import java.util.List;

public class QuadTree implements Cloneable{
    private static final int MAX_POINTS = 1;
    private Region area;
    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<QuadTree> quadTrees = new ArrayList<>();
    private StringBuilder searchTraversePath;

    public QuadTree(Region area) {
        this.area = area;
    }

    @Override
    public Object clone(){ // clone object as a global clock 
        QuadTree q = new QuadTree((Region) this.area.clone());
        q.setPoints((ArrayList)this.points.clone());
        q.setQuadTrees((ArrayList)this.quadTrees.clone());

        return q;
    }

    public boolean addPoint(Point point) {
        if (this.area.containsPoint(point)) {
            if (this.points.size() < MAX_POINTS) {
                this.points.add(point);
                return true;
            } else {
                if (this.quadTrees.size() == 0) {
                    createQuadrants();
                }
                return addPointToOneQuadrant(point);
            }
        }
        return false;
    }

    private boolean addPointToOneQuadrant(Point point) {
        boolean isPointAdded;
        for (int i = 0; i < 4; i++) {
            isPointAdded = this.quadTrees.get(i).addPoint(point);
            if (isPointAdded)
                return true;
        }
        return false;
    }

    private void createQuadrants() {
        Region region;
        for (int i = 0; i < 4; i++) {
            region = this.area.getQuadrant(i);
            quadTrees.add(new QuadTree(region));
        }
    }

    public List<Point> search(Region searchRegion, List<Point> matches, String depthIndicator) {
        searchTraversePath = new StringBuilder();
        if (matches == null) {
            matches = new ArrayList<Point>();
            searchTraversePath.append(depthIndicator)
                .append("Search Boundary =")
                .append(searchRegion)
                .append("\n");
        }
        if (!this.area.doesOverlap(searchRegion)) {
            return matches;
        } else {
            for (Point point : points) {
                if (searchRegion.containsPoint(point)) {
                    searchTraversePath.append(depthIndicator)
                    .append("Found match " + point)
                    .append("\n");
                    matches.add(point);
                }
            }
            if (this.quadTrees.size() > 0) {
                for (int i = 0; i < 4; i++) {
                    searchTraversePath.append(depthIndicator)
                        .append("Q")
                        .append(i)
                        .append("-->")
                        .append(quadTrees.get(i).area)
                        .append("\n");
                    quadTrees.get(i)
                        .search(searchRegion, matches, depthIndicator + "\t");
                    this.searchTraversePath.append(quadTrees.get(i)
                        .printSearchTraversePath());
                }
            }
        }
        return matches;
    }

    public String printTree(String depthIndicator) {
        String str = "";
        if (depthIndicator == "") {
            str += "Root-->" + area.toString() + "\n";
        }

        for (Point point : points) {
            str += depthIndicator + point.toString() + "\n";
        }
        for (int i = 0; i < quadTrees.size(); i++) {
            str += depthIndicator + "Q" + String.valueOf(i) + "-->" + quadTrees.get(i).area.toString() + "\n";
            str += quadTrees.get(i)
                .printTree(depthIndicator + "\t");
        }
        return str;
    }

    public String printSearchTraversePath() {
        return searchTraversePath.toString();
    }

    public List<QuadTree> getQuadTrees() {
        return quadTrees;
    }

    public Region getArea() {
        return area;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setArea(Region area) {
        this.area = area;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public void setQuadTrees(ArrayList<QuadTree> quadTrees) {
        this.quadTrees = quadTrees;
    }
}