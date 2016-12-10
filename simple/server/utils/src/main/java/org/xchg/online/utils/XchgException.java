/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.utils.XchgException
 * Author:              rsankarx
 * Revision:            1.0
 * Date:                21-10-2016
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An exception for functions
 *
 * ************************************************************
 * */

package org.xchg.online.utils;

public class XchgException extends Exception
{
    private int code;

    public XchgException(int cde, String msg)
    {
        super(msg);
        code = cde;
    }
}

