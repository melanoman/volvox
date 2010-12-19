/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mel.common;

/**
 *
 * @author nicholson
 */
public class Seat
{
    private String name;
    private User user; //first user


    public Seat(String name)
    {
        this.name = name;
        this.user = User.NOBODY;
    }

    public String getName() { return name; }
    public User getUser() { return user; }
    public void setUser(User in) { user = in; }
}
