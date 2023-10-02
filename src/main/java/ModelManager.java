import java.util.HashMap;

public class ModelManager {

    private static HashMap<Integer, Model> models;
    private static ModelManager instance;

    // Private constructor to prevent instantiation
    private ModelManager() {
        models = new HashMap<>();
    }

    // Public method to provide access to the instance
    public static ModelManager getInstance() {
        if (instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }

    public void createNewGeneration(int generation, int[] bestModels) {
        for (int i = 0; i < 5; i++){
            for (int e = 0; e < 7; e++){
                int modelID = ((i*7)+e) + (generation * 100);
                if (bestModels != null){
                    System.out.println("Created model " + modelID + " for generation " + generation + " from existing model");
                    models.put(modelID, new Model(models.get(bestModels[i]), generation, modelID, false));
                }
                else {
                    System.out.println("Created model " + modelID + " for generation " + generation + " from random model");
                    models.put(modelID, new Model(null, generation, modelID, false));
                }
            }
        }
    }

    public keyOutput[] getNextMoveProbability(int modelID, int[] inputs) {
        Model model = models.get(modelID);
        keyOutput[] errorOutput = new keyOutput[]{keyOutput.ERROR, keyOutput.ERROR, keyOutput.ERROR, keyOutput.ERROR};
        if (!models.containsKey(modelID)) return errorOutput;
        return model.runOutput(inputs);
    }
}
