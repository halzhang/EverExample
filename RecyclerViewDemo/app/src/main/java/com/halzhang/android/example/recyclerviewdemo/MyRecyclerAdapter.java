package com.halzhang.android.example.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghanguo@yy.com on 2015/2/27.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<String> mDatas = new ArrayList<>(0);

    public MyRecyclerAdapter(List<String> datas) {
        if (datas != null) {
            mDatas.addAll(datas);
        }
    }

    public static final class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final Button button;


        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
            button = (Button) itemView.findViewById(R.id.item_button);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), String.valueOf(getPosition()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public Button getButton() {
            return button;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getButton().setText(String.valueOf(position));
        holder.getTextView().setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}
