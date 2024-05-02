import { useEffect, useState } from "react";
import { getRequestFetchingLogic } from "./getRequestFetchingLogic";
import Post from "./Post";

const LatestPosts = () => {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        let postsData = await getRequestFetchingLogic(); //dodaj url od BE
        setPosts(postsData);
      } catch (error) {
        setError(error.message);
        setPosts([]);
      } finally {
        setLoading(false);
      }
    };
    fetchPosts();
  }, []);

  return (
    <div>
      <h2>Posljedne objave</h2>
      {loading ? (
        <div className="">Loading posts...</div>
      ) : error ? (
        <div> {error} </div>
      ) : (
        posts.map((post) => {
          <Post key={post.id} {...post}></Post>;
        })
      )}
    </div>
  );
};

export default LatestPosts;
