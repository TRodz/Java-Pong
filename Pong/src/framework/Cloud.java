package framework;

public class Cloud {

    private int cX, cY;
    public int cDx = -1;

    public Cloud(int x, int y) {
        cX = x;
        cY = y;
    }

    public void update() {

        cX += cDx;

        if (cX < -900) {
            cX += 1800;
        }

    }

    public int getcX() {
        return cX;
    }

    public int getcY() {
        return cY;
    }
}
