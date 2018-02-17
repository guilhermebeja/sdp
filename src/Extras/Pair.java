package Extras;

import java.io.Serializable;

public class Pair<t, k> implements Serializable{

    private t objectT;
    private k objectK;

    public Pair(t objectT, k objectK){
        this.objectK = objectK;
        this.objectT = objectT;
    }

    public t getLeft() {
        return objectT;
    }

    public void setLeft(t objectT) {
        this.objectT = objectT;
    }

    public k getRight() {
        return objectK;
    }

    public void setRight(k objectK) {
        this.objectK = objectK;
    }
}
