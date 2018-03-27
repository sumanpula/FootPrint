package footprint.mobile.com.footprint.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import footprint.mobile.com.footprint.R;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class AdapterFootPrintsList extends RecyclerView.Adapter {
    private List<String> footPrintNames;
    private OnFootPrintItemClickEvent onFootPrintItemClickEvent;
    public AdapterFootPrintsList(List<String> footPrintNames) {
        this.footPrintNames = footPrintNames;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_foot_prints_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       final MyViewHolder myViewHolder = (MyViewHolder) holder;
       myViewHolder.footPrintName.setText(footPrintNames.get(position));
    }

    @Override
    public int getItemCount() {
        return footPrintNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       private TextView footPrintName;
        public MyViewHolder(View itemView) {
            super(itemView);

            footPrintName = itemView.findViewById(R.id.tv);
            footPrintName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onFootPrintItemClickEvent.onFootPrintClick(footPrintNames.get(getAdapterPosition()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public interface OnFootPrintItemClickEvent {
        void onFootPrintClick(final String footPrintName) throws Exception;
    }

    public void setOnFootPrintItemClickEvent(OnFootPrintItemClickEvent onFootPrintItemClickEvent) {
        this.onFootPrintItemClickEvent = onFootPrintItemClickEvent;
    }
}
