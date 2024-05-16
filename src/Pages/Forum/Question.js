import { useEffect, useState } from "react";
import { getRequestFetchingLogic } from "../../Api";
import { useNavigate } from "react-router-dom";

const Question = () => {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    const fetchQuestions = async () => {
      const response = await getRequestFetchingLogic(`/api/questions`);
      setQuestions(response);
    };

    fetchQuestions();
  }, []);
  //question.id, showPosts
  const navigate = useNavigate();

  const handlePostClick = (questionId) => {
    navigate("/forum/posts", { state: { questionId } });
  };

  return (
    <div>
      {questions.map((question) => {
        return (
          <div key={question.id} onClick={() => handlePostClick(question.id)}>
            {question.q_text}
          </div>
        );
      })}
    </div>
  );
};

export default Question;
