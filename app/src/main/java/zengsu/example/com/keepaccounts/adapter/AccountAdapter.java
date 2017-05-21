package zengsu.example.com.keepaccounts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import zengsu.example.com.keepaccounts.R;
import zengsu.example.com.keepaccounts.entity.Account;

/**
 * Created by zeng on 2017/5/8.
 */
public class AccountAdapter extends ArrayAdapter<Account> {
    private int resourceId;

    public AccountAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.remarks = (TextView) view.findViewById(R.id.remarks);
            viewHolder.figure = (TextView) view.findViewById(R.id.figure);
            viewHolder.id=(TextView) view.findViewById(R.id.id);
            viewHolder.category= (TextView) view.findViewById(R.id.category);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.date.setText(account.getDate());

        viewHolder.remarks.setText(account.getRemarks());
        viewHolder.category.setText(account.getCategory());

        if (account.getType().equals("0")) {
            viewHolder.figure.setTextColor(Color.rgb(106, 168, 79));
            viewHolder.figure.setText("+" + account.getFigure());

        }else {
            viewHolder.figure.setTextColor(Color.rgb(152, 0, 0));
            viewHolder.figure.setText("-" + account.getFigure());
        }
        return view;
    }

    class ViewHolder {
        TextView date;
        TextView remarks;
        TextView figure;
        TextView id;
        TextView category;
    }
}
