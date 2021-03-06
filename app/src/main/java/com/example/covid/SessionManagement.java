package com.example.covid;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY="session_user";
    String ROLE;
    public SessionManagement(Context context)
    {
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }
    public void saveSession(User user)
    {
       String id= user.getId();
       editor.putString(SESSION_KEY,id).commit();



    }
    public void saveRole(String role){
        editor.putString("role",role).commit();
    }

    public String getROLE() {
            return  sharedPreferences.getString("role","0");
    }

    public String getSession()
    {
      return  sharedPreferences.getString(SESSION_KEY,"default") ;
    }
    public void removeSession()
    {

        editor.putString(SESSION_KEY,"default" ).commit();
    }
}
