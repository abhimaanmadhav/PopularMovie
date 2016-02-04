package movies.movieDetails;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import movies.abhimaan.com.popularmovies1.MovieDetailsModel;
import movies.abhimaan.com.popularmovies1.R;
import movies.abhimaan.com.popularmovies1.databinding.FragmentMovieDetailBinding;
import utility.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment
{
    public static final String ARG_PARAM1 = "details";
    MovieDetailsModel moveDetailsObj;

    FragmentMovieDetailBinding mBinding;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(MovieDetailsModel movie)
        {
            MovieDetailFragment fragment = new MovieDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARG_PARAM1, movie);
            fragment.setArguments(args);
            return fragment;
        }

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            moveDetailsObj = getArguments().getParcelable(ARG_PARAM1);
            Logger.debug(this, moveDetailsObj);
            setRetainInstance(true);
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
        {
            mBinding = DataBindingUtil.inflate(inflater, R.layout
                    .fragment_movie_detail, container, false);
            if (savedInstanceState != null)
                {
                    moveDetailsObj = savedInstanceState.getParcelable("details");
                }
            mBinding.setMoveDetailsObj(moveDetailsObj);
            Picasso.with(getActivity()).load(moveDetailsObj.getPosterPath()).fit().into(mBinding
                    .poster);

            return mBinding.getRoot();
        }

    public void setData(MovieDetailsModel data)
        {
            moveDetailsObj = data;
            mBinding.setMoveDetailsObj(moveDetailsObj);
            Picasso.with(getActivity()).load(moveDetailsObj.getPosterPath()).fit().into(mBinding
                    .poster);
        }


    @Override
    public void onSaveInstanceState(Bundle outState)
        {
            outState.putParcelable("details", moveDetailsObj);
            super.onSaveInstanceState(outState);
        }
}
