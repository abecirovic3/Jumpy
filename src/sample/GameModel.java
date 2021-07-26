package sample;

public class GameModel {
    private static GameModel instance;

    public byte[] generatedList;
    public byte[] inputList = new byte[4];

    public Difficulty difficulty;

    public byte activeRow = 0, activeColumn = 0;
    public boolean firstAction = true;
    public boolean gameEnded = false;

    public static GameModel getInstance() {
        if (instance == null) instance = new GameModel();
        return instance;
    }

    private GameModel() {}

    public void generateList() {
        generatedList = new byte[4];
        byte min = 1, max = 6;
        for (int i=0; i<4; i++) {
            generatedList[i] = (byte)(min + (byte)(Math.random() * ((max - min) + 1)));
        }
    }

    public byte[] validateInput() {
        byte[] result = new byte[2];    // result[0] number of full hits result[1] number of half hits

        byte[] copyOfGeneratedList = new byte[4];
        System.arraycopy(generatedList, 0, copyOfGeneratedList, 0, 4);

        byte fullHitsCounter = 0;
        // full hits loop
        for (int i=0; i<4; i++) {
            if (inputList[i] == copyOfGeneratedList[i]) {
                fullHitsCounter++;
                inputList[i] = -1;
                copyOfGeneratedList[i] = -1;
            }
        }

        byte halfHitsCounter = 0;
        //half hits loop
        for(int i=0; i<4; i++) {
            if (inputList[i] != -1) {
                for( int j=0; j<4; j++) {
                    if(inputList[i] == copyOfGeneratedList[j]) {
                        copyOfGeneratedList[j] = -1;
                        halfHitsCounter++;
                    }
                }
            }
        }

        result[0] = fullHitsCounter;
        result[1] = halfHitsCounter;

        return result;
    }

    public void clearInputList() {
        for (int i = 0; i < 4; i++)
            inputList[i] = -1;
    }

    public void endGame() {
        activeRow = 0;
        activeColumn = 0;
        firstAction = true;
        gameEnded = false;
    }
}
