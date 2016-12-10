/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.register.Verify
 * Author:              rsankarx
 * Revision:            1.0
 * Date:                21-10-2016
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A verification event for SMS verification
 *
 * ************************************************************
 * */

package org.xchg.online.register;

public class Verify implements java.io.Serializable
{
    private String code;
    private String password;

    private String identityType;

    public Verify()
    {
    }

    public void setup()
    {
        identityType = "custom";
    }

    public String getCode() { return code; }
}

