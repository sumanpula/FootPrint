package footprint.mobile.com.footprint.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import footprint.mobile.com.footprint.R;

/**
 * Created by Suman Pula on 3/14/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class AdapterShowImagesToEdt extends RecyclerView.Adapter {
    private List<File> fileList;
    private OnEditImageClickListener onEditImageClickListener;

    public AdapterShowImagesToEdt(List<File> fileList) {
        this.fileList = fileList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_images,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.imageView.setImageURI(Uri.parse(fileList.get(position).getAbsolutePath()));
        myViewHolder.textView.setText(fileList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CardView  cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);

            imageView = itemView.findViewById(R.id.ivImage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        onEditImageClickListener.onImageClick(fileList.get(getAdapterPosition()),
                                cardView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        onEditImageClickListener.onImageLongPress(fileList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });


            textView = itemView.findViewById(R.id.tvImageName);

        }
    }

    public interface OnEditImageClickListener {
        void onImageClick(final File file,final CardView cardView) throws Exception;
        void onImageLongPress(final List<File> fileList) throws Exception;
    }

    public void setOnEditImageClickListener(OnEditImageClickListener onEditImageClickListener) {
        this.onEditImageClickListener = onEditImageClickListener;
    }

    public void updateFiles(final File file) {
       fileList.remove(file);
       notifyDataSetChanged();
    }
}
