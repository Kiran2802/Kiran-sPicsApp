package apps.developer.picsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import apps.developer.picsapp.R;
import apps.developer.picsapp.model.PicsDataList;

public class PicsAdapter extends RecyclerView.Adapter<PicsAdapter.Pics_adapterViewHolder> {

    Context context;
    ArrayList<PicsDataList> picsDataLists;

    public PicsAdapter(Context context, ArrayList<PicsDataList> picsDataLists) {
        this.context=context;
        this.picsDataLists = picsDataLists;
    }

    @NonNull
    @Override
    public Pics_adapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_picardview, parent,false);
        return new Pics_adapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pics_adapterViewHolder holder, int position) {
        PicsDataList list= picsDataLists.get(position);
        holder.txt_picName.setText(list.getAuthor());
        Glide.with(context)
                .load("https://picsum.photos/300/300?image="+list.getId())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.img_pic);
    }

    @Override
    public int getItemCount() {
        return picsDataLists.size();
    }

    public class Pics_adapterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_pic;
        private final TextView txt_picName;

        public Pics_adapterViewHolder(@NonNull View itemView) {
            super(itemView);

            img_pic=(ImageView)itemView.findViewById(R.id.img_pic);
            txt_picName=(TextView)itemView.findViewById(R.id.txt_picName);

        }
    }
}
