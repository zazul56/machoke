import { useEffect, useState } from "react";
import { getRequestFetchingLogic } from "../../Api";
import Question from "../Forum/Question";

const Category = ({ category }) => {
  const [questions, setQuestions] = useState([]);

  useEffect(() => {
    const fetchQuestions = async () => {
      const response = await getRequestFetchingLogic("/api/questions");
      setQuestions(response.data);
    };

    fetchQuestions();
  }, [category.id]);
  //samo ako je category.id taj onda mi pokreni ovo etc...
  return (
    <div>
      <h2>{category.name}</h2>
      {questions.map((question) => (
        <Question key={question.id} question={question} />
      ))}
    </div>
  );
};

export default Category;
