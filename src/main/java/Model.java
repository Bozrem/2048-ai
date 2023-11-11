import java.util.Arrays;
import java.util.Random;

public class Model {
    
    private double[][] weightsToN1; //[input][n1]
    private double[][] weightsToOut; //[n1][output]

    private double[] biasN1;
    private double[] biasOut;
    private static final int INPUT_NEURONS = 16;
    private static final int HIDDEN_1_NEURONS = 20;
    private static final int OUT_NEURONS = 4;
    private final Random random = new Random();

    public Model(Model previousModel, int generation, int modelID, boolean zeroModel) {
        if (zeroModel) {
            weightsToN1 = new double[INPUT_NEURONS][HIDDEN_1_NEURONS];
            weightsToOut = new double[INPUT_NEURONS][HIDDEN_1_NEURONS];

        }
        else if (previousModel == null) initializeModel();
        else{
            mutateModel(previousModel, generation);
        }
    }

    private void mutateModel(Model previousModel, int generation){
        biasOut = new double[OUT_NEURONS];
        biasN1 = new double[HIDDEN_1_NEURONS];
        weightsToN1 = new double[INPUT_NEURONS][HIDDEN_1_NEURONS];
        weightsToOut = new double[HIDDEN_1_NEURONS][OUT_NEURONS];
        for (int i = 0; i < INPUT_NEURONS; i++) {
            for (int e = 0; e < HIDDEN_1_NEURONS; e++) {
                double[][] previousWeights = previousModel.getWeightsToN1();
                weightsToN1[i][e] = previousWeights[i][e] + getGaussianRandom(1.0/(generation+1.0));
            }
        }
        for (int i = 0; i < HIDDEN_1_NEURONS; i++) {
            for (int e = 0; e < OUT_NEURONS; e++) {
                double[][] previousWeights = previousModel.getWeightsToOut();
                weightsToOut[i][e] = previousWeights[i][e] + getGaussianRandom(1.0/(generation+1.0));
            }
        }
        for (int i = 0; i < HIDDEN_1_NEURONS; i++){
            biasN1[i] = previousModel.biasN1[i] + getGaussianRandom(1.0/(generation+1.0));
        }
        for (int i = 0; i < OUT_NEURONS; i++){
            biasOut[i] = previousModel.biasOut[i] + getGaussianRandom(1.0/(generation+1.0));
        }
    }

    private void initializeModel() {
        biasOut = new double[OUT_NEURONS];
        biasN1 = new double[HIDDEN_1_NEURONS];
        weightsToN1 = new double[INPUT_NEURONS][HIDDEN_1_NEURONS];
        weightsToOut = new double[HIDDEN_1_NEURONS][OUT_NEURONS];
        initializeWeightsXavier();
    } // Generates a model with somewhat random weighting, for first time use

    public void initializeWeightsXavier() {
        double varianceN1 = 2.0 / (INPUT_NEURONS + HIDDEN_1_NEURONS);
        double varianceOut = 2.0 / (HIDDEN_1_NEURONS + OUT_NEURONS);
        for (int i = 0; i < INPUT_NEURONS; i++) {
            for (int j = 0; j < HIDDEN_1_NEURONS; j++) {
                weightsToN1[i][j] = (Math.random() - 0.5) * 4 * Math.sqrt(varianceN1);
            }
        }
        for (int i = 0; i < HIDDEN_1_NEURONS; i++) {
            for (int j = 0; j < OUT_NEURONS; j++) {
                weightsToOut[i][j] = (Math.random() - 0.5) * 4 * Math.sqrt(varianceOut);
            }
        }
    } // Using Xavier distribution to somewhat randomly initialize the weights

    private double getGaussianRandom(double temperature){
        return random.nextGaussian() * temperature;
    }

    public keyOutput[] runOutput(int[] inputs) {
        double[] filteredInputs = filterInputs(inputs);
        // Calculate n1
        double[] n1Nodes = computeLayer(filteredInputs, INPUT_NEURONS, HIDDEN_1_NEURONS, biasN1, weightsToN1);
        double[] outNodes = computeLayer(n1Nodes, HIDDEN_1_NEURONS, OUT_NEURONS, biasOut, weightsToOut);
        return getNodeProbabilityOrder(outNodes);
    } // Runs the model to get the models move for a given grid

    private keyOutput[] getNodeProbabilityOrder(double[] nodes) {
        // Create an array of indices
        Integer[] indices = new Integer[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            indices[i] = i;
        }
        Arrays.sort(indices, (i1, i2) -> Double.compare(nodes[i2], nodes[i1]));
        keyOutput[] orderedKeys = new keyOutput[4];

        // Fill the orderedKeys array based on the sorted indices
        for (int i = 0; i < indices.length; i++) {
            switch (indices[i]) {
                case 0 -> orderedKeys[i] = keyOutput.UP;
                case 1 -> orderedKeys[i] = keyOutput.DOWN;
                case 2 -> orderedKeys[i] = keyOutput.LEFT;
                case 3 -> orderedKeys[i] = keyOutput.RIGHT;
                default -> orderedKeys[i] = keyOutput.ERROR;
            }
        }
        return orderedKeys;
    }


    private double[] computeLayer(double[] inputs, int numOfNodesIn, int numOfNodesOut, double[] biasNodes, double[][] weights) {
        double[] outNodes = new double[numOfNodesOut];
        for (int i = 0; i < numOfNodesOut; i++) {
            double sum = 0;
            // add the input * the weight from that input to the current n1
            for (int e = 0; e < numOfNodesIn; e++) {
                sum += (weights[e][i] * inputs[e]);
            }
            outNodes[i] = sigmoid(sum + biasNodes[i]);
        }
        return outNodes;
    }

    private double[] filterInputs(int[] inputs) {
        double[] filteredInputs = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] != 0 && Math.log(inputs[i]) / Math.log(2) != Math.floor(Math.log(inputs[i]) / Math.log(2))) {
                System.out.println("ERROR: Inputs are not powers of 2");
            }
            if (inputs[i] != 0) filteredInputs[i] = (Math.log(inputs[i]) / Math.log(2));
            //System.out.println(inputs[i]);
        }
        return filteredInputs;
    } // Take the log base 2 of inputs to avoid exploding gradient problem with large numbers

    public double sigmoid(double x) {
        return (1.0 / (1 + Math.exp(-1.0 * x)));
    } // Activation function

    public double[][] getWeightsToN1() {
        return weightsToN1;
    }

    public double[][] getWeightsToOut() {
        return weightsToOut;
    }

    public double[] getBiasN1() {
        return biasN1;
    }

    public double[] getBiasOut() {
        return biasOut;
    }
}
