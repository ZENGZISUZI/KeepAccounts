package fanghao.example.com.keepaccounts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fanghao.example.com.keepaccounts.R;
import fanghao.example.com.keepaccounts.entity.Acount;

/**
 * Created by fanghao on 2015/12/8.
 */
public class AcountAdapter extends ArrayAdapter<Acount> {
    private int resourceId;

    public AcountAdapter(Context context, int resource, List<Acount> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Acount acount = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.remarks = (TextView) view.findViewById(R.id.remarks);
            viewHolder.figure = (TextView) view.findViewById(R.id.figure);
            viewHolder.id=(TextView) view.findViewById(R.id.id);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.date.setText(acount.getDate());

        viewHolder.remarks.setText(acount.getRemarks());

        if (acount.getType().equals("0")) {
            viewHolder.figure.setTextColor(Color.rgb(106, 168, 79));
            viewHolder.figure.setText("+" + acount.getFigure());

        }else {
            viewHolder.figure.setTextColor(Color.rgb(152, 0, 0));
            viewHolder.figure.setText("-" + acount.getFigure());
        }
        return view;
    }

    class ViewHolder {
        TextView date;
        TextView remarks;
        TextView figure;
        TextView id;
    }
}
