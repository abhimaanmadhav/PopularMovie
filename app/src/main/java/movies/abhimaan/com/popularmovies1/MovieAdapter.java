package movies.abhimaan.com.popularmovies1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abhimaan on 28/01/16.
 */
public class MovieAdapter extends BaseAdapter
{
    Context mContext;
    Holder holder;
    ArrayList<MovieDetailsModel> list;
    int currentSelection = -1;

    public MovieAdapter(Context mContext, ArrayList<MovieDetailsModel> list)
        {
            this.mContext = mContext;
            this.list = list;

//            Picasso.with(mContext).setIndicatorsEnabled(true);

        }

    public void addData(ArrayList<MovieDetailsModel> list)
        {
            this.list.addAll(list);
            notifyDataSetChanged();
        }

    public void clear()
        {
            this.list.clear();
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
                    holder.selector = (ImageView) convertView.findViewById(R.id.poster_selected);
                    convertView.setTag(holder);
                }
            holder = (Holder) convertView.getTag();
            if (list.get(position).posterPath != null)
                {
                    Picasso.with(mContext).load(list.get
                            (position)
                            .getPosterPath()
                            .trim()).fit()
                            .into(holder.posterImage);
                } else
                {
                    holder.posterImage.setImageBitmap(null);
                }

            //  Can i use any better technic to show its selected??
            if (list.get(position).selected)
                {
                    currentSelection = position;
                    holder.selector.setVisibility(View.VISIBLE);
                } else
                {
                    holder.selector.setVisibility(View.GONE);
                }
            return convertView;
        }

    class Holder
    {
        ImageView posterImage, selector;
    }

    void setSelectedPosition(int position)
        {
            if (currentSelection != -1)
                list.get(currentSelection).selected = false;
            currentSelection = position;
            list.get(currentSelection).selected = true;
            notifyDataSetChanged();
        }

    public ArrayList<MovieDetailsModel> getData()
        {
            return list;
        }
}
