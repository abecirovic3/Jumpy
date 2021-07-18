package sample;

public class GameModel {
    private byte[] generatedList = new byte[4];
    private byte[] inputList = new byte[4];

    public GameModel() {
        generatedList = generateList();
    }

    public byte[] generateList() {
        byte[] result = new byte[4];
        byte min = 1, max = 6;
        for (int i=0; i<4; i++) {
            result[i] = (byte)(min + (byte)(Math.random() * ((max - min) + 1)));
        }
        return result;
    }

    public byte[] getGeneratedList() {
        return generatedList;
    }

    public byte[] getInputList() {
        return inputList;
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
}
