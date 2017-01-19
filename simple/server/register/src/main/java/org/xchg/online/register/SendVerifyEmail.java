/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.register.SendVerifyEmail
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

public class SendVerifyEmail implements java.io.Serializable
{
    private String email;
    private String subject;
    private String message;
    private String ccemail;

    public SendVerifyEmail(String e, String s, String msg)
    {
        email = e;
        subject = s;
        message = msg;
        ccemail = null;
    }
}

