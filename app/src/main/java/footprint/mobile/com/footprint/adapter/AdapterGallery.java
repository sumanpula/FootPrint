package footprint.mobile.com.footprint.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.dto.FootPrints;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class AdapterGallery extends RecyclerView.Adapter {
    private FootPrints footPrints;
    public AdapterGallery(FootPrints footPrints) {
        this.footPrints = footPrints;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_foot_print,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.fileImage.setImageBitmap(footPrints.getBitmaps().get(position));
        myViewHolder.fileName.setText(footPrints.getFileNames().get(position));
    }

    @Override
    public int getItemCount() {
        return footPrints.getBitmaps().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView fileName;
        private ImageView fileImage;
        public MyViewHolder(View itemView) {
            super(itemView);

            fileImage = itemView.findViewById(R.id.ivViewFootPrint);

            fileName = itemView.findViewById(R.id.tvFootPrintName);
        }
    }
}
