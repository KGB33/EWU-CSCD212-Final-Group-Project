package ai;

public interface CurrentSquare {
    char[] current = {'0', '0'};

    void setCurrent(char file, char rank);

    char[] getCurrent();
}
