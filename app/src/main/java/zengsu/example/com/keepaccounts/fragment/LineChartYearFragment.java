package zengsu.example.com.keepaccounts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import zengsu.example.com.keepaccounts.R;
import zengsu.example.com.keepaccounts.entity.Account;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;


/**
 *
 */
public class LineChartYearFragment extends BaseFragment {
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
    private LineChartView chart;
    private LineChartData data;
    private int numberOfLines = 1;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 12;


    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLines = true;
    private boolean hasPoints = true;
    private ValueShape shape = ValueShape.CIRCLE;
    private boolean isFilled = false;
    private boolean hasLabels = false;
    private boolean isCubic = false;
    private boolean hasLabelForSelected = false;
    private boolean pointsHaveDifferentColor;

    /*MyDatabasehelper myDatabasehelper;*/

    float max=1000;//  y轴最高
    public LineChartYearFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);

        chart = (LineChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());


        generateData();

        // Disable viewpirt recalculations, see toggleCubic() method for more info.
        chart.setViewportCalculationEnabled(false);

        resetViewport();

        return rootView;
    }





    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = max;
        v.left = 0;
        v.right = numberOfPoints - 1;
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    private void generateData() {
       // chart = (LineChartView) findViewById(R.id.chart);
        /*myDatabasehelper = new MyDatabasehelper(getActivity(), "myAccounts.db", null, 2);*/
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        String date=year+"年%";
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {
            DbManager db = x.getDb(daoConfig);
            List<PointValue> values = new ArrayList<PointValue>();
            //                for (int j = 0; j < numberOfPoints; ++j) {
            //                    values.add(new PointValue(j, randomNumbersTab[i][j]));
            //                }
           /* Cursor cursor = myDatabasehelper.getReadableDatabase().rawQuery(
                    "select * from acounts where date =?", new String[]{date}
            );*/ try {
                List<Account> accountList =db.selector(Account.class).where("date", "like", date).and("type","=",1).findAll();
                int j = 0;
                for (int t = 0; t < accountList.size(); t++) {
                    values.add(new PointValue(j, accountList.get(t).getFigure()));
                    if(accountList.get(t).getFigure()>max)
                        max= accountList.get(t).getFigure();
                    j++;
                }
                /*while (cursor.moveToNext()) {
                    values.add(new PointValue(j,Float.parseFloat(cursor.getString(1))));
                    if(Float.parseFloat(cursor.getString(1))>max)
                        max=Float.parseFloat(cursor.getString(1));
                    j++;
                }*/
            }catch (Throwable e){

            }



            Line line = new Line(values);
            line.setColor(ChartUtils.COLORS[4]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[4]);
            }
            lines.add(line);

            values = new ArrayList<PointValue>();
            try {
                List<Account> accountList =db.selector(Account.class).where("date", "like", date).and("type","=",0).findAll();
                int j = 0;
                for (int t = 0; t < accountList.size(); t++) {
                    values.add(new PointValue(j, accountList.get(t).getFigure()));
                    if(accountList.get(t).getFigure()>max)
                        max= accountList.get(t).getFigure();
                    j++;
                }
                /*while (cursor.moveToNext()) {
                    values.add(new PointValue(j,Float.parseFloat(cursor.getString(1))));
                    if(Float.parseFloat(cursor.getString(1))>max)
                        max=Float.parseFloat(cursor.getString(1));
                    j++;
                }*/
            }catch (Throwable e){

            }



             line = new Line(values);
            line.setColor(ChartUtils.COLORS[2]);
            line.setShape(shape);
            line.setCubic(isCubic);
            line.setFilled(isFilled);
            line.setHasLabels(hasLabels);
            line.setHasLabelsOnlyForSelected(hasLabelForSelected);
            line.setHasLines(hasLines);
            line.setHasPoints(hasPoints);
            if (pointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[2]);
            }
            lines.add(line);
        }

        data = new LineChartData(lines);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("本年     红（花费） 绿（收入）");
                axisY.setName("金额（￥）");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);

    }


    /**
     * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
     * method(don't confuse with View.animate()). If you operate on data that was set before you don't have to call
     * {@link LineChartView#setLineChartData(LineChartData)} again.
     */


    private class ValueTouchListener implements LineChartOnValueSelectListener {

        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }

    }

}
