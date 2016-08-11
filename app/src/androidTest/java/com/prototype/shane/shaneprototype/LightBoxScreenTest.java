package com.prototype.shane.shaneprototype;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.v4.content.res.ResourcesCompat;
import android.test.suitebuilder.annotation.LargeTest;

import com.prototype.shane.shaneprototype.view.lightBox.LightBoxFragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by shane on 8/7/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LightBoxScreenTest {
    private static final String PHOTO_URL = "https://farm9.staticflickr.com/8689/28222841893_df26e55e9c_o.jpg";
    private static final long PHOTO_ID = 28761457741L;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        LightBoxFragment lightBoxFragment = LightBoxFragment.create(ResourcesCompat.getDrawable(mActivityRule.getActivity().getResources(), R.drawable.download_icon, null), PHOTO_URL, PHOTO_ID);
        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content, lightBoxFragment, LightBoxFragment.TAG_LIGHT_BOX_FRAGMENT).commit();
    }

    @Test
    public void testDisplayingLightBoxFragment(){
        mDevice.waitForIdle();
        LightBoxFragment myFragment = (LightBoxFragment) mActivityRule.getActivity().getSupportFragmentManager().findFragmentByTag(LightBoxFragment.TAG_LIGHT_BOX_FRAGMENT);
        if (myFragment == null || !myFragment.isVisible()) {
            Assert.fail("not displaying lightboxFragment");
        }
    }

    @Test
    public void testOrientation(){
        try {
            mDevice.setOrientationRight();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mDevice.setOrientationLeft();
            mDevice.setOrientationNatural();
            mDevice.unfreezeRotation();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
