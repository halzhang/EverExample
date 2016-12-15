package com.halzhang.android.example.meterialdesignexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static void start(Context context) {
        Intent starter = new Intent(context, RecyclerViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mImageView.setImageResource(R.mipmap.ic_launcher);
            holder.mTextView.setText(String.valueOf(position));
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), TransitionActivity.class);
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(RecyclerViewActivity.this, view,
                            ViewCompat.getTransitionName(view)).toBundle();
                    ActivityCompat.startActivity(RecyclerViewActivity.this, intent, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView mImageView;
            public TextView mTextView;


            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.imageView);
                mTextView = (TextView) itemView.findViewById(R.id.textView);
            }
        }
    }


}
