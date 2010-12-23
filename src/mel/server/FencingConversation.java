package mel.server;

public class FencingConversation extends BasicServerConversation
{

    public FencingConversation(String name)
    {
        super(name);
        addSeat("White");
        addSeat("Black");
        //TODO register commands for FencingConversation
    }
    
    //TODO define seats
    
    @Override
    public String getType() { return "Fencing"; }
}
