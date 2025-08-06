package com.sport.matches.winline.model;

public class SimpleModel implements ViewModel {

    public int title;
    public int icon;
    public int ic2;

    public int field_size;

    public int id;
    private boolean solved;

//    public DrumpadKey(int i, int i2, int id) {
//        this(i, i2, -1, id);
//    }


    public SimpleModel(int title, int icon, int ic2, int field_size, int id) {
        this.title = title;
        this.icon = icon;
        this.ic2 = ic2;
        this.field_size = field_size;
        this.id = id;
    }

    public SimpleModel(int title, int icon, int ic2, int field_size, int id, boolean solved) {
        this.title = title;
        this.icon = icon;
        this.ic2 = ic2;
        this.field_size = field_size;
        this.id = id;
        this.solved = solved;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
