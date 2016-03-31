package Realm;

import io.realm.RealmObject;

/**
 * Created by fabian on 13-02-16.
 */
public class RealmString extends RealmObject {
    private String value;

    public RealmString(){

    }

    public RealmString(String value){
        this.value  = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
