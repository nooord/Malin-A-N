package com.example.malin.lab2bapplication.Data.Contracts;


import android.provider.BaseColumns;


public class ContractGallery {

    public ContractGallery(){
    }
    //Set the column names for the database.
    public static abstract class Gallery implements BaseColumns{
        public final static String TABLE_NAME = "gallery";
        public final static String COLUMN_NAME_URL = "url";
        public final static String COLUMN_NAME_NAME = "name";
        public final static String COLUMN_NAME_AGE= "age";
        public final static String COLUMN_NAME_DESCRIPTION = "description";


    }

}
