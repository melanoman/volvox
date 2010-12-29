package fencing;

import mel.common.Command;
import mel.common.Seat;
import mel.common.User;
import mel.server.BasicServerConversation;

public class FencingConversation extends BasicServerConversation
{
    private FencingServerModel model = new FencingServerModel();

    public FencingConversation(String name)
    {
        super(name);
        addSeat("White");
        addSeat("Black");
        //TODO register commands for FencingConversation
    }
    
    public class RefreshCommand extends AbstractCommand
    {        
        public void execute(User user, String content)
        {
            Seat seat = getSeat(user);
            if(seat == null)
            {
                send(User.MODERATOR, user, 'h', "00000");
                send(User.MODERATOR, user, 's', ""+FencingBaseModel.NO_SIDE);
            }
            else if(seat.getName().equalsIgnoreCase("Black"))
            {
                send(User.MODERATOR, user, 'h', model.getBlackCardValues());
                send(User.MODERATOR, user, 's', ""+FencingBaseModel.BLACK_SIDE);
            }
            else if(seat.getName().equalsIgnoreCase("White"))
            {
                send(User.MODERATOR, user, 'h', model.getWhiteCardValues());
                send(User.MODERATOR, user, 's', ""+FencingBaseModel.WHITE_SIDE);
            }
            
            send(User.MODERATOR, user, 'a', ""+model.getWhiteHP());
            send(User.MODERATOR, user, 'b', ""+model.getBlackHP());
            
            send(User.MODERATOR, user, 'm', ""+model.getMode());
            send(User.MODERATOR, user, 'x', ""+model.getBlackPos());
            send(User.MODERATOR, user, 'y', ""+model.getWhitePos());
        }
    }
    
    @Override
    public String requireAuth() { return null; }
    
    @Override
    public String getType() { return "Fencing"; }
}
