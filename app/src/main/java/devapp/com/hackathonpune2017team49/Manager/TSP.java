package devapp.com.hackathonpune2017team49.Manager;

import android.util.Log;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Stack;

/**
 * Created by root on 23/9/17.
 */

public class TSP {


    private static String TAG = "Test";
    public static ArrayList<Integer> resultNodes = new ArrayList<>();

    public int numberOfNodes = 0;
    private Stack<Double> stack;



    public TSP()

    {
        Log.d(TAG, "TSP: in constructor");

        stack = new Stack<Double>();

    }



    public void tsp(double adjacencyMatrix[][] , int numberOfNodes)

    {

        this.numberOfNodes = numberOfNodes;

        numberOfNodes = adjacencyMatrix[1].length - 1;

        double[] visited = new double[numberOfNodes + 1];

        visited[1] = 1;

        stack.push(1.0);

        int dst = 0 ;

        double element;
        int  i;
        double min = 99999;

        boolean minFlag = false;

        System.out.print(1 + "\t");



        while (!stack.isEmpty())

        {

            element = stack.peek();

            i = 1;

            min = Integer.MAX_VALUE;

            while (i <= numberOfNodes)

            {

                if (adjacencyMatrix[(int) element][i] > 1 && visited[i] == 0)

                {

                    if (min > adjacencyMatrix[(int) element][i])

                    {

                        min = adjacencyMatrix[(int) element][i];

                        dst = i;

                        minFlag = true;

                    }

                }

                i++;

            }

            if (minFlag)

            {

                visited[dst] = 1;

                stack.push((double) dst);
                Log.d(TAG, "tsp: adding node "+dst);
                resultNodes.add(dst);

                System.out.print(dst + "\t");

                minFlag = false;

                continue;

            }

            stack.pop();

        }

    }





    public static ArrayList<Integer> getMinDistance(double[][] adjacency_matrix , int count)

    {

        Log.d(TAG, "getMinDistance: ");
            System.out.println("the cities are visited as follows");

            TSP tspNearestNeighbour = new TSP();

           tspNearestNeighbour.tsp(adjacency_matrix , count);
        return resultNodes;

    }
}


// 18.5831363 lon = 73.7419016