package fanghao.example.com.keepaccounts;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * {@link IncomeAddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IncomeAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 添加账单页面功能处理
 */
@ContentView(R.layout.fragment_acount_income)
public class IncomeAddFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    final String[] items = new String[]{"1"};
    final String TYPE_INCOME = "0";
    final String TYPE_EXPENSE = "1";

    public IncomeAddFragment() {
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



    @Event(R.id.category_button)
    private void onCategory2Click(View view) {
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
//收入类型的数据添加
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
            db.save(acount);
        } catch (Throwable throwable) {
            i = false;
            Toast.makeText(getActivity(), "记账收入失败", Toast.LENGTH_SHORT).show();
        }
        if (i)
            Toast.makeText(getActivity(), "记账收入成功", Toast.LENGTH_SHORT).show();
    }
//
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeAddFragment newInstance(String param1, String param2) {
        IncomeAddFragment fragment = new IncomeAddFragment();
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
               View root = inflater.inflate(R.layout.fragment_acount_income, null);
              date = (TextView) root.findViewById(R.id.date);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int monthOfYear = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }


}
