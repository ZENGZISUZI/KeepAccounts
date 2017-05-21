package zengsu.example.com.keepaccounts.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import zengsu.example.com.keepaccounts.R;
import zengsu.example.com.keepaccounts.entity.Account;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcountAddCluFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcountAddCluFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 添加账单页面功能处理
 */
@ContentView(R.layout.fragment_acount_add_clu)
public class AcountAddCluFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("myAccount")
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
    //MyDatabasehelper myDatabasehelper;
    final String[] items = new String[]{"1"};
    final String TYPE_INCOME = "0";
    final String TYPE_EXPENSE = "1";

    public AcountAddCluFragment() {
        // Required empty public constructor
    }

    @ViewInject(R.id.figure)
    private EditText figure;
    @ViewInject(R.id.remarks)
    private EditText remarks;
    @ViewInject(R.id.date)
    private TextView date;
    /*@ViewInject(R.id.date_button)
    private Button date_button;*/
    @ViewInject(R.id.category)
    private TextView category;

    /* @ViewInject(R.id.category_button)
     private Button category_button;*/
   /* @ViewInject(R.id.income)
    private Button income;

    private Button expense;*/
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
//收入类型的数据添加
    @Event(R.id.income)
    private void onIncomeClick(View view) {
        boolean i = true;
        try {
            DbManager db = x.getDb(daoConfig);
            Account account = new Account();
            account.setType(TYPE_INCOME);
            account.setFigure(Float.parseFloat(figure.getText().toString()));
            account.setDate(date.getText().toString());
            account.setCategory(category.getText().toString());
            account.setRemarks(remarks.getText().toString());
            db.save(account);
        } catch (Throwable throwable) {
            i = false;
            Toast.makeText(getActivity(), "记账收入失败", Toast.LENGTH_SHORT).show();
        }
        if (i)
            Toast.makeText(getActivity(), "记账收入成功", Toast.LENGTH_SHORT).show();
    }
    //支出类型的数据添加
    @Event(R.id.expense)
    private void onExpenseClick(View view) {
        boolean i = true;
        try {
            DbManager db = x.getDb(daoConfig);
            Account account = new Account();
//            account.getUserid(this.id);
            account.setType(TYPE_EXPENSE);
            account.setFigure(Float.parseFloat(figure.getText().toString()));
            account.setDate(date.getText().toString());
            account.setCategory(category.getText().toString());
            account.setRemarks(remarks.getText().toString());
            db.save(account);

        } catch (Throwable throwable) {
            i = false;
            Toast.makeText(getActivity(), "记账支出失败", Toast.LENGTH_SHORT).show();
        }
        if (i)
            Toast.makeText(getActivity(), "记账支出成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcountAddCluFragment newInstance(String param1, String param2) {
        AcountAddCluFragment fragment = new AcountAddCluFragment();
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
        View root = inflater.inflate(R.layout.fragment_acount_add_clu, null);
       /*  figure = (EditText) root.findViewById(R.id.figure);
        remarks = (EditText) root.findViewById(R.id.remarks);

       date_button = (Button) root.findViewById(R.id.date_button);*/

       /* income = (Button) root.findViewById(R.id.income);
        expense = (Button) root.findViewById(R.id.expense);
        category=(TextView) root.findViewById(R.id.category);*/
        /*category_button = (Button) root.findViewById(R.id.category_button);*/
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


       /* myDatabasehelper = new MyDatabasehelper(getActivity(), "myAccounts.db", null, 2);
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                            }
                        }
                ,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });*/
        /*category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("类型选择")
                        .setSingleChoiceItems(R.array.category, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                category.setText(getResources().getStringArray(R.array.category)[which]);
                            }
                        });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });*/
       /* income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(myDatabasehelper.getWritableDatabase(), figure.getText().toString(), remarks.getText().toString(),
                        category.getText().toString(), date.getText().toString(), TYPE_INCOME);
                Toast.makeText(getActivity(), "记账收入成功", Toast.LENGTH_SHORT).show();
            }
        });*/

       /* expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(myDatabasehelper.getWritableDatabase(), figure.getText().toString(), remarks.getText().toString(),
                        category.getText().toString(), date.getText().toString(), TYPE_EXPENSE);
                Toast.makeText(getActivity(), "记账支出成功", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    /*public void insert(SQLiteDatabase db,String figure, String remarks, String category, String date,String type) {
        final String INSERT_TABLE_SQL="insert into acounts(figure,remarks,category,date,type) values (?,?,?,?,?)";
        db.execSQL(INSERT_TABLE_SQL, new String[]{figure, remarks, category, date,type});
    }*/
}
