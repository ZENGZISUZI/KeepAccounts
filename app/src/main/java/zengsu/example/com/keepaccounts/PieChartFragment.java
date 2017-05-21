//package zengsu.example.com.keepaccounts;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
//import lecho.lib.hellocharts.model.PieChartData;
//import lecho.lib.hellocharts.model.SliceValue;
//import lecho.lib.hellocharts.util.ChartUtils;
//import lecho.lib.hellocharts.view.PieChartView;
//import zengsu.example.com.keepaccounts.fragment.BaseFragment;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link PieChartFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link PieChartFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class PieChartFragment extends BaseFragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private boolean hasLabels = true;
//    private boolean hasLabelsOutside = false;
//    private boolean hasCenterCircle = false;
//    private boolean hasCenterText1 = true;
//    private boolean hasCenterText2 = false;
//    private boolean isExploded = false;
//    private boolean hasLabelForSelected = false;
//    // TODO: Rename and change types of parameters
//    private PieChartView chart;
//    private PieChartData data;
//
//    private OnFragmentInteractionListener mListener;
//
//    public PieChartFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment PieChartFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static PieChartFragment newInstance(String param1, String param2) {
//        PieChartFragment fragment = new PieChartFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        setHasOptionsMenu(true);
//        View rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false);
//
//        chart = (PieChartView) rootView.findViewById(R.id.chart);
//        chart.setOnValueTouchListener(new ValueTouchListener());
//
//        generateData();
//
//        return rootView;
//
//    }
//
//    private void generateData() {
//        int numValues = 6;
//
//        List<SliceValue> values = new ArrayList<SliceValue>();
//        for (int i = 0; i < numValues; ++i) {
//            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
//            values.add(sliceValue);
//        }
//
//        data = new PieChartData(values);
//        data.setHasLabels(hasLabels);
//        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
//        data.setHasLabelsOutside(hasLabelsOutside);
//        data.setHasCenterCircle(hasCenterCircle);
//
//        if (isExploded) {
//            data.setSlicesSpacing(24);
//        }
//
//        if (hasCenterText1) {
//            data.setCenterText1("Hello!");
//
//            // Get roboto-italic font.
//         //   Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//        //    data.setCenterText1Typeface(tf);
//
//            // Get font size from dimens.xml and convert it to sp(library uses sp values).
//            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
//        }
//
//        if (hasCenterText2) {
//            data.setCenterText2("Charts (Roboto Italic)");
//
//        //    Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
//
//         //   data.setCenterText2Typeface(tf);
//            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
//                    (int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
//        }
//
//        if (hasLabels) {
//            hasLabelForSelected = false;
//            chart.setValueSelectionEnabled(hasLabelForSelected);
//
//            if (hasLabelsOutside) {
//                chart.setCircleFillRatio(0.7f);
//            } else {
//                chart.setCircleFillRatio(1.0f);
//            }
//        }
//        chart.setPieChartData(data);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//    private class ValueTouchListener implements PieChartOnValueSelectListener {
//
//        @Override
//        public void onValueSelected(int arcIndex, SliceValue value) {
//            Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onValueDeselected() {
//            // TODO Auto-generated method stub
//
//        }
//
//    }
//}
