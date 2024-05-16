import { useEffect, useState } from "react";
import { getRequestFetchingLogic, fetchRequest } from "../../Api";
import { useLocation } from "react-router-dom";
import { useAuth } from "../../Auth/AuthProvider";

const Post = () => {
  const [posts, setPosts] = useState([]);
  const [comment, setComment] = useState("");
  const location = useLocation();
  const { questionId } = location.state || {};
  const { userCredential } = useAuth();

  useEffect(() => {
    const fetchPosts = async () => {
      const response = await getRequestFetchingLogic(
        `/api/posts/question/${questionId}`
      );
      setPosts(response);
    };

    fetchPosts();
  }, []);

  const handleSubmitComment = async (e) => {
    e.preventDefault();
    const post = {
      content: comment,
      user: { username: userCredential.username },
      questionId: questionId,
    };
    const response = await fetchRequest(`/api/posts`, post);
    if (response) {
      setPosts(...posts);
    }
    setComment("");
  };

  return (
    <div>
      {posts.map((post) => {
        return <div key={post.id}>{post.content}</div>;
      })}

      <form onSubmit={handleSubmitComment}>
        <textarea
          rows={5}
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          type="text"
        />

        <button>Add Comment</button>
      </form>
    </div>
  );
};

export default Post;
