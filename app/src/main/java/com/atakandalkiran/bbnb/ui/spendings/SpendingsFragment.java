package com.atakandalkiran.bbnb.ui.spendings;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian3d;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.atakandalkiran.bbnb.R;
import com.atakandalkiran.bbnb.data.base.BaseFragment;
import com.atakandalkiran.bbnb.databinding.FragmentSpendingsBinding;

import java.util.ArrayList;
import java.util.List;

public class SpendingsFragment extends BaseFragment {

    FragmentSpendingsBinding binding;

    String[] renkler = {"#86C8CD", "#5499a4", "#6DB2C8", "#40698E", "#415771", "#B4A0AC", "#619ba1"};
    public SpendingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackground();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);

        AnyChartView anyChartView = binding.anyChartView;

        setupPieChart(anyChartView);

        return binding.getRoot();
    }

    private void setupPieChart(AnyChartView anyChartView) {
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Akaryakıt", 2300));
        data.add(new ValueDataEntry("Eğitim", 5000));
        data.add(new ValueDataEntry("Giyim", 1700));
        data.add(new ValueDataEntry("Market", 1100));
        data.add(new ValueDataEntry("Sağlık", 4110));
        data.add(new ValueDataEntry("Yemek", 850));
        data.add(new ValueDataEntry("Diğer", 900));

        pie.data(data);
        pie.palette(renkler);
        pie.sort("asc");
        pie.tooltip().titleFormat("Kategori: {%x}")
                .format("Harcama Miktarı: {%value} TL");

        pie.title("Aylık Harcama Grafiği");

        pie.legend()
                .itemsLayout(LegendLayout.VERTICAL);



        anyChartView.setChart(pie);
    }


    @Override
    protected void setupUI() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_spendings;
    }
}