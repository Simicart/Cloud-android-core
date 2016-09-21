package com.simicart.core.instantcontact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by frank on 9/20/16.
 */

public class ContactUsFragment extends SimiFragment {

    ContactUsEntity baseContact;
    protected ArrayList<ContactUsEntity> listContactUs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int idView = Rconfig.getInstance().layout("plugins_contactus_fragment");
        rootView = inflater.inflate(idView, container, false);

        RecyclerView rcvContact = (RecyclerView) rootView.findViewById(Rconfig.getInstance().id("rv_list_contact"));
        listContactUs = new ArrayList<>();
        baseContact = new ContactUsEntity();
        ArrayList<String> emails = new ArrayList<>();
        emails.add("support@simicart.com");
        baseContact.setEmail(emails);

        ArrayList<String> phones = new ArrayList<>();
        phones.add("+840485854587");
        baseContact.setPhone(phones);
        baseContact.setMessage(phones);

        baseContact.setStyle("2");
        baseContact.setActiveColor("FFBA42");
        baseContact.setWebsite("https://www.simicart.com/");


        createListContact();

        ContactUsAdapter adapter = new ContactUsAdapter(listContactUs, "2");
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        rcvContact.setLayoutManager(manager);
        rcvContact.setAdapter(adapter);

        Log.e("Contact Us Fragment ","---------> onCreateView");

        return rootView;
    }

    protected void createListContact() {
        if (baseContact.getStyle().equals("2")) {
            createItemContact("plugins_contactus_email", "Email");
            createItemContact("plugins_contactus_message", "Message");
            createItemContact("plugins_contactus_call", "Call");
            createItemContact("plugins_contactus_web", "Website");
        } else {
            createItemContact("plugins_contactusemail_list", "Email");
            createItemContact("plugins_contactusmessage_list", "Message");
            createItemContact("plugins_contactusphone_list", "Call");
            createItemContact("plugins_contactusweb_list", "Website");
        }
    }

    protected void createItemContact(String icon, String name) {
        ContactUsEntity item = new ContactUsEntity(baseContact, icon, name);
        listContactUs.add(item);
    }

}
