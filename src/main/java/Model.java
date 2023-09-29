public class Model {
    int modelID;
    double[][] weightsToN1; //[input][n1]
    double[][] weightsToOut; //[n1][output]

    double[] biasN1;
    double[] biasOut;
    final int numOfInputs = 16;
    final int numOfN1 = 8;
    final int numOfOut = 4;

    public Model(Model previousModel, int generation, int modelID) {
        if (previousModel == null && generation == 0) randomizeModel();
        this.modelID = modelID;
    }

    private void randomizeModel() {
        biasOut = new double[numOfOut];
        biasN1 = new double[numOfN1];
        weightsToN1 = new double[numOfN1][numOfInputs];
        weightsToOut = new double[numOfOut][numOfN1];
        initializeWeightsXavier();
    } // Generates a model with somewhat random weighting, for first time use

    public void initializeWeightsXavier() {
        double varianceN1 = 2.0 / (numOfInputs + numOfN1);
        double varianceOut = 2.0 / (numOfN1 + numOfOut);
        for (int i = 0; i < numOfN1; i++) {
            for (int j = 0; j < numOfInputs; j++) {
                weightsToN1[i][j] = (Math.random() - 0.5) * 2 * Math.sqrt(varianceN1);
            }
        }
        for (int i = 0; i < numOfOut; i++) {
            for (int j = 0; j < numOfN1; j++) {
                weightsToOut[i][j] = (Math.random() - 0.5) * 2 * Math.sqrt(varianceOut);
            }
        }
    } // Using Xavier distribution to somewhat randomly initialize the weights

    public keyOutput runOutput(int[] inputs) {
        double[] filteredInputs = filterInputs(inputs);
        // Calculate n1
        double[] n1Nodes = computeLayer(filteredInputs, numOfInputs, numOfN1, biasN1, weightsToN1);
        double[] outNodes = computeLayer(n1Nodes, numOfN1, numOfOut, biasOut, weightsToOut);
        return getBestNode(outNodes);
    } // Runs the model to get the models move for a given grid

    private keyOutput getBestNode(double[] nodes) {
        int likelyKey = 0;
        double highestOutput = nodes[0];
        for (int i = 1; i < numOfOut; i++) {
            if (nodes[i] > highestOutput) {
                likelyKey = i;
                highestOutput = nodes[i];
            }
        }
        switch (likelyKey) {
            case 0:
                return keyOutput.UP;
            case 1:
                return keyOutput.DOWN;
            case 2:
                return keyOutput.LEFT;
            case 3:
                return keyOutput.RIGHT;
            default:
                return keyOutput.ERROR;
        }
    } // Get the highest likelihood output

    private double[] computeLayer(double[] inputs, int numOfNodesIn, int numOfNodesOut, double[] biasNodes, double[][] weights) {
        double[] outNodes = new double[numOfNodesOut];
        for (int i = 0; i < numOfNodesOut; i++) {
            double sum = 0;
            // add the input * the weight from that input to the current n1
            for (int e = 0; e < numOfNodesIn; e++) {
                sum += (weights[i][e] * inputs[e]);
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
            System.out.println(inputs[i]);
        }
        return filteredInputs;
    } // Take the log base 2 of inputs to avoid exploding gradient problem with large numbers

    public double sigmoid(double x) {
        return (1.0 / (1 + Math.exp(-1.0 * x)));
    } // Activation function
}
