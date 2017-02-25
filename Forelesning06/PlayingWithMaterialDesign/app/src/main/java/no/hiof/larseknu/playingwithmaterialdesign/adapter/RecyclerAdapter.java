package no.hiof.larseknu.playingwithmaterialdesign.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import no.hiof.larseknu.playingwithmaterialdesign.R;
import no.hiof.larseknu.playingwithmaterialdesign.model.Landscape;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();

    List<Landscape> mDataList;
    private LayoutInflater inflater;

    public RecyclerAdapter(Context context, List<Landscape> data) {
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        View view = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder" + position);

        Landscape current = mDataList.get(position);
        holder.setData(current, position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void removeItem(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDataList.size());
// 		  notifyDataSetChanged();
    }

    public void addItem(int position, Landscape landscape) {
        mDataList.add(position, landscape);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDataList.size());
//		  notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView imgThumb, imgDelete, imgAdd;
        int position;
        Landscape current;

        public MyViewHolder(View itemView) {
            super(itemView);
            title       = (TextView)  itemView.findViewById(R.id.tvTitle);
            imgThumb    = (ImageView) itemView.findViewById(R.id.img_row);
            imgDelete   = (ImageView) itemView.findViewById(R.id.img_row_delete);
            imgAdd      = (ImageView) itemView.findViewById(R.id.img_row_add);
        }

        public void setData(Landscape current, int position) {
            this.title.setText(current.getTitle());
            imgThumb.setImageResource(current.getImageID());
            this.position = position;
            this.current = current;
        }

        public void setListeners() {
            imgDelete.setOnClickListener(MyViewHolder.this);
            imgAdd.setOnClickListener(MyViewHolder.this);
        }

        @Override
        public void onClick(View v) {
			Log.i("onClick before operatio", position + " " + mDataList.size());
            switch (v.getId()) {
                case R.id.img_row_delete:
                    removeItem(position);
                    break;

                case R.id.img_row_add:
                    addItem(position, current);
                    break;
            }
			Log.i("onClick after operation", mDataList.size() + "");
        }
    }
}
