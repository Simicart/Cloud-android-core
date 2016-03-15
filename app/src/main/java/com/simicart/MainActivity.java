package com.simicart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.common.FontsOverride;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.controller.AutoGetQtyNotSignInController;
import com.simicart.core.customer.controller.AutoSignInController;
import com.simicart.core.menutop.fragment.FragmentMenuTop;
import com.simicart.core.notification.NotificationActivity;
import com.simicart.core.notification.controller.NotificationController;
import com.simicart.core.slidemenu.fragment.SlideMenuFragment;

public class MainActivity extends FragmentActivity {

    public final static int PAUSE = 2;
    private SlideMenuFragment mNavigationDrawerFragment;
    public static Activity context;
    private NotificationController notification;
    public static int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimiManager.getIntance().setCurrentActivity(this);
        SimiManager.getIntance().setCurrentContext(this);
        setContentView(R.layout.core_main_activity);

        if (DataLocal.isSignInComplete()) {
            autoSignin();
        } else {
            if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
                autoGetQtyNotSignin();
            }
        }
        context = this;
        SimiManager.getIntance().setManager(getSupportFragmentManager());
        if (DataLocal.isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        mNavigationDrawerFragment = (SlideMenuFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.setup(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        changeFont();
        notification = new NotificationController(this);
        notification.registerNotification();

        recieveNotification();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentMenuTop fragment = FragmentMenuTop
                .newInstance(mNavigationDrawerFragment);
        ft.replace(Rconfig.getInstance().id("menu_top"), fragment);
        ft.commit();

    }

    private void autoSignin() {
        AutoSignInController controller = new AutoSignInController();
        controller.onStart();
    }

    private void autoGetQtyNotSignin() {
        AutoGetQtyNotSignInController controller = new AutoGetQtyNotSignInController();
        controller.onStart();
    }

    private void recieveNotification() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Intent i = new Intent(context, NotificationActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtras(extras);
            context.startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        state = PAUSE;
        super.onPause();
    }

    @Override
    protected void onResume() {
        state = 0;
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                notification.openNotificationSetting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeFont() {
        FontsOverride.setDefaultFont(this, "DEFAULT", Config.getInstance()
                .getFontCustom());
        FontsOverride.setDefaultFont(this, "NORMAL", Config.getInstance()
                .getFontCustom());
        FontsOverride.setDefaultFont(this, "MONOSPACE", Config.getInstance()
                .getFontCustom());
        FontsOverride.setDefaultFont(this, "SERIF", Config.getInstance()
                .getFontCustom());
    }

    @Override
    public void onBackPressed() {
        int count = SimiManager.getIntance().getManager()
                .getBackStackEntryCount();
        if (count == 1) {
            showConfirmExitApp();
        } else {
            super.onBackPressed();
        }
    }

    private void showConfirmExitApp() {

        String title = Config.getInstance()
                .getText("CLOSE APPLICATION").toUpperCase();

        String message = Config.getInstance()
                .getText("Are you sure you want to exit?");

        String ok = Config.getInstance()
                .getText("OK").toUpperCase();

        String cancel = Config.getInstance()
                .getText("CANCEL").toUpperCase();

        AlertDialog.Builder dialog_builder = new AlertDialog.Builder(
                this);
        dialog_builder.setTitle(title);
        dialog_builder.setMessage(message);
        dialog_builder.setCancelable(false);
        dialog_builder.setPositiveButton(ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        context = null;
                        SimiManager.getIntance().getManager()
                                .popBackStack();
                        android.os.Process
                                .killProcess(android.os.Process
                                        .myPid());
                        finish();
                    }
                });
        dialog_builder.setNegativeButton(cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                    }
                });
        dialog_builder.show();
    }


    @Override
    protected void onDestroy() {
        notification.destroy();
        System.gc();
        Runtime.getRuntime().freeMemory();
        super.onDestroy();
    }


}
