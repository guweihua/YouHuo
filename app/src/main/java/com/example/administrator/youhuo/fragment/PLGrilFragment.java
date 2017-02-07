package com.example.administrator.youhuo.fragment;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/1/11.
 */

public class PLGrilFragment extends BasePinLeiChildFragment {
    public PLGrilFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "girl");
        setArguments(bundle);
    }
}
