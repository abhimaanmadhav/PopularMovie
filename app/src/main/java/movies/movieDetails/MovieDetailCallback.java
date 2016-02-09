package movies.movieDetails;

import java.util.ArrayList;

/**
 * Created by Abhimaan on 05/02/16.
 */
public interface MovieDetailCallback
{
    public void onFailure();

    public void trailerRespose(ArrayList<TrailerModel> trailerList);

    public void reviewResponse(ArrayList<ReviewModel> reviewList);
}
