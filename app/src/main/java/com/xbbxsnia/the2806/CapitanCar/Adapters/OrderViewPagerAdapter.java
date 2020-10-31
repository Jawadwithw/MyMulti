package com.xbbxsnia.the2806.CapitanCar.Adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xbbxsnia.the2806.CapitanCar.AdressContract.AddressFragment;
import com.xbbxsnia.the2806.CapitanCar.DateInfoContract.DateInfoFragment;
import com.xbbxsnia.the2806.CapitanCar.FinishContract.FinishFragment;
import com.xbbxsnia.the2806.CapitanCar.PersonalInfoContract.PersonalInfoFragment;
import com.xbbxsnia.the2806.CapitanCar.ProblemInfoContract.ProblemInfoFragment;
import com.xbbxsnia.the2806.CapitanCar.SingupContract.SignUpFragment;
import com.xbbxsnia.the2806.CapitanCar.VerificationContract.VerificationFragment;

public class OrderViewPagerAdapter extends FragmentPagerAdapter {
    private PersonalInfoFragment personalInfoFragment;
    private ProblemInfoFragment problemInfoFragment;
    private DateInfoFragment dateInfoFragment;
    private AddressFragment addressFragment;
    private SignUpFragment signUpFragment;
    private VerificationFragment verificationFragment;
    private FinishFragment finishFragment;

    public OrderViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return 7;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                personalInfoFragment = (PersonalInfoFragment) new PersonalInfoFragment().newInstance();
                return personalInfoFragment;
            case 1:
                problemInfoFragment = (ProblemInfoFragment) new ProblemInfoFragment().newInstance();
                return problemInfoFragment;
            case 2:
                dateInfoFragment = (DateInfoFragment) new DateInfoFragment().newInstance();
                return dateInfoFragment;
            case 3:
                addressFragment = (AddressFragment) new AddressFragment().newInstance();
                return addressFragment;
            case 4:
                signUpFragment = (SignUpFragment) new SignUpFragment().newInstance();
                return signUpFragment;
            case 5:
                verificationFragment = (VerificationFragment) new VerificationFragment().newInstance();
                return verificationFragment;
            case 6:
                finishFragment = (FinishFragment) new FinishFragment().newInstance();
                return finishFragment;
            default:
                return null;


        }
    }


    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return personalInfoFragment;
            case 1:
                return problemInfoFragment;
            case 2:
                return dateInfoFragment;
            case 3:
                return addressFragment;
            case 4:
                return signUpFragment;
            case 5:
                return verificationFragment;
            case 6:
                return finishFragment;
            default:
                return null;
        }

    }
}