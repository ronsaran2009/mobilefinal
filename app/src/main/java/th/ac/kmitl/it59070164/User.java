package th.ac.kmitl.it59070164;


import android.content.ContentValues;

public class User {
    String userid ;
    String name;
    int age;
    String pass;
    ContentValues _row = new ContentValues();
    ContentValues contentValues;
    public User(){}
    public User(String userid , String name, int age ,String pass){
        this.userid = userid;
        this.name = name;
        this.age = age;
        this.pass = pass;
    }
    public void setContent(String userid , String name, int age ,String pass) {
        this._row.put("userid", userid);
        this._row.put("name", name);
        this._row.put("age", age);
        this._row.put("pass", pass);
    }

    public ContentValues getContent() {
        return _row;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
