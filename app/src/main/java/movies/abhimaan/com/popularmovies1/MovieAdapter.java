package movies.abhimaan.com.popularmovies1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.abhimaan.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import movies.constants.Constants;

/**
 * Created by Abhimaan on 28/01/16.
 */
public class MovieAdapter extends BaseAdapter
{
    Context mContext;
    Holder holder;
    ArrayList<Result> list;

    public MovieAdapter(Context mContext, ArrayList<Result> list)
        {
            this.mContext = mContext;
            this.list = list;
            Picasso.with(mContext).setLoggingEnabled(true);
            Picasso.with(mContext).setIndicatorsEnabled(true);

        }

    public void resetData(ArrayList<Result> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

    @Override
    public int getCount()
        {
            return list.size();
        }

    @Override
    public Object getItem(int position)
        {
            return list.get(position);
        }

    @Override
    public long getItemId(int position)
        {
            return position;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
        {

            if (convertView == null)
                {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.popular_grid,
                            parent, false);
                    holder = new Holder();
                    holder.posterImage = (ImageView) convertView.findViewById(R.id.poster);
                    convertView.setTag(holder);
                }
            holder = (Holder) convertView.getTag();
            Log.d("adapter", Constants.PHOTOURL + "/w500" + list.get(position).posterPath);
            if (list.get(position).posterPath != null)
                {
                    Picasso.with(mContext).load(Constants.PHOTOURL + "/w185" + list.get
                            (position)
                            .posterPath
                            .trim()).fit()
                            .into(holder.posterImage);
                }else{
                holder.posterImage.setImageBitmap(null);
            }
            return convertView;
        }

    class Holder
    {
        ImageView posterImage;
    }
}
