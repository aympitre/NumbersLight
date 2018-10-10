package tapptic.com.numberslight;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.ViewHolder> {

    public static String FIELD_NAME = "name";
    public static String FIELD_TEXT = "text";
    public static String FIELD_IMAGE = "image";

    private Context myContext;
    private ArrayList<HashMap<String, Object>> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    LightsAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
        myContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_light, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position).get("title").toString();
        holder.myTextView.setText(animal);

        BitmapDownloaderTask task = new BitmapDownloaderTask(holder.myImageView, myContext);
        task.execute(mData.get(position).get("image").toString());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.txtTitle);
            myImageView = (ImageView) itemView.findViewById(R.id.imgThumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).get("title").toString();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

