package mel.dice;

public class DiceUtil
{
	final public static int rollDie()            { return rollDie(6); }
	final public static int rollDice(int amount) { return rollDice(amount, 6); }
	
	final public static int rollDie(int sides) { 
		return (int)(Math.random()*sides)+1; 
	}
	
	final public static int rollDice(int amount, int sides)
	{
		int result = 0;
		for(int i=0; i<amount; i++) result += rollDie(sides);
		return result;
	}
}
