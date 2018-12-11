package th.ac.kmitl.it59070164;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment{

    SQLiteDatabase myDB;
    User user;
    SharedPreferences sp;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        sp = getContext().getSharedPreferences("user", MODE_PRIVATE);

        if(!sp.getString("name","error").equals("error")){
            Log.d("LOGIN", "GOTO home");
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new homefragment()).commit();
        }
        else {
            initLogin();
            initRegisterBtn();
        }
    }

    void initLogin() {
        TextView _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("LOGIN", "Click login");
                EditText _userId = getView().findViewById(R.id.login_userid);
                EditText _password = getView().findViewById(R.id.login_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill out this form", Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN", "Please fill out this form");
                }
                else{

                    Cursor myCursor = myDB.rawQuery("SELECT * FROM user", null);
                    Log.d("LOGIN", "Query");
                    while (myCursor.moveToNext()){
                        Log.d("LOGIN", "while");
                        if(_userIdStr.equals(myCursor.getString(1))){
                            Log.d("LOGIN", "USERID CHECK pass");
                            if(_passwordStr.equals(myCursor.getString(4))) {
                                Log.d("LOGIN", "PASS CHECK pass");
                                String _useridSql = myCursor.getString(1);
                                String _nameSql = myCursor.getString(2);
                                String _passSql = myCursor.getString(4);
                                int _ageSql = myCursor.getInt(3);

                                user = new User(_useridSql, _nameSql, _ageSql, _passSql);


                                sp.edit().putString("userid", _userIdStr).apply();
                                sp.edit().putString("name", _nameSql).apply();
                                sp.edit().putInt("age", _ageSql).apply();
                                sp.edit().putString("pass", _passwordStr).apply();
                                sp.edit().commit();
                                Log.d("LOGIN","SharedPreferences = "+ sp.getString("name","error"));
                            }
                            Log.d("LOGIN", "User = "+ user.getName());
                            Log.d("LOGIN", "GOTO home");
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new homefragment()).commit();
                        } else {
                            Toast.makeText(getActivity(), "Invalid user or password", Toast.LENGTH_SHORT).show();
                            Log.d("LOGIN", "Invalid user or password");
                        }
                    }
                }
            }
        });

    }


    void initRegisterBtn(){
        TextView _regBtn = (TextView) getView().findViewById(R.id.login_register);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("LOGIN", "GOTO REGISTER");
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }
}
