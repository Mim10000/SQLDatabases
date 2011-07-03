/***
 * Excerpted from "Hello, Android!",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband for more book information.
***/
package org.example.events;

import static android.provider.BaseColumns._ID;
import static org.example.events.Constants.TABLE_NAME;

//For the date
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.example.events.Constants.DATE;
import static org.example.events.Constants.USER_NAME;
import static org.example.events.Constants.HSCORE;

import android.app.ListActivity;
// ...

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.widget.SimpleCursorAdapter;
// ...



public class Events extends ListActivity {
   // ...
   private static String[] FROM = { _ID,USER_NAME, HSCORE, DATE, };
   private static String ORDER_BY = HSCORE + " DESC";
   
   private static int[] TO = {  R.id.rowid,R.id.username, R.id.hscore, R.id.date};
   
   private EventsData events;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      events = new EventsData(this);
      try {
         addEvent("Jeremy" , "9001");
         addEvent("Jerome" , "4000");

         
         Cursor cursor = getEvents();
         showEvents(cursor);
      } finally {
         events.close();
      }
   }

   private void addEvent(String name, String hscore) {
      // Insert a new record into the Events data source.
      // You would do something similar for delete and update.
      SQLiteDatabase db = events.getWritableDatabase();
      ContentValues values = new ContentValues();
      
      values.put(USER_NAME, name);
      values.put(HSCORE, hscore);  
      values.put(DATE, getDateTime());


      db.insertOrThrow(TABLE_NAME, null, values);
   }

   private Cursor getEvents() {
      // Perform a managed query. The Activity will handle closing
      // and re-querying the cursor when needed.
      SQLiteDatabase db = events.getReadableDatabase();
      Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
            null, ORDER_BY);
      startManagingCursor(cursor);
      return cursor;
   }

   
   private void showEvents(Cursor cursor) {
      // Set up data binding
      SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
            R.layout.item, cursor, FROM, TO);
      setListAdapter(adapter);
   }
   private String getDateTime() {
       DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
       Date date = new Date();
       return dateFormat.format(date);
   }
   
}
