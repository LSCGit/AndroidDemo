package com.lsc.runtimepermission;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by lsc on 2020-04-17 18:23.
 * E-Mail:2965219926@qq.com
 */
public class RuntimePermissionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, null);

        // BEGIN_INCLUDE(m_only_permission)
        if (Build.VERSION.SDK_INT < 23) {
            /*
            联系人权限已在AndroidManifest中声明，仅适用于AndroidM及以上版本。它们在旧的平台上不可用，所以我们隐藏了访问联系人数据库的按钮。
            这显示了如何添加新的仅运行时权限，而不应用于旧的平台版本。这对于自动更新非常有用，因为附加的权限可能会提示用户升级。
             */
            root.findViewById(R.id.button_contacts).setVisibility(View.GONE);
        }
        // END_INCLUDE(m_only_permission)

        return root;
    }
}
