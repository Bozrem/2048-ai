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

    public void createNewGeneration(int generation, Model[] bestModels) {
        for (int i = 0; i < 35; i++) {
            int modelID = i + (generation * 100);
            models.put(modelID, new Model(null, generation, modelID));
        }
    }

    public keyOutput[] getNextMoveProbability(int modelID, int[] inputs) {
        Model model = models.get(modelID);
        keyOutput[] errorOutput = new keyOutput[]{keyOutput.ERROR, keyOutput.ERROR, keyOutput.ERROR, keyOutput.ERROR};
        if (!models.containsKey(modelID)) return errorOutput;
        return model.runOutput(inputs);
    }
}
