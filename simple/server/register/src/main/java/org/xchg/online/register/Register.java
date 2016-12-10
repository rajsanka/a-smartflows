/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.register.Register
 * Author:              rsankarx
 * Revision:            1.0
 * Date:                21-10-2016
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An event to register an user
 *
 * ************************************************************
 * */

package org.xchg.online.register;

import java.util.List;

public class Register implements java.io.Serializable
{
    private String name;
    private String email;
    private String phone;
    private String defaultCity;
    private List<String> roles;

    private String emailMsg;
    private String SMSmsg;

    private Profile existing;

    public Register()
    {
    }

    void setExist(Profile e)
    {
        existing = e;
    }

    public Profile createProfile()
    {
        Profile p = new Profile(email, name, phone, defaultCity, roles);
        String verificationcode = p.generateVerificationCode();
        SMSmsg = "Your verification code is " + verificationcode;
        return p;
    }

    public boolean isAlreadyExisting()
    {
        return (existing != null);
    }

    public boolean isVerified()
    {
        return ((existing != null) && (existing.isVerified()));
    }
}

