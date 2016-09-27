package com.chenglong.muscle.fragment;

import com.chenglong.muscle.R;
import com.chenglong.muscle.adapter.ToolBoxAdapter;
import com.chenglong.muscle.activity.toolbox.ToolGameSettingActivity;
import com.chenglong.muscle.activity.toolbox.ToolAlbumActivity;
import com.chenglong.muscle.activity.toolbox.ToolHealthActivity;
import com.chenglong.muscle.activity.toolbox.ToolDietActivity;
import com.chenglong.muscle.activity.toolbox.ToolInfoActivity;
import com.chenglong.muscle.activity.toolbox.ToolLessonActivity;
import com.chenglong.muscle.activity.toolbox.ToolGymActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class ToolBoxFragment extends Fragment implements android.widget.AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.fragment_toolbox, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        GridView gv = (GridView) getActivity().findViewById(R.id.tool_grid);
        gv.setAdapter(new ToolBoxAdapter(getActivity()));
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        switch (position) {
            case 0: /* 地图 */ {
                Intent intent = new Intent(getActivity(), ToolGymActivity.class);
                startActivity(intent);
                break;
            }
            case 1: /* 课程  */ {
                Intent intent = new Intent(getActivity(), ToolLessonActivity.class);
                startActivity(intent);
                break;
            }
            case 2: /* 餐饮  */ {
                Intent intent = new Intent(getActivity(), ToolDietActivity.class);
                startActivity(intent);
                break;
            }
            case 3: /* 咨询  */ {
                Intent intent = new Intent(getActivity(), ToolInfoActivity.class);
                startActivity(intent);
                break;
            }
            case 4: /* 游戏  */ {
                Intent intent = new Intent(getActivity(), ToolGameSettingActivity.class);
                startActivity(intent);
                break;
            }
            case 5: /* 计算  */ {
                Intent intent = new Intent(getActivity(), ToolHealthActivity.class);
                startActivity(intent);
                break;
            }
            case 6: /* 相册 */ {
                Intent intent = new Intent(getActivity(), ToolAlbumActivity.class);
                startActivity(intent);
                break;
            }
            default: {
                break;
            }
        }
    }
}
