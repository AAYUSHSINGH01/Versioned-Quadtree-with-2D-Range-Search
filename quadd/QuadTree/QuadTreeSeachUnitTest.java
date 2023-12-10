import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QuadVersionTree{
    QuadTree quad;
    long timestamp;

    QuadVersionTree(QuadTree quad,long timestamp){
        this.quad = quad;
        this.timestamp = timestamp;
    }
}

class QuadTreeSearchUnitTest {


    private static QuadTree quadTree;

    @BeforeAll
    public static void setUp() {
        Region area = new Region(0, 0, 400, 400);
        quadTree = new QuadTree(area);
        ArrayList<QuadVersionTree> timestamp = new ArrayList<>();
        Date date = new Date();

        float[][] points = new float[][] { {5, 10},
                {15, 20},
                {25, 30},
                {35, 40},
                {45, 50},
                {55, 60},
                {65, 70},
                {75, 80},
                {85, 90},
                {95, 100},
                {10, 15},
                {20, 25},
                {30, 35},
                {40, 45},
                {50, 55},
                {60, 65},
                {70, 75},
                {80, 85},
                {90, 95},
                {100, 105},
                {110, 115},
                {120, 125},
                {130, 135},
                {140, 145},
                {150, 155} };

        for (int i = 0; i < points.length; i++) {
            Point point = new Point(points[i][0], points[i][1],date.getTime());
            quadTree.addPoint(point);
            // versioned list 
            timestamp.add(new QuadVersionTree(quadTree,date.getTime()));

            quadTree.printTree("Level");

            quadTree = (QuadTree) quadTree.clone();

        }

    }

    private static boolean areArraysEqual(float[][] arr1, float[][] arr2) {
        if (arr1.length != arr2.length || arr1[0].length != arr2[0].length) {
            return false; // Arrays have different dimensions
        }

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    return false; // Elements at position (i, j) are different
                }
            }
        }

        return true; // Arrays are equal
    }

    void givenQuadTree_whenSearchingForRange_thenReturn1MatchingItem(int[] reg, float[][] r) {
        Region searchArea = new Region(reg[0], reg[1], reg[2], reg[3]);
        List<Point> result = quadTree.search(searchArea, null, "");
        System.out.print(result);
        float[][] pointsArray = new float[result.size()][2];

        for (int i = 0; i < result.size(); i++) {
            pointsArray[i][0] = result.get(i).getX();
            pointsArray[i][1] = result.get(i).getY();
        }
        boolean res = areArraysEqual(pointsArray, r);
        System.out.print(res);
    }

    @Test
    void givenQuadTree_whenSearchingForRange_thenReturn2MatchingItems() {
        Region searchArea = new Region(0, 0, 100, 100);
        List<Point> result = quadTree.search(searchArea, null, "");


        assertEquals(2, result.size());
        assertArrayEquals(new float[] { 21, 25 },
            new float[]{result.get(0).getX(), result.get(0).getY() }, 0);
        assertArrayEquals(new float[] { 55, 53 },
            new float[]{result.get(1).getX(), result.get(1).getY() }, 0);

    }
}