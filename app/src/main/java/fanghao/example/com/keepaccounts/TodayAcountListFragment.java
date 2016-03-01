package fanghao.example.com.keepaccounts;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
import org.xutils.DbManager;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fanghao.example.com.keepaccounts.adapter.AcountAdapter;
import fanghao.example.com.keepaccounts.entity.Acount;
import lecho.lib.hellocharts.model.PointValue;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TodayAcountListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayAcountListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayAcountListFragment extends BaseFragment {
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
    @ViewInject(R.id.list_view)
    ListView listView;
    AcountAdapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /*MyDatabasehelper myDatabasehelper;*/
    public TodayAcountListFragment() {
        // Required empty public constructor
    }
    @ViewInject(R.id.begin_date)
    TextView begin_date;
    @ViewInject(R.id.end_date)
    TextView end_date;
    @Event(R.id.begin_date_button)
    private void onBeginClick(View view) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        begin_date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        generateData(begin_date.getText().toString(), end_date.getText().toString());
                    }
                }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
    @Event(R.id.end_date_button)
    private void onEndClick(View view) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        end_date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                        generateData(begin_date.getText().toString(), end_date.getText().toString());
                    }
                }
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodayAcountListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayAcountListFragment newInstance(String param1, String param2) {
        TodayAcountListFragment fragment = new TodayAcountListFragment();
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
        View root = inflater.inflate(R.layout.fragment_today_acount_list, null);
       /* myDatabasehelper = new MyDatabasehelper(getActivity(), "myAccounts.db", null, 2);
        Cursor cursor = myDatabasehelper.getReadableDatabase().rawQuery(
                "select * from acounts order by date desc", null
        );


        List<Acount> acountList=new ArrayList(){};

        while (cursor.moveToNext()) {
            Acount acount = new Acount();
            acount.setFigure(Float.parseFloat(cursor.getString(1)));
            acount.setRemarks(cursor.getString(2));
            acount.setCategory(cursor.getString(3));
            acount.setDate(cursor.getString(4));
            acount.setType(cursor.getString(5));
            acountList.add(acount);
        }*/
        try {
            DbManager db = x.getDb(daoConfig);
            List<Acount> acountList = db.selector(Acount.class).orderBy("_id",true).findAll();
            adapter = new AcountAdapter(getActivity(), R.layout.acount_item, acountList);
            listView = (ListView) root.findViewById(R.id.list_view);
            listView.setAdapter(adapter);
            listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                public void onCreateContextMenu(ContextMenu menu, View v,
                                                ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("选择操作");
                    menu.add(0, 0, 0, "更新该条");
                    menu.add(0, 1, 0, "删除该条");
                }
            });
        } catch (Throwable throwable) {

        }
        return root;
    }



    public void generateData(String beginDate, String endDate) {
        List<Acount> acountList=null;
        try {
            DbManager db = x.getDb(daoConfig);
            if (!beginDate.equals("")) {
                if (!endDate.equals("")) {
                    acountList= db.selector(Acount.class).where("date","between",new String[]{begin_date.getText().toString(),end_date.getText().toString()}).orderBy("date",true).findAll();
                } else {
                    acountList= db.selector(Acount.class).where("date",">=",begin_date.getText().toString()).orderBy("date",true).findAll();

                }
            } else {
                if (!endDate.equals("")) {
                    acountList= db.selector(Acount.class).where("date","<=",end_date.getText().toString()).orderBy("date",true).findAll();
                } else {
                    acountList= db.selector(Acount.class).orderBy("date",true).findAll();
                }
            }
        } catch (Throwable throwable) {

        }

        adapter = new AcountAdapter(getActivity(), R.layout.acount_item, acountList);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "更新该条");
                menu.add(0, 1, 0, "删除该条");
            }
        });
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        String id = String.valueOf(info.id);
        switch (item.getItemId()) {
            case 0:
                updateDialog(((Acount) listView.getItemAtPosition((int) info.id)).getId());  //更新事件的方法
                /*Toast.makeText(getActivity(),((Acount)listView.getItemAtPosition((int)info.id)).getId()+"x"+
                        info.id+"info.id", Toast.LENGTH_SHORT).show();*/
                return true;
            case 1:
                DbManager db = x.getDb(daoConfig);
                Boolean x = true;
                try {
                    db.deleteById(Acount.class, ((Acount) listView.getItemAtPosition((int) info.id)).getId());
                } catch (Throwable throwable) {
                    x = false;
                    Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                }
                if (x) {
                    Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                    adapter.remove((Acount) listView.getItemAtPosition((int) info.id));
                }

                //System.out.println("删除"+info.id);
                //deleteData(db,id);  //删除事件的方法
               // showlist();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateDialog(int id) {

        getActivity().setTitle("修改");
        Bundle nBundle = new Bundle();
        nBundle.putInt("id", id);
        AcountUpdateFragment fragment = AcountUpdateFragment.getInstance(nBundle);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.container_home,fragment)
                .addToBackStack(null)
                .commit();

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
