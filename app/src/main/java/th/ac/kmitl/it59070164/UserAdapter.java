package th.ac.kmitl.it59070164;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter  extends ArrayAdapter<User> {
    List<User> user = new ArrayList();
    Context context;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.user = objects;
        this.context = context;
    }
}
