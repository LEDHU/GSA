public class Main {
    public static void main(String[] args) {
            int numVariables = 10;
            int populationSize = 50;
            int maxIterations = 1000;

            GSA gsaRas = new GSA(numVariables, populationSize, maxIterations);
            gsaRas.optimize("Rastrigin");

            GSA gsaRos = new GSA(numVariables, populationSize, maxIterations);
            gsaRos.optimize("Rosenbrock");

            GSA gsaAck = new GSA(numVariables, populationSize, maxIterations);
            gsaAck.optimize("Ackley");

            GSA gsaEsf = new GSA(numVariables, populationSize, maxIterations);
            gsaEsf.optimize("Esfera");

            System.out.println("Valor da função Rastrigin: " + gsaRas.getGlobalBestValue());
            System.out.println("Valor da função Rosenbrock: " + gsaRos.getGlobalBestValue());
            System.out.println("Valor da função Ackley: " + gsaAck.getGlobalBestValue());
            System.out.println("Valor da função Esfera: " + gsaEsf.getGlobalBestValue());
    }
}
