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
        SendVerifyEmail verify = new SendVerifyEmail(p.getEmail(), createSubject(p), createMessage(p));
        MessageResponse resp = new MessageResponse("Please Verify your registration to proceed.");
        return true;
    }

    private String createSubject(Profile reg)
    {
        return "Please verify your email " + reg.getEmail() + " for the registered Application";
    }

    private String createMessage(Profile reg)
    {
        String message = "";
        message += " Thank you for signing up to use the Application. We do not have a record of your Email and number. \n\n";
        message += " Please verify your email with the below code, so that you can get the access once approved: \n\n ";

        message += " userId: " + reg.getEmail() + "\n";
        message += " uniqueCode: " + reg.getVerificationCode() + "\n";
        message += " email: " + reg.getEmail() + "\n\n";

        message += "Thanx From Team\n";

        return message;
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

