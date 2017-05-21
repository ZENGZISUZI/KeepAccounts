//package fanghao.example.com.keepaccounts.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import java.util.List;
//
//import fanghao.example.com.keepaccounts.R;
//import fanghao.example.com.keepaccounts.entity.User;
//
///**
// * Created by zeng on 2017/5/8.
// */
//public class AboutAdapter extends ArrayAdapter<User> {
//    private int resourceId;
//
//    public AboutAdapter(Context context, int resource, List<User> objects) {
//        super(context, resource, objects);
//        resourceId = resource;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        User user = getItem(position);
//        View view;
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//            viewHolder = new ViewHolder();
//            viewHolder.name= (TextView) view.findViewById(R.id.tv_about_name);
//            viewHolder.id=(TextView) view.findViewById(R.id.id);
//            view.setTag(viewHolder);
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        viewHolder.name.setText(user.getName());
//
//        return view;
//    }
//
//    class ViewHolder {
//        TextView name;
//        TextView id;
//
//
//    }
//}
