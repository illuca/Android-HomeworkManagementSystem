package com.sayo.homeworkmanagementsystem.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
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
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sayo.homeworkmanagementsystem.MyHomeworkItemRecyclerViewAdapter;
import com.sayo.homeworkmanagementsystem.R;
import com.sayo.homeworkmanagementsystem.bean.HomeworkItem;
import com.sayo.homeworkmanagementsystem.bean.Page;
import com.sayo.homeworkmanagementsystem.dummy.DummyContent;
import com.sayo.homeworkmanagementsystem.utils.JSONUtils;
import com.sayo.homeworkmanagementsystem.utils.NetworkAPI;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class HomeworkItemFragment extends Fragment {

    private BaseAdapter adapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private EditText searchByHomeworkIdEditView;
    private EditText searchByHomeworkTitleEditView;
    private Button searchBtn;

    private ListView homeworkItemListView;
    private TextView homeworkItemCurrentPageTextView;

    private TextView previousPageTextView;
    private TextView nextPageTextView;

    private Button pageJumpBtn;
    private EditText pageNumberEditText;
    private Page<HomeworkItem> homeworkItemPage;

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
        homeworkItemListView = view.findViewById(R.id.homework_item_list_view);

        searchByHomeworkTitleEditView = view.findViewById(R.id.search_homework_title_edit_view);
        searchByHomeworkTitleEditView = view.findViewById(R.id.search_homework_id_edit_view);

        searchBtn = view.findViewById(R.id.search_btn);

        homeworkItemCurrentPageTextView = view.findViewById(R.id.homework_item_currentPage_text_view);
        previousPageTextView = view.findViewById(R.id.previous_page_text_view);
        nextPageTextView = view.findViewById(R.id.next_page_text_view);

        pageNumberEditText = view.findViewById(R.id.page_number_edit_text);
        pageJumpBtn = view.findViewById(R.id.page_jump_btn);

        homeworkItemPage = new Page<>();

        JSONObject queryForm = new JSONObject();
        JSONUtils.put(queryForm, "homeworkId", "");
        JSONUtils.put(queryForm, "homeTitle", "");

        getPageCount(queryForm);

        previousPageTextView.setOnClickListener(v -> {

            // 当前页面数大于1才有上一页
            if (homeworkItemPage.getPageNo() > 1) {
                getPage(homeworkItemPage.getPageNo() - 1);
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "已到达首页", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        nextPageTextView.setOnClickListener(v -> {
            //
            if (homeworkItemPage.getPageNo() < homeworkItemPage.getPageTotal()) {
                getPage(homeworkItemPage.getPageNo() + 1);
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "已到达最后一页", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 获取总页数，并跳到第一页
     *
     * @param queryForm
     */
    public void getPageCount(JSONObject queryForm) {
        String url = "http://192.168.8.118:9090/student/homework/page/count?homeworkId=&homeworkTitle=";
        NetworkAPI.query(url, null).observe(getActivity(), new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject resultVO) {
                if (NetworkAPI.isSuccess(resultVO) == true) {
                    Log.e("query", resultVO.toString());
                    // 去继续获取页面
                    homeworkItemPage.setPageTotal((int) JSONUtils.get(resultVO, "data"));
                    getPage(1);
                }
            }
        });
    }

    /**
     * 获取页面
     *
     * @param pageIndex
     */
    public void getPage(int pageIndex) {

        // 请求连接
        String url = "http://192.168.8.118:9090/student/homework/page/" + pageIndex + "?homeworkId=&homeworkTitle=";
        NetworkAPI.query(url, null).observe(getActivity(), new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject resultVO) {
                if (NetworkAPI.isSuccess(resultVO) == true) {
                    Log.e("getPage", resultVO.toString());
                    Gson gson = new Gson();
                    JSONArray data = (JSONArray) JSONUtils.get(resultVO, "data");
                    List<HomeworkItem> list = gson.fromJson(data.toString(), new TypeToken<List<HomeworkItem>>() {
                    }.getType());
                    // 更新页面数据
                    homeworkItemPage.setItems(list);
                    homeworkItemPage.setPageNo(pageIndex);
                    homeworkItemPage.setPageNo(pageIndex);
                    // 更新视图
                    homeworkItemCurrentPageTextView.setText("第" + homeworkItemPage.getPageNo() + "页");
                    adapter = new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return homeworkItemPage.getItems().size();
                        }

                        @Override
                        public Object getItem(int i) {
                            return null;
                        }

                        @Override
                        public long getItemId(int i) {
                            return 0;
                        }

                        @Override
                        public View getView(int i, View convertView, ViewGroup parent) {
                            convertView = View.inflate(getContext(), R.layout.fragment_homework_item, null);
                            TextView homeworkId = convertView.findViewById(R.id.homework_item_id_text_view);
                            TextView homeworkTitle = convertView.findViewById(R.id.homework_item_title_text_view);
                            TextView homeworkItemContent = convertView.findViewById(R.id.homework_item_content_text_view);
                            homeworkId.setText(homeworkId.getText() + Integer.toString(homeworkItemPage.getItems().get(i).getHomeworkId()));
                            homeworkTitle.setText(homeworkTitle.getText() + homeworkItemPage.getItems().get(i).getHomeworkTitle());
                            homeworkItemContent.setText(homeworkItemContent.getText() + homeworkItemPage.getItems().get(i).getHomeworkContent());

                            return convertView;
                        }

                        @Override
                        public CharSequence[] getAutofillOptions() {
                            return super.getAutofillOptions();
                        }
                    };
                    homeworkItemListView.setAdapter(adapter);
                } // networkAPI is success
            }
        });
    }
}