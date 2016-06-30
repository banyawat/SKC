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
public class ChassisDetailVip extends Fragment {
    private ChassisInstance mChassis;

    public ChassisDetailVip() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ChassisDetailMain.ARG_PARAM1)) {
            mChassis = DummyChassisInstance.CHASSIS_MAP.get(getArguments().getString(ChassisDetailMain.ARG_PARAM1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chassis_detail_vip, container, false);

        if (mChassis != null) {
            ((TextView) view.findViewById(R.id.chassis_detail_vip_card)).setText(mChassis.vipCard);
            ((TextView) view.findViewById(R.id.chassis_detail_start_date)).setText(mChassis.vipStartDate);
            ((TextView) view.findViewById(R.id.chassis_detail_end_date)).setText(mChassis.vipEndDate);
        }

        return view;
    }
}
