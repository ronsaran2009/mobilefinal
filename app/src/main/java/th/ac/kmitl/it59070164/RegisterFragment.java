package th.ac.kmitl.it59070164;

import android.content.ContentValues;
import android.content.Context;
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



public class RegisterFragment extends Fragment {


    SQLiteDatabase myDB;
    Bundle _bundle;
    User user = new User();
    ContentValues _row = new ContentValues();
    int _ageint;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

        initRegisterBtn();

    }

    void initRegisterBtn(){
        TextView _regBtn = getView().findViewById(R.id.reg_register);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText _userid = getView().findViewById(R.id.reg_user_id);
                EditText _name = getView().findViewById(R.id.reg_name);
                EditText _age = getView().findViewById(R.id.reg_age);
                EditText _pass = getView().findViewById(R.id.reg_password);

                String _useridstr = _userid.getText().toString();
                String _namestr = _name.getText().toString();
                String _agestr = _age.getText().toString();
                String _passstr = _pass.getText().toString();
                if (_useridstr.isEmpty()||_namestr.isEmpty()||_agestr.isEmpty()||_passstr.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "กรุณากรอกข้อมูลให้ครบ");
                }
                else if (isNum(_agestr)) {
                    _ageint = Integer.parseInt(_agestr);


                if (_useridstr.length() <6 || _useridstr.length()>12){
                    Toast.makeText(getActivity(), "User Id ต้องมีความยาวอยู่ในช่วง 6 - 12 ตัวอักษร", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "User Id ต้องมีความยาวอยู่ในช่วง 6 - 12 ตัวอักษร");
                }
                else if (checkname(_namestr)){
                    Toast.makeText(getActivity(), "Name รูปแบบไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "Name รูปแบบไม่ถูกต้อง");

                }
                else if (_ageint <10 || _ageint >80){
                    Toast.makeText(getActivity(), "Age ต้องเป็นตัวเลขเท่านั้นและอยู่ในช่วง 10 - 80", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "Age ต้องเป็นตัวเลขเท่านั้นและอยู่ในช่วง 10 - 80");
                }
                else if (_passstr.length() < 6){
                    Toast.makeText(getActivity(), "Password มีความยาวมากกว่า 6 ", Toast.LENGTH_SHORT).show();
                    Log.d("USER", "Password มีความยาวมากกว่า 6 ");

                }
                else {
                    myDB.execSQL(
                            "CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, userid VARCHAR(12), name VARCHAR(255), age INTEGER(2), pass VARCHAR(255))"
                    );
                    Log.d("REGIST", "CREATE TABLE ALREADY");
                    _bundle = getArguments();
                    user.setContent(_useridstr,_namestr,_ageint,_passstr);

                    _row = user.getContent();

                    myDB.insert("user", null, _row);
                    Log.d("LOGIN", "GOTO login");
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new LoginFragment()).commit();

                }

            }
                else{
                Toast.makeText(getActivity(), "Age รูปแบบไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                Log.d("USER", "Age รูปแบบไม่ถูกต้อง");
            }


            }
        });
    }
    boolean checkname(String name) {
        int check = 0;
        for (int i = 0; i < name.length(); i += 1) {
            Log.d("REGIST", ""+name.charAt(i));
            if (name.charAt(i) == ' ') {
                check += 1;

            }

        }
        if (check == 1 ){
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }
}
