package com.tfd.ffcsez.fragments.landscapefragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tfd.ffcsez.ConstantsActivity;
import com.tfd.ffcsez.R;
import com.tfd.ffcsez.database.ExecutorClass;
import com.tfd.ffcsez.database.FacultyDatabase;
import com.tfd.ffcsez.database.TimeTableData;

import java.util.List;


public class AfternoonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_afternoon, container, false);

        FacultyDatabase database = FacultyDatabase.getInstance(getActivity().getApplicationContext());
        LiveData<List<TimeTableData>> data = database.timeTableDao().loadTT(1, 5, 9);
        data.observe(getActivity(), new Observer<List<TimeTableData>>() {
            @Override
            public void onChanged(List<TimeTableData> timeTableData) {
                TextView slotHolder;
                String slots;
                for (TimeTableData tableData: timeTableData){
                    int r, c, num;
                    if(tableData.getColumn() == 5){
                        num = ((tableData.getRow() + 1)*6);
                    }else{
                        num = ((tableData.getRow())*6) + (tableData.getColumn() + 1);
                    }
                    slotHolder = view.findViewById(getResources().
                            getIdentifier("text" + num, "id",
                                    getActivity().getPackageName()));
                    slots = slotHolder.getText().toString();
                    if (slots.isEmpty()){
                        slots += ConstantsActivity.getNumList().get(num);
                    }
                    slots += ("\n" + tableData.getCourseCode() + "-" + tableData.getCourseType());

                    slotHolder.setText(slots);

                    if (tableData.isClash()){
                        slotHolder.setBackgroundColor(Color.parseColor("#ff0000"));
                        slotHolder.setTextColor(Color.parseColor("#fff5eb"));
                    }else{
                        slotHolder.setBackgroundColor(Color.parseColor("#ccff33"));
                        slotHolder.setTextColor(Color.parseColor("#232323"));
                    }
                }
            }
        });
        return view;
    }
}