package fencing;

public abstract class FencingBaseModel
{
    public static final int STRIP_LENGTH = 23;
    public static final int NO_SIDE = 0;
    public static final int BLACK_SIDE = 0;
    public static final int WHITE_SIDE = 1;
    public static final int FREE_MODE = 0;
    public static final int ATTACK_MODE = 1;
    public static final int PAT_MODE = 2;
    public static final int ENDATTACK_MODE = 3;
    public static final int ENDPAT_MODE = 4;
    public static final int GAMEOVER_MODE = 5;
    
    protected int side;
    protected int whiteHP;
    protected int blackHP;
    protected int whitePos;
    protected int blackPos;
    protected int mode;
    protected int turn;

    // TODO fire changes for setters
    public int getMode() { return mode; }
    public int getSide() { return side; }
    public int getTurn() { return turn; }
    public void setMode(int mode) { this.mode = mode; }
    public void setSide(int side) { this.side = side; }
    public void setTurn(int turn) { this.turn = turn; }
    
    //Strip Model
    public int getWhitePos() { return whitePos; }
    public int getBlackPos() { return blackPos; }
    
    public void setWhitePos(int whitePosition)
    {
        whitePos = whitePosition;
    }
    
    public void setBlackPos(int blackPosition)
    {
        blackPos = blackPosition;
    }
    
  //HitPointModel
    public int getWhiteHP() { return whiteHP; }
    public int getBlackHP() { return blackHP; }
    public void setWhiteHP(int newHP) { whiteHP=newHP; }
    public void setBlackHP(int newHP) { blackHP=newHP; }
    public void loseWhiteHP() { whiteHP--; }
    public void loseBlackHP() { blackHP--; }
}
