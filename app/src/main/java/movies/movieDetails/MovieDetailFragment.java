package movies.movieDetails;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import movies.abhimaan.com.popularmovies1.BR;
import movies.abhimaan.com.popularmovies1.MovieDetailsModel;
import movies.abhimaan.com.popularmovies1.R;
import movies.abhimaan.com.popularmovies1.databinding.FragmentMovieDetailBinding;
import movies.database.MovieDataBase;
import movies.movieDetails.network.MovieDetailServiceManager;
import utility.Logger;
import utility.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment implements
        MovieDetailCallback, View.OnClickListener
{
    public static final String ARG_PARAM1 = "details";
    MovieDetailsModel moveDetailsObj;

    FragmentMovieDetailBinding mBinding;
    MovieDetailServiceManager manager;
    ArrayList<ReviewModel> reviews;
    ArrayList<TrailerModel> trailers;
    MovieDetailTabletInterface mMovieDetailTabletInterface;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
            moveDetailsObj.setFavorite(MovieDataBase.getInstanse(getActivity()).isMovieFavorite
                    (moveDetailsObj.getId()));
            setRetainInstance(true);
            manager = new MovieDetailServiceManager(this);
            if (moveDetailsObj.getId() != -1 && Utils.isConnected(getActivity()))
                {
                    manager.fetchReviews(moveDetailsObj.getId());
                    manager.fetchTrailers(moveDetailsObj.getId());
                }
            trailers = new ArrayList
                    <TrailerModel>(0);
            reviews = new ArrayList<ReviewModel>(0);
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
            Picasso.with(getActivity()).load(moveDetailsObj.getPosterPath()).fit().placeholder(R
                    .drawable.loading).into(mBinding
                    .poster);

            mBinding.trailers.setAdapter(new TrailerAdapter(getActivity(), trailers));
            mBinding.reviews.setAdapter(new ReviewsAdapter(getActivity(), reviews));
            mBinding.trailers.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBinding.reviews.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBinding.fav.setOnClickListener(this);
            return mBinding.getRoot();
        }

    public void shareUrl()
        {
            if (trailers.size() > 0)
                {
                    Intent intent = new Intent();
                    intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string
                            .share_intent_text) + " "
                            + trailers.get(0).getKey());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Youtube trailer");
                    intent.setType("text/plain");
                    Intent chooser = Intent.createChooser(intent, "Share");

                    if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                        {
                            startActivity(chooser);
                        }
                }
        }

    public void setData(MovieDetailsModel data)
        {
            moveDetailsObj = data;
            mBinding.setMoveDetailsObj(moveDetailsObj);
            Picasso.with(getActivity()).load(moveDetailsObj.getPosterPath()).placeholder(R
                    .drawable.loading).error(R.drawable.error).fit().into(mBinding
                    .poster);
            moveDetailsObj.setFavorite(MovieDataBase.getInstanse(getActivity()).isMovieFavorite
                    (moveDetailsObj.getId()));
            ((TrailerAdapter) mBinding.trailers.getAdapter()).clearTrailers();
            ((ReviewsAdapter) mBinding.reviews.getAdapter()).clearReviews();
            manager.resetPages();
            if (moveDetailsObj.getId() != -1 && Utils.isConnected(getActivity()))
                {
                    manager.fetchReviews(moveDetailsObj.getId());
                    manager.fetchTrailers(moveDetailsObj.getId());
                }
        }


    @Override
    public void onSaveInstanceState(Bundle outState)
        {
            outState.putParcelable("details", moveDetailsObj);
            super.onSaveInstanceState(outState);
        }

    @Override
    public void onFailure()
        {
            Logger.error(this, "failed");
        }

    @Override
    public void trailerRespose(ArrayList<TrailerModel> trailerList)
        {
//            trailers = trailerList;
            Logger.error(this, "trailer response ");
            if (isAdded())
                {
                    ((TrailerAdapter) mBinding.trailers.getAdapter()).setTrailers
                            (trailerList);
                }

        }

    @Override
    public void reviewResponse(ArrayList<ReviewModel> reviewList)
        {
            Logger.error(this, "review response " + reviewList.toString());
//            reviews.addAll(reviewList);
            if (isAdded())
                {

                    ((ReviewsAdapter) mBinding.reviews.getAdapter()).addReviews
                            (reviewList);
                }
        }

    @Override
    public void onDestroy()
        {
            super.onDestroy();
        }


    @Override
    public void onClick(View v)
        {
            if (moveDetailsObj.isFavorite())
                {
                    MovieDataBase.getInstanse(getActivity()).removeFavorite(moveDetailsObj.getId());
                    if (!Utils.isTablet(getActivity()))
                        {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        }else{
                        mMovieDetailTabletInterface.removedAsFavorite(moveDetailsObj.getId());
                    }
                } else
                {
                    MovieDataBase.getInstanse(getActivity()).insertFavorite(moveDetailsObj);
                    moveDetailsObj.setFavorite(true);
                    mBinding.setVariable(BR.moveDetailsObj, moveDetailsObj);
                }
        }

    @Override
    public void onAttach(Context context)
        {
            super.onAttach(context);
            if (getActivity() instanceof MovieDetailTabletInterface)
                {
                    mMovieDetailTabletInterface = (MovieDetailTabletInterface) getActivity();
                }
        }

    @Override
    public void onAttach(Activity context)
        {
            super.onAttach(context);
            if (getActivity() instanceof MovieDetailTabletInterface)
                {
                    mMovieDetailTabletInterface = (MovieDetailTabletInterface) getActivity();
                }
        }

    @Override
    public void onDetach()
        {
            mMovieDetailTabletInterface = null;
            super.onDetach();
        }

    public interface MovieDetailTabletInterface
    {
        void removedAsFavorite(int id);
    }
}
