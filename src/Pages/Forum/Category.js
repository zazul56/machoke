import { useNavigate } from "react-router-dom";

const Category = ({ category }) => {
  const navigate = useNavigate();

  const handleQuestionClick = () => {
    navigate(`/forum/questions`);
  };

  return (
    <div>
      <h2 onClick={handleQuestionClick}>{category.name}</h2>
    </div>
  );
};

export default Category;
