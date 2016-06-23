package com.ibm.android.kit.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bassam on 20-02-2016.
 */
public class ContactUtility {

    private static final String TAG = "CONTACTS_UTILITY";

    public static class Contact {

        public String name;
        public List<String> phones = new ArrayList<>();
        public Bitmap photo;
    }

    public static Contact getContactFromUri(Context context, Uri contactUri) {

        Contact contact = null;
        Cursor c = null;
        Cursor phones = null;
        try {
            c = context.getContentResolver().query(contactUri, null, null, null, null);
            if (c.moveToFirst()) {

                contact = new Contact();

                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                // add contact name
                contact.name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // add contact phones
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhone.equalsIgnoreCase("1")) {
                    phones = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    while (phones.moveToNext()) {
                        contact.phones.add(phones.getString(phones.getColumnIndex("data1")));
                    }
                }

                // add contact photo
//                contact.photo = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) c.close();
            if (phones != null) phones.close();
        }

        return contact;
    }

    public static Contact getContactFromPhone(Context context, String phone) {

        Contact contact = null;

        if (phone != null && !phone.isEmpty()) {
            Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                    Uri.encode(phone));

            String contactId = getContactId(context, contactUri);
            if (contactId != null) {
                contact = new Contact();
                contact.name = getContactName(context, contactId);
                contact.photo = getContactPhotoBitmap(context, contactId);
                contact.phones = getContactPhones(context, contactId);
            }
        }

        return contact;
    }

    private static String getContactId(Context context, Uri uri) {

        Cursor cursorID = null;
        String contactID = null;

        try {

            // getting contacts ID
            cursorID = context.getContentResolver().query(uri,
                    new String[]{ContactsContract.Contacts._ID},
                    null, null, null);

            if (cursorID.moveToFirst()) {

                contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursorID != null) cursorID.close();
        }

        return contactID;
    }

    public static Bitmap getContactPhotoBitmap(Context context, String contactId) {

        Bitmap photo = null;
        try {

            if (contactId != null) {

                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

                if (inputStream != null) {
                    photo = BitmapFactory.decodeStream(inputStream);
                    assert inputStream != null;
                    inputStream.close();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return photo;
    }

    public static String getContactName(Context context, String contactId) {

        String name = null;
        Cursor cursor = null;

        try {
            // querying contact data store
//            cursor = context.getContentResolver().query(contactUri, null, null, null, null);
            cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
                    ContactsContract.Contacts._ID + "=?", new String[]{contactId}, null);

            if (cursor.moveToFirst()) {

                // DISPLAY_NAME = The display name for the contact.
                // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
        }

        Log.d(TAG, "Contact Name: " + name);

        return name;
    }

    public static List<String> getContactPhones(Context context, Uri uri) {

        String contactId = getContactId(context, uri);
        if (contactId != null) {
            return getContactPhones(context, contactId);
        }

        return null;
    }

    public static List<String> getContactPhones(Context context, String contactID) {

        List<String> contactNumbers = new ArrayList<>();
        Cursor cursorPhone = null;

        try {
            if (contactID != null) {

                Log.d(TAG, "Contact ID: " + contactID);

                // Using the contact ID now we will get contact phone number
                cursorPhone = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", /*AND " +
                                ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,*/

                        new String[]{contactID},
                        null);

                while (cursorPhone.moveToNext()) {
                    contactNumbers.add(cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursorPhone != null) cursorPhone.close();
        }


        Log.d(TAG, "Contact Phone Number: " + contactNumbers.toString());

        return contactNumbers;
    }

}
