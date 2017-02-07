package com.example.administrator.youhuo.fragment;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PLLifeFragment extends BasePinLeiChildFragment {
    public PLLifeFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "life");
        setArguments(bundle);
    }
}
