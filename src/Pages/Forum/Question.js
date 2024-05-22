import { useEffect, useState } from "react";
import { getRequestFetchingLogic, fetchResponseRequest } from "../../Api";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { useAuth } from "../../Auth/AuthProvider";

const Question = () => {
  const [questions, setQuestions] = useState([]);
  const location = useLocation();
  const navigate = useNavigate();
  const { categoryId } = location.state || {};
  const { userCredential, userRole } = useAuth();

  const fetchQuestions = async () => {
    const response = await getRequestFetchingLogic(
      `/api/questions/category/${categoryId}`
    );

    setQuestions(response);
  };

  useEffect(() => {
    fetchQuestions();
  }, []);
  //question.id, showPost

  const handlePostClick = (questionId) => {
    navigate("/forum/posts", { state: { questionId } });
  };

  const handleDeleteQuestion = async (questionId) => {
    const response = await fetchResponseRequest(
      `/api/questions/delete/${questionId}`
    );

    if (response.ok) {
      fetchQuestions();
    }
  };

  const canDelete = (question) => {
    return (
      userRole === "ROLE_ADMIN" ||
      userCredential?.username === question.username
    );
  };

  return (
    <div className="question-list">
      <h1>Forum Questions</h1>
      {questions.map((question) => {
        return (
          <div
            key={question.id}
            className="question-card"
            onClick={() => handlePostClick(question.id)}>
            <div className="question-text">
              <h3>{question.q_text}</h3>
            </div>
            <div className="question-info">
              <small>
                Asked on: {new Date(question.created_at).toLocaleDateString()}
              </small>
              {canDelete(question) && (
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleDeleteQuestion(question.id);
                  }}>
                  Delete
                </button>
              )}
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default Question;
