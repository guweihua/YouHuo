package com.example.administrator.youhuo.fragment;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PLBoyFragment extends BasePinLeiChildFragment {
    public PLBoyFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "boy");
        setArguments(bundle);
    }
}
