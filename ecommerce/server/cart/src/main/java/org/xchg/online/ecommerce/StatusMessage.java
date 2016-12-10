/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.ecommerce.StatusMessage
 * Author:              rsankar
 * Revision:            1.0
 * Date:                19-09-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A status response sent for events
 *
 * ************************************************************
 * */

package org.xchg.online.ecommerce;

public class StatusMessage implements java.io.Serializable
{
    private String message;

    public StatusMessage(String msg)
    {
        message = msg;
    }
}

