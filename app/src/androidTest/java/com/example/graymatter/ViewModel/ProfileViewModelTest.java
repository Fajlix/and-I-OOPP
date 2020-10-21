package com.example.graymatter.ViewModel;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProfileViewModelTest {
    ProfileViewModel profileViewModel;
    DataAccess gsa = new DataAccess(InstrumentationRegistry.getInstrumentation().getTargetContext());

    @Before
    public void init(){
        profileViewModel = new ProfileViewModel();
        try {
            gsa.logIn("Tuff-tuff22oHalvt", "hejNej88*");
        } catch (UserInfoException e) {
            e.printStackTrace();
        }
        profileViewModel.init(gsa);
    }
    @Test
    public void getFriendsTest() throws UserInfoException {
        String[] friends = profileViewModel.getFriends();
        assertEquals(friends.length,1);
        assertEquals(friends[0],"Mathilda97");
    }
}
