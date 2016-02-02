
package com.abhimaan;

import java.util.ArrayList;

public class MovieResponse
{

    public int page;
    public ArrayList<Result> results = new ArrayList<Result>();
    public int totalResults;
    public int totalPages;



    @Override
    public String toString()
        {
            return "MovieResponse{" +
                    "page=" + page +
                    ", results=" + results +
                    ", totalResults=" + totalResults +
                    ", totalPages=" + totalPages +
                    '}';
        }
}
