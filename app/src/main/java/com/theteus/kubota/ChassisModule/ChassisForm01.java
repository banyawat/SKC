package com.theteus.kubota.ChassisModule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.theteus.kubota.R;

public class ChassisForm01 extends Fragment {
    Spinner carTypeSpinner, carStatusSpinner;

    public ChassisForm01() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_form01, container, false);

        findViewsById(view);
        initSpinner(view);

        return view;
    }

    private void findViewsById(View view){
        carTypeSpinner = (Spinner) view.findViewById(R.id.chassis_form1_cartype);
        carStatusSpinner = (Spinner) view.findViewById(R.id.chassis_form1_carstatus);
    }

    private void initSpinner(View view){
        ArrayAdapter<CharSequence> carTypeAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chassis_type, android.R.layout.simple_spinner_item);
        carTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTypeSpinner.setAdapter(carTypeAdapter);

        ArrayAdapter<CharSequence> carStatusAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chassis_status, android.R.layout.simple_spinner_item);
        carStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carStatusSpinner.setAdapter(carStatusAdapter);
    }

}
