package net.iqbalfauzan.agribid;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginStatus {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String getEmail(){
        if (user!= null){
            return user.getEmail();
        }else {
            return null;
        }
    }
}
