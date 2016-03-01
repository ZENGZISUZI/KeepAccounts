package fanghao.example.com.keepaccounts;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

import fanghao.example.com.keepaccounts.entity.Acount;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcountUpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcountUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@ContentView(R.layout.fragment_acount_update)
public class AcountUpdateFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final String TYPE_INCOME = "0";
    final String TYPE_EXPENSE = "1";
    private OnFragmentInteractionListener mListener;
    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("myAcounts")
                    //.setDbDir(new File("/data/data/fanghao.example.com.keepaccounts"))
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    // TODO: ...
                    // db.addColumn(...);
                    // db.dropTable(...);
                    // ...
                }
            });
    public AcountUpdateFragment() {
        // Required empty public constructor
    }
    @ViewInject(R.id.figure)
    private EditText figure;
    @ViewInject(R.id.remarks)
    private EditText remarks;
    @ViewInject(R.id.date)
    private TextView date;
    @ViewInject(R.id.category)
    private TextView category;

    @Event(R.id.date_button)
    private void onTestDbClick(View view) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                    }
                }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Event(R.id.category_button)
    private void onCategoryClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("类型选择")
                .setSingleChoiceItems(R.array.icategory, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category.setText(getResources().getStringArray(R.array.icategory)[which]);
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Event(R.id.category_button2)
    private void onCategory2Click(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("类型选择")
                .setSingleChoiceItems(R.array.ecategory, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category.setText(getResources().getStringArray(R.array.ecategory)[which]);
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Event(R.id.income)
    private void onIncomeClick(View view) {
        boolean i = true;
        try {
            DbManager db = x.getDb(daoConfig);
            Acount acount = new Acount();
            acount.setType(TYPE_INCOME);
            acount.setFigure(Float.parseFloat(figure.getText().toString()));
            acount.setDate(date.getText().toString());
            acount.setCategory(category.getText().toString());
            acount.setRemarks(remarks.getText().toString());
            acount.setId(getArguments().getInt("id"));
            db.saveOrUpdate(acount);

        } catch (Throwable throwable) {
            i = false;
            Toast.makeText(getActivity(), "记账收入修改失败", Toast.LENGTH_SHORT).show();
        }
        if (i)
            Toast.makeText(getActivity(), "记账收入修改成功", Toast.LENGTH_SHORT).show();
    }

    @Event(R.id.expense)
    private void onExpenseClick(View view) {
        boolean i = true;
        try {
            DbManager db = x.getDb(daoConfig);
            Acount acount = new Acount();
            acount.setType(TYPE_EXPENSE);
            acount.setFigure(Float.parseFloat(figure.getText().toString()));
            acount.setDate(date.getText().toString());
            acount.setCategory(category.getText().toString());
            acount.setRemarks(remarks.getText().toString());
            acount.setId(getArguments().getInt("id"));
            db.saveOrUpdate(acount);


        } catch (Throwable throwable) {
            i = false;
            Toast.makeText(getActivity(), "记账支出修改失败", Toast.LENGTH_SHORT).show();
        }
        if (i)
            Toast.makeText(getActivity(), "记账支出修改成功", Toast.LENGTH_SHORT).show();
    }

    public static AcountUpdateFragment getInstance(Bundle bundle) {
        AcountUpdateFragment secondFragment = new AcountUpdateFragment();
        secondFragment.setArguments(bundle);
        return secondFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcountUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcountUpdateFragment newInstance(String param1, String param2) {
        AcountUpdateFragment fragment = new AcountUpdateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_acount_update, null);
        figure = (EditText) root.findViewById(R.id.figure);
        remarks = (EditText) root.findViewById(R.id.remarks);
        category=(TextView) root.findViewById(R.id.category);
        date = (TextView) root.findViewById(R.id.date);
        try {
            DbManager db = x.getDb(daoConfig);
            Acount acount = db.selector(Acount.class).where("_id", "in", new int[]{getArguments().getInt("id")}).findFirst();
            figure.setText(acount.getFigure()+"");
            remarks.setText(acount.getRemarks());
            category.setText(acount.getCategory());
            date.setText(acount.getDate());
        } catch (Throwable throwable) {

        }



        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
