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
    public String getName(){
        if (user!= null){
            return user.getDisplayName();
        }else {
            return null;
        }
    }
    public String getUID(){
        if (user!= null){
            return user.getUid();
        }else {
            return null;
        }
    }
}
