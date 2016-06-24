package com.theteus.kubota.ChassisModule;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.theteus.kubota.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChassisForm02 extends Fragment {
    Spinner saleCondition;

    public ChassisForm02() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_form02, container, false);

        findViewsById(view);
        initSpinner(view);

        return view;
    }

    private void findViewsById(View view){
        saleCondition = (Spinner) view.findViewById(R.id.chassis_form2_salecondition);
    }

    private void initSpinner(View view) {
        ArrayAdapter<CharSequence> saleConditionAdapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.chassis_sale_condition, android.R.layout.simple_spinner_item);
        saleConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        saleCondition.setAdapter(saleConditionAdapter);
    }

}
