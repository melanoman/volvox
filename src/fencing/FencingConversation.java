package fencing;

import mel.common.Command;
import mel.common.User;
import mel.server.BasicServerConversation;

public class FencingConversation extends BasicServerConversation
{

    public FencingConversation(String name)
    {
        super(name);
        addSeat("White");
        addSeat("Black");
        //TODO register commands for FencingConversation
    }
    
    public class RefreshCommand implements Command
    {
        public void execute(String userName, String content)
        {
            execute(new User(userName), content);
        }
        
        public void execute(User user, String content)
        {
            //send(User.MODERATOR, user, a, )
        }
    }
    
    @Override
    public String requireAuth() { return null; }
    
    @Override
    public String getType() { return "Fencing"; }
}
