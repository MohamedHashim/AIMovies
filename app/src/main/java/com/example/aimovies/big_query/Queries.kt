package com.example.aimovies.big_query

/**
 * Created by Mohamed Hashim on 26/08/2023.
 */

object Queries {
    fun recommendationQuery(userId : String) = """
SELECT
  user_id,
  ARRAY_AGG(STRUCT(tmdbId, movie_title, genre, predicted_rating)
    ORDER BY predicted_rating DESC LIMIT 5) AS top_recommendations
FROM (
  SELECT DISTINCT
    recommended_movies.user_id,
    movie_titles.movie_title,
    movie_titles.genre,
    recommended_movies.predicted_rating,
    links.tmdbId
  FROM
    `ai-movies-6f6ce.movielens.recommend_1m` AS recommended_movies
  JOIN
    `ai-movies-6f6ce.movielens.movie_titles` AS movie_titles
  ON
    recommended_movies.item_id = movie_titles.movie_id
  LEFT JOIN
    `ai-movies-6f6ce.movielens.movielens_links` AS links
  ON
    movie_titles.movie_id = links.movieId
) where user_id = $userId
GROUP BY
  user_id;
    """
}