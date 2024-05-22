import { useEffect, useState } from "react";
import { getRequestFetchingLogic, fetchResponseRequest } from "../../Api";
import { useLocation } from "react-router-dom";
import { useAuth } from "../../Auth/AuthProvider";

const Post = () => {
  const [posts, setPosts] = useState([]);
  const [comment, setComment] = useState("");
  const location = useLocation();
  const { questionId } = location.state || {};
  const { userCredential, userRole } = useAuth();

  const fetchPosts = async () => {
    const response = await getRequestFetchingLogic(
      `/api/posts/question/${questionId}`
    );
    setPosts(response);
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const handleSubmitComment = async (e) => {
    e.preventDefault();
    const post = {
      content: comment,
      user: { username: userCredential?.username },
      questionId: questionId,
    };

    const response = await fetchResponseRequest(`/api/posts`, post);
    if (response) {
      posts.push(post);
      fetchPosts();
    }

    setComment("");
  };

  const handleDeletePost = async (postId) => {
    const response = await fetchResponseRequest(`/api/posts/delete/${postId}`);
    if (response) {
      fetchPosts();
    }
  };

  const canDelete = (postUser) => {
    return (
      userRole === "ROLE_ADMIN" ||
      userCredential?.username === postUser.username
    );
  };

  return (
    <div className="forum">
      <h2>Discussion Thread</h2>
      {posts.map((post) => {
        return (
          <div key={post.id} className="post-card">
            <div className="post-header">
              <h3>
                {post.user.firstname} {post.user.lastname}
              </h3>
              <small>{post.created_at}</small>
              {canDelete(post.user) && (
                <button onClick={() => handleDeletePost(post.id)}>
                  Delete Post
                </button>
              )}
            </div>
            <p className="post-content">{post.content}</p>
          </div>
        );
      })}

      <form onSubmit={handleSubmitComment} className="comment-form">
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
