/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.register.MessageResponse
 * Author:              rsankarx
 * Revision:            1.0
 * Date:                21-10-2016
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A response with simple messages
 *
 * ************************************************************
 * */

package org.xchg.online.register;

public class MessageResponse implements java.io.Serializable
{
    private String message;

    public MessageResponse(String msg)
    {
        message = msg;
    }
}

