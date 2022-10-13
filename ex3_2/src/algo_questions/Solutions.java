package algo_questions;

import java.util.Arrays;

public class Solutions {

    public static final double SQRT5 = Math.sqrt(5);
    public static final double FIB_CONSTANT_PLUS = (1 + Math.sqrt(5)) / 2;
    public static final double FIB_CONSTANT_MINUS = (1 - Math.sqrt(5)) / 2;

    /**
     * Method computing the maximal amount of tasks out of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time slot is grater than
     * the no. of hours needed to complete the task.
     *
     * @param tasks     array of integers of length n. tasks[i] is the time in hours required to complete task i.
     * @param timeSlots array of integers of length m. timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots) {
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        int tasksCounter = 0, timeSlotsCounter = 0;
        while (tasksCounter < tasks.length && timeSlotsCounter < timeSlots.length) {
            if (tasks[tasksCounter] <= timeSlots[timeSlotsCounter]) {
                tasksCounter++;
            }
            timeSlotsCounter++;
        }
        return tasksCounter;
    }

    /**
     * Method computing the nim amount of leaps a frog needs to jumb across n waterlily leaves, from leaf 1 to leaf n.
     * The leaves vary in size and how stable they are, so some leaves allow larger leaps than others.
     * leapNum[i] is an integer telling you how many leaves ahead you can jump from leaf i. If leapNum[3]=4,
     * the frog can jump from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     *
     * @param leapNum array of ints. leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum) {
        //TODO: fix runTime in 100000000
        if (leapNum.length <= 1) {
            return 0;
        }
        int[] memory = new int[leapNum.length];
        for (int i = 1; i < leapNum.length; i++) {
            for (int j = 0; j < i; j++) {
                if (i <= j + leapNum[j]) {
                    memory[i] = memory[j] + 1;
                    break;
                }
            }
        }
        return memory[memory.length - 1];
    }

    /**
     * Method computing the solution to the following problem: A boy is filling the water trough for his father's cows
     * in their village. The trough holds n liters of water. With every trip to the village well, he can return using
     * either the 2 bucket yoke, or simply with a single bucket. A bucket holds 1 liter. In how many different ways
     * can he fill the water trough?
     * n can be assumed to be greater or equal to 0, less than or equal to 48 (0<= n <=48)
     *
     * @param n liters of water that the trough holds
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n) {
        if (n <= 1) {
            return 1;
        }
        return (int) (1 / SQRT5 * Math.pow(FIB_CONSTANT_PLUS, n + 1) - 1 / SQRT5 * Math.pow(FIB_CONSTANT_MINUS, n + 1));

    }

    /**
     * Method computing the solution to the following problem: Given an integer n, return the number of structurally
     * unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n. You can assume n is
     * at least 1 and at most 19. (Definition: two trees S and T are structurally distinct if one can not be obtained
     * from the other by renaming of the nodes.) (credit: LeetCode)
     *
     * @param n
     * @return valid output of algorithm.
     */
    public static int numTrees(int n) {
        int[] memory = new int[n + 1];
        memory[0] = memory[1] = 1;
        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                memory[i] += memory[j - 1] * memory[i - j];
            }
        }
        return memory[n];
    }

}
