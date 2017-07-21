package com.example.jf.cardapp.fragment;



import android.app.Fragment;

import com.example.jf.cardapp.R;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/23.
 */
public class MyFragmentFactory {
    /**
     * fragment的数量
     */
    public static final int FRAGMENT_COUNT = 5;
    private static Map<Integer, SoftReference<Fragment>> fragments = new HashMap<>();


    public static Fragment getFragment(int position) {
        SoftReference<Fragment> fragmentSoftReference1 = fragments.get(position);
        Fragment fragment = null;
        if (fragmentSoftReference1 != null) {
            fragment = fragmentSoftReference1.get();
        }
        if (fragment == null) {
            switch (position) {
                case R.id.rb_square:
                    fragment = new SquareFragment();
                    break;
                case R.id.rb_group:
                    fragment = new GroupFragment();
                    break;
                case R.id.rb_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.rb_information:
                    fragment = new InformationFragment();
                    break;
                case R.id.rb_message:
                    fragment = new MassageFragment();
                    break;

            }
            SoftReference<Fragment> fragmentSoftReference = new SoftReference<>(fragment);
            fragments.put(position, fragmentSoftReference);
        }
        return fragment;
    }
}
