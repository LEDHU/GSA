import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GSA {
    private int numVariables;
    private List<List<Double>> population;
    private List<Double> globalBestSolution;
    private double globalBestValue;
    private double G;
    private double alpha;
    private int maxIterations;

    public GSA(int numVariables, int populationSize, int maxIterations) {
        this.numVariables = numVariables;
        this.maxIterations = maxIterations;

        population = new ArrayList<>();
        globalBestSolution = new ArrayList<>();
        globalBestValue = Double.MAX_VALUE;

        initializePopulation(populationSize);
    }

    private void initializePopulation(int populationSize) {
        Random rand = new Random();

        for (int i = 0; i < populationSize; i++) {
            List<Double> solution = new ArrayList<>();
            for (int j = 0; j < numVariables; j++) {
                solution.add(rand.nextDouble() * 10.0 - 5.0);
            }
            population.add(solution);
        }
    }

    public void optimize(String objectiveFunction) {
        G = 100.0;
        alpha = 0.9;

        Random rand = new Random();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            updateGlobalBest(objectiveFunction);

            for (int i = 0; i < population.size(); i++) {
                List<Double> solution = population.get(i);
                List<Double> newSolution = new ArrayList<>(solution);

                for (int j = 0; j < numVariables; j++) {
                    double r = rand.nextDouble();
                    double force = G * (globalBestValue - evaluateObjectiveFunction(solution, objectiveFunction)) * r;
                    newSolution.set(j, solution.get(j) + force);
                }

                population.set(i, newSolution);
            }

            G *= alpha;
        }
    }

    private void updateGlobalBest(String objectiveFunction) {
        for (List<Double> solution : population) {
            double value = evaluateObjectiveFunction(solution, objectiveFunction);
            if (value < globalBestValue) {
                globalBestValue = value;
                globalBestSolution = new ArrayList<>(solution);
            }
        }
    }

    private double evaluateObjectiveFunction(List<Double> solution, String objectiveFunction) {
        double result = 0.0;
        if ("Rastrigin".equals(objectiveFunction)) {
            for (double x : solution) {
                result += (x * x - 10 * Math.cos(2 * Math.PI * x));
            }
            result += 10 * solution.size();
        } else if ("Rosenbrock".equals(objectiveFunction)) {
            for (int i = 0; i < solution.size() - 1; i++) {
                double xi = solution.get(i);
                double xi1 = solution.get(i + 1);
                result += 100 * Math.pow(xi1 - xi * xi, 2) + Math.pow(xi - 1, 2);
            }
        } else if ("Esfera".equals(objectiveFunction)) {
            for (double x : solution) {
                result += x * x;
            }
        } else if ("Ackley".equals(objectiveFunction)) {
            double sum1 = 0.0;
            double sum2 = 0.0;
            for (double x : solution) {
                sum1 += x * x;
                sum2 += Math.cos(2 * Math.PI * x);
            }
            result = -20.0 * Math.exp(-0.2 * Math.sqrt(sum1 / solution.size())) - Math.exp(sum2 / solution.size()) + 20.0 + Math.exp(1);
        }
        return result;
    }

    public List<Double> getGlobalBestSolution() {
        return globalBestSolution;
    }

    public double getGlobalBestValue() {
        return globalBestValue;
    }

}
