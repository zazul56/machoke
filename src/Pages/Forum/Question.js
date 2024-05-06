import { useEffect, useState } from "react";
import { getRequestFetchingLogic } from "../../Api";
import Post from "../Forum/Post";

const Question = ({ question }) => {
  const [posts, setPosts] = useState([]);
  const [showPosts, setShowPosts] = useState(false);

  useEffect(() => {
    if (showPosts) {
      const fetchPosts = async () => {
        const response = await getRequestFetchingLogic("/api/posts");
        setPosts(response.data);
      };

      fetchPosts();
    }
  }, [question.id, showPosts]);

  return (
    <div>
      <h3 onClick={() => setShowPosts(!showPosts)}>{question.q_text}</h3>
      {showPosts && (
        <div>
          {posts.map((post) => (
            <Post key={post.id} post={post} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Question;
