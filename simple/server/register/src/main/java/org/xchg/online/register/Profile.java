/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.register.Profile
 * Revision:            1.0
 * Date:                09-09-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A register to be displayed to the world
 *
 * ************************************************************
 * */

package org.xchg.online.register;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

public class Profile implements java.io.Serializable
{
    private String email;
    private String name;
    private String phone;
    private String defaultCity;
    private List<String> roles;
    private boolean verified;
    private String verificationCode;

    public Profile(String e, String n, String p, String c, List<String> r)
    {
        email = e;
        name = n;
        phone = p;
        defaultCity = c;
        verified = false;
        roles = r;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getDefaultCity() { return defaultCity; }
    public boolean isVerified() { return verified; }

    public String generateVerificationCode()
    {
        verificationCode = RandomStringUtils.randomAlphanumeric(10);
        return verificationCode;
    }

    public String getVerificationCode() { return verificationCode; }

    public void verify(String code) 
    { 
        if ((verificationCode != null) && verificationCode.equals(code)) 
        {
            verified = true; 
            verificationCode = null;
        }
    }
}

