package com.example.aimovies.data

/**
 * Created by Mohamed Hashim on 26/08/2023.
 */

object Queries {
    const val RECOMMENDATION_QUERY = """
      SELECT user_id, ARRAY_AGG(STRUCT(movie_title, genre, predicted_rating)
    ORDER BY predicted_rating DESC LIMIT 10) AS top_recommendations
FROM (
  SELECT DISTINCT
    recommended_movies.user_id,
    movie_titles.movie_title,
    movie_titles.genre,
    recommended_movies.predicted_rating
  FROM
    `ai-movies-6f6ce.movielens.recommend_1m` AS recommended_movies
  JOIN
    `ai-movies-6f6ce.movielens.movie_titles` AS movie_titles
  ON
    recommended_movies.item_id = movie_titles.movie_id
) where user_id = 5306
GROUP BY
  user_id;
    """
}