import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class main {
    public static void main(String[] args) {
        QuadTreeSearchUnitTest qts = new QuadTreeSearchUnitTest();
        QuadTreeSearchUnitTest.setUp();
        int[][] rectangles = {
                {0, 0, 20, 20},
                {10, 10, 40, 40},
                {20, 20, 60, 60},
                {30, 30, 80, 80},
                {40, 40, 100, 100},
                {50, 50, 120, 120},
                {60, 60, 140, 140},
                {70, 70, 160, 160},
                {80, 80, 180, 180},
                {90, 90, 200, 200}
        };

        float[][][] points = {
                {{5, 10}, {10, 15}},
                {{15, 20}, {25, 30}, {10, 15}, {20, 25}, {30, 35}},
                {{25, 30}, {35, 40}, {20, 25}, {30, 35}, {40, 45}, {50, 55}},
                {{35, 40}, {30, 35},{40, 45}, {45, 50}, {55, 60}, {65, 70}, {50, 55}, {60, 65}, {70, 75}},
                {{40, 45}, {45, 50}, {55, 60}, {65, 70}, {50, 55}, {60, 65}, {70, 75}, {75, 80}, {80, 85}, {85, 90},{90, 95}},
                {{55, 60}, {65, 70}, {75, 80}, {85, 90}, {95, 100}, {50, 55}, {60, 65}, {70, 75}, {80, 85}, {90, 95}, {100, 105}, {110, 115}},
                {{65, 70}, {75, 80}, {85, 90}, {95, 100}, {60, 65}, {70, 75}, {80, 85}, {90, 95}, {100, 105}, {110, 115}, {120, 125}, {130, 135}},
                {{75, 80}, {85, 90}, {95, 100}, {70, 75}, {80, 85}, {90, 95}, {100, 105}, {110, 115}, {120, 125}, {130, 135}, {140, 145}, {150, 155}},
                {{85, 90}, {95, 100}, {80, 85}, {90, 95}, {100, 105}, {110, 115}, {120, 125}, {130, 135}, {140, 145}, {150, 155}},
                {{95, 100}, {90, 95}, {100, 105}, {110, 115}, {120, 125}, {130, 135}, {140, 145}, {150, 155}}
        };
//        qts.givenQuadTree_whenSearchingForRange_thenReturn1MatchingItem(rectangles[4], points[4]);

        ExecutorService executorService = Executors.newFixedThreadPool(rectangles.length);
        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 0; i < rectangles.length; i++) {
            final int index = i;
            Future<Long> future = executorService.submit(() -> {
                long startTime = System.nanoTime();
                qts.givenQuadTree_whenSearchingForRange_thenReturn1MatchingItem(rectangles[index], points[index]);
                long endTime = System.nanoTime();
                return endTime - startTime;
            });
            futures.add(future);
        }

        // Shut down the executor service after all tasks are submitted
        executorService.shutdown();

        // Collect and print the execution times
        for (int i = 0; i < rectangles.length; i++) {
            try {
                long executionTime = futures.get(i).get();
                System.out.println("Thread " + (i + 1) + " execution time: " + executionTime + " nanoseconds");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


    }
}
