public class Testing {
    public static void main(String[] args) {
        ModelManager manager = new ModelManager();
        manager.createNewGeneration(0);
        int[] testInputs = new int[]{0,0,0,0,2,0,0,0,2,0,0,0,0,0,0,0};
        System.out.println(manager.getNextMove(0, testInputs).name());
        System.out.println(manager.getNextMove(0, testInputs).name());

    }
}
