/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.common;

/**
 *
 * @author nicholson
 */
public class User
{
    public static final User NOBODY = new User("");
    public static final User MODERATOR = new User("_");

    private final String name;

    public User(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() { return name; }
    public String getName() { return name; }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof User) return name.equals(((User)o).name);
        return false;
    }
}
