package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
public class GridApdapter extends ArrayAdapter<ImageItem> {

    public GridApdapter(@NonNull Context context, ArrayList<ImageItem> ItemArrayList) {
        super(context, 0, ItemArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.album, parent, false);
        }

        ImageItem Item = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.name);
        ImageView courseIV = listitemView.findViewById(R.id.avatar);

        courseTV.setText(Item.getCourse_name());
        courseIV.setImageResource(Item.getImgid());
        return listitemView;
    }
}