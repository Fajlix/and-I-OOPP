package com.example.graymatter.viewModel;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.graymatter.model.dataAccess.DataAccess;
import com.example.graymatter.model.dataAccess.social.UserInfoException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

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
        } catch (UserInfoException | IOException e) {
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
