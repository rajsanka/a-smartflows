/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.xchg.online.register.RegistrationManager
 * Author:              rsankarx
 * Revision:            1.0
 * Date:                21-10-2016
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A manager to register new users
 *
 * ************************************************************
 * */

package org.xchg.online.register;

import org.xchg.online.utils.XchgException;

public class RegistrationManager
{
    public RegistrationManager()
    {
    }

    public boolean registerProfile(Register reg, Profile exist)
        throws XchgException
    {
        reg.setExist(exist);
        if (reg.isAlreadyExisting() && reg.isVerified())
        {
            MessageResponse resp = new MessageResponse("The email is already registered. Please Login.");
            return false;
        }
        else if (reg.isAlreadyExisting())
        {
            MessageResponse resp = new MessageResponse("The email is already registered. Please Verify to Login.");
            return false;
        }

        Profile p = reg.createProfile();
        System.out.println("RegistrationManager: " + p.getVerificationCode());
        MessageResponse resp = new MessageResponse("Please Verify your registration to proceed.");
        return true;
    }

    public boolean verifyProfile(Verify verify, Profile profile)
        throws XchgException
    {
        if (profile.isVerified())
        {
            MessageResponse resp = new MessageResponse("Already verified. Please login.");
            return false;
        }

        profile.verify(verify.getCode());
        if (profile.isVerified())
        {
            MessageResponse resp = new MessageResponse("Successfully verified the phone. Please login.");
            verify.setup();
            return true;
        }

        MessageResponse resp = new MessageResponse("The code entered is wrong. Please verify with the correct code.");
        return false;
    }

}

