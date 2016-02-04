package movies.abhimaan.com.popularmovies1;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import movies.constants.Constants;

public class MovieDetailsModel implements Parcelable
{
    public MovieDetailsModel()
        {
            posterPath = "";
            releaseDate = "";
        }

    public boolean selected = false;
    @SerializedName("poster_path")
    @Expose
    public String posterPath;
    @SerializedName("adult")
    @Expose
    public Boolean adult;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("original_title")
    @Expose
    public String originalTitle;
    @SerializedName("original_language")
    @Expose
    public String originalLanguage;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath;
    @SerializedName("popularity")
    @Expose
    public Float popularity;
    @SerializedName("vote_count")
    @Expose
    public Integer voteCount;
    @SerializedName("video")
    @Expose
    public Boolean video;
    @SerializedName("vote_average")
    @Expose
    public Float voteAverage = 0f;

    @Override
    public String toString()
        {
            return "MovieDetailsModel{" +
                    "posterPath='" + posterPath + '\'' +
                    ", adult=" + adult +
                    ", overview='" + overview + '\'' +
                    ", releaseDate='" + releaseDate + '\'' +
                    ", genreIds=" + genreIds +
                    ", id=" + id +
                    ", originalTitle='" + originalTitle + '\'' +
                    ", originalLanguage='" + originalLanguage + '\'' +
                    ", title='" + title + '\'' +
                    ", backdropPath='" + backdropPath + '\'' +
                    ", popularity=" + popularity +
                    ", voteCount=" + voteCount +
                    ", video=" + video +
                    ", voteAverage=" + voteAverage +
                    '}';
        }

    public String getPosterPath()
        {
            return Constants.PHOTOURL + posterPath;
        }

    public void setPosterPath(String posterPath)
        {
            this.posterPath = posterPath;
        }

    public Boolean getAdult()
        {
            return adult;
        }

    public void setAdult(Boolean adult)
        {
            this.adult = adult;
        }

    public String getOverview()
        {
            return overview;
        }

    public void setOverview(String overview)
        {
            this.overview = overview;
        }

    public String getReleaseDate()
        {
            return releaseDate;
        }

    public String getReleaseYear()
        {
            if (releaseDate.isEmpty())
                return releaseDate;
            return releaseDate.substring(0, 4);
        }

    public void setReleaseDate(String releaseDate)
        {
            this.releaseDate = releaseDate;
        }

    public List<Integer> getGenreIds()
        {
            return genreIds;
        }

    public void setGenreIds(List<Integer> genreIds)
        {
            this.genreIds = genreIds;
        }

    public Integer getId()
        {
            return id;
        }

    public void setId(Integer id)
        {
            this.id = id;
        }

    public String getOriginalTitle()
        {
            return originalTitle;
        }

    public void setOriginalTitle(String originalTitle)
        {
            this.originalTitle = originalTitle;
        }

    public String getOriginalLanguage()
        {
            return originalLanguage;
        }

    public void setOriginalLanguage(String originalLanguage)
        {
            this.originalLanguage = originalLanguage;
        }

    public String getTitle()
        {
            return title;
        }

    public void setTitle(String title)
        {
            this.title = title;
        }

    public String getBackdropPath()
        {
            return backdropPath;
        }

    public void setBackdropPath(String backdropPath)
        {
            this.backdropPath = backdropPath;
        }

    public Float getPopularity()
        {
            return popularity;
        }

    public void setPopularity(Float popularity)
        {
            this.popularity = popularity;
        }

    public Integer getVoteCount()
        {
            return voteCount;
        }

    public void setVoteCount(Integer voteCount)
        {
            this.voteCount = voteCount;
        }

    public Boolean getVideo()
        {
            return video;
        }

    public void setVideo(Boolean video)
        {
            this.video = video;
        }

    public Float getVoteAverage()
        {
            return voteAverage;
        }

    public String getVoteAverageString()
        {
            return String.valueOf(voteAverage) + "/10";
        }

    public void setVoteAverageString(String voteAverageString)
        {
            this.voteAverage = Float.valueOf(voteAverage);
        }

    public void setVoteAverage(Float voteAverage)
        {
            this.voteAverage = voteAverage;
        }


    @Override
    public int describeContents()
        {
            return 0;
        }

    @Override
    public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeString(getPosterPath());
            dest.writeString(overview);
            dest.writeString(getReleaseDate());
            dest.writeString(getOriginalTitle());
            dest.writeFloat(getVoteAverage());
        }

    protected MovieDetailsModel(Parcel in)
        {
            posterPath = in.readString();
            overview = in.readString();
            releaseDate = in.readString();
            originalTitle = in.readString();
            setVoteAverage(in.readFloat());
            originalLanguage = in.readString();
            title = in.readString();
            backdropPath = in.readString();
        }

    public static final Creator<MovieDetailsModel> CREATOR = new Creator<MovieDetailsModel>()
    {
        @Override
        public MovieDetailsModel createFromParcel(Parcel in)
            {
                return new MovieDetailsModel(in);
            }

        @Override
        public MovieDetailsModel[] newArray(int size)
            {
                return new MovieDetailsModel[size];
            }
    };
}
