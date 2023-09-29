import java.util.HashMap;

public class ModelManager {

    private HashMap<Integer, Model> models;
    public ModelManager(){
        models = new HashMap<>();
    }

    public void createNewGeneration(int generation){
        for (int i = 0; i < 35; i++){
            models.put(0, new Model(null, generation, i*(generation+1)));
        }
    }
    public keyOutput getNextMove(int modelID, int[] inputs){
        Model model = models.get(modelID);
        return model.runOutput(inputs);
    }

}
