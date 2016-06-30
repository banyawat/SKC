package com.theteus.kubota.ChassisModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theteus.kubota.R;
import com.theteus.kubota.Reference;

/**
 * Created by whorangester on 6/29/16.
 */
public class ChassisDetailGeneral extends Fragment {
    private ChassisInstance mChassis;

    public ChassisDetailGeneral() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ChassisDetailMain.ARG_PARAM1)) {
            mChassis = DummyChassisInstance.CHASSIS_MAP.get(getArguments().getString(ChassisDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_detail_general, container, false);

        if (mChassis != null) {
            ((TextView) view.findViewById(R.id.chassis_detail_chassis_number)).setText(mChassis.chassisNumber);
            ((TextView) view.findViewById(R.id.chassis_detail_engine_number)).setText(mChassis.engineNumber);
            ((TextView) view.findViewById(R.id.chassis_detail_product_type)).setText(Reference.MASTER_PRODUCTTYPE.get(mChassis.productType));
            ((TextView) view.findViewById(R.id.chassis_detail_model)).setText(mChassis.productModel);
            ((TextView) view.findViewById(R.id.chassis_detail_product_status)).setText(Reference.MASTER_PRODUCTSTATUS.get(mChassis.productStatus));
            ((TextView) view.findViewById(R.id.chassis_detail_status_change_date)).setText(mChassis.statusChangeDate);
            ((TextView) view.findViewById(R.id.chassis_detail_remark)).setText(mChassis.remarkADMS);
        }

        return view;
    }
}
