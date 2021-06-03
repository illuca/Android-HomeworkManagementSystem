package com.sayo.homeworkmanagementsystem.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sayo.homeworkmanagementsystem.MyHomeworkItemRecyclerViewAdapter;
import com.sayo.homeworkmanagementsystem.R;
import com.sayo.homeworkmanagementsystem.dummy.DummyContent;
import com.sayo.homeworkmanagementsystem.utils.JSONUtils;
import com.sayo.homeworkmanagementsystem.utils.NetworkAPI;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.concurrent.Callable;

/**
 * A fragment representing a list of Items.
 */
public class HomeworkItemFragment extends Fragment {

    private int pageCount;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeworkItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeworkItemFragment newInstance(int columnCount) {
        HomeworkItemFragment fragment = new HomeworkItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyHomeworkItemRecyclerViewAdapter(DummyContent.ITEMS));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JSONObject queryForm = new JSONObject();
        JSONUtils.put(queryForm, "homeworkId", "");
        JSONUtils.put(queryForm, "homeTitle", "");
        query(queryForm);
    }

    public void query(JSONObject queryForm) {

        NetworkAPI.query(NetworkAPI.url, null).observe(getActivity(), new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject result) {
                System.out.println(result);
            }
        });
    }

    public static void getPage(int pageIndex) {
        NetworkAPI.url = "http://192.168.8.118:9090/student/homework/page/1?homeworkId=&homeworkTitle=";
        // JSONObject resultVO = NetworkAPI.query("", null);
        // if (NetworkAPI.isSuccess(resultVO) == true) {
        //     Object data = JSONUtils.get(resultVO, "data");
        //     System.out.println("data = " + data);
        // } else {
        //     Log.e("query", "请求作业页面失败");
        // }
    }
}