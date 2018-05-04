package com.example.hp.parentteacherapp.ModelClasses;

public class ParentLog {
    String email,password;

        ParentLog(String email, String password)
        {
            this.email=email;
            this.password=password;
        }
        public String getEmail()
        {
            return email;
        }
        public  String getPassword()
        {
            return password;
        }

}

//The above class will be only used to hold the json data that we will fetch.
// That is why the class is having only a constructor to initialize the fields and getters to get the values.
